package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
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

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "资讯", description = "资讯实体")
@TableName("in_news_flash")
public class NewsFlash implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 快讯id
     */
    @TableId(type = IdType.INPUT)
    private Long nId;
    /**
     * 快讯标题
     */
    private String nTitle;
    /**
     * 快讯摘要
     */
    private String nBrief;

    /**
     * 快讯内容
     */
    private String nContent;
    /**
     * 利好
     */
    private Integer nBull;
    /**
     * 利空
     */
    private Integer nBad;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nCreateTime;

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
}
