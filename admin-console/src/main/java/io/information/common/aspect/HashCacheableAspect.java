

package io.information.common.aspect;

import com.guansuo.common.StringUtil;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.DataUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * redis Hash缓存，切面处理类
 *
 * @author LX
 */
@Aspect
@Component
public class HashCacheableAspect {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(io.information.common.annotation.HashCacheable)")
    public void cachePointCut() {

    }

    @Around("cachePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (null == args || args.length < 1) {
            return invokeMethod(point);
        }
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        Method method = methodSignature.getMethod();
        HashCacheable hash = method.getAnnotation(HashCacheable.class);
        String key = hash.key();
        String filed = hash.keyField();
        if (StringUtil.isBlank(filed)) {
            filed = parameterNames[0];
        } else {
            for (int i = 0, len = parameterNames.length; i < len; i++) {
                filed = filed.replace("#" + parameterNames[i], String.valueOf(args[i]));
            }
        }
        if (redisTemplate.opsForHash().hasKey(key, filed)) {
            return redisTemplate.opsForHash().get(key, filed);
        } else {
            Object result = invokeMethod(point);
            if (StringUtil.isBlank(result)) {
                return null;
            }
            redisTemplate.opsForHash().put(key, filed, DataUtils.objToString(result));
            return result;
        }
    }

    Object invokeMethod(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        return result;
    }

}
