package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "主页文章展示数据", description = "文章部分信息")
public class ArticleBannerVo implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "文章ID",name = "aId")
    private Long aId;

    @ApiModelProperty(value = "文章封面URL",name = "aCover")
    private String aCover;


    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public String getaCover() {
        return aCover;
    }

    public void setaCover(String aCover) {
        this.aCover = aCover;
    }
}
