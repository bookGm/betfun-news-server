package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 资讯帖子辩论表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "帖子辩论信息", description = "帖子辩论信息对象")
public class InCardArgue implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 帖子基础信息
     */
    @ApiModelProperty(value = "帖子基础信息", name = "cardBase", required = true)
    private InCardBase cardBase;
    /**
     * 帖子id
     */
    @TableId
    private Long cId;

    /**
     * 正方观点
     */
    @ApiModelProperty(value = "正方观点", name = "caFside", required = true)
    private String caFside;

    /**
     * 反方观点
     */
    @ApiModelProperty(value = "反方观点", name = "caRside", required = true)
    private String caRside;

    /**
     * 正方观点投票人ids，逗号分隔
     */
    @ApiModelProperty(value = "正方观点投票人ids，逗号分隔", name = "caFsideUids", required = false,example = "0,1,2")
    private String caFsideUids;
    //正方数量
    @TableField(exist = false)
    private Integer caFsideNumber;

    /**
     * 反方观点投票人ids，逗号分隔
     */
    @ApiModelProperty(value = "反方观点投票人ids，逗号分隔", name = "caRsideUids", required = false,example = "0,1,2")
    private String caRsideUids;
    //反方数量
    @TableField(exist = false)
    private Integer caRsideNumber;

    /**
     * 辩论结束日期
     */
    @ApiModelProperty(value = "辩论结束日期(yyyy-MM-dd HH:mm:ss)", name = "caCloseTime", required = true,example = "0")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date caCloseTime;


    public InCardBase getCardBase() {
        return cardBase;
    }

    public void setCardBase(InCardBase cardBase) {
        this.cardBase = cardBase;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getCaFside() {
        return caFside;
    }

    public void setCaFside(String caFside) {
        this.caFside = caFside;
    }

    public String getCaRside() {
        return caRside;
    }

    public void setCaRside(String caRside) {
        this.caRside = caRside;
    }

    public Integer getCaFsideNumber() {
        return caFsideNumber;
    }

    public void setCaFsideNumber(Integer caFsideNumber) {
        this.caFsideNumber = caFsideNumber;
    }

    public Integer getCaRsideNumber() {
        return caRsideNumber;
    }

    public void setCaRsideNumber(Integer caRsideNumber) {
        this.caRsideNumber = caRsideNumber;
    }

    public String getCaFsideUids() {
        return caFsideUids;
    }

    public void setCaFsideUids(String caFsideUids) {
        this.caFsideUids = caFsideUids;
    }

    public String getCaRsideUids() {
        return caRsideUids;
    }

    public void setCaRsideUids(String caRsideUids) {
        this.caRsideUids = caRsideUids;
    }

    public Date getCaCloseTime() {
        return caCloseTime;
    }

    public void setCaCloseTime(Date caCloseTime) {
        this.caCloseTime = caCloseTime;
    }
}
