package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.dao.InCardArgueDao;
import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.service.IInCardArgueService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 资讯帖子辩论表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InCardArgueServiceImpl extends ServiceImpl<InCardArgueDao, InCardArgue> implements IInCardArgueService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InCardArgue> page = this.page(
                new Query<InCardArgue>().getPage(params),
                new QueryWrapper<InCardArgue>()
        );
        return new PageUtils(page);
    }


    @Override
    @HashCacheable(key = RedisKeys.SUPPORT, keyField = "#cid-#uid")
    public String support(Long cid, Long uid, Integer sIndex) {
        InCardArgue argue = this.getById(cid);
        switch (sIndex) {
            case 0:
                //正方
                int fNumber = argue.getCaFsideNumber() == null ? 0 : argue.getCaFsideNumber();
                argue.setCaFsideNumber(fNumber + 1);
                break;
            case 1:
                //反方
                int rNumber = argue.getCaRsideNumber() == null ? 0 : argue.getCaFsideNumber();
                argue.setCaRsideNumber(rNumber + 1);
                break;
        }
        this.updateById(argue);
        return String.valueOf(sIndex);
    }


    @Override
    @HashCacheable(key = RedisKeys.JOIN, keyField = "#cid-#uid")
    public String join(Long cid, Long uid, Integer jIndex) {
        InCardArgue argue = this.getById(cid);
        switch (jIndex) {
            case 0:
                //正方
                argue.setCaFsideDebater(argue.getCaFsideDebater() + 1);
                break;
            case 1:
                //反方
                argue.setCaRsideDebater(argue.getCaRsideDebater() + 1);
                break;
        }
        this.updateById(argue);
        return String.valueOf(jIndex);
    }
}
