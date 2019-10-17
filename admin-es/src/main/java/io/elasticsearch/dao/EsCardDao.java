package io.elasticsearch.dao;

import io.elasticsearch.entity.EsCardEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface EsCardDao extends ElasticsearchRepository<EsCardEntity,Long> {
    EsCardEntity findBycId(Long cId);
}
