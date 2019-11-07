package io.elasticsearch.service.impl;

import io.elasticsearch.dao.EsFlashDao;
import io.elasticsearch.entity.EsFlashEntity;
import io.elasticsearch.service.EsFlashService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class EsFlashServiceImpl implements EsFlashService {
    @Autowired
    private EsFlashDao flashDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public void saveFlash(EsFlashEntity flash) {
        flashDao.save(flash);
    }

    @Override
    public void removeFlash(Long[] nIds) {
        for (Long nId : nIds) {
            flashDao.deleteById(nId);
        }
    }

    @Override
    public void updatedFlash(EsFlashEntity flash) {
        flashDao.deleteById(flash.getnId());
        flashDao.save(flash);
    }

    @Override
    public PageUtils searchFlash(SearchRequest request) {
        if (null != request.getKey() && !request.getKey().isEmpty()) {
            String key = request.getKey();
            Integer size = request.getPageSize();
            Integer page = request.getCurrPage();
            //多字段匹配[标题，摘要，内容]
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(multiMatchQuery(key, "nTitle", "nBrief", "nContent")
                            .operator(Operator.OR)/*.minimumShouldMatch("30%")*/)
                    .withPageable(PageRequest.of(page, size))
                    .build();
            AggregatedPage<EsFlashEntity> esFlashEntities =
                    elasticsearchTemplate.queryForPage(searchQuery, EsFlashEntity.class);
            if (null != esFlashEntities && !esFlashEntities.getContent().isEmpty()) {
                List<EsFlashEntity> list = esFlashEntities.getContent();
                long totalCount = esFlashEntities.getTotalElements();
                //列表数据 总记录数 每页记录数 当前页数
                return new PageUtils(list, totalCount, size, page);
            }
            return null;
        }
        return null;
    }

    /**
     * 拼接在某属性的 set方法
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }


    //高亮显示
//    Page<EsFlashEntity> search = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsFlashEntity.class, new SearchResultMapper() {
//        @Override
//        public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
//            List<EsFlashEntity> flashs = new ArrayList<>();
//            SearchHits hits = response.getHits();
//            for (SearchHit searchHit : hits) {
//                if (hits.getHits().length <= 0) {
//                    return null;
//                }
//                EsFlashEntity flash = new EsFlashEntity();
//                String msg = searchHit.getHighlightFields().get(key).fragments()[0].toString();
//                flash.setnTitle(String.valueOf(searchHit.getFields().get("nTitle")));
//                flash.setnBrief(String.valueOf(searchHit.getFields().get("nBrief")));
//                flash.setnContent(String.valueOf(searchHit.getFields().get("nContent")));
//                try {
//                    String name = parSetName(key);
//                    Class<? extends EsFlashEntity> flashClass = flash.getClass();
//                    Method method = flashClass.getMethod(name, String.class);
//                    method.invoke(flash, msg);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                flashs.add(flash);
//            }
//            if (flashs.size() > 0) {
//                return (AggregatedPage<T>) new PageImpl<T>((List<T>) flashs);
//
//            }
//            return null;
//        }
//    });
}
