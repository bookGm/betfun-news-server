package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

@TableName("in_tag")
@ApiModel(value = "InTag", description = "标签信息")
public class InTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签id
     */
    @TableId(value = "t_id", type = IdType.AUTO)
    @ApiModelProperty(value = "标签id", name = "tId", required = true)
    private Long tId;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称", name = "tName")
    private String tName;
    /**
     * 标签描述
     */
    @ApiModelProperty(value = "标签描述", name = "tDescribe")
    private String tDescribe;

    /**
     * 标签来源（0：抓取  1：后台维护）
     */
    @ApiModelProperty(value = "标签来源(0：抓取  1：后台维护）", name = "tFrom")
    private Integer tFrom;
    /**
     * 标签类型
     */
    @ApiModelProperty(value = "标签类型", name = "tCategory")
    private Integer tCategory;

    /**
     * 标签创建时间
     */
    @ApiModelProperty(value = "标签创建时间", name = "tCreateTime")
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

    public String gettDescribe() {
        return tDescribe;
    }

    public void settDescribe(String tDescribe) {
        this.tDescribe = tDescribe;
    }
}
