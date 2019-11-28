package io.information.modules.news.service.feign.service;

import io.information.modules.news.service.feign.common.FeignRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LX
 */

@FeignClient(name = "bbtc-service", url = "https://webapi.8btc.com/bbt_api", fallback = void.class)
public interface BbtcService {
    /**
     * 获取文章列表
     *
     * @return
     */
    @RequestMapping(value = "/news/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    FeignRes getPageList(@RequestParam("num") int num, @RequestParam("page") int page);

}
