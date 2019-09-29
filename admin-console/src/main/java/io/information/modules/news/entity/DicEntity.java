package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 资讯字典表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-29 13:13:05
 */
@TableName("in_dic")
public class DicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典id(值)
	 */
	@TableId
	private Long dId;
	/**
	 * 字典父编码
	 */
	private Long pId;
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
	private Long dCode;
	/**
	 * 字典父编码
	 */
	private Long dPcode;
	/**
	 * 是否禁用（0：否  1：是）
	 */
	private Integer dDisable;
	/**
	 * 字典资源路径
	 */
	private String dUrl;

	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;

	@TableField(exist=false)
	private List<?> list;


	public Long getdId() {
		return dId;
	}
	public void setdId(Long dId) {
		this.dId = dId;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getdValue() {
		return dValue;
	}
	public void setdValue(String dValue) {
		this.dValue = dValue;
	}
	public Long getdCode() {
		return dCode;
	}
	public void setdCode(Long dCode) {
		this.dCode = dCode;
	}
	public Long getdPcode() {
		return dPcode;
	}
	public void setdPcode(Long dPcode) {
		this.dPcode = dPcode;
	}
	public Integer getdDisable() {
		return dDisable;
	}
	public void setdDisable(Integer dDisable) {
		this.dDisable = dDisable;
	}
	public String getdUrl() {
		return dUrl;
	}
	public void setdUrl(String dUrl) {
		this.dUrl = dUrl;
	}

	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
}
