package io.information.modules.app.dto;

import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InCardBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户收藏信息", description = "用户收藏信息")
public class CollectDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文章对象
     */
    @ApiModelProperty(value = "文章对象", name = "article", required = false)
    private InArticle article;

    /**
     * 帖子基础信息对象
     */
    @ApiModelProperty(value = "帖子基础信息对象", name = "cardBase", required = false)
    private InCardBase cardBase;

    /**
     * 活动对象
     */
    @ApiModelProperty(value = "活动对象", name = "activity", required = false)
    private InActivity activity;

    public InArticle getArticle() {
        return article;
    }

    public void setArticle(InArticle article) {
        this.article = article;
    }

    public InCardBase getCardBase() {
        return cardBase;
    }

    public void setCardBase(InCardBase cardBase) {
        this.cardBase = cardBase;
    }

    public InActivity getActivity() {
        return activity;
    }

    public void setActivity(InActivity activity) {
        this.activity = activity;
    }
}
