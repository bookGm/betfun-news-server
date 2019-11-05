package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.vo.InCardVote;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯投票帖详情（最多30个投票选项） 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInCardVoteService extends IService<InCardVote> {
    /**
     * 投票
     * @return
     */
    List<Integer> vote(Long cid, Long uid, List<Integer> optIndex);

    PageUtils queryPage(Map<String, Object> params);
}
