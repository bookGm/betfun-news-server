package io.elasticsearch.service;

import io.elasticsearch.entity.EsCardEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

import java.util.Map;

public interface EsCardService {

    void saveCard(EsCardEntity cardEntity);

    void removeCard(Long[] cIds);

    void updatedCard(EsCardEntity cardEntity);

    PageUtils cardSearch(Map<String,Object> map);

    PageUtils statusSearch(SearchRequest request);

    PageUtils userSearch(SearchRequest request);

    EsCardEntity infoSearch(SearchRequest request);
}
