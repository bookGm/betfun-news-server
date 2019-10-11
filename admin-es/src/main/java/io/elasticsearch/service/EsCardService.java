package io.elasticsearch.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsCardService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

}
