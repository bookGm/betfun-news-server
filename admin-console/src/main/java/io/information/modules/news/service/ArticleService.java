package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.ArticleEntity;

import java.util.Map;

/**
 * 资讯文章表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
public interface ArticleService extends IService<ArticleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryAllArticle(Map<String, Object> params, Long userId);

    void deleteAllArticle(Long userId);

    PageUtils audit(Map<String, Object> params);

    /**
     * 抓取文章（全量）
     * @param page
     */
    void catchArticles(int page);
}

