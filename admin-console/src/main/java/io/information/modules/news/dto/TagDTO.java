package io.information.modules.news.dto;

import java.io.Serializable;

public class TagDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long[] tIds;
    private Integer tCategory;

    public Long[] gettIds() {
        return tIds;
    }

    public void settIds(Long[] tIds) {
        this.tIds = tIds;
    }

    public Integer gettCategory() {
        return tCategory;
    }

    public void settCategory(Integer tCategory) {
        this.tCategory = tCategory;
    }
}
