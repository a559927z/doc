package net.chinahrd.eis.aop;

public interface LogService {

    void insertLoginLog(AopInformation aopInformation);

    void information(AopInformation aopInfo);

    void insertOperateLog(String clazz, String description);

    void insertOperateLog(String optUser, String clazz, String description);
}
