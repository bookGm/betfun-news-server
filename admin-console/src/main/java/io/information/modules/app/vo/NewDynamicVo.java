package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "社区最新动态", description = "社区最新动态")
public class NewDynamicVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帖子
     */
    @ApiModelProperty(value = "帖子信息[发布]", name = "dynamicCardVo")
    private List<DynamicCardVo> dynamicCardVos;

    /**
     * 评论
     */
    @ApiModelProperty(value = "评论信息[回复]", name = "dynamicReplyVo")
    private List<DynamicReplyVo> dynamicReplyVos;

    public List<DynamicCardVo> getDynamicCardVos() {
        return dynamicCardVos;
    }

    public void setDynamicCardVos(List<DynamicCardVo> dynamicCardVos) {
        this.dynamicCardVos = dynamicCardVos;
    }

    public List<DynamicReplyVo> getDynamicReplyVos() {
        return dynamicReplyVos;
    }

    public void setDynamicReplyVos(List<DynamicReplyVo> dynamicReplyVos) {
        this.dynamicReplyVos = dynamicReplyVos;
    }
}
