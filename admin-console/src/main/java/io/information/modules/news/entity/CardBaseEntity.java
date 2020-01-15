package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 资讯帖子基础表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@TableName("in_card_base")
public class CardBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    @TableId(type = IdType.INPUT)
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
     * 用户名称
     */
    @TableField(exist = false)
    private String uName;
    /**
     * 帖子分类（字典）
     */
    private Integer cCategory;
    /**
     * 帖子节点分类（字典）
     */
    private Long cNodeCategory;
    /**
     * 帖子标题
     */
    private String cTitle;
    /**
     * 帖子正文
     */
    private String cContent;
    /**
     * 回帖仅作者可见（0：是  1：否）
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cCreateTime;
    /**
     * 浏览量
     */
    private Long cReadNumber;


    public Long getcReadNumber() {
        return cReadNumber;
    }

    public void setcReadNumber(Long cReadNumber) {
        this.cReadNumber = cReadNumber;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

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

    public Long getcNodeCategory() {
        return cNodeCategory;
    }

    public void setcNodeCategory(Long cNodeCategory) {
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

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
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
