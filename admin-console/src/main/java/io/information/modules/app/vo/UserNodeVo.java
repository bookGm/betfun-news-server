package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "专栏社区数据", description = "用户信息")
public class UserNodeVo implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户ID", name = "uId")
    private Long uId;
    @ApiModelProperty(value = "用户昵称", name = "uNick")
    private String uNick;

    @ApiModelProperty(value = "用户头像", name = "uPhoto")
    private String uPhoto;

    @ApiModelProperty(value = "用户简介", name = "uIntro")
    private String uIntro;

    @ApiModelProperty(value = "文章数", name = "articleNumber")
    private Integer articleNumber;

    @ApiModelProperty(value = "浏览量", name = "readNumber")
    private Long readNumber;

    @ApiModelProperty(value = "点赞数", name = "likeNumber")
    private Long likeNumber;


    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

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

    public String getuIntro() {
        return uIntro;
    }

    public void setuIntro(String uIntro) {
        this.uIntro = uIntro;
    }

    public Integer getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(Integer articleNumber) {
        this.articleNumber = articleNumber;
    }

    public Long getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(Long readNumber) {
        this.readNumber = readNumber;
    }

    public Long getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Long likeNumber) {
        this.likeNumber = likeNumber;
    }
}
