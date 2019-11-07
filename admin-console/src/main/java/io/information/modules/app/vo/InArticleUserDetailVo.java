package io.information.modules.app.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class InArticleUserDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", name = "uPhoto", dataType = "String")
    private String uPhoto;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "uNick", dataType = "String")
    private String uNick;

    /**
     * 是否关注
     */
    @ApiModelProperty(value = "是否关注", name = "isFocus", dataType = "boolean")
    private boolean isFocused;

    /**
     * 是否点赞
     */
    @ApiModelProperty(value = "是否点赞", name = "isLiked", dataType = "boolean")
    private boolean isLiked;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数", name = "aLike", dataType = "integer")
    private Long aLike;

    /**
     * 是否收藏
     */
    @ApiModelProperty(value = "是否收藏", name = "isCollected", dataType = "boolean")
    private boolean isCollected;

    /**
     * 收藏数
     */
    @ApiModelProperty(value = "收藏数", name = "aCollect", dataType = "integer")
    private Integer aCollect;

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Long getaLike() {
        return aLike;
    }

    public void setaLike(Long aLike) {
        this.aLike = aLike;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public Integer getaCollect() {
        return aCollect;
    }

    public void setaCollect(Integer aCollect) {
        this.aCollect = aCollect;
    }
}
