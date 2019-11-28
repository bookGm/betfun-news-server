

package io.information.modules.sys.controller;

import io.information.common.utils.R;
import io.information.common.utils.RedisUtils;
import io.information.modules.sys.entity.SysCitysEntity;
import io.information.modules.sys.service.SysCitysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 省市县（区）
 *
 * @author LX
 */
@RestController
@RequestMapping("/sys/citys")
public class SysCitysController extends AbstractController {
    @Autowired
    SysCitysService sysCitysService;
    @Autowired
    RedisUtils redis;

    /**
     * 所有城市列表
     */
    @GetMapping("/listAll")
    public R list(String key) {
        Map<String, List<SysCitysEntity>> listAll = sysCitysService.getListAll("citys");
        return R.ok().put("citys", listAll);
    }

}
