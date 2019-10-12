package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.ArticleDao;
import io.information.modules.news.entity.ArticleEntity;
import io.information.modules.news.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ArticleEntity> page = this.page(
                new Query<ArticleEntity>().getPage(params),
                new QueryWrapper<ArticleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryAllArticle(Map<String, Object> params, Long userId) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ArticleEntity::getuId, userId);
        IPage<ArticleEntity> page = this.page(
                new Query<ArticleEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllActive(Long userId) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ArticleEntity::getuId, userId);
        List<ArticleEntity> activities = this.list(queryWrapper);
        List<Long> activeIds = activities.stream().map(ArticleEntity::getaId).collect(Collectors.toList());
        this.removeByIds(activeIds);
    }


}