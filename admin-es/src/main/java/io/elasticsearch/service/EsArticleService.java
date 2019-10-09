package io.elasticsearch.service;

import io.mq.utils.Constants;
import io.mq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class EsArticleService {
    private static final Logger LOG = LoggerFactory.getLogger(EsArticleService.class);
    @RabbitListener(queues = Constants.queue)
    public void receive(String payload, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        LOG.info("消费内容为：{}", payload);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }
}
