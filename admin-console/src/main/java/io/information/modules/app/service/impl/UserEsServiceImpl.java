package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guansuo.common.StringUtil;
import io.elasticsearch.dao.EsUserDao;
import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.common.utils.BeanHelper;
import io.information.modules.app.dao.InUserDao;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.UserEsService;
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
public class UserEsServiceImpl implements UserEsService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private EsUserDao esUserDao;
    @Autowired
    private InUserDao userDao;
    @Autowired
    RabbitTemplate rabbitTemplate;


    //????????????ex
    @Override
    public PageUtils searchUsers(SearchRequest request) {
        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
            String key = request.getKey();
            Integer pageSize = request.getPageSize();
            Integer currPage = request.getCurrPage();
            currPage -= 1;
            if (currPage < 0) {
                currPage = 0;
            }

            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
            //????????????...
            withHighlight(searchQuery);
            //??????????????????
            searchQuery.withQuery(multiMatchQuery(key, "uName", "uIntro", "uNick", "uCompanyName")
                    .operator(Operator.OR).minimumShouldMatch("70%"))
                    .withPageable(PageRequest.of(currPage, pageSize));

            //???????????????????????????
            AggregatedPage<EsUserEntity> esUserEntities = elasticsearchTemplate.queryForPage(searchQuery.build(), EsUserEntity.class,
                    new SearchResultMapper() {

                        @Override
                        public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
                                                                Pageable pageable) {
                            // TODO Auto-generated method stub
                            List<EsUserEntity> chunk = new ArrayList<>();
                            SearchHits hits = response.getHits();

                            for (SearchHit hit : hits) {
                                if (hits.getHits().length <= 0) {
                                    return null;
                                }
                                Map<String, Object> smap = hit.getSourceAsMap();
                                Map<String, HighlightField> hmap = hit.getHighlightFields();
                                EsUserEntity esEntity = createEsEntity(smap, hmap);

                                //????????????
                                setHighLight(hit, "uName", esEntity);
                                setHighLight(hit, "uNick", esEntity);
                                setHighLight(hit, "uIntro", esEntity);
                                setHighLight(hit, "uCompanyName", esEntity);

                                chunk.add(esEntity);
                            }
                            return new AggregatedPageImpl<>((List<T>) chunk, pageable, response.getHits().getTotalHits());
                        }
                    });

            List<EsUserEntity> entityList = esUserEntities.getContent();
//            ????????? ????????? ?????????
            for (EsUserEntity entity : entityList) {
                LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
                if (null != entity && null != entity.getuId()) {
                    queryWrapper.eq(InArticle::getuId, entity.getuId());
                    int articleNumber = articleService.count(queryWrapper);
                    entity.setArticleNumber(articleNumber);
                    List<InArticle> articles = articleService.list(queryWrapper);
                    long readNumber = articles.stream().mapToLong(InArticle::getaReadNumber).sum();
                    long likeNumber = articles.stream().mapToLong(InArticle::getaLike).sum();
                    entity.setReadNumber(readNumber);
                    entity.setLikeNUmber(likeNumber);
                }
            }
            long total = esUserEntities.getTotalElements();
            return new PageUtils(entityList, total, pageSize, currPage);
        }
        return null;
    }


    /**
     * ??????????????????
     */
    private void withHighlight(NativeSearchQueryBuilder searchQuery) {
        HighlightBuilder.Field hfield = new HighlightBuilder.Field("uName")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield2 = new HighlightBuilder.Field("uIntro")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield3 = new HighlightBuilder.Field("uNick")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield4 = new HighlightBuilder.Field("uCompanyName")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        searchQuery.withHighlightFields(hfield, hfield2, hfield3, hfield4);
    }

    /**
     * ????????????????????????ES??????
     */
    private EsUserEntity createEsEntity(Map<String, Object> smap, Map<String, HighlightField> hmap) {
        EsUserEntity ed = new EsUserEntity();
        if (smap.get("uId") != null)
            ed.setuId(Long.parseLong(String.valueOf(smap.get("uId").toString())));
        if (smap.get("uPhoto") != null)
            ed.setuPhoto(String.valueOf(smap.get("uPhoto").toString()));
        if (smap.get("uFocus") != null)
            ed.setuFocus(Integer.parseInt(String.valueOf(smap.get("uFocus").toString())));
        if (smap.get("uFans") != null)
            ed.setuFans(Long.parseLong(String.valueOf(smap.get("uFans").toString())));
        if (smap.get("uPhone") != null)
            ed.setuPhone(String.valueOf(smap.get("uPhone").toString()));
        if (smap.get("uName") != null)
            ed.setuName(String.valueOf(smap.get("uName").toString()));
        if (smap.get("uNick") != null)
            ed.setuNick(String.valueOf(smap.get("uNick").toString()));
        if (smap.get("uIntro") != null)
            ed.setuIntro(String.valueOf(smap.get("uIntro").toString()));
        if (smap.get("uCompanyName") != null)
            ed.setuCompanyName(String.valueOf(smap.get("uCompanyName").toString()));
        if (smap.get("uAuthStatus") != null)
            ed.setuAuthStatus(Integer.parseInt(String.valueOf(smap.get("uAuthStatus").toString())));
        if (smap.get("uAuthType") != null)
            ed.setuAuthType(Integer.parseInt(String.valueOf(smap.get("uAuthType").toString())));
        if (smap.get("uCreateTime") != null) {
            String timeString = String.valueOf(smap.get("uCreateTime").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(timeString);
                ed.setuCreateTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (smap.get("uPotential") != null)
            ed.setuPotential(Integer.parseInt(String.valueOf(smap.get("uPotential").toString())));
        return ed;
    }


    @Override
    public void updateAll() {
        Iterable<EsUserEntity> esUsers = esUserDao.findAll();
        List<InUser> userList = userDao.all();
        ArrayList<EsUserEntity> list = new ArrayList<>();
        if (esUsers.iterator().hasNext()) {
            EsUserEntity next = esUsers.iterator().next();
            list.add(next);
        }
        List<Long> collect = list.stream().map(EsUserEntity::getuId).collect(Collectors.toList());
        for (InUser user : userList) {
            Long id = user.getuId();
            boolean b = collect.contains(id);
            if (!b) {
                EsUserEntity esUser = BeanHelper.copyProperties(user, EsUserEntity.class);
                rabbitTemplate.convertAndSend(Constants.userExchange,
                        Constants.user_Save_RouteKey, esUser);
            }
        }

    }

    /**
     * ?????????????????????set???????????????????????????   ??????BUG
     */
    public void setHighLight(SearchHit searchHit, String field, Object object) {
        Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
        HighlightField highlightField = highlightFieldMap.get(field);
        if (highlightField != null) {
            String highLightMessage = highlightField.fragments()[0].toString();
//            String capitalize = StringUtils.capitalize(field);
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

//        @Override
//    public PageUtils searchTest(SearchRequest request) {
//        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
//            String key = request.getKey();
//            Integer size = request.getPageSize();
//            Integer page = request.getCurrPage();
//            //???????????????[??????????????????????????????????????????]
//            SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                    .withQuery(multiMatchQuery(key, "uName", "uIntro", "uNick", "uCompanyName")
//                            .operator(Operator.OR) /*.minimumShouldMatch("30%")*/)
//                    .withPageable(PageRequest.of(page, size))
//                    .build();
//            AggregatedPage<EsUserEntity> esUserEntities =
//                    elasticsearchTemplate.queryForPage(searchQuery, EsUserEntity.class);
//            if (null != esUserEntities && !esUserEntities.getContent().isEmpty()) {
//                List<EsUserEntity> list = esUserEntities.getContent();
//                long totalCount = esUserEntities.getTotalElements();
//                //????????? ????????? ?????????
//                for (EsUserEntity entity : list) {
//                    LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
//                    if (null != entity && null != entity.getUId()) {
//                        queryWrapper.eq(InArticle::getuId, entity.getUId());
//                        int articleNumber = articleService.count(queryWrapper);
//                        entity.setArticleNumber(articleNumber);
//                        List<InArticle> articles = articleService.list(queryWrapper);
//                        long readNumber = articles.stream().mapToLong(InArticle::getaReadNumber).sum();
//                        long likeNumber = articles.stream().mapToLong(InArticle::getaLike).sum();
//                        entity.setReadNumber(readNumber);
//                        entity.setLikeNUmber(likeNumber);
//                    }
//                }
//                //???????????? ???????????? ??????????????? ????????????
//                return new PageUtils(list, totalCount, size, page);
//            }
//            return null;
//        }
//        return null;
//    }
}
