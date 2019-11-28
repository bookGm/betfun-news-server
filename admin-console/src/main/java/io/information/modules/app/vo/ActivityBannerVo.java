package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "活动轮播图", description = "活动部分数据")
public class ActivityBannerVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动ID", name = "actId", required = false)
    private Long actId;

    /**
     * 活动封面URL
     */
    @ApiModelProperty(value = "活动封面URL", name = "actCover", required = false)
    private String actCover;

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getActCover() {
        return actCover;
    }

    public void setActCover(String actCover) {
        this.actCover = actCover;
    }
}
