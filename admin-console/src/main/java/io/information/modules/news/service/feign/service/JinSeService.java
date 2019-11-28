package io.information.modules.news.service.feign.service;

import io.information.modules.news.service.feign.common.FeignResJinSe;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LX
 */

@FeignClient(name = "jinse-service", url = "https://api.jinse.com/v4", fallback = void.class)
public interface JinSeService {
    /**
     * 获取文章列表
     *
     * @return
     */
    @RequestMapping(value = "/live/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    FeignResJinSe getPageList(@RequestParam("limit") int limit, @RequestParam("id") int id,
                              @RequestParam(value = "reading", required = false, defaultValue = "false") String reading,
                              @RequestParam(value = "source", required = false, defaultValue = "web") String source,
                              @RequestParam(value = "flag", required = false, defaultValue = "down") String flag);

}
