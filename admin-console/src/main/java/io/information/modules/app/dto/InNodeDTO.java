package io.information.modules.app.dto;

import java.io.Serializable;

public class InNodeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点表ID
     */
    private Long noId;
    /**
     * 节点名称
     */
    private String noName;
    /**
     * 节点头像
     */
    private String noPhoto;
    /**
     * 节点简介
     */
    private String noBrief;

    public Long getNoId() {
        return noId;
    }

    public void setNoId(Long noId) {
        this.noId = noId;
    }

    public String getNoName() {
        return noName;
    }

    public void setNoName(String noName) {
        this.noName = noName;
    }

    public String getNoPhoto() {
        return noPhoto;
    }

    public void setNoPhoto(String noPhoto) {
        this.noPhoto = noPhoto;
    }

    public String getNoBrief() {
        return noBrief;
    }

    public void setNoBrief(String noBrief) {
        this.noBrief = noBrief;
    }
}
