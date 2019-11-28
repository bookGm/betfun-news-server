package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "社区 -- 用户帖子数据", description = "用户信息和帖子信息")
public class CardUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", name = "uId", required = true)
    private Long uId;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "uNick")
    private String uNick;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", name = "uPhoto")
    private String uPhoto;

    /**
     * 用户简介
     */
    @ApiModelProperty(value = "用户简介", name = "uIntro")
    private String uIntro;

    /**
     * 用户帖子数量
     */
    @ApiModelProperty(value = "用户帖子数量", name = "cardNumber")
    private Integer cardNumber;

    /**
     * 用户帖子获赞数量
     */
    @ApiModelProperty(value = "用户帖子获赞数量", name = "cLike")
    private Long cLike;

    /**
     * 用户帖子推荐信息
     */
    @ApiModelProperty(value = "用户帖子信息", name = "cardBaseVos")
    private List<CardBaseVo> cardBaseVos;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数", name = "totalCount", required = true)
    private int totalCount;
    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数", name = "pageSize", required = true)
    private int pageSize;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", name = "totalPage", required = true)
    private int totalPage;
    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数", name = "currPage", required = true)
    private int currPage;

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getuNick() {
        return uNick;
    }

    public void setuNick(String uNick) {
        this.uNick = uNick;
    }

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public String getuIntro() {
        return uIntro;
    }

    public void setuIntro(String uIntro) {
        this.uIntro = uIntro;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<CardBaseVo> getCardBaseVos() {
        return cardBaseVos;
    }

    public void setCardBaseVos(List<CardBaseVo> cardBaseVos) {
        this.cardBaseVos = cardBaseVos;
    }

    public Long getcLike() {
        return cLike;
    }

    public void setcLike(Long cLike) {
        this.cLike = cLike;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
        ;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }
}
