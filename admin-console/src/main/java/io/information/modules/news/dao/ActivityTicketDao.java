package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.ActivityTicketEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 活动票
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 13:32:16
 */
@Mapper
public interface ActivityTicketDao extends BaseMapper<ActivityTicketEntity> {

}
