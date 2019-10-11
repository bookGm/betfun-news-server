package com.guansuo.newsenum;

public enum NewsEnum {
    帖子分类_普通帖("0","普通帖"),帖子分类_辩论帖("1","辩论帖"),帖子分类_投票帖("1","投票帖");

    NewsEnum(String code, String name) {
        this.code=code;
        this.name=name;
    }
    private String code;
    private String name;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 获取名称
     */
    public static NewsEnum getName(String code) {
        NewsEnum [] es=NewsEnum.values();
        for (NewsEnum e : es) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}
