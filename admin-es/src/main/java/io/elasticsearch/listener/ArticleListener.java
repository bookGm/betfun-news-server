package io.elasticsearch.listener;

import com.rabbitmq.client.Channel;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.service.EsArticleService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ArticleListener {
    @Autowired
    private EsArticleService articleService;

    /**
     * 文章发布
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.article_Save_Queue),
            exchange = @Exchange(name = Constants.articleExchange),
            key = Constants.article_Save_RouteKey
    ))
    public void created(EsArticleEntity esArticle, Channel channel, @Headers Map<String, Object> headers) {
        articleService.saveArticle(esArticle);
        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            System.err.println("rabbitMQ手动应答失败" + e.getMessage());
        }
    }


    /**
     * 文章删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.article_Delete_Queue),
            exchange = @Exchange(name = Constants.articleExchange),
            key = Constants.article_Delete_RouteKey
    ))
    public void remove(String aIds, Channel channel, @Headers Map<String, Object> headers) {
        if (null != aIds) {
            articleService.removeArticle(aIds.split(","));
            // 手动ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            // 手动签收
            try {
                channel.basicAck(deliveryTag, false);
            } catch (IOException e) {
                System.err.println("rabbitMQ手动应答失败" + e.getMessage());
            }
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
    public void update(EsArticleEntity esArticle, Channel channel, @Headers Map<String, Object> headers) {
        articleService.updatedArticle(esArticle);
        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            System.err.println("rabbitMQ手动应答失败" + e.getMessage());
        }
    }
}
