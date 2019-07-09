package net.chinahrd.service;

import net.chinahrd.utils.holiday.DaysDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ImportMgrService {

    /**
     * 更新days表带薪假
     *
     * @param days
     * @param isVacation 0 不是，1 是
     */
//    boolean updateVacation(@Param("days") String days, @Param("isVacation") int isVacation);
    boolean updateVacation(DaysDto daysDto);

    /**
     * days最大日期
     *
     * @return
     */
    Map<String, Date> getMaxDay();

    /**
     * 正常考勤表最大最小时间
     *
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
    List<DaysDto> queryDayList(String startDate, String endDate);


}