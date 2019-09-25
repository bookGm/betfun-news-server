package io.elasticsearch.service;

import io.elasticsearch.entity.LocationEntity;
import io.elasticsearch.utils.PageUtils;

public interface LocationService{

    //保存
    boolean save(LocationEntity entity);

    // 删除
    void delete(LocationEntity entity);

    // 根据ID查询
    LocationEntity findById(String id);

    // 查询全部
    Iterable<LocationEntity> findAll();

    // 分页查询
    PageUtils findPage(String params,int currentPage,int size);

}
