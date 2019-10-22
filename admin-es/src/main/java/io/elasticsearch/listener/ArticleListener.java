package io.elasticsearch.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guansuo.common.JsonUtil;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.service.EsArticleService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void created(JSONObject obj){
        articleService.saveArticle(JSON.toJavaObject(obj,EsArticleEntity.class));
    }


    /**
     * 文章删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.article_Delete_Queue),
            exchange = @Exchange(name = Constants.articleExchange),
            key = Constants.article_Delete_RouteKey
    ))
    public void remove(Long[] aIds){
        articleService.removeArticle(aIds);
    }


    /**
     * 文章更新
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.article_Update_Queue),
            exchange = @Exchange(name = Constants.defaultExchange),
            key = Constants.article_Update_RouteKey
    ))
    public void update(JSONObject obj){
        articleService.updatedArticle(JSON.toJavaObject(obj,EsArticleEntity.class));
    }
}
