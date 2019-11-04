package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.dao.InNodeDao;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InNode;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInNodeService;
import io.information.modules.app.service.IInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class InNodeServiceImpl extends ServiceImpl<InNodeDao, InNode> implements IInNodeService {
    @Autowired
    private IInUserService userService;
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private IInCardBaseService baseService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InNode> page = this.page(
                new Query<InNode>().getPage(params),
                new QueryWrapper<InNode>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<Long, String> search(Long noType) {
        LambdaQueryWrapper<InNode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InNode::getNoType, noType);
        List<InNode> nodeList = this.list(queryWrapper);
        if (null != nodeList && !nodeList.isEmpty()) {
            HashMap<Long, String> map = new HashMap<>();
            for (InNode node : nodeList) {
                map.put(node.getNoId(), node.getNoName());
            }
            return map;
        }
        return null;
    }

    @Override
    public PageUtils query(Map<String, Object> map) {
        LambdaQueryWrapper<InNode> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("noType") && StringUtil.isNotBlank(map.get("noType"))) {
            Integer noType = Integer.valueOf(String.valueOf(map.get("noType")));
            queryWrapper.eq(InNode::getNoType, noType);
        }
        IPage<InNode> page = this.page(
                new Query<InNode>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }


    @Override
    public Map<String, Object> special(Map<String, Object> map) {
        //寻找通过任何认证的用户
        LambdaQueryWrapper<InUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InUser::getuAuthStatus, 2);
        IPage<InUser> page = userService.page(
                new Query<InUser>().getPage(map),
                queryWrapper
        );
        List<InUser> users = page.getRecords();
        //拼装 文章 浏览量 获赞数
        Map<String, Object> rm = new HashMap<>();
        for (InUser user : users) {
            List<InArticle> list = articleService.list(new LambdaQueryWrapper<InArticle>().eq(InArticle::getuId, user.getuId()));
            //累计文章数
            rm.put("aCount", list.size());
            //累计阅读量
            LongSummaryStatistics readNumber = list.stream().collect(Collectors.summarizingLong((n) -> n.getaReadNumber() == null ? 0L : n.getaReadNumber()));
            rm.put("rCount", readNumber.getSum());
            //累计粉丝数
            rm.put("fCount", user.getuFans());
            //用户信息
            rm.put("user", user);
            //分页数据
            rm.put("pageSize", page.getSize());    //显示条数
            rm.put("currPage", page.getCurrent());    //当前页数
            rm.put("totalCount", page.getTotal());   //总条数
            rm.put("totalPage", page.getPages());  //总页数
        }
        return rm;
    }

    @Override
    public PageUtils cardList(Map<String, Object> map) {
        LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("noId") && StringUtil.isNotBlank(map.get("noId"))) {
            Long noId = Long.valueOf(String.valueOf(map.get("noId")));
            queryWrapper.eq(InCardBase::getNoId, noId);

            if (null != map.get("cCategory") && StringUtil.isNotBlank(map.get("cCategory"))) {
                Integer type = Integer.valueOf(String.valueOf(map.get("cCategory")));
                switch (type) {
                    case 0: //投票
                        queryWrapper.eq(InCardBase::getcCategory, 2);
                        break;
                    case 1: //辩论
                        queryWrapper.eq(InCardBase::getcCategory, 1);
                        break;
                }
            }
            if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
                Integer type = Integer.valueOf(String.valueOf(map.get("type")));
                switch (type) {
                    case 0: //最新
                        queryWrapper.orderByDesc(InCardBase::getcCreateTime);
                        break;
                    case 1: //最热
                        queryWrapper.orderByDesc(InCardBase::getcLike);
                        break;
                }
            }
            IPage<InCardBase> page = baseService.page(
                    new Query<InCardBase>().getPage(map),
                    queryWrapper
            );
            return new PageUtils(page);
        }
        return null;
    }


    @Override
    public PageUtils star(Map<String, Object> map) {
        LambdaQueryWrapper<InUser> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
            Integer type = Integer.valueOf(String.valueOf(map.get("type")));
            switch (type) {
                case 0: //最新
                    queryWrapper.orderByDesc(InUser::getuFans);
                    break;
                case 1: //最热
                    queryWrapper.orderByDesc(InUser::getuCreateTime).orderByDesc(InUser::getuFans);
                    break;
            }
        }
        IPage<InUser> page = userService.page(
                new Query<InUser>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }


    @Override
    @HashCacheable(key = RedisKeys.NODES, keyField = "#uId-#type-#noId")
    public Long focus(Long uId, Long noId, Long type) {
        this.baseMapper.increaseFocus(noId);
        return noId;
    }
}