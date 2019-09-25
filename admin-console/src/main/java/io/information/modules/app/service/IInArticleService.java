package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.InArticle;

import java.util.List;

/**
 * <p>
 * 资讯文章表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInArticleService extends IService<InArticle> {

    void deleteAllArticle(Long userId);

    List<InArticle> queryAllArticle(Long userId);
}
