package io.information.modules.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 资讯用户表
    * </p>
*
* @author ZXS
* @since 2019-09-24
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class InUser implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 用户id
            */
    private Long uId;

            /**
            * 用户姓名
            */
    private String uName;

            /**
            * 密码
            */
    private String uPwd;

            /**
            * 用户昵称
            */
    private String uNick;

            /**
            * 用户手机
            */
    private String uPhone;

            /**
            * 用户简介
            */
    private String uIntro;

            /**
            * 用户粉丝
            */
    private Long uFans;

            /**
            * 用户关注
            */
    private Integer uFocus;

            /**
            * 认证状态（0：未通过  1：通过 ）
            */
    private Integer uAuthStatus;

            /**
            * 用户令牌
            */
    private String uToken;


}
