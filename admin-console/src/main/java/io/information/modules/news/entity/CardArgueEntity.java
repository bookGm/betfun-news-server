package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 资讯帖子辩论表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@TableName("in_card_argue")
public class CardArgueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 普通帖子
	 */
	@TableField(exist = false)
	private CardBaseEntity baseCard;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date caCloseTime;


	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public String getCaFside() {
		return caFside;
	}

	public void setCaFside(String caFside) {
		this.caFside = caFside;
	}

	public String getCaRside() {
		return caRside;
	}

	public void setCaRside(String caRside) {
		this.caRside = caRside;
	}

	public String getCaFsideUids() {
		return caFsideUids;
	}

	public void setCaFsideUids(String caFsideUids) {
		this.caFsideUids = caFsideUids;
	}

	public String getCaRsideUids() {
		return caRsideUids;
	}

	public void setCaRsideUids(String caRsideUids) {
		this.caRsideUids = caRsideUids;
	}

	public Date getCaCloseTime() {
		return caCloseTime;
	}

	public void setCaCloseTime(Date caCloseTime) {
		this.caCloseTime = caCloseTime;
	}

	public CardBaseEntity getBaseCard() {
		return baseCard;
	}

	public void setBaseCard(CardBaseEntity baseCard) {
		this.baseCard = baseCard;
	}
}
