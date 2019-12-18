package io.elasticsearch.listener;


import com.rabbitmq.client.Channel;
import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.service.EsUserService;
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
    public void created(EsUserEntity esUser, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        userService.saveUser(esUser);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }


    /**
     * 用户删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.user_Delete_Queue),
            exchange = @Exchange(name = Constants.userExchange),
            key = Constants.user_Delete_RouteKey
    ))
    public void remove(String uIds, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        if (null != uIds) {
            userService.removeUser(uIds.split(","));
            RabbitMQUtils.askMessage(channel, tag, LOG);
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
    public void update(EsUserEntity esUser, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        userService.updatedUser(esUser);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }
}
