package io.elasticsearch.dao;

import io.elasticsearch.entity.EsArticleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsArticleDao extends ElasticsearchRepository<EsArticleEntity,Long> {
}
