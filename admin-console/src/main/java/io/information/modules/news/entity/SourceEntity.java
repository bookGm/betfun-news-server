package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯资源表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Data
@TableName("in_source")
public class SourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资源路径
	 */
	@TableId
	private String sUrl;
	/**
	 * 组件名称
	 */
	private String sComponent;
	/**
	 * 资源名称
	 */
	private String sName;
	/**
	 * 系统操作用户id
	 */
	private Long sOperationUserid;
	/**
	 * 是否禁用（0：否 1：是）
	 */
	private Integer sDisable;
	/**
	 * 更新时间
	 */
	private Date sUpdateTime;
	/**
	 * 资源创建时间
	 */
	private Date sCreateTime;

}
