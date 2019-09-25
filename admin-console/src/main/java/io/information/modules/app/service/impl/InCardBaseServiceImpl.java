package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.service.IInCardBaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯帖子基础表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InCardBaseServiceImpl extends ServiceImpl<InCardBaseDao, InCardBase> implements IInCardBaseService {

    @Override
    public List<InCardBase> queryAllCardBase(Long userId) {
        QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InCardBase::getUId,userId);
        List<InCardBase> cardBases = this.list(queryWrapper);
        return cardBases;
    }

    @Override
    public void deleteAllCardBase(Long userId) {
        List<InCardBase> cardBaseList = this.queryAllCardBase(userId);
        List<Long> cardBaseIds = cardBaseList.stream().map(InCardBase::getCId).collect(Collectors.toList());
        this.removeByIds(cardBaseIds);
    }
}
