package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 资讯用户表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "u_id", type = IdType.INPUT)
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


    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
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
}
