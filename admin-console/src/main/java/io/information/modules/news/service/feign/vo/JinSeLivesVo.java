package io.information.modules.news.service.feign.vo;

import java.io.Serializable;

public class JinSeLivesVo implements Serializable {
    private static final long serialVersionUID = 1L;
    Long id;
    /**
     * 包含标题和内容【】分隔
     */
    String content;
    /**
     * 创建时间（时间戳）
     */
    Long created_at;
    /**
     * 利空
     */
    Integer down_counts;
    /**
     * 利好
     */
    Integer up_counts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Integer getDown_counts() {
        return down_counts;
    }

    public void setDown_counts(Integer down_counts) {
        this.down_counts = down_counts;
    }

    public Integer getUp_counts() {
        return up_counts;
    }

    public void setUp_counts(Integer up_counts) {
        this.up_counts = up_counts;
    }
}
