package io.elasticsearch.service.impl;

import com.rabbitmq.client.Channel;
import io.elasticsearch.dao.EsArticleDao;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.service.EsArticleService;
import io.mq.utils.Constants;
import io.mq.utils.RabbitMQUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class EsArticleServiceImpl implements EsArticleService {
    @Autowired
    private EsArticleDao articleDao;

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

}
