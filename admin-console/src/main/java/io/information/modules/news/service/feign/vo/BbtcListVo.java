package io.information.modules.news.service.feign.vo;

import java.io.Serializable;

public class BbtcListVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文章id
     */
    Long id;
    String views;
    String title;
    String image;
    String desc;
    String post_date_format;
    Object []tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object[] getTags() {
        return tags;
    }

    public void setTags(Object[] tags) {
        this.tags = tags;
    }

    public String getPost_date_format() {
        return post_date_format;
    }

    public void setPost_date_format(String post_date_format) {
        this.post_date_format = post_date_format;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
