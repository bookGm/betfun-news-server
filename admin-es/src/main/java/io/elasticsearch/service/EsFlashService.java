package io.elasticsearch.service;

import io.elasticsearch.entity.EsFlashEntity;

public interface EsFlashService {
    void saveFlash(EsFlashEntity flashEntity);

    void removeFlash(String[] nIds);

    void updatedFlash(EsFlashEntity flashEntity);
}
