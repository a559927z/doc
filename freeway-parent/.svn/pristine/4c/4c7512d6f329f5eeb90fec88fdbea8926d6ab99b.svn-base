package net.chinahrd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** 	 
 * 导出标签 <br/> 	  
 * date: 2018年12月18日 下午1:43:00 <br/> 	 
 * 	 
 * @author 伟 	  
*/
@Documented
@Retention(RUNTIME)
@Target({ FIELD })
public @interface ExportExcel{
    /**
     * 中文描述
     */
    String name() default "";
    /**
     * 中文描述
     */
    String example() default "";
    /**
     * Specifies if the parameter is required or not.
     */
    boolean export() default true;
    
    /**
     * Allows explicitly ordering the property in the model.
     */
    int indexCol() default 0;

}
