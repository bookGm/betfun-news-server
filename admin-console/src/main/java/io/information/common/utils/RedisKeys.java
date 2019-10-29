

package io.information.common.utils;

/**
 * Redis所有Keys
 *
 * @author Mark sunlightcs@gmail.com
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
    public static final String BASEKEY="news:";
    //登录手机验证码
    public static final String LOGIN_PHONECODE=BASEKEY+"login:phonecode:";
    //常量
    public static final String CONSTANT=BASEKEY+"constant";
    //点赞
    public static final String LIKE=BASEKEY+"like";
    //收藏
    public static final String COLLECT=BASEKEY+"collect";
    //app用户
    public static final String INUSER=BASEKEY+"inuser";
    //投票
    public static final String VOTE=BASEKEY+"vote";
    //浏览量
    public static final String BROWSE=BASEKEY+"browse:";
    //IP地址
    public static final String BROWSEIP=BASEKEY+"browseip:";
    //辩论支持
    public static final String SUPPORT=BASEKEY+"support";
    //辩论辩手
    public static final String JOIN=BASEKEY+"join";
    //关注
    public static final String FOCUS=BASEKEY+"focus";
    //文章id
    public static final String ARTICLE=BASEKEY+"article:";
    //快讯id
    public static final String NEWSFLASH=BASEKEY+"newsflash:";
    //标签名
    public static final String TAGNAME=BASEKEY+"tagname:";
}
