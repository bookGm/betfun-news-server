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
 * 资讯文章表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@TableName("in_article")
@ApiModel(value = "文章", description = "文章对象")
public class InArticle implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 文章id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(hidden = true)
    private Long aId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户ID", name = "uId")
    private Long uId;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", name = "uName")
    private String uName;

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题", name = "aTitle", required = true)
    private String aTitle;
    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容", name = "aContent", required = true)
    private String aContent;
    /**
     * 文章摘要
     */
    @ApiModelProperty(value = "文章摘要", name = "aBrief", required = true)
    private String aBrief;
    /**
     * 文章关键字（例：1,2,3）
     */
    @ApiModelProperty(value = "文章关键词（标签）", name = "aKeyword", required = true)
    private String aKeyword;
    /**
     * 文章封面URL
     */
    @ApiModelProperty(value = "文章封面图", name = "aCover", required = true)
    private String aCover;
    /**
     * 文章类型（字典）
     */
    @ApiModelProperty(value = "文章类型", name = "aType", required = true, example = "0")
    private Integer aType;
    /**
     * 文章来源（非转载类型，此字段为空）
     */
    @ApiModelProperty(value = "文章来源（非转载类型，不用传此字段）", name = "aSource", required = false)
    private String aSource;
    /**
     * 文章链接（非转载类型，此字段为空）
     */
    @ApiModelProperty(value = "文章链接（非转载类型，不用传此字段）", name = "aLink", required = false)
    private String aLink;
    /**
     * 点赞数
     */
    @ApiModelProperty(value = "文章点赞数", name = "aLike")
    private Long aLike;
    /**
     * 收藏数
     */
    @ApiModelProperty(value = "文章收藏数", name = "aCollect")
    private Integer aCollect;
    /**
     * 评论数
     */
    @ApiModelProperty(value = "文章评论数", name = "aCritic")
    private Long aCritic;
    /**
     * 文章状态（0：草稿箱，1：审核中，2：已发布）
     */
    @ApiModelProperty(value = "文章状态（0：草稿箱，1：审核中，2：已发布）", name = "aStatus", required = true)
    private Integer aStatus;
    /**
     * 浏览量
     */
    private Long aReadNumber;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date aCreateTime;
    /**
     * 简单时间
     */
    @ApiModelProperty(value = "文章创建时间", name = "aSimpleTime")
    @TableField(exist = false)
    private String aSimpleTime;

    /**
     * 是否主页展示
     */
    @ApiModelProperty(value = "是否主页展示(0：否 1：是)", name = "aBanner")
    private Integer aBanner;

    /**
     * 是否存在
     */
    @ApiModelProperty(value = "活动是否存在（0：存在 1：不存在）", name = "isExist")
    @TableField(exist = false)
    private Integer isExist;


    public Integer getExist() {
        return isExist;
    }

    public void setExist(Integer exist) {
        isExist = exist;
    }

    public Integer getaBanner() {
        return aBanner;
    }

    public void setaBanner(Integer aBanner) {
        this.aBanner = aBanner;
    }

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

    public String getaKeyword() {
        return aKeyword;
    }

    public void setaKeyword(String aKeyword) {
        this.aKeyword = aKeyword;
    }

    public String getaCover() {
        return aCover;
    }

    public void setaCover(String aCover) {
        this.aCover = aCover;
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

    public Long getaLike() {
        return aLike;
    }

    public void setaLike(Long aLike) {
        this.aLike = aLike;
    }

    public Integer getaCollect() {
        return aCollect;
    }

    public void setaCollect(Integer aCollect) {
        this.aCollect = aCollect;
    }

    public Long getaCritic() {
        return aCritic;
    }

    public void setaCritic(Long aCritic) {
        this.aCritic = aCritic;
    }

    public Date getaCreateTime() {
        return aCreateTime;
    }

    public void setaCreateTime(Date aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    public Integer getaStatus() {
        return aStatus;
    }

    public void setaStatus(Integer aStatus) {
        this.aStatus = aStatus;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getaSimpleTime() {
        return aSimpleTime;
    }

    public void setaSimpleTime(String aSimpleTime) {
        this.aSimpleTime = aSimpleTime;
    }

    public Long getaReadNumber() {
        return aReadNumber;
    }

    public void setaReadNumber(Long aReadNumber) {
        this.aReadNumber = aReadNumber;
    }
}
