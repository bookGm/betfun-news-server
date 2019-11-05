package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论回复表
 */
@TableName("in_common_reply")
@Api(value = "评论回复表", description = "评论回复表信息")
public class InCommonReply implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评论或回复id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(hidden = true)
    private Long crId;
    /**
     * 目标id（文章、帖子，活动，用户等）
     */
    @ApiModelProperty(value = "目标id（文章、帖子，活动，用户等）", name = "tId", required = true)
    private Long tId;
    /**
     * 目标类型（字典 ：文章、帖子，活动，用户等）
     */
    @ApiModelProperty(value = "目标类型（字典 ：文章、帖子，活动，用户等）", name = "tType", required = true)
    private Integer tType;
    /**
     * 目标名称（文章，帖子，活动，用户）
     */
    @ApiModelProperty(value = "目标名称（文章，帖子，活动，用户）", name = "tName", required = true)
    private String tName;
    /**
     * 被评论或回复id
     */
    @ApiModelProperty(value = "被评论或回复id", name = "toCrId", required = true)
    private Long toCrId;
    /**
     * 回复或评论内容
     */
    @ApiModelProperty(value = "回复或评论内容", name = "crContent", required = true)
    private String crContent;
    /**
     * 评论或回复时间
     */
    @ApiModelProperty(hidden = true)
    private Date crTime;

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
}
