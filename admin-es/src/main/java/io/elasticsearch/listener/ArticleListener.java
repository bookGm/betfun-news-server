package io.elasticsearch.listener;

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
            value = @Queue(name = Constants.queue),
            exchange = @Exchange(name = Constants.defaultExchange),
            key = Constants.routeKey
    ))
    public void created(EsArticleEntity articleEntity){
        articleService.saveArticle(articleEntity);
    }


    /**
     * 文章删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.queue),
            exchange = @Exchange(name = Constants.defaultExchange),
            key = Constants.routeKey
    ))
    public void remove(EsArticleEntity articleEntity){
        articleService.removeArticle(articleEntity);
    }


    /**
     * 文章更新
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.queue),
            exchange = @Exchange(name = Constants.defaultExchange),
            key = Constants.routeKey
    ))
    public void update(EsArticleEntity articleEntity){
        articleService.updatedArticle(articleEntity);
    }
}
