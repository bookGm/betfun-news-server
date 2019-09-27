package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯帖子基础表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Data
@TableName("in_card_base")
public class CardBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 帖子id
	 */
	@TableId
	private Long cId;
	/**
	 * 用户id
	 */
	private Long uId;
	/**
	 * 帖子分类（字典）
	 */
	private Integer cCategory;
	/**
	 * 帖子节点分类（字典）
	 */
	private Integer cNodeCategory;
	/**
	 * 帖子正文
	 */
	private String cContent;
	/**
	 * 回帖仅作者可见（0：是  1：否）
	 */
	private Integer cHide;

}
