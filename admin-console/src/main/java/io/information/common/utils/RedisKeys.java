

package io.information.common.utils;

/**
 * Redis所有Keys
 *
 * @author Mark sunlightcs@gmail.com
 */
public class RedisKeys {

    public static String getSysConfigKey(String key) {
        return "sys:config:" + key;
    }

    public static final String BASEKEY = "news:";
    //登录手机验证码
    public static final String LOGIN_PHONECODE = BASEKEY + "login:phonecode:";
    //常量
    public static final String CONSTANT = BASEKEY + "constant";
    //点赞
    public static final String LIKE = BASEKEY + "like";
    //收藏
    public static final String COLLECT = BASEKEY + "collect";
    //报名
    public static final String APPLY = BASEKEY + "apply";
    //app用户
    public static final String INUSER = BASEKEY + "inuser";
    //投票
    public static final String VOTE = BASEKEY + "vote";
    //文章浏览量
    public static final String ABROWSE = BASEKEY + "abrowse:";
    //活动浏览量
    public static final String ACTBROWSE = BASEKEY + "actbrowse:";
    //帖子浏览量
    public static final String CARDBROWSE = BASEKEY + "cardbrowse:";
    //文章P地址
    public static final String ABROWSEIP = BASEKEY + "abrowseip:";
    //活动IP地址
    public static final String ACTBROWSEIP = BASEKEY + "actbrowseip:";
    //帖子IP地址
    public static final String CARDBROWSEIP = BASEKEY + "cardbrowseip:";
    //辩论支持
    public static final String SUPPORT = BASEKEY + "support";
    //辩论辩手
    public static final String JOIN = BASEKEY + "join";
    //关注
    public static final String FOCUS = BASEKEY + "focus";
    //节点关注
    public static final String NODES = BASEKEY + "nodes";
    //文章id
    public static final String ARTICLE = BASEKEY + "article:";
    //快讯id
    public static final String NEWSFLASH = BASEKEY + "newsflash:";
    //标签名
    public static final String TAGNAME = BASEKEY + "tagname:";
    //作者id
    public static final String AUTHORID = BASEKEY + "authorid:";
    //快讯利好利空
    public static final String NATTITUDE = BASEKEY + "nattitude:";

    //-------------------------锁-------------------------//
    public static final String BASELOCK = "redis_lock:";
    //抓取文章锁
    public static final String CATCH_ARTICLE_LOCK = BASELOCK + "catch_article_lock";
    //抓取咨询锁
    public static final String CATCH_NEWS_LOCK = BASELOCK + "catch_news_lock";
}
