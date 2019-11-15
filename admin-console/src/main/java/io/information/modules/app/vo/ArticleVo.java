package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "文章部分信息", description = "文章部分信息")
public class ArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID", name = "aId", required = true)
    private Long aId;

    @ApiModelProperty(value = "文章标题", name = "aTitle", required = true)
    private String aTitle;

    @ApiModelProperty(value = "文章封面URL", name = "aCover", required = true)
    private String aCover;

    @ApiModelProperty(value = "文章创建时间", name = "aCreateTime", required = true)
    private Date aCreateTime;

    @ApiModelProperty(value = "文章创建简单时间", name = "aSimpleTime", required = true)
    private String aSimpleTime;

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

    public String getaCover() {
        return aCover;
    }

    public void setaCover(String aCover) {
        this.aCover = aCover;
    }

    public Date getaCreateTime() {
        return aCreateTime;
    }

    public void setaCreateTime(Date aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    public String getaSimpleTime() {
        return aSimpleTime;
    }

    public void setaSimpleTime(String aSimpleTime) {
        this.aSimpleTime = aSimpleTime;
    }
}
