package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-25 10:56:47
 */
@Mapper
public interface InMessageDao extends BaseMapper<InMessage> {

}
