package io.elasticsearch.service;

import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

public interface EsCardService {
    PageUtils cardSearch(SearchRequest request);
}
