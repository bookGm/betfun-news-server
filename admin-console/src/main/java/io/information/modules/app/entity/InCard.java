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

    public InCardBase getBase() {
        return base;
    }

    public void setBase(InCardBase base) {
        this.base = base;
    }

    public InCardArgue getArgue() {
        return argue;
    }

    public void setArgue(InCardArgue argue) {
        this.argue = argue;
    }

    public InCardVote getVote() {
        return vote;
    }

    public void setVote(InCardVote vote) {
        this.vote = vote;
    }
}
