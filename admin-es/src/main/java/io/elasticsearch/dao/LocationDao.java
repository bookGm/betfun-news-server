package io.elasticsearch.dao;

import io.elasticsearch.entity.LocationEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 测试Es地址数据支持
 */
public interface LocationDao extends ElasticsearchRepository<LocationEntity, String> {
}
