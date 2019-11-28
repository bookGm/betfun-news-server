package io.elasticsearch.service;

import io.elasticsearch.entity.EsArticleEntity;

public interface EsArticleService {
    void saveArticle(EsArticleEntity articleEntity);

    void removeArticle(String[] aIds);

    void updatedArticle(EsArticleEntity articleEntity);
}
