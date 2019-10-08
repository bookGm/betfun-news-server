package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 资讯帖子基础表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
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

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public Integer getcCategory() {
		return cCategory;
	}

	public void setcCategory(Integer cCategory) {
		this.cCategory = cCategory;
	}

	public Integer getcNodeCategory() {
		return cNodeCategory;
	}

	public void setcNodeCategory(Integer cNodeCategory) {
		this.cNodeCategory = cNodeCategory;
	}

	public String getcContent() {
		return cContent;
	}

	public void setcContent(String cContent) {
		this.cContent = cContent;
	}

	public Integer getcHide() {
		return cHide;
	}

	public void setcHide(Integer cHide) {
		this.cHide = cHide;
	}
}
