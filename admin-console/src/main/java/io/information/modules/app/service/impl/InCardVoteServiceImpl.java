package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.dao.InCardVoteDao;
import io.information.modules.app.entity.InCardVote;
import io.information.modules.app.service.IInCardVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

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
   @Autowired
   InCardVoteDao inCardVoteDao;

    @Override
    @CachePut(value= RedisKeys.VOTE,key = "#cid+'-'+#uid+'-'+#optIndex")
    public boolean vote(Long cid, Long uid, Integer optIndex) {
        try {
            inCardVoteDao.addVote(cid,optIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
