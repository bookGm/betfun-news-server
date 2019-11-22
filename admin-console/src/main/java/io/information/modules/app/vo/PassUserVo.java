package io.information.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "活动用户", description = "报名数据")
public class PassUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报名数据(fName:上 dValue:下)")
    private List userMap;

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


    public PassUserVo(List userMap, int totalCount, int pageSize, int currPage) {
        this.userMap = userMap;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
        this.currPage = currPage;
    }

    public List getUserMap() {
        return userMap;
    }

    public void setUserMap(List userMap) {
        this.userMap = userMap;
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
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }
}
