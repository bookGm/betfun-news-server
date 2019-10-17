package io.elasticsearch.service;

import io.elasticsearch.entity.EsUserEntity;

public interface EsUserService {
    void saveUser(EsUserEntity userEntity);

    void removeUser(Long[] uIds);

    void updatedUser(EsUserEntity userEntity);
}
