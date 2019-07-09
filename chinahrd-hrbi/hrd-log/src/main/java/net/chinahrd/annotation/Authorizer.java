package net.chinahrd.annotation;

import java.lang.annotation.*;

/**
 * Title: RunTime <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年1月26日 下午4:47:44
 * @Verdion 1.0 版本
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorizer {
}
