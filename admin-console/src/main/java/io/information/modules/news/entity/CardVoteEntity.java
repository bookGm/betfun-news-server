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

    @TableField("cv_0")
    private Integer cv0;
    @TableField(exist = false)
    private String info0;
    @TableField("cv_1")
    private Integer cv1;
    @TableField(exist = false)
    private String info1;
    @TableField("cv_2")
    private Integer cv2;
    @TableField(exist = false)
    private String info2;
    @TableField("cv_3")
    private Integer cv3;
    @TableField(exist = false)
    private String info3;
    @TableField("cv_4")
    private Integer cv4;
    @TableField(exist = false)
    private String info4;
    @TableField("cv_5")
    private Integer cv5;
    @TableField(exist = false)
    private String info5;
    @TableField("cv_6")
    private Integer cv6;
    @TableField(exist = false)
    private String info6;
    @TableField("cv_7")
    private Integer cv7;
    @TableField(exist = false)
    private String info7;
    @TableField("cv_8")
    private Integer cv8;
    @TableField(exist = false)
    private String info8;
    @TableField("cv_9")
    private Integer cv9;
    @TableField(exist = false)
    private String info9;
    @TableField("cv_10")
    private Integer cv10;
    @TableField(exist = false)
    private String info10;
    @TableField("cv_11")
    private Integer cv11;
    @TableField(exist = false)
    private String info11;
    @TableField("cv_12")
    private Integer cv12;
    @TableField(exist = false)
    private String info12;
    @TableField("cv_13")
    private Integer cv13;
    @TableField(exist = false)
    private String info13;
    @TableField("cv_14")
    private Integer cv14;
    @TableField(exist = false)
    private String info14;
    @TableField("cv_15")
    private Integer cv15;
    @TableField(exist = false)
    private String info15;
    @TableField("cv_16")
    private Integer cv16;
    @TableField(exist = false)
    private String info16;
    @TableField("cv_17")
    private Integer cv17;
    @TableField(exist = false)
    private String info17;
    @TableField("cv_18")
    private Integer cv18;
    @TableField(exist = false)
    private String info18;
    @TableField("cv_19")
    private Integer cv19;
    @TableField(exist = false)
    private String info19;
    @TableField("cv_20")
    private Integer cv20;
    @TableField(exist = false)
    private String info20;
    @TableField("cv_21")
    private Integer cv21;
    @TableField(exist = false)
    private String info21;
    @TableField("cv_22")
    private Integer cv22;
    @TableField(exist = false)
    private String info22;
    @TableField("cv_23")
    private Integer cv23;
    @TableField(exist = false)
    private String info23;
    @TableField("cv_24")
    private Integer cv24;
    @TableField(exist = false)
    private String info24;
    @TableField("cv_25")
    private Integer cv25;
    @TableField(exist = false)
    private String info25;
    @TableField("cv_26")
    private Integer cv26;
    @TableField(exist = false)
    private String info26;
    @TableField("cv_27")
    private Integer cv27;
    @TableField(exist = false)
    private String info27;
    @TableField("cv_28")
    private Integer cv28;
    @TableField(exist = false)
    private String info28;
    @TableField("cv_29")
    private Integer cv29;
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
