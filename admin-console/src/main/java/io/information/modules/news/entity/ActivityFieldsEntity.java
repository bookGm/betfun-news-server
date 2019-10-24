package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
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
public class ActivityFieldsEntity implements Serializable {
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
	private String fKey;
	/**
	 * 属性名
	 */
	private String fName;
	/**
	 * 提示
	 */
	private String fPlaceholder;
	/**
	 * 属性是否必填（0：非必填  1必填）
	 */
	private Integer fNotnull;

}
