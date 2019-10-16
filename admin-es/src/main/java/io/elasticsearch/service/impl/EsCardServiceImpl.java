package io.elasticsearch.service.impl;

import io.elasticsearch.dao.EsCardDao;
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

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

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
    public void removeCard(Long[] cIds) {
        for (Long cId : cIds) {
            cardDao.deleteById(cId);
        }
    }

    @Override
    public void updatedCard(EsCardEntity cardEntity) {
        cardDao.deleteById(cardEntity.getcId());
        cardDao.save(cardEntity);
    }

    @Override
    //TODO  投票帖子的转换未实现
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
        return pageOK(queryBuilder, request);
    }

    @Override
    public PageUtils statusSearch(SearchRequest request) {
        Map<String, String> map = request.getParams();
        String cCategory = map.get("cCategory");
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termQuery("cCategory", cCategory));
        //判断是否为投票帖
        if (Integer.valueOf(cCategory) == 2) {
            Integer pageSize = request.getPageSize();
            Integer currPage = request.getCurrPage();
            queryBuilder.withPageable(PageRequest.of(currPage, pageSize));
            AggregatedPage<EsCardEntity> aggregatedPage = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsCardEntity.class);
            long totalCount = aggregatedPage.getTotalElements();
            List<EsCardEntity> entityList = aggregatedPage.getContent();
            for (EsCardEntity esCardEntity : entityList) {
                String cvInfo = esCardEntity.getCvInfo();
                String[] split = cvInfo.split(",");
                esCardEntity.setCvInfoList(split);
            }
            return new PageUtils(entityList, totalCount, pageSize, currPage);
        } else {
            //普通帖子和辩论帖子
            return pageOK(queryBuilder, request);
        }
    }

    @Override
    public PageUtils userSearch(SearchRequest request) {
        Map<String, String> map = request.getParams();
        String cCategory = map.get("cCategory");
        String uId = map.get("uId");
        //根据状态查询用户帖子
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.boolQuery()
                .must(termQuery("uId", uId))
                .must(termQuery("cCategory", cCategory))
        );
        //判断是否为投票帖
        if (Integer.valueOf(cCategory) == 2) {
            Integer pageSize = request.getPageSize();
            Integer currPage = request.getCurrPage();
            queryBuilder.withPageable(PageRequest.of(currPage, pageSize));
            AggregatedPage<EsCardEntity> aggregatedPage = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsCardEntity.class);
            List<EsCardEntity> entityList = aggregatedPage.getContent();
            for (EsCardEntity esCardEntity : entityList) {
                String cvInfo = esCardEntity.getCvInfo();
                String[] split = cvInfo.split(",");
                esCardEntity.setCvInfoList(split);
            }
            long totalCount = aggregatedPage.getTotalElements();
            return new PageUtils(entityList, totalCount, pageSize, currPage);
        } else {
            //普通帖子和辩论帖子
            return pageOK(queryBuilder, request);
        }
    }


    //数据填充
    private PageUtils pageOK(NativeSearchQueryBuilder queryBuilder, SearchRequest request) {
        Integer pageSize = request.getPageSize();
        Integer currPage = request.getCurrPage();
        queryBuilder.withPageable(PageRequest.of(currPage, pageSize));
        AggregatedPage<EsCardEntity> aggregatedPage = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsCardEntity.class);
        List<EsCardEntity> articleList = aggregatedPage.getContent();
        long totalCount = aggregatedPage.getTotalElements();
        return new PageUtils(articleList, totalCount, pageSize, currPage);
    }
}
