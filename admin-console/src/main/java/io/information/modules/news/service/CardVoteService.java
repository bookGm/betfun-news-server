package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.CardVoteEntity;

import java.util.Map;

/**
 * 资讯投票帖详情（最多30个投票选项）
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
public interface CardVoteService extends IService<CardVoteEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

