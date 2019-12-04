package io.information.modules.app.vo;

import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "标签文章", description = "标签文章对象")
public class TagArticleVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签数据", name = "tag")
    private InTag tag;
    /**
     * 文章列表
     */
    @ApiModelProperty(value = "文章数据集合", name = "articleList")
    private List<InArticle> articleList;

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


    public TagArticleVo() {

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
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public InTag getTag() {
        return tag;
    }

    public void setTag(InTag tag) {
        this.tag = tag;
    }

    public List<InArticle> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<InArticle> articleList) {
        this.articleList = articleList;
    }
}
