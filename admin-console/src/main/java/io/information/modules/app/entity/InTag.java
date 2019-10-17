package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签id
     */
    @TableId(value = "t_id", type = IdType.AUTO)
    private Long tId;

    /**
     * 标签名称
     */
    private String tName;

    /**
     * 标签来源（0：抓取  1：后台维护）
     */
    private Integer tFrom;
    /**
     * 标签类型
     */
    private Integer tCategory;

    /**
     * 标签创建时间
     */
    private Date tCreateTime;


    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Integer gettFrom() {
        return tFrom;
    }

    public void settFrom(Integer tFrom) {
        this.tFrom = tFrom;
    }

    public Date gettCreateTime() {
        return tCreateTime;
    }

    public void settCreateTime(Date tCreateTime) {
        this.tCreateTime = tCreateTime;
    }

    public Integer gettCategory() {
        return tCategory;
    }

    public void settCategory(Integer tCategory) {
        this.tCategory = tCategory;
    }
}
