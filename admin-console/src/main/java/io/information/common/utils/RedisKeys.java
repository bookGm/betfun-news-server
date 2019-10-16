

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
}
