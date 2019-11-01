package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *  活动票
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 13:32:16
 */
@TableName("in_activity_ticket")
public class ActivityTicketEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 票id
	 */
	@TableId(type = IdType.INPUT)
	private Long tId;
	/**
	 * 活动id
	 */
	private Long actId;
	/**
	 * 票种名称
	 */
	private String tName;
	/**
	 * 价格
	 */
	private BigDecimal tPrice;
	/**
	 * 张数
	 */
	private Integer tNum;

	public Long gettId() {
		return tId;
	}

	public void settId(Long tId) {
		this.tId = tId;
	}

	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public BigDecimal gettPrice() {
		return tPrice;
	}

	public void settPrice(BigDecimal tPrice) {
		this.tPrice = tPrice;
	}

	public Integer gettNum() {
		return tNum;
	}

	public void settNum(Integer tNum) {
		this.tNum = tNum;
	}
}
