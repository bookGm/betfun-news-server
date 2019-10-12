package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.dao.InCardVoteDao;
import io.information.modules.app.entity.InCardVote;
import io.information.modules.app.service.IInCardVoteService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 资讯投票帖详情（最多30个投票选项） 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InCardVoteServiceImpl extends ServiceImpl<InCardVoteDao, InCardVote> implements IInCardVoteService {

    @Override
    @CachePut(value = RedisKeys.VOTE, key = "#cid+'-'+#uid+'-'+#optIndex")
    public boolean vote(Long cid, Long uid, Integer optIndex) {
        try {
            this.baseMapper.addVote(cid, optIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InCardVote> page = this.page(
                new Query<InCardVote>().getPage(params),
                new QueryWrapper<InCardVote>()
        );
        return new PageUtils(page);
    }
}
