package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "用户相关数量", description = "用户相关数量对象")
public class UserBoolVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "uNick")
    private String uNick;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", name = "uPhoto")
    private String uPhoto;

    /**
     * 是否点赞
     */
    @ApiModelProperty(value = "是否点赞", name = "isLike")
    private Boolean isLike;

    /**
     * 点赞数量
     */
    @ApiModelProperty(value = "点赞数量", name = "likeNumber")
    private Integer likeNumber;

    /**
     * 是否收藏
     */
    @ApiModelProperty(value = "是否收藏", name = "isCollect")
    private Boolean isCollect;

    /**
     * 收藏数量
     */
    @ApiModelProperty(value = "收藏数量", name = "collectNumber")
    private Integer collectNumber;

    /**
     * 评论数量
     */
    @ApiModelProperty(value = "评论数量", name = "replyNumber")
    private Integer replyNumber;

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    public Boolean getCollect() {
        return isCollect;
    }

    public void setCollect(Boolean collect) {
        isCollect = collect;
    }

    public Integer getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(Integer replyNumber) {
        this.replyNumber = replyNumber;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Integer getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(Integer collectNumber) {
        this.collectNumber = collectNumber;
    }
}
