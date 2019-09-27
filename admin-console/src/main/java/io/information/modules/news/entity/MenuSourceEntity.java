package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯菜单资源关系表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
@Data
@TableName("in_menu_source")
public class MenuSourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
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
	 * 
	 */
	private String mCode;
	/**
	 * 资源路径
	 */
	private String sUrl;

}
