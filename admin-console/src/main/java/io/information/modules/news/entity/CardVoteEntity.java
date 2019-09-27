package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯投票帖详情（最多30个投票选项）
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
@Data
@TableName("in_card_vote")
public class CardVoteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 帖子id
	 */
	@TableId
	private Long cId;
	/**
	 * 投票选项信息（逗号分隔）
	 */
	private String cvInfo;
	/**
	 * 结束日期
	 */
	private Date cvCloseTime;
	/**
	 * 
	 */
	private Integer cv0;
	/**
	 * 
	 */
	private Integer cv1;
	/**
	 * 
	 */
	private Integer cv2;
	/**
	 * 
	 */
	private Integer cv3;
	/**
	 * 
	 */
	private Integer cv4;
	/**
	 * 
	 */
	private Integer cv5;
	/**
	 * 
	 */
	private Integer cv6;
	/**
	 * 
	 */
	private Integer cv7;
	/**
	 * 
	 */
	private Integer cv8;
	/**
	 * 
	 */
	private Integer cv9;
	/**
	 * 
	 */
	private Integer cv10;
	/**
	 * 
	 */
	private Integer cv11;
	/**
	 * 
	 */
	private Integer cv12;
	/**
	 * 
	 */
	private Integer cv13;
	/**
	 * 
	 */
	private Integer cv14;
	/**
	 * 
	 */
	private Integer cv15;
	/**
	 * 
	 */
	private Integer cv16;
	/**
	 * 
	 */
	private Integer cv17;
	/**
	 * 
	 */
	private Integer cv18;
	/**
	 * 
	 */
	private Integer cv19;
	/**
	 * 
	 */
	private Integer cv20;
	/**
	 * 
	 */
	private Integer cv21;
	/**
	 * 
	 */
	private Integer cv22;
	/**
	 * 
	 */
	private Integer cv23;
	/**
	 * 
	 */
	private Integer cv24;
	/**
	 * 
	 */
	private Integer cv25;
	/**
	 * 
	 */
	private Integer cv26;
	/**
	 * 
	 */
	private Integer cv27;
	/**
	 * 
	 */
	private Integer cv28;
	/**
	 * 
	 */
	private Integer cv29;

}
