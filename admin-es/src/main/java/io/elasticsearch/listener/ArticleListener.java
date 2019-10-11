package io.elasticsearch.listener;

import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.service.EsCardService;
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
    public void created(Long aId){
        //把es 的索引创建出来
        articleService.saveArticle(aId);
    }

    /**
     * 文章删除
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.queue),
            exchange = @Exchange(name = Constants.defaultExchange),
            key = Constants.routeKey
    ))
    public void remove(Long aId){
        //删除es的文档
        articleService.removeArticle(aId);
    }
}
