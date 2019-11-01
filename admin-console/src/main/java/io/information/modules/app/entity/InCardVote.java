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
    private Integer cv0;
    @ApiModelProperty(hidden=true)
    private Integer cv1;
    @ApiModelProperty(hidden=true)
    private Integer cv2;
    @ApiModelProperty(hidden=true)
    private Integer cv3;
    @ApiModelProperty(hidden=true)
    private Integer cv4;
    @ApiModelProperty(hidden=true)
    private Integer cv5;
    @ApiModelProperty(hidden=true)
    private Integer cv6;
    @ApiModelProperty(hidden=true)
    private Integer cv7;
    @ApiModelProperty(hidden=true)
    private Integer cv8;
    @ApiModelProperty(hidden=true)
    private Integer cv9;
    @ApiModelProperty(hidden=true)
    private Integer cv10;
    @ApiModelProperty(hidden=true)
    private Integer cv11;
    @ApiModelProperty(hidden=true)
    private Integer cv12;
    @ApiModelProperty(hidden=true)
    private Integer cv13;
    @ApiModelProperty(hidden=true)
    private Integer cv14;
    @ApiModelProperty(hidden=true)
    private Integer cv15;
    @ApiModelProperty(hidden=true)
    private Integer cv16;
    @ApiModelProperty(hidden=true)
    private Integer cv17;
    @ApiModelProperty(hidden=true)
    private Integer cv18;
    @ApiModelProperty(hidden=true)
    private Integer cv19;
    @ApiModelProperty(hidden=true)
    private Integer cv20;
    @ApiModelProperty(hidden=true)
    private Integer cv21;
    @ApiModelProperty(hidden=true)
    private Integer cv22;
    @ApiModelProperty(hidden=true)
    private Integer cv23;
    @ApiModelProperty(hidden=true)
    private Integer cv24;
    @ApiModelProperty(hidden=true)
    private Integer cv25;
    @ApiModelProperty(hidden=true)
    private Integer cv26;
    @ApiModelProperty(hidden=true)
    private Integer cv27;
    @ApiModelProperty(hidden=true)
    private Integer cv28;
    @ApiModelProperty(hidden=true)
    private Integer cv29;


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

    public Integer getCv0() {
        return cv0;
    }

    public void setCv0(Integer cv0) {
        this.cv0 = cv0;
    }

    public Integer getCv1() {
        return cv1;
    }

    public void setCv1(Integer cv1) {
        this.cv1 = cv1;
    }

    public Integer getCv2() {
        return cv2;
    }

    public void setCv2(Integer cv2) {
        this.cv2 = cv2;
    }

    public Integer getCv3() {
        return cv3;
    }

    public void setCv3(Integer cv3) {
        this.cv3 = cv3;
    }

    public Integer getCv4() {
        return cv4;
    }

    public void setCv4(Integer cv4) {
        this.cv4 = cv4;
    }

    public Integer getCv5() {
        return cv5;
    }

    public void setCv5(Integer cv5) {
        this.cv5 = cv5;
    }

    public Integer getCv6() {
        return cv6;
    }

    public void setCv6(Integer cv6) {
        this.cv6 = cv6;
    }

    public Integer getCv7() {
        return cv7;
    }

    public void setCv7(Integer cv7) {
        this.cv7 = cv7;
    }

    public Integer getCv8() {
        return cv8;
    }

    public void setCv8(Integer cv8) {
        this.cv8 = cv8;
    }

    public Integer getCv9() {
        return cv9;
    }

    public void setCv9(Integer cv9) {
        this.cv9 = cv9;
    }

    public Integer getCv10() {
        return cv10;
    }

    public void setCv10(Integer cv10) {
        this.cv10 = cv10;
    }

    public Integer getCv11() {
        return cv11;
    }

    public void setCv11(Integer cv11) {
        this.cv11 = cv11;
    }

    public Integer getCv12() {
        return cv12;
    }

    public void setCv12(Integer cv12) {
        this.cv12 = cv12;
    }

    public Integer getCv13() {
        return cv13;
    }

    public void setCv13(Integer cv13) {
        this.cv13 = cv13;
    }

    public Integer getCv14() {
        return cv14;
    }

    public void setCv14(Integer cv14) {
        this.cv14 = cv14;
    }

    public Integer getCv15() {
        return cv15;
    }

    public void setCv15(Integer cv15) {
        this.cv15 = cv15;
    }

    public Integer getCv16() {
        return cv16;
    }

    public void setCv16(Integer cv16) {
        this.cv16 = cv16;
    }

    public Integer getCv17() {
        return cv17;
    }

    public void setCv17(Integer cv17) {
        this.cv17 = cv17;
    }

    public Integer getCv18() {
        return cv18;
    }

    public void setCv18(Integer cv18) {
        this.cv18 = cv18;
    }

    public Integer getCv19() {
        return cv19;
    }

    public void setCv19(Integer cv19) {
        this.cv19 = cv19;
    }

    public Integer getCv20() {
        return cv20;
    }

    public void setCv20(Integer cv20) {
        this.cv20 = cv20;
    }

    public Integer getCv21() {
        return cv21;
    }

    public void setCv21(Integer cv21) {
        this.cv21 = cv21;
    }

    public Integer getCv22() {
        return cv22;
    }

    public void setCv22(Integer cv22) {
        this.cv22 = cv22;
    }

    public Integer getCv23() {
        return cv23;
    }

    public void setCv23(Integer cv23) {
        this.cv23 = cv23;
    }

    public Integer getCv24() {
        return cv24;
    }

    public void setCv24(Integer cv24) {
        this.cv24 = cv24;
    }

    public Integer getCv25() {
        return cv25;
    }

    public void setCv25(Integer cv25) {
        this.cv25 = cv25;
    }

    public Integer getCv26() {
        return cv26;
    }

    public void setCv26(Integer cv26) {
        this.cv26 = cv26;
    }

    public Integer getCv27() {
        return cv27;
    }

    public void setCv27(Integer cv27) {
        this.cv27 = cv27;
    }

    public Integer getCv28() {
        return cv28;
    }

    public void setCv28(Integer cv28) {
        this.cv28 = cv28;
    }

    public Integer getCv29() {
        return cv29;
    }

    public void setCv29(Integer cv29) {
        this.cv29 = cv29;
    }

    public InCardBase getInCardBase() {
        return inCardBase;
    }

    public void setInCardBase(InCardBase inCardBase) {
        this.inCardBase = inCardBase;
    }
}
