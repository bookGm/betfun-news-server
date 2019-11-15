package io.elasticsearch.service;

import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

public interface EsArticleService {
    void saveArticle(EsArticleEntity articleEntity);

    void removeArticle(String[] aIds);

    void updatedArticle(EsArticleEntity articleEntity);
}
