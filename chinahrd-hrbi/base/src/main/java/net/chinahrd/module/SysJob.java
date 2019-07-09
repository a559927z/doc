/**
 * net.chinahrd.module
 */
package net.chinahrd.module;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.job.JobRegisterAbstract;
import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.entity.enums.TipsEnum;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.holiday.DaysDto;

/**
 * @author htpeng 2016年11月9日下午4:55:29
 * @author jxzhang 2017年02月14日下午14:33:00
 * @version 1.1
 * <p>
 * 注意:日和星期是任先其一 ?:代表可有可无 :代表每一年 秒 分 时 日 月 星期几 年 0 0 0 10 12 ? 2009
 * //代表:2009年12月10日0点0分0秒执行(星期几:'?'代表忽略) 0 0 0 10 12 ? *
 * //代表:每年12月10日0点0分0秒执行 0 0 0 10 * ? //代表:每月10日0点0分0秒执行 0 0 1 1 * ?
 * //代表:每月1号1点0分0秒执行 0 0 1 1 3,6,9 ? //代表:3月 6月 9月,1号1点0分0秒执行 0 0 1 1
 * 2-5 ?
 */
public class SysJob extends JobRegisterAbstract {

    public static final Logger log = LoggerFactory.getLogger(SysJob.class);

    @Injection
    private CommonService commonService;

    @Override
    public void execute(JobContext context) {
        log.info(TipsEnum.JOB_BEGIN.getDesc(), commonService);
        DateTime year = new DateTime().withMonthOfYear(1).withDayOfMonth(1); // 当前年第一天
        DateTime nextYear = year.plusYears(1);
        String startDay = year.toString("yyyy-MM-dd");
        String endDay = nextYear.toString("yyyy-MM-dd");
        int ym = DateUtil.convertToIntYearMonth(DateUtil.getDBCurdate());

        // 新的一年日表维度
        initDataDays(year, startDay, endDay, ym);
        //历史机构-月
        initDataHisOrganization(ym);

        log.info(TipsEnum.JOB_END.getDesc());
    }

    /**
     * 历史机构-月
     */
    private void initDataHisOrganization(int ym) {
        int isExist = commonService.checkHistoryOrganization(ym);
        if (isExist > 0) {
            log.info(TipsEnum.DATA_EXIST.getDesc());
        } else {
            int isSuccess = commonService.insertHistoryOrganization(ym);
            log.info(isSuccess == 1 ? TipsEnum.DATA_SUCCESS.getDesc() : TipsEnum.DATA_FAIL.getDesc());
        }
    }

    /**
     * 新的一年日表维度
     *
     * @param year
     * @param startDay
     * @param endDay
     */
    private void initDataDays(DateTime year, String startDay, String endDay, int ym) {
        boolean exist = commonService.checkDaysTable(year.toString("yyyy"));
        if (exist) {
            log.info(TipsEnum.DATA_EXIST.getDesc());
        } else {
            List<DaysDto> daysRs = commonService.insertDays(endDay, startDay);
            if (daysRs.size() > 0) {
                log.info(TipsEnum.DATA_SUCCESS.getDesc());
                commonService.insertTheoryAttendance(daysRs, 7.5, ym, Integer.parseInt(year.toString("yyyy")));
            } else {
                TipsEnum.DATA_FAIL.getDesc();
            }
        }
    }

    /**
     * 秒 0-59 , - * /
     * <p>
     * 分 0-59 , - * /
     * <p>
     * 小时 0-23 , - * /
     * <p>
     * 日期 1-31 , - * ? / L W C
     * <p>
     * 月份 1-12 或者 JAN-DEC , - * /
     * <p>
     * 星期 1-7 或者 SUN-SAT , - * ? / L C
     * <p>
     * 年（可选） 留空, 1970-2099 , - * /
     * <p>
     *
     * @see net.chinahrd.core.job.JobRegisterAbstract#setTimerConfig(net.chinahrd.core.timer.model.TimerConfig)
     */
    @Override
    public void setTimerConfig(TimerConfig tc) {
        tc.setPriority(4);
        // tc.setCron("0 30 1 1 1 ?"); // 每年，1月1号，凌晨1点30分
        tc.setCron("0 30 1 * * ?"); // 每天，凌晨1点30分
        // tc.setCron("0/30 08 19 * * ?"); // 测试
    }

}
