package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.MenuSourceEntity;

import java.util.Map;

/**
 * 资讯菜单资源关系表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
public interface MenuSourceService extends IService<MenuSourceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    MenuSourceEntity infoUrl(String sUrl);

    void updatesUrl(MenuSourceEntity menuSource);
}

