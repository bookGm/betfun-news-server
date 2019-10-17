package io.elasticsearch.service.impl;

import io.elasticsearch.dao.EsCardDao;
import io.elasticsearch.dao.EsUserDao;
import io.elasticsearch.entity.EsCardEntity;
import io.elasticsearch.entity.EsUserEntity;
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
    private EsUserDao userDao;
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
    //TODO  未完全实现
    //只需展示基本信息[标题、时间、用户头像、用户昵称等]
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
        return pageOK(queryBuilder,request,null);
    }

    @Override
    public PageUtils statusSearch(SearchRequest request) {
        Map<String, String> map = request.getParams();
        String cCategory = map.get("cCategory");
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termQuery("cCategory", cCategory));
        //判断帖子类型
        if (Integer.valueOf(cCategory) == 2) {
            //投票帖子
            return pageResult(queryBuilder, request,null);
        } else {
            //普通帖子和辩论帖子
            return pageOK(queryBuilder, request,null);
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
        EsUserEntity user = userDao.findByuId(Long.parseLong(uId));
        //判断帖子类型
        if (Integer.valueOf(cCategory) == 2) {
            //投票帖子
            return pageResult(queryBuilder,request,user);
        } else {
            //普通帖子和辩论帖子
            return pageOK(queryBuilder,request,user);
        }
    }

    @Override
    public EsCardEntity infoSearch(SearchRequest request) {
        Map<String, String> map = request.getParams();
        String cId = map.get("cId");
        //根据帖子ID查询帖子
        EsCardEntity cardEntity = cardDao.findBycId(Long.parseLong(cId));
        Long uId = cardEntity.getuId();
        //查询用户信息
        EsUserEntity user = userDao.findByuId(uId);
        if (cardEntity.getcCategory() == 2) {
            if (!cardEntity.getCvInfo().isEmpty()) {
                String[] split = cardEntity.getCvInfo().split(",");
                cardEntity.setCvInfoList(split);
                cardEntity.setUserEs(user);
            }
            return cardEntity;
        } else {
            cardEntity.setUserEs(user);
            return cardEntity;
        }
    }


    //普通、辩论帖子数据填充
    private PageUtils pageOK(NativeSearchQueryBuilder queryBuilder, SearchRequest request,EsUserEntity user) {
        Integer pageSize = request.getPageSize();
        Integer currPage = request.getCurrPage();
        queryBuilder.withPageable(PageRequest.of(currPage, pageSize));
        AggregatedPage<EsCardEntity> aggregatedPage = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsCardEntity.class);
        List<EsCardEntity> cardEntities = aggregatedPage.getContent();
        long totalCount = aggregatedPage.getTotalElements();
        if(user!=null){
            cardEntities.stream().forEach(cardEntity -> {
                cardEntity.setUserEs(user);
            });
        }
        return new PageUtils(cardEntities, totalCount, pageSize, currPage);
    }

    //投票帖子数据填充
    private PageUtils pageResult(NativeSearchQueryBuilder queryBuilder, SearchRequest request,EsUserEntity user) {
        Integer pageSize = request.getPageSize();
        Integer currPage = request.getCurrPage();
        queryBuilder.withPageable(PageRequest.of(currPage, pageSize));
        AggregatedPage<EsCardEntity> aggregatedPage = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsCardEntity.class);
        List<EsCardEntity> entityList = aggregatedPage.getContent();
        for (EsCardEntity esCardEntity : entityList) {
            if (!esCardEntity.getCvInfo().isEmpty()) {
                String[] split = esCardEntity.getCvInfo().split(",");
                esCardEntity.setCvInfoList(split);
            }
        }
        long totalCount = aggregatedPage.getTotalElements();
        if(user!=null){
            entityList.stream().forEach(cardEntity -> {
                cardEntity.setUserEs(user);
            });
        }
        return new PageUtils(entityList, totalCount, pageSize, currPage);
    }
}
