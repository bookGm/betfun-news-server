package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "投票", description = "投票对象")
public class VoteVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "帖子id", name = "cId")
    private Long cId;
    @ApiModelProperty(value = "投票选项索引", name = "optIndexs[]")
    private Integer[] optIndexs;

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Integer[] getOptIndexs() {
        return optIndexs;
    }

    public void setOptIndexs(Integer[] optIndexs) {
        this.optIndexs = optIndexs;
    }
}
