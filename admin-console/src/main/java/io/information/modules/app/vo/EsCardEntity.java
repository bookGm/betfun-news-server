package io.information.modules.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.elasticsearch.entity.EsUserEntity;

import java.io.Serializable;
import java.util.Date;

public class EsCardEntity implements Serializable {
    private static final Long serialVersionUID = 1L;
    /**
     * 帖子id
     */
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
     * 帖子分类（0：普通帖  1：辩论帖  2：投票帖）
     */
    private Integer cCategory;
    /**
     * 帖子节点分类（字典）
     */
    private Integer cNodeCategory;
    /**
     * 帖子标题
     */
    private String cTitle;
    /**
     * 帖子正文
     */
    private String cContent;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cCreateTime;
    /**
     * 回帖仅作者可见（0：是  1：否）
     */
    private Integer cHide;
    /**
     * 正方观点
     */
    private String caFside;
    /**
     * 反方观点
     */
    private String caRside;
    /**
     * 辩论结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date caCloseTime;
    /**
     * 投票结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cvCloseTime;
    //用户展示信息
    private EsUserEntity userEs;


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

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
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

    public Date getCaCloseTime() {
        return caCloseTime;
    }

    public void setCaCloseTime(Date caCloseTime) {
        this.caCloseTime = caCloseTime;
    }

    public Date getCvCloseTime() {
        return cvCloseTime;
    }

    public void setCvCloseTime(Date cvCloseTime) {
        this.cvCloseTime = cvCloseTime;
    }

    public EsUserEntity getUserEs() {
        return userEs;
    }

    public void setUserEs(EsUserEntity userEs) {
        this.userEs = userEs;
    }

    public Date getcCreateTime() {
        return cCreateTime;
    }

    public void setcCreateTime(Date cCreateTime) {
        this.cCreateTime = cCreateTime;
    }
}
