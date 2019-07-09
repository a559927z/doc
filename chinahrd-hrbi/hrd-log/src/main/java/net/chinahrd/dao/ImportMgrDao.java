package net.chinahrd.dao;

import net.chinahrd.utils.holiday.DaysDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("importMgrDao")
public interface ImportMgrDao {

    /**
     * 更新days表带薪假
     */
    void updateVacation(Map<String, Object> paramMap);

    /**
     * days最大日期
     *
     * @return
     */
    Map<String, Date> getMaxDay();

    /**
     * 正常考勤表最大最小时间
     *
     * @param customerId
     * @return
     */
    Map<String, Integer> findMinMaxTime(String customerId);

    /**
     * 本月日期情况
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<DaysDto> queryDayList(@Param("startDate") String startDate, @Param("endDate") String endDate);


    /**
     * 按照月为最小时间段获取考勤表
     */
    List<HashMap<String, Object>> attendanceMonthly(@Param("yearMonth") String yearmonth, @Param("coustomerId") String coustomerId);

    /**
     * 获取当月应出勤表
     */
    List<HashMap<String, Object>> theoryAttendance(@Param("yearMonth") String yearmonth);

    /**
     * 获取到节假日的hashmap
     */
    List<HashMap<String, Object>> queryCheckWorkTypeMap(@Param("yearMonth") String yearmonth);


    /**
     * 查找本月总法定假期（带薪假期）
     *
     * @return
     */
    int findVacationDays(@Param("beginTime") String beginTime, @Param("endTime") String endTime);


}
