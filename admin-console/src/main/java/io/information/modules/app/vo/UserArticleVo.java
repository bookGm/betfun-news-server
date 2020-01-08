package io.information.modules.app.vo;

import io.information.modules.app.entity.InArticle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "人物社区 -- 用户和文章数据", description = "用户信息和文章信息")
public class UserArticleVo implements Serializable {
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
     * 用户粉丝数量
     */
    @ApiModelProperty(value = "用户粉丝数量", name = "uFans")
    private Long uFans;

    /**
     * 用户文章浏览量
     */
    @ApiModelProperty(value = "用户文章浏览量", name = "readNumber")
    private Long readNumber;

    /**
     * 用户获赞数量
     */
    @ApiModelProperty(value = "用户获赞数量", name = "likeNumber")
    private Long likeNumber;

    /**
     * 用户文章信息
     */
    @ApiModelProperty(value = "用户文章信息", name = "articles")
    private List<InArticle> articles;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数", name = "totalCount", required = true)
    private int totalCount;
    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数", name = "pageSize", required = true)
    private int pageSize;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", name = "totalPage", required = true)
    private int totalPage;
    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数", name = "currPage", required = true)
    private int currPage;

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

    public Long getuFans() {
        return uFans;
    }

    public void setuFans(Long uFans) {
        this.uFans = uFans;
    }

    public List<InArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<InArticle> articles) {
        this.articles = articles;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }
}
