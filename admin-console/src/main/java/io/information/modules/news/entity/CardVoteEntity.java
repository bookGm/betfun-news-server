package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 资讯投票帖详情（最多30个投票选项）
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
@TableName("in_card_vote")
public class CardVoteEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 普通帖子
     */
    @TableField(exist = false)
    private CardBaseEntity baseCard;
    /**
     * 帖子id
     */
    @TableId(type = IdType.INPUT)
    private Long cId;
    /**
     * 帖子类型
     * 0单选 1多选
     */
    private int cvType;
    /**
     * 投票选项信息（逗号分隔）
     */
    private String cvInfo;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cvCloseTime;

    private Integer cv_0;
    @TableField(exist = false)
    private String info0;

    private Integer cv_1;
    @TableField(exist = false)
    private String info1;

    private Integer cv_2;
    @TableField(exist = false)
    private String info2;

    private Integer cv_3;
    @TableField(exist = false)
    private String info3;

    private Integer cv_4;
    @TableField(exist = false)
    private String info4;

    private Integer cv_5;
    @TableField(exist = false)
    private String info5;

    private Integer cv_6;
    @TableField(exist = false)
    private String info6;

    private Integer cv_7;
    @TableField(exist = false)
    private String info7;

    private Integer cv_8;
    @TableField(exist = false)
    private String info8;

    private Integer cv_9;
    @TableField(exist = false)
    private String info9;

    private Integer cv_10;
    @TableField(exist = false)
    private String info10;

    private Integer cv_11;
    @TableField(exist = false)
    private String info11;

    private Integer cv_12;
    @TableField(exist = false)
    private String info12;

    private Integer cv_13;
    @TableField(exist = false)
    private String info13;

    private Integer cv_14;
    @TableField(exist = false)
    private String info14;

    private Integer cv_15;
    @TableField(exist = false)
    private String info15;

    private Integer cv_16;
    @TableField(exist = false)
    private String info16;

    private Integer cv_17;
    @TableField(exist = false)
    private String info17;

    private Integer cv_18;
    @TableField(exist = false)
    private String info18;

    private Integer cv_19;
    @TableField(exist = false)
    private String info19;

    private Integer cv_20;
    @TableField(exist = false)
    private String info20;

    private Integer cv_21;
    @TableField(exist = false)
    private String info21;

    private Integer cv_22;
    @TableField(exist = false)
    private String info22;

    private Integer cv_23;
    @TableField(exist = false)
    private String info23;

    private Integer cv_24;
    @TableField(exist = false)
    private String info24;

    private Integer cv_25;
    @TableField(exist = false)
    private String info25;

    private Integer cv_26;
    @TableField(exist = false)
    private String info26;

    private Integer cv_27;
    @TableField(exist = false)
    private String info27;

    private Integer cv_28;
    @TableField(exist = false)
    private String info28;

    private Integer cv_29;
    @TableField(exist = false)
    private String info29;

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

    public void setInfo(Integer index, String info) {
        switch (index) {
            case 0:
                this.info0 = info;
                break;
            case 1:
                this.info1 = info;
                break;
            case 2:
                this.info2 = info;
                break;
            case 3:
                this.info3 = info;
                break;
            case 4:
                this.info4 = info;
                break;
            case 5:
                this.info5 = info;
                break;
            case 6:
                this.info6 = info;
                break;
            case 7:
                this.info7 = info;
                break;
            case 8:
                this.info8 = info;
                break;
            case 9:
                this.info9 = info;
                break;
            case 10:
                this.info10 = info;
                break;
            case 11:
                this.info11 = info;
                break;
            case 12:
                this.info12 = info;
                break;
            case 13:
                this.info13 = info;
                break;
            case 14:
                this.info14 = info;
                break;
            case 15:
                this.info15 = info;
                break;
            case 16:
                this.info16 = info;
                break;
            case 17:
                this.info17 = info;
                break;
            case 18:
                this.info18 = info;
                break;
            case 19:
                this.info19 = info;
                break;
            case 20:
                this.info20 = info;
                break;
            case 21:
                this.info21 = info;
                break;
            case 22:
                this.info22 = info;
                break;
            case 23:
                this.info23 = info;
                break;
            case 24:
                this.info24 = info;
                break;
            case 25:
                this.info25 = info;
                break;
            case 26:
                this.info26 = info;
                break;
            case 27:
                this.info27 = info;
                break;
            case 28:
                this.info28 = info;
                break;
            case 29:
                this.info29 = info;
                break;
        }
    }

    public String getInfo0() {
        return info0;
    }

    public void setInfo0(String info0) {
        this.info0 = info0;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public String getInfo6() {
        return info6;
    }

    public void setInfo6(String info6) {
        this.info6 = info6;
    }

    public String getInfo7() {
        return info7;
    }

    public void setInfo7(String info7) {
        this.info7 = info7;
    }

    public String getInfo8() {
        return info8;
    }

    public void setInfo8(String info8) {
        this.info8 = info8;
    }

    public String getInfo9() {
        return info9;
    }

    public void setInfo9(String info9) {
        this.info9 = info9;
    }

    public String getInfo10() {
        return info10;
    }

    public void setInfo10(String info10) {
        this.info10 = info10;
    }

    public String getInfo11() {
        return info11;
    }

    public void setInfo11(String info11) {
        this.info11 = info11;
    }

    public String getInfo12() {
        return info12;
    }

    public void setInfo12(String info12) {
        this.info12 = info12;
    }

    public String getInfo13() {
        return info13;
    }

    public void setInfo13(String info13) {
        this.info13 = info13;
    }

    public String getInfo14() {
        return info14;
    }

    public void setInfo14(String info14) {
        this.info14 = info14;
    }

    public String getInfo15() {
        return info15;
    }

    public void setInfo15(String info15) {
        this.info15 = info15;
    }

    public String getInfo16() {
        return info16;
    }

    public void setInfo16(String info16) {
        this.info16 = info16;
    }

    public String getInfo17() {
        return info17;
    }

    public void setInfo17(String info17) {
        this.info17 = info17;
    }

    public String getInfo18() {
        return info18;
    }

    public void setInfo18(String info18) {
        this.info18 = info18;
    }

    public String getInfo19() {
        return info19;
    }

    public void setInfo19(String info19) {
        this.info19 = info19;
    }

    public String getInfo20() {
        return info20;
    }

    public void setInfo20(String info20) {
        this.info20 = info20;
    }

    public String getInfo21() {
        return info21;
    }

    public void setInfo21(String info21) {
        this.info21 = info21;
    }

    public String getInfo22() {
        return info22;
    }

    public void setInfo22(String info22) {
        this.info22 = info22;
    }

    public String getInfo23() {
        return info23;
    }

    public void setInfo23(String info23) {
        this.info23 = info23;
    }

    public String getInfo24() {
        return info24;
    }

    public void setInfo24(String info24) {
        this.info24 = info24;
    }

    public String getInfo25() {
        return info25;
    }

    public void setInfo25(String info25) {
        this.info25 = info25;
    }

    public String getInfo26() {
        return info26;
    }

    public void setInfo26(String info26) {
        this.info26 = info26;
    }

    public String getInfo27() {
        return info27;
    }

    public void setInfo27(String info27) {
        this.info27 = info27;
    }

    public String getInfo28() {
        return info28;
    }

    public void setInfo28(String info28) {
        this.info28 = info28;
    }

    public String getInfo29() {
        return info29;
    }

    public void setInfo29(String info29) {
        this.info29 = info29;
    }

    public CardBaseEntity getBaseCard() {
        return baseCard;
    }

    public void setBaseCard(CardBaseEntity baseCard) {
        this.baseCard = baseCard;
    }
}
