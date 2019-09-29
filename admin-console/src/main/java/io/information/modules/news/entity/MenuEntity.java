package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯菜单表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@TableName("in_menu")
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单id
	 */
	@TableId
	private Long mId;
	/**
	 * 菜单名称
	 */
	private String mName;
	/**
	 * 菜单编码
	 */
	private String mCode;
	/**
	 * 菜单父编码
	 */
	private String mPcode;
	/**
	 * 菜单路径（资源表）
	 */
	private String mUrl;
	/**
	 * 是否禁用（0：否  1：是）
	 */
	private Integer mDisable;

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}

	public String getmPcode() {
		return mPcode;
	}

	public void setmPcode(String mPcode) {
		this.mPcode = mPcode;
	}

	public String getmUrl() {
		return mUrl;
	}

	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}

	public Integer getmDisable() {
		return mDisable;
	}

	public void setmDisable(Integer mDisable) {
		this.mDisable = mDisable;
	}
}
