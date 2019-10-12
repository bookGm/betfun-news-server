package io.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "articles", type = "arts", shards = 1, replicas = 1)
public class EsArticleEntity implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @Field(type = FieldType.Keyword)
    private Long aId; // 文章Id
    @Field(type = FieldType.Keyword)
    private Long uId;   //用户Id
    @Field(type = FieldType.Keyword)
    private String aTitle;  //文章标题
    @Field(type = FieldType.Keyword)
    private String aContent;    //文章内容
    @Field(type = FieldType.Keyword)
    private String aBrief;  //文章摘要
    @Field(type = FieldType.Keyword)
    private Integer aType;  //文章类型
    @Field(type = FieldType.Keyword)
    private String aSource; //文章来源
    @Field(type = FieldType.Keyword)
    private String aLink;   //文章链接
//    private Long aLike; //点赞数
//    private Integer aCollect;   //收藏数
//    private Long aCritic;   //评论数
    @Field(type = FieldType.Keyword)
    private Date aCreateTime;// 创建时间


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
}
