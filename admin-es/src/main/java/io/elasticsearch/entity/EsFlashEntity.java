package io.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "flashs", type = "flash", shards = 5, replicas = 1, refreshInterval = "-1")
public class EsFlashEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 快讯id
     */
    @Id
    @Field(type = FieldType.Long)
    private Long nId;
    /**
     * 快讯标题
     */
    @Field(type = FieldType.Keyword)
    private String nTitle;
    /**
     * 快讯摘要
     */
    @Field(type = FieldType.Keyword)
    private String nBrief;

    /**
     * 快讯内容
     */
    @Field(type = FieldType.Keyword)
    private String nContent;
    /**
     * 利好
     */
    @Field(type = FieldType.Integer)
    private Integer nBull;
    /**
     * 利空
     */
    @Field(type = FieldType.Integer)
    private Integer nBad;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date,format= DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nCreateTime;

    public Long getnId() {
        return nId;
    }

    public void setnId(Long nId) {
        this.nId = nId;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnBrief() {
        return nBrief;
    }

    public void setnBrief(String nBrief) {
        this.nBrief = nBrief;
    }

    public String getnContent() {
        return nContent;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    public Integer getnBull() {
        return nBull;
    }

    public void setnBull(Integer nBull) {
        this.nBull = nBull;
    }

    public Integer getnBad() {
        return nBad;
    }

    public void setnBad(Integer nBad) {
        this.nBad = nBad;
    }

    public Date getnCreateTime() {
        return nCreateTime;
    }

    public void setnCreateTime(Date nCreateTime) {
        this.nCreateTime = nCreateTime;
    }
}
