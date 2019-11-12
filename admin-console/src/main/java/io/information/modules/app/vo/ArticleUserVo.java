package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "社区 -- 用户文章数据", description = "用户信息和文章信息")
public class ArticleUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", name = "uId", required = true)
    private Long uId;

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
     * 用户简介
     */
    @ApiModelProperty(value = "用户简介", name = "uIntro")
    private String uIntro;

    /**
     * 用户文章数量
     */
    @ApiModelProperty(value = "用户文章数量", name = "articleNumber")
    private Integer articleNumber;

    /**
     * 用户文章推荐信息
     */
    @ApiModelProperty(value = "用户文章信息", name = "cardBaseVos")
    private List<ArticleVo> articleVos;


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

    public List<ArticleVo> getArticleVos() {
        return articleVos;
    }

    public void setArticleVos(List<ArticleVo> articleVos) {
        this.articleVos = articleVos;
    }
}
