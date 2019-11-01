package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InCommonReplyDao;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.service.IInCommonReplyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InCommonReplyServiceImpl extends ServiceImpl<InCommonReplyDao, InCommonReply> implements IInCommonReplyService {


    @Override
    public PageUtils discuss(Map<String, Object> map) {
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("id") && StringUtil.isNotBlank(map.get("id"))) {
            int tId = (int) map.get("id");
            queryWrapper.eq(InCommonReply::gettId, tId);
            if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
                int tType = (int) map.get("type");
                queryWrapper.eq(InCommonReply::gettType, tType);
            }
            IPage<InCommonReply> page = this.page(
                    new Query<InCommonReply>().getPage(map),
                    queryWrapper
            );
            return new PageUtils(page);
        }
        return null;
    }

    @Override
    public PageUtils revert(Map<String, Object> map) {
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("id") && StringUtil.isNotBlank(map.get("id"))) {
            int id = (int) map.get("id");
            queryWrapper.eq(InCommonReply::getToCrId, id);
            IPage<InCommonReply> page = this.page(
                    new Query<InCommonReply>().getPage(map),
                    queryWrapper
            );
            return new PageUtils(page);
        }
        return null;
    }

    @Override
    public PageUtils userMsg(Map<String, Object> params) {
        if (null != params.get("uId") && StringUtil.isNotBlank(params.get("uId"))) {
            Long uId = (Long) params.get("uId");
            LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InCommonReply::gettId, uId).or().eq(InCommonReply::getToCrId, uId);
            IPage<InCommonReply> page = this.page(
                    new Query<InCommonReply>().getPage(params),
                    queryWrapper
            );
            return new PageUtils(page);
        }
        return null;
    }

    @Override
    public PageUtils reply(Map<String, Object> map, List<Long> cIds) {
        IPage<InCommonReply> page = this.page(
                new Query<InCommonReply>().getPage(map),
                new LambdaQueryWrapper<InCommonReply>().in(InCommonReply::gettId, cIds)
        );
        return new PageUtils(page);
    }


    private List<InCommonReply> comList = null;

    public List<InCommonReply> recursion(InCommonReply commonReply) {
        if (null != commonReply.getToCrId() && !String.valueOf(commonReply.getToCrId()).isEmpty()) {
            LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InCommonReply::getCrId, commonReply.getToCrId());
            InCommonReply one = this.getOne(queryWrapper);
            comList.add(one);
            recursion(one);
        }
        return comList;
    }
}
