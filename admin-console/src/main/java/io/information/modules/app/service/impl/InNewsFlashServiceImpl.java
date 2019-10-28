package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InNewsFlashDao;
import io.information.modules.app.entity.InNewsFlash;
import io.information.modules.app.service.IInNewsFlashService;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * <p>
 * 资讯快讯表 服务实现类
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
@Service
public class InNewsFlashServiceImpl extends ServiceImpl<InNewsFlashDao, InNewsFlash> implements IInNewsFlashService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<InNewsFlash> qw = new LambdaQueryWrapper<InNewsFlash>();
        qw.orderByDesc(InNewsFlash::getnCreateTime);
        IPage<InNewsFlash> page = this.page(
                new Query<InNewsFlash>().getPage(params), qw
        );
        return new PageUtils(page);
    }
}