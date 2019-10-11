package io.information.modules.app.entity;

import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 资讯菜单表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
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
     * 菜单路径（资源表）
     */
    private String mUrl;

    /**
     * 是否禁用（0：否  1：是）
     */
    private Integer mDisable;


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

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public Integer getmDisable() {
        return mDisable;
    }

    public void setmDisable(Integer mDisable) {
        this.mDisable = mDisable;
    }
}
