package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.CardBaseEntity;
import io.information.modules.news.entity.CardVo;

import java.util.Map;

/**
 * 资讯帖子基础表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
public interface CardBaseService extends IService<CardBaseEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void addCard(CardVo cardVo);

    void deleteCard(Long[] cardIds);

    PageUtils queryAllCard(Map<String, Object> params, Long userId);
}

