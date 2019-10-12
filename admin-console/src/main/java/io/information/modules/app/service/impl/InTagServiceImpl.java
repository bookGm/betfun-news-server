package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InTagDao;
import io.information.modules.app.entity.InTag;
import io.information.modules.app.service.IInTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InTagServiceImpl extends ServiceImpl<InTagDao, InTag> implements IInTagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InTag> page = this.page(
                new Query<InTag>().getPage(params),
                new QueryWrapper<InTag>()
        );
        return new PageUtils(page);
    }
}
