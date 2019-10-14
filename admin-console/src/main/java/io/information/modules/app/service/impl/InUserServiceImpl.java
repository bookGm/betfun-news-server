package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.app.dao.InUserDao;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯用户表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InUserServiceImpl extends ServiceImpl<InUserDao, InUser> implements IInUserService {
    private static final Logger LOG = LoggerFactory.getLogger(InUserServiceImpl.class);
    @Autowired
    RedisUtils redisUtils;

    @Override
    public List<InUser> queryLikeByUser(String params) {
        List<InUser> users = new ArrayList<>();

        QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
        //根据昵称查询
        queryWrapper.lambda().like(InUser::getuNick, params);
        InUser user1 = this.getOne(queryWrapper);
        user1.setuPwd(null);
        user1.setuToken(null);
        user1.setuName(null);
        users.add(user1);
        //根据电话查询
        queryWrapper.lambda().eq(InUser::getuPhone, params);
        InUser user2 = this.getOne(queryWrapper);
        user2.setuPwd(null);
        user2.setuToken(null);
        user2.setuName(null);
        users.add(user2);
        //根据用户简介查询
        queryWrapper.lambda().like(InUser::getuIntro, params);
        InUser user3 = this.getOne(queryWrapper);
        user3.setuPwd(null);
        user3.setuToken(null);
        user3.setuName(null);
        users.add(user3);

        return users;
    }

    @Override
    public InUser queryUserByNick(String nick) {
        QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InUser::getuNick, nick);
        return this.getOne(queryWrapper);
    }

    @Override
    public PageUtils queryUsersByArgueIds(Map<String, Object> params) {
        String userIds = null;
        String caFsideUids = (String) params.get("caFsideUids");
        String caRsideUids = (String) params.get("caRsideUids");
        if (!"".equals(caFsideUids) && !caFsideUids.isEmpty()) {
            userIds = (String) params.get("caFsideUids");
        }
        if (!"".equals(caRsideUids) && !caRsideUids.isEmpty()) {
            userIds = (String) params.get("caRsideUids");
        }

        if (!userIds.isEmpty()) {
            String[] ids = caFsideUids.split(",");
            List<Long> idList = Arrays.stream(ids).map(Long::valueOf).collect(Collectors.toList());

            QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(InUser::getuId, idList);
            IPage<InUser> page = this.page(
                    new Query<InUser>().getPage(params),
                    queryWrapper
            );
            return new PageUtils(page);
        }
        return null;
    }

    @Override
    public Boolean saveWithCache(InUser inUser) {
        if (this.save(inUser)) {
            try {
                redisUtils.hset(RedisKeys.INUSER, inUser.getuId(), inUser);
            } catch (Exception e) {
                LOG.error("新注册用户:" + inUser.getuPhone() + "放入缓存失败");
            }
            return true;
        }
        return false;
    }

    @Override
    public InUser findUser(String username, String password) {
        QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InUser::getuName, username);
        InUser user = this.getOne(queryWrapper);
        //2. 判断是否存在
        if (user == null) {
            //用户名错误
            throw new IMException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        //3. 校验密码  解密
        if (!password.equals(user.getuPwd())) {
            //密码错误
            throw new IMException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        return user;
    }


}
