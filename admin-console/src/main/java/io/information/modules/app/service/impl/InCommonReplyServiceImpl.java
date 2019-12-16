package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InCommonReplyDao;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCommonReplyService;
import io.information.modules.app.service.IInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InCommonReplyServiceImpl extends ServiceImpl<InCommonReplyDao, InCommonReply> implements IInCommonReplyService {
    @Autowired
    IInUserService iInUserService;

    @Override
    public PageUtils discuss(Map<String, Object> map) {
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("tId") && StringUtil.isNotBlank(map.get("tId"))) {
            Long tId = Long.parseLong(String.valueOf(map.get("tId")));
            queryWrapper.eq(InCommonReply::gettId, tId);
            if (null != map.get("tType") && StringUtil.isNotBlank(map.get("tType"))) {
                //0文章，1帖子，2活动，3用户
                int tType = Integer.parseInt(String.valueOf(map.get("tType")));
                queryWrapper.eq(InCommonReply::gettType, tType);
            }
            queryWrapper.orderByDesc(InCommonReply::getCrTime);
            IPage<InCommonReply> page = this.page(
                    new Query<InCommonReply>().getPage(map),
                    queryWrapper
            );
            for (InCommonReply r : page.getRecords()) {
                r.setCrCount(this.count(new LambdaQueryWrapper<InCommonReply>().eq(InCommonReply::getToCrId, r.getCrId())));
                InUser u = iInUserService.getById(r.getcId());
                if (null != u) {
                    r.setcName(u.getuName());
                    r.setcPhoto(u.getuPhoto());
                } else {
                    r.setcName("用户已不存在");
                }
            }
            return new PageUtils(page);
        }
        return null;
    }

    @Override
    public PageUtils revert(Map<String, Object> map) {
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("id") && StringUtil.isNotBlank(map.get("id"))) {
            Long id = Long.parseLong(String.valueOf(map.get("id")));
            queryWrapper.eq(InCommonReply::getCrTId, id);
            queryWrapper.orderByDesc(InCommonReply::getCrTime);
            IPage<InCommonReply> page = this.page(
                    new Query<InCommonReply>().getPage(map),
                    queryWrapper
            );
            for (InCommonReply r : page.getRecords()) {
                InUser u = iInUserService.getById(r.getcId());
                r.setcName(u.getuNick());
                r.setcPhoto(u.getuPhoto());
                r.setCrSimpleTime(DateUtils.getSimpleTime(r.getCrTime()));
            }
            return new PageUtils(page);
        }
        return null;
    }

    @Override
    public PageUtils<InCommonReply> userMsg(Map<String, Object> params) {
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("uId") && StringUtil.isNotBlank(params.get("uId"))) {
            Long uId = Long.parseLong(String.valueOf(params.get("uId")));
            //根据用户ID查询回复ID或被评论ID为用户的评论或回复
            queryWrapper.eq(InCommonReply::gettId, uId).or().eq(InCommonReply::getToCrId, uId);
            IPage<InCommonReply> page = this.page(
                    new Query<InCommonReply>().getPage(params),
                    queryWrapper
            );
            for (InCommonReply r : page.getRecords()) {
                InUser u = iInUserService.getById(r.getcId());
                if (null != u) {
                    r.setcName(u.getuNick());
                    r.setcPhoto(u.getuPhoto());
                    r.setCrSimpleTime(DateUtils.getSimpleTime(r.getCrTime()));
                    LambdaQueryWrapper<InCommonReply> query = new LambdaQueryWrapper<>();
                    //根据根ID查询回复数量
                    query.eq(InCommonReply::getCrTId, r.getCrId());
                    int count = this.count(query);
                    r.setCrCount(count);
                }
            }
            return new PageUtils<InCommonReply>(page);
        }
        return null;
    }

    @Override
    public PageUtils<InCommonReply> reply(Map<String, Object> map, List<Long> cIds) {
        IPage<InCommonReply> page = this.page(
                new Query<InCommonReply>().getPage(map),
                new LambdaQueryWrapper<InCommonReply>().in(InCommonReply::gettId, cIds)
        );
        for (InCommonReply record : page.getRecords()) {
            InUser user = iInUserService.getById(record.getcId());
            record.setcName(user.getuName());
            record.setcPhoto(user.getuPhoto());
            record.setCrSimpleTime(DateUtils.getSimpleTime(record.getCrTime()));
        }
        return new PageUtils<>(page);
    }


    private List<InCommonReply> comList = null;

    public List<InCommonReply> recursion(InCommonReply commonReply) {
        if (null != commonReply.getToCrId() && !String.valueOf(commonReply.getToCrId()).isEmpty()) {
            LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InCommonReply::getCrId, commonReply.getToCrId());
            InCommonReply one = this.getOne(queryWrapper);
            comList.add(one);
            recursion(one);
        }
        return comList;
    }
}
