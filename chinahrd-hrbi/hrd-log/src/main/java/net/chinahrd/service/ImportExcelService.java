package net.chinahrd.service;

import javax.servlet.http.HttpServletResponse;

public interface ImportExcelService {


    /**
     * 月考勤报表导出
     */
    void attendanceMonthly(HttpServletResponse response, String yearmonth, String coustomerId);

}