package io.elasticsearch.service;

import io.elasticsearch.entity.EsCardEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

public interface EsCardService {

    void saveCard(EsCardEntity cardEntity);

    void removeCard(Long[] cIds);

    void updatedCard(EsCardEntity cardEntity);

    PageUtils cardSearch(SearchRequest request);

    PageUtils statusSearch(SearchRequest request);

    PageUtils userSearch(SearchRequest request);
}
