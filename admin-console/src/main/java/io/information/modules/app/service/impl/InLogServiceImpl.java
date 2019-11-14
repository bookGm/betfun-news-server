package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InLogDao;
import io.information.modules.app.entity.InLog;
import io.information.modules.app.service.IInLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author LX
 * @since 2019-11-07
 */
@Service
public class InLogServiceImpl extends ServiceImpl<InLogDao, InLog> implements IInLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<InLog> qw=new LambdaQueryWrapper<InLog>();
        IPage<InLog> page = this.page(
                new Query<InLog>().getPage(params),
                qw
        );
        return new PageUtils(page);
    }
}
