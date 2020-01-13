package io.information.modules.app.controller;

import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.common.utils.R;
import io.information.modules.app.service.FlashEsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * es快讯查询
 */
@RestController
@RequestMapping("/app/es/flash")
public class EsFlashController {
    @Autowired
    private FlashEsService flashService;

    /**
     * 搜索快讯
     */
    @PostMapping("/search")
    @ApiOperation(value = "搜索快讯", httpMethod = "POST")
    public PageUtils searchFlash(@RequestBody SearchRequest request) {
        return flashService.searchFlash(request);
    }


    /**
     * 同步
     */
    @PostMapping("/KSIQUXOPELK77")
    public R update() {
        flashService.updateAll();
        return R.ok();
    }
}
