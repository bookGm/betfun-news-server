package io.information.modules.app.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 资讯菜单总和
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InMSource implements Serializable {
        private static final long serialVersion = 1L;

            /**
             * 资讯菜单
             */
    private InMenu menu;
            /**
             * 资讯菜单资源关系
             */
    private InMenuSource menuSource;
            /**
             * 咨讯资源
             */
    private InSource source;
}
