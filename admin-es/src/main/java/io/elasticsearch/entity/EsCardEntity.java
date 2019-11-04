package io.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "cards", type = "card", shards = 5, replicas = 1,refreshInterval = "-1")
public class EsCardEntity implements Serializable {
    private static final Long serialVersionUID = 1L;
    /**
     * 帖子id
     */
    @Id
    @Field(type = FieldType.Long)
    private Long cId;
    /**
     * 用户id
     */
    @Field(type = FieldType.Long)
    private Long uId;
    /**
     * 节点id
     */
    @Field(type = FieldType.Long)
    private Long noId;
    /**
     * 帖子分类（字典）
     */
    @Field(type = FieldType.Integer)
    private Integer cCategory;
    /**
     * 帖子节点分类（字典）
     */
    @Field(type = FieldType.Integer)
    private Integer cNodeCategory;
    /**
     * 帖子正文
     */
    @Field(type = FieldType.Keyword)
    private String cContent;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date,format= DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cCreateTime;
    /**
     * 回帖仅作者可见（0：是  1：否）
     */
    @Field(type = FieldType.Integer)
    private Integer cHide;
    /**
     * 正方观点
     */
    @Field(type = FieldType.Keyword,index = false)
    private String caFside;
    /**
     * 反方观点
     */
    @Field(type = FieldType.Keyword,index = false)
    private String caRside;
    /**
     * 正方观点投票人ids，逗号分隔
     */
    @Field(type = FieldType.Keyword,index = false)
    private String caFsideUids;
    /**
     * 反方观点投票人ids，逗号分隔
     */
    @Field(type = FieldType.Keyword,index = false)
    private String caRsideUids;
    /**
     * 辩论结束日期
     */
    @Field(type = FieldType.Date,format= DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date caCloseTime;
    /**
     * 投票选项信息（逗号分隔）
     */
    @Field(type = FieldType.Keyword,index = false)
    private String cvInfo;
    //投票选项拆分
    private String[] cvInfoList;
    /**
     * 投票结束日期
     */
    @Field(type = FieldType.Date,format= DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cvCloseTime;
    //用户展示信息
    private EsUserEntity userEs;



    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getNoId() {
        return noId;
    }

    public void setNoId(Long noId) {
        this.noId = noId;
    }

    public Integer getcCategory() {
        return cCategory;
    }

    public void setcCategory(Integer cCategory) {
        this.cCategory = cCategory;
    }

    public Integer getcNodeCategory() {
        return cNodeCategory;
    }

    public void setcNodeCategory(Integer cNodeCategory) {
        this.cNodeCategory = cNodeCategory;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    public Integer getcHide() {
        return cHide;
    }

    public void setcHide(Integer cHide) {
        this.cHide = cHide;
    }

    public String getCaFside() {
        return caFside;
    }

    public void setCaFside(String caFside) {
        this.caFside = caFside;
    }

    public String getCaRside() {
        return caRside;
    }

    public void setCaRside(String caRside) {
        this.caRside = caRside;
    }

    public String getCaFsideUids() {
        return caFsideUids;
    }

    public void setCaFsideUids(String caFsideUids) {
        this.caFsideUids = caFsideUids;
    }

    public String getCaRsideUids() {
        return caRsideUids;
    }

    public void setCaRsideUids(String caRsideUids) {
        this.caRsideUids = caRsideUids;
    }

    public Date getCaCloseTime() {
        return caCloseTime;
    }

    public void setCaCloseTime(Date caCloseTime) {
        this.caCloseTime = caCloseTime;
    }

    public String getCvInfo() {
        return cvInfo;
    }

    public void setCvInfo(String cvInfo) {
        this.cvInfo = cvInfo;
    }

    public Date getCvCloseTime() {
        return cvCloseTime;
    }

    public void setCvCloseTime(Date cvCloseTime) {
        this.cvCloseTime = cvCloseTime;
    }

    public String[] getCvInfoList() {
        return cvInfoList;
    }

    public void setCvInfoList(String[] cvInfoList) {
        this.cvInfoList = cvInfoList;
    }

    public EsUserEntity getUserEs() {
        return userEs;
    }

    public void setUserEs(EsUserEntity userEs) {
        this.userEs = userEs;
    }

    public Date getcCreateTime() {
        return cCreateTime;
    }

    public void setcCreateTime(Date cCreateTime) {
        this.cCreateTime = cCreateTime;
    }
}
