package net.chinahrd.utils;

import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author htpeng 2016年9月22日上午11:28:42
 * <p>
 * 重构
 * @author jxzhang 2018-05-28
 */
@Slf4j
public class DateUtil2 {

    /**
     * 是否使用测试时间
     */
    private static boolean TEST_DATE = false;

    static {
        try {
            TEST_DATE = Boolean.valueOf(PropertiesUtil.pps.getProperty("dbtest"));
        } catch (Exception e) {
        }
    }

    // 2015-12-18 09:45:22
    private static long defaultTimeLog = 1450403122691L;

    // 2016-06-01 00:00:00
    // private static long defaultTimeLog = 1464710400000L;


    private static Timestamp timestamp = new Timestamp(defaultTimeLog);


    /**
     * Title: YMD <br/>
     * Description:
     *
     * @author jxzhang
     * @DATE 2017年12月27日 下午6:41:14
     * @Verdion 1.0 版本
     */
    @SuppressWarnings("unused")
    private static class YMD {
        private int y;
        private int m;
        private int d;

        private String ys;
        private String ms;
        private String ds;

        public YMD(int y, int m, int d) {
            super();
            this.y = y;
            this.m = m;
            this.d = d;
            this.ys = String.valueOf(y);
            this.ms = String.valueOf(m);
            this.ds = String.valueOf(d);
        }

        public int yearInt() {
            return y;
        }

        public int yearMonthInt() {
            return Integer.parseInt(ys + ms);
        }

        public int dayInt() {
            return d;
        }

        public String ymdLine() {
            System.out.println(y + "_" + m + "_" + d);
            if (m <= 9 && m >= 1) {
                this.ms = "0" + ms;
            }
            if (d <= 9 && d >= 1) {
                this.ds = "0" + ds;
            }
            return StringUtils.join(ys, "-", ms, "-", ds);
        }

        public static YMD init(int y, int m, int d) {
            return new YMD(y, m, d);
        }

    }


    /**
     * Title: DateUtilEnum枚举模板 <br/>
     * Description:
     *
     * @author jxzhang
     * @DATE 2018年05月28日 下午6:50:00
     * @Verdion 1.0 版本
     */
    @Getter
    @AllArgsConstructor
    public enum TemplateEnum {

        /**
         * 默认日期格式
         */
        FORMAT_DEFAULT_DATE("yyyy-MM-dd hh:mm:ss") {
            @Override
            public String is24() {
                return "yyyy-MM-dd HH:mm:ss";
            }

            @Override
            public String noLine() {
                return "yyyyMMdd HH:mm:ss";
            }

            @Override
            public String zh() {
                return "yyyy年MM月dd日 HH时mm分ss秒";
            }
        },

        /**
         * 年
         */
        FORMAT_YEAR("yyyy") {
            @Override
            public String is24() {
                return "yyyy";
            }

            @Override
            public String noLine() {
                return "yyyy";
            }

            @Override
            public String zh() {
                return "yyyy年";
            }
        },
        /**
         * 月
         */
        FORMAT_MONTH("MM") {
            @Override
            public String is24() {
                return "MM";
            }

            @Override
            public String noLine() {
                return "MM";
            }

            @Override
            public String zh() {
                return "MM月";
            }
        },
        /**
         * 年月
         */
        FORMAT_YEAR_MONTH("yyyy-MM") {
            @Override
            public String is24() {
                return "yyyy-MM";
            }

            @Override
            public String noLine() {
                return "yyyyMM";
            }

            @Override
            public String zh() {
                return "yyyy年MM月";
            }
        },

        /**
         * 年月日
         */
        FORMAT_YEAR_MONTH_DAY("yyyy-MM-dd") {
            @Override
            public String is24() {
                return "yyyy-MM-dd";
            }

            @Override
            public String noLine() {
                return "yyyyMMdd";
            }

            @Override
            public String zh() {
                return "yyyy年MM月dd日";
            }
        },


        /**
         * 时分秒
         */
        FORMAT_HOUR_MIN_SECOND("hh:mm:ss") {
            @Override
            public String is24() {
                return "HH:mm:ss";
            }

            @Override
            public String noLine() {
                return "HH:mm:ss";
            }

            @Override
            public String zh() {
                return "HH时mm分ss秒";
            }
        };


        /**
         * 模板值
         */
        private String value;

        /**
         * 是否24小制
         *
         * @return
         */
        public abstract String is24();

        /**
         * 是否带下划线
         *
         * @return
         */
        public abstract String noLine();

        /**
         * 中文显示
         *
         * @return
         */
        public abstract String zh();

    }


//    /**
//     * date to string
//     *
//     * @param date
//     * @param pattern yyyy-MM-dd
//     * @return <br>
//     * @author jxzhang on 2016-03-18
//     */
//    public static String dateToStr(Date date, String pattern) {
//        return new DateTime(date).toString(pattern);
//    }

//    /**
//     * string to date
//     *
//     * @param dateStr
//     * @return <br>
//     * @author jxzhang on 2016-03-18
//     */
//    public static Date strToDate(String dateStr) {
//        return new DateTime(dateStr).toDate();
//    }

//    /**
//     * date to timestamp
//     *
//     * @param date
//     * @return <br>
//     * @author jxzhang on 2016-03-18
//     */
//    public static Timestamp dateToTimestamp(Date date) {
//        return new Timestamp(date.getTime());
//    }


    // 以下Calendar类，将会用DataTime类取代========================================================


//    /**
//     * 获取当年的第一天
//     *
//     * @return
//     */
//    public static Date getCurrYearFirst() {
//        Calendar currCal = Calendar.getInstance();
//        int currentYear = currCal.get(Calendar.YEAR);
//        return getYearFirst(currentYear);
//    }
//
//    /**
//     * 获取当年的最后一天
//     *
//     * @return
//     */
//    public static Date getCurrYearLast() {
//        Calendar currCal = Calendar.getInstance();
//        int currentYear = currCal.get(Calendar.YEAR);
//        return getYearLast(currentYear);
//    }

//    /**
//     * 获取某年第一天日期
//     *
//     * @param year 年份
//     * @return Date
//     */
//    public static Date getYearFirst(int year) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.clear();
//        calendar.set(Calendar.YEAR, year);
//        Date currYearFirst = calendar.getTime();
//        return currYearFirst;
//    }
//
//    /**
//     * 获取某年最后一天日期
//     *
//     * @param year 年份
//     * @return Date
//     */
//    public static Date getYearLast(int year) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.clear();
//        calendar.set(Calendar.YEAR, year);
//        calendar.roll(Calendar.DAY_OF_YEAR, -1);
//        Date currYearLast = calendar.getTime();
//        return currYearLast;
//    }

//    /**
//     * 获取某月第一天日期
//     *
//     * @param date
//     * @return
//     */
//    public static String getMonthFirst(Date date) {
//        Calendar c = Calendar.getInstance();
//        c.clear();
//        c.setTime(date);
//        c.set(Calendar.DAY_OF_MONTH, 1);
//        return new SimpleDateFormat(TemplateEnum.FORMAT_YEAR_MONTH_DAY.value).format(c.getTime());
//    }

//    /**
//     * 获取某月最后一天日期
//     *
//     * @param date
//     * @return
//     */
//    public static String getMonthLast(Date date) {
//        Calendar c = Calendar.getInstance();
//        c.clear();
//        c.setTime(date);
//        c.add(Calendar.MONTH, 1);
//        c.set(Calendar.DAY_OF_MONTH, 0);
//        return new SimpleDateFormat(TemplateEnum.FORMAT_YEAR_MONTH_DAY.value).format(c.getTime());
//    }


    //========================================= 获取时间 =================================================

    /**
     * 获取时间
     */
    public static class Get {

        public enum Year {

            /**
             * 年第一天
             *
             * @return
             */
            FIRST() {
                @Override
                public int ym() {
                    return Integer.parseInt(new DateTime(Now.DB.date()).getYear() + "01");
                }

                @Override
                public int ymd() {
                    return Integer.parseInt(new DateTime(Now.DB.date()).getYear() + "0101");
                }

                @Override
                public Date ymdDate() {
                    Calendar currCal = Calendar.getInstance();
                    int currentYear = currCal.get(Calendar.YEAR);
                    return ymdDate(currentYear);
                }

                /**
                 * 获取某年第一天日期
                 *
                 * @param year 年份
                 * @return Date
                 */
                @Override
                public Date ymdDate(int year) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(Calendar.YEAR, year);
                    Date currYearFirst = calendar.getTime();
                    return currYearFirst;
                }
            },

            /**
             * 年最后一天
             *
             * @return
             */
            LAST() {
                @Override
                public int ym() {
                    return Integer.parseInt(new DateTime(Now.DB.date()).getYear() + "12");
                }

                @Override
                public int ymd() {
                    return Integer.parseInt(new DateTime(Now.DB.date()).getYear() + "1231");
                }

                @Override
                public Date ymdDate() {
                    Calendar currCal = Calendar.getInstance();
                    int currentYear = currCal.get(Calendar.YEAR);
                    return ymdDate(currentYear);
                }

                /**
                 * 获取某年最后一天日期
                 *
                 * @param year 年份
                 * @return Date
                 */
                @Override
                public Date ymdDate(int year) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(Calendar.YEAR, year);
                    calendar.roll(Calendar.DAY_OF_YEAR, -1);
                    Date currYearLast = calendar.getTime();
                    return currYearLast;
                }
            };

            public abstract int ym();

            public abstract int ymd();

            public abstract Date ymdDate();

            /**
             * 指定某年
             *
             * @param year
             * @return
             */
            public abstract Date ymdDate(int year);


            /**
             * 获取系统 前一年的时间
             *
             * @return
             */
            public static String before() {
                return before(TemplateEnum.FORMAT_DEFAULT_DATE.value, 1);
            }

            /**
             * 获取系统 前几年的时间
             *
             * @param num 前几年
             * @return
             */
            public static String before(int num) {
                return before(TemplateEnum.FORMAT_DEFAULT_DATE.value, num);
            }

            /**
             * 获取系统 前几年的时间
             *
             * @param num       前几年
             * @param formatTpl
             * @return
             */
            public static String before(String formatTpl, int num) {
                long t = getTimestamp();
                DateTime dt = new DateTime(t);
                return dt.minusYears(num).toString(formatTpl);
            }
        }

        public enum Month {
            FIRST() {
                /**
                 * 获取某月第一天日期
                 *
                 * @param date
                 * @return
                 */
                @Override
                public String ymd(Date date) {
                    DateTime datetime = new DateTime(date);
                    int i = datetime.dayOfMonth().withMinimumValue().dayOfMonth().get();
                    return datetime.toString(TemplateEnum.FORMAT_YEAR_MONTH.value) + "-0" + i;
                }

            },
            LAST() {
                /**
                 * 获取某月最后一天日期
                 *
                 * @param date
                 * @return
                 */
                @Override
                public String ymd(Date date) {
                    DateTime datetime = new DateTime(date);
                    int i = datetime.dayOfMonth().withMaximumValue().dayOfMonth().get();
                    return datetime.toString(TemplateEnum.FORMAT_YEAR_MONTH.value) + "-" + i;
                }

            };

            public abstract String ymd(Date date);

        }

        public static class Day {
            /**
             * 昨天
             *
             * @return
             */
            public static String yesterday() {
                return yesterday(TemplateEnum.FORMAT_DEFAULT_DATE.value);
            }

            /**
             * 获取系统 前一天时间
             * <br>
             * 昨天
             *
             * @param formatTpl
             * @return
             */
            public static String yesterday(String formatTpl) {
                long t = getTimestamp();
                DateTime dt = new DateTime(t);
                return dt.minusDays(1).toString(formatTpl);
            }

            /**
             * 获得某个月有多少天
             *
             * @param dataObj 标准时间格式，或 可转标准时间格式 '2018-05-29'
             * @return
             */
            public static int total(Object dataObj) {
                // TODO string 要做验证格式
                if (dataObj instanceof DateTime || dataObj instanceof String || dataObj instanceof Date) {
                    return new DateTime(dataObj).dayOfMonth().getMaximumValue();
                }
                return 0;
            }

            /**
             * 年月转上半年下半年;例如 201504 --》2015上半年 ； 201507--》2015下半年
             *
             * @param list
             * @return
             */
            public static List<String> getYearMonthTohHalfs(List<String> list) {
                List<String> result = CollectionKit.newList();
                for (String str : list) {
                    result.add(getYearMonthTohHalf(str));
                }
                return result;
            }

            /**
             * 年月转上半年下半年;例如 201504 --》2015上半年 ； 201507--》2015下半年
             *
             * @param date
             * @return
             */
            public static String getYearMonthTohHalf(String date) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil2.Parse.ConvertTo.asDate(date));
                String year = date.substring(0, 4);
                int month = -1;
                if (date.charAt(4) == '-') {
                    month = Integer.parseInt(date.substring(5, 7));
                } else {
                    month = Integer.parseInt(date.substring(4, 6));
                }

                if (month < 7) {
                    return year + "上半年";
                } else {
                    return year + "下半年";
                }
            }

            /**
             * 获取中文的日期描述
             *
             * @param date
             * @param format:年，年月，月日
             * @return
             */
            public static String getDate4CN(String date, String format) {
                if (!Str.IsEmpty(format)) {
                    Date d = DateUtil2.Parse.ConvertTo.asDate(date);
                    Calendar cl = Calendar.getInstance();
                    cl.setTime(d);
                    if (format.equals("年")) {
                        return date.substring(0, 4) + "年";
                    }
                    if (format.equals("年月")) {
                        return date.substring(0, 4) + "年" + date.substring(5, 7) + "月";
                    }
                    if (format.equals("月日")) {
                        return date.substring(5, 7) + "月" + date.substring(8, 10) + "日";
                    }
                    if (format.equals("月日星期")) {
                        return date.substring(5, 7) + "月" + date.substring(8, 10) + "日星期"
                                + Str.dayofWeek2Cn(cl.get(Calendar.DAY_OF_WEEK) + "");
                    }
                    return date;
                }
                return date;
            }
        }

        @Getter
        @AllArgsConstructor
        public enum Now {

            /**
             * 获取服务器当前时间
             */
            SERVER() {
                @Override
                public String def() {
                    return toString(TemplateEnum.FORMAT_DEFAULT_DATE.value);
                }

                @Override
                public Date date() {
                    return new Date(getTimestamp());
                }

                @Override
                public Timestamp timestamp() {
                    return new Timestamp(System.currentTimeMillis());
                }

                @Override
                public String toString(String formatTpl) {
                    return new DateTime(System.currentTimeMillis()).toString(formatTpl);
                }
            },

            /**
             * 获取数据库当前时间 == NOW()
             */
            DB() {
                @Override
                public String def() {
                    return toString(TemplateEnum.FORMAT_DEFAULT_DATE.value);
                }

                @Override
                public Date date() {
                    return new DateTime(getTimestamp()).minusDays(1).toDate();
                }

                @Override
                public Timestamp timestamp() {
                    long timestamp = getTimestamp();
                    long day = timestamp / (24 * 60 * 60 * 1000);
                    return new Timestamp(timestamp - day);
                }

                /**
                 *  获取系统当前时间 == CURDATE()
                 * @return
                 */
                public String curDate() {
                    return toString(TemplateEnum.FORMAT_YEAR_MONTH_DAY.value);
                }

                /**
                 * 获取年月日
                 *
                 * @return
                 */
                public String getDBNowYMD() {
                    return toString(TemplateEnum.FORMAT_YEAR_MONTH_DAY.zh());
                }

                @Override
                public String toString(String formatTpl) {
                    // 因为系统要看昨天数据，所以当前时间-1天
                    return new DateTime(System.currentTimeMillis()).minusDays(1).toString(formatTpl);
                }
            };


            /**
             * 默认值
             *
             * @return yyyy-MM-dd hh:mm:ss
             */
            public abstract String def();

            /**
             * @return 返回日期类型
             */
            public abstract Date date();

            /**
             * @return 返回时间戳类型
             */
            public abstract Timestamp timestamp();

            /**
             * 格式化
             *
             * @param formatTpl 模板
             * @return
             */
            public abstract String toString(String formatTpl);


            /**
             * 获取当前季度 by jxzhang
             *
             * @param date
             * @return
             */
            public static int quarter(Date date) {
                DateTime dt = new DateTime(date);
                int rs = dt.getMonthOfYear();
                if (rs >= 1 && rs <= 3) {
                    return 1;
                } else if (rs >= 4 && rs <= 6) {
                    return 2;
                } else if (rs >= 7 && rs <= 9) {
                    return 3;
                } else {
                    return 4;
                }
            }

        }

        /**
         * 获取今天的开始时间
         *
         * @return
         */
        public static Date todayStartDate() {
            return DateTime.now().withTime(0, 0, 0, 0).toDate();
        }

        /**
         * 获取今天结束时间
         *
         * @return
         */
        public static Date todayEndDate() {
            return DateTime.now().withTime(23, 59, 59, 59).toDate();
        }

        /**
         * 指定开始时间
         *
         * @return
         */
        public static Date dateStart(Date date) {
            return new DateTime(date).withTime(0, 0, 0, 0).toDate();
        }

        /**
         * 指定结束时间
         *
         * @return
         */
        public static Date dateEnd(Date date) {
            return new DateTime(date).withTime(23, 59, 59, 59).toDate();
        }

        /**
         * 取本月开始时间
         *
         * @return
         */
        public static Date monthStart() {
            return monthStart(new Date());
        }

        /**
         * 取本月开始时间
         *
         * @param date
         * @return
         */
        public static Date monthStart(Date date) {
            return new DateTime(date).withDayOfMonth(1).withTime(0, 0, 0, 0).toDate();
        }

        /**
         * 取本月结束时间
         *
         * @return
         */
        public static Date monthEnd() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        }


        /**
         * 获取系统当前时间
         * <br>
         *
         * @return
         */
        private static long getTimestamp() {
            long t;
            if (TEST_DATE) {
                t = defaultTimeLog;
            } else {
                t = System.currentTimeMillis();
            }
            return t;
        }


    }


    //========================================= 转换时间 =================================================

    /**
     * 转换时间
     */
    public static class Parse {
        /**
         * 格式化日期
         *
         * @param date 日期对象
         * @return String 日期字符串 yyyy-MM-dd HH:mm:ss
         */
        public static String format(Date date) {
            return format(date, null);
        }

        /**
         * 格式化日期
         *
         * @param date 日期 yyyyMMdd
         * @return String 日期字符串 yyyy-MM-dd
         */
        public static String format(Integer date) {
            String dateStr = date.toString();
            if (dateStr.length() != 8) {
                return null;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(dateStr.substring(0, 4));
            sb.append("-");
            sb.append(dateStr.substring(4, 6));
            sb.append("-");
            sb.append(dateStr.substring(6, 8));
            return sb.toString();
        }

        /**
         * 格式化日期
         *
         * @param timeMillis 时间戳
         * @return String 日期字符串 yyyy-MM-dd HH:mm:ss
         */
        public static String format(long timeMillis) {
            return format(new Date(timeMillis), null);
        }

        /**
         * 格式化日期
         *
         * @param timeMillis 时间戳
         * @return 时间str（天,   时:分:秒.毫秒）
         */
        public static String format2(long timeMillis) {
            long day = timeMillis / (24 * 60 * 60 * 1000);
//            long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
//            long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
//            long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//            long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
//            return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
            String edDay = (day > 0 ? "从1970年1月1日的时间过了：" + day + "天" : "") + "\r\n";
            return edDay + format(new Date(timeMillis), TemplateEnum.FORMAT_HOUR_MIN_SECOND.value);
        }

        /**
         * 格式化日期
         * <br>
         * 核心方法
         *
         * @param date      指定日期
         * @param formatTpl 指定格式模版
         * @return
         */
        public static String format(Date date, String formatTpl) {

            if (null == date) {
                return "";
            }
            if (null == formatTpl || formatTpl.length() == 0) {
                formatTpl = TemplateEnum.FORMAT_DEFAULT_DATE.value;
            }
            return new DateTime(date).toString(formatTpl);
        }

        @Getter
        public enum ConvertTo {

            YearMonth() {
                @Override
                public int asInt(Object obj) {
                    if (obj instanceof DateTime) {
                        DateTime dt = (DateTime) obj;
                        int year = dt.getYear();
                        int monthOfYear = dt.getMonthOfYear();
                        String monthOfYearStr = monthOfYear <= 9 ? "0" + monthOfYear : monthOfYear + "";
                        String ymStr = year + monthOfYearStr;
                        return Integer.valueOf(ymStr);
                    } else if (obj instanceof String) {
                        String str = (String) obj;
                        str = str.split("-")[0] + str.split("-")[1];
                        int rs = Integer.parseInt(str);
                        return rs;
                    } else if (obj instanceof Date) {
                        DateTime dt = new DateTime((Date) obj);
                        int year = dt.getYear();
                        int monthOfYear = dt.getMonthOfYear();
                        String monthOfYearStr = monthOfYear <= 9 ? "0" + monthOfYear : monthOfYear + "";
                        String ymStr = year + monthOfYearStr;
                        return Integer.valueOf(ymStr);
                    } else {
                        try {
                            throw new UtilsException("传入参数对像不在 DateTime、Date、String");
                        } catch (UtilsException e) {
                            log.debug(e.toString());
                            return 0;
                        }
                    }
                }
            },

            MonthDay() {
                @Override
                public int asInt(Object obj) {
                    if (obj instanceof DateTime) {
                        DateTime dt = (DateTime) obj;
                        int year = dt.getYear();
                        int monthOfYear = dt.getMonthOfYear();
                        String monthOfYearStr = monthOfYear <= 9 ? "0" + monthOfYear : monthOfYear + "";
                        String ymStr = year + monthOfYearStr;
                        return Integer.valueOf(ymStr);
                    } else if (obj instanceof String) {
                        String str = (String) obj; // TODO 可能越界
                        str = str.split("-")[1] + str.split("-")[2];
                        int rs = Integer.parseInt(str);
                        return rs;
                    } else if (obj instanceof Date) {
                        DateTime dt = new DateTime((Date) obj);
                        int monthOfYear = dt.getMonthOfYear();
                        int dayOfMonth = dt.getDayOfMonth();
                        String dayOfMonthStr = dayOfMonth <= 9 ? "0" + dayOfMonth : dayOfMonth + "";
                        String mdStr = monthOfYear + dayOfMonthStr;
                        return Integer.valueOf(mdStr);
                    } else {
                        try {
                            throw new UtilsException("传入参数对像不在 DateTime、Date、String");
                        } catch (UtilsException e) {
                            log.debug(e.toString());
                            return 0;
                        }
                    }
                }
            };

            /**
             * 转换时间为Int类
             *
             * @param obj new Date() 或者 new DateTime() 或者 '2015-09-06'
             * @return 201509 或者返回 906
             * @author jxzhang on 2016-12-15
             */
            public abstract int asInt(Object obj);

            /**
             * date to string
             *
             * @param date
             * @param formatTpl
             * @return <br>
             * @author jxzhang on 2016-03-18
             */
            public static String asString(Date date, String formatTpl) {
                return new DateTime(date).toString(formatTpl);
            }

            /**
             * string to date
             *
             * @param dateStr
             * @return <br>
             * @author jxzhang on 2016-03-18
             */
            public static Date asDate(String dateStr) {
                return new DateTime(dateStr).toDate();
            }

            /**
             * date to timestamp
             *
             * @param date
             * @return <br>
             * @author jxzhang on 2016-03-18
             */
            public static Timestamp asTimestamp(Date date) {
                return new Timestamp(date.getTime());
            }

        }

    }

    //========================================= 操作时间 =================================================

    /**
     * 操作时间
     */
    public static class Operation {
        /**
         * 加秒
         *
         * @param date 指定时间
         * @param num  秒
         * @return
         * @throws ParseException
         */
        public static Date addSeconds(Date date, int num) throws ParseException {
            return new DateTime(date).plusSeconds(num).toDate();
        }

        /**
         * 加分钟
         *
         * @param date 指定时间
         * @param num  分钟
         * @return
         * @throws ParseException
         */
        public static Date addMinute(Date date, int num) throws ParseException {
            return new DateTime(date).plusMinutes(num).toDate();
        }

        /**
         * 加时
         *
         * @param date 指定时间
         * @param num  时
         * @return
         * @throws ParseException
         */
        public static Date addHour(Date date, int num) throws ParseException {
            return new DateTime(date).plusHours(num).toDate();
        }
    }


    //========================================= 计算时间 =================================================

    /**
     * 计算时间
     */
    public static class Calc {

        /**
         * 计算当前时间距离第二天凌晨的秒数
         *
         * @return
         */
        public static int calcTodayRemainSeconds() {
            DateTime curDateTime = DateTime.now();
            DateTime tomorrowDateTime = curDateTime.plusDays(1).withTime(0, 0, 0, 0);
            return (int) ((tomorrowDateTime.getMillis() - curDateTime.getMillis()) / 1000);
        }

        /**
         * 计算指定时间加上指定时间之后，距离当前时间还剩多少秒
         *
         * @param begin     指定时间
         * @param limitTime 指定分钟之后
         * @return 还剩多少秒
         */
        public static long calcRemainTime(Date begin, int limitTime) {
            long remainTime = 0;
            try {
                remainTime = DateUtil2.Operation.addMinute(begin, limitTime).getTime() - System.currentTimeMillis();
                if (remainTime <= 0) {
                    remainTime = 0;
                }
            } catch (ParseException e) {
                log.error("错误转换时间，这错误：" + e);
                throw Throwables.propagate(e);
            }
            return remainTime;

        }

        /**
         * 计算时间相差多少 天，小时，分钟，秒 by jxzhang
         *
         * @param start 开始时间
         * @param end   结束时间
         * @return 时间相差：多少 天，小时，分钟，秒
         */
        public static String getInterval(Date start, Date end) {
            Period p = getPeriod(start, end);
            return "时间相差：" + p.getDays() + " 天 " + p.getHours() + " 小时 " + p.getMinutes() + " 分钟" + p.getSeconds() + " 秒";
        }

        /**
         * 计算两个日期间隔的
         *
         * @param start
         * @param end
         * @param type  1:日、2:月、3秒
         * @return 两个日期间隔的(天数、月数、秒数)
         * @author jxzhang
         */
        public static int getBetweenDiff(Date start, Date end, int type) {
            LocalDate startLd = new LocalDate(start);
            LocalDate endLd = new LocalDate(end);
            switch (type) {
                case 1:
                    return Days.daysBetween(startLd, endLd).getDays();
                case 2:
                    return Months.monthsBetween(startLd, endLd).getMonths();
                case 3:
                    return Seconds.secondsBetween(startLd, endLd).getSeconds();
                default:
                    return 0;
            }
        }

        /**
         * 一个不可变的时间指定一组持续时间字段值。
         *
         * @param start 开始时间
         * @param end   结束时间
         * @return
         */
        private static Period getPeriod(Date start, Date end) {
            Interval interval = new Interval(start.getTime(), end.getTime());
            return interval.toPeriod();
        }

        @Getter
        @AllArgsConstructor
        enum Offset {
            YearMonth() {
                @Override
                public int asInt(Date date, int offsetMonth) {
                    return preYmStr(new DateTime(date), offsetMonth);
                }

                @Override
                public int asInt(int yearMonth, int offsetMonth) {
                    DateTime dt = new DateTime(yearMonth / 100, yearMonth % 100, 1, 0, 0, 0);
                    return preYmStr(dt, offsetMonth);
                }

                @Override
                public DateTime asDateTime(Date date, int offsetMonth) {
                    DateTime minusMonths = null;
                    if (offsetMonth > 0) {
                        minusMonths = new DateTime(date).plusMonths(offsetMonth);
                    } else {
                        minusMonths = new DateTime(date).minusMonths(Math.abs(offsetMonth));
                    }
                    return minusMonths;
                }

                /**
                 * 通过指定数字计算年月
                 * <p>
                 * 作用:获取上一个月: getCalculateInt(new Date(), -1)
                 *
                 * @param date      '2016-10-04'
                 * @param monthsNum -3
                 * @return '2016-07-04'
                 * @author jxzhang on 2016-12-15
                 */
                @Override
                public Date asDate(Date date, int monthsNum) {
                    return asDateTime(date, monthsNum).toDate();
                }

            },
            Year() {
                @Override
                public Date asDate(Date date, int offsetMonth) {
                    return asDateTime(date, offsetMonth).toDate();
                }

                @Override
                public DateTime asDateTime(Date date, int offsetMonth) {
                    return new DateTime(asInt(date, offsetMonth));
                }

                @Override
                public int asInt(Date date, int offsetMonth) {
                    return asInt(Integer.parseInt(new DateTime(date).toString(TemplateEnum.FORMAT_YEAR.value)), offsetMonth);
                }

                @Override
                public int asInt(int yearMonth, int offsetMonth) {
                    return yearMonth / 100 + offsetMonth;
                }
            };

            /**
             * 通过指定数字计算年月-核心
             *
             * @param dt
             * @param offsetMonth
             * @return
             */
            private static int preYmStr(DateTime dt, int offsetMonth) {
                String preYmStr = dt.plusMonths(offsetMonth).toString(TemplateEnum.FORMAT_YEAR_MONTH.noLine());
                return Integer.parseInt(preYmStr);
            }

            /**
             * 通过指定数字计算年月
             * <p>
             * 作用:获取上一个月: calcPreYearMonth(new Date(), -1)
             *
             * @param date        '2016-10-04'
             * @param offsetMonth -3
             * @return 201607
             * @author jxzhang on 2016-12-15
             */
            public abstract int asInt(Date date, int offsetMonth);

            /**
             * 通过指定数字计算年月
             * <p>
             * 获取前几个月的yearMonth值，如：传入参数yearMonth为201506，offsetMonth为2，结果则返回201504;
             * 若offsetMonth为-2，则返回201508
             *
             * @param yearMonth   年月值
             * @param offsetMonth 往前推几个月（正数为以前年月，负数为将来年月）
             * @return yearMonth - offsetMonth
             */
            public abstract int asInt(int yearMonth, int offsetMonth);

            /**
             * 通过指定数字计算年月
             * <p>
             * 作用:获取上一个月: getCalculateInt(new Date(), -1)
             *
             * @param date        '2016-10-04'
             * @param offsetMonth -3
             * @return DateTime
             * @author jxzhang on 2016-12-15
             */
            public abstract DateTime asDateTime(Date date, int offsetMonth);

            /**
             * 通过指定数字计算年月
             * <p>
             * 作用:获取上一个月: getCalculateInt(new Date(), -1)
             *
             * @param date        '2016-10-04'
             * @param offsetMonth -3
             * @return '2016-07-04'
             * @author jxzhang on 2016-12-15
             */
            public abstract Date asDate(Date date, int offsetMonth);

        }
    }

    //========================================= 判断时间 =================================================
    public static class If {
        /**
         * 是否月的第一天 by jxzhang on 2016-03-07
         *
         * @param d
         * @return
         */
        public static boolean isBeginMonth(Date d) {
            return isBeginMonth(new DateTime(d));
        }

        /**
         * 是否月的最后一天 by jxzhang on 2016-4-22
         *
         * @param d
         * @return
         */
        public static boolean isLastMonth(Date d) {
            return isLastMonth(new DateTime(d));
        }

        /**
         * 是否月的第一天 by jxzhang on 2016-03-07
         *
         * @param dt
         * @return
         */
        public static boolean isBeginMonth(DateTime dt) {
            int dayOfMonth = dt.getDayOfMonth();
            if (1 == dayOfMonth) {
                return true;
            }
            return false;
        }

        /**
         * 是否月的最后一天 by jxzhang on 2016-4-22
         *
         * @param dt
         * @return
         */
        public static boolean isLastMonth(DateTime dt) {
            LocalDate d = LocalDate.now();
            String lastDayOfPreviousMonth = d.dayOfMonth().withMaximumValue().toString(TemplateEnum.FORMAT_YEAR_MONTH_DAY.value, Locale.CHINESE);
            String now = DateTime.now().toString(TemplateEnum.FORMAT_YEAR_MONTH_DAY.value, Locale.CHINESE);

            if (lastDayOfPreviousMonth.equals(now)) {
                return true;
            }
            return false;
        }
    }


    public static void main(String[] args) {
        String before = Get.Day.yesterday(TemplateEnum.FORMAT_DEFAULT_DATE.value);
        System.out.println(before);
    }
}
