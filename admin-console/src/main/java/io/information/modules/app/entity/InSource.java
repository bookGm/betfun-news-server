package io.information.modules.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 资讯资源表
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InSource implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 资源路径
            */
    private String sUrl;

            /**
            * 组件名称
            */
    private String sComponent;

            /**
            * 资源名称
            */
    private String sName;

            /**
            * 系统操作用户id
            */
    private Long sOperationUserid;

            /**
            * 是否禁用（0：否 1：是）
            */
    private Integer sDisable;

            /**
            * 更新时间
            */
    private LocalDateTime sUpdateTime;

            /**
            * 资源创建时间
            */
    private LocalDateTime sCreateTime;


}
