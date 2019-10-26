package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 资讯文章表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@TableName("in_article")
public class ArticleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文章id
	 */
	@TableId(type = IdType.INPUT)
	private Long aId;
	/**
	 * 用户id
	 */
	private Long uId;
	/**
	 * 用户名称
	 */
	private String uName;
	/**
	 * 文章标题
	 */
	private String aTitle;
	/**
	 * 文章内容
	 */
	private String aContent;
	/**
	 * 文章摘要
	 */
	private String aBrief;
	/**
	 * 文章关键字（例：1,2,3）
	 */
	private String aKeyword;
	/**
	 * 文章封面URL
	 */
	private String aCover;
	/**
	 * 文章类型（字典）
	 */
	private Integer aType;
	/**
	 * 文章来源（非转载类型，此字段为空）
	 */
	private String aSource;
	/**
	 * 文章链接（非转载类型，此字段为空）
	 */
	private String aLink;
	/**
	 * 点赞数
	 */
	private Long aLike;
	/**
	 * 收藏数
	 */
	private Integer aCollect;
	/**
	 * 评论数
	 */
	private Long aCritic;
	/**
	 * 文章状态（0：草稿箱，1：审核中，2：已发布）
	 */
	private Integer aStatus;
	/**
	 * 浏览量
	 */
	private Long aReadNumber;
	/**
	 * 创建时间
	 */
	private Date aCreateTime;
	/**
	 * 简洁时间
	 */
	@TableField(exist = false)
	private String aSimpleTime;

	public Long getaId() {
		return aId;
	}

	public void setaId(Long aId) {
		this.aId = aId;
	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public String getaTitle() {
		return aTitle;
	}

	public void setaTitle(String aTitle) {
		this.aTitle = aTitle;
	}

	public String getaContent() {
		return aContent;
	}

	public void setaContent(String aContent) {
		this.aContent = aContent;
	}

	public String getaBrief() {
		return aBrief;
	}

	public void setaBrief(String aBrief) {
		this.aBrief = aBrief;
	}

	public String getaKeyword() {
		return aKeyword;
	}

	public void setaKeyword(String aKeyword) {
		this.aKeyword = aKeyword;
	}

	public String getaCover() {
		return aCover;
	}

	public void setaCover(String aCover) {
		this.aCover = aCover;
	}

	public Integer getaType() {
		return aType;
	}

	public void setaType(Integer aType) {
		this.aType = aType;
	}

	public String getaSource() {
		return aSource;
	}

	public void setaSource(String aSource) {
		this.aSource = aSource;
	}

	public String getaLink() {
		return aLink;
	}

	public void setaLink(String aLink) {
		this.aLink = aLink;
	}

	public Long getaLike() {
		return aLike;
	}

	public void setaLike(Long aLike) {
		this.aLike = aLike;
	}

	public Integer getaCollect() {
		return aCollect;
	}

	public void setaCollect(Integer aCollect) {
		this.aCollect = aCollect;
	}

	public Long getaCritic() {
		return aCritic;
	}

	public void setaCritic(Long aCritic) {
		this.aCritic = aCritic;
	}

	public Date getaCreateTime() {
		return aCreateTime;
	}

	public void setaCreateTime(Date aCreateTime) {
		this.aCreateTime = aCreateTime;
	}

	public Integer getaStatus() {
		return aStatus;
	}

	public void setaStatus(Integer aStatus) {
		this.aStatus = aStatus;
	}

	public Long getaReadNumber() {
		return aReadNumber;
	}

	public void setaReadNumber(Long aReadNumber) {
		this.aReadNumber = aReadNumber;
	}

	public String getaSimpleTime() {
		return aSimpleTime;
	}

	public void setaSimpleTime(String aSimpleTime) {
		this.aSimpleTime = aSimpleTime;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}
}
