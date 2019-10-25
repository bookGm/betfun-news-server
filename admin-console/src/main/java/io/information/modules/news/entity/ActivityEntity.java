package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 资讯活动表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
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
	 * 活动标题
	 */
	private String actTitle;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date actStartTime;
	/**
	 * 活动结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date actCloseTime;
	/**
	 * 活动创建时间
	 */
	private Date actCreateTime;
	/**
	 * 活动状态（0:未通过 1:审核中 2:已通过）
	 */
	private Integer actStatus;

	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public String getActTitle() {
		return actTitle;
	}

	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}

	public Integer getActCategory() {
		return actCategory;
	}

	public void setActCategory(Integer actCategory) {
		this.actCategory = actCategory;
	}

	public String getActAddr() {
		return actAddr;
	}

	public void setActAddr(String actAddr) {
		this.actAddr = actAddr;
	}

	public String getActAddrDetail() {
		return actAddrDetail;
	}

	public void setActAddrDetail(String actAddrDetail) {
		this.actAddrDetail = actAddrDetail;
	}

	public Long getActNum() {
		return actNum;
	}

	public void setActNum(Long actNum) {
		this.actNum = actNum;
	}

	public Long getActInNum() {
		return actInNum;
	}

	public void setActInNum(Long actInNum) {
		this.actInNum = actInNum;
	}

	public String getActCover() {
		return actCover;
	}

	public void setActCover(String actCover) {
		this.actCover = actCover;
	}

	public String getActDetail() {
		return actDetail;
	}

	public void setActDetail(String actDetail) {
		this.actDetail = actDetail;
	}

	public Date getActStartTime() {
		return actStartTime;
	}

	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}

	public Date getActCloseTime() {
		return actCloseTime;
	}

	public void setActCloseTime(Date actCloseTime) {
		this.actCloseTime = actCloseTime;
	}

	public Date getActCreateTime() {
		return actCreateTime;
	}

	public void setActCreateTime(Date actCreateTime) {
		this.actCreateTime = actCreateTime;
	}

	public Integer getActStatus() {
		return actStatus;
	}

	public void setActStatus(Integer actStatus) {
		this.actStatus = actStatus;
	}
}
