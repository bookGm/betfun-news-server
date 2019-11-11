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
