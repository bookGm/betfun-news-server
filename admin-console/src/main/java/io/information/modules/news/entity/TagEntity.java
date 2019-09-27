package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Data
@TableName("in_tag")
public class TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@TableId
	private Long tId;
	/**
	 * 标签名称
	 */
	private String tName;
	/**
	 * 标签来源（0：抓取  1：后台维护）
	 */
	private Integer tFrom;
	/**
	 * 标签创建时间
	 */
	private Date tCreateTime;

}
