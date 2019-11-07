package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "文章部分信息", description = "文章部分信息")
public class ArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID", name = "aId", required = true)
    private Long aId;

    @ApiModelProperty(value = "文章标题", name = "aTitle", required = true)
    private String aTitle;


    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public String getaTitle() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
    }
}
