package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InMessageDao;
import io.information.modules.app.entity.InMessage;
import io.information.modules.app.service.IInMessageService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class InMessageServiceImpl extends ServiceImpl<InMessageDao, InMessage> implements IInMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (null != params.get("uId") && StringUtil.isNotBlank(params.get("uId"))) {
            LambdaQueryWrapper<InMessage> queryWrapper = new LambdaQueryWrapper<>();
            long uId = Long.parseLong(String.valueOf(params.get("uId")));
            queryWrapper.eq(InMessage::gettId, uId);
            queryWrapper.orderByDesc(InMessage::getmCreateTime);
            IPage<InMessage> page = this.page(
                    new Query<InMessage>().getPage(params),
                    queryWrapper
            );
            return new PageUtils(page);
        }
        return null;
    }

}