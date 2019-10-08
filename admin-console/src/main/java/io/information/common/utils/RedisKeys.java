

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
    public static String BASEKEY="news:";
    //登录手机验证码
    public static String LOGIN_PHONECODE=BASEKEY+"login:phonecode:";
    //常量：城市
    public static String  CONSTANT_CITYS=BASEKEY+"constant:citys";
}
