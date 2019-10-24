package io.information.modules.app.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 * 资讯帖子前台字段
 *
 */
@ApiModel(value = "帖子", description = "所有帖子字段")
public class InCard implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 基础信息
     */
    @ApiModelProperty(value = "帖子基础信息", name = "base", required = true)
    private InCardBase base;
    /**
     * 辩论信息
     */
    @ApiModelProperty(value = "帖子辩论信息", name = "argue", required = false)
    private InCardArgue argue;
    /**
     * 投票信息
     */
    @ApiModelProperty(value = "帖子投票信息", name = "vote", required = false)
    private InCardVote vote;

    public InCardBase getBase() {
        return base;
    }

    public void setBase(InCardBase base) {
        this.base = base;
    }

    public InCardArgue getArgue() {
        return argue;
    }

    public void setArgue(InCardArgue argue) {
        this.argue = argue;
    }

    public InCardVote getVote() {
        return vote;
    }

    public void setVote(InCardVote vote) {
        this.vote = vote;
    }
}
