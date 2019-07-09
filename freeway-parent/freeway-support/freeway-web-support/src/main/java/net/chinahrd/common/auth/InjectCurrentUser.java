package net.chinahrd.common.auth;

import java.lang.annotation.*;

/**
 * <p>
 *  api层自动注入当前用户注解
 * </p>
 *
 * @author bright
 * @since 2019/3/13 14:34
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface InjectCurrentUser {
    String value() default "user";
}
