package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "报名数据", description = "用户填写信息")
public class ActivityDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动ID", name = "actId")
    private Long actId;
    @ApiModelProperty(value = "属性键[留言]", name = "fKey")
    private String fKey;
    @ApiModelProperty(value = "属性名", name = "fName")
    private String fName;
    @ApiModelProperty(value = "属性名", name = "dValue")
    private String dValue;

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getfKey() {
        return fKey;
    }

    public void setfKey(String fKey) {
        this.fKey = fKey;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getdValue() {
        return dValue;
    }

    public void setdValue(String dValue) {
        this.dValue = dValue;
    }
}
