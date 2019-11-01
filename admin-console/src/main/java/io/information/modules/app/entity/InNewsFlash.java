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
 * 资讯快讯表
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */

@TableName("in_news_flash")
@ApiModel(value = "资讯", description = "资讯实体")
public class InNewsFlash implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 快讯id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(hidden=true)
    private Long nId;
    /**
     * 快讯标题
     */
    @ApiModelProperty(value = "快讯标题", name = "nTitle", required = true)
    private String nTitle;
    /**
     * 快讯摘要
     */
    @ApiModelProperty(value = "快讯摘要", name = "nBrief", required = true)
    private String nBrief;

    /**
     * 快讯内容
     */
    @ApiModelProperty(value = "快讯内容", name = "nContent", required = true)
    private String nContent;
    /**
     * 利好
     */
    @ApiModelProperty(value = "利好", name = "nBull", required = true)
    private Integer nBull;
    /**
     * 利空
     */
    @ApiModelProperty(value = "利空", name = "nBad", required = true)
    private Integer nBad;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", name = "nCreateTime", required = false)
    private Date nCreateTime;

    @TableField(exist = false)
    private String nSimpleTime;

    public Long getnId() {
        return nId;
    }

    public void setnId(Long nId) {
        this.nId = nId;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnBrief() {
        return nBrief;
    }

    public void setnBrief(String nBrief) {
        this.nBrief = nBrief;
    }

    public String getnContent() {
        return nContent;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    public Integer getnBull() {
        return nBull;
    }

    public void setnBull(Integer nBull) {
        this.nBull = nBull;
    }

    public Integer getnBad() {
        return nBad;
    }

    public void setnBad(Integer nBad) {
        this.nBad = nBad;
    }

    public Date getnCreateTime() {
        return nCreateTime;
    }

    public void setnCreateTime(Date nCreateTime) {
        this.nCreateTime = nCreateTime;
    }

    public String getnSimpleTime() {
        return nSimpleTime;
    }

    public void setnSimpleTime(String nSimpleTime) {
        this.nSimpleTime = nSimpleTime;
    }
}
