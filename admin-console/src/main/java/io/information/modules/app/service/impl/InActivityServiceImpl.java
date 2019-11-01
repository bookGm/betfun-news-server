package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InActivityDatas;
import io.information.modules.app.entity.InActivityFields;
import io.information.modules.app.entity.InActivityTicket;
import io.information.modules.app.service.IInActivityDatasService;
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
    @Autowired
    private IInActivityDatasService datasService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveActivity(InActivity activity) {
        if (null != activity.getFieldsList() && !activity.getFieldsList().isEmpty()) {
            List<InActivityFields> fieldsList = activity.getFieldsList();
            for (InActivityFields fields : fieldsList) {
                fields.setActId(activity.getActId());
                fieldsService.save(fields);
            }
        }
        if (null != activity.getTicketList() && !activity.getTicketList().isEmpty()) {
            List<InActivityTicket> ticketList = activity.getTicketList();
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
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<InActivity> queryWrapper = new LambdaQueryWrapper<>();
        if (params.containsKey("type") && StringUtil.isNotBlank(params.get("type"))) {
            switch (Integer.parseInt(String.valueOf(params.get("type")))) {
                case 0: //最新发布
                    queryWrapper.orderByDesc(InActivity::getActCreateTime);
                    break;
                case 1: //全部
                    queryWrapper.orderByDesc(InActivity::getActStartTime);
                    break;
                case 2: //峰会
                    break;
                case 3: //线上活动
                    break;
                case 4: //其他
                    queryWrapper.orderByAsc(InActivity::getActCloseTime);
                    break;
            }
        }
        if (params.containsKey("uId") && StringUtil.isNotBlank(params.get("uId"))) {
            queryWrapper.eq(InActivity::getuId, params.get("uId"));
        }
        if (params.containsKey("actStatus") && StringUtil.isNotBlank(params.get("actStatus"))) {
            queryWrapper.eq(InActivity::getActStatus, params.get("actStatus"));
        }
        IPage<InActivity> page = this.page(
                new Query<InActivity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeActivity(List<Long> actIds) {
        List<InActivityFields> fieldsList = field(actIds);
        List<InActivityTicket> ticketList = ticket(actIds);
        List<InActivityDatas> datasList = datas(actIds);
        if (null != fieldsList && !fieldsList.isEmpty()) {
            List<Long> fIds = fieldsList.stream().map(InActivityFields::getfId).collect(Collectors.toList());
            fieldsService.removeByIds(fIds);
        }
        if (null != ticketList && !ticketList.isEmpty()) {
            List<Long> tIds = ticketList.stream().map(InActivityTicket::gettId).collect(Collectors.toList());
            ticketService.removeByIds(tIds);
        }
        if (null != datasList && !datasList.isEmpty()) {
            List<Long> dIds = datasList.stream().map(InActivityDatas::getdId).collect(Collectors.toList());
            datasService.removeByIds(dIds);
        }
        this.removeByIds(actIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateActivity(InActivity activity) {
        if (null != activity.getFieldsList() && !activity.getFieldsList().isEmpty()) {
            List<InActivityFields> fieldsList = activity.getFieldsList();
            fieldsService.updateBatchById(fieldsList);
        }
        if (null != activity.getTicketList() && !activity.getTicketList().isEmpty()) {
            List<InActivityTicket> ticketList = activity.getTicketList();
            ticketService.updateBatchById(ticketList);
        }
        this.updateById(activity);
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

    private List<InActivityDatas> datas(List<Long> ids) {
        LambdaQueryWrapper<InActivityDatas> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InActivityDatas::getActId, ids);
        List<InActivityDatas> list = datasService.list(queryWrapper);
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }

}
