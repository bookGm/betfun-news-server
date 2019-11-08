package io.information.modules.news.service.feign.vo;

import java.io.Serializable;

/**
 * 巴比特作者信息
 */
public class AuthorInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private String id;
    /**
     * 账户名
     */
    private String name;
    /**
     * 用户显示名称（昵称）
     */
    private String display_name;
    /**
     * 用户简介
     */
    private String desc;
    /**
     * 用户比特币地址
     */
    private String bitcoin_address;
    /**
     * 用户比特币二维码
     */
    private String bitcoin_qrcode;
    /**
     * 用户比原链地址
     */
    private String bytom_address;
    /**
     * 用户比原链二维码
     */
    private String bytom_qrcode;
    /**
     * 形象照
     */
    private String avatar;
    /**
     * 多尺寸形象照
     */
    private String[] avatars;
    /**
     * 未知状态
     */
    private Integer is_viking;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBitcoin_address() {
        return bitcoin_address;
    }

    public void setBitcoin_address(String bitcoin_address) {
        this.bitcoin_address = bitcoin_address;
    }

    public String getBitcoin_qrcode() {
        return bitcoin_qrcode;
    }

    public void setBitcoin_qrcode(String bitcoin_qrcode) {
        this.bitcoin_qrcode = bitcoin_qrcode;
    }

    public String getBytom_address() {
        return bytom_address;
    }

    public void setBytom_address(String bytom_address) {
        this.bytom_address = bytom_address;
    }

    public String getBytom_qrcode() {
        return bytom_qrcode;
    }

    public void setBytom_qrcode(String bytom_qrcode) {
        this.bytom_qrcode = bytom_qrcode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String[] getAvatars() {
        return avatars;
    }

    public void setAvatars(String[] avatars) {
        this.avatars = avatars;
    }

    public Integer getIs_viking() {
        return is_viking;
    }

    public void setIs_viking(Integer is_viking) {
        this.is_viking = is_viking;
    }
}
