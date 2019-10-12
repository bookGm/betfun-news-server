package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.service.IInArticleService;
import org.springframework.cache.annotation.CachePut;
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

    @Override
    @Transactional
    public void deleteAllArticle(Long userId) {
        LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InArticle::getuId,userId);
        List<InArticle> articleList = this.list(queryWrapper);
        List<Long> articleIds = articleList.stream()
                .map(InArticle::getaId)
                .collect(Collectors.toList());
        this.removeByIds(articleIds);
    }

    @Override
    public PageUtils queryAllArticle(Map<String, Object> params, Long userId) {
        QueryWrapper<InArticle> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InArticle::getuId, userId);
        IPage<InArticle> page = this.page(
                new Query<InArticle>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InArticle> page = this.page(
                new Query<InArticle>().getPage(params),
                new QueryWrapper<InArticle>()
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
    @CachePut(value = RedisKeys.COLLECT, key = "#aid+'-'+#uid")
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
