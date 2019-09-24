package io.information.modules.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 资讯帖子基础表
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InCardBase implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 帖子id
            */
    private Long cId;

            /**
            * 用户id
            */
    private Long uId;

            /**
            * 帖子分类（字典）
            */
    private Integer cCategory;

            /**
            * 帖子节点分类（字典）
            */
    private Integer cNodeCategory;

            /**
            * 帖子正文
            */
    private String cContent;

            /**
            * 回帖仅作者可见（0：是  1：否）
            */
    private Integer cHide;


}
