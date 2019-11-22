package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
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
	 * 标签描述
	 */
	private String tDescribe;
	/**
	 * 标签来源（0：抓取  1：后台维护）
	 */
	private Integer tFrom;
	/**
	 * 标签类型
	 */
	private Integer tCategory;
	/**
	 * 标签创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date tCreateTime;


	public Long gettId() {
		return tId;
	}
	public void settId(Long tId) {
		this.tId = tId;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public Integer gettFrom() {
		return tFrom;
	}
	public void settFrom(Integer tFrom) {
		this.tFrom = tFrom;
	}
	public Date gettCreateTime() {
		return tCreateTime;
	}
	public void settCreateTime(Date tCreateTime) {
		this.tCreateTime = tCreateTime;
	}
	public Integer gettCategory() {
		return tCategory;
	}
	public void settCategory(Integer tCategory) {
		this.tCategory = tCategory;
	}

	public String gettDescribe() {
		return tDescribe;
	}

	public void settDescribe(String tDescribe) {
		this.tDescribe = tDescribe;
	}
}
