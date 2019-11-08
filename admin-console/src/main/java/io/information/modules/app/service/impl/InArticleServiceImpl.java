package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.*;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InLog;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.app.vo.ArticleUserVo;
import io.information.modules.app.vo.ArticleVo;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private IInUserService userService;
    @Autowired
    private InActivityDao inActivityDao;
    @Autowired
    private InCardBaseDao inCardBaseDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;

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
            if (StringUtil.isNotBlank(a.getaCreateTime())) {
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
    public List<ArticleVo> hotTopic() {
        return this.baseMapper.searchArticleByTime();
    }

    @Override
    public ArticleUserVo articleRecommended(Map<String, Object> map) {
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            InUser user = userService.getById(uId);
            ArticleUserVo articleUserVo = BeanHelper.copyProperties(user, ArticleUserVo.class);
            if (null != articleUserVo) {
                LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(InArticle::getuId, uId);
                int articleNumber = this.count(queryWrapper);
                if (articleNumber > 0) {
                    articleUserVo.setArticleNumber(articleNumber);
                    List<ArticleVo> articleVos = this.baseMapper.searchTitleAndId(uId, currPage, pageSize);
                    articleUserVo.setArticleVos(articleVos);
                } else {
                    articleUserVo.setArticleNumber(0);
                    articleUserVo.setArticleVos(null);
                }
                return articleUserVo;
            }
            return null;
        }
        return null;
    }

    /**
     * 记录操作日志
     */
    void logOperate(Long uId,Long tId,NewsEnum e){
        InLog log=new InLog();
        log.setlOperateId(uId);
        log.setlTargetId(tId);
        log.setlTargetType(1);
        log.setlDo(Integer.parseInt(e.getCode()));
        log.setlTime(new Date());
        rabbitTemplate.convertAndSend(Constants.logExchange, Constants.log_Save_RouteKey, log);
    }

    @Override
    @HashCacheable(key = RedisKeys.LIKE, keyField = "#id-#uid-#tId-#type")
    public String giveALike(Long id, Long tId, int type, Long uid) {
        if (NewsEnum.点赞_文章.getCode().equals(type)) {
            this.baseMapper.addALike(id);
        }
        if (NewsEnum.点赞_帖子.getCode().equals(type)) {
            logOperate(uid,id,NewsEnum.操作_点赞);
            this.inCardBaseDao.addALike(id);
        }
        if (NewsEnum.点赞_活动.getCode().equals(type)) {
            this.inActivityDao.addALike(id);
        }
        return DateUtils.format(new Date());
    }

    @Override
    @HashCacheable(key = RedisKeys.COLLECT, keyField = "#id-#uid-#tId-#type")
    public String collect(Long id, Long tId, int type, Long uid) {
        if (NewsEnum.收藏_文章.getCode().equals(type)) {
            this.baseMapper.addACollect(id);
        }
        if (NewsEnum.收藏_帖子.getCode().equals(type)) {
            logOperate(uid,id,NewsEnum.操作_收藏);
            this.inCardBaseDao.addACollect(id);
        }
        if (NewsEnum.收藏_活动.getCode().equals(type)) {
            this.inActivityDao.addACollect(id);
        }
        return DateUtils.format(new Date());
    }
}
