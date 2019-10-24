package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.entity.InActivityTicket;
import io.information.modules.app.service.IInActivityTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 活动票
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 13:32:16
 */
@RestController
@RequestMapping("app/activity/ticket")
public class InActivityTicketController {
    @Autowired
    private IInActivityTicketService ticketService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = ticketService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tId}")
    public R info(@PathVariable("tId") Long tId) {
        InActivityTicket ticket = ticketService.getById(tId);

        return R.ok().put("actTicket", ticket);
    }

    /**
     * 保存
     */
    @Login
    @RequestMapping("/save")
    public R save(@RequestBody InActivityTicket ticket) {
        ticketService.save(ticket);

        return R.ok();
    }

    /**
     * 修改
     */
    @Login
    @RequestMapping("/update")
    public R update(@RequestBody InActivityTicket ticket) {
        ticketService.updateById(ticket);

        return R.ok();
    }

    /**
     * 删除
     */
    @Login
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] tIds) {
        ticketService.removeByIds(Arrays.asList(tIds));

        return R.ok();
    }

}
