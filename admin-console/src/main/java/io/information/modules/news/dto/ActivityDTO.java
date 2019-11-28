package io.information.modules.news.dto;

import java.io.Serializable;

public class ActivityDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long actId;
    private Integer actBanner;

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Integer getActBanner() {
        return actBanner;
    }

    public void setActBanner(Integer actBanner) {
        this.actBanner = actBanner;
    }
}
