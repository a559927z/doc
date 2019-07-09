package net.chinahrd.deployment;

import java.lang.annotation.*;

/**
 * <p>
 *  用于根据参数自动切换数据源
 * </p>
 *
 * @author bright
 * @since 2019/3/18 15:07
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SwitchDataSource {
    String value() default "";
}
