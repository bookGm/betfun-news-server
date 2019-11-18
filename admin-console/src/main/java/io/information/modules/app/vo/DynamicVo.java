package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "最新动态数据", description = "动态信息")
public class DynamicVo implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户ID", name = "uId")
    private Long uId;

    @ApiModelProperty(value = "用户名称", name = "uName")
    private String uName;

    @ApiModelProperty(value = "目标ID", name = "tId")
    private Long tId;

    @ApiModelProperty(value = "目标名称", name = "tName")
    private String tName;

    @ApiModelProperty(value = "创建简单时间", name = "simpleDate")
    private String simpleDate;

    @ApiModelProperty(value = "发布 或 评论", name = "dType")
    private String dType;


    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getSimpleDate() {
        return simpleDate;
    }

    public void setSimpleDate(String simpleDate) {
        this.simpleDate = simpleDate;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }
}
