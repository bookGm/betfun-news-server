package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.TagDao;
import io.information.modules.news.entity.TagEntity;
import io.information.modules.news.service.TagService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<TagEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = String.valueOf(params.get("key"));
            queryWrapper.eq(TagEntity::gettId, key)
                    .or()
                    .like(TagEntity::gettName, key)
                    .or()
                    .like(TagEntity::gettDescribe, key);
        }
        queryWrapper.orderByDesc(TagEntity::gettCreateTime);
        IPage<TagEntity> page = this.page(
                new Query<TagEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}