package net.chinahrd.manpowerCost.mvc.pc.service.impl;

import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerItemDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerOrganDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerTrendDto;
import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.manpowerCost.mvc.pc.dao.ManpowerCostDao;
import net.chinahrd.manpowerCost.mvc.pc.service.ManpowerCostService;
import net.chinahrd.mvc.pc.dao.admin.FunctionDao;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("manpowerCostService")
public class ManpowerCostServiceImpl implements ManpowerCostService {
    @Autowired
    ManpowerCostDao manpowerCostDao;

    @Autowired
    FunctionDao functionDao;

    @Override
    public List<ManpowerCompareDto> getCompareMonth(String customerId, String organId, String time) {
        Map<String, String> map = getYearMonthArr(time);
        List<ManpowerCompareDto> list = manpowerCostDao.queryCompareMonth(customerId, organId, map.get("yearMonth1"), map.get("yearMonth2"));
        return list;
    }


    @Override
    public List<ManpowerCompareDto> getCompareYear(String customerId, String organId, String time) {
        FunctionTreeDto dto = functionDao.findFunctionObj(customerId, null, "manpowerCost/toManpowerCostView");

        Map<String, String> parmMap = getYearArr(time);
        parmMap.put("customerId", customerId);
        parmMap.put("organId", organId);
        parmMap.put("functionId", dto.getId());
        List<ManpowerCompareDto> list = manpowerCostDao.queryCompareYear(parmMap);
        return list;
    }


    @Override
    public ManpowerTrendDto queryTrendByMonth(String customerId, String organId, String time) {
        ManpowerTrendDto mtd = new ManpowerTrendDto();
        Double yearBudget = manpowerCostDao.queryYearBudget(customerId, organId, getYear(time));
        if (null == yearBudget) yearBudget = 0.0;
        List<ManpowerDto> list = manpowerCostDao.queryTrendByMonth(customerId, organId, getYear(time));
        double total = 0;
        if (null != list && list.size() > 0) {
            for (ManpowerDto o : list) {
                total = ArithUtil.sum(total, o.getCost());
            }
        }
        mtd.setBudget(yearBudget);
        mtd.setTotal(total);
        mtd.setDetail(list);
        return mtd;
    }

    @Override
    public List<ManpowerDto> queryAvgTrendByMonth(String customerId,
                                                  String organId, String time) {
        List<ManpowerDto> list = manpowerCostDao.queryAvgTrendByMonth(customerId, organId, getYear(time));
        return list;
    }


    @Override
    public List<ManpowerItemDto> queryItemDetail(String customerId,
                                                 String organId, String time) {
        List<ManpowerItemDto> list = manpowerCostDao.queryItemDetail(customerId, organId, getYear(time));
        return list;
    }

    @Override
    public List<ManpowerOrganDto> queryOrganCost(String customerId, String organId, String time) {
        List<ManpowerOrganDto> list = manpowerCostDao.queryOrganCost(customerId, organId, getYear(time));
        if (null != list && list.size() > 0) {
            for (ManpowerOrganDto o : list) {
                if (o.getOrganId().equals(organId)) {
                    list.remove(o);
                    list.add(0, o);
                    break;
                }
            }
        }
        return list;
    }


    @Override
    public List<ManpowerCompareDto> queryProportionYear(String customerId,
                                                        String organId, String time) {
        Map<String, String> map = getYearArr(time);
        List<ManpowerCompareDto> list = manpowerCostDao.queryProportionYear(customerId, organId, map.get("year1"), map.get("year2"));
        return list;
    }


    @Override
    public List<ManpowerCompareDto> queryProportionMonth(String customerId,
                                                         String organId, String time) {
        Map<String, String> map = getYearMonthArr(time);
        List<ManpowerCompareDto> list = manpowerCostDao.queryProportionMonth(customerId, organId, map.get("yearMonth1"), map.get("yearMonth2"));
        return list;
    }


    @Override
    public Double queryAvgValue(String customerId, String organId) {
        return manpowerCostDao.queryAvgValue(customerId, organId);
    }


    @Override
    public Map<String, Double> getCostAvgWarn(String customerId, String organId, String time) {
        Map<String, Double> result = CollectionKit.newMap();
        Double avgValue = manpowerCostDao.queryAvgValue(customerId, organId);
        FunctionTreeDto dto = functionDao.findFunctionObj(customerId, null, "manpowerCost/toManpowerCostView");
        Map<String, String> parmMap = getYearArr(time);
        parmMap.put("customerId", customerId);
        parmMap.put("organId", organId);
        parmMap.put("functionId", dto.getId());
        List<ManpowerCompareDto> list = manpowerCostDao.queryCompareYear(parmMap);
        Double avgCost = null;
        if (null != list) {
            for (ManpowerCompareDto m : list) {
                if (m.getType() == 1) {
                    avgCost = m.getCostAvg();
                }
            }
        }
        result.put("avgValue", avgValue);
        result.put("avgCost", avgCost);
        return result;
    }

    @Override
    public List<ManpowerDto> queryAllDetailData(String customerId,
                                                String organId, String time) {
        return manpowerCostDao.queryAllDetailData(customerId, organId, getYear(time));
    }

    private int getYear(String time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getDate());
        return cal.get(Calendar.YEAR);
    }

    private Map<String, String> getYearMonthArr(String time) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.setTime(DateUtil.getDate());
        cal.add(Calendar.MONTH, -1);
        String yearMonth1 = cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) + 1);
        cal.add(Calendar.MONTH, -1);
        String yearMonth2 = cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) + 1);
        Map<String, String> map = CollectionKit.newMap();
        map.put("yearMonth1", yearMonth1);
        map.put("yearMonth2", yearMonth2);
        return map;
    }

    private Map<String, String> getYearArr(String time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getDate());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String year1 = cal.get(Calendar.YEAR) + "";

        String year1_begin = formatDate(getCurrYearFirst(cal.get(Calendar.YEAR)));
        DateTime dt = new DateTime(cal.getTime());
        String year1_end = dt.toString("yyyy-MM-dd");

        cal.add(Calendar.YEAR, -1);
        String year2 = cal.get(Calendar.YEAR) + "";
        String year2_begin = formatDate(getCurrYearFirst(cal.get(Calendar.YEAR)));

        String year2_end = formatDate(getCurrYearLast(cal.get(Calendar.YEAR)));
        Map<String, String> map = CollectionKit.newMap();
        map.put("year1", year1);
        map.put("year1_begin", year1_begin);
        map.put("year1_end", year1_end);
        map.put("year2", year2);
        map.put("year2_begin", year2_begin);
        map.put("year2_end", year2_end);
        return map;
    }

    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @return String 日期字符串
     */
    private static String formatDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    private static Date getCurrYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    private static Date getCurrYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }
}
