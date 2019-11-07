package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "最新动态 -- 评论信息", description = "最新动态 -- 评论信息")
public class DynamicReplyVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "uNick")
    private String uNick;

    /**
     * 评论ID
     */
    @ApiModelProperty(value = "评论ID", name = "tId")
    private String tId;

    /**
     * 评论目标名称
     */
    @ApiModelProperty(value = "评论目标名称", name = "tName")
    private String tName;

    /**
     * 评论创建时间
     */
    @ApiModelProperty(value = "评论时间", name = "crTime")
    private String crTime;


    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getCrTime() {
        return crTime;
    }

    public void setCrTime(String crTime) {
        this.crTime = crTime;
    }
}
