package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.modules.app.dao.InMenuDao;
import io.information.modules.app.entity.InMenu;
import io.information.modules.app.service.IInMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯菜单表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InMenuServiceImpl extends ServiceImpl<InMenuDao, InMenu> implements IInMenuService {

}
