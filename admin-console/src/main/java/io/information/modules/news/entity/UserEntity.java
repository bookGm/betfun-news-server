package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资讯用户表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Data
@TableName("in_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId
	private Long uId;
	/**
	 * 用户姓名
	 */
	private String uName;
	/**
	 * 密码
	 */
	private String uPwd;
	/**
	 * 盐
	 */
	private String uSalt;
	/**
	 * 用户昵称
	 */
	private String uNick;
	/**
	 * 用户手机
	 */
	private String uPhone;
	/**
	 * 用户简介
	 */
	private String uIntro;
	/**
	 * 用户粉丝
	 */
	private Long uFans;
	/**
	 * 用户关注
	 */
	private Integer uFocus;
	/**
	 * 认证状态（0：未通过  1：通过 ）
	 */
	private Integer uAuthStatus;
	/**
	 * 用户令牌
	 */
	private String uToken;

}
