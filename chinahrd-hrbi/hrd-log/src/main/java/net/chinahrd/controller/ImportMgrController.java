package net.chinahrd.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.constants.BaseConstants;
import net.chinahrd.constants.RedisConstants;
import net.chinahrd.service.ImportExcelService;
import net.chinahrd.service.ImportMgrService;
import net.chinahrd.utils.DateUtil2;
import net.chinahrd.utils.JedisUtils;
import net.chinahrd.utils.holiday.DaysDto;
import net.chinahrd.utils.holiday.EasybotsGetDays;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Title: ${type_name} <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年06月07日 18:28
 * @Verdion 1.0 版本
 * ${tags}
 */
@Controller
@RequestMapping("/import")
@Slf4j
public class ImportMgrController {

    private JedisUtils jedis = new JedisUtils(RedisConstants.REDIS_HOST, RedisConstants.PROT, RedisConstants.AUTH);

    @Autowired
    private ImportMgrService importMgrService;
    @Autowired
    private ImportExcelService importExcelService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index.do")
    public String toUserOpt() {
        return "importMgr";
    }

    /**
     * 正常考勤表最大最小时间
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMinMaxTime.do")
    public Map<String, Object> getMinMaxTime() {
        Map<String, Object> rsMap = Maps.newHashMap();

        String year = DateUtil2.Get.Now.SERVER.toString(DateUtil2.TemplateEnum.FORMAT_YEAR.getValue());
        String month = DateUtil2.Get.Now.SERVER.toString(DateUtil2.TemplateEnum.FORMAT_MONTH.getValue());

        Map<String, Integer> mainMax = importMgrService.findMinMaxTime(BaseConstants.CUSTOMER_ID);
        if (mainMax != null) {
            Integer minTime = mainMax.get("minTime");
            Integer maxTime = mainMax.get("maxTime");

            rsMap.put("minTime", minTime);
            rsMap.put("maxTime", maxTime);
        } else {
            rsMap.put("yearList", year);
        }
        return rsMap;
    }

    /**
     * 本月日期情况
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDayList.do")
    public List<DaysDto> queryDayList(
            @RequestParam("yearMonthDay") String yearMonthDay) {

        Date selectYMD = new DateTime(yearMonthDay).toDate();
        String startDate = DateUtil2.Get.Month.FIRST.ymd(selectYMD);
        String endDate = DateUtil2.Get.Month.LAST.ymd(selectYMD);
        List<DaysDto> daysList = importMgrService.queryDayList(startDate, endDate);

        String jsonDaysList = jedis.get(String.format(BaseConstants.REDIS_KEY_DAYS, startDate));
        if(StringUtils.isBlank(jsonDaysList)){
            // 修改db前，先保存redis备份，在还原时可以用
            jedis.set(String.format(BaseConstants.REDIS_KEY_DAYS, startDate), JSON.toJSONString(daysList));
        }
        return daysList;
    }

    /**
     * 本月日期情况
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMaxDay.do")
    public Map<String, Date> getMaxDay() {
        return importMgrService.getMaxDay();
    }


    /**
     * 更新days表带薪假
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateVacation.do")
    public boolean updateVacation(DaysDto daysDto) {
        String yearMonthDay = daysDto.getDays();
        String key = DateUtil2.Get.Month.FIRST.ymd(new DateTime(yearMonthDay).toDate());

        if (daysDto.getIsWorkFlag() == 9999) {
            String jsonDaysList = jedis.get(String.format(BaseConstants.REDIS_KEY_DAYS, key));

            List<DaysDto> daysDtoList = JSON.parseArray(jsonDaysList, DaysDto.class);
            for (DaysDto dto : daysDtoList) {
                if (dto.getDays().equals(yearMonthDay)) {
                    return importMgrService.updateVacation(dto);
                }
            }
        }

        return importMgrService.updateVacation(daysDto);
    }


    /**
     * 更新days表带薪假
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDaysDto.do")
    public DaysDto getDaysDto(@RequestParam("ymd") int ymd) {
        return new EasybotsGetDays().getDaysDto(ymd);
    }


    /**
     * 导出月度考勤表
     *
     * @param yearmonth
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/exportAttendanceMonthly.do")
    public void attendanceMonthly(String yearmonth, HttpServletResponse response) {
        if (StringUtils.isEmpty(yearmonth)) {
            log.error("yearmonth is null");
            return;
        }
        yearmonth = yearmonth.replaceAll("-", "");
        importExcelService.attendanceMonthly(response, yearmonth, BaseConstants.CUSTOMER_ID);
    }

}
