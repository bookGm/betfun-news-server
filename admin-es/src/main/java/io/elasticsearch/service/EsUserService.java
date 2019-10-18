package io.elasticsearch.service;

import io.elasticsearch.entity.EsUserEntity;

import java.util.List;

public interface EsUserService {
    void saveUser(EsUserEntity userEntity);

    void removeUser(Long[] uIds);

    void updatedUser(EsUserEntity userEntity);

    List<EsUserEntity> search(String key);
}
