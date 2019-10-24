package io.information.modules.app.entity;

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
@Data
@TableName("in_activity_fields")
@ApiModel(value = "活动动态表单属性", description = "表单属性对象")
public class InActivityFields implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@TableId
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

}
