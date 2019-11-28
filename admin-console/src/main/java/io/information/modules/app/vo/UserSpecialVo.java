package io.information.modules.app.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.information.modules.app.entity.InArticle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "专栏详情", description = "专栏详情对象")
public class UserSpecialVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "u_id", type = IdType.INPUT)
    @ApiModelProperty(value = "用户ID", name = "uId", required = true)
    private Long uId;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "uNick", required = true)
    private String uNick;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", name = "uPhoto", required = false)
    private String uPhoto;

    /**
     * 用户简介
     */
    @ApiModelProperty(value = "用户简介", name = "uIntro", required = false)
    private String uIntro;

    /**
     * 认证类型（0：个人 1：媒体 2：企业）
     */
    @ApiModelProperty(value = "认证类型（0：个人 1：媒体 2：企业）", name = "uAuthType", required = false)
    private Integer uAuthType;

    /**
     * 用户粉丝
     */
    @ApiModelProperty(value = "用户粉丝", name = "uFans", required = false)
    private Long uFans;

    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量", name = "readNumber", required = false)
    private Long readNumber;

    /**
     * 获赞数
     */
    @ApiModelProperty(value = "获赞数", name = "likeNumber", required = false)
    private Long likeNumber;

    /**
     * 文章信息
     */
    @ApiModelProperty(value = "文章信息", name = "articles", required = false)
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

    public Integer getuAuthType() {
        return uAuthType;
    }

    public void setuAuthType(Integer uAuthType) {
        this.uAuthType = uAuthType;
    }

    public Long getuFans() {
        return uFans;
    }

    public void setuFans(Long uFans) {
        this.uFans = uFans;
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
