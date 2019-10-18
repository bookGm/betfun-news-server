package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.modules.app.dao.InCommonReplyDao;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.service.IInCommonReplyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InCommonReplyServiceImpl extends ServiceImpl<InCommonReplyDao,InCommonReply> implements IInCommonReplyService {


    @Override
    public List<InCommonReply> search(Long ToCrId) {
        //查询回复信息
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InCommonReply::getToCrId,ToCrId);
        InCommonReply reply = this.getOne(queryWrapper);
//        reply.get
        return null;
    }
}
