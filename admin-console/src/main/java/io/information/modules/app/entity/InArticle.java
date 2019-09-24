package io.information.modules.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 资讯文章表
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InArticle implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 文章id
            */
    private Long aId;

            /**
            * 用户id
            */
    private Long uId;

            /**
            * 文章标题
            */
    private String aTitle;

            /**
            * 文章内容
            */
    private String aContent;

            /**
            * 文章摘要
            */
    private String aBrief;

            /**
            * 文章关键字（例：1,2,3）
            */
    private String aKeyword;

            /**
            * 文章封面URL
            */
    private String aCover;

            /**
            * 文章类型（字典）
            */
    private Integer aType;

            /**
            * 文章来源（非转载类型，此字段为空）
            */
    private String aSource;

            /**
            * 文章链接（非转载类型，此字段为空）
            */
    private String aLink;

            /**
            * 点赞数
            */
    private Long aLike;

            /**
            * 收藏数
            */
    private Integer aCollect;

            /**
            * 评论数
            */
    private Long aCritic;

            /**
            * 创建时间
            */
    private LocalDateTime aCreateTime;


}
