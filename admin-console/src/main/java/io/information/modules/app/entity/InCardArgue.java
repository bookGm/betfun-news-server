package io.information.modules.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 资讯帖子辩论表
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InCardArgue implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 帖子id
            */
    private Long cId;

            /**
            * 正方观点
            */
    private String caFside;

            /**
            * 反方观点
            */
    private String caRside;

            /**
            * 正方观点投票人ids，逗号分隔
            */
    private String caFsideUids;

            /**
            * 反方观点投票人ids，逗号分隔
            */
    private String caRsideUids;

            /**
            * 辩论结束日期
            */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime caCloseTime;


}
