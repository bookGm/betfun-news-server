package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 活动票
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 13:32:16
 */
@TableName("in_activity_ticket")
@ApiModel(value = "活动票种信息", description = "票种信息对象")
public class InActivityTicket implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 票id
     */
    @TableId
    private Long tId;
    /**
     * 活动id
     */
    private Long actId;
    /**
     * 票种名称
     */
    @ApiModelProperty(value = "票种名称", name = "tName", required = true)
    private String tName;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格", name = "tPrice", required = true)
    private BigDecimal tPrice;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量", name = "tNum", required = true)
    private Integer tNum;

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public BigDecimal gettPrice() {
        return tPrice;
    }

    public void settPrice(BigDecimal tPrice) {
        this.tPrice = tPrice;
    }

    public Integer gettNum() {
        return tNum;
    }

    public void settNum(Integer tNum) {
        this.tNum = tNum;
    }
}
