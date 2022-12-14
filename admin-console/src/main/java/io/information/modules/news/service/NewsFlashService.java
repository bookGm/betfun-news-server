package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.NewsFlash;

import java.util.Map;

/**
 * <p>
 * 资讯文章表 服务类
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
public interface NewsFlashService extends IService<NewsFlash> {
    /**
     * 抓取快讯（全量）
     *
     * @param start
     * @param pages
     */
    void catchNewsFlash(int start, int pages);

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 抓取快讯（增量）
     */
    public void catchIncrementsFlash();
}
