package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.entity.InSource;
import io.information.modules.app.service.IInSourceService;
import io.information.modules.sys.controller.AbstractController;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.config.HunterConfigContext;
import me.zhyd.hunter.config.platform.Platform;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.enums.ExitWayEnum;
import me.zhyd.hunter.processor.BlogHunterProcessor;
import me.zhyd.hunter.processor.HunterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 资讯资源表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/source")
public class InSourceController extends AbstractController {
    @Autowired
    private IInSourceService sourceService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = sourceService.queryPage(map);

        return R.ok().put("page",page);
    }


    /**
     * 角色列表
     */
    @GetMapping("/select")
    public R select() {
        Map<String, Object> map = new HashMap<>();

        List<InSource> sourceList = (List<InSource>) sourceService.listByMap(map);

        return R.ok().put("sourceList",sourceList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{sUrl}")
    public R info(@PathVariable("sUrl") String sUrl) {
        InSource source = sourceService.getByUrl(sUrl);

        return R.ok().put("source",source);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody InSource source) {
        source.setsOperationUserid(getUserId());
        source.setsCreateTime(new Date());
        sourceService.save(source);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody InSource source) {
        sourceService.updateByUrl(source);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] sUrls) {
        sourceService.removeByUrl(Arrays.asList(sUrls));
        return R.ok();
    }


    public static void main(String[] args) {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.BBTC);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        for(VirtualArticle va:list){
            System.out.println("getTitle=---------------------："+va.getTitle());
            System.out.println("getDescription--------------------："+va.getDescription());
        }
    }
}
