package io.information.modules.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 资讯资源表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@TableName("in_source")
public class SourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源路径
     */
    @TableId
    private String sUrl;
    /**
     * 组件名称
     */
    private String sComponent;
    /**
     * 资源名称
     */
    private String sName;
    /**
     * 系统操作用户id
     */
    private Long sOperationUserid;
    /**
     * 是否禁用（0：否 1：是）
     */
    private Integer sDisable;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sUpdateTime;
    /**
     * 资源创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sCreateTime;
    /**
     * 是否菜单资源（0：否 1：是）
     */
    private Integer sIsmenu;

    public String getsUrl() {
        return sUrl;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public String getsComponent() {
        return sComponent;
    }

    public void setsComponent(String sComponent) {
        this.sComponent = sComponent;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Long getsOperationUserid() {
        return sOperationUserid;
    }

    public void setsOperationUserid(Long sOperationUserid) {
        this.sOperationUserid = sOperationUserid;
    }

    public Integer getsDisable() {
        return sDisable;
    }

    public void setsDisable(Integer sDisable) {
        this.sDisable = sDisable;
    }

    public Date getsUpdateTime() {
        return sUpdateTime;
    }

    public void setsUpdateTime(Date sUpdateTime) {
        this.sUpdateTime = sUpdateTime;
    }

    public Date getsCreateTime() {
        return sCreateTime;
    }

    public void setsCreateTime(Date sCreateTime) {
        this.sCreateTime = sCreateTime;
    }

    public Integer getsIsmenu() {
        return sIsmenu;
    }

    public void setsIsmenu(Integer sIsmenu) {
        this.sIsmenu = sIsmenu;
    }
}
