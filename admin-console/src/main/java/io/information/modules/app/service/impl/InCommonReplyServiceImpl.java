package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.dao.InCommonReplyDao;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInCommonReplyService;
import io.information.modules.app.service.IInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InCommonReplyServiceImpl extends ServiceImpl<InCommonReplyDao, InCommonReply> implements IInCommonReplyService {
    @Autowired
    IInUserService iInUserService;
    @Autowired
    IInCardBaseService cardBaseService;
    @Autowired
    InCardBaseDao cardBaseDao;
    @Autowired
    InArticleDao articleDao;
    @Autowired
    InActivityDao activityDao;
    @Autowired
    IInUserService userService;

    @Override
    public PageUtils discuss(Map<String, Object> map) {
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        Long tId = Long.parseLong(String.valueOf(map.get("tId")));
        queryWrapper.eq(InCommonReply::gettId, tId);
        //0文章，1帖子，2活动，3用户
        int tType = Integer.parseInt(String.valueOf(map.get("tType")));
        queryWrapper.eq(InCommonReply::gettType, tType);
        queryWrapper.orderByDesc(InCommonReply::getCrTime);
        IPage<InCommonReply> page = this.page(
                new Query<InCommonReply>().getPage(map),
                queryWrapper
        );
        for (InCommonReply r : page.getRecords()) {
            r.setCrCount(this.count(new LambdaQueryWrapper<InCommonReply>().eq(InCommonReply::getToCrId, r.getCrId())));
            InUser u = iInUserService.getById(r.getcId());
            if (null != u) {
                r.setcName(u.getuName() == null || u.getuName().equals("")
                        ? u.getuNick() : u.getuName());
                r.setcPhoto(u.getuPhoto() == null || u.getuPhoto().equals("")
                        ? "http://guansuo.info/news/upload/20191231115456head.png" : u.getuPhoto());
            } else {
                r.setcName("用户已不存在");
                r.setcPhoto("http://guansuo.info/news/upload/20191231115456head.png");
            }
        }
        return new PageUtils(page);
    }


    @Override
    public PageUtils discussUser(Map<String, Object> map) {
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        Long tId = Long.parseLong(String.valueOf(map.get("tId")));
        queryWrapper.eq(InCommonReply::getcId, uId).eq(InCommonReply::gettId, tId);
        //0文章，1帖子，2活动，3用户
        int tType = Integer.parseInt(String.valueOf(map.get("tType")));
        queryWrapper.eq(InCommonReply::gettType, tType);
        queryWrapper.orderByDesc(InCommonReply::getCrTime);
        IPage<InCommonReply> page = this.page(
                new Query<InCommonReply>().getPage(map),
                queryWrapper
        );
        for (InCommonReply r : page.getRecords()) {
            r.setCrCount(this.count(new LambdaQueryWrapper<InCommonReply>().eq(InCommonReply::getToCrId, r.getCrId())));
            InUser u = iInUserService.getById(uId);
            r.setcName(u.getuName() == null || u.getuName().equals("")
                    ? u.getuNick() : u.getuName());
            r.setcPhoto(u.getuPhoto() == null || u.getuPhoto().equals("")
                    ? "http://guansuo.info/news/upload/20191231115456head.png" : u.getuPhoto());
        }
        return new PageUtils(page);
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
                r.setcName(u.getuName() == null ? u.getuNick() : u.getuName());
                r.setcPhoto(u.getuPhoto());
                r.setCrSimpleTime(DateUtils.getSimpleTime(r.getCrTime()));
            }
            return new PageUtils(page);
        }
        return null;
    }

    @Override
    public PageUtils<InCommonReply> userMsg(Map<String, Object> params) {
        //查询评论用户的数据
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("uId") && StringUtil.isNotBlank(params.get("uId"))) {
            Long uId = Long.parseLong(String.valueOf(params.get("uId")));
            //查询用户的所有可评论的发布
            ArrayList<Long> list = new ArrayList<>();
            List<Long> cIds = cardBaseDao.allCId(uId);
            List<Long> actIds = activityDao.allActId(uId);
            List<Long> aIds = articleDao.allAId(uId);
            list.addAll(cIds);
            list.addAll(actIds);
            list.addAll(aIds);
            //查询被评论ID为用户ID的评论
            if (!list.isEmpty()) {
                queryWrapper.eq(InCommonReply::gettId, uId).or().in(InCommonReply::gettId, list);
            } else {
                queryWrapper.eq(InCommonReply::gettId, uId);
            }
            queryWrapper.orderByDesc(InCommonReply::getCrTime);
            IPage<InCommonReply> page = this.page(
                    new Query<InCommonReply>().getPage(params),
                    queryWrapper
            );
            for (InCommonReply record : page.getRecords()) {
                InUser user = userService.getById(record.getcId());
                if (null != user) {
                    record.setcName(user.getuName() == null || user.getuName().equals("")
                            ? user.getuNick() : user.getuName());
                    record.setcPhoto(user.getuPhoto() == null || user.getuPhoto().equals("")
                            ? "http://guansuo.info/news/upload/20191231115456head.png" : user.getuPhoto());
                } else {
                    record.setcName("用户已不存在");
                    record.setcPhoto("http://guansuo.info/news/upload/20191231115456head.png");
                }
                record.setCrSimpleTime(DateUtils.getSimpleTime(record.getCrTime()));
                LambdaQueryWrapper<InCommonReply> query = new LambdaQueryWrapper<>();
                //根据根ID查询回复数量
                query.eq(InCommonReply::getCrTId, record.getCrId());
                int count = this.count(query);
                record.setCrCount(count);
                String typeNmae = "";
                switch (record.gettType()) {
                    //0文章，1帖子，2活动，3用户
                    case 0:
                        typeNmae = "文章";
                        break;
                    case 1:
                        typeNmae = "帖子";
                        break;
                    case 2:
                        typeNmae = "活动";
                        break;
                    case 3:
                        typeNmae = "用户";
                        break;
                }
                record.settTypeName(typeNmae);
            }
            return new PageUtils<>(page);
        }
        return null;
    }

    @Override
    public PageUtils<InCommonReply> reply(Map<String, Object> map, List<Long> cIds) {
        IPage<InCommonReply> page = this.page(
                new Query<InCommonReply>().getPage(map),
                new LambdaQueryWrapper<InCommonReply>()
                        .in(InCommonReply::gettId, cIds)
                        .orderByDesc(InCommonReply::getCrTime)
        );
        for (InCommonReply record : page.getRecords()) {
            InUser user = iInUserService.getById(record.getcId());
            if (user != null) {
                record.setcName(user.getuName() == null ? user.getuNick() : user.getuName());
                record.setcPhoto(user.getuPhoto() == null || user.getuPhoto().equals("")
                        ? "http://guansuo.info/news/upload/20191231115456head.png" : user.getuPhoto());
            } else {
                record.setcName("用户已不存在");
                record.setcPhoto("http://guansuo.info/news/upload/20191231115456head.png");
            }
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
