package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资讯用户表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@TableName("in_user")
@ApiModel(value = "资讯用户", description = "资讯用户对象")
public class InUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "u_id", type = IdType.INPUT)
    @ApiModelProperty(value = "文章标题", name = "aTitle", required = true)
    private Long uId;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", name = "uAccount", required = true)
    private String uAccount;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名", name = "uName", required = true)
    private String uName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "uPwd", required = true)
    private String uPwd;
    /**
     * 盐
     */
    @ApiModelProperty(value = "盐", name = "uSalt", hidden = true)
    private String uSalt;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "uNick", required = true)
    private String uNick;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", name = "uPhoto", required = false)
    private String uPhoto;

    /**
     * 用户手机
     */
    @ApiModelProperty(value = "用户手机", name = "uPhone", required = true)
    private String uPhone;

    /**
     * 用户简介
     */
    @ApiModelProperty(value = "用户简介", name = "uIntro", required = false)
    private String uIntro;

    /**
     * 用户粉丝
     */
    @ApiModelProperty(value = "用户粉丝", name = "uFans", required = false)
    private Long uFans;

    /**
     * 用户关注
     */
    @ApiModelProperty(value = "用户关注", name = "uFocus", required = false)
    private Integer uFocus;

    /**
     * 认证状态（0：未通过  1：通过 ）
     */
    @ApiModelProperty(value = "认证状态（0：未通过  1：通过 ）", name = "uAuthStatus", required = false)
    private Integer uAuthStatus;
    /**
     * 认证类型（0：个人 1：媒体 2：企业）
     */
    @ApiModelProperty(value = "认证类型（0：个人 1：媒体 2：企业）", name = "uAuthType", required = false)
    private Integer uAuthType;
    /**
     * 用户令牌
     */
    @ApiModelProperty(value = "用户令牌", name = "uToken", hidden = true)
    private String uToken;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", name = "uIdcard", required = false)
    private String uIdcard;
    /**
     * 身份证正面
     */
    @ApiModelProperty(value = "身份证正面", name = "uIdcardA", required = false)
    private String uIdcardA;
    /**
     * 身份证背面
     */
    @ApiModelProperty(value = "身份证背面", name = "uIdcardB", required = false)
    private String uIdcardB;
    /**
     * 手持身份证照
     */
    @ApiModelProperty(value = "手持身份证照", name = "uIdcardHand", required = false)
    private String uIdcardHand;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称", name = "uCompanyName", required = false)
    private String uCompanyName;
    /**
     * 营业注册号
     */
    @ApiModelProperty(value = "营业注册号", name = "uBrNumber", required = false)
    private String uBrNumber;
    /**
     * 营业执照扫描件
     */
    @ApiModelProperty(value = "营业执照扫描件", name = "uBrPicture", required = false)
    private String uBrPicture;
    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间", name = "uCreateTime", required = false)
    private Date uCreateTime;
    /**
     * 用户位置 (0：普通用户 1：红人榜 2：黑榜  )
     */
    @ApiModelProperty(value = "用户位置 (0：普通用户 1：红人榜 2：黑榜  )", name = "uPotential", required = false)
    private Integer uPotential;


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

    public Date getuCreateTime() {
        return uCreateTime;
    }

    public void setuCreateTime(Date uCreateTime) {
        this.uCreateTime = uCreateTime;
    }

    public Integer getuPotential() {
        return uPotential;
    }

    public void setuPotential(Integer uPotential) {
        this.uPotential = uPotential;
    }
}
