package net.chinahrd.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.annotation.ExportExcel;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelWriterUtil {

    public static final String XLSX = ".xlsx";

    /**
     * 导出Excel数据
     *
     * @param title
     *              标题名称
     * @param classBean
     * @param rows
     * @param path
     *              文件目录
     *              Map{key-字段名： value-中文描述}
     * @return
     */
    public static <T> String writeToExcel(String title, Class<T> classBean, List<T> rows, String path) {
        String destFilePath = getDestFilePath(path);
        log.debug("writeToExcel -- filePath: {} ", destFilePath);
        BigExcelWriter writer = ExcelUtil.getBigWriter(destFilePath);
        writerExcel(writer, classBean, rows);
        // 关闭writer，释放内存
        writer.close();
        return destFilePath;
    }

    /**
     * 获取写文件的对象
     * @author 伟
     * @param path
     * @return
     */
    public static BigExcelWriter getMyBigWriter(String path, String sheetName) {
        String destFilePath = getDestFilePath(path);
        log.debug("writeToExcel -- filePath: {} ", destFilePath);
        BigExcelWriter writer = getMyBigWriterWithDestPath(destFilePath, sheetName);
        return writer;
    }
    
    /**
     * 获取写多sheet文件的对象
     * @author 伟
     * @return
     */
    public static BigExcelWriter getMyBigWriterWithDestPath(String destFilePath, String sheetName) {
        log.debug("writeToExcel -- filePath: {} ", destFilePath);
        BigExcelWriter writer = ExcelUtil.getBigWriter(destFilePath, sheetName);
        return writer;
    }

    /**
     * 获取文件路径
     * @author 伟
     * @param path
     * @return
     */
    public static String getDestFilePath(String path) {
        String destFilePath = path + "/" + (UuidUtil.uuid32() + XLSX);
        return destFilePath;
    }
    
    /**
     * 获取文件路径
     * @author 伟
     * @param path
     * @param fileId
     * @return
     */
    public static String getDestFilePath(String path,String fileId) {
        String destFilePath = path + "/" + (fileId + XLSX);
        return destFilePath;
    }

    /**
     * 调用writer写数据
     * @author 伟
     * @param writer
     * @param clazz
     * @param list
     */
    public static <T> void writerExcel(BigExcelWriter writer, Class<T> clazz, List<T> list){
        // 处理数据行
        List<List<Object>> rowLists = processDataRows(clazz, list, false);
        // 一次性写出内容，使用默认样式
        writer.write(rowLists);
    }
    
    /**
     * 调用writer写导入错误数据
     * @author 伟
     * @param writer
     * @param clazz
     * @param list
     */
    public static <T> void writerErrorExcel(BigExcelWriter writer, Class<T> clazz, List<T> list){
        // 处理数据行
        List<List<Object>> rowLists = processDataRows(clazz, list, true);
        // 一次性写出内容，使用默认样式
        writer.write(rowLists);
    }

    /**
     * 处理数据行
     * 
     * @author 伟
     * @param classBean
     * @param rows
     * @return
     */
    public static <T> List<List<Object>> processDataRows(Class<T> classBean, List<T> rows, boolean errorExport) {
        //自定义标题别名
        List<String> headPropertyList = new ArrayList<String>();
        List<Object> headChnNameList = new ArrayList<Object>();
      
       /* 取不到父类的父类
        Field[] fields = classBean.getDeclaredFields();
        Field[] declaredFields = classBean.getSuperclass().getDeclaredFields();
        fields = ArrayUtils.addAll(fields, declaredFields);*/
//        List<Field> list  = new ArrayList<>();
        Field[] fields = ReflectUtil.getFieldsDirectly(classBean, true);
//        CollUtil.addAll(list, fields);
//        log.debug(JSON.toJSONString(list));
        //根据字段排序
        /*Collections.sort(list, new Comparator<Field>() {
            @Override
            public int compare(Field h1, Field h2) {
                ExportExcel annotation1 = h1.getAnnotation(ExportExcel.class);
                ExportExcel annotation2 = h2.getAnnotation(ExportExcel.class);
                if(annotation1==null&&annotation2==null){
                    return 0;
                }
                if(annotation1!=null&&annotation2==null){
                    return 1;
                }
                if(annotation1==null&&annotation2!=null){
                    return -1;
                }
                return annotation1.indexCol() - annotation2.indexCol();
            }
        });      */  
//        log.debug(JSON.toJSONString(list));
        for (Field field : fields) {
            ExportExcel annotation = field.getAnnotation(ExportExcel.class);
            boolean fieldHasAnno = field.isAnnotationPresent(ExportExcel.class);
            if (!fieldHasAnno) {
                continue;
            }
            // 不是错误导出不显示错误信息列
            if(!errorExport && "errorMsg".equals(field.getName())){
                continue;
            }
            if (annotation.export()) {
                headPropertyList.add(field.getName());
                headChnNameList.add(annotation.name());
            }
        }
        
        List<List<Object>> rowLists = new ArrayList<List<Object>>();
        rowLists.add(headChnNameList);
        log.debug("writeToExcel --column size {}, headers :{} {}", headPropertyList.size(),
                JSON.toJSON(headPropertyList), JSON.toJSON(headChnNameList));
        
        for (T t : rows) {
            List<Object> values = new ArrayList<Object>();

            for (String property : headPropertyList) {
                try {
                    Object fieldValue = ReflectUtil.getFieldValue(t, property);
                    Field field = ReflectUtil.getField(t.getClass(), property);
                    JsonFormat annotation = field.getAnnotation(JsonFormat.class);
            		boolean fieldHasAnno = field.isAnnotationPresent(JsonFormat.class);
                    if(fieldValue!=null&& fieldValue instanceof Date && fieldHasAnno) {
                    	fieldValue = formatDate(property, fieldValue,annotation);
                    }
                    values.add(fieldValue);
                } catch (SecurityException e) {
                    log.debug("writeToExcel SecurityException filed  {}", e);
                } catch (IllegalArgumentException e) {
                    log.debug("writeToExcel IllegalArgumentException filed  {}", e);
                } catch (Exception e) {
                	log.debug("writeToExcel Exception filed  {}", e);
				}
            }
            rowLists.add(values);
        }
        return rowLists;
    }

	public static Object formatDate(String property, Object value,JsonFormat annotation ) throws Exception {
		String pattern = annotation.pattern();
		if (value != null && StringUtils.isNotBlank(pattern)) {
			Date date = (Date) value;
			return DTUtils.date2String(date,pattern);
		}
		return value;
	}
//	public static String formatDate(Class<?> clszz, String property, Object value) throws Exception {
//		String val = "";
//		Field field = ReflectUtil.getField(clszz, property);
//		if(field==null) {
//			return val;
//		}
//		JsonFormat annotation = field.getAnnotation(JsonFormat.class);
//		boolean fieldHasAnno = field.isAnnotationPresent(JsonFormat.class);
//		if (!fieldHasAnno) {
//			Type type = field.getGenericType();
//			String typeName = type.getTypeName();
//			if (StringUtils.equals("java.util.Date", typeName)) {
//				String pattern = annotation.pattern();
//				if (value != null && StringUtils.isNotBlank(pattern)) {
//					return DTUtils.getDateFormatStr(pattern);
//				}
//			}
//			return value.toString();
//		}
//		return val;
//	}
    
    
    public static <T> Map<String, String> getHeadMap(Class<T> classBean, boolean errorExport){
        Map<String, String> headMap = MapUtil.newHashMap();
        Field[] fields = ReflectUtil.getFieldsDirectly(classBean, true);
        for (Field field : fields) {
            ExportExcel annotation = field.getAnnotation(ExportExcel.class);
            boolean fieldHasAnno = field.isAnnotationPresent(ExportExcel.class);
            if (!fieldHasAnno) {
                continue;
            }
            // 不是错误导出不显示错误信息列
            if(!errorExport && "errorMsg".equals(field.getName())){
                continue;
            }
            if (annotation.export()) {
                headMap.put(field.getName(), annotation.name());
            }
        }
        return headMap;
    }
}
