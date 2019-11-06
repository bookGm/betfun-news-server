package io.elasticsearch.service.impl;

import com.guansuo.common.StringUtil;
import com.rabbitmq.client.Channel;
import io.elasticsearch.dao.EsArticleDao;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.mq.utils.Constants;
import io.mq.utils.RabbitMQUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
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

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

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
    public PageUtils searchInfo(SearchRequest request) {
        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
            String key = request.getKey();
            Integer size = request.getPageSize();
            Integer page = request.getCurrPage();
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            //多字段匹配[关键字，标题，内容，摘要]
            queryBuilder.withQuery(
                    multiMatchQuery(key, "aKeyword", "aTitle", "aContent", "aBrief")
                            .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)); //匹配度评分优先
            //分页
            queryBuilder.withPageable(PageRequest.of(page, size));
            AggregatedPage<EsArticleEntity> esArticleEntities =
                    elasticsearchTemplate.queryForPage(queryBuilder.build(), EsArticleEntity.class);
            if (null != esArticleEntities && !esArticleEntities.getContent().isEmpty()) {
                List<EsArticleEntity> list = esArticleEntities.getContent();
                long totalCount = esArticleEntities.getTotalElements();
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


}
