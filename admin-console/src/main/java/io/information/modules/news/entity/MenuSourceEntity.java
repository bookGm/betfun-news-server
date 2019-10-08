package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 资讯菜单资源关系表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
@TableName("in_menu_source")
public class MenuSourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 中间表ID
	 */
	@TableId
	private Long msId;
	/**
	 * 菜单名称
	 */
	private String mName;
	/**
	 * 资源名称
	 */
	private String sName;
	/**
	 * 菜单编码
	 */
	private String mCode;
	/**
	 * 资源路径
	 */
	private String sUrl;

	public Long getMsId() {
		return msId;
	}

	public void setMsId(Long msId) {
		this.msId = msId;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}

	public String getsUrl() {
		return sUrl;
	}

	public void setsUrl(String sUrl) {
		this.sUrl = sUrl;
	}
}
