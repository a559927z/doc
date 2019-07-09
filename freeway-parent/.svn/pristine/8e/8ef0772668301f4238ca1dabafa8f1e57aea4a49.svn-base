package net.chinahrd.common.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** 	 
 * TODO <br/> 	  
 * date: 2018年10月10日 下午8:47:46 <br/> 	 
 * 	 
 * @author guanjian 	  
*/
@Documented
@Retention(RUNTIME)
@Target({ METHOD })
public @interface MyValid {
    Class<?>[] value() default {};

    Class<?>[] group() default {};
}
