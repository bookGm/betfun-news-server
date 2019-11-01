package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资讯活动动态表单属性
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 10:53:16
 */

@TableName("in_activity_fields")
@ApiModel(value = "活动动态表单属性", description = "表单属性对象")
public class InActivityFields implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@TableId(type = IdType.INPUT)
	private Long fId;
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
	 * 提示
	 */
	@ApiModelProperty(value = "提示", name = "fPlaceholder", required = false)
	private String fPlaceholder;
	/**
	 * 属性是否必填（0：非必填  1必填）
	 */
	@ApiModelProperty(value = "属性是否必填（0：非必填  1必填）", name = "fNotnull", required = true)
	private Integer fNotnull;

	public Long getfId() {
		return fId;
	}
	public void setfId(Long fId) {
		this.fId = fId;
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
	public String getfPlaceholder() {
		return fPlaceholder;
	}
	public void setfPlaceholder(String fPlaceholder) {
		this.fPlaceholder = fPlaceholder;
	}
	public Integer getfNotnull() {
		return fNotnull;
	}
	public void setfNotnull(Integer fNotnull) {
		this.fNotnull = fNotnull;
	}
}
