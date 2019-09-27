package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯帖子辩论表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Data
@TableName("in_card_argue")
public class CardArgueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 帖子id
	 */
	@TableId
	private Long cId;
	/**
	 * 正方观点
	 */
	private String caFside;
	/**
	 * 反方观点
	 */
	private String caRside;
	/**
	 * 正方观点投票人ids，逗号分隔
	 */
	private String caFsideUids;
	/**
	 * 反方观点投票人ids，逗号分隔
	 */
	private String caRsideUids;
	/**
	 * 辩论结束日期
	 */
	private Date caCloseTime;

}
