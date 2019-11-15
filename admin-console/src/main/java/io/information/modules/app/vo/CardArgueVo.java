package io.information.modules.app.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "已登录用户辩论状态", description = "用户支持和加入状态")
public class CardArgueVo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 是否支持或支持某一方
     */
    @ApiModelProperty(value = "是否支持或支持某一方（0：正方 1：反方 2：未加入）", name = "isSupport")
    @TableField(exist = false)
    private Integer isSupport;


    /**
     * 是否加入或加入某一方
     */
    @ApiModelProperty(value = "是否加入或加入某一方（0：正方 1：反方 2：未加入）", name = "isJoin")
    @TableField(exist = false)
    private Integer isJoin;


    public Integer getIsSupport() {
        return isSupport;
    }

    public void setIsSupport(Integer isSupport) {
        this.isSupport = isSupport;
    }

    public Integer getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Integer isJoin) {
        this.isJoin = isJoin;
    }
}
