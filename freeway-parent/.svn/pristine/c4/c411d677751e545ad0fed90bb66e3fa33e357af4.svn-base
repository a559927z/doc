package net.chinahrd.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间日期工具，目前主要用于日期解析匹配格式化工具<br/>
 * date: 2019年1月3日 下午5:57:25 <br/>
 * 
 * @author guanjian
 */
@Slf4j
public class DTUtils extends DateTimeUtils{
    public static Map<String, String> formatMap = new HashMap<String, String>();
    static {
        formatMap.put("yyyyMM", "^\\d{4}\\d{1,2}$");
        formatMap.put("yyyyMMdd", "^\\d{4}\\d{1,2}\\d{1,2}$");
        formatMap.put("yyyy/MM/dd", "^\\d{4}/\\d{1,2}/\\d{1,2}$");
        formatMap.put("yyyy/MM/dd HH:mm", "^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}$");
        formatMap.put("yyyy/MM/dd HH:mm:ss", "^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$");
        formatMap.put("yyyy-MM-dd", "^\\d{4}-\\d{1,2}-\\d{1,2}$");
        formatMap.put("yyyy-MM-dd HH:mm", "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}$");
        formatMap.put("yyyy-MM-dd HH:mm:ss", "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$");
        formatMap.put("yyyy年MM月dd日", "^\\d{4}年\\d{1,2}月\\d{1,2}日$");
    }

    /**
     * 返回匹配上的格式化字符串,如：2018-08-08 返回 yyyy-MM-dd
     * @author guanjian
     * @param dateStr
     * @return
     * @throws Exception 
     */
    public static String getDateFormatStr(String dateStr) throws Exception {
        String format = null;
        for (Map.Entry<String, String> m : formatMap.entrySet()) {
            Pattern pattern = Pattern.compile(m.getValue());
            Matcher matcher = pattern.matcher(dateStr);
            if (matcher.find()) {
                format = m.getKey();
//                log.debug(dateStr+":"+format);
            }
        }
        if(format==null){
            log.error(dateStr+"没有匹配上的格式化字符串！");
            throw new Exception(dateStr+"没有匹配上的格式化字符串！");
        }
        return format;
    }
    
    public static String date2String(Date date,String format){
        return DateFormatUtils.format(date, format);
    }
    /**
     * 默认为yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String date2String(Date date){
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * yyyyMMdd
     * @param date
     * @return
     */
    public static String date2yyyymmdd(Date date){
        return DateFormatUtils.format(date, "yyyyMMdd");
    }
    /**
     * yyyy-MM-dd
     * @author guanjian
     * @param date
     * @return
     */
    public static String date2yyyy_mm_dd(Date date){
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }
    
    /**
     *  获取当天凌晨0点0分0秒
     * @author 伟
     * @return
     */
    public static Date getCurrentDayBegin(){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar1.getTime();
    }
    /**
     *  获取当天23点59分59秒
     * @author 伟
     * @return
     */
    public static Date getCurrentDayEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime();
    }
    
    /**
     * 获取当月1号凌晨0点0分0秒
     * @author 伟
     * @return
     */
    public static Date getMonthFirstDayBegin(){
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        //将小时至0  
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        //将分钟至0  
        calendar.set(Calendar.MINUTE, 0);  
        //将秒至0  
        calendar.set(Calendar.SECOND,0);  
        //将毫秒至0  
        calendar.set(Calendar.MILLISECOND, 0);  
        //获得当前月第一天  
        return calendar.getTime();  
    }
    
    /***
     * 将string字符串转化为Date类型的字符串
     * @param dateTimeStr 需要转化的string类型的字符串
     * @param formatStr 转化规则
     * @return 返回转化后的Date类型的时间
     */
    public static Date strToDate(String dateTimeStr, String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
 
    public static Date yyyy_mm_dd2Date(String str){
    	return strToDate(str, "yyyy-MM-dd");
    }
}
