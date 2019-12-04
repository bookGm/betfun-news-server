package io.information.modules.news.controller;

import com.guansuo.common.JsonUtil;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.MessageEntity;
import io.information.modules.news.service.MessageService;
import io.information.netty.socket.TextWebSocketFrameHandler;
import io.information.netty.util.MyChannelHandlerPool;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-25 10:56:47
 */
@RestController
@RequestMapping("news/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:message:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = messageService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{mId}")
    @RequiresPermissions("news:message:info")
    public R info(@PathVariable("mId") Long mId) {
        MessageEntity message = messageService.getById(mId);
        return R.ok().put("message", message);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:message:save")
    public R save(@RequestBody MessageEntity message) {
        message.setmId(IdGenerator.getId());
        message.setmCreateTime(new Date());
        messageService.save(message);
        if (message.gettId() == -1) {
            for (Channel c : MyChannelHandlerPool.channelGroup) {
                c.writeAndFlush(new TextWebSocketFrame(JsonUtil.toJSONString(message)));
            }
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:message:update")
    public R update(@RequestBody MessageEntity message) {
        messageService.updateById(message);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:message:delete")
    public R delete(@RequestBody Long[] mIds) {
        messageService.removeByIds(Arrays.asList(mIds));

        return R.ok();
    }

}
