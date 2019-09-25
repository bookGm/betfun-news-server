package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.modules.app.dao.InUserDao;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Override
    public List<InUser> queryLikeByUser(String params) {
        List<InUser> users = new ArrayList<>();

        QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
        //根据昵称查询
        queryWrapper.lambda().like(InUser::getUNick,params);
        InUser user1 = this.getOne(queryWrapper);
        user1.setUPwd(null);
        user1.setUToken(null);
        user1.setUName(null);
        users.add(user1);
        //根据电话查询
        queryWrapper.lambda().eq(InUser::getUPhone,params);
        InUser user2 = this.getOne(queryWrapper);
        user1.setUPwd(null);
        user1.setUToken(null);
        user1.setUName(null);
        users.add(user2);
        //根据用户简介查询
        queryWrapper.lambda().like(InUser::getUIntro,params);
        InUser user3 = this.getOne(queryWrapper);
        user1.setUPwd(null);
        user1.setUToken(null);
        user1.setUName(null);
        users.add(user3);

        return users;
    }

    @Override
    public InUser queryUserByNick(String nick) {
        QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InUser::getUNick,nick);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<InUser> queryUsersByArgueIds(String userIds) {
        String[] ids = userIds.split(",");
        List<Long> idList = Arrays.stream(ids).map(Long::valueOf).collect(Collectors.toList());

        QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InUser::getUId,idList);
        List<InUser> userList = this.list(queryWrapper);

        return userList;
    }

    @Override
    public InUser findUser(String username, String password) {
        QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InUser::getUName,username);
        InUser user = this.getOne(queryWrapper);
        //2. 判断是否存在
        if(user == null){
            //用户名错误
            throw new IMException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        //3. 校验密码  解密
        if(!password.equals(user.getUPwd())){
            //密码错误
            throw new IMException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        return user;
    }


}
