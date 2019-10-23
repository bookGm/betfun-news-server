package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 资讯帖子基础表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "帖子基础信息", description = "帖子基础信息对象")
public class InCardBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    @TableId
    private Long cId;

    /**
     * 用户id
     */
    private Long uId;

    /**
     * 帖子分类（字典）
     */
    @ApiModelProperty(value = "帖子分类", name = "cCategory", required = true,example = "0：普通帖  1：辩论帖  2：投票帖")
    private Integer cCategory;

    /**
     * 帖子节点分类（字典）
     */
    @ApiModelProperty(value = "帖子节点分类", name = "cNodeCategory", required = true,example = "0")
    private Integer cNodeCategory;
    /**
     * 帖子标题
     */
    @ApiModelProperty(value = "帖子标题", name = "cTitle", required = true)
    private String cTitle;
    /**
     * 帖子正文
     */
    @ApiModelProperty(value = "帖子正文", name = "cContent", required = true)
    private String cContent;

    /**
     * 回帖仅作者可见（0：是  1：否）
     */
    @ApiModelProperty(value = "回帖仅作者可见（0：是  1：否）", name = "cHide", required = false)
    private Integer cHide;


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
}
