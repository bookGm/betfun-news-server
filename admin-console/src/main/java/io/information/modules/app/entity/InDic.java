package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 资讯字典表
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InDic implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 字典id
            */
            @TableId(value = "d_id", type = IdType.AUTO)
    private Long dId;

            /**
            * 字典名称
            */
    private String dName;

            /**
            * 字典值
            */
    private String dValue;

            /**
            * 字典编码
            */
    private String dCode;

            /**
            * 字典父编码
            */
    private String dPcode;

            /**
            * 是否禁用（0：否  1：是）
            */
    private Integer dDisable;

            /**
            * 字典资源路径
            */
    private String dUrl;


}
