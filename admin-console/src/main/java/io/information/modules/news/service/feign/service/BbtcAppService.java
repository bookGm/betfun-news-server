package io.information.modules.news.service.feign.service;

import feign.Headers;
import io.information.modules.news.service.feign.common.FeignBbApp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LX
 */

@FeignClient(name = "bbtc-app-service", url = "https://app.blockmeta.com/w1", fallback = void.class)
public interface BbtcAppService {
    /**
     * 获取行情
     *
     * @return
     */
    @Headers({
            "from:web",
            "origin:https://www.8btc.com",
            "referer:https://www.8btc.com/"
    })
    @RequestMapping(value = "/head/ticker", method = RequestMethod.GET, headers = {"from=web", "origin=https://www.8btc.com"})
    FeignBbApp getTikerList(
            @RequestParam(value = "symbols") String symbols
    );

}
