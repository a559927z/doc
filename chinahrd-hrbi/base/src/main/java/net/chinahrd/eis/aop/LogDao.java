package net.chinahrd.eis.aop;

import org.springframework.stereotype.Repository;

/**
 * @author jxzhang on 2016年11月9日
 * @Verdion 1.0 版本
 */
//@Repository("logDao")
@Repository
public interface LogDao {
    /**
     * 添加用户登录记录
     *
     * @param loginLog 用户登录对象
     */
    void insertLoginLog(AopInformation loginLog);

    /**
     * 添加操作日志
     */
    void insertOperateLog(AopInformation loginLog);
}
