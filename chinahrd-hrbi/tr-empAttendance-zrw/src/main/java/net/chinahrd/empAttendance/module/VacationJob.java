package net.chinahrd.empAttendance.module;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import net.chinahrd.entity.dtozrw.pc.empAttendance.EmpVacationDto;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.job.JobRegisterAbstract;
import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.entity.dtozrw.pc.empAttendance.EmpAttendanceDto;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

/**
 * 每天1月7号更新大家年假
 *
 * @author jxzhang on 2017年1月7日
 * @Verdion 1.0 版本
 */
public class VacationJob extends JobRegisterAbstract {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Injection
    private VacationDao vacationDao;

    @Override
    public void execute(JobContext context) {

        boolean flag = checkIsExist();
        if (flag) {
            logger.info("本年年假已更新");
        } else {
            updateAnnualMain();
        }
    }

    /**
     * 每晚检查是否有本年的年假已更新
     *
     * @return
     */
    private boolean checkIsExist() {
        int v = vacationDao.checkIsExist(DateTime.now().toString("yyyy"));
        return v > 0 ? true : false;
    }

    @Override
    public void setTimerConfig(TimerConfig tc) {
        tc.setPriority(4);
        // 每晚1点30分
        tc.setCron("0 30 1 * * ?");
        // 测试 每10秒
//        tc.setCron("0/10 * * * * ?");
    }

    /**
     * 组装参数对象
     *
     * @param params    组装参数结果集
     * @param empAnnual 员工剩余年假数
     * @param num       新的年假数
     * @param empId     操作对像Id
     */
    private void wrapParamLogin(List<EmpAttendanceDto> params, float empAnnual, float num, String empId) {
        EmpAttendanceDto paramDto = new EmpAttendanceDto();

        if (empAnnual < 0) {
            // 业务：预支年假的情况做抵扣
            float calc = empAnnual + num;
            paramDto.setAnnual(calc);
        } else {
            paramDto.setAnnual(num);
        }
        paramDto.setEmpId(empId);
        paramDto.setRefresh(DateUtil.getDBCurdate());
        params.add(paramDto);
    }

    /**
     * 更新年假核心
     */
    public void updateAnnualMain() {

        List<EmpAttendanceDto> annualEdRs = vacationDao.queryAnnual();

        String yyyy = new DateTime().toString("yyyy");
        List<EmpVacationDto> annualRs = vacationDao.queryAnnualHistory(yyyy);

        List<EmpAttendanceDto> params = CollectionKit.newList();
        logger.debug(JSON.toJSONString(annualRs));

        for (EmpVacationDto emp : annualRs) {
            String empId = emp.getEmpId();
            for (EmpAttendanceDto i : annualEdRs) {
                String empEdId = i.getEmpId();
                if (StringUtils.equals(empEdId, empId)) {
                    wrapParamLogin(params, i.getAnnual(), emp.getAnnualTotal() + emp.getAnnualDelayed(), emp.getEmpId());
                }
            }
        }
        int sourceCount = annualRs.size();
        updateAnnual(params, sourceCount);
    }

    /**
     * 更新数据库
     *
     * @param params
     * @param sourceCount 数据源总条件
     */
    private void updateAnnual(List<EmpAttendanceDto> params, int sourceCount) {
        int optCount = 0;
        try {
            for (EmpAttendanceDto param : params) {
                int i = vacationDao.updateAnnual(param);
                optCount = optCount + i;
            }
            if (sourceCount == optCount) {
                logger.info("更新成功，操作总条数：{}", sourceCount);
            } else {
                logger.info("更新完成，操作总条数：{}, 已操作条数：{}", sourceCount, optCount);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 读jar包里的properties
     *
     * @param filePath
     * @param key
     * @return
     */
    private static String readValue(String filePath, String key) {
        Properties props = new Properties();
        try {
            // InputStream ips = new BufferedInputStream(new FileInputStream(filePath));
            InputStream ips = VacationJob.class.getResourceAsStream(filePath);
            BufferedReader ipss = new BufferedReader(new InputStreamReader(ips));
            props.load(ipss);
            String value = props.getProperty(key);
            return value;
        } catch (FileNotFoundException e) {
            System.out.println("无法找到文件:" + filePath);
            return null;
        } catch (IOException e) {
            System.out.println("读文件出错:" + filePath + "---" + e.getMessage());
            return null;
        }
    }
}
