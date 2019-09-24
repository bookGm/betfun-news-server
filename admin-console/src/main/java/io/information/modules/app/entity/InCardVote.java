package io.information.modules.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 资讯投票帖详情（最多30个投票选项）
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InCardVote implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 帖子id
            */
    private Integer cId;

            /**
            * 投票选项信息（逗号分隔）
            */
    private String cvInfo;

            /**
            * 结束日期
            */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime cvCloseTime;

    private Integer cv0;

    private Integer cv1;

    private Integer cv2;

    private Integer cv3;

    private Integer cv4;

    private Integer cv5;

    private Integer cv6;

    private Integer cv7;

    private Integer cv8;

    private Integer cv9;

    private Integer cv10;

    private Integer cv11;

    private Integer cv12;

    private Integer cv13;

    private Integer cv14;

    private Integer cv15;

    private Integer cv16;

    private Integer cv17;

    private Integer cv18;

    private Integer cv19;

    private Integer cv20;

    private Integer cv21;

    private Integer cv22;

    private Integer cv23;

    private Integer cv24;

    private Integer cv25;

    private Integer cv26;

    private Integer cv27;

    private Integer cv28;

    private Integer cv29;


}
