package io.information.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InTag implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 标签id
            */
            @TableId(value = "t_id", type = IdType.AUTO)
    private Long tId;

            /**
            * 标签名称
            */
    private String tName;

            /**
            * 标签来源（0：抓取  1：后台维护）
            */
    private Integer tFrom;

            /**
            * 标签创建时间
            */
    private LocalDateTime tCreateTime;


}
