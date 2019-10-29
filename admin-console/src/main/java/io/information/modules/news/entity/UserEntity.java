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
 * @date 2019-10-14 09:12:17
 */
@TableName("in_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId
	private Long uId;
    /**
     * 账号
     */
	private String uAccount;
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
     * 用户头像
     */
    private String uPhoto;
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
	 * 认证状态（0：未通过 1：审核中 1：审核通过 ）
	 */
	private Integer uAuthStatus;
    /**
     * 认证类型（0：个人 1：媒体 2：企业）
     */
    private Integer uAuthType;
	/**
	 * 用户令牌
	 */
	private String uToken;
	/**
	 * 身份证号
	 */
	private String uIdcard;
	/**
	 * 身份证正面
	 */
	private String uIdcardA;
	/**
	 * 身份证背面
	 */
	private String uIdcardB;
	/**
	 * 手持身份证照
	 */
	private String uIdcardHand;
    /**
     * 企业名称
     */
    private String uCompanyName;
    /**
     * 营业注册号
     */
    private String uBrNumber;
    /**
     * 营业执照扫描件
     */
    private String uBrPicture;


    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getuAccount() {
        return uAccount;
    }

    public void setuAccount(String uAccount) {
        this.uAccount = uAccount;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public String getuSalt() {
        return uSalt;
    }

    public void setuSalt(String uSalt) {
        this.uSalt = uSalt;
    }

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuIntro() {
        return uIntro;
    }

    public void setuIntro(String uIntro) {
        this.uIntro = uIntro;
    }

    public Long getuFans() {
        return uFans;
    }

    public void setuFans(Long uFans) {
        this.uFans = uFans;
    }

    public Integer getuFocus() {
        return uFocus;
    }

    public void setuFocus(Integer uFocus) {
        this.uFocus = uFocus;
    }

    public Integer getuAuthStatus() {
        return uAuthStatus;
    }

    public void setuAuthStatus(Integer uAuthStatus) {
        this.uAuthStatus = uAuthStatus;
    }

    public String getuToken() {
        return uToken;
    }

    public void setuToken(String uToken) {
        this.uToken = uToken;
    }

    public String getuIdcard() {
        return uIdcard;
    }

    public void setuIdcard(String uIdcard) {
        this.uIdcard = uIdcard;
    }

    public String getuIdcardA() {
        return uIdcardA;
    }

    public void setuIdcardA(String uIdcardA) {
        this.uIdcardA = uIdcardA;
    }

    public String getuIdcardB() {
        return uIdcardB;
    }

    public void setuIdcardB(String uIdcardB) {
        this.uIdcardB = uIdcardB;
    }

    public String getuIdcardHand() {
        return uIdcardHand;
    }

    public void setuIdcardHand(String uIdcardHand) {
        this.uIdcardHand = uIdcardHand;
    }

    public Integer getuAuthType() {
        return uAuthType;
    }

    public void setuAuthType(Integer uAuthType) {
        this.uAuthType = uAuthType;
    }

    public String getuCompanyName() {
        return uCompanyName;
    }

    public void setuCompanyName(String uCompanyName) {
        this.uCompanyName = uCompanyName;
    }

    public String getuBrNumber() {
        return uBrNumber;
    }

    public void setuBrNumber(String uBrNumber) {
        this.uBrNumber = uBrNumber;
    }

    public String getuBrPicture() {
        return uBrPicture;
    }

    public void setuBrPicture(String uBrPicture) {
        this.uBrPicture = uBrPicture;
    }
}
