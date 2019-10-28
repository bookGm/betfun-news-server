package io.elasticsearch.service;

import io.elasticsearch.entity.EsFlashEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

public interface EsFlashService {
    void saveFlash(EsFlashEntity flashEntity);

    void removeFlash(Long[] nIds);

    void updatedFlash(EsFlashEntity flashEntity);

    PageUtils searchFlash(SearchRequest request);
}
