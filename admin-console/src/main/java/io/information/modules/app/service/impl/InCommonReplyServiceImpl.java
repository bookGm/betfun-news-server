package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.modules.app.dao.InCommonReplyDao;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.service.IInCommonReplyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InCommonReplyServiceImpl extends ServiceImpl<InCommonReplyDao, InCommonReply> implements IInCommonReplyService {


    @Override
    public List<InCommonReply> search(Map<String, Object> map) {
        int tId = (int) map.get("tId");
        int tType = (int) map.get("tType");
        //查询回复信息
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InCommonReply::gettId, tId).eq(InCommonReply::gettType, tType);
        InCommonReply reply = this.getOne(queryWrapper);
        return recursion(reply);
    }


    private List<InCommonReply> comList = null;

    public List<InCommonReply> recursion(InCommonReply commonReply) {
        if (commonReply.getToCrId() != null) {
            LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InCommonReply::getCrId, commonReply.getToCrId());
            InCommonReply one = this.getOne(queryWrapper);
            comList.add(one);
            recursion(one);
        }
        return comList;
    }
}
