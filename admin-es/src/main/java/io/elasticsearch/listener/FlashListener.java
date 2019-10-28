package io.elasticsearch.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.entity.EsFlashEntity;
import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.service.EsFlashService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void created(JSONObject obj){
        flashService.saveFlash(JSON.toJavaObject(obj,EsFlashEntity.class));
    }


    /**
     * 快讯删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.flash_Delete_Queue),
            exchange = @Exchange(name = Constants.flashExchange),
            key = Constants.flash_Delete_RouteKey
    ))
    public void remove(Long[] nIds){
        flashService.removeFlash(nIds);
    }


    /**
     * 快讯更新
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.flash_Update_Queue),
            exchange = @Exchange(name = Constants.flashExchange),
            key = Constants.flash_Update_RouteKey
    ))
    public void update(JSONObject obj){
        flashService.updatedFlash(JSON.toJavaObject(obj,EsFlashEntity.class));
    }
}
