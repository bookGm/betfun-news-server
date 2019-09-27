package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯活动表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Data
@TableName("in_activity")
public class ActivityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	@TableId
	private Long actId;
	/**
	 * 用户id
	 */
	private Long uId;
	/**
	 * 活动内容
	 */
	private String actTitle;
	/**
	 * 活动时间
	 */
	private Date actTime;
	/**
	 * 活动分类（字典）
	 */
	private Integer actCategory;
	/**
	 * 活动地址（省市县编码横线分隔）
	 */
	private String actAddr;
	/**
	 * 活动地址详情
	 */
	private String actAddrDetail;
	/**
	 * 活动人数
	 */
	private Long actNum;
	/**
	 * 已参加人数
	 */
	private Long actInNum;
	/**
	 * 活动封面URL
	 */
	private String actCover;
	/**
	 * 活动详情
	 */
	private String actDetail;
	/**
	 * 活动开始时间
	 */
	private Date actStartTime;
	/**
	 * 活动结束时间
	 */
	private Date actCloseTime;
	/**
	 * 活动创建时间
	 */
	private Date actCreateTime;

}
