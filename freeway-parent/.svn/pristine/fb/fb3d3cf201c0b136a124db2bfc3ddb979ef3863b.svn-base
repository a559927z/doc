package net.chinahrd.common.validate;

import net.chinahrd.bean.CheckErrorResultBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** 	 
 * TODO <br/> 	  
 * date: 2018年10月10日 下午8:47:31 <br/> 	 
 * 	 
 * @author guanjian 	  
*/
public class ValidationUtil {
    private static ValidationUtil util;

    private Validator validator;

    public ValidationUtil() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    /**
     * @return the validator
     */
    public Validator getValidator() {
        return validator;
    }

    public static ValidationUtil getValidationUtil() {
        if (util == null) {
            util = new ValidationUtil();
        }
        return util;

    }

    public static List<CheckErrorResultBean> validate(Object o) {
        Validator validator2 = ValidationUtil.getValidationUtil().getValidator();
        Set<ConstraintViolation<Object>> set = validator2.validate(o);
        return getErrorList(set);
    }

    public static List<CheckErrorResultBean> getErrorList(Set<ConstraintViolation<Object>> set) {
        List<CheckErrorResultBean> errorList = null;
        for (ConstraintViolation<Object> cv : set) {
            if (errorList == null) {
                errorList = new ArrayList<CheckErrorResultBean>();
            }
            errorList.add(new CheckErrorResultBean(cv.getPropertyPath().toString(), cv.getMessage()));

        }
        return errorList;
    }

    /**
     * 验证实体列表的正确性
     * 
     * @param objList
     * @return
     */
    public static <T> List<CheckErrorResultBean> validateList(List<T> objList) {
        if (null == objList || objList.isEmpty()) {
            return null;
        }
        List<CheckErrorResultBean> strList = new ArrayList<CheckErrorResultBean>();
        for (Object obj : objList) {
            List<CheckErrorResultBean> checkErrorResultBean = validate(obj);
            if (null != checkErrorResultBean) {
                strList.addAll(validate(obj));
            }
        }
        return strList;
    }

    public static List<CheckErrorResultBean> validate(Object o, Class<?>... c) {
        Set<ConstraintViolation<Object>> set = ValidationUtil.getValidationUtil().getValidator().validate(o, c);
        return getErrorList(set);
    }

}
