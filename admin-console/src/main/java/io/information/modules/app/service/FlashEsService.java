package io.information.modules.app.service;

import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;

public interface FlashEsService {

    PageUtils searchFlash(SearchRequest request);
}
