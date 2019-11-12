package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.information.modules.app.entity.InCardBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资讯投票帖详情（最多30个投票选项）
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@TableName("in_card_vote")
@ApiModel(value = "投票帖", description = "投票帖")
public class InCardVote implements Serializable {

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
     * 帖子类型
     * 0单选 1多选
     */
    @ApiModelProperty(value = "类型（0单选 1多选）", name = "cvType", required = true)
    private int cvType;

    /**
     * 投票选项信息（数组）
     */
    @ApiModelProperty(value = "投票选项信息（数组）", name = "cvInfo", required = true)
    private String cvInfo;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束日期", name = "cvCloseTime", required = true)
    private Date cvCloseTime;
    @ApiModelProperty(hidden=true)
    private Integer cv_0;
    @ApiModelProperty(hidden=true)
    private Integer cv_1;
    @ApiModelProperty(hidden=true)
    private Integer cv_2;
    @ApiModelProperty(hidden=true)
    private Integer cv_3;
    @ApiModelProperty(hidden=true)
    private Integer cv_4;
    @ApiModelProperty(hidden=true)
    private Integer cv_5;
    @ApiModelProperty(hidden=true)
    private Integer cv_6;
    @ApiModelProperty(hidden=true)
    private Integer cv_7;
    @ApiModelProperty(hidden=true)
    private Integer cv_8;
    @ApiModelProperty(hidden=true)
    private Integer cv_9;
    @ApiModelProperty(hidden=true)
    private Integer cv_10;
    @ApiModelProperty(hidden=true)
    private Integer cv_11;
    @ApiModelProperty(hidden=true)
    private Integer cv_12;
    @ApiModelProperty(hidden=true)
    private Integer cv_13;
    @ApiModelProperty(hidden=true)
    private Integer cv_14;
    @ApiModelProperty(hidden=true)
    private Integer cv_15;
    @ApiModelProperty(hidden=true)
    private Integer cv_16;
    @ApiModelProperty(hidden=true)
    private Integer cv_17;
    @ApiModelProperty(hidden=true)
    private Integer cv_18;
    @ApiModelProperty(hidden=true)
    private Integer cv_19;
    @ApiModelProperty(hidden=true)
    private Integer cv_20;
    @ApiModelProperty(hidden=true)
    private Integer cv_21;
    @ApiModelProperty(hidden=true)
    private Integer cv_22;
    @ApiModelProperty(hidden=true)
    private Integer cv_23;
    @ApiModelProperty(hidden=true)
    private Integer cv_24;
    @ApiModelProperty(hidden=true)
    private Integer cv_25;
    @ApiModelProperty(hidden=true)
    private Integer cv_26;
    @ApiModelProperty(hidden=true)
    private Integer cv_27;
    @ApiModelProperty(hidden=true)
    private Integer cv_28;
    @ApiModelProperty(hidden=true)
    private Integer cv_29;


    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public int getCvType() {
        return cvType;
    }

    public void setCvType(int cvType) {
        this.cvType = cvType;
    }

    public String getCvInfo() {
        return cvInfo;
    }

    public void setCvInfo(String cvInfo) {
        this.cvInfo = cvInfo;
    }

    public Date getCvCloseTime() {
        return cvCloseTime;
    }

    public void setCvCloseTime(Date cvCloseTime) {
        this.cvCloseTime = cvCloseTime;
    }

    public Integer getCv_0() {
        return cv_0;
    }

    public void setCv_0(Integer cv_0) {
        this.cv_0 = cv_0;
    }

    public Integer getCv_1() {
        return cv_1;
    }

    public void setCv_1(Integer cv_1) {
        this.cv_1 = cv_1;
    }

    public Integer getCv_2() {
        return cv_2;
    }

    public void setCv_2(Integer cv_2) {
        this.cv_2 = cv_2;
    }

    public Integer getCv_3() {
        return cv_3;
    }

    public void setCv_3(Integer cv_3) {
        this.cv_3 = cv_3;
    }

    public Integer getCv_4() {
        return cv_4;
    }

    public void setCv_4(Integer cv_4) {
        this.cv_4 = cv_4;
    }

    public Integer getCv_5() {
        return cv_5;
    }

    public void setCv_5(Integer cv_5) {
        this.cv_5 = cv_5;
    }

    public Integer getCv_6() {
        return cv_6;
    }

    public void setCv_6(Integer cv_6) {
        this.cv_6 = cv_6;
    }

    public Integer getCv_7() {
        return cv_7;
    }

    public void setCv_7(Integer cv_7) {
        this.cv_7 = cv_7;
    }

    public Integer getCv_8() {
        return cv_8;
    }

    public void setCv_8(Integer cv_8) {
        this.cv_8 = cv_8;
    }

    public Integer getCv_9() {
        return cv_9;
    }

    public void setCv_9(Integer cv_9) {
        this.cv_9 = cv_9;
    }

    public Integer getCv_10() {
        return cv_10;
    }

    public void setCv_10(Integer cv_10) {
        this.cv_10 = cv_10;
    }

    public Integer getCv_11() {
        return cv_11;
    }

    public void setCv_11(Integer cv_11) {
        this.cv_11 = cv_11;
    }

    public Integer getCv_12() {
        return cv_12;
    }

    public void setCv_12(Integer cv_12) {
        this.cv_12 = cv_12;
    }

    public Integer getCv_13() {
        return cv_13;
    }

    public void setCv_13(Integer cv_13) {
        this.cv_13 = cv_13;
    }

    public Integer getCv_14() {
        return cv_14;
    }

    public void setCv_14(Integer cv_14) {
        this.cv_14 = cv_14;
    }

    public Integer getCv_15() {
        return cv_15;
    }

    public void setCv_15(Integer cv_15) {
        this.cv_15 = cv_15;
    }

    public Integer getCv_16() {
        return cv_16;
    }

    public void setCv_16(Integer cv_16) {
        this.cv_16 = cv_16;
    }

    public Integer getCv_17() {
        return cv_17;
    }

    public void setCv_17(Integer cv_17) {
        this.cv_17 = cv_17;
    }

    public Integer getCv_18() {
        return cv_18;
    }

    public void setCv_18(Integer cv_18) {
        this.cv_18 = cv_18;
    }

    public Integer getCv_19() {
        return cv_19;
    }

    public void setCv_19(Integer cv_19) {
        this.cv_19 = cv_19;
    }

    public Integer getCv_20() {
        return cv_20;
    }

    public void setCv_20(Integer cv_20) {
        this.cv_20 = cv_20;
    }

    public Integer getCv_21() {
        return cv_21;
    }

    public void setCv_21(Integer cv_21) {
        this.cv_21 = cv_21;
    }

    public Integer getCv_22() {
        return cv_22;
    }

    public void setCv_22(Integer cv_22) {
        this.cv_22 = cv_22;
    }

    public Integer getCv_23() {
        return cv_23;
    }

    public void setCv_23(Integer cv_23) {
        this.cv_23 = cv_23;
    }

    public Integer getCv_24() {
        return cv_24;
    }

    public void setCv_24(Integer cv_24) {
        this.cv_24 = cv_24;
    }

    public Integer getCv_25() {
        return cv_25;
    }

    public void setCv_25(Integer cv_25) {
        this.cv_25 = cv_25;
    }

    public Integer getCv_26() {
        return cv_26;
    }

    public void setCv_26(Integer cv_26) {
        this.cv_26 = cv_26;
    }

    public Integer getCv_27() {
        return cv_27;
    }

    public void setCv_27(Integer cv_27) {
        this.cv_27 = cv_27;
    }

    public Integer getCv_28() {
        return cv_28;
    }

    public void setCv_28(Integer cv_28) {
        this.cv_28 = cv_28;
    }

    public Integer getCv_29() {
        return cv_29;
    }

    public void setCv_29(Integer cv_29) {
        this.cv_29 = cv_29;
    }

    public InCardBase getInCardBase() {
        return inCardBase;
    }

    public void setInCardBase(InCardBase inCardBase) {
        this.inCardBase = inCardBase;
    }
}
