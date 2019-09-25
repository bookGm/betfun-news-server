package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.modules.app.dao.InSourceDao;
import io.information.modules.app.entity.InSource;
import io.information.modules.app.service.IInSourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯资源表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InSourceServiceImpl extends ServiceImpl<InSourceDao, InSource> implements IInSourceService {

}
