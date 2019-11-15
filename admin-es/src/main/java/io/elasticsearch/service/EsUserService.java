package io.elasticsearch.service;

import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

import java.util.List;
import java.util.Map;

public interface EsUserService {
    void saveUser(EsUserEntity userEntity);

    void removeUser(String[] uIds);

    void updatedUser(EsUserEntity userEntity);
}
