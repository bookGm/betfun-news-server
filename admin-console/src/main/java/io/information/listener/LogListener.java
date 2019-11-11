package io.information.listener;

import io.information.common.utils.IdGenerator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void created(InLog log) {
        InUser u=iInUserService.getById(log.getlOperateId());
        if(1==log.getlTargetType()){
            InCardBase c=iInCardService.getById(log.getlTargetId());
            log.setlTargetName(c.getcTitle());
        }
        if(0==log.getlTargetType()){
            InUser tu=iInUserService.getById(log.getlTargetId());
            log.setlTargetName(tu.getuNick());
        }
        log.setlOperateName(u.getuNick());

        iInLogService.save(log);
    }

}