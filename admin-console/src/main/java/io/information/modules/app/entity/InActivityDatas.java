package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资讯活动动态表单数据
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 11:03:00
 */
@TableName("in_activity_datas")
@ApiModel(value = "活动动态表单数据", description = "表单数据对象")
public class InActivityDatas implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据id
	 */
	@TableId
	private Long dId;
	/**
	 * 活动id
	 */
	private Long actId;
	/**
	 * 属性键
	 */
	@ApiModelProperty(value = "属性键", name = "fKey", required = true)
	private String fKey;
	/**
	 * 属性名
	 */
	@ApiModelProperty(value = "属性名", name = "fName", required = true)
	private String fName;
	/**
	 * 数据值
	 */
	@ApiModelProperty(value = "数据值", name = "dValue", required = true)
	private String dValue;
	/**
	 * 报名用户id
	 */
	private Long uId;
	/**
	 * 参加时间
	 */
	@ApiModelProperty(value = "参加时间", name = "dTime", required = true)
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
