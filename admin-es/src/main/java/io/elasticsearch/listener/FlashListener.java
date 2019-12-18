package io.elasticsearch.listener;

import com.rabbitmq.client.Channel;
import io.elasticsearch.entity.EsFlashEntity;
import io.elasticsearch.service.EsFlashService;
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
public class FlashListener {
    @Autowired
    private EsFlashService flashService;

    private static final Logger LOG = LoggerFactory.getLogger(EsArticleServiceImpl.class);

    /**
     * 快讯发布
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.flash_Save_Queue),
            exchange = @Exchange(name = Constants.flashExchange),
            key = Constants.flash_Save_RouteKey
    ))
    public void created(EsFlashEntity esFlash, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        flashService.saveFlash(esFlash);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }


    /**
     * 快讯删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.flash_Delete_Queue),
            exchange = @Exchange(name = Constants.flashExchange),
            key = Constants.flash_Delete_RouteKey
    ))
    public void remove(String nIds, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        if (null != nIds) {
            flashService.removeFlash(nIds.split(","));
            RabbitMQUtils.askMessage(channel, tag, LOG);
        }
    }


    /**
     * 快讯更新
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.flash_Update_Queue),
            exchange = @Exchange(name = Constants.flashExchange),
            key = Constants.flash_Update_RouteKey
    ))
    public void update(EsFlashEntity esFlash, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        flashService.updatedFlash(esFlash);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }
}
