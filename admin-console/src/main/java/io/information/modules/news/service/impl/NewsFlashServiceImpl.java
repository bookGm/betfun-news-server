package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import io.elasticsearch.entity.EsFlashEntity;
import io.information.common.utils.*;
import io.information.modules.news.dao.NewsFlashDao;
import io.information.modules.news.entity.NewsFlash;
import io.information.modules.news.service.NewsFlashService;
import io.information.modules.news.service.feign.common.FeignResJinSe;
import io.information.modules.news.service.feign.service.JinSeService;
import io.information.modules.news.service.feign.vo.JinSeLivesVo;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 资讯快讯表 服务实现类
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
@Service
public class NewsFlashServiceImpl extends ServiceImpl<NewsFlashDao, NewsFlash> implements NewsFlashService {
    @Autowired
    JinSeService jinSeService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void catchNewsFlash(int start, int pages) {
        for (int i = 0; i < pages; i++) {
            int s = start - (i * 20);
            FeignResJinSe jinse = jinSeService.getPageList(20, s, null, null, null);
            List<JinSeLivesVo> jinseList = JsonUtil.parseList(jinse.getList().getJSONObject(0).getString("lives"), JinSeLivesVo.class);
            for (JinSeLivesVo se : jinseList) {
                NewsFlash n = new NewsFlash();
                String c = se.getContent();
                String title = c.substring(c.indexOf("【") + 1, c.indexOf("】"));
                String content = c.substring(c.indexOf("】") + 1);
                n.setnId(IdGenerator.getId());
                n.setnTitle(title);
                n.setnBrief(content);
                n.setnContent(content);
                n.setnBull(se.getUp_counts());
                n.setnBad(se.getDown_counts());
                n.setnCreateTime(new Date(se.getCreated_at() * 1000));
                this.save(n);
            }
        }
    }

    @Override
    public void catchIncrementsFlash() {
        boolean lock = redisUtils.lock(RedisKeys.CATCH_NEWS_LOCK, RedisKeys.CATCH_NEWS_LOCK, 10);
        if (lock) {
            int s = 0;
            FeignResJinSe jinse = jinSeService.getPageList(20, s, null, null, null);
            List<JinSeLivesVo> jinseList = JsonUtil.parseList(jinse.getList().getJSONObject(0).getString("lives"), JinSeLivesVo.class);
            for (JinSeLivesVo se : jinseList) {
                Long id = se.getId();
                if (redisUtils.hasKey(RedisKeys.NEWSFLASH + id)) {
                    continue;
                }
                redisUtils.set(RedisKeys.NEWSFLASH + id, String.valueOf(id));
                NewsFlash n = new NewsFlash();
                String c = se.getContent();
                String title = c.substring(c.indexOf("【") + 1, c.indexOf("】"));
                String content = c.substring(c.indexOf("】") + 1);
                n.setnId(IdGenerator.getId());
                n.setnTitle(title);
                n.setnBrief(content);
                n.setnContent(content);
                n.setnBull(se.getUp_counts());
                n.setnBad(se.getDown_counts());
                n.setnCreateTime(new Date(se.getCreated_at() * 1000));
                this.save(n);
                rabbitTemplate.convertAndSend(Constants.flashExchange,
                        Constants.flash_Save_RouteKey, DataUtils.copyData(n, EsFlashEntity.class));
            }
            jinse = null;
            jinseList = null;
            redisUtils.releaseLock(RedisKeys.CATCH_NEWS_LOCK, RedisKeys.CATCH_NEWS_LOCK);
        }

    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<NewsFlash> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(NewsFlash::getnCreateTime);
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = (String) params.get("key");
            queryWrapper.like(NewsFlash::getnTitle, key)
                    .or()
                    .eq(NewsFlash::getnId, key)
                    .or()
                    .like(NewsFlash::getnBrief, key);
        }
        queryWrapper.orderByDesc(NewsFlash::getnCreateTime);
        IPage<NewsFlash> page = this.page(
                new Query<NewsFlash>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}
