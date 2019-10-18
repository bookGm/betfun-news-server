package io.elasticsearch.dao;

import io.elasticsearch.entity.EsSeekEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsSeekDao extends ElasticsearchRepository<EsSeekEntity,Long> {
}
