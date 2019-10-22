package io.elasticsearch.listener;

//import io.elasticsearch.entity.EsUserEntity;
//import io.elasticsearch.service.EsUserService;
//import io.mq.utils.Constants;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserListener {
//    @Autowired
//    private EsUserService userService;


//    /**
//     * 用户注册
//     */
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = Constants.user_Save_Queue),
//            exchange = @Exchange(name = Constants.userExchange),
//            key = Constants.user_Save_RouteKey
//    ))
//    public void created(EsUserEntity userEntity) {
//        userService.saveUser(userEntity);
//    }


//    /**
//     * 用户删除
//     */
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = Constants.user_Delete_Queue),
//            exchange = @Exchange(name = Constants.userExchange),
//            key = Constants.user_Delete_RouteKey
//    ))
//    public void remove(Long[] uIds) {
//        userService.removeUser(uIds);
//    }


//    /**
//     * 用户更新
//     */
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = Constants.user_Update_Queue),
//            exchange = @Exchange(name = Constants.userExchange),
//            key = Constants.user_Update_RouteKey
//    ))
//    public void update(EsUserEntity userEntity) {
//        userService.updatedUser(userEntity);
//    }
}
