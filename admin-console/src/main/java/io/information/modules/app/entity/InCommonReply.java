package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
     * id
     */
    @ApiModelProperty(value = "用户id", name = "cId", required = true)
    private Long cId;
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
    @ApiModelProperty(value = "目标类型名称", name = "tTypeName")
    @TableField(exist = false)
    private String tTypeName;
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
    @ApiModelProperty(value = "回复或评论时间", name = "crTime", required = false)
    private Date crTime;
    /**
     * 评论或回复时间简单格式
     */
    @ApiModelProperty(value = "评论或回复时间简单格式", name = "crSimpleTime", required = false)
    @TableField(exist = false)
    private String crSimpleTime;
    /**
     * 回复数
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "回复数", name = "crCount", required = false)
    private Integer crCount;

    @ApiModelProperty(value = "根评论id", name = "crTId", required = false)
    private Long crTId;

    /**
     * 用户名
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户名", name = "cName", required = false)
    private String cName;

    /**
     * 用户头像
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户头像", name = "cPhoto", required = false)
    private String cPhoto;

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

    public Integer getCrCount() {
        return crCount;
    }

    public void setCrCount(Integer crCount) {
        this.crCount = crCount;
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

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcPhoto() {
        return cPhoto;
    }

    public void setcPhoto(String cPhoto) {
        this.cPhoto = cPhoto;
    }

    public String getCrSimpleTime() {
        return crSimpleTime;
    }

    public void setCrSimpleTime(String crSimpleTime) {
        this.crSimpleTime = crSimpleTime;
    }

    public String gettTypeName() {
        return tTypeName;
    }

    public void settTypeName(String tTypeName) {
        this.tTypeName = tTypeName;
    }
}
