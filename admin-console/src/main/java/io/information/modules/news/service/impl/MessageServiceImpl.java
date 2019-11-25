package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.MessageDao;
import io.information.modules.news.entity.MessageEntity;
import io.information.modules.news.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<MessageEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("mContent") && StringUtil.isNotBlank(params.get("mContent"))) {
            String mContent = String.valueOf(params.get("mContent"));
            queryWrapper.like(MessageEntity::getmContent, mContent);
        }
        queryWrapper.orderByDesc(MessageEntity::getmCreateTime);
        IPage<MessageEntity> page = this.page(
                new Query<MessageEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}