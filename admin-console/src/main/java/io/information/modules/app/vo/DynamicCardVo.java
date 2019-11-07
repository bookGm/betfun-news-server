package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "最新动态 -- 帖子信息", description = "最新动态 -- 帖子信息")
public class DynamicCardVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "uNick")
    private String uNick;

    /**
     * 帖子ID
     */
    @ApiModelProperty(value = "帖子ID", name = "cId")
    private String cId;

    /**
     * 帖子标题
     */
    @ApiModelProperty(value = "帖子标题", name = "cTitle")
    private String cTitle;

    /**
     * 帖子发布时间
     */
    @ApiModelProperty(value = "帖子发布时间", name = "cCreateTime")
    private String cCreateTime;

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public String getcCreateTime() {
        return cCreateTime;
    }

    public void setcCreateTime(String cCreateTime) {
        this.cCreateTime = cCreateTime;
    }
}
