package net.chinahrd.modules.pub.service.impl;

import net.chinahrd.common.SysLog;
import net.chinahrd.modules.pub.entity.LogInfo;
import net.chinahrd.modules.pub.service.LogInfoService;
import net.chinahrd.service.log.SysLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  日志代理封装
 * </p>
 *
 * @author bright
 * @since 2019/3/21 17:32
 */
@Service
public class AdminSysLogService implements SysLogService {

    @Resource
    private LogInfoService logInfoService;

    @Override
    public void insert(SysLog sysLog) {
        LogInfo logInfo = new LogInfo();
        BeanUtils.copyProperties(sysLog, logInfo);
        logInfoService.save(logInfo);
    }

    @Override
    public void updateById(SysLog sysLog) {
        LogInfo logInfo = new LogInfo();
        BeanUtils.copyProperties(sysLog, logInfo);
        logInfoService.updateById(logInfo);
    }
}
