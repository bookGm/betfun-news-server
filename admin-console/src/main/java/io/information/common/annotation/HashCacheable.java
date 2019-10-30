

package io.information.common.annotation;

import java.lang.annotation.*;

/**
 * hash cacheable注解
 * @author LX
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HashCacheable {
	String key() default "";
	String keyField() default "";
}
