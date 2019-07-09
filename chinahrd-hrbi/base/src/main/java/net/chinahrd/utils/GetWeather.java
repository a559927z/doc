package net.chinahrd.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import net.chinahrd.entity.dto.pc.ws.WeatherDto;

/**
 * 调用请求天气状况接口
 *
 * @author xwli 20161107
 */
public class GetWeather {
    public static final Logger logger = Logger.getLogger(GetWeather.class);

    /**
     * @param httpUrl :请求接口
     * @param httpArg :参数
     * @return 返回结果
     */
    public static String requestForKey(String httpUrl, String httpArg) {
        if (null == httpUrl) {
            httpUrl = "http://apis.baidu.com/heweather/weather/free";
        }
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "b757d63ed864dd413892557c7626a401");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public static String request(String httpUrl) {
        if (null == httpUrl) return null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return result;
        }
        return result;
    }

    public static WeatherDto getWeather(String httpArg) {
        String httpUrl = "http://apis.baidu.com/heweather/weather/free";
        if (null == httpArg) {
            httpArg = "city=guangzhou";
        }
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String weekDay = dateFm.format(date);
        String jsonResult = requestForKey(httpUrl, httpArg);
        System.out.println(jsonResult);
        JSONObject obj = new JSONObject(jsonResult);
        JSONArray array = (JSONArray) obj.get("HeWeather data service 3.0");
        JSONObject object = (JSONObject) array.get(0);
        JSONArray daily_forecast = (JSONArray) object.get("daily_forecast");
        WeatherDto dto = new WeatherDto();
        for (int i = 0; i < daily_forecast.length(); i++) {
            JSONObject daily = (JSONObject) daily_forecast.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = sdf.format(date);
            if (daily.get("date").toString().equals(nowDate)) {
                sdf = new SimpleDateFormat("yyyy年MM月dd日");
                dto.setDate(sdf.format(date));
                JSONObject tmp = new JSONObject(daily.get("tmp").toString());
                dto.setTmpMax(tmp.get("max").toString());
                dto.setTmpMin(tmp.get("min").toString());
                JSONObject wind = new JSONObject(daily.get("wind").toString());
                dto.setWindDir(wind.get("dir").toString());
                JSONObject cond = new JSONObject(daily.get("cond").toString());
                dto.setCond(cond.get("txt_n").toString());
                dto.setCondCode((Integer.valueOf(cond.get("code_n").toString())));
            }
        }
        JSONObject suggestion = (JSONObject) object.get("suggestion");
        JSONObject drsg = (JSONObject) suggestion.get("drsg");
        dto.setSuggest(drsg.get("txt").toString());
        dto.setWeekDay(weekDay);
        return dto;
    }

    /***
     * IP地址转城市
     * @param ipStr
     * @return
     */
    public static String getCity(String ipStr) {
        String httpUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ipStr;
        String jsonResult = request(httpUrl);
        JSONObject obj = new JSONObject(jsonResult);

        if ("invaild ip.".equals(obj.get("data"))) return null;

        JSONObject object = (JSONObject) obj.get("data");
        if (null != object.get("city")) {
            String city = object.get("city").toString();
            return city.indexOf("市") == -1 ? city : city.substring(0, city.indexOf("市"));
        }
        return null;
    }

    public static void main(String[] args) {
        String city = getCity("219.137.150.255");
        city = city.indexOf("市") == -1 ? city : city.substring(0, city.indexOf("市"));
        WeatherDto dto = getWeather("city=" + city);
        System.out.println(dto.getSuggest());
        System.out.println(dto.getDate());
    }
}
