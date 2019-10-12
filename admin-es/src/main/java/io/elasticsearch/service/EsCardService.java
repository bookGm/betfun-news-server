package io.elasticsearch.service;

import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.entity.EsCardEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

public interface EsCardService {

    void saveCard(EsCardEntity cardEntity);

    void removeCard(EsCardEntity cardEntity);

    void updatedCard(EsCardEntity cardEntity);

    PageUtils cardSearch(SearchRequest request);
}
