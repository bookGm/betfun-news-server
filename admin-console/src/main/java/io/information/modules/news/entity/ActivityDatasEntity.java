package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 资讯活动动态表单数据
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 11:03:00
 */
@TableName("in_activity_datas")
public class ActivityDatasEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据id
	 */
	@TableId(type = IdType.INPUT)
	private Long dId;
	/**
	 * 活动id
	 */
	private Long actId;
	/**
	 * 属性键
	 */
	private String fKey;
	/**
	 * 属性名
	 */
	private String fName;
	/**
	 * 数据值
	 */
	private String dValue;
	/**
	 * 报名用户id
	 */
	private Long uId;
	/**
	 * 参加时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date dTime;

	public Long getdId() {
		return dId;
	}

	public void setdId(Long dId) {
		this.dId = dId;
	}

	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public String getfKey() {
		return fKey;
	}

	public void setfKey(String fKey) {
		this.fKey = fKey;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getdValue() {
		return dValue;
	}

	public void setdValue(String dValue) {
		this.dValue = dValue;
	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public Date getdTime() {
		return dTime;
	}

	public void setdTime(Date dTime) {
		this.dTime = dTime;
	}
}
