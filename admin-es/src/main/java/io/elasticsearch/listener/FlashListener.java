package io.elasticsearch.listener;

import com.rabbitmq.client.Channel;
import io.elasticsearch.entity.EsFlashEntity;
import io.elasticsearch.service.EsFlashService;
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
public class FlashListener {
    @Autowired
    private EsFlashService flashService;


    /**
     * 快讯发布
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.flash_Save_Queue),
            exchange = @Exchange(name = Constants.flashExchange),
            key = Constants.flash_Save_RouteKey
    ))
    public void created(EsFlashEntity esFlash, Channel channel, @Headers Map<String, Object> headers) {
        flashService.saveFlash(esFlash);
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
     * 快讯删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.flash_Delete_Queue),
            exchange = @Exchange(name = Constants.flashExchange),
            key = Constants.flash_Delete_RouteKey
    ))
    public void remove(String nIds, Channel channel, @Headers Map<String, Object> headers) {
        if (null != nIds) {
            flashService.removeFlash(nIds.split(","));
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
     * 快讯更新
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.flash_Update_Queue),
            exchange = @Exchange(name = Constants.flashExchange),
            key = Constants.flash_Update_RouteKey
    ))
    public void update(EsFlashEntity esFlash, Channel channel, @Headers Map<String, Object> headers) {
        flashService.updatedFlash(esFlash);
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
