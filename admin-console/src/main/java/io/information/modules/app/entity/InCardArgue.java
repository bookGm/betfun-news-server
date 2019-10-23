package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class InCardArgue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    @TableId
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
     * 正方观点投票人ids，逗号分隔
     */
    private String caFsideUids;
    //正方数量
    private Integer caFsideNumber;

    /**
     * 反方观点投票人ids，逗号分隔
     */
    private String caRsideUids;
    //反方数量
    private Integer caRsideNumber;

    /**
     * 辩论结束日期
     */
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
}
