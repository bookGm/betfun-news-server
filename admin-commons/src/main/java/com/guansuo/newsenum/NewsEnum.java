package com.guansuo.newsenum;

public enum NewsEnum {
    帖子分类_普通帖("0","普通帖"),帖子分类_辩论帖("1","辩论帖"),帖子分类_投票帖("2","投票帖"),
    标签来源_爬虫抓取("0","爬虫抓取"),标签来源_后台维护("1","后台维护"),
    标签类型_行业要闻("1","行业要闻"),标签类型_技术前沿("2","技术前沿"),
    文章类型_原创("0","原创"),文章类型_转载("1","转载"),
    点赞_文章("0","文章点赞"),点赞_帖子("1","帖子点赞"),点赞_活动("2","活动点赞"),
    收藏_文章("0","收藏文章"),收藏_帖子("1","收藏帖子"),收藏_活动("2","收藏活动")
    ;

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
