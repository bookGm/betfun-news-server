package io.information.modules.app.dto;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户信息", description = "用户信息")
public class InUserDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    private String uNick;

    /**
     * 用户头像
     */
    private String uPhoto;

    /**
     * 用户简介
     */
    private String uIntro;

    /**
     * 用户粉丝
     */
    private Long uFans;

    /**
     * 用户关注的ID
     */
    private List<Long> uFIds;

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public String getuIntro() {
        return uIntro;
    }

    public void setuIntro(String uIntro) {
        this.uIntro = uIntro;
    }

    public Long getuFans() {
        return uFans;
    }

    public void setuFans(Long uFans) {
        this.uFans = uFans;
    }

    public List<Long> getuFIds() {
        return uFIds;
    }

    public void setuFIds(List<Long> uFIds) {
        this.uFIds = uFIds;
    }
}
