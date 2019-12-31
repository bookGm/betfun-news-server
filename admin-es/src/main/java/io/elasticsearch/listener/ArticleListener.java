package io.elasticsearch.listener;

import com.rabbitmq.client.Channel;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.service.impl.EsArticleServiceImpl;
import io.mq.utils.Constants;
import io.mq.utils.RabbitMQUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class ArticleListener {
    @Autowired
    private EsArticleService articleService;

    private static final Logger LOG = LoggerFactory.getLogger(EsArticleServiceImpl.class);

    /**
     * 文章发布
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.article_Save_Queue),
            exchange = @Exchange(name = Constants.articleExchange),
            key = Constants.article_Save_RouteKey
    ))
    public void created(EsArticleEntity esArticle, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        articleService.saveArticle(esArticle);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }


    /**
     * 文章删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.article_Delete_Queue),
            exchange = @Exchange(name = Constants.articleExchange),
            key = Constants.article_Delete_RouteKey
    ))
    public void remove(String aIds, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        if (null != aIds) {
            articleService.removeArticle(aIds.split(","));
            RabbitMQUtils.askMessage(channel, tag, LOG);
        }
    }


    /**
     * 文章更新
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.article_Update_Queue),
            exchange = @Exchange(name = Constants.articleExchange),
            key = Constants.article_Update_RouteKey
    ))
    public void update(EsArticleEntity esArticle, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        articleService.updatedArticle(esArticle);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }
}
