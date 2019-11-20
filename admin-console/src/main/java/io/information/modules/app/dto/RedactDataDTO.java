package io.information.modules.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "编辑资料", description = "用户编辑资料")
public class RedactDataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "uNick", required = false)
    private String uNick;

    /**
     * 用户简介
     */
    @ApiModelProperty(value = "用户简介", name = "uIntro", required = false)
    private String uIntro;

    @ApiModelProperty(value = "用户头像", name = "uPhoto", required = false)
    private String uPhoto;


    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getuIntro() {
        return uIntro;
    }

    public void setuIntro(String uIntro) {
        this.uIntro = uIntro;
    }
}
