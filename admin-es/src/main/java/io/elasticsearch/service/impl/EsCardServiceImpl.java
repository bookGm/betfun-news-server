package io.elasticsearch.service.impl;

import io.elasticsearch.dao.EsCardDao;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.entity.EsCardEntity;
import io.elasticsearch.service.EsCardService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EsCardServiceImpl implements EsCardService {
    @Autowired
    private EsCardDao cardDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void saveCard(EsCardEntity cardEntity) {
        cardDao.save(cardEntity);
    }

    @Override
    public void removeCard(EsCardEntity cardEntity) {
        cardDao.deleteById(cardEntity.getcId());
    }

    @Override
    public void updatedCard(EsCardEntity cardEntity) {
        cardDao.deleteById(cardEntity.getcId());
        cardDao.save(cardEntity);
    }

    /**
     * 帖子搜索关键词
     */
    public PageUtils cardSearch(SearchRequest request) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        String key = request.getKey();
        queryBuilder.withQuery(QueryBuilders
                .multiMatchQuery(key, "cContent", "caFside", "caRside")
                .operator(Operator.AND)
                .minimumShouldMatch("75%"));
        Map<String, String> params = request.getParams();
        if (params != null || params.size() != 0) {
            for (String filter : params.keySet()) {
                queryBuilder.withQuery(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.termQuery(filter, params.get(filter))));
            }
        }
        Integer pageSize = request.getPageSize();
        Integer currPage = request.getCurrPage();
        queryBuilder.withPageable(PageRequest.of(currPage, pageSize));
        AggregatedPage<EsArticleEntity> aggregatedPage = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsArticleEntity.class);
        List<EsArticleEntity> articleList = aggregatedPage.getContent();
        long totalCount = aggregatedPage.getTotalElements();
        return new PageUtils(articleList, totalCount, pageSize, currPage);
    }
}
