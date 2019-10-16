package io.elasticsearch.listener;

import io.elasticsearch.entity.EsCardEntity;
import io.elasticsearch.service.EsCardService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardListener {
    @Autowired
    private EsCardService cardService;


    /**
     * 帖子发布
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.card_Save_Queue),
            exchange = @Exchange(name = Constants.cardExchange),
            key = Constants.card_Save_RouteKey
    ))
    public void created(EsCardEntity cardEntity){
        cardService.saveCard(cardEntity);
    }


    /**
     * 帖子删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.card_Delete_Queue),
            exchange = @Exchange(name = Constants.cardExchange),
            key = Constants.card_Delete_RouteKey
    ))
    public void remove(Long[] cIds){
        cardService.removeCard(cIds);
    }


    /**
     * 帖子更新
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.card_Update_Queue),
            exchange = @Exchange(name = Constants.cardExchange),
            key = Constants.card_Update_RouteKey
    ))
    public void update(EsCardEntity cardEntity){
        cardService.updatedCard(cardEntity);
    }
}
