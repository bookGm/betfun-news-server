package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InActivityFields;
import io.information.modules.app.entity.InActivityTicket;
import io.information.modules.app.service.IInActivityFieldsService;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.app.service.IInActivityTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯活动表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InActivityServiceImpl extends ServiceImpl<InActivityDao, InActivity> implements IInActivityService {
    @Autowired
    private IInActivityTicketService ticketService;
    @Autowired
    private IInActivityFieldsService fieldsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InActivity> page = this.page(
                new Query<InActivity>().getPage(params),
                new QueryWrapper<InActivity>()
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeActivity(List<Long> actIds) {
        List<InActivityFields> fieldsList = field(actIds);
        List<InActivityTicket> ticketList = ticket(actIds);
        if (null != fieldsList && !fieldsList.isEmpty()) {
            List<Long> fIds = fieldsList.stream().map(InActivityFields::getFId).collect(Collectors.toList());
            fieldsService.removeByIds(fIds);
        }
        if (null != ticketList && !ticketList.isEmpty()) {
            List<Long> tIds = ticketList.stream().map(InActivityTicket::gettId).collect(Collectors.toList());
            ticketService.removeByIds(tIds);
        }
        this.removeByIds(actIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateActivity(InActivity activity) {
        List<InActivityTicket> ticketList = activity.getTicketList();
        List<InActivityFields> fieldsList = activity.getFieldsList();
        if (null != fieldsList && !fieldsList.isEmpty()) {
            fieldsService.updateBatchById(fieldsList);
        }
        if (null != ticketList && !ticketList.isEmpty()) {
            ticketService.updateBatchById(ticketList);
        }
        this.updateById(activity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveActivity(InActivity activity) {
        List<InActivityTicket> ticketList = activity.getTicketList();
        List<InActivityFields> fieldsList = activity.getFieldsList();
        if (null != fieldsList && !fieldsList.isEmpty()) {
            for (InActivityFields fields : fieldsList) {
                fields.setActId(activity.getActId());
                fieldsService.save(fields);
            }
        }
        if (null != ticketList && !ticketList.isEmpty()) {
            for (InActivityTicket ticket : ticketList) {
                ticket.setActId(activity.getActId());
                ticketService.save(ticket);
            }
        }
        this.save(activity);
    }

    @Override
    public List<InActivityFields> apply(Long actId) {
        return field(Collections.singletonList(actId));
    }

    @Override
    public PageUtils queryActByUId(Map<String, Object> params, Long userId) {
        QueryWrapper<InActivity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InActivity::getuId, userId);
        IPage<InActivity> page = this.page(
                new Query<InActivity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }


    //根据活动ID查询关联数据
    private List<InActivityFields> field(List<Long> ids) {
        LambdaQueryWrapper<InActivityFields> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InActivityFields::getActId, ids);
        List<InActivityFields> list = fieldsService.list(queryWrapper);
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }

    private List<InActivityTicket> ticket(List<Long> ids) {
        LambdaQueryWrapper<InActivityTicket> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InActivityTicket::getActId, ids);
        List<InActivityTicket> list = ticketService.list(queryWrapper);
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }


}
