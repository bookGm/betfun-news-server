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
 * 资讯帖子基础表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@TableName("in_card_base")
@ApiModel(value = "普通帖", description = "普通帖")
public class InCardBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(hidden = true)
    private Long cId;

    /**
     * 用户id
     */
    private Long uId;

    /**
     * 节点id
     */
    private Long noId;

    /**
     * 帖子分类（字典）
     */
    @ApiModelProperty(value = "帖子分类(0:普通 1:辩论 2:投票帖)", name = "cCategory", required = true)
    private Integer cCategory;

    /**
     * 帖子节点分类（字典）
     */
    @ApiModelProperty(value = "节点分类", name = "cNodeCategory", required = true)
    private Integer cNodeCategory;
    /**
     * 帖子标题
     */
    @ApiModelProperty(value = "标题", name = "cTitle", required = true)
    private String cTitle;
    /**
     * 帖子正文
     */
    @ApiModelProperty(value = "正文", name = "cContent", required = true)
    private String cContent;

    /**
     * 回帖仅作者可见（0：是  1：否）
     */
    @ApiModelProperty(value = "回帖仅作者可见（0：是  1：否）", name = "cHide", required = true)
    private Integer cHide;

    /**
     * 点赞数
     */
    private Long cLike;

    /**
     * 收藏数
     */
    private Integer cCollect;

    /**
     * 评论数
     */
    private Long cCritic;

    /**
     * 创建时间
     */
    private Date cCreateTime;


    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getNoId() {
        return noId;
    }

    public void setNoId(Long noId) {
        this.noId = noId;
    }

    public Integer getcCategory() {
        return cCategory;
    }

    public void setcCategory(Integer cCategory) {
        this.cCategory = cCategory;
    }

    public Integer getcNodeCategory() {
        return cNodeCategory;
    }

    public void setcNodeCategory(Integer cNodeCategory) {
        this.cNodeCategory = cNodeCategory;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    public Integer getcHide() {
        return cHide;
    }

    public void setcHide(Integer cHide) {
        this.cHide = cHide;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public Long getcLike() {
        return cLike;
    }

    public void setcLike(Long cLike) {
        this.cLike = cLike;
    }

    public Integer getcCollect() {
        return cCollect;
    }

    public void setcCollect(Integer cCollect) {
        this.cCollect = cCollect;
    }

    public Long getcCritic() {
        return cCritic;
    }

    public void setcCritic(Long cCritic) {
        this.cCritic = cCritic;
    }

    public Date getcCreateTime() {
        return cCreateTime;
    }

    public void setcCreateTime(Date cCreateTime) {
        this.cCreateTime = cCreateTime;
    }
}
