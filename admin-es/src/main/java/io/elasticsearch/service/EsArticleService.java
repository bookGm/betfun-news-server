package io.elasticsearch.service;

import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

public interface EsArticleService {
    void saveArticle(EsArticleEntity articleEntity);

    void removeArticle(Long[] aIds);

    void updatedArticle(EsArticleEntity articleEntity);

    PageUtils articleSearchKey(SearchRequest request);

    PageUtils articleSearch(SearchRequest request);

    PageUtils articleSearchStatus(SearchRequest request);
}
