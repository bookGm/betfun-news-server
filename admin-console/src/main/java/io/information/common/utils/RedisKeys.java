

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
    public static String LOGIN_PHONECODE=BASEKEY+"login:phonecode:";
}
