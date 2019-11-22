package io.information.modules.app.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 日志表
 *
 */
@ApiModel(value = "日志", description = "日志字段")
@TableName("in_log")
public class InLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @TableId(value = "l_id", type = IdType.AUTO)
    private Integer lId;
    /**
     * 操作人id
     */
    private Long lOperateId;
    /**
     * 操作人名称
     */
    private String lOperateName;
    /**
     * 目标id
     */
    private Long lTargetId;
    /**
     * 目标类型（0：用户 1：帖子 ）
     */
    private Integer lTargetType;
    /**
     * 目标名称
     */
    private String lTargetName;
    /**
     * 操作（0：点赞 1：收藏 2：关注 3：评论 4：发布）
     */
    private Integer lDo;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lTime;

    public Integer getlId() {
        return lId;
    }

    public void setlId(Integer lId) {
        this.lId = lId;
    }

    public Long getlOperateId() {
        return lOperateId;
    }

    public void setlOperateId(Long lOperateId) {
        this.lOperateId = lOperateId;
    }

    public String getlOperateName() {
        return lOperateName;
    }

    public void setlOperateName(String lOperateName) {
        this.lOperateName = lOperateName;
    }

    public Long getlTargetId() {
        return lTargetId;
    }

    public void setlTargetId(Long lTargetId) {
        this.lTargetId = lTargetId;
    }

    public Integer getlTargetType() {
        return lTargetType;
    }

    public void setlTargetType(Integer lTargetType) {
        this.lTargetType = lTargetType;
    }

    public String getlTargetName() {
        return lTargetName;
    }

    public void setlTargetName(String lTargetName) {
        this.lTargetName = lTargetName;
    }

    public Integer getlDo() {
        return lDo;
    }

    public void setlDo(Integer lDo) {
        this.lDo = lDo;
    }

    public Date getlTime() {
        return lTime;
    }

    public void setlTime(Date lTime) {
        this.lTime = lTime;
    }
}
