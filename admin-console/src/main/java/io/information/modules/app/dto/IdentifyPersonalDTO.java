package io.information.modules.app.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 个人认证
 * </p>
 *
 * @author LX
 * @since 2019-10-29
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "个人认证", description = "个人认证")
public class IdentifyPersonalDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", name = "uIdcard", required = true)
    private String uIdcard;
    /**
     * 身份证正面
     */
    @ApiModelProperty(value = "身份证正面", name = "uIdcardA", required = true)
    private String uIdcardA;
    /**
     * 身份证背面
     */
    @ApiModelProperty(value = "身份证背面", name = "uIdcardB", required = true)
    private String uIdcardB;
    /**
     * 手持身份证照
     */
    @ApiModelProperty(value = "手持身份证照", name = "uIdcardHand", required = true)
    private String uIdcardHand;

    public String getuIdcard() {
        return uIdcard;
    }

    public void setuIdcard(String uIdcard) {
        this.uIdcard = uIdcard;
    }

    public String getuIdcardA() {
        return uIdcardA;
    }

    public void setuIdcardA(String uIdcardA) {
        this.uIdcardA = uIdcardA;
    }

    public String getuIdcardB() {
        return uIdcardB;
    }

    public void setuIdcardB(String uIdcardB) {
        this.uIdcardB = uIdcardB;
    }

    public String getuIdcardHand() {
        return uIdcardHand;
    }

    public void setuIdcardHand(String uIdcardHand) {
        this.uIdcardHand = uIdcardHand;
    }
}
