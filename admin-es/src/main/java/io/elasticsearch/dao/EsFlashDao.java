package io.elasticsearch.dao;

import io.elasticsearch.entity.EsFlashEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsFlashDao extends ElasticsearchRepository<EsFlashEntity, Long> {
}
