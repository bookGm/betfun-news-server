package io.elasticsearch.service.impl;

import io.elasticsearch.dao.EsUserDao;
import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.service.EsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsUserServiceImpl implements EsUserService {
    @Autowired
    private EsUserDao userDao;


    @Override
    public void saveUser(EsUserEntity userEntity) {
        userDao.save(userEntity);
    }

    @Override
    public void removeUser(Long[] uIds) {
        for (Long uId : uIds) {
            userDao.deleteById(uId);
        }
    }

    @Override
    public void updatedUser(EsUserEntity userEntity) {
        userDao.deleteById(userEntity.getuId());
        userDao.save(userEntity);
    }
}
