package io.information.modules.app.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 点赞信息前端
 * </p>
 *
 */
@ApiModel(value = "点赞信息", description = "点赞信息对象")
public class InLikeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 点赞用户昵称
     */
    @ApiModelProperty(value = "点赞用户昵称", name = "nick", dataType = "String")
    private String nick;
    /**
     * 点赞用户头像
     */
    @ApiModelProperty(value = "点赞用户头像", name = "nick", dataType = "String")
    private String photo;
    /**
     * 点赞类型
     */
    @ApiModelProperty(value = "点赞类型", name = "nick", dataType = "String")
    private String type;
    /**
     * 点赞目标内容
     */
    @ApiModelProperty(value = "点赞目标内容", name = "nick", dataType = "String")
    private String data;
    /**
     * 点赞时间
     */
    @ApiModelProperty(value = "点赞时间", name = "nick", dataType = "Date")
    private Date time;

    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
}
