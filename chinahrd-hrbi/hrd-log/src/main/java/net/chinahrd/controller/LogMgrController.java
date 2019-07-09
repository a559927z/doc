package net.chinahrd.controller;

import net.chinahrd.dto.EmpAttendanceDto;
import net.chinahrd.dto.EmpOtherDayDto;
import net.chinahrd.dto.LogDto;
import net.chinahrd.dto.RequestParamsDto;
import net.chinahrd.service.LogMgrService;
import net.chinahrd.utils.CollectionKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogMgrController {

    @Autowired
    private LogMgrService logMgrService;

    @RequestMapping(value = "/logMgr.do")
    public String logMgr() {
        return "logMgr";
    }

    /**
     * 操作日志
     *
     * @return
     */
    @RequestMapping(value = "/operLogMgr/index.do")
    public String operLogMgr() {
        return "operLogMgr";
    }

    /**
     * 操作日志
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/operLogMgr/list.do")
    public Map<String, Object> operlog(HttpServletRequest request) {

        //数据起始位置
        Integer startIndex = Integer.parseInt(request.getParameter("startIndex"));
        //数据长度
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        //原生搜索
        String search = request.getParameter("search");
        List<LogDto> rs = logMgrService.queryAllLog(startIndex, pageSize, search);
        Long count = logMgrService.countAllLog(search);
        Map<String, Object> rsMap = CollectionKit.newMap();
        rsMap.put("data", rs);
        rsMap.put("total", count);
        return rsMap;
    }

    /**
     * 剩余假期汇总
     *
     * @return
     */
    @RequestMapping(value = "/empVactionLogMgr/index.do", method = RequestMethod.GET)
    public String toEmpVacationLog() {
        return "empVacationLogMgr";
    }

    /**
     * 剩余假期汇总
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/empVactionLogMgr/list.do", method = RequestMethod.POST)
    public Map<String, Object> empVacation(HttpServletRequest request) {
        Integer startIndex = Integer.parseInt(request.getParameter("startIndex"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String search = request.getParameter("search");
        List<EmpAttendanceDto> rs = logMgrService.queryEmpVacation(startIndex, pageSize, search);
        int count = logMgrService.countEmpVacation();
        Map<String, Object> rsMap = CollectionKit.newMap();
        rsMap.put("data", rs);
        rsMap.put("total", count);
        return rsMap;
    }

    /**
     * 休假明细
     *
     * @return
     */
    @RequestMapping(value = "/empOtherDayLogMgr/index.do", method = RequestMethod.GET)
    public String toEmpOtherDayLog() {
        return "empOtherDayLogMgr";
    }

    /**
     * 休假明细
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/empOtherDayLogMgr/list.do", method = RequestMethod.POST)
    public Map<String, Object> empOtherDay(HttpServletRequest request, RequestParamsDto requestParamsDto) {
        int start = requestParamsDto.getStart();
        int length = requestParamsDto.getLength();
        int startYm = requestParamsDto.getStartYm();
        int endYm = requestParamsDto.getEndYm();
        String search = request.getParameter("search");
        List<EmpOtherDayDto> rs = logMgrService.queryEmpOtherDay(start, length, startYm, endYm, search);
        int count = logMgrService.countEmpOtherDay();
        Map<String, Object> rsMap = CollectionKit.newMap();
        rsMap.put("data", rs);
        rsMap.put("total", count);
        return rsMap;
    }

    /**
     * 历史休假期年份
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryEmpOtherDayYear.do", method = RequestMethod.POST)
    public List<String> queryEmpOtherDayYear() {
        return logMgrService.queryEmpOtherDayYear();
    }

}
