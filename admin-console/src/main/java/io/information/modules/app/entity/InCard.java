package io.information.modules.app.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 资讯帖子表
 * </p>
 *
 * @author ZXS
 * @since 2019-09-25
 */

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InCard implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
             * 基础信息
             */
    private InCardBase base;
            /**
             * 辩论信息
             */
    private InCardArgue argue;
            /**
             * 投票信息
             */
    private InCardVote vote;

}
