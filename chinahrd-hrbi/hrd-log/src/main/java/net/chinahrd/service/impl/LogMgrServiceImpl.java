package net.chinahrd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.dto.AopInformation;
import net.chinahrd.utils.DateUtil2;
import net.chinahrd.utils.Identities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chinahrd.dao.EmpAttendanceDao;
import net.chinahrd.dao.LogMgrDao;
import net.chinahrd.dto.EmpAttendanceDto;
import net.chinahrd.dto.EmpOtherDayDto;
import net.chinahrd.dto.LogDto;
import net.chinahrd.service.LogMgrService;

@Slf4j
@Service("logMgrService")
public class LogMgrServiceImpl implements LogMgrService {

    @Autowired
    private LogMgrDao logMgrDao;
    @Autowired
    private EmpAttendanceDao empAttendanceDao;

    @Override
    public List<LogDto> queryAllLog(int startIndex, int pageSize, String search) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startIndex", startIndex);
        paramMap.put("pageSize", pageSize);
        paramMap.put("search", search);
        return logMgrDao.queryAllLog(paramMap);
    }

    @Override
    public Long countAllLog(String search) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("search", search);
        return logMgrDao.countAllLog(paramMap);
    }

    @Override
    public List<EmpAttendanceDto> queryEmpVacation(int start, int length, String search) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("search", search);
        return empAttendanceDao.queryEmpVacation(paramMap);
    }

    @Override
    public int countEmpVacation() {
        return empAttendanceDao.countEmpVacation();
    }

    @Override
    public List<EmpOtherDayDto> queryEmpOtherDay(int start, int length, int startYm, int endYm, String search) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("search", search);
        paramMap.put("startYm", startYm);
        paramMap.put("endYm", endYm);
        return empAttendanceDao.queryEmpOtherDay(paramMap);
    }

    @Override
    public int countEmpOtherDay() {
        return empAttendanceDao.countEmpOtherDay();
    }

    @Override
    public List<String> queryEmpOtherDayYear() {
        return empAttendanceDao.queryEmpOtherDayYear();
    }

    @Override
    public void information(AopInformation aopInfo) {
        // *========控制台输出=========*//
//        log.info("方法描述:" + aopInfo.getDescription());
//        log.info("请求方法:" + aopInfo.getMethodFull());
        // System.out.println("请求参数对象:" + aopInfo.getParams());
//        log.info("请求人:" + aopInfo.getUserKey() + " " + aopInfo.getUserNameCh());
//        log.info("请求IP:" + aopInfo.getIpAddress());
//        log.info("用时（秒）:" + aopInfo.getUseTime() + " millis");

        // *========数据库日志=========*//
        if (aopInfo.isWriteDb()) {
            // aopInfo.setExceptionCode(0);
            aopInfo.setExceptionDetail(null);
            aopInfo.setParams(null);
            aopInfo.setCreateDate(DateUtil2.Get.Now.SERVER.timestamp());
            if (aopInfo.getType() <= 2) {
                aopInfo.setUserLoginId(Identities.uuid2());
                logMgrDao.insertLoginLog(aopInfo);
            } else {
                logMgrDao.insertOperateLog(aopInfo);
            }
        }
    }

}
