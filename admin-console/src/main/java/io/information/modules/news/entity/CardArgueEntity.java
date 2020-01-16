package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 资讯帖子辩论表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@TableName("in_card_argue")
public class CardArgueEntity implements Serializable {
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
     * 正方观点
     */
    private String caFside;
    /**
     * 反方观点
     */
    private String caRside;
    /**
     * 正方观点数量
     */
    private String caFsideNumber;
    /**
     * 反方观点数量
     */
    private String caRsideNumber;
    /**
     * 辩论结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date caCloseTime;
    /**
     * 正方辩手数量
     */
    private Integer caFsideDebater;
    /**
     * 反方辩手数量
     */
    private Integer caRsideDebater;
    /**
     * 辩论是否结束
     */
    @TableField(exist = false)
    private Boolean timeType;
    /**
     * 裁判
     */
    private String caReferee;


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

    public String getCaFsideNumber() {
        return caFsideNumber;
    }

    public void setCaFsideNumber(String caFsideNumber) {
        this.caFsideNumber = caFsideNumber;
    }

    public String getCaRsideNumber() {
        return caRsideNumber;
    }

    public void setCaRsideNumber(String caRsideNumber) {
        this.caRsideNumber = caRsideNumber;
    }

    public Date getCaCloseTime() {
        return caCloseTime;
    }

    public void setCaCloseTime(Date caCloseTime) {
        this.caCloseTime = caCloseTime;
    }

    public CardBaseEntity getBaseCard() {
        return baseCard;
    }

    public void setBaseCard(CardBaseEntity baseCard) {
        this.baseCard = baseCard;
    }

    public String getCaReferee() {
        return caReferee;
    }

    public void setCaReferee(String caReferee) {
        this.caReferee = caReferee;
    }

    public Integer getCaFsideDebater() {
        return caFsideDebater;
    }

    public void setCaFsideDebater(Integer caFsideDebater) {
        this.caFsideDebater = caFsideDebater;
    }

    public Integer getCaRsideDebater() {
        return caRsideDebater;
    }

    public void setCaRsideDebater(Integer caRsideDebater) {

        this.caRsideDebater = caRsideDebater;
    }

    public Boolean getTimeType() {
        return timeType;
    }

    public void setTimeType(Boolean timeType) {
        this.timeType = timeType;
    }
}
