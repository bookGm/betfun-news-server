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

/**
 * <p>
 * 资讯帖子辩论表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@TableName("in_card_argue")
@ApiModel(value = "辩论贴", description = "辩论贴")
public class InCardArgue implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "普通帖", name = "inCardBase", required = true)
    @TableField(exist = false)
    private InCardBase inCardBase;
    /**
     * 帖子id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(hidden=true)
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
    @ApiModelProperty(hidden=true)
    private String caFsideUids;
    /**
     * 正方数量
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden=true)
    private Integer caFsideNumber;

    /**
     * 反方观点投票人ids，逗号分隔
     */
    @ApiModelProperty(hidden=true)
    private String caRsideUids;
    /**
     * 反方数量
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden=true)
    private Integer caRsideNumber;

    /**
     * 辩论结束日期
     */
    @ApiModelProperty(value = "辩论结束日期", name = "caCloseTime", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date caCloseTime;

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

    public InCardBase getInCardBase() {
        return inCardBase;
    }

    public void setInCardBase(InCardBase inCardBase) {
        this.inCardBase = inCardBase;
    }
}
