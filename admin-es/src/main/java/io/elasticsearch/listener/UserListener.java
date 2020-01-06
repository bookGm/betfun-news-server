package io.elasticsearch.listener;


import com.rabbitmq.client.Channel;
import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.service.EsUserService;
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
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class UserListener {
    @Autowired
    private EsUserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(EsUserService.class);

    /**
     * 用户注册
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.user_Save_Queue),
            exchange = @Exchange(name = Constants.userExchange),
            key = Constants.user_Save_RouteKey
    ))
    public void created(EsUserEntity esUser, Channel channel, @Headers Map<String, Object> headers) {
        userService.saveUser(esUser);
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
     * 用户删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.user_Delete_Queue),
            exchange = @Exchange(name = Constants.userExchange),
            key = Constants.user_Delete_RouteKey
    ))
    public void remove(String uIds, Channel channel, @Headers Map<String, Object> headers) {
        if (null != uIds) {
            userService.removeUser(uIds.split(","));
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
     * 用户更新
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.user_Update_Queue),
            exchange = @Exchange(name = Constants.userExchange),
            key = Constants.user_Update_RouteKey
    ))
    public void update(EsUserEntity esUser, Channel channel, @Headers Map<String, Object> headers) {
        userService.updatedUser(esUser);
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
