package io.information.modules.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 资讯菜单资源关系表
    * </p>
*
* @author ZXS
* @since 2019-09-25
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InMenuSource implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long msId;

            /**
            * 菜单名称
            */
    private String mName;

            /**
            * 资源名称
            */
    private String sName;

    private String mCode;

            /**
            * 资源路径
            */
    private String sUrl;


}
