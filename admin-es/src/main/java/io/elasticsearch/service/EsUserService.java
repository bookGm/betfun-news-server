package io.elasticsearch.service;

import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

import java.util.List;

public interface EsUserService {
    void saveUser(EsUserEntity userEntity);

    void removeUser(Long[] uIds);

    void updatedUser(EsUserEntity userEntity);

    PageUtils search(SearchRequest request);
}
