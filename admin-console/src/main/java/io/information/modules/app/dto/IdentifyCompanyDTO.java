package io.information.modules.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 媒体或企业认证
 * </p>
 *
 * @author LX
 * @since 2019-10-29
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "媒体或企业认证", description = "媒体或企业认证")
public class IdentifyCompanyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称", name = "uCompanyName", required = true)
    private String uCompanyName;
    /**
     * 营业注册号
     */
    @ApiModelProperty(value = "营业注册号", name = "uBrNumber", required = true)
    private String uBrNumber;
    /**
     * 营业执照扫描件
     */
    @ApiModelProperty(value = "营业执照扫描件", name = "uBrPicture", required = true)
    private String uBrPicture;

    public String getuCompanyName() {
        return uCompanyName;
    }

    public void setuCompanyName(String uCompanyName) {
        this.uCompanyName = uCompanyName;
    }

    public String getuBrNumber() {
        return uBrNumber;
    }

    public void setuBrNumber(String uBrNumber) {
        this.uBrNumber = uBrNumber;
    }

    public String getuBrPicture() {
        return uBrPicture;
    }

    public void setuBrPicture(String uBrPicture) {
        this.uBrPicture = uBrPicture;
    }
}
