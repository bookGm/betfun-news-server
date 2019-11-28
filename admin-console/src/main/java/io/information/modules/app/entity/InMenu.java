package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 资讯菜单表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@TableName("in_menu")
public class InMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId
    private Long mId;

    /**
     * 菜单名称
     */
    private String mName;

    /**
     * 菜单编码
     */
    private String mCode;

    /**
     * 菜单父编码
     */
    private String mPcode;
    /**
     * 菜单资源
     */
    private String mComponent;

    /**
     * 菜单路径（资源表）
     */
    private String mUrl;

    /**
     * 权限（0：路人  1：需登录  2：需验证）
     */
    private Integer mAuth;
    /**
     * 是否禁用（0：否  1：是）
     */
    private Integer mDisable;
    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<InMenu> children;


    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmPcode() {
        return mPcode;
    }

    public void setmPcode(String mPcode) {
        this.mPcode = mPcode;
    }

    public String getmComponent() {
        return mComponent;
    }

    public void setmComponent(String mComponent) {
        this.mComponent = mComponent;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public List<InMenu> getChildren() {
        return children;
    }

    public void setChildren(List<InMenu> children) {
        this.children = children;
    }

    public Integer getmAuth() {
        return mAuth;
    }

    public void setmAuth(Integer mAuth) {
        this.mAuth = mAuth;
    }

    public Integer getmDisable() {
        return mDisable;
    }

    public void setmDisable(Integer mDisable) {
        this.mDisable = mDisable;
    }
}
