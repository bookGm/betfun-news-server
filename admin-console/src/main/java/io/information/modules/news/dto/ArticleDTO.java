package io.information.modules.news.dto;

import java.io.Serializable;

public class ArticleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long aId;
    private Integer aBanner;

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public Integer getaBanner() {
        return aBanner;
    }

    public void setaBanner(Integer aBanner) {
        this.aBanner = aBanner;
    }
}
