package io.elasticsearch.dao;

import io.elasticsearch.entity.EsUserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsUserDao extends ElasticsearchRepository<EsUserEntity,Long> {
    EsUserEntity findByuId(Long uId);
}
