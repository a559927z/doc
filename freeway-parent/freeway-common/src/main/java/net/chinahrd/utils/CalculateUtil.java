package net.chinahrd.utils;

import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.Date;

public class CalculateUtil {

    private final static int[] DAY_ARR = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    private final static String[] CONSTELLATION_ARR = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };

    private final static String[] YEAR_1900 = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
    /**
     * Java通过生日计算星座
     * 
     * @param month
     * @param day
     * @return
     */
    public static String getAstrology(int month, int day) {
        return day < DAY_ARR[month - 1] ? CONSTELLATION_ARR[month - 1] : CONSTELLATION_ARR[month];
    }

    /**
     * 通过生日计算属相
     * 
     * @param year
     * @return
     */
    public static String getZodiac(int year) {
        if (year < 1900) {
            return "未知";
        }
        int start = 1900;
        
        return YEAR_1900[(year - start) % YEAR_1900.length];
    }
    
    public static Integer calculateAge(Date birthday){
        if(birthday == null){
            return null;
        }
        DateTime birth = new DateTime(birthday);
        int monthOfYear = birth.getMonthOfYear();
        int dayOfMonth = birth.getDayOfMonth();
        LocalDate dateStart = LocalDate.of(birth.getYear(),monthOfYear, dayOfMonth);
        LocalDate dateNow = LocalDate.now();
        int age = dateStart.until(dateNow).getYears();
        return age;
    }
    
    public static String[] caculateAstrologyAndZodiac(Date birthday){
        if(birthday == null){
            return new String[]{null,null};
        }
        DateTime birth = new DateTime(birthday);
        int monthOfYear = birth.getMonthOfYear();
        int dayOfMonth = birth.getDayOfMonth();

        String astrologyStr = CalculateUtil.getAstrology(monthOfYear, dayOfMonth);
        String zodiacStr = CalculateUtil.getZodiac(birth.getYear());
        return new String[]{astrologyStr, zodiacStr};
    }

    /**
     * 根据出生日期计算属相和星座
     * 
     * @param args
     */
    public static void main(String[] args) {
        int month = 11;
        int day = 10;
        System.out.println("星座为：" + getAstrology(month, day));
        System.out.println("属相为:" + getZodiac(1987));

        DateTime birth = new DateTime();
        int monthOfYear = birth.getMonthOfYear();
        int dayOfMonth = birth.getDayOfMonth();
        System.out.println(monthOfYear);
        System.out.println(dayOfMonth);
        System.out.println(birth.getYear());
        CalculateUtil.getAstrology(monthOfYear,dayOfMonth);
    }
}
