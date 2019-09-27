package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯字典表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Data
@TableName("in_dic")
public class DicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典id
	 */
	@TableId
	private Long dId;
	/**
	 * 字典名称
	 */
	private String dName;
	/**
	 * 字典值
	 */
	private String dValue;
	/**
	 * 字典编码
	 */
	private String dCode;
	/**
	 * 字典父编码
	 */
	private String dPcode;
	/**
	 * 是否禁用（0：否  1：是）
	 */
	private Integer dDisable;
	/**
	 * 字典资源路径
	 */
	private String dUrl;

}
