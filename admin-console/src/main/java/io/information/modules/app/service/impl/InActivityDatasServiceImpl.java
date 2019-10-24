package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InActivityDatasDao;
import io.information.modules.app.entity.InActivityDatas;
import io.information.modules.app.service.IInActivityDatasService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("activityDatasService")
public class InActivityDatasServiceImpl extends ServiceImpl<InActivityDatasDao, InActivityDatas> implements IInActivityDatasService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InActivityDatas> page = this.page(
                new Query<InActivityDatas>().getPage(params),
                new QueryWrapper<InActivityDatas>()
        );

        return new PageUtils(page);
    }


    @Override
    public List<InActivityDatas> queryByActId(Long actId) {
        LambdaQueryWrapper<InActivityDatas> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InActivityDatas::getActId,actId);
        List<InActivityDatas> datasList = this.list(queryWrapper);
        return datasList;
    }

    @Override
    public PageUtils pass(Map<String, Object> map) {
        String actId = (String)map.get("actId");
        LambdaQueryWrapper<InActivityDatas> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InActivityDatas::getActId,actId);
        IPage<InActivityDatas> page = this.page(
                new Query<InActivityDatas>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }

}