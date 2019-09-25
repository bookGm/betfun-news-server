package io.information.modules.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 资讯活动表
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InActivity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 活动id
            */
    private Long actId;

            /**
            * 用户id
            */
    private Long uId;

            /**
            * 活动内容
            */
    private String actTitle;

            /**
            * 活动时间
            */
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actTime;

            /**
            * 活动分类（字典）
            */
    private Integer actCategory;

            /**
            * 活动地址（省市县编码横线分隔）
            */
    private String actAddr;

            /**
            * 活动地址详情
            */
    private String actAddrDetail;

            /**
            * 活动人数
            */
    private Long actNum;

            /**
            * 已参加人数
            */
    private Long actInNum;

            /**
            * 活动封面URL
            */
    private String actCover;

            /**
            * 活动详情
            */
    private String actDetail;

            /**
            * 活动开始时间
            */
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actStartTime;

            /**
            * 活动结束时间
            */
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actCloseTime;

            /**
            * 活动创建时间
            */
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actCreateTime;


}
