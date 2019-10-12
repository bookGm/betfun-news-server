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

@Service
public class EsArticleServiceImpl implements EsArticleService {
//    @Autowired
//    private EsArticleDao articleDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    private static final Logger LOG = LoggerFactory.getLogger(EsArticleServiceImpl.class);
    @RabbitListener(queues = Constants.queue)
    public void receive(String payload, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        LOG.info("消费内容为：{}", payload);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }


    /**
     * ES添加文章
     */
    public void saveArticle(EsArticleEntity articleEntity) {
//        articleDao.save(articleEntity);
    }


    /**
     * ES删除文章
     */
    public void removeArticle(EsArticleEntity articleEntity) {
//        articleDao.delete(articleEntity);
    }


    /**
     * 文章搜索关键词
     */
    public PageUtils articleSearchKey(SearchRequest request) {
        // 创建原生搜索器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //搜索的关键词
        String key = request.getKey();
        //多字段匹配[关键字，标题，内容，摘要]
        queryBuilder.withQuery(QueryBuilders
                .multiMatchQuery(key,"aKeyword","aTitle","aContent","aBrief")
                .operator(Operator.AND)
                .minimumShouldMatch("75%"));
        //过滤项[可无]
        Map<String, String> params = request.getParams();
        if(params != null || params.size() != 0){
            for (String filter : params.keySet()) {
                //根据某个字段和其值  完全匹配值
                queryBuilder.withQuery(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.termQuery(filter,params.get(filter))));
            }
        }
        // 分页
        Integer currPage = request.getCurrPage();
        Integer pageSize = request.getPageSize();
        queryBuilder.withPageable(PageRequest.of(currPage,pageSize));
        // 发送搜索器，并获取返回值
        AggregatedPage<EsArticleEntity> aggregatedPage = elasticsearchTemplate.queryForPage(queryBuilder.build(), EsArticleEntity.class);
        // 解析返回值
        List<EsArticleEntity> articleList = aggregatedPage.getContent();
        long totalCount = aggregatedPage.getTotalElements();
//        int totalPages = aggregatedPage.getTotalPages();
        // 构造PageUtils，并返回
        return new PageUtils(articleList,totalCount,pageSize,currPage);
    }




}
