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

@Document(indexName = "flashs", type = "flash", shards = 5, replicas = 1, refreshInterval = "-1")
@Data
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
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String nTitle;
    /**
     * 快讯摘要
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String nBrief;

    /**
     * 快讯内容
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
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
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nCreateTime;
}
