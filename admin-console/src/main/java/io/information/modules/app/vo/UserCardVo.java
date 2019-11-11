package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "社区列表数据", description = "社区列表对象")
public class UserCardVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", name = "uId")
    private Long uId;

    @ApiModelProperty(value = "用户昵称", name = "uName")
    private String uName;

    @ApiModelProperty(value = "用户头像", name = "uPhoto")
    private String uPhoto;

    @ApiModelProperty(value = "发布时间", name = "time")
    private String time;

    @ApiModelProperty(value = "类型", name = "type")
    private String type;

    @ApiModelProperty(value = "帖子ID", name = "cId")
    private Long cId;

    @ApiModelProperty(value = "帖子标题", name = "cTitle")
    private String cTitle;

    @ApiModelProperty(value = "浏览量", name = "readNumber")
    private Long readNumber;

    @ApiModelProperty(value = "评论数", name = "replyNumber")
    private Long replyNumber;

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

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public Long getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(Long readNumber) {
        this.readNumber = readNumber;
    }

    public Long getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(Long replyNumber) {
        this.replyNumber = replyNumber;
    }
}
