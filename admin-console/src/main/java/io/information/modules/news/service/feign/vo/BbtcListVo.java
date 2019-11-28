package io.information.modules.news.service.feign.vo;

import java.io.Serializable;

public class BbtcListVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文章id
     */
    Long id;
    /**
     * 文章访问量
     */
    String views;
    /**
     * 文章标题
     */
    String title;
    /**
     * 文章封面图
     */
    String image;
    /**
     * 文章简介
     */
    String desc;
    /**
     * 文章发布时间
     */
    String post_date_format;
    /**
     * 作者信息
     */
    AuthorInfoVo author_info;
    /**
     * 文章涉及标签
     */
    Object[] tags;

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

    public AuthorInfoVo getAuthor_info() {
        return author_info;
    }

    public void setAuthor_info(AuthorInfoVo author_info) {
        this.author_info = author_info;
    }
}
