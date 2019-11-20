package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InActivityTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 活动票
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 13:32:16
 */
@Component
@Mapper
public interface InActivityTicketDao extends BaseMapper<InActivityTicket> {
    List<InActivityTicket> searchByActId(@Param("actId") Long actId);
}
