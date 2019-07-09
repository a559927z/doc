package net.chinahrd.entity.dto.pc.ws;

import java.io.Serializable;

public class WeatherDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8388152848240646701L;

    private String windDir;  //风力
    private String tmpMax;  //最高气温
    private String tmpMin;  //最低气温
    private String cond;  //天气状况
    private Integer condCode;    //天气状况编号

    private String date;  //日期
    private String weekDay;  //星期
    private String suggest;  //建议

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getTmpMax() {
        return tmpMax;
    }

    public void setTmpMax(String tmpMax) {
        this.tmpMax = tmpMax;
    }

    public String getTmpMin() {
        return tmpMin;
    }

    public void setTmpMin(String tmpMin) {
        this.tmpMin = tmpMin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public Integer getCondCode() {
        return condCode != null ? resolveFisrt(condCode.toString()) : condCode;
    }

    public void setCondCode(Integer condCode) {
        this.condCode = condCode;
    }

    private Integer resolveFisrt(String code) {
        char fisrt = code.charAt(0);
        if (fisrt == '4') {
            return 402;
        } else if (fisrt == '3') {
            return 305;
        } else if (fisrt == '2') {    //预测2开头的是雾天，所以转成多云图标
            return 104;
        } else {
            return Integer.valueOf(code);
        }
    }
}
