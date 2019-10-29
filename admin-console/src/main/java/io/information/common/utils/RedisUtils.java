

package io.information.common.utils;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;
    private final static Gson gson = new Gson();

    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public boolean isFuzzyEmpty(String key){
        return getAllFuzzyKeys(key).isEmpty();
    }

    public Set<String> getAllFuzzyKeys(String key){
        Set<String> keys = redisTemplate.keys(key + "*");
        return keys;
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }

    /**
     * @Description: key是否存在
     * @param @param key
     * @param @return
     * @return boolean
     * @throws
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @Description: 获取keys
     * @param @param pattren
     * @param @return
     * @return List<String>
     * @throws
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<String> getAllKeys(String pattren){
        List<String> list = null;
        Set<String> set =redisTemplate.keys(pattren);
        if (set != null) {
            list = new ArrayList();
            for (Iterator it = set.iterator(); it.hasNext();) {
                list.add(it.next().toString());
            }
        }
        return list;
    }


    /**
     * @Description: 字符串key增加1
     * @param @param key
     * @param @return
     * @return long
     * @throws
     */
    public long incr(String key) {
        return incr(key,1);
    }

    /**
     * @Description: 字符串key增加指定数值
     * @param @param key
     * @param @param delta
     * @param @return
     * @return long
     * @throws
     */
    public long incr(String key , long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }


    /**
     * @Description: hash结构中某字段增加指定数值
     * @param @param key
     * @param @param hashKey  字段key
     * @param @param delta
     * @param @return
     * @return long
     * @throws
     */
    public long hincr(String key,Object hashKey ,long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * @Description: hash结构中某字段增加1
     * @param @param key
     * @param @param hashKey
     * @param @return
     * @throws
     */
    public long hincr(String key,Object hashKey) {
        return hincr( key, hashKey ,1);
    }

    /**
     * @Description: hash结构中某字段增加指定浮点数
     * @param @param key
     * @param @param hashKey
     * @param @param delta
     * @param @return
     * @return double
     * @throws
     */
    public double hincr(String key,Object hashKey ,double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 获取所有hash key
     * @param key
     * @return
     */
    public Set getAllHashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 添加hash
     * @param key
     * @param hashKey
     * @param value
     */
    public void hset(String key,Object hashKey,Object value){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }
    /**
     * 获取hash
     * @param key
     * @param hashKey
     * @return Object
     */
    public Object hget(String key,Object hashKey){
       return redisTemplate.opsForHash().get(key,hashKey);
    }

    /**
     * 批量添加hash
     * @param key
     * @param map
     */
    public void hmset(String key, Map<?,?> map){
        redisTemplate.opsForHash().putAll(key,map);
    }

}
