package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论回复表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 15:11:28
 */
@TableName("in_common_reply")
public class CommonReplyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评论或回复id
     */
    @TableId(type = IdType.INPUT)
    private Long crId;
    /**
     * id
     */
    @ApiModelProperty(value = "用户id", name = "cId", required = true)
    private Long cId;
    /**
     * 目标id（文章、帖子，活动，用户等）
     */
    private Long tId;
    /**
     * 目标类型（字典 ：文章、帖子，活动，用户等）
     */
    private Integer tType;
    /**
     * 目标名称（文章，帖子，活动，用户）
     */
    private String tName;
    /**
     * 被评论或回复id
     */
    private Long toCrId;
    /**
     * 回复或评论内容
     */
    private String crContent;
    /**
     * 评论或回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crTime;

    @ApiModelProperty(value = "根评论id", name = "crTId", required = false)
    private Long crTId;

    public Long getCrId() {
        return crId;
    }

    public void setCrId(Long crId) {
        this.crId = crId;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Long getToCrId() {
        return toCrId;
    }

    public void setToCrId(Long toCrId) {
        this.toCrId = toCrId;
    }

    public String getCrContent() {
        return crContent;
    }

    public void setCrContent(String crContent) {
        this.crContent = crContent;
    }

    public Date getCrTime() {
        return crTime;
    }

    public void setCrTime(Date crTime) {
        this.crTime = crTime;
    }

    public Long getCrTId() {
        return crTId;
    }

    public void setCrTId(Long crTId) {
        this.crTId = crTId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }
}
