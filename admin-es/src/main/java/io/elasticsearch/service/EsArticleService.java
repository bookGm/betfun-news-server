package io.elasticsearch.service;

import io.elasticsearch.dao.EsArticleDao;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.mq.utils.Constants;
import io.mq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EsArticleService {
    private static final Logger LOG = LoggerFactory.getLogger(EsArticleService.class);
    @RabbitListener(queues = Constants.queue)
    public void receive(String payload, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        LOG.info("消费内容为：{}", payload);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }

    @Autowired
    private EsArticleDao articleDao;
    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * ES添加文章
     */
    public void saveArticle(EsArticleEntity articleEntity) {
        articleDao.save(articleEntity);
    }


    /**
     * ES删除文章
     */
    public void removeArticle(EsArticleEntity articleEntity) {
        articleDao.delete(articleEntity);
    }


    /**
     * ES搜索关键词 返回分页
     */
    public PageUtils searchPage(SearchRequest request) {
        // 创建原生搜索器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 使用 match进行搜索
        queryBuilder.withQuery(basicQuery(request));
        // 分页
        Integer currPage = request.getCurrPage()-1;
        Integer pageSize = request.getPageSize();
        queryBuilder.withPageable(PageRequest.of(currPage,pageSize));
        // 发送搜索器，并获取返回值
        AggregatedPage<EsArticleEntity> aggregatedPage = esTemplate.queryForPage(queryBuilder.build(), EsArticleEntity.class);
        // 解析返回值
        List<EsArticleEntity> articleList = aggregatedPage.getContent();
        int totalPages = aggregatedPage.getTotalPages();
        long totalCount = aggregatedPage.getTotalElements();
        // 构造PageUtils，并返回
        return new PageUtils(articleList,totalCount,pageSize,currPage);
    }


    /**
     * ES过滤搜索
     */
    public Map<String, List<?>> queryFilter(SearchRequest request) {
        //返回的 过滤条件结果
        Map<String,List<?>> filterMap = new LinkedHashMap<>();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //不需要任何 返回的列值
        queryBuilder.withSourceFilter(new FetchSourceFilterBuilder().build());
        queryBuilder.withQuery(basicQuery(request));
        queryBuilder.withPageable(PageRequest.of(0,1));

        //添加聚合条件
        String  agg = "条件";
        queryBuilder.addAggregation(AggregationBuilders.terms(agg).field("字段"));

        AggregatedPage<EsArticleEntity> aggregatedPage = esTemplate.queryForPage(queryBuilder.build(), EsArticleEntity.class);
        Aggregations aggregations = aggregatedPage.getAggregations();
        StringTerms terms = aggregations.get(request.getKey()/*返回的字段*/);
        List<String> results = terms.getBuckets().stream()
                .map(StringTerms.Bucket::getKeyAsString)
                .collect(Collectors.toList());
        filterMap.put(request.getKey()/*返回的字段*/,results);

        return filterMap;
    }


    /**
     * 基本搜索+ 过滤条件
     */
    private QueryBuilder basicQuery(SearchRequest request){
        //搜索的关键词
        String key = request.getKey();
        Map<String, String> params = request.getParams();
        //声名一个布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //必须包含的内容
        boolQueryBuilder.must(QueryBuilders.matchQuery("aTitle",key).operator(Operator.AND));
        for (String esKey : params.keySet()) {
            // TODO 预留查询操作
            String esValue = params.get(esKey);
            boolQueryBuilder.filter(QueryBuilders.termQuery(esKey,esValue));
        }
        return boolQueryBuilder;
    }

}
