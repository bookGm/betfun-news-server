package io.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

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
     * 用户昵称
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word",searchAnalyzer="ik_max_word")
    private String uNick;
    /**
     * 用户昵称
     */
    @Field(type = FieldType.Keyword)
    private String uPhoto;
    /**
     * 用户简介
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word",searchAnalyzer="ik_max_word")
    private String uIntro;
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
}
