package io.elasticsearch.service.impl;

import com.rabbitmq.client.Channel;
import io.elasticsearch.dao.EsArticleDao;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.mq.utils.Constants;
import io.mq.utils.RabbitMQUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
public class EsArticleServiceImpl implements EsArticleService {
    @Autowired
    private EsArticleDao articleDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    private static final Logger LOG = LoggerFactory.getLogger(EsArticleServiceImpl.class);

    @RabbitListener(queues = Constants.queue)
    public void receive(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        LOG.info("消费内容为：{}", payload);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }

    @Override
    public void saveArticle(EsArticleEntity articleEntity) {
        articleDao.save(articleEntity);
    }

    @Override
    public void removeArticle(Long[] aIds) {
        for (Long aId : aIds) {
            articleDao.deleteById(aId);
        }
    }

    @Override
    public void updatedArticle(EsArticleEntity articleEntity) {
        articleDao.deleteById(articleEntity.getaId());
        articleDao.save(articleEntity);
    }

    @Override
    public PageUtils articleSearchKey(SearchRequest request) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //搜索的关键词
        String key = request.getKey();
        //多字段匹配[关键字，标题，内容，摘要]
        queryBuilder.withQuery(QueryBuilders
                .multiMatchQuery(key, "aKeyword", "aTitle", "aContent", "aBrief")
                .operator(Operator.AND)
                .minimumShouldMatch("75%"));
        //过滤项[可无]
        Map<String, String> params = request.getParams();
        if (params != null || params.size() != 0) {
            for (String filter : params.keySet()) {
                //根据某个字段和其值  完全匹配值
                queryBuilder.withQuery(QueryBuilders.boolQuery()
                        .filter(termQuery(filter, params.get(filter))));
            }
        }
        return pageOk(queryBuilder, request);
    }

    @Override
    public PageUtils articleSearch(SearchRequest request) {
        //查询所有已发布的文章
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(termQuery("aStatus", 2));
        return pageOk(queryBuilder, request);
    }

    @Override
    public PageUtils articleSearchStatus(SearchRequest request) {
        Map<String, String> map = request.getParams();
        String uId = map.get("uId");
        String aStatus = map.get("aStatus");
        //根据状态查询用户文章
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.boolQuery()
                .must(termQuery("uId", uId))
                .must(termQuery("aStatus", aStatus))
        );
        return pageOk(queryBuilder,request);
    }

    //数据填充
    private PageUtils pageOk(NativeSearchQueryBuilder queryBuilder, SearchRequest request) {
        //设置分页数据
        Integer currPage = request.getCurrPage();
        Integer pageSize = request.getPageSize();
        queryBuilder.withPageable(PageRequest.of(currPage, pageSize));
        //查询数据
        AggregatedPage<EsArticleEntity> aggregatedPage = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsArticleEntity.class);
        List<EsArticleEntity> articleList = aggregatedPage.getContent();
        long totalCount = aggregatedPage.getTotalElements();
        return new PageUtils(articleList, totalCount, pageSize, currPage);
    }

}
