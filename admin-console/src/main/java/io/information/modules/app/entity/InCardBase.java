package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资讯帖子基础表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@TableName("in_card_base")
@ApiModel(value = "普通帖", description = "普通帖")
public class InCardBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(hidden = true)
    private Long cId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", name = "uId")
    private Long uId;

    /**
     * 用户昵称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户昵称", name = "uNick", required = false)
    private String uNick;
    /**
     * 用户昵称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户头像", name = "uPhoto", required = false)
    private String uPhoto;


    /**
     * 节点id
     */
    @ApiModelProperty(value = "节点id", name = "noId")
    private Long noId;

    /**
     * 帖子分类（字典）
     */
    @ApiModelProperty(value = "帖子分类(0:普通 1:辩论 2:投票帖)", name = "cCategory", required = true)
    private Integer cCategory;

    /**
     * 帖子节点分类（字典）
     */
    @ApiModelProperty(value = "节点分类", name = "cNodeCategory", required = true)
    private Integer cNodeCategory;

    /**
     * 帖子节点分类
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "节点分类值", name = "cNodeCategoryValue", required = false)
    private String cNodeCategoryValue;

    /**
     * 帖子标题
     */
    @ApiModelProperty(value = "标题", name = "cTitle", required = true)
    private String cTitle;
    /**
     * 帖子正文
     */
    @ApiModelProperty(value = "正文", name = "cContent", required = true)
    private String cContent;

    /**
     * 回帖仅作者可见（0：是  1：否）
     */
    @ApiModelProperty(value = "回帖仅作者可见（0：是  1：否）", name = "cHide", required = true)
    private Integer cHide;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数", name = "cLike")
    private Long cLike;

    /**
     * 收藏数
     */
    @ApiModelProperty(value = "收藏数", name = "cCollect")
    private Integer cCollect;

    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数", name = "cCritic")
    private Long cCritic;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "cCreateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cCreateTime;

    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量", name = "cReadNumber")
    private Long cReadNumber;

    /**
     * 简单时间
     */
    @TableField(exist = false)
    private String cSimpleTime;


    public Long getcReadNumber() {
        return cReadNumber;
    }

    public void setcReadNumber(Long cReadNumber) {
        this.cReadNumber = cReadNumber;
    }

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

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public Long getcLike() {
        return cLike;
    }

    public void setcLike(Long cLike) {
        this.cLike = cLike;
    }

    public Integer getcCollect() {
        return cCollect;
    }

    public void setcCollect(Integer cCollect) {
        this.cCollect = cCollect;
    }

    public Long getcCritic() {
        return cCritic;
    }

    public void setcCritic(Long cCritic) {
        this.cCritic = cCritic;
    }

    public Date getcCreateTime() {
        return cCreateTime;
    }

    public void setcCreateTime(Date cCreateTime) {
        this.cCreateTime = cCreateTime;
    }

    public String getcSimpleTime() {
        return cSimpleTime;
    }

    public void setcSimpleTime(String cSimpleTime) {
        this.cSimpleTime = cSimpleTime;
    }

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getcNodeCategoryValue() {
        return cNodeCategoryValue;
    }

    public void setcNodeCategoryValue(String cNodeCategoryValue) {
        this.cNodeCategoryValue = cNodeCategoryValue;
    }

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }
}
