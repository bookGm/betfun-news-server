package io.information.modules.app.service;

import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

public interface ArticleEsService {

    PageUtils searchInfo(SearchRequest request);
}
