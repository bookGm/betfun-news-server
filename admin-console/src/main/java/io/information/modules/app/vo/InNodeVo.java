package io.information.modules.app.vo;

import io.information.modules.app.entity.InNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 节点信息前端
 * </p>
 */
@ApiModel(value = "节点列表数据", description = "节点列表数据对象")
public class InNodeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点信息
     */
    @ApiModelProperty(value = "节点信息", name = "node")
    private InNode node;
    /**
     * 帖子总数
     */
    @ApiModelProperty(value = "帖子总数", name = "cardNumber", dataType = "Integer")
    private Integer cardNumber;


    public InNode getNode() {
        return node;
    }

    public void setNode(InNode node) {
        this.node = node;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }
}
