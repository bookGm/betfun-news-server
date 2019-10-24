package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯活动动态表单数据
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 11:03:00
 */
@TableName("in_activity_datas")
public class InActivityDatas implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据id
	 */
	@TableId
	private Long dId;
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
	private Long dTime;


	public Long getdId() {
		return dId;
	}

	public void setdId(Long dId) {
		this.dId = dId;
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

	public Long getdTime() {
		return dTime;
	}

	public void setdTime(Long dTime) {
		this.dTime = dTime;
	}
}
