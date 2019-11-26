package io.information.modules.news.controller;

import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.config.IdWorker;
import io.information.modules.news.dto.ArticleDTO;
import io.information.modules.news.entity.ArticleEntity;
import io.information.modules.news.entity.MessageEntity;
import io.information.modules.news.service.ArticleService;
import io.information.modules.news.service.MessageService;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 资讯文章表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/article")
public class ArticleController extends AbstractController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:article:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = articleService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{aId}")
    @RequiresPermissions("news:article:info")
    public R info(@PathVariable("aId") Long aId) {
        ArticleEntity article = articleService.getById(aId);
        return R.ok().put("article", article);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:article:save")
    public R save(@RequestBody ArticleEntity article) {
        article.setaId(new IdWorker().nextId());
        article.setaCreateTime(new Date());
        article.setuId(getUserId());
        articleService.save(article);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:article:update")
    public R update(@RequestBody ArticleEntity article) {
        articleService.updateById(article);
        return R.ok();
    }


    /**
     * 文章是否主页展示
     */
    @PostMapping("/isBanner")
    @RequiresPermissions("news:article:update")
    public R isBanner(@RequestBody ArticleDTO dto) {
        ArticleEntity article = new ArticleEntity();
        article.setaId(dto.getaId());
        article.setaBanner(dto.getaBanner());
        articleService.updateById(article);
        return R.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:article:delete")
    public R delete(@RequestBody Long[] aIds) {
        articleService.removeByIds(Arrays.asList(aIds));
        return R.ok();
    }


    /**
     * 审核
     */
    @GetMapping("/auditList")
    @RequiresPermissions("news:article:list")
    public R draft(@RequestParam Map<String, Object> params) {
        PageUtils page = articleService.audit(params);
        return R.ok().put("page", page);
    }


    /**
     * 已通过
     */
    @PostMapping("/auditOk")
    @RequiresPermissions("news:article:update")
    public R auditOk(@RequestBody Map<String, Object> params) {
        if (null != params.get("aId") && StringUtil.isNotBlank(params.get("aId"))) {
            long aId = Long.parseLong(String.valueOf(params.get("aId")));
            ArticleEntity article = new ArticleEntity();
            article.setaId(aId);
            article.setaStatus(2);
            articleService.updateById(article);
            MessageEntity message = new MessageEntity();
            message.setmId(IdGenerator.getId());
            message.setmContent("恭喜，您发布的文章《" + article.getaTitle() + "》已通过审核");
            message.settId(article.getuId());
            message.setmCreateTime(new Date());
            messageService.save(message);
//            rabbitTemplate.convertAndSend(Constants.systemExchange,
//                    Constants.system_Save_RouteKey, JsonUtil.toJSONString(message));
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }


    /**
     * 未通过
     */
    @PostMapping("/auditNo")
    @RequiresPermissions("news:article:update")
    public R auditNo(@RequestBody Map<String, Object> params) {
        if (null != params.get("aId") && StringUtil.isNotBlank(params.get("aId"))) {
            long aId = Long.parseLong(String.valueOf(params.get("aId")));
            ArticleEntity article = new ArticleEntity();
            article.setaId(aId);
            article.setaStatus(0);
            articleService.updateById(article);
            MessageEntity message = new MessageEntity();
            message.setmId(IdGenerator.getId());
            message.setmContent("很遗憾，您发布的文章《" + article.getaTitle() + "》未通过审核");
            message.settId(article.getuId());
            message.setmCreateTime(new Date());
            messageService.save(message);
//            rabbitTemplate.convertAndSend(Constants.systemExchange,
//                    Constants.system_Save_RouteKey, JsonUtil.toJSONString(message));
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }
}
