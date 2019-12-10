package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.dao.InNewsFlashDao;
import io.information.modules.app.entity.InNewsFlash;
import io.information.modules.app.service.IInNewsFlashService;
import io.information.modules.app.vo.FlashVo;
import org.springframework.stereotype.Service;

import java.util.List;
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
        for (InNewsFlash a : page.getRecords()) {
            if (StringUtil.isNotBlank(a.getnCreateTime())) {
                a.setnSimpleTime(DateUtils.getSimpleTime(a.getnCreateTime()));
            }
        }
        return new PageUtils(page);
    }

    @Override
    public FlashVo details(Long nId) {
        InNewsFlash flash = this.getById(nId);
        if (null != flash) {
            FlashVo vo = new FlashVo();
            InNewsFlash upDetails = this.baseMapper.upDetails(nId);
            if (null != upDetails) {
                vo.setUpFlash(upDetails);
                vo.setGFlash(true);
            } else {
                vo.setGFlash(false);
            }
            InNewsFlash beDetails = this.baseMapper.beDetails(nId);
            if (null != beDetails) {
                vo.setDeFlash(beDetails);
                vo.setTFlash(true);
            } else {
                vo.setTFlash(false);
            }
            vo.setFlash(flash);
            return vo;
        }
        return null;
    }

    @Override
    @HashCacheable(key = RedisKeys.NATTITUDE, keyField = "#nId-#uId")
    public String attitude(Long nId, Long uId, Integer bId) {
        if (NewsEnum.快讯_利空.getCode().equals(bId + "")) {
            this.baseMapper.addNBad(nId);
        }
        if (NewsEnum.快讯_利好.getCode().equals(bId + "")) {
            this.baseMapper.addNBull(nId);
        }
        return String.valueOf(bId);
    }

    @Override
    public List<InNewsFlash> all() {
        return this.baseMapper.all();
    }
}
