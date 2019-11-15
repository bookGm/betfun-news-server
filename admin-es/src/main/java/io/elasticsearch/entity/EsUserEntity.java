package io.elasticsearch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "users", type = "user", shards = 5, replicas = 1,refreshInterval = "-1")
public class EsUserEntity implements Serializable {
    private static final Long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @Id
    @Field(type = FieldType.Long)
    private Long uId;

    /**
     * 账号
     */
    @Field(index = false)
    private String uAccount;
    /**
     * 用户姓名
     */
    @Field(index = false)
    private String uName;
    /**
     * 密码
     */
    @Field(index = false)
    private String uPwd;
    /**
     * 盐
     */
    @Field(index = false)
    private String uSalt;
    /**
     * 用户令牌
     */
    @Field(index = false)
    private String uToken;
    /**
     * 用户昵称
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word",searchAnalyzer="ik_max_word")
    private String uNick;
    /**
     * 用户头像
     */
    @Field(type = FieldType.Keyword)
    private String uPhoto;
    /**
     * 用户简介
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word",searchAnalyzer="ik_max_word")
    private String uIntro;
    /**
     * 用户关注
     */
    @Field(index = false)
    private Integer uFocus;
    /**
     * 用户粉丝
     */
    @Field(index = false)
    private Long uFans;
    /**
     * 用户手机
     */
    @Field(type = FieldType.Integer)
    private String uPhone;
    /**
     * 认证状态（0：未通过  1：通过 ）
     */
    @Field(type = FieldType.Integer)
    private Integer uAuthStatus;
    /**
     * 认证类型（0：个人 1：媒体 2：企业）
     */
    @Field(type = FieldType.Integer)
    private Integer uAuthType;
    /**
     * 企业名称
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word",searchAnalyzer="ik_max_word")
    private String uCompanyName;
    /**
     * 身份证号
     */
    @Field(type = FieldType.Text)
    private String uIdcard;
    /**
     * 身份证正面
     */
    @Field(index = false)
    private String uIdcardA;
    /**
     * 身份证背面
     */
    @Field(index = false)
    private String uIdcardB;
    /**
     * 手持身份证照
     */
    @Field(index = false)
    private String uIdcardHand;
    /**
     * 营业注册号
     */
    @Field(index = false)
    private String uBrNumber;
    /**
     * 营业执照扫描件
     */
    @Field(index = false)
    private String uBrPicture;
    /**
     * 注册时间
     */
    @Field(type = FieldType.Date,format= DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uCreateTime;
    /**
     * 用户类型 (-1：抓取用户 0：普通用户 1：红人榜 2：黑榜  )
     */
    @Field(index = false)
    private Integer uPotential;

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getuIntro() {
        return uIntro;
    }

    public void setuIntro(String uIntro) {
        this.uIntro = uIntro;
    }

    public Integer getuAuthStatus() {
        return uAuthStatus;
    }

    public void setuAuthStatus(Integer uAuthStatus) {
        this.uAuthStatus = uAuthStatus;
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

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
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

    public String getuToken() {
        return uToken;
    }

    public void setuToken(String uToken) {
        this.uToken = uToken;
    }

    public Integer getuFocus() {
        return uFocus;
    }

    public void setuFocus(Integer uFocus) {
        this.uFocus = uFocus;
    }

    public Long getuFans() {
        return uFans;
    }

    public void setuFans(Long uFans) {
        this.uFans = uFans;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
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
