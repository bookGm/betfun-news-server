package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.IInCardBaseService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯文章表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InArticleServiceImpl extends ServiceImpl<InArticleDao, InArticle> implements IInArticleService {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    InActivityDao inActivityDao;
    @Autowired
    InCardBaseDao inCardBaseDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<InArticle> qw = new LambdaQueryWrapper<InArticle>();
        if (params.containsKey("type") && StringUtil.isNotBlank(params.get("type"))) {
            switch (Integer.parseInt(String.valueOf(params.get("type")))) {
                case 0:
                    qw.orderByDesc(InArticle::getaCreateTime);
                    break;
                case 1:
                    qw.orderByDesc(InArticle::getaReadNumber);
                    break;
                case 2:
                    qw.orderByDesc(InArticle::getaCreateTime, InArticle::getaReadNumber);
                    break;
            }
        }
        if (params.containsKey("uId") && StringUtil.isNotBlank(params.get("uId"))) {
            qw.eq(InArticle::getuId, params.get("uId"));
        }
        if (params.containsKey("aStatus") && StringUtil.isNotBlank(params.get("aStatus"))) {
            qw.eq(InArticle::getaStatus, params.get("aStatus"));
        }
        IPage<InArticle> page = this.page(
                new Query<InArticle>().getPage(params), qw
        );
        for (InArticle a : page.getRecords()) {
            Object obj = redisUtils.hget(RedisKeys.INUSER, String.valueOf(a.getuId()));
            if (null != obj) {
                a.setuName(((InUser) obj).getuName());
            }
            if(StringUtil.isNotBlank(a.getaCreateTime())){
                a.setaSimpleTime(DateUtils.getSimpleTime(a.getaCreateTime()));
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void updateReadNumber(Long aReadNumber, Long aId) {
        this.baseMapper.addReadNumber(aReadNumber, aId);
    }


    @Override
    @HashCacheable(key = RedisKeys.LIKE, keyField = "#id-#uid-#type")
    public Date giveALike(Long id,int type, Long uid) {
        if(NewsEnum.点赞_文章.getCode().equals(type)){
            this.baseMapper.addALike(id);
        }
        if(NewsEnum.点赞_帖子.getCode().equals(type)){
            this.inCardBaseDao.addALike(id);
        }
        if(NewsEnum.点赞_活动.getCode().equals(type)){
            this.inActivityDao.addALike(id);
        }
        return new Date();
    }

    @Override
    @HashCacheable(key = RedisKeys.COLLECT, keyField = "#id-#uid-#type")
    public Date collect(Long id,int type, Long uid) {
        if(NewsEnum.收藏_文章.getCode().equals(type)){
            this.baseMapper.addACollect(id);
        }
        if(NewsEnum.收藏_帖子.getCode().equals(type)){
            this.inCardBaseDao.addACollect(id);
        }
        if(NewsEnum.收藏_活动.getCode().equals(type)){
            this.inActivityDao.addACollect(id);
        }
        return new Date();
    }
}
