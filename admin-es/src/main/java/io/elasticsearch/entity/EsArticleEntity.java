package io.elasticsearch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "articles", type = "article", shards = 5, replicas = 1, refreshInterval = "-1")
@Data
public class EsArticleEntity implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @Field(type = FieldType.Keyword)
    private Long aId; // 文章Id

    @Field(type = FieldType.Keyword)
    private Long uId;   //用户Id

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String uName;   //用户名称

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String aTitle;  //文章标题

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String aContent; //文章内容

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String aBrief;  //文章摘要

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String aKeyword;  //文章关键字

    @Field(type = FieldType.Keyword)
    private String aCover;  //文章封面URL

    @Field(type = FieldType.Integer)
    private Integer aType;  //文章类型

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word")
    private String aSource; //文章来源

    @Field(type = FieldType.Integer)
    private Integer aStatus;    //文章状态

    @Field(type = FieldType.Keyword)
    private String aLink;   //文章链接

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
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

}
