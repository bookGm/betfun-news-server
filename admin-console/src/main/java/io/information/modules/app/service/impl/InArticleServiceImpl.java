package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInArticleService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private RabbitTemplate rabbitTemplate;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<InArticle> qw = new LambdaQueryWrapper<InArticle>();
        if (params.containsKey("type")) {
            Object type = params.get("type");
            switch (Integer.parseInt(String.valueOf(type))) {
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
        IPage<InArticle> page = this.page(
                new Query<InArticle>().getPage(params), qw
        );
        for (InArticle a : page.getRecords()) {
            Object obj = redisUtils.hget(RedisKeys.INUSER, String.valueOf(a.getuId()));
            if (null != obj) {
                a.setuName(((InUser) obj).getuName());
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
    public PageUtils statusOK(Map<String, Object> map) {
        LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InArticle::getaStatus, 2);
        IPage<InArticle> page = this.page(
                new Query<InArticle>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils statusArticleUser(Map<String, Object> map, Long uId) {
        int aStatus = (int) map.get("aStatus");
        //0草稿 1审核 2发布
        LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InArticle::getuId, uId).eq(InArticle::getaStatus, aStatus);
        IPage<InArticle> page = this.page(
                new Query<InArticle>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    @CachePut(value = RedisKeys.LIKE, key = "#aid+'-'+#uid")
    public boolean giveALike(Long aid, Long uid) {
        try {
            this.baseMapper.addALike(aid);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Cacheable(value = RedisKeys.COLLECT, key = "#aid+'-'+#uid")
    public boolean collect(Long aid, Long uid) {
        try {
            this.baseMapper.addACollect(aid);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
