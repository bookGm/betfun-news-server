package io.information.modules.app.vo;

import io.information.modules.app.entity.InNewsFlash;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "快讯详情", description = "快讯详情对象")
public class FlashVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否有上一条
     */
    @ApiModelProperty(value = "是否有上一条(true：有  false：没有)", name = "gFlash")
    private Boolean isGFlash;

    /**
     * 上一条
     */
    @ApiModelProperty(value = "上一条", name = "upFlash")
    private InNewsFlash upFlash;


    /**
     * 当前快讯详情
     */
    @ApiModelProperty(value = "当前快讯详情", name = "flash")
    private InNewsFlash flash;

    /**
     * 是否有下一条
     */
    @ApiModelProperty(value = "是否有下一条(true：有  false：没有)", name = "tFlash")
    private Boolean isTFlash;

    /**
     * 下一条
     */
    @ApiModelProperty(value = "下一条", name = "deFlash")
    private InNewsFlash deFlash;


    public InNewsFlash getUpFlash() {
        return upFlash;
    }

    public void setUpFlash(InNewsFlash upFlash) {
        this.upFlash = upFlash;
    }

    public InNewsFlash getDeFlash() {
        return deFlash;
    }

    public void setDeFlash(InNewsFlash deFlash) {
        this.deFlash = deFlash;
    }

    public InNewsFlash getFlash() {
        return flash;
    }

    public void setFlash(InNewsFlash flash) {
        this.flash = flash;
    }

    public Boolean getGFlash() {
        return isGFlash;
    }

    public void setGFlash(Boolean GFlash) {
        isGFlash = GFlash;
    }

    public Boolean getTFlash() {
        return isTFlash;
    }

    public void setTFlash(Boolean TFlash) {
        isTFlash = TFlash;
    }
}
