package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.SourceDao;
import io.information.modules.news.entity.SourceEntity;
import io.information.modules.news.service.SourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("sourceService")
public class SourceServiceImpl extends ServiceImpl<SourceDao, SourceEntity> implements SourceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<SourceEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = String.valueOf(params.get("key"));
            queryWrapper.eq(SourceEntity::getsOperationUserid, key)
                    .or()
                    .like(SourceEntity::getsComponent, key)
                    .or()
                    .like(SourceEntity::getsName, key);
        }
        queryWrapper.orderByDesc(SourceEntity::getsCreateTime);
        IPage<SourceEntity> page = this.page(
                new Query<SourceEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public SourceEntity getByUrl(String sUrl) {
        LambdaQueryWrapper<SourceEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SourceEntity::getsUrl, sUrl);
        return this.getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByUrl(SourceEntity source) {
        LambdaUpdateWrapper<SourceEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SourceEntity::getsUrl, source.getsUrl());
        this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByUrl(List<String> urlList) {
        LambdaQueryWrapper<SourceEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SourceEntity::getsUrl, urlList);
        this.remove(queryWrapper);
    }

}