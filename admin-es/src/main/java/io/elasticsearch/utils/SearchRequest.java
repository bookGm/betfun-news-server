package io.elasticsearch.utils;

import java.util.Map;

/**
 * 搜索过滤条件及分页工具类
 */

public class SearchRequest {

    //关键字
    private String key;

    //过滤条件
    private Map<String,String> filter;

    //当前页数
    private Integer currPage;
    private static final Integer DEFAULT_PAGE = 0;// 默认页

    //条数
    private Integer pageSize = 10;


    public Integer getCurrPage() {
        if(currPage == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于0
        return Math.max(DEFAULT_PAGE, currPage);
    }
    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Map<String, String> getParams() {
        return filter;
    }
    public void setParams(Map<String, String> params) {
        this.filter = params;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
