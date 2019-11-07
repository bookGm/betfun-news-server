package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "节点推荐", description = "节点推荐对象")
public class NodeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "节点ID", name = "noId")
    private Long noId;

    @ApiModelProperty(value = "节点名称", name = "noName")
    private String noName;

    @ApiModelProperty(value = "节点图标", name = "noPhoto")
    private String noPhoto;


    public Long getNoId() {
        return noId;
    }

    public void setNoId(Long noId) {
        this.noId = noId;
    }

    public String getNoName() {
        return noName;
    }

    public void setNoName(String noName) {
        this.noName = noName;
    }

    public String getNoPhoto() {
        return noPhoto;
    }

    public void setNoPhoto(String noPhoto) {
        this.noPhoto = noPhoto;
    }
}
