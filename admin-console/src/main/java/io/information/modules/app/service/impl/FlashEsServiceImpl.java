package io.information.modules.app.service.impl;

import com.guansuo.common.StringUtil;
import io.elasticsearch.entity.EsFlashEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.modules.app.service.FlashEsService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
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

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class FlashEsServiceImpl implements FlashEsService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public PageUtils searchFlash(SearchRequest request) {
        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
            String key = request.getKey();
            Integer pageSize = request.getPageSize();
            Integer currPage = request.getCurrPage();

            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
            //设置索引...
            //设置高亮
            withHighlight(searchQuery);
            //设置查询条件
            searchQuery.withQuery(multiMatchQuery(key, "nTitle", "nBrief", "nContent")
                    .operator(Operator.OR) /*.minimumShouldMatch("30%")*/)
                    .withPageable(PageRequest.of(currPage, pageSize));

            //自定义查询结果封装
            AggregatedPage<EsFlashEntity> esFlashEntities = elasticsearchTemplate.queryForPage(searchQuery.build(), EsFlashEntity.class,
                    new SearchResultMapper() {

                        @Override
                        public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
                                                                Pageable pageable) {
                            // TODO Auto-generated method stub
                            List<EsFlashEntity> chunk = new ArrayList<>();
                            SearchHits hits = response.getHits();

                            for (SearchHit hit : hits) {
                                if (hits.getHits().length <= 0) {
                                    return null;
                                }
                                Map<String, Object> smap = hit.getSourceAsMap();
                                Map<String, HighlightField> hmap = hit.getHighlightFields();
                                EsFlashEntity esEntity = createEsEntity(smap, hmap);

                                //高亮内容
                                setHighLight(hit, "nTitle", esEntity);
                                setHighLight(hit, "nBrief", esEntity);
                                setHighLight(hit, "nContent", esEntity);

                                chunk.add(esEntity);
                            }
                            return new AggregatedPageImpl<>((List<T>) chunk);
                        }
                    });

            List<EsFlashEntity> entityList = esFlashEntities.getContent();
            long total = esFlashEntities.getTotalElements();
            return new PageUtils(entityList, total, pageSize, currPage);
        }
        return null;
    }

    /**
     * 添加高亮条件
     */
    private void withHighlight(NativeSearchQueryBuilder searchQuery) {
        HighlightBuilder.Field hfield = new HighlightBuilder.Field("nTitle")
                .preTags("<em style='color:#349dff'>")
                .postTags("</em>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield2 = new HighlightBuilder.Field("nBrief")
                .preTags("<em style='color:#349dff'>")
                .postTags("</em>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield3 = new HighlightBuilder.Field("nContent")
                .preTags("<em style='color:#349dff'>")
                .postTags("</em>")
                .fragmentSize(100);
        searchQuery.withHighlightFields(hfield, hfield2, hfield3);
    }


    /**
     * 根据搜索结果创建ES对象
     */
    private EsFlashEntity createEsEntity(Map<String, Object> smap, Map<String, HighlightField> hmap) {
        EsFlashEntity ed = new EsFlashEntity();
        if (smap.get("nId") != null)
            ed.setNId(Long.parseLong(String.valueOf(smap.get("nId").toString())));
        if (smap.get("nBull") != null)
            ed.setNBull(Integer.parseInt(String.valueOf(smap.get("nBull").toString())));
        if (smap.get("nBad") != null)
            ed.setNBad(Integer.getInteger(String.valueOf(smap.get("nBad").toString())));
        if (smap.get("nCreateTime") != null) {
            String timeString = String.valueOf(smap.get("nCreateTime").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(timeString);
                ed.setNCreateTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ed;
    }


    /**
     * 反射调用属性的set方法设置高亮的内容   注意BUG
     */
    public void setHighLight(SearchHit searchHit, String field, Object object) {
        Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
        HighlightField highlightField = highlightFieldMap.get(field);
        if (highlightField != null) {
            String highLightMessage = highlightField.fragments()[0].toString();
            String capitalize = StringUtils.capitalize(field);
            String methodName = "set" + capitalize;//setUName
            Class<?> clazz = object.getClass();
            try {
                Method setMethod = clazz.getMethod(methodName, String.class);
                setMethod.invoke(object, highLightMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


//    @Override
//    public PageUtils searchFlash(SearchRequest request) {
//        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
//            String key = request.getKey();
//            Integer size = request.getPageSize();
//            Integer page = request.getCurrPage();
//            //多字段匹配[标题，摘要，内容]
//            SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                    .withQuery(multiMatchQuery(key, "nTitle", "nBrief", "nContent")
//                                    .operator(Operator.OR)
//                            /*.minimumShouldMatch("30%")*/)
//                    .withIndices("flashs")
//                    .withPageable(PageRequest.of(page, size))
//                    .build();
//            AggregatedPage<EsFlashEntity> esFlashEntities =
//                    elasticsearchTemplate.queryForPage(searchQuery, EsFlashEntity.class);
//            if (null != esFlashEntities && !esFlashEntities.getContent().isEmpty()) {
//                List<EsFlashEntity> list = esFlashEntities.getContent();
//                long totalCount = esFlashEntities.getTotalElements();
//                //列表数据 总记录数 每页记录数 当前页数
//                return new PageUtils(list, totalCount, size, page);
//            }
//            return null;
//        }
//        return null;
//    }
}
