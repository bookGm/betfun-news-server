package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.UserDao;
import io.information.modules.news.entity.UserEntity;
import io.information.modules.news.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = String.valueOf(params.get("key"));
            queryWrapper.like(UserEntity::getuNick, key)
                    .or()
                    .eq(UserEntity::getuId, key)
                    .or()
                    .like(UserEntity::getuName, key)
                    .or()
                    .eq(UserEntity::getuPhone, key);
        }
        queryWrapper.orderByDesc(UserEntity::getuCreateTime);
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils audit(Map<String, Object> params) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = String.valueOf(params.get("key"));
            queryWrapper.like(UserEntity::getuNick, key)
                    .or()
                    .eq(UserEntity::getuId, key)
                    .or()
                    .like(UserEntity::getuName, key)
                    .or()
                    .eq(UserEntity::getuPhone, key);
        }
        queryWrapper.eq(UserEntity::getuAuthStatus, 1);
        queryWrapper.orderByDesc(UserEntity::getuCreateTime);
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }
}