package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InActivityTicketDao;
import io.information.modules.app.entity.InActivityTicket;
import io.information.modules.app.service.IInActivityTicketService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class InActivityTicketServiceImpl extends ServiceImpl<InActivityTicketDao, InActivityTicket> implements IInActivityTicketService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InActivityTicket> page = this.page(
                new Query<InActivityTicket>().getPage(params),
                new QueryWrapper<InActivityTicket>()
        );

        return new PageUtils(page);
    }

}