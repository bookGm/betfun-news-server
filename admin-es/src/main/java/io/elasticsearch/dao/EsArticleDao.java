package io.elasticsearch.dao;

import io.elasticsearch.entity.EsArticleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

public interface EsArticleDao extends ElasticsearchRepository<EsArticleEntity,Long> {
}
