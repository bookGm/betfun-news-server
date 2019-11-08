package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "帖子部分信息", description = "帖子部分信息")
public class CardBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "帖子ID", name = "cId", required = true)
    private Long cId;

    @ApiModelProperty(value = "帖子标题", name = "cTitle", required = true)
    private String cTitle;

    @ApiModelProperty(value = "帖子点赞数", name = "cLike", required = true)
    private Long cLike;

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public Long getcLike() {
        return cLike;
    }

    public void setcLike(Long cLike) {
        this.cLike = cLike;
    }
}
