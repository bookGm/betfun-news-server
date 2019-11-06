package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 帖子节点表
 * </p>
 *
 * @author zxs
 * @since 2019-11-04
 */

@TableName("in_node")
@ApiModel()
public class InNode implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点表ID
     */
    @TableId
    private Long noId;
    /**
     * 节点名称
     */
    private String noName;
    /**
     * 节点头像
     */
    private String noPhoto;
    /**
     * 节点简介
     */
    private String noBrief;
    /**
     * 节点属性(字典)
     */
    private Long noType;
    /**
     * 关注
     */
    private Integer noFocus;
    /**
     * 创建时间
     */
    private Date noCreateTime;

    /**
     * 帖子总数
     */
    @ApiModelProperty(value = "帖子总数", name = "cardNumber", dataType = "Integer")
    @TableField(exist = false)
    private Integer cardNumber;


    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getNoId() {
        return noId;
    }

    public void setNoId(Long noId) {
        this.noId = noId;
    }

    public String getNoName() {
        return noName;
    }

    public void setNoName(String noName) {
        this.noName = noName;
    }

    public String getNoPhoto() {
        return noPhoto;
    }

    public void setNoPhoto(String noPhoto) {
        this.noPhoto = noPhoto;
    }

    public String getNoBrief() {
        return noBrief;
    }

    public void setNoBrief(String noBrief) {
        this.noBrief = noBrief;
    }

    public Long getNoType() {
        return noType;
    }

    public void setNoType(Long noType) {
        this.noType = noType;
    }

    public Integer getNoFocus() {
        return noFocus;
    }

    public void setNoFocus(Integer noFocus) {
        this.noFocus = noFocus;
    }

    public Date getNoCreateTime() {
        return noCreateTime;
    }

    public void setNoCreateTime(Date noCreateTime) {
        this.noCreateTime = noCreateTime;
    }
}
