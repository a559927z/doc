package net.chinahrd.enums;

/** 	 
 * TODO <br/> 	  
 * date: 2018年12月4日 下午2:31:11 <br/> 	 
 * @author guanjian 	  
*/
public enum ResponseStatusEnum {
    ERROR(0, "error"), SUCCESS(1, "success"),PARM_CHECK_ERROR(2,"参数校验错误！"),SESSION_TIMEOUT(3,"session已过期！");

    private String value;

    private Integer code;

    private ResponseStatusEnum(Integer code , String value) {
        this.value = value;
        this.code =code;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
