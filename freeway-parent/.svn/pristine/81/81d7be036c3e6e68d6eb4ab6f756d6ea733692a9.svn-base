package net.chinahrd.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLSyntaxErrorException;
import java.util.Set;

/**
 * 全局异常
 * 
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalDefaultException{
    /**
     * 身份验证异常 加上http状态后，会导致前端获取信息不准确，得不到后端
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(AuthenticationException.class)
    public BaseResponse<String> handleException(AuthenticationException e) {
        log.error("身份验证异常:", e);
        return new BaseResponse<String>().error("用户名或密码错误！");
    }

     @ExceptionHandler(WebRuntimeException.class)
     public BaseResponse<String> handleException(WebRuntimeException e) {
     return new BaseResponse<String>().error(e.getMessage());
     }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse<String> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        log.error("缺少请求参数", e);
        return new BaseResponse<String>().error("required_parameter_is_not_present");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        return new BaseResponse<String>().error("could_not_read_json");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new BaseResponse<String>().error(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public BaseResponse<String> handleBindException(BindException e) {
        log.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new BaseResponse<String>().error(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<String> handleServiceException(ConstraintViolationException e) {
        log.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new BaseResponse<String>().error("parameter:" + message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public BaseResponse<String> handleValidationException(ValidationException e) {
        log.error("参数验证失败", e);
        return new BaseResponse<String>().error("validation_exception");
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return new BaseResponse<String>().error("request_method_not_supported");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResponse<String> handleHttpMediaTypeNotSupportedException(GlobalDefaultException e) {
        log.error("不支持当前媒体类型", e);
        return new BaseResponse<String>().error("content_type_not_supported");
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public BaseResponse<String> handleServiceException(ServiceException e) {
        log.error("业务逻辑异常", e);
        return new BaseResponse<String>().error("业务逻辑异常：" + e.getMessage());
    }

    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    /*
     * @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     * 
     * @ExceptionHandler(IbatisException.class) public BaseResponse<String>
     * handleException(IbatisException e) { log.error("操作数据库出现异常:", e);
     * return new BaseResponse<String>().error("操作数据库出现异常：字段重复、有外键关联等"); }
     * 
     *//**
       * 操作数据库出现异常:返回记录有多条
       *//*
         * @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
         * 
         * @ExceptionHandler(TooManyResultsException.class) public
         * BaseResponse<String> handleException(TooManyResultsException e) {
         * log.error("操作数据库出现异常:", e); return new
         * BaseResponse<String>().error("操作数据库出现异常：返回记录有多条"); }
         */

    /**
     * 操作数据库出现异常:返回记录有多条
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public BaseResponse<String> handleException(SQLSyntaxErrorException e) {
        log.error("操作数据库出现异常:", e);
        return new BaseResponse<String>().error("操作数据库出现异常");
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handleException(Exception e) {
        log.error("通用异常", e);
        return new BaseResponse<String>().error("通用异常：" + e.getCause());
    }

}

class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServiceException(String msg) {
        super(msg);
    }
}