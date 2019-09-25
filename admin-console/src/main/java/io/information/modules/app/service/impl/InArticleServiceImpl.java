package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.service.IInArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        List<InArticle> articleList = this.queryAllArticle(userId);
        List<Long> articleIds = articleList.stream().map(InArticle::getAId).collect(Collectors.toList());
        this.removeByIds(articleIds);
    }

    @Override
    public List<InArticle> queryAllArticle(Long userId) {
        QueryWrapper<InArticle> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InArticle::getUId,userId);
        List<InArticle> articleList = this.list(queryWrapper);
        return articleList;
    }
}
