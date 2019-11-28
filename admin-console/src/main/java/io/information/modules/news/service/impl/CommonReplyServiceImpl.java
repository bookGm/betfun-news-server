package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.CommonReplyDao;
import io.information.modules.news.entity.CommonReplyEntity;
import io.information.modules.news.service.CommonReplyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CommonReplyServiceImpl extends ServiceImpl<CommonReplyDao, CommonReplyEntity> implements CommonReplyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommonReplyEntity> page = this.page(
                new Query<CommonReplyEntity>().getPage(params),
                new QueryWrapper<CommonReplyEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CommonReplyEntity> search(Long crIds) {
        CommonReplyEntity commonReplyEntity = this.getById(crIds);
        return null;
    }

}