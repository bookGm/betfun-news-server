package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.modules.app.dao.InTagDao;
import io.information.modules.app.entity.InTag;
import io.information.modules.app.service.IInTagService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<InTag> queryAllTag() {
        LambdaQueryWrapper<InTag> queryWrapper = new LambdaQueryWrapper<>();
        List<InTag> tagList = this.list(queryWrapper);
        return tagList;
    }
}
