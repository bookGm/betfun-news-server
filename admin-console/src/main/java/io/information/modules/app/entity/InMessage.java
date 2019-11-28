package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-25 10:56:47
 */
@TableName("in_message")
@ApiModel(value = "系统消息", description = "消息信息")
public class InMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(hidden = true)
    private Long mId;
    /**
     * 目标id（-1：后台推送）
     */
    @ApiModelProperty(value = "目标id（-1：后台推送）", name = "tId")
    private Long tId;
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容", name = "mContent")
    private String mContent;
    /**
     * 消息创建时间
     */
    @ApiModelProperty(value = "消息创建时间", name = "mCreateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mCreateTime;

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(Date mCreateTime) {
        this.mCreateTime = mCreateTime;
    }
}
