package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资讯活动表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@TableName("in_activity")
@ApiModel(value = "活动", description = "活动对象")
public class InActivity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 动态表单信息
     */
    @ApiModelProperty(value = "动态表单信息集合", name = "fieldsList", dataType = "List", required = false)
    @TableField(exist = false)
    private List<InActivityFields> fieldsList;
    @ApiModelProperty(value = "活动票种信息集合", name = "ticketList", dataType = "List", required = false)
    @TableField(exist = false)
    private List<InActivityTicket> ticketList;
    @ApiModelProperty(value = "动态表单数据集合", name = "datasList", dataType = "List", required = false)
    @TableField(exist = false)
    private List<InActivityDatas> datasList;
    /**
     * 活动id
     */
    @TableId(type = IdType.INPUT)
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
    @ApiModelProperty(value = "已参加人数", name = "actInNum", required = false)
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

    /**
     * 活动状态（0:未通过 1:审核中 2:已通过）
     */
    @ApiModelProperty(value = "活动状态（0:未通过 1:审核中 2:已通过）", name = "actStatus", required = true)
    private Integer actStatus;

    /**
     * 点赞数
     */
    private Long actLike;

    /**
     * 收藏数
     */
    private Integer aCollect;

    /**
     * 评论数
     */
    private Long aCritic;

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

    public Integer getActStatus() {
        return actStatus;
    }

    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }

    public List<InActivityFields> getFieldsList() {
        return fieldsList;
    }

    public void setFieldsList(List<InActivityFields> fieldsList) {
        this.fieldsList = fieldsList;
    }

    public List<InActivityTicket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<InActivityTicket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<InActivityDatas> getDatasList() {
        return datasList;
    }

    public void setDatasList(List<InActivityDatas> datasList) {
        this.datasList = datasList;
    }

    public Long getActLike() {
        return actLike;
    }

    public void setActLike(Long actLike) {
        this.actLike = actLike;
    }

    public Integer getaCollect() {
        return aCollect;
    }

    public void setaCollect(Integer aCollect) {
        this.aCollect = aCollect;
    }

    public Long getaCritic() {
        return aCritic;
    }

    public void setaCritic(Long aCritic) {
        this.aCritic = aCritic;
    }
}
