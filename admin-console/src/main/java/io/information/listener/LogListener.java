package io.information.listener;

import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import com.rabbitmq.client.Channel;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InLog;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardService;
import io.information.modules.app.service.IInLogService;
import io.information.modules.app.service.IInUserService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class LogListener {
    @Autowired
    private IInLogService iInLogService;
    @Autowired
    private IInUserService iInUserService;
    @Autowired
    private IInCardService iInCardService;

    /**
     * 文章发布
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.log_Save_Queue),
            exchange = @Exchange(name = Constants.logExchange),
            key = Constants.log_Save_RouteKey
    ))
    public void created(String l, Channel channel, @Headers Map<String, Object> headers) {
        InLog log = JsonUtil.parseObject(l, InLog.class);
        InUser u = iInUserService.getById(log.getlOperateId());
        if (StringUtil.isBlank(log.getlTargetName())) {
            if (1 == log.getlTargetType()) {
                InCardBase c = iInCardService.getById(log.getlTargetId());
                log.setlTargetName(c.getcTitle());
            }
            if (0 == log.getlTargetType()) {
                InUser tu = iInUserService.getById(log.getlTargetId());
                log.setlTargetName(tu.getuNick());
            }
        }
        log.setlOperateName(u.getuNick());
        iInLogService.save(log);
        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            System.err.println("rabbitMQ手动应答失败" + e.getMessage());
        }
    }


//    /**
//     * 系统消息
//     */
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = Constants.system_Save_Queue),
//            exchange = @Exchange(name = Constants.systemExchange),
//            key = Constants.system_Save_RouteKey
//    ))
//    public void system(String m) {
//        InMessage message = JsonUtil.parseObject(m, InMessage.class);
//        //接收到新消息&&内容
//        if(message.gettId() == -1){
//            //内部消息  推送全部
//
//        }else {
//            //用户消息  推送单个
//
//        }
//    }

}
