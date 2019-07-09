package net.chinahrd.eis.aop;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.RemoteUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jxzhang on 2016年11月9日
 * @Verdion 1.0 版本
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    private static Logger logger = LoggerFactory.getLogger(LogExecutionAspect.class);

    @Autowired
    private LogDao logDao;

    @Override
    public void information(AopInformation aopInfo) {
        // *========控制台输出=========*//
        logger.info("方法描述:" + aopInfo.getDescription());
        logger.info("请求方法:" + aopInfo.getMethodFull());
        // System.out.println("请求参数对象:" + aopInfo.getParams());
        logger.info("请求人:" + aopInfo.getUserKey() + " " + aopInfo.getUserNameCh());
        logger.info("请求IP:" + aopInfo.getIpAddress());
        logger.info("用时（秒）:" + aopInfo.getUseTime() + " millis");

        // *========数据库日志=========*//
        if (aopInfo.isWriteDb()) {
            // aopInfo.setExceptionCode(0);
            aopInfo.setExceptionDetail(null);
            aopInfo.setParams(null);
            aopInfo.setCreateDate(DateUtil.getTimestamp());
            if (aopInfo.getType() <= 2) {
                aopInfo.setUserLoginId(Identities.uuid2());
                logDao.insertLoginLog(aopInfo);
            } else {
                logDao.insertOperateLog(aopInfo);
            }
        }
    }

    @Override
    public void insertLoginLog(AopInformation aopInfo) {
        logDao.insertLoginLog(aopInfo);
    }

    @Override
    public void insertOperateLog(String clazz, String description) {
        insertOperateLog(null, clazz, description);
    }

    @Override
    public void insertOperateLog(String optUser, String clazz, String description) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AopInformation aopInfo = new AopInformation();
        aopInfo.setIpAddress(RemoteUtil.getRemoteIP(request));
        if (StringUtils.isBlank(optUser)) {
            RbacUser rbacUser = EisWebContext.getCurrentUser();
            aopInfo.setUserId(rbacUser.getEmpId());
            aopInfo.setUserKey(rbacUser.getUserKey());
            aopInfo.setUserName(rbacUser.getUsername());
            aopInfo.setUserNameCh(rbacUser.getUserNameCh());
        } else {
            aopInfo.setUserId(null);
            aopInfo.setUserKey(optUser);
            aopInfo.setUserName(null);
            aopInfo.setUserNameCh(null);
        }
        aopInfo.setClazz(clazz);
        aopInfo.setDescription(description);
        logDao.insertOperateLog(aopInfo);
    }


}
