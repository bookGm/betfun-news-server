package io.elasticsearch.dao;

import io.elasticsearch.entity.LocationEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * 测试Es地址数据支持
 *
 */
@Component
public interface LocationDao extends ElasticsearchRepository<LocationEntity,String> {
}
