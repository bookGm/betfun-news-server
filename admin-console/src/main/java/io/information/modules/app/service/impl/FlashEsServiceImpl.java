package io.information.modules.app.service.impl;

import com.guansuo.common.StringUtil;
import io.elasticsearch.dao.EsFlashDao;
import io.elasticsearch.entity.EsFlashEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.common.utils.BeanHelper;
import io.information.modules.app.dao.InNewsFlashDao;
import io.information.modules.app.entity.InNewsFlash;
import io.information.modules.app.service.FlashEsService;
import io.mq.utils.Constants;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class FlashEsServiceImpl implements FlashEsService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private EsFlashDao esFlashDao;
    @Autowired
    private InNewsFlashDao flashDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public PageUtils searchFlash(SearchRequest request) {
        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
            String key = request.getKey();
            Integer pageSize = request.getPageSize();
            Integer currPage = request.getCurrPage();
            currPage -= 1;
            if (currPage < 0) {
                currPage = 0;
            }

            //???????????????[????????????????????????]
            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
            //??????????????????
            searchQuery.withQuery(multiMatchQuery(key, "nTitle", "nBrief", "nContent")
                    .operator(Operator.OR).minimumShouldMatch("70%"))
                    //????????????...
                    .withIndices("flashs")
                    .withPageable(PageRequest.of(currPage, pageSize));

            //????????????
            withHighlight(searchQuery);
            //???????????????????????????
            AggregatedPage<EsFlashEntity> esFlashEntities = elasticsearchTemplate.queryForPage(searchQuery.build(), EsFlashEntity.class,
                    new SearchResultMapper() {

                        @Override
                        public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
                                                                Pageable pageable) {
                            // TODO Auto-generated method stub
                            List<T> chunk = new ArrayList<>();
                            SearchHits hits = response.getHits();

                            for (SearchHit hit : hits) {
                                if (hits.getHits().length <= 0) {
                                    return null;
                                }
                                Map<String, Object> smap = hit.getSourceAsMap();
                                Map<String, HighlightField> hmap = hit.getHighlightFields();
                                EsFlashEntity esEntity = createEsEntity(smap, hmap);

                                //????????????
                                setHighLight(hit, "nTitle", esEntity);
                                setHighLight(hit, "nBrief", esEntity);
                                setHighLight(hit, "nContent", esEntity);

                                chunk.add((T) (esEntity));
                            }
                            return new AggregatedPageImpl<T>(chunk, pageable, response.getHits().getTotalHits());
                        }
                    });

            List<EsFlashEntity> entityList = esFlashEntities.getContent();
            long total = esFlashEntities.getTotalElements();
            return new PageUtils(entityList, total, pageSize, currPage);
        }
        return null;
    }

    /**
     * ??????????????????
     */
    private void withHighlight(NativeSearchQueryBuilder searchQuery) {
        HighlightBuilder.Field hfield = new HighlightBuilder.Field("nTitle")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield2 = new HighlightBuilder.Field("nBrief")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield3 = new HighlightBuilder.Field("nContent")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        searchQuery.withHighlightFields(hfield, hfield2, hfield3);
    }


    /**
     * ????????????????????????ES??????
     */
    private EsFlashEntity createEsEntity(Map<String, Object> smap, Map<String, HighlightField> hmap) {
        EsFlashEntity ed = new EsFlashEntity();
        if (smap.get("nId") != null)
            ed.setnId(Long.parseLong(String.valueOf(smap.get("nId").toString())));
        if (smap.get("nBull") != null)
            ed.setnBull(Integer.parseInt(String.valueOf(smap.get("nBull").toString())));
        if (smap.get("nBad") != null)
            ed.setnBad(Integer.parseInt(String.valueOf(smap.get("nBad").toString())));
        if (smap.get("nTitle") != null)
            ed.setnTitle(String.valueOf(smap.get("nTitle").toString()));
        if (smap.get("nBrief") != null)
            ed.setnBrief(String.valueOf(smap.get("nBrief").toString()));
        if (smap.get("nContent") != null)
            ed.setnContent(String.valueOf(smap.get("nContent").toString()));
        if (smap.get("nCreateTime") != null) {
            String timeString = String.valueOf(smap.get("nCreateTime").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(timeString);
                ed.setnCreateTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ed;
    }


    /**
     * ?????????????????????set???????????????????????????   ??????BUG
     */
    public void setHighLight(SearchHit searchHit, String field, Object object) {
        Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
        HighlightField highlightField = highlightFieldMap.get(field);
        if (highlightField != null) {
            String highLightMessage = highlightField.fragments()[0].toString();
//            String capitalize = StringUtils.capitalize(field); //???????????????
//            String methodName = "set" + capitalize;//setUName
            String methodName = "set" + field;//setuName
            Class<?> clazz = object.getClass();
            try {
                Method setMethod = clazz.getMethod(methodName, String.class);
                setMethod.invoke(object, highLightMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void updateAll() {
        Iterable<EsFlashEntity> esUsers = esFlashDao.findAll();
        List<InNewsFlash> userList = flashDao.all();
        ArrayList<EsFlashEntity> list = new ArrayList<>();
        if (esUsers.iterator().hasNext()) {
            EsFlashEntity next = esUsers.iterator().next();
            list.add(next);
        }
        List<Long> collect = list.stream().map(EsFlashEntity::getnId).collect(Collectors.toList());
        for (InNewsFlash user : userList) {
            Long id = user.getnId();
            boolean b = collect.contains(id);
            if (!b) {
                EsFlashEntity esUser = BeanHelper.copyProperties(user, EsFlashEntity.class);
                rabbitTemplate.convertAndSend(Constants.flashExchange,
                        Constants.flash_Save_RouteKey, esUser);
            }
        }
    }


    //    @Override
//    public PageUtils searchTest(SearchRequest request) {
//        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
//            String key = request.getKey();
//            Integer size = request.getPageSize();
//            Integer page = request.getCurrPage();
//            //???????????????[????????????????????????]
//            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
//            searchQuery.withQuery(multiMatchQuery(key, "nTitle", "nBrief", "nContent")
//                            .operator(Operator.OR)
//                    /*.minimumShouldMatch("30%")*/)
//                    .withIndices("flashs")
//                    .withPageable(PageRequest.of(page, size));
//            AggregatedPage<EsFlashEntity> esFlashEntities =
//                    elasticsearchTemplate.queryForPage(searchQuery.build(), EsFlashEntity.class);
//            if (null != esFlashEntities && !esFlashEntities.getContent().isEmpty()) {
//                List<EsFlashEntity> list = esFlashEntities.getContent();
//                long totalCount = esFlashEntities.getTotalElements();
//                //???????????? ???????????? ??????????????? ????????????
//                return new PageUtils(list, totalCount, size, page);
//            }
//            return null;
//        }
//        return null;
//    }
}
