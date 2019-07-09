package net.chinahrd.common;


import net.chinahrd.enums.ResponseStatusEnum;

import java.io.Serializable;

/**
 * @author guanjian
 *
 * @param <T>
 */
public class BaseResponse<T> implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String error) {
        this.message = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResponse() {
    }

    public BaseResponse<T> success(T t) {
        setCode(ResponseStatusEnum.SUCCESS.getCode());
        setMessage(ResponseStatusEnum.SUCCESS.getValue());
        setData(t);
        return this;
    }

    public BaseResponse<T> error(String msg, int code) {
        setCode(code);
        setMessage(msg);
        return this;
    }

    public BaseResponse<T> error(String msg) {
        setCode(ResponseStatusEnum.ERROR.getCode());// 错误码
        setMessage(msg);
        return this;
    }

    @Override
    public String toString() {
        return "BaseResponse [code=" + code + ", " + (message != null ? "message=" + message + ", " : "")
                + (data != null ? "data=" + data : "") + "]";
    }

}
