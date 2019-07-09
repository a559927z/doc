package net.chinahrd.service.log;

import net.chinahrd.common.SysLog;

/**
 * <p>
 *  日志抽象接口信息，可以在各自相关的服务实现类中调用具体的日志实体服务类
 * </p>
 *
 * @author bright
 * @since 2019/3/19 12:13
 */
public interface SysLogService {

    /**
     * 写入日志
     * @param sysLog
     */
    void insert(SysLog sysLog);

    /**
     * 更新日志，主要是如果有报错写入错误信息
     * @param sysLog
     */
    void updateById(SysLog sysLog);
}
