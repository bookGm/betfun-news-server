package io.elasticsearch.service.impl;

import com.guansuo.common.StringUtil;
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


    @Override
    public void saveFlash(EsFlashEntity flash) {
        flashDao.save(flash);
    }

    @Override
    public void removeFlash(String[] nIds) {
        for (String nId : nIds) {
            flashDao.deleteById(Long.parseLong(nId));
        }
    }

    @Override
    public void updatedFlash(EsFlashEntity flash) {
        flashDao.deleteById(flash.getnId());
        flashDao.save(flash);
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
