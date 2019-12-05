package io.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "users", type = "user", shards = 5, replicas = 1, refreshInterval = "-1")
@Data
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
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String uNick;
    /**
     * 用户头像
     */
    @Field(type = FieldType.Keyword)
    private String uPhoto;
    /**
     * 用户简介
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
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
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
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
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uCreateTime;
    /**
     * 用户类型 (-1：抓取用户 0：普通用户 1：红人榜 2：黑榜  )
     */
    @Field(index = false)
    private Integer uPotential;

    //文章数
    @Field(index = false)
    private Integer articleNumber;

    //浏览数
    @Field(index = false)
    private Long readNumber;

    @Field(index = false)
    //获赞数
    private Long likeNUmber;
}
