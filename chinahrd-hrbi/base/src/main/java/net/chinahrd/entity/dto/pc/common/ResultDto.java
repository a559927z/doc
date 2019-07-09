package net.chinahrd.entity.dto.pc.common;

/**
 * Created by wqcai on 15/6/15.
 */
public class ResultDto<T> {


    private boolean type = false;   //结果类型
    private String msg;     //提醒消息
    private T t;            //返回的数据

    public ResultDto() {
    }

    public ResultDto(T t, String msg) {
        this.t = t;
        this.msg = msg;
    }

    public ResultDto(boolean type) {
        this.type = type;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

}
