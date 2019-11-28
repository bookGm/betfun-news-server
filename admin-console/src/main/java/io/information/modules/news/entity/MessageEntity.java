package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-25 10:56:47
 */
@TableName("in_message")
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId(type = IdType.INPUT)
    private Long mId;
    /**
     * 目标id（-1：后台推送）
     */
    private Long tId;
    /**
     * 消息内容
     */
    private String mContent;
    /**
     * 消息创建时间
     */
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
