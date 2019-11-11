package com.guansuo.newsenum;

public enum NewsEnum {
    帖子分类_普通帖("0","普通帖"),帖子分类_辩论帖("1","辩论帖"),帖子分类_投票帖("2","投票帖"),
    标签来源_爬虫抓取("0","爬虫抓取"),标签来源_后台维护("1","后台维护"),
    标签类型_行业要闻("1","行业要闻"),标签类型_技术前沿("2","技术前沿"),
    文章类型_原创("0","原创"),文章类型_转载("1","转载"),
    快讯_利空("0","利空"),快讯_利好("1","利好"),
    点赞_文章("0","文章"),点赞_帖子("1","帖子"),点赞_活动("2","活动"),
    收藏_文章("0","收藏文章"),收藏_帖子("1","收藏帖子"),收藏_活动("2","收藏活动"),
    用户认证状态_未通过("0","未通过"),用户认证状态_审核中("1","审核中"),用户认证状态_审核通过("2","审核通过"),
    用户认证类型_个人("0","个人"),用户认证类型_媒体("1","媒体"),用户认证类型_企业("2","企业"),
    用户类型_抓取用户("-1","抓取用户"),用户类型_普通用户("0","普通用户"),用户类型_红人榜("1","红人榜"),用户类型_黑榜("2","黑榜"),
    文章状态_草稿箱("0","草稿箱"),文章状态_审核中("1","审核中"),文章状态_已发布("2","已发布"),
    操作_点赞("0","点赞"),操作_收藏("1","收藏"),操作_关注("2","关注"),操作_评论("3","评论"),操作_发布("4","发布"),
    评论_文章("0","文章"),评论_帖子("1","帖子"),评论_活动("2","活动"),评论_用户("3","用户"),
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
