package net.chinahrd.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 	 
 * TODO <br/> 	  
 * date: 2018年12月6日 下午9:02:59 <br/> 	 
 * 	 
 * @author guanjian 	  
*/
public class StringSplitUtil {
    public static final String SPLIT_STR = ",";
    public static final String BLANK = " ";
    /**
     * 分割数据
     * 
     * @author 伟
     * @param phoneStr
     * @return
     */
    public static List<String> getSplitList(String phoneStr) {
        if(phoneStr == null){
            return new ArrayList<String>();
        }
        phoneStr = phoneStr.replaceAll("，", SPLIT_STR);
        phoneStr = phoneStr.replaceAll(",", SPLIT_STR);
        phoneStr = phoneStr.replaceAll("、", SPLIT_STR);
        phoneStr = phoneStr.replaceAll("/", SPLIT_STR);
        //全角空格
        phoneStr = phoneStr.replaceAll("　", SPLIT_STR);
        phoneStr = phoneStr.replaceAll(BLANK, SPLIT_STR);
        List<String> phoneList = new ArrayList<String>();
        if (!phoneStr.contains(SPLIT_STR)) {
            phoneList.add(phoneStr.trim());
            return phoneList;
        }
        if (phoneStr.contains(SPLIT_STR)) {
            String[] phone = phoneStr.trim().split(SPLIT_STR);
            phoneList.addAll(Arrays.asList(phone));
        }
        return phoneList;
    }
    /**
     * 分割数据，并拼接前后字符
     * 
     * @author 伟
     * @param phoneStr
     * @param prefix
     * @param tailStr
     * @return
     */
    public static List<String> getSplitList(String phoneStr, String prefix, String tailStr) {
        if(phoneStr == null){
            return new ArrayList<String>();
        }
        phoneStr = processStrReplace(phoneStr, SPLIT_STR);
        List<String> phoneList = new ArrayList<String>();
        if (!phoneStr.contains(SPLIT_STR)) {
            phoneList.add(prefix + phoneStr + tailStr);
            return phoneList;
        }
        if (phoneStr.contains(SPLIT_STR)) {
            String[] phone = phoneStr.split(SPLIT_STR);
            for (String str : phone) {
                if(StringUtils.isEmpty(str)) {
                    continue;
                }
                phoneList.add(prefix + str + tailStr);
            }
        }
        return phoneList;
    }
    
    private static String processStrReplace(String phoneStr, String finalStr) {
        phoneStr = phoneStr.replaceAll("，", finalStr);
        phoneStr = phoneStr.replaceAll(",", finalStr);
        phoneStr = phoneStr.replaceAll("、", finalStr);
        phoneStr = phoneStr.replaceAll("/", finalStr);
        phoneStr = phoneStr.replaceAll("#", finalStr);
        phoneStr = phoneStr.replaceAll(BLANK, finalStr);
        //全角空格
        phoneStr = phoneStr.replaceAll("　", finalStr);
        return phoneStr;
    }
    
    /**
     * 将各种分隔字符替换为空格
     * @author 伟
     * @param str
     * @return
     */
    public static String replaceWithBlank(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }
        str = str.trim();
        return processStrReplace(str, BLANK);
    }
}
