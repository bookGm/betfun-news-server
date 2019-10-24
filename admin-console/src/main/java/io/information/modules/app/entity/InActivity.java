package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资讯活动表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "活动", description = "活动对象")
public class InActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    @TableId
    private Long actId;

    /**
     * 用户id
     */
    private Long uId;

    /**
     * 活动标题
     */
    @ApiModelProperty(value = "活动标题", name = "actTitle", required = true)
    private String actTitle;

    /**
     * 活动分类（字典）
     */
    @ApiModelProperty(value = "活动分类", name = "actCategory", required = true, example = "0")
    private Integer actCategory;

    /**
     * 活动地址（省市县编码横线分隔）
     */
    @ApiModelProperty(value = "活动地址（省市县编码横线分隔）", name = "actAddr", required = true, example = "0-0-0")
    private String actAddr;

    /**
     * 活动地址详情
     */
    @ApiModelProperty(value = "活动地址详情", name = "actAddrDetail", required = true)
    private String actAddrDetail;

    /**
     * 活动人数
     */
    @ApiModelProperty(value = "活动人数", name = "actNum", required = true)
    private Long actNum;

    /**
     * 已参加人数
     */
    private Long actInNum;

    /**
     * 活动封面URL
     */
    @ApiModelProperty(value = "活动封面URL", name = "actCover", required = false)
    private String actCover;

    /**
     * 活动详情
     */
    @ApiModelProperty(value = "活动详情", name = "actDetail", required = true)
    private String actDetail;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间(yyyy-MM-dd HH:mm:ss)", name = "actStartTime", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actStartTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间(yyyy-MM-dd HH:mm:ss)", name = "actCloseTime", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actCloseTime;

    /**
     * 活动创建时间
     */
    private Date actCreateTime;


    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getActTitle() {
        return actTitle;
    }

    public void setActTitle(String actTitle) {
        this.actTitle = actTitle;
    }

    public Integer getActCategory() {
        return actCategory;
    }

    public void setActCategory(Integer actCategory) {
        this.actCategory = actCategory;
    }

    public String getActAddr() {
        return actAddr;
    }

    public void setActAddr(String actAddr) {
        this.actAddr = actAddr;
    }

    public String getActAddrDetail() {
        return actAddrDetail;
    }

    public void setActAddrDetail(String actAddrDetail) {
        this.actAddrDetail = actAddrDetail;
    }

    public Long getActNum() {
        return actNum;
    }

    public void setActNum(Long actNum) {
        this.actNum = actNum;
    }

    public Long getActInNum() {
        return actInNum;
    }

    public void setActInNum(Long actInNum) {
        this.actInNum = actInNum;
    }

    public String getActCover() {
        return actCover;
    }

    public void setActCover(String actCover) {
        this.actCover = actCover;
    }

    public String getActDetail() {
        return actDetail;
    }

    public void setActDetail(String actDetail) {
        this.actDetail = actDetail;
    }

    public Date getActStartTime() {
        return actStartTime;
    }

    public void setActStartTime(Date actStartTime) {
        this.actStartTime = actStartTime;
    }

    public Date getActCloseTime() {
        return actCloseTime;
    }

    public void setActCloseTime(Date actCloseTime) {
        this.actCloseTime = actCloseTime;
    }

    public Date getActCreateTime() {
        return actCreateTime;
    }

    public void setActCreateTime(Date actCreateTime) {
        this.actCreateTime = actCreateTime;
    }
}
