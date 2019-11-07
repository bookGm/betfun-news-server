package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "社区最新动态", description = "社区最新动态")
public class NewDynamicVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帖子
     */
    @ApiModelProperty(value = "帖子信息[发布]", name = "dynamicCardVo")
    private DynamicCardVo dynamicCardVo;

    /**
     * 评论
     */
    @ApiModelProperty(value = "评论信息[回复]", name = "dynamicReplyVo")
    private DynamicReplyVo dynamicReplyVo;

    public DynamicCardVo getDynamicCardVo() {
        return dynamicCardVo;
    }

    public void setDynamicCardVo(DynamicCardVo dynamicCardVo) {
        this.dynamicCardVo = dynamicCardVo;
    }

    public DynamicReplyVo getDynamicReplyVo() {
        return dynamicReplyVo;
    }

    public void setDynamicReplyVo(DynamicReplyVo dynamicReplyVo) {
        this.dynamicReplyVo = dynamicReplyVo;
    }
}
