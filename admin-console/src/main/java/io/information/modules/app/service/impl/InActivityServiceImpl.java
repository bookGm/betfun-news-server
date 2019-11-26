package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.StringUtil;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.*;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.dao.InActivityDatasDao;
import io.information.modules.app.dao.InActivityFieldsDao;
import io.information.modules.app.dao.InActivityTicketDao;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InActivityDatas;
import io.information.modules.app.entity.InActivityFields;
import io.information.modules.app.entity.InActivityTicket;
import io.information.modules.app.service.IInActivityDatasService;
import io.information.modules.app.service.IInActivityFieldsService;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.app.service.IInActivityTicketService;
import io.information.modules.sys.service.SysCitysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
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
    IInActivityTicketService ticketService;
    @Autowired
    IInActivityFieldsService fieldsService;
    @Autowired
    IInActivityDatasService datasService;
    @Autowired
    InActivityFieldsDao fieldsDao;
    @Autowired
    InActivityTicketDao ticketDao;
    @Autowired
    InActivityDatasDao datasDao;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    SysCitysService sysCitysService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveActivity(InActivity activity) {
        if (null != activity.getFieldsList() && !activity.getFieldsList().isEmpty()) {
            List<InActivityFields> fieldsList = activity.getFieldsList();
            for (InActivityFields fields : fieldsList) {
                fields.setfId(IdGenerator.getId());
                fields.setActId(activity.getActId());
                fieldsService.save(fields);
            }
        }
        if (null != activity.getTicketList() && !activity.getTicketList().isEmpty()) {
            List<InActivityTicket> ticketList = activity.getTicketList();
            for (InActivityTicket ticket : ticketList) {
                ticket.settId(IdGenerator.getId());
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
        if (null != params.get("type") && StringUtil.isNotBlank(params.get("type"))) {
            int type = Integer.parseInt(String.valueOf(params.get("type")));
            switch (Integer.parseInt(String.valueOf(params.get("type")))) {
                case -1: //最新发布
                    queryWrapper.orderByDesc(InActivity::getActCreateTime);
                    break;
                case 0: //全部
                    queryWrapper.orderByDesc(InActivity::getActStartTime);
                    break;
                default: //峰会 线上活动 其他
                    queryWrapper.eq(InActivity::getActCategory, type);
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
        String actTimeType = "";
        Long currDate = new Date().getTime();
        for (InActivity activity : page.getRecords()) {
            Long startTime = activity.getActStartTime().getTime();
            Long closeTime = activity.getActCloseTime().getTime();
            if (startTime > currDate) {
                actTimeType = "未开始";
            } else if (startTime <= currDate) {  //活动开始
                if (closeTime < currDate) {
                    actTimeType = "已结束";
                } else if ((closeTime > currDate)) {
                    actTimeType = "进行中";
                }
            }
            activity.setActTimeType(actTimeType);
            if (null != activity.getActAddr()) {
                String[] split = activity.getActAddr().split("-");
                StringBuilder actAddName = new StringBuilder();
                for (String s : split) {
                    long id = Long.parseLong(s);
                    String name = sysCitysService.getById(id).getName();
                    actAddName.append(name).append("-");
                }
                activity.setActAddrName(actAddName.toString());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public InActivity details(Long actId) {
        InActivity activity = this.getById(actId);
        List<InActivityFields> fields = fieldsDao.searchByActId(actId);
        List<InActivityTicket> tickets = ticketDao.searchByActId(actId);
        List<InActivityDatas> datas = datasDao.searchByActId(actId);
        if (null != fields && !fields.isEmpty()) {
            activity.setFieldsList(fields);
        }
        if (null != tickets && !tickets.isEmpty()) {
            activity.setTicketList(tickets);
        }
        if (null != datas && !datas.isEmpty()) {
            activity.setDatasList(datas);
        }
        if (null != activity.getActAddr()) {
            String[] split = activity.getActAddr().split("-");
            StringBuilder actAddName = new StringBuilder();
            for (String s : split) {
                long id = Long.parseLong(s);
                String name = sysCitysService.getById(id).getName();
                actAddName.append(name).append("-");
            }
            activity.setActAddrName(actAddName.toString());
        }
        Long startTime = activity.getActStartTime().getTime();
        Long closeTime = activity.getActCloseTime().getTime();
        String actTimeType = "";
        Long currDate = new Date().getTime();
        if (startTime > currDate) {
            actTimeType = "未开始";
        } else {
            if (closeTime < currDate) {
                actTimeType = "已结束";
            } else if ((closeTime > currDate)) {
                actTimeType = "进行中";
            }
        }
        activity.setActTimeType(actTimeType);
        return activity;
    }

    @Override
    @HashCacheable(key = RedisKeys.APPLY, keyField = "#actId-#uId")
    public String signUp(Long actId, Long uId) {
        this.baseMapper.allActId(actId);
        return DateUtils.format(new Date());
    }

    @Override
    public boolean isApply(Long uId, Long actId) {
        return redisUtils.hashHasKey(RedisKeys.APPLY, actId + "-" + uId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeActivity(List<Long> actIds) {
        List<InActivityFields> fieldsList = field(actIds);
        List<InActivityTicket> ticketList = ticket(actIds);
        List<InActivityDatas> datasList = datas(actIds);
        if (null != fieldsList && !fieldsList.isEmpty()) {
            List<Long> fIds = fieldsList.stream().map(InActivityFields::getfId).collect(Collectors.toList());
            deleteField(fIds);
        }
        if (null != ticketList && !ticketList.isEmpty()) {
            List<Long> tIds = ticketList.stream().map(InActivityTicket::gettId).collect(Collectors.toList());
            deleteTicket(tIds);
        }
        if (null != datasList && !datasList.isEmpty()) {
            List<Long> dIds = datasList.stream().map(InActivityDatas::getdId).collect(Collectors.toList());
            deleteDatas(dIds);
        }
        this.removeByIds(actIds);
    }

    @Async
    void deleteField(List<Long> Ids) {
        fieldsService.removeByIds(Ids);
    }

    @Async
    void deleteTicket(List<Long> Ids) {
        ticketService.removeByIds(Ids);
    }

    @Async
    void deleteDatas(List<Long> Ids) {
        datasService.removeByIds(Ids);
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


    @Override
    public List<InActivity> interested() {
        List<InActivity> activityList = this.baseMapper.interested();
        String actTimeType = "";
        Long currDate = new Date().getTime();
        for (InActivity activity : activityList) {
            Long startTime = activity.getActStartTime().getTime();
            Long closeTime = activity.getActCloseTime().getTime();
            if (startTime > currDate) {
                actTimeType = "未开始";
            } else if (startTime <= currDate) {  //活动开始
                if (closeTime < currDate) {
                    actTimeType = "已结束";
                } else if ((closeTime > currDate)) {
                    actTimeType = "进行中";
                }
            }
            activity.setActTimeType(actTimeType);
            if (null != activity.getActAddr()) {
                String[] split = activity.getActAddr().split("-");
                StringBuilder actAddName = new StringBuilder();
                for (String s : split) {
                    long id = Long.parseLong(s);
                    String name = sysCitysService.getById(id).getName();
                    actAddName.append(name).append("-");
                }
                activity.setActAddrName(actAddName.toString());
            }
        }
        return activityList;
    }
}
