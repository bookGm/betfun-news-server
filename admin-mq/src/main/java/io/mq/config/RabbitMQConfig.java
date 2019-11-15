package io.mq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mq.utils.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.AbstractJackson2MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    @Bean
    public Queue queue() {
        boolean durable = true;
        boolean exclusive = false;
        boolean autoDelete = false;
        return new Queue(Constants.queue, durable, exclusive, autoDelete);
    }

    @Bean
    public DirectExchange defaultExchange() {
        boolean durable = true;
        boolean autoDelete = false;
        return new DirectExchange(Constants.defaultExchange, durable, autoDelete);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(defaultExchange())
                .with(Constants.routeKey);
    }
    /**
     * {@link org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration}
     *  会自动识别
     * @param objectMapper json序列化实现类
     * @return mq 消息序列化工具
     */
    @Bean
    public AbstractJackson2MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
