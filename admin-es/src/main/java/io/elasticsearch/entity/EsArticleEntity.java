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

@Document(indexName = "articles", type = "article", shards = 5, replicas = 1, refreshInterval = "-1")
public class EsArticleEntity implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @Field(type = FieldType.Keyword)
    private Long aId; // 文章Id

    @Field(type = FieldType.Keyword)
    private Long uId;   //用户Id
    //ik_max_word  细粒度搜索
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String uName;   //用户名称

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String aTitle;  //文章标题

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String aContent; //文章内容

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String aBrief;  //文章摘要

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String aKeyword;  //文章关键字

    @Field(index = false, type = FieldType.Keyword)
    private String aCover;  //文章封面URL

    @Field(index = false, type = FieldType.Integer)
    private Integer aType;  //文章类型

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String aSource; //文章来源

    @Field(index = false, type = FieldType.Integer)
    private Integer aStatus;    //文章状态

    @Field(index = false, type = FieldType.Keyword)
    private String aLink;   //文章链接

    @Field(index = false, type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date aCreateTime;// 创建时间

    @TableField(exist = false)
    private Long aLike; //点赞数

    @TableField(exist = false)
    private Integer aCollect; //收藏数

    @TableField(exist = false)
    private Long aCritic; //评论数

    @TableField(exist = false)
    private Long aReadNumber; //浏览量

    @TableField(exist = false)
    private String aSimpleTime; //简单时间

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

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

    public String getaTitle() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent;
    }

    public String getaBrief() {
        return aBrief;
    }

    public void setaBrief(String aBrief) {
        this.aBrief = aBrief;
    }

    public String getaKeyword() {
        return aKeyword;
    }

    public void setaKeyword(String aKeyword) {
        this.aKeyword = aKeyword;
    }

    public String getaCover() {
        return aCover;
    }

    public void setaCover(String aCover) {
        this.aCover = aCover;
    }

    public Integer getaType() {
        return aType;
    }

    public void setaType(Integer aType) {
        this.aType = aType;
    }

    public String getaSource() {
        return aSource;
    }

    public void setaSource(String aSource) {
        this.aSource = aSource;
    }

    public Integer getaStatus() {
        return aStatus;
    }

    public void setaStatus(Integer aStatus) {
        this.aStatus = aStatus;
    }

    public String getaLink() {
        return aLink;
    }

    public void setaLink(String aLink) {
        this.aLink = aLink;
    }

    public Date getaCreateTime() {
        return aCreateTime;
    }

    public void setaCreateTime(Date aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    public Long getaLike() {
        return aLike;
    }

    public void setaLike(Long aLike) {
        this.aLike = aLike;
    }

    public Integer getaCollect() {
        return aCollect;
    }

    public void setaCollect(Integer aCollect) {
        this.aCollect = aCollect;
    }

    public Long getaCritic() {
        return aCritic;
    }

    public void setaCritic(Long aCritic) {
        this.aCritic = aCritic;
    }

    public Long getaReadNumber() {
        return aReadNumber;
    }

    public void setaReadNumber(Long aReadNumber) {
        this.aReadNumber = aReadNumber;
    }

    public String getaSimpleTime() {
        return aSimpleTime;
    }

    public void setaSimpleTime(String aSimpleTime) {
        this.aSimpleTime = aSimpleTime;
    }
}
