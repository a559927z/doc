package net.chinahrd.service.impl;

import net.chinahrd.dao.ImportMgrDao;
import net.chinahrd.listener.WebConfigListener;
import net.chinahrd.service.ImportExcelService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.DateUtil2;
import net.chinahrd.utils.Str;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Title: ${type_name} <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年04月27日 12:12
 * @Verdion 1.0 版本
 * ${tags}
 */
@Service("importExcelService")
public class ImportExcelServiceImpl implements ImportExcelService {

    @Autowired
    private ImportMgrDao importMgrDao;

    @Override
    public void attendanceMonthly(HttpServletResponse response, String yearmonth, String coustomerId) {
        String year = yearmonth.substring(0, 4);
        int i1 = Integer.parseInt(yearmonth.substring(4, yearmonth.length()));
        String month = i1 <= 9 ? "0" + i1 : "" + i1;

        Properties pps = new Properties();
        try {
            InputStream inputStream = WebConfigListener.class.getClassLoader()
                    .getResourceAsStream("conf/vacation.properties");
            pps.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<HashMap<String, Object>> resultmap = this.importMgrDao.attendanceMonthly(yearmonth, coustomerId);
        List<HashMap<String, Object>> theory = this.importMgrDao.theoryAttendance(yearmonth);
        List<HashMap<String, Object>> cwtMap = this.importMgrDao.queryCheckWorkTypeMap(yearmonth);
        //获取假日天数
        Integer vacationCount = this.vacationCount(yearmonth);

        HashMap<String, Object> cwtHashMap = findCheckWorkTypeMap(cwtMap);
        String title = "月度考勤表";


        String dateTitle = year + "年" + month + "月份" + "考勤汇总表";
        try {
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + title + yearmonth + ".xls"
                    + new String((title + ".xls").getBytes("GBK"), "iso-8859-1"));


            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title);

            //题目样式
            HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
            cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont tfont = workbook.createFont();
            tfont.setFontHeightInPoints((short) 18);
            tfont.setBold(true);
            tfont.setFontName("標楷體");
            cellStyleTitle.setFont(tfont);
            cellStyleTitle.setWrapText(true);

            //工作日样式-表头
            CellStyle workdaystyle = workbook.createCellStyle();
            workdaystyle.setFillForegroundColor(HSSFCellStyle.ALIGN_CENTER);
            workdaystyle.setWrapText(true);
            HSSFFont workdayfont = workbook.createFont();
            workdayfont.setFontHeightInPoints((short) 9);
            workdayfont.setBold(true);
            workdayfont.setFontName("新細明體");
            workdaystyle.setFont(workdayfont);

            //周末/节假日样式-表头
            CellStyle weekendstyle = workbook.createCellStyle();
            weekendstyle.setFillForegroundColor(HSSFCellStyle.ALIGN_CENTER);
            weekendstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            weekendstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            HSSFFont weekendfont = workbook.createFont();
            weekendfont.setFontHeightInPoints((short) 9);
            weekendfont.setBold(true);
            weekendfont.setFontName("新細明體");
            weekendstyle.setFont(weekendfont);

            //未填任何东西的样式-正文
            CellStyle nullstyle = workbook.createCellStyle();
            nullstyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
            nullstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

            //节假日的样式-正文
            CellStyle weekstyle = workbook.createCellStyle();
//			weekstyle.setFillForegroundColor(IndexedColors.GOLD.GETINDEX());
//			WEEKSTYLE.SETFILLPATTERN(CELLSTyle.SOLID_FOREGROUND);
            HSSFFont weekfont = workbook.createFont();
            weekfont.setFontName("宋体");
            weekfont.setColor(IndexedColors.RED.getIndex());
            weekstyle.setFont(weekfont);


            //合并
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, theory.size() * 2 + 1));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));

            HSSFRow row0 = sheet.createRow(0);
            HSSFCell cell0 = row0.createCell(0);
            row0.setHeight((short) 600);
            cell0.setCellValue(dateTitle);
            cell0.setCellStyle(cellStyleTitle);

            HSSFRow row1 = sheet.createRow(1);
            HSSFRow row2 = sheet.createRow(2);

            /**************创建表头*********************/
            for (int i = 1, size = theory.size(); i <= size; i++) {
                sheet.addMergedRegion(new CellRangeAddress(1, 1, i * 2 - 1, i * 2));//每一日占两列
                HSSFCell celln = row1.createCell(i * 2 - 1);
                celln.setCellValue(DateUtil.getDate4CN(theory.get(i - 1).get("days") + "", "月日星期"));
                //根据周六日设置为特定的样式
                Calendar cal = Calendar.getInstance();
                cal.setTime(DateUtil.strToDate(theory.get(i - 1).get("days") + ""));
                int dw = cal.get(Calendar.DAY_OF_WEEK);
                //休息日表头（周六日且计划不用上班 或 计划不用上班）
                if ((dw == 1 || dw == 7) && String.valueOf(theory.get(i - 1).get("hour_count")).equals("0.0") || String.valueOf(theory.get(i - 1).get("hour_count")).equals("0.0")) {
                    celln.setCellStyle(weekendstyle);
                } else {
                    celln.setCellStyle(workdaystyle);
                }
                //某一天
                HSSFCell cellnM = row2.createCell(i * 2 - 1);
                cellnM.setCellValue("上午");
                HSSFCell cellnA = row2.createCell(i * 2);
                cellnA.setCellValue("下午");
            }
            /****************end 创建表头********************/
            int mergedcol = theory.size() * 2 + 1;
            sheet.addMergedRegion(new CellRangeAddress(1, 2, mergedcol,
                    mergedcol));
            HSSFCell cellCount = row1.createCell(theory.size() * 2 + 1);
            cellCount.setCellValue("出勤合计（天）");
            cellCount.setCellStyle(workdaystyle);

            HSSFComment commentendH = cellCount.getCellComment();
            //获取最后一列表头说明
            cellCount.setCellComment(this.makeaHSSFComment(sheet, "出勤合计（天）=打勾+带薪节假日+正常考勤", commentendH));

            String currentEmp = "";
            Integer currentRowNum = 3;
            HSSFRow row = null;

            double dayCount = 0.0D;
            Integer dayofmonth = 0;
            Calendar cl = Calendar.getInstance();
            //存储各种类型的考勤调整的时间
            Map<String, Double> cwtCountMap = CollectionKit.newMap();

            for (int i = 0, size = resultmap.size(); i < size; i++) {

                String empId = resultmap.get(i).get("emp_id") + "";
                String empName = resultmap.get(i).get("user_name_ch") + "";
                String days = resultmap.get(i).get("days") + "";
                String morn = resultmap.get(i).get("morning") + "";
                String after = resultmap.get(i).get("afteroon") + "";
                cl.setTime(DateUtil.strToDate(days));
                dayofmonth = cl.get(Calendar.DAY_OF_MONTH);
                /****************第一列人名****************/
                if (!currentEmp.equals(empId)) {
                    currentEmp = empId;
                    row = sheet.createRow(currentRowNum);
                    //初始化行,根据thoery表获取到底是否假日
                    ini1Row(row, "休息", nullstyle, weekstyle, theory, sheet);
                    HSSFCell cellName = row.createCell(0);
                    cellName.setCellValue(empName);
                    //重置天数
                    dayCount = 0.0D;
                    //重置列
                    currentRowNum++;
                    //重置调整考勤天数
                    cwtCountMap = CollectionKit.newMap();
                }
                /********************正文,逐天填写上下午的考勤情况********************/
                HSSFCell cell1 = row.createCell(dayofmonth * 2 - 1);
                HSSFCell cell2 = row.createCell(dayofmonth * 2);
                //打勾算0.5天；缺勤，迟到，早退0天；事假，病假0天；其余考勤状态0.5天
                //上午
                if ((!Str.IsEmpty(morn)) && (morn.equals("√"))) {
                    cell1.setCellValue(morn);
                    dayCount += 0.5D;
                } else if (!Str.IsEmpty(morn) && morn.length() == 2) {
                    if (morn.equals("缺勤")) {
                        cell1.setCellValue("");
                        cell1.setCellStyle(nullstyle);
                    } else {
                        cell1.setCellValue(morn);
                        cell1.setCellStyle(nullstyle);
                        HSSFComment comment = cell1.getCellComment();
                        cell1.setCellComment(makeaHSSFComment(sheet, "打卡时间：" + resultmap.get(i).get("clock_in_am"), comment));
                    }
                }
                //是调整考勤的
                else if (!Str.IsEmpty(morn) && morn.length() > 2) {
                    //病假，事假：无
                    //cwtId:c0bbcedb57024005a93d9278198412b6
                    //cwtId:7e09efa0bce04eaca623ee84a5546444
                    String cwtid = morn.substring(0, 32);
                    cell1.setCellValue(cwtHashMap.get(cwtid) + "");
                    if (!Str.IsEmpty(cwtid) && cwtid.equals(pps.getProperty("CHACK.WORK.TYPT.LEAVE.JOB"))) {
                    }
                    if (!Str.IsEmpty(cwtid) && cwtid.equals(pps.getProperty("CHACK.WORK.TYPT.LEAVE.SICK"))) {
                    } else if (!Str.IsEmpty(cwtid)) {
                        cwtCountMap = countAllCWT(cwtCountMap, cwtid, 0.5D);
                    }
                }
                //有bug的情况
                else {
                }
                //下午
                if ((!Str.IsEmpty(after)) && (after.equals("√"))) {
                    cell2.setCellValue(after);
                    dayCount += 0.5D;
                } else if (!Str.IsEmpty(after) && after.length() == 2) {
                    if (after.equals("缺勤")) {
                        cell2.setCellValue("");
                        cell2.setCellStyle(nullstyle);
                    } else {
                        cell2.setCellValue(after);
                        cell2.setCellStyle(nullstyle);
                        HSSFComment comment = cell2.getCellComment();
                        cell2.setCellComment(makeaHSSFComment(sheet, "打卡时间：" + resultmap.get(i).get("clock_out_pm"), comment));
                    }
                } else if (!Str.IsEmpty(after) && after.length() > 2) {
                    String cwtid = after.substring(0, 32);
                    cell2.setCellValue(cwtHashMap.get(cwtid) + "");
                    if (!Str.IsEmpty(cwtid) && cwtid.equals("c0bbcedb57024005a93d9278198412b6")) {
                    }
                    if (!Str.IsEmpty(cwtid) && cwtid.equals("7e09efa0bce04eaca623ee84a5546444")) {
                    } else if (!Str.IsEmpty(cwtid)) {
                        cwtCountMap = countAllCWT(cwtCountMap, cwtid, 0.5D);
                    }
                }
                //有bug的情况
                else {
                }

                /**********最后一列显示合计出勤数,并列出所有考勤状态的天数，病假，事假除外。例子：出勤15天，调休1天，婚假4天，带薪节假日1天******/
                if ((i + 1 != size) && (!currentEmp.equals(resultmap.get(i + 1).get("emp_id") + "")) || i + 1 == size) {
                    HSSFCell cellC = row.createCell(theory.size() * 2 + 1);
                    //考勤总计=勾+带薪节假日+正常出勤
                    double nomal = Str.IsEmpty(cwtCountMap.get(pps.get("CHACK.WORK.TYPT.NOMAL")) + "") ? 0.0D : Double.valueOf(cwtCountMap.get(pps.getProperty("CHACK.WORK.TYPT.NOMAL")) + "");
                    cellC.setCellValue(dayCount + vacationCount + nomal);
                    HSSFComment comment = cellC.getCellComment();
                    //获取最后一列的总结各种调整考勤的天数集合
                    String summery = resultSummeryCWTCount(cwtCountMap, cwtHashMap);
                    cellC.setCellComment(this.makeaHSSFComment(sheet, "考勤记录：" + dayCount + "天，带薪节假日:" + vacationCount + "天" + summery, comment));
                }
            }

            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取到该月份的带薪假期日数
     *
     * @return
     */
    public int vacationCount(String yearmonth) {
        StringBuffer sb = new StringBuffer();
        sb.append(yearmonth.substring(0, 4));
        sb.append("-");
        sb.append(yearmonth.substring(4, yearmonth.length()));
        sb.append("-01");
        String beginTime = sb.toString();
        String endTime = DateUtil.getMonthLast(new DateTime(beginTime).toDate());
        return importMgrDao.findVacationDays(beginTime, endTime);

    }

    /**
     * 获取到考勤类型的Hashmap集合key=cwtid,value=考勤类型名
     *
     * @param list
     * @return
     */
    public HashMap<String, Object> findCheckWorkTypeMap(List<HashMap<String, Object>> list) {
        HashMap<String, Object> hmap = new HashMap<String, Object>();
        for (HashMap<String, Object> hm : list) {
            hmap.put(hm.get("checkwork_type_id") + "", hm.get("checkwork_type_name") + "");
        }
        return hmap;
    }

    /**
     * 创建一个标注
     */
    public HSSFComment makeaHSSFComment(HSSFSheet sheet, String text, HSSFComment comment) {

        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置，详见文档
        if (comment == null) {
            comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 80, 20, (short) 60, 50));
        }
        // 设置注释内容
        comment.setString(new HSSFRichTextString(text));
        return comment;
    }

    /**
     * 初始化行
     *
     * @param row
     * @param content   默认节假日填写的值
     * @param workstyle 应工作日的样式
     * @param weekstyle 节假日的样式
     * @param theory    应出勤
     */
    public void ini1Row(HSSFRow row, String content, CellStyle workstyle, CellStyle weekstyle, List<HashMap<String, Object>> theory, HSSFSheet sheet) {
        for (int i = 0, size = theory.size(); i < size; i++) {
            double hourC = 0.00D;
            hourC = Double.valueOf(theory.get(i).get("hour_count") + "");
            //公共节假日
            if (hourC == 0.00D) {
                HSSFCell cellam = row.createCell(i * 2 + 2);
                HSSFCell cellpm = row.createCell(i * 2 + 1);
                cellam.setCellValue(content);
                cellpm.setCellValue(content);
                cellam.setCellStyle(weekstyle);
                cellpm.setCellStyle(weekstyle);
            }
            //设定工作日的背景
            else {
                HSSFCell cellam = row.createCell(i * 2 + 2);
                HSSFCell cellpm = row.createCell(i * 2 + 1);
                cellam.setCellStyle(workstyle);
                cellpm.setCellStyle(workstyle);
            }
        }
    }

    /**
     * 叠加各种类型的考勤调整天数
     *
     * @param cwtCountMap 考勤类型计数集合
     * @param key         考勤类型id
     * @param price       单次调用的叠加天数
     */
    private Map<String, Double> countAllCWT(Map<String, Double> cwtCountMap, String key, double price) {
        if (cwtCountMap == null) {
            cwtCountMap = CollectionKit.newMap();
        } else if (cwtCountMap.get(key) == null) {
            cwtCountMap.put(key, price);
        } else {
            cwtCountMap.put(key, cwtCountMap.get(key) + price);
        }
        return cwtCountMap;
    }

    /**
     * 获取到考勤总天数+带薪假期天数+各类的考勤调整后天数
     *
     * @param cwtCountMap
     * @return
     */
    private String resultSummeryCWTCount(Map<String, Double> cwtCountMap, Map<String, Object> cwtHashMap) {
        StringBuilder sb = new StringBuilder();
        if (cwtCountMap == null) {
            return "";
        }
        Iterator<String> it = cwtCountMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next() + "";
            double count = cwtCountMap.get(key);
            String cwtName = cwtHashMap.get(key) + "";
            sb.append("," + cwtName + ":" + count + ";");
        }
        return sb.toString();
    }
}
