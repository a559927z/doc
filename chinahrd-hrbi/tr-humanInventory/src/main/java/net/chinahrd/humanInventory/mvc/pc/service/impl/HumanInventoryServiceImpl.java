package net.chinahrd.humanInventory.mvc.pc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryChartDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryImportErrorDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryImportInfoDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryInputTypeDto;
import net.chinahrd.entity.dto.pc.humanInventory.HumanInventoryParamDto;
import net.chinahrd.entity.enums.HumanInventoryEnum;
/**
 * 项目人力盘点
 * @author malong and lixingwen
 */
import net.chinahrd.humanInventory.mvc.pc.dao.HumanInventoryDao;
import net.chinahrd.humanInventory.mvc.pc.service.HumanInventoryService;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

@Service("humanInventoryService")
public class HumanInventoryServiceImpl implements HumanInventoryService {

    @Autowired
    private HumanInventoryDao humanInventoryDao;

    private boolean isRepeat = false;


    public List<HumanInventoryDto> queryProjectProgress(String customerId) {
        return humanInventoryDao.queryProjectProgress(customerId);
    }

    /**
     * 获取本年度项目总数和人均项目数
     *
     * @param customerId
     * @param organId
     * @param year
     */
    @Override
    public Map<String, Object> getProjectConAndAvgNum(String customerId, String organId, String year) {
    	List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        List<HumanInventoryChartDto> list = humanInventoryDao.getProjectConAndAvgNum(customerId, organId, year, subOrganIdList);
        int proNum = 0;
        double avgNum = 0.00D;
        if (list != null && list.size() > 0) {
            proNum = list.get(0).getConNum();
            avgNum = list.get(0).getAvgNum();
        }
        Map<String, Object> map = CollectionKit.newMap();
        map.put("year", year);
        map.put("conNum", proNum);
        map.put("avgNum", avgNum);
        return map;
    }

    /**
     * 获取本年度项目投入产出
     *
     * @param customerId
     * @param organId
     * @param year
     */
    @Override
    public Map<String, Object> getProjectInputOutputNum(String customerId, String organId, String year) {
        Map<String, Object> map = CollectionKit.newMap();
        List<HumanInventoryChartDto> list = humanInventoryDao.getProjectInputOutputNum(customerId, organId, year);
        double inputNum = 0d, outputNum = 0d;
        if (list != null && list.size() > 0) {
            inputNum = list.get(0).getSumInput();
            outputNum = list.get(0).getSumOutput();
        }
        map.put("sumInputNum", inputNum);
        map.put("sumOutputNum", outputNum);
        return map;
    }

    /**
     * 获取本年度项目负荷
     *
     * @param customerId
     * @param organId
     * @param year
     */
    @Override
    public Map<String, Object> getProjectLoadNum(String customerId, String organId, String year) {
        List<HumanInventoryChartDto> list = humanInventoryDao.getProjectLoadNum(customerId, organId, year);
        double loadNum = 0d;
        if (list != null && list.size() > 0) {
            loadNum = list.get(0).getPerNum();
        }
        Map<String, Object> map = CollectionKit.newMap();
        map.put("loadNum", loadNum);
        return map;
    }

    /**
     * 主导项目
     */
    public PaginationDto<HumanInventoryDto> findLeadingProject(String time,
    		HumanInventoryParamDto humanDto, PaginationDto<HumanInventoryDto> dto) {
    	List<String> organPermitIds = getOrganPermitId();
        if (null == organPermitIds || organPermitIds.isEmpty()) return null;
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(humanDto.getOrganId());
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        Map<String, Object> map = CollectionKit.newMap();
        map.put("organId", humanDto.getOrganId());
        map.put("customerId", humanDto.getCustomerId());
        map.put("keyName", humanDto.getKeyName());
        map.put("time", time);
        map.put("startDate", humanDto.getStartDate());
        map.put("endDate", humanDto.getEndDate());
        map.put("principal", humanDto.getPrincipal());
        map.put("startManpowerInYear", humanDto.getStartManpowerInYear());
        map.put("endManpowerInYear", humanDto.getEndManpowerInYear());
        map.put("startManpowerInMonth", humanDto.getStartManpowerInMonth());
        map.put("endManpowerInMonth", humanDto.getEndManpowerInMonth());
        map.put("startManpowerLastMonth", humanDto.getStartManpowerLastMonth());
        map.put("endManpowerLastMonth", humanDto.getEndManpowerLastMonth());
        map.put("startFeeYearInput", humanDto.getStartFeeYearInput());
        map.put("endFeeYearInput", humanDto.getEndFeeYearInput());
        map.put("startFeeMonthInput", humanDto.getStartFeeMonthInput());
        map.put("endFeeMonthInput", humanDto.getEndFeeMonthInput());
        map.put("startFeeLastMonthInput", humanDto.getStartFeeLastMonthInput());
        map.put("endFeeLastMonthInput", humanDto.getEndFeeLastMonthInput());
        map.put("startGainInYear", humanDto.getStartGainInYear());
        map.put("endGainInYear", humanDto.getEndGainInYear());
        map.put("startGainInMonth", humanDto.getStartGainInMonth());
        map.put("endGainInMonth", humanDto.getEndGainInMonth());
        map.put("startGainLastMonth", humanDto.getStartGainLastMonth());
        map.put("endGainLastMonth", humanDto.getEndGainLastMonth());
        map.put("projectProgress", humanDto.getProjectProgress());
        map.put("organPermitIds", organPermitIds);
        map.put("subOrganIdList", subOrganIdList);
        map.put("rowBounds", rowBounds);
        int count = humanInventoryDao.findLeadingProjectCount(map);
        List<HumanInventoryDto> dtos = humanInventoryDao.findLeadingProject(map);
        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 参与项目
     */
    public PaginationDto<HumanInventoryDto> findParticipateProject(String time, HumanInventoryParamDto humanDto, 
    		PaginationDto<HumanInventoryDto> dto) {

        List<String> organPermitIds = getOrganPermitId();
        if (null == organPermitIds || organPermitIds.isEmpty()) {
            return null;
        }
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(humanDto.getOrganId());
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        Map<String, Object> map = CollectionKit.newMap();
        map.put("organId", humanDto.getOrganId());
        map.put("keyName", humanDto.getKeyName());
        map.put("customerId", humanDto.getCustomerId());
        map.put("time", time);
        map.put("startDate", humanDto.getStartDate());
        map.put("endDate", humanDto.getEndDate());
        map.put("startManpowerInYear", humanDto.getStartManpowerInYear());
        map.put("endManpowerInYear", humanDto.getEndManpowerInYear());
        map.put("startManpowerInMonth", humanDto.getStartManpowerInMonth());
        map.put("endManpowerInMonth", humanDto.getEndManpowerInMonth());
        map.put("startManpowerLastMonth", humanDto.getStartManpowerLastMonth());
        map.put("endManpowerLastMonth", humanDto.getEndManpowerLastMonth());
        map.put("projectProgress", humanDto.getProjectProgress());
        map.put("organPermitIds", organPermitIds);
        map.put("subOrganIdList", subOrganIdList);
        map.put("rowBounds", rowBounds);
        int count = humanInventoryDao.findParticipateProjectCount(map);
        List<HumanInventoryDto> dtos = humanInventoryDao.findParticipateProject(map);

        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 员工统计表
     */
    public PaginationDto<HumanInventoryDto> findEmployeeStatistics(String time, HumanInventoryParamDto humanDto,
    		PaginationDto<HumanInventoryDto> dto) {

        List<String> organPermitIds = getOrganPermitId();
        if (null == organPermitIds || organPermitIds.isEmpty()) {
            return null;
        }
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        Map<String, Object> map = CollectionKit.newMap();
        map.put("organId", humanDto.getOrganId());
        map.put("keyName", humanDto.getKeyName());
        map.put("customerId", humanDto.getCustomerId());
        map.put("startProjectNumInYear", humanDto.getStartProjectNumInYear());
        map.put("endProjectNumInYear", humanDto.getEndProjectNumInYear());
        map.put("startProjectNum", humanDto.getStartProjectNum());
        map.put("endProjectNum", humanDto.getEndProjectNum());
        map.put("principal", humanDto.getPrincipal());
        map.put("year", getYear(time));
        map.put("organPermitIds", organPermitIds);
        map.put("rowBounds", rowBounds);
        int count = humanInventoryDao.findEmployeeStatisticsCount(map);
        List<HumanInventoryDto> dtos = humanInventoryDao.findEmployeeStatistics(map);

        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 当前人力投入
     */
    @Override
    public PaginationDto<HumanInventoryDto> getCurrentEmployeeList(String customerId, String organId, String projectId,
                                                                   String time, PaginationDto<HumanInventoryDto> dto) {
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        int count = humanInventoryDao.findCurrentEmployeeCount(customerId, organId, projectId, time);
        List<HumanInventoryDto> list = humanInventoryDao.findCurrentEmployee(customerId, time, organId, projectId, rowBounds);
        dto.setRecords(count);
        dto.setRows(list);
        return dto;
    }

    /**
     * 参与项目明细
     */
    @Override
    public List<HumanInventoryDto> getParticipateProjectDetail(String customerId, String empId, String time) {
        List<HumanInventoryDto> list = humanInventoryDao.findParticipateProjectDetail(customerId, empId, getYear(time));
        return list;
    }

    /**
     * 人力投入环比趋势3
     */
    @Override
    public List<HumanInventoryDto> queryManpowerInputByMonth(String customerId, String organId, String projectId) {
        List<HumanInventoryDto> list = humanInventoryDao.queryManpowerInputByMonth(customerId, organId, projectId,
                DateUtil.getDBNow());
        return list;
    }

    /**
     * 子项目人力投入明细
     */
    @Override
    public List<HumanInventoryDto> querySubprojectById(String customerId, String organId, String projectId, String type) {
        Map<String, Object> param = CollectionKit.newMap();
        Map<String, Object> paramParent = CollectionKit.newMap();
        List<HumanInventoryDto> listNew = CollectionKit.newList();
        if ("1".equals(type)) {
            paramParent.put("customerId", customerId);
            paramParent.put("organId", organId);
            paramParent.put("projectId", projectId);
            paramParent.put("now", DateUtil.getDBNow());
            List<HumanInventoryDto> listParent = humanInventoryDao.querySubprojectById(paramParent);
            listNew.addAll(listParent);
        }
        param.put("customerId", customerId);
        param.put("organId", organId);
        param.put("parentId", projectId);
        param.put("now", DateUtil.getDBNow());
        List<HumanInventoryDto> list = humanInventoryDao.querySubprojectById(param);
        listNew.addAll(list);
        return listNew;
    }

    /**
     * 查询费用类型
     *
     * @param customerId
     * @return
     */
    public List<HumanInventoryDto> queryProjectInputTypeInfo(String customerId) {
        return humanInventoryDao.queryProjectInputTypeInfo(customerId);
    }

    /**
     * 费用明细
     */
    @Override
    public List<HumanInventoryInputTypeDto> queryFeeDetailById(String customerId, String organId, String projectId, String type) {
        Map<String, Object> param = CollectionKit.newMap();
        Map<String, Object> paramParent = CollectionKit.newMap();
        List<String> listCost = CollectionKit.newList();
        List<HumanInventoryInputTypeDto> listNew = CollectionKit.newList();
        List<HumanInventoryDto> listType = humanInventoryDao.queryProjectInputTypeInfo(customerId);
        for (HumanInventoryDto dto : listType) {
            listCost.add(dto.getProjectTypeId());
        }
        if ("1".equals(type)) {
            paramParent.put("customerId", customerId);
            paramParent.put("organId", organId);
            paramParent.put("projectId", projectId);
            paramParent.put("now", DateUtil.getDBNow());
            paramParent.put("listCost", listCost);
            List<HumanInventoryInputTypeDto> listParent = humanInventoryDao.queryFeeDetailById(paramParent);
            listNew.addAll(listParent);
        }
        param.put("customerId", customerId);
        param.put("organId", organId);
        param.put("parentId", projectId);
        param.put("now", DateUtil.getDBNow());
        param.put("listCost", listCost);
        List<HumanInventoryInputTypeDto> list = humanInventoryDao.queryFeeDetailById(param);

        listNew.addAll(list);
        return listNew;
    }

    /**
     * 投入产出比
     */
    @Override
    public Map<String, Object> queryInputOutputByMonth(String customerId, String organId, String projectId,
                                                       String time) {
        Map<String, Object> map = CollectionKit.newMap();
        List<HumanInventoryDto> list = humanInventoryDao.queryInputOutputByMonth(customerId, organId, projectId,
                DateUtil.getDBNow());
        HumanInventoryDto dto = humanInventoryDao.queryInputOutputByYear(customerId, organId, projectId, getYear(time));
        map.put("list", list);
        map.put("dto", dto);
        return map;
    }

    /**
     * 登录人所有数据权限ID集
     *
     * @return
     */
    private List<String> getOrganPermitId() {
        List<OrganDto> organPermit = EisWebContext.getCurrentUser().getOrganPermit();
        if (null == organPermit) {
            return null;
        }
        List<String> rs = CollectionKit.newList();
        for (OrganDto organDto : organPermit) {
            rs.add(organDto.getOrganizationId());
        }
        return rs;
    }

    private int getYear(String time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getDate());
        return cal.get(Calendar.YEAR);
    }

    /**
     * 投入产出分析
     *
     * @param customerId
     * @param organId
     * @param year
     * @param quarter
     */
    @Override
    public Map<String, Object> queryInputOutputAmount(String customerId, String organId, String year, String quarter) {
        List<HumanInventoryChartDto> list = humanInventoryDao.queryInputOutputAmount(customerId, organId, year,
                quarter);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Double input_con = 0d, output_con = 0d;
        int size = list.size() == 0 ? 0 : list.size();
        Object[] sum_input_con = new Object[size + 1];
        Object[] sum_output_con = new Object[size + 1];
        String[] sum_date = new String[size + 1];
        Object[] sum_input = new Object[size + 1];
        Object[] sum_output = new Object[size + 1];
        Double[] percent = new Double[size + 1];
        if (list != null && list.size() > 0 && list.get(0) != null) {
            for (int i = 0; i < list.size(); i++) {
                HumanInventoryChartDto dto = list.get(i);
                input_con = ArithUtil.sum(input_con, dto.getSumInput());
                output_con = ArithUtil.sum(output_con, dto.getSumOutput());
                sum_date[i + 1] = dto.getDate();
                sum_input[i + 1] = dto.getSumInput();
                sum_output[i + 1] = dto.getSumOutput();
                percent[i + 1] = ArithUtil.div(dto.getSumOutput() * 100, dto.getSumInput(), 2);
                sum_input_con[i + 1] = '-';
                sum_output_con[i + 1] = '-';
            }
        }
        sum_date[0] = "当年累计";
        sum_input[0] = "-";
        sum_output[0] = "-";
        sum_input_con[0] = input_con;
        sum_output_con[0] = output_con;
        if (input_con > 0) {
            percent[0] = ArithUtil.div(output_con * 100, input_con, 2);
        } else {
            percent[0] = 0d;
        }
        map.put("sumInputCon", sum_input_con);
        map.put("sumOutputCon", sum_output_con);
        map.put("sumDate", sum_date);
        map.put("sumInput", sum_input);
        map.put("sumOutput", sum_output);
        map.put("percent", percent);
        return map;
    }

//    /**
//     * double四舍五入保留两位
//     */
//    public Double formatDouble(Double data) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        return Double.parseDouble(df.format(data));
//    }

    /**
     * 投入产出地图
     *
     * @param customerId
     * @param organId
     * @param year       3.732
     */
    @Override
    public Map<String, Object> queryInputOutputMap(String customerId, String organId, String year) {
        List<HumanInventoryChartDto> list = humanInventoryDao.queryInputOutputMap(customerId, organId, year);
        Map<String, Object> map = CollectionKit.newMap();
        Object[] redArrLast = new Object[list.size()];
        Object[] yelArrLast = new Object[list.size()];
        Object[] greArrLast = new Object[list.size()];
        int redNum = 0, yelNum = 0, greNum = 0;
        double maxNum = 0D;
        List<Double> linkedList = CollectionKit.newLinkedList();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getSumOutput() > 0) {
                if (list.get(i).getSumInput() > list.get(i).getSumOutput()) {
                    linkedList.add(list.get(i).getSumInput());
                } else {
                    linkedList.add(list.get(i).getSumOutput());
                }

                Double num = list.get(i).getSumOutput() == 0D ? 4.0 : list.get(i).getSumInput() / list.get(i).getSumOutput();
                if (num > 3.732) {
                    // red
                    Map<String, Object> redMap = CollectionKit.newMap();
                    list.get(i).getProjectName();
                    Double[] redArr = new Double[list.size() + 1];
                    redArr[0] = list.get(i).getSumInput();
                    redArr[1] = list.get(i).getSumOutput();
                    redMap.put("name", list.get(i).getProjectName());
                    redMap.put("value", redArr);
                    redArrLast[redNum] = redMap;
                    redNum++;
                } else if (num > 1 && num < 3.732) {
                    // yellow
                    Map<String, Object> yelMap = CollectionKit.newMap();
                    Double[] yelArr = new Double[list.size() + 1];
                    yelArr[0] = list.get(i).getSumInput();
                    yelArr[1] = list.get(i).getSumOutput();
                    yelMap.put("name", list.get(i).getProjectName());
                    yelMap.put("value", yelArr);
                    yelArrLast[yelNum] = yelMap;
                    yelNum++;
                } else if (num < 1) {
                    // green
                    Map<String, Object> greMap = CollectionKit.newMap();
                    Double[] greArr = new Double[list.size() + 1];
                    greArr[0] = list.get(i).getSumInput();
                    greArr[1] = list.get(i).getSumOutput();
                    greMap.put("name", list.get(i).getProjectName());
                    greMap.put("value", greArr);
                    greArrLast[greNum] = greMap;
                    greNum++;
                }
//                }
            }
        }
        if (linkedList != null && linkedList.size() > 0) {
            Collections.sort(linkedList);
            maxNum = linkedList.get(linkedList.size() - 1);
        }
        map.put("redDatas", redArrLast);
        map.put("yellowDatas", yelArrLast);
        map.put("greenDatas", greArrLast);
        map.put("maxNum", maxNum);
        return map;
    }

    /**
     * 盈亏项目数分析，盈亏金额分析
     *
     * @param customerId
     * @param organId
     * @param year
     * @param quarter
     */
    @Override
    public Map<String, Object> queryProfitLossProject(String customerId, String organId, String year, String quarter) {
        LinkedList<HumanInventoryChartDto> list = humanInventoryDao.queryProfitLossProject(customerId, organId, year,
                quarter);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        int more_con = 0;
        int less_con = 0;
        int size = list.size() == 0 ? 0 : list.size() - 1;
        String[] sum_date = new String[size + 1];
        Object[] total_more_con = new Object[size + 1];
        Object[] total_less_con = new Object[size + 1];
        Object[] sum_more_con = new Object[size + 1];
        Object[] sum_less_con = new Object[size + 1];
        Double more_gain = 0D;
        Double less_gain = 0D;
        Object[] total_more_gain = new Object[size + 1];
        Object[] total_less_gain = new Object[size + 1];
        Object[] sum_more_gain = new Object[size + 1];
        Object[] sum_less_gain = new Object[size + 1];
        if (list != null && list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) {
                sum_date[i + 1] = list.get(i).getDate();
                sum_more_con[i + 1] = list.get(i).getSumMoreCon();
                sum_less_con[i + 1] = Math.abs(list.get(i).getSumLessCon());

                sum_more_gain[i + 1] = list.get(i).getSumMoreGain();
                sum_less_gain[i + 1] = Math.abs(list.get(i).getSumLessGain());
                
                total_more_con[i + 1] = '-';
                total_less_con[i + 1] = '-';
                total_more_gain[i + 1] = '-';
                total_less_gain[i + 1] = '-';
            }
            more_con += list.get(list.size() - 1).getSumMoreCon();
            less_con += list.get(list.size() - 1).getSumLessCon();
            more_gain = list.get(list.size() - 1).getSumMoreGain();
            less_gain = list.get(list.size() - 1).getSumLessGain();
        }
        sum_date[0] = "当年累计";
        total_more_con[0] = more_con;
        total_less_con[0] = less_con;
        total_more_gain[0] = more_gain;
        total_less_gain[0] = Math.abs(less_gain);
        sum_more_con[0] = "-";
        sum_less_con[0] = "-";
        sum_more_gain[0] = "-";
        sum_less_gain[0] = "-";
        map.put("sumDate", sum_date);
        map.put("totalMoreCon", total_more_con);
        map.put("totalLessCon", total_less_con);
        map.put("sumMoreCon", sum_more_con);
        map.put("sumLessCon", sum_less_con);
        map.put("totalMoreGain", total_more_gain);
        map.put("totalLessGain", total_less_gain);
        map.put("sumMoreGain", sum_more_gain);
        map.put("sumLessGain", sum_less_gain);
        return map;
    }

    /**
     * 盈亏项目数分析，盈亏金额分析 获取盈利总金额，亏损总金额
     *
     * @param customerId
     * @param organId
     * @param year
     */
    @Override
    public Map<String, Object> getProfitAndLossCountAmount(String customerId, String organId, String year,
                                                           String month) {
        List<HumanInventoryChartDto> list = humanInventoryDao.getProfitAndLossCountAmount(customerId, organId, year,
                month);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Double pNum = 0D;
        Double lNum = 0D;
        for (HumanInventoryChartDto l : list) {
            // A表示盈利总金额，B表示亏损总金额
            if ("A".equals(l.getFlag())) {
                pNum = l.getSumGain();
            } else {
                lNum = l.getSumGain();
            }
        }
        map.put("pNum", pNum);
        map.put("lNum", lNum);
        return map;
    }

    /**
     * 盈亏项目数分析，盈亏金额分析 获取盈亏项目明细
     *
     * @param customerId
     * @param organId
     * @param year
     */
    @Override
    public Map<String, Object> getProfitAndLossProjectDetail(String customerId, String organId, String year,
                                                             String month) {
        List<HumanInventoryChartDto> list = humanInventoryDao.getProfitAndLossProjectDetail(customerId, organId, year,
                month);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        int pNum = 0, lNum = 0;
        List<Object> pList = CollectionKit.newList();
        List<Object> lList = CollectionKit.newList();
        for (int i = 0; i < list.size(); i++) {
            if ("A".equals(list.get(i).getFlag())) {
                pList.add(list.get(i));
                pNum++;
            } else {
                lList.add(list.get(i));
                lNum++;
            }
        }
        map.put("pNum", pNum);
        map.put("pDetail", pList);
        map.put("lNum", lNum);
        map.put("lDetail", lList);
        return map;
    }

    /**
     * 项目投入统计-人力统计
     *
     * @param customerId
     * @param organId
     * @param year
     * @param quarter
     */
    @Override
    public Map<String, Object> queryProjectManpower(String customerId, String organId, String year, String quarter) {
        List<HumanInventoryChartDto> list = humanInventoryDao.queryProjectManpower(customerId, organId, year, quarter);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Double sum = 0d;
        int size = list.size() == 0 ? 0 : list.size();
        String[] sumDate = new String[size + 1];
        Object[] sumInput = new Object[size + 1];
        Object[] totalInput = new Object[size + 1];
        for (int i = 0; i < list.size(); i++) {
            HumanInventoryChartDto dto = list.get(i);
            sum = ArithUtil.sum(sum, dto.getSumInput());
            sumDate[i + 1] = dto.getDate();
            sumInput[i + 1] = dto.getSumInput();
            totalInput[i + 1] = '-';
        }
        totalInput[0] = sum;
        sumDate[0] = "当年累计";
        sumInput[0] = "-";
        map.put("totalInput", totalInput);
        map.put("sumDate", sumDate);
        map.put("sumInput", sumInput);
        return map;
    }

    /**
     * 项目投入统计-费用统计
     *
     * @param customerId
     * @param organId
     * @param year
     * @param quarter
     */
    @Override
    public Map<String, Object> queryProjectInputCost(String customerId, String organId, String year, String quarter) {
        List<HumanInventoryChartDto> list = humanInventoryDao.queryProjectInputCost(customerId, organId, year, quarter);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Double sum = 0D;
        int size = list.size() == 0 ? 0 : list.size();
        String[] sumDate = new String[size + 1];
        Object[] sumInput = new Object[size + 1];
        Object[] totalInput = new Object[size + 1];
        for (int i = 0; i < list.size(); i++) {
            HumanInventoryChartDto dto = list.get(i);
            sum = ArithUtil.sum(sum, dto.getSumOutlay());
            sumDate[i + 1] = dto.getDate();
            sumInput[i + 1] = dto.getSumOutlay();
            totalInput[i + 1] = '-';
        }
        totalInput[0] = sum;
        sumDate[0] = "当年累计";
        sumInput[0] = "-";
        map.put("totalInput", totalInput);
        map.put("sumDate", sumDate);
        map.put("sumInput", sumInput);
        return map;
    }

    /**
     * 项目类型分析
     *
     * @param customerId
     * @param organId
     * @param year
     */
    @Override
    public Map<String, Object> queryProjectType(String customerId, String organId, String year) {
        List<HumanInventoryChartDto> list = humanInventoryDao.queryProjectType(customerId, organId, year);
        List<Object> lastList = CollectionKit.newList();
        Map<String, Object> lastMap = CollectionKit.newMap();
        String[] names = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            HumanInventoryChartDto dto = list.get(i);
            Map<String, Object> map = CollectionKit.newMap();
            map.put("name", dto.getProjectTypeName() + ", " + dto.getConNum());
            map.put("value", dto.getConNum());
            lastList.add(map);
            names[i] = dto.getProjectTypeName();
        }
        lastMap.put("typeNames", names);
        lastMap.put("typeDatas", lastList);
        return lastMap;
    }

    /**
     * 部门人力投入
     *
     * @param customerId
     * @param organId
     * @param projectId
     * @param time
     */
    @Override
    public List<Object> getDepartmentInput(String customerId, String organId, String projectId, String time) {
        List<HumanInventoryDto> list = humanInventoryDao.getDepartmentInput(customerId, projectId, getYear(time));
        List<Object> lastList = CollectionKit.newList();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (HumanInventoryDto dto : list) {
            map.put(dto.getOrganName(), dto.getManpowerInput());
        }
        lastList.add(map);
        return lastList;
    }

    /**
     * 职位序列人力投入
     *
     * @param customerId
     * @param organId
     * @param projectId
     * @param time
     */
    @Override
    public List<Object> getJobSeqInput(String customerId, String organId, String projectId, String time) {
        List<HumanInventoryDto> list = humanInventoryDao.getJobSeqInput(customerId, projectId, getYear(time));
        List<Object> lastList = CollectionKit.newList();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (HumanInventoryDto dto : list) {
            map.put(dto.getOrganName(), dto.getManpowerInput());
        }
        lastList.add(map);
        return lastList;
    }

    /**
     * 职级人力投入
     *
     * @param customerId
     * @param organId
     * @param projectId
     * @param time
     */
    @Override
    public List<Object> getRankInput(String customerId, String organId, String projectId, String time) {
        List<HumanInventoryDto> list = humanInventoryDao.getRankInput(customerId, projectId, getYear(time));
        List<Object> lastList = CollectionKit.newList();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (HumanInventoryDto dto : list) {
            map.put(dto.getOrganName(), dto.getManpowerInput());
        }
        lastList.add(map);
        return lastList;
    }

    /**
     * 工作地人力投入
     *
     * @param customerId
     * @param organId
     * @param projectId
     * @param time
     */
    @Override
    public List<Object> getWorkplaceInput(String customerId, String organId, String projectId, String time) {
        List<HumanInventoryDto> list = humanInventoryDao.getWorkplaceInput(customerId, projectId, getYear(time));
        List<Object> lastList = CollectionKit.newList();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (HumanInventoryDto dto : list) {
            map.put(dto.getOrganName(), dto.getManpowerInput());
        }
        lastList.add(map);
        return lastList;
    }

    /**
     * 数据导入功能模板下载
     */
    @Override
    public void downLoadProjectInfoAndCost(String customerId, HttpServletResponse response, String title,
                                           String dateTitle, String date, String[] headers) {
        List<HumanInventoryDto> list = humanInventoryDao.queryProjectCostTypeNames(customerId);
        String[] newHeaders = new String[headers.length + list.size()];
        System.arraycopy(headers, 0, newHeaders, 0, headers.length);
        for (int i = 0; i < list.size(); i++) {
            newHeaders[headers.length + i] = list.get(i).getProjectInputTypeName() + "(万元)";
        }
        List<HumanInventoryDto> progressNamelist = humanInventoryDao.queryProjectProgressBySourceCodeItem(customerId);
        String progress = "项目进度内容为以下其一：\r\n";
        for (HumanInventoryDto p : progressNamelist) {
            progress += p.getCodeItemName() + "\r\n";
        }
        exportExcel(response, title, dateTitle, date, newHeaders, "1", progress);
    }

    /**
     * 人员数据导入功能模板下载
     */
    @Override
    public void downLoadProjectPersonExcel(String customerId, HttpServletResponse response, String title,
                                           String dateTitle, String date, String[] headers) {
        exportExcel(response, title, dateTitle, date, headers, "2", "");
    }

    /**
     * 导出excel方法
     */
    public void exportExcel(HttpServletResponse response, String title, String dateTitle, String date, String[] headers,
                            String type, String progress) {
        try {
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(("项目人力盘点《" + title + "》导入模板.xls").getBytes("GBK"), "iso-8859-1"));
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title + "导入表格");
            HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
            cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyleTitle.setWrapText(true);
            if (!"".equals(progress)) {
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                HSSFComment comment = patriarch
                        .createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3, 10));
                comment.setString(new HSSFRichTextString(progress));
                comment.setColumn(5);
                comment.setRow(1);
            }
            HSSFRow row0 = sheet.createRow(0);
            HSSFCell cell0 = row0.createCell(0);
            cell0.setCellValue(dateTitle);
            HSSFCell cell1 = row0.createCell(1);
            cell1.setCellValue(date);
            HSSFRow row = sheet.createRow(1);
            if ("1".equals(type)) {
                for (int i = 0; i < headers.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(cellStyleTitle);
                    if (i == 0) {
                        sheet.setColumnWidth(i, 50 * 256);
                    } else if (i == 2 || i == 3) {
                        sheet.setColumnWidth(i, 35 * 256);
                    } else {
                        sheet.setColumnWidth(i, 15 * 256);
                    }
                    HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                    cell.setCellValue(text);
                }
            } else if ("2".equals(type)) {
                for (int i = 0; i < headers.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(cellStyleTitle);
                    if (i == 3) {
                        sheet.setColumnWidth(i, 30 * 256);
                    } else {
                        sheet.setColumnWidth(i, 15 * 256);
                    }
                    HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                    cell.setCellValue(text);
                }
            }
            workbook.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
        }
    }


    /**
     * 获取盘点时间类型
     *
     * @param total 页面盘点时间类型
     */
    public int totalType(String total) {
        int totalType = 2;
        if ("quarter".equals(total))
            totalType = 1;
        else if ("halfYear".equals(total))
            totalType = 4;
        else if ("year".equals(total))
            totalType = 5;
        else
            totalType = 2;
        return totalType;
    }

    /**
     * 获取盘点时间格式
     */
    public int date(String detail) {
        int date = 0;
        if (detail.toLowerCase().indexOf("q") != -1) {
            if ("Q1".equals(detail.substring(4, 6))) {
                date = Integer.parseInt((detail.substring(0, 4) + "0101"));
            } else if ("Q2".equals(detail.substring(4, 6))) {
                date = Integer.parseInt((detail.substring(0, 4) + "0401"));
            } else if ("Q3".equals(detail.substring(4, 6))) {
                date = Integer.parseInt((detail.substring(0, 4) + "0701"));
            } else {
                date = Integer.parseInt((detail.substring(0, 4) + "1001"));
            }
        } else if (detail.matches("^[0-9]*$")) {
            date = Integer.parseInt(detail + "01");
        }
        return date;
    }

    /**
     * 比较导入模板与选择导入类型是否一致
     */
    @Override
    public Map<String, Object> compareTemplateIsSame(String customerId, String organId, String tempType, String total,
                                                     String detail, String type, MultipartFile file) {
        Map<String, Object> map = CollectionKit.newMap();
        Map<String, Object> conMap = CollectionKit.newMap();
        List<HumanInventoryImportErrorDto> listErrors = CollectionKit.newList();
        List<HumanInventoryImportInfoDto> listInfo = CollectionKit.newList();
        List<HumanInventoryDto> list;
        int totalType = totalType(total);
        int date = date(detail);
        String fileName = file.getOriginalFilename();
        String lastFileName = fileName.substring(fileName.lastIndexOf("."));
        isRepeat = false;
        try {
            String[] title;
            if (".xls".equals(lastFileName)) {
                title = readXlsTitle(file.getInputStream());
            } else {
                title = readXlsxTitle(file.getInputStream());
            }
            String templateIsSame = compareTemplate(tempType, title); // 比较模版是否正确
            if (templateIsSame != null && !"".equals(templateIsSame)) {
                map.put("templateError", templateIsSame);
                return map;
            }
            String templateType = getFileType(title);
            // 二次导入
            if ("2".equals(type)) {
                if ("optionPerson".equals(templateType)) {
                    humanInventoryDao.deleteProjectManpower(customerId, organId, String.valueOf(date));
                    if (".xls".equals(lastFileName)) {
                        conMap = readPersonXlsContent(file.getInputStream());
                    } else {
                        conMap = readPersonXlsxContent(file.getInputStream());
                    }
                    List<HumanInventoryImportErrorDto> err = (List<HumanInventoryImportErrorDto>) conMap.get("listError");
                    if (err != null && err.size() > 0) {
                        listErrors.addAll(err);
                    }
                    list = (List<HumanInventoryDto>) conMap.get("list");
                    addProjectManpowerInfo(list, customerId, date, totalType);
                    map.put(SUCCESS, SUCCESS);
                    map.put("totalNum", list.size());
                    return map;
                }
                if ("optionCost".equals(templateType)) {
                    Map<String, String[]> columnMap;
                    if (".xls".equals(lastFileName)) {
                        columnMap = readExcelContentWithXls(file.getInputStream());
                    } else {
                        columnMap = readExcelContentWithXlsx(file.getInputStream());
                    }
                    String result = addProjectCostToDatabase(title, columnMap, organId, customerId, total, detail,
                            file);
                    String totalNum = "", flag = "";
                    if (result.indexOf("#") != -1) {
                        String[] arr = result.split("#");
                        totalNum = arr[0];
                        if (SUCCESS.equals(arr[1])) {
                            flag = SUCCESS;
                        } else if (ERROR.equals(arr[1])) {
                            flag = ERROR;
                        }
                    }
                    map.put(SUCCESS, flag);
                    map.put("totalNum", totalNum);
                    return map;
                }
            }

            // 初次导入
            if ("1".equals(type)) {
                // 人力统计
                if ("optionPerson".equals(templateType)) {
                    String titleDate = "";
                    if (".xls".equals(lastFileName)) {
                        titleDate = readXlsTitleWithDate(file.getInputStream());
                    } else {
                        titleDate = readXlsxTitleWithDate(file.getInputStream());
                    }

                    List<HumanInventoryImportErrorDto> listTimeError = compareDate(total, detail, titleDate); // 比较盘点时间
                    if (listTimeError != null && listTimeError.size() > 0) {
                        listErrors.addAll(listTimeError);
                    }
                    if (".xls".equals(lastFileName)) {
                        conMap = readPersonXlsContent(file.getInputStream());
                    } else {
                        conMap = readPersonXlsxContent(file.getInputStream());
                    }
                    List<HumanInventoryImportErrorDto> err = (List<HumanInventoryImportErrorDto>) conMap.get("listError");
                    if (err != null && err.size() > 0) {
                        listErrors.addAll(err);
                    }
                    list = (List<HumanInventoryDto>) conMap.get("list");
                    if (list != null && list.size() > 0) {
                        if ("".equals(list.get(0).getProjectName()) && "".equals(list.get(0).getEmpName()) && list.get(0).getManpowerInput() == 0
                                && "".equals(list.get(0).getSubProjectName()) && "".equals(list.get(0).getWorkContent())) {
                            map.put("contentIsNull", HumanInventoryEnum.FILECONTENTISNULL.getValue());
                        } else {
                            List<HumanInventoryImportErrorDto> listDataError = compareDataUnique(list, customerId, organId);
                            if (listDataError != null && listDataError.size() > 0) {
                                listErrors.addAll(listDataError);
                            }

                            Map<String, Object> mapManpower = CollectionKit.newMap();
                            mapManpower.put("organId", organId);
                            mapManpower.put("customerId", customerId);
                            mapManpower.put("yearMonth", date);
                            int countManpower = humanInventoryDao.queryProjectManpowerCount(mapManpower);
                            if (countManpower > 0) {
                                listInfo = compareDataInfo(list, customerId, organId, String.valueOf(date));
                            }

                            if (listErrors != null && listErrors.size() > 0) {
                                map.put("listErrors", listErrors);
                            } else if ((listErrors == null || listErrors.size() <= 0)
                                    && (listInfo != null && listInfo.size() > 0)) {
                                map.put("listInfo", listInfo);
                                String infoDate = getDateAndType(total, detail).split("#")[1].replaceAll("-", "").substring(0,
                                        6);
                                map.put("infoDate", infoDate);
                            } else {
                                // 数据插入
                                if ("optionPerson".equals(tempType) && list != null && list.size() > 0) {
                                    try {
                                        if (isRepeat) {
                                            // humanInventoryDao.deleteProjectManpower(customerId,
                                            // organId, String.valueOf(date));
                                            map.put(HumanInventoryEnum.FILEISREPEAT.getValue(),
                                                    HumanInventoryEnum.CONTENTISREPEAT.getValue());
                                            return map;
                                        }

                                        addProjectManpowerInfo(list, customerId, date, totalType);
                                        map.put(SUCCESS, SUCCESS);
                                        map.put("totalNum", list.size());
                                    } catch (Exception e) {

                                    }
                                }
                            }
                        }
                    } else {
                        map.put("contentIsNull", HumanInventoryEnum.FILECONTENTISNULL.getValue());
                    }
                }

                // 费用统计
                if ("optionCost".equals(templateType)) {
                    String titleDate = "";
                    Map<Integer, String> contentMap;
                    Map<String, String[]> columnMap = null;
                    if (".xls".equals(lastFileName)) {
                        titleDate = readXlsTitleWithDate(file.getInputStream());
                        contentMap = readXlsExcelContentByLine(file.getInputStream());
                        if (contentMap != null && contentMap.size() > 0) {
                            columnMap = readExcelContentWithXls(file.getInputStream());
                        }
                    } else {
                        titleDate = readXlsxTitleWithDate(file.getInputStream());
                        contentMap = readXlsxExcelContentByLine(file.getInputStream());
                        if (contentMap != null && contentMap.size() > 0) {
                            columnMap = readExcelContentWithXlsx(file.getInputStream());
                        }
                    }
                    if (columnMap != null) {
                        listErrors = findProjectCostErrorValidateList(title, columnMap, titleDate, customerId, total,
                                detail);
                        if (listErrors != null && listErrors.size() > 0) {
                            map.put("listErrors", listErrors);
                        } else {
                            listInfo = findProjectCostOperateValidateList(title, columnMap, customerId, total, detail);
                            if (listInfo != null && listInfo.size() > 0) {
                                map.put("listInfo", listInfo);
                                String infoDate = getDateAndType(total, detail).split("#")[1].replaceAll("-", "")
                                        .substring(0, 6);
                                map.put("infoDate", infoDate);
                            } else {
                                // 添加
                                String result = addProjectCostToDatabase(title, columnMap, organId, customerId, total,
                                        detail, file);
                                String totalNum = "", flag = "";
                                if (result.indexOf("#") != -1) {
                                    String[] arr = result.split("#");
                                    totalNum = arr[0];
                                    if (SUCCESS.equals(arr[1])) {
                                        flag = SUCCESS;
                                    } else if (ERROR.equals(arr[1])) {
                                        flag = ERROR;
                                    }
                                }
                                map.put(SUCCESS, flag);
                                map.put("totalNum", totalNum);
                            }
                        }
                    } else {
                        map.put("contentIsNull", HumanInventoryEnum.FILECONTENTISNULL.getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 费用数据错误验证
     *
     * @param title      标题数组
     * @param columnMap  每列标题和内容组成的map集合
     * @param titleDate  导入文件盘点时间
     * @param customerId 客户id
     * @param total      页面盘点时间类型
     * @param detail     页面盘点时间
     */
    @SuppressWarnings("unchecked")
    public List<HumanInventoryImportErrorDto> findProjectCostErrorValidateList(String[] title,
                                                                               Map<String, String[]> columnMap, String titleDate, String customerId, String total, String detail) {
        List<HumanInventoryImportErrorDto> listErrors = null;
        try {
            // 盘点时间是否一致
            List<String> dateList = compareImportAndTypeDate(total, detail, titleDate, 1, INVENTORYTIMEERROR,
                    INVENTORYTIMETYPEERROR);
            // 项目名称是否唯一
            List<String> projectNamesIsNull = findColumnIsNullOrMoreLine(columnMap.get(getFullNameByString(title, PROJECTNAME)),
                    FILECONTENTSTARTNUM, PRONAMEISNULLERROR, PRONAMEISMOREERROR);
            List<String> projectNamesIsSame = findColumnIsSame(columnMap.get(getFullNameByString(title, PROJECTNAME)),
                    FILECONTENTSTARTNUM, PRONAMEISSAMEERROR);
            // 项目负责人账号是否存在
            List<String> projectPrincipalIsNull = findColumnIsNullOrMoreLine(
                    columnMap.get(getFullNameByString(title, PROJECTPRINCIPAL)), FILECONTENTSTARTNUM,
                    PRINCIPALISNULLERROR, PRINCIPALISMOREERROR);
            List<String> userList = humanInventoryDao.queryProjectUserByImport(customerId,
                    Arrays.asList(columnMap.get(getFullNameByString(title, PROJECTPRINCIPAL))));
            List<String> projectPrincipalIsNo = filterFromArrayByListWithoutData(userList,
                    columnMap.get(getFullNameByString(title, PROJECTPRINCIPAL)), FILECONTENTSTARTNUM,
                    PRINCIPALISNOERROR);
            // 主导组织是否存在
            List<String> projectLeadOrganIsNull = findColumnIsNullOrMoreLine(
                    columnMap.get(getFullNameByString(title, PROJECTLEADORGAN)), FILECONTENTSTARTNUM,
                    LEADORGANISNULLERROR, LEADORGANISMOREERROR);
            List<HumanInventoryDto> leadOrganIdList = humanInventoryDao.queryProjectOrganIdByImport(customerId,
                    changeArrayToListWithOnly(columnMap.get(getFullNameByString(title, PROJECTLEADORGAN))));
            List<String> projectLeadOrganIsNo = filterProjectWithoutLeaderOrgans(leadOrganIdList,
                    columnMap.get(getFullNameByString(title, PROJECTLEADORGAN)), FILECONTENTSTARTNUM,
                    LEADORGANISNOERROR);
            // 参与组织是否存在
            List<String> partakeOrganIsNull = CollectionKit.newList();
//            List<String> partakeOrganIsNull = findColumnIsNull(
//            		columnMap.get(getFullNameByString(title, PROJECTPARTAKEORGAN)), FILECONTENTSTARTNUM,
//            		PARTAKEORGANISNULLERROR);
            List<HumanInventoryDto> partakeOrganIdList = humanInventoryDao.queryProjectOrganIdByImport(customerId,
                    changeArrayToListWithOnly(columnMap.get(getFullNameByString(title, PROJECTPARTAKEORGAN))));
            List<String> partakeOrganIsNo = filterProjectWithoutOrgans(partakeOrganIdList,
                    columnMap.get(getFullNameByString(title, PROJECTPARTAKEORGAN)), FILECONTENTSTARTNUM,
                    PARTAKEORGANISNOERROR);
            // 项目类型
            List<String> projectTypeIsNull = findColumnIsNullOrMoreLine(columnMap.get(getFullNameByString(title, PROJECTTYPE)),
                    FILECONTENTSTARTNUM, TYPEISNULLERROR, TYPEISMOREERROR);
            // 项目进度
            List<String> projectProgressIsNull = findColumnIsNullOrMoreLine(columnMap.get(getFullNameByString(title, PROJECTPROGRESS)),
                    FILECONTENTSTARTNUM, PROGRESSERROR, PROGRESSISMOREERROR);
            List<HumanInventoryDto> progressList = humanInventoryDao.queryProjectProgressBySourceCodeItem(customerId);
            List<String> progressNamelist = CollectionKit.newLinkedList();
            for (HumanInventoryDto p : progressList) {
                progressNamelist.add(p.getCodeItemName());
            }
            List<String> progress = filterFromArrayByListWithoutData(progressNamelist,
                    columnMap.get(getFullNameByString(title, PROJECTPROGRESS)), FILECONTENTSTARTNUM, PROGRESSERROR);
            // 项目开始时间/项目结束时间是否日期格式
            List<String> startDate = compareDateTypeIsNullAndTrue(columnMap.get(getFullNameByString(title, PROJECTSTARTDATE)),
                    FILECONTENTSTARTNUM, DATETYPEERROR, STARTDATETYPEERROR);
            List<String> endDate = compareDateTypeIsTrue(columnMap.get(getFullNameByString(title, PROJECTENDDATE)),
                    FILECONTENTSTARTNUM, ENDDATETYPEERROR);
            // 费用是否是数字形式
            List<String> costList = CollectionKit.newList();
            List<String> newList = CollectionKit.newList();
            for (int i = 0; i < title.length; i++) {
                if (title[i].indexOf("万元") != -1) {
                    costList.addAll(findColumnIsNullForCost(columnMap.get(title[i]), FILECONTENTSTARTNUM, NUMISNULLERROR, title[i]));
                    newList.addAll(findCellIsNotNumber(columnMap.get(title[i]), FILECONTENTSTARTNUM, NUMTYPEERROR, title[i]));
                }
            }
            List<String> conList = removeSameDataWithList(
                    concatListAll(dateList, projectNamesIsNull, projectNamesIsSame, projectPrincipalIsNull,
                            projectPrincipalIsNo, projectLeadOrganIsNull, projectLeadOrganIsNo, partakeOrganIsNull,
                            partakeOrganIsNo, projectTypeIsNull, projectProgressIsNull, progress, startDate, endDate,
                            costList, newList));
            String[] errorsArray = makeFinalSortArray(conList.toArray(new String[conList.size()]));
            listErrors = changeErrorsFlagInfoToList(errorsArray);
        } catch (Exception e) {
            return null;
        }
        return listErrors;
    }

    /**
     * 费用删除/更新操作
     *
     * @param title      导入文件标题数组
     * @param columnMap  每列标题和内容组成的map集合
     * @param customerId 客户id
     * @param total      页面盘点时间类型
     * @param detail     页面盘点时间
     */
    public List<HumanInventoryImportInfoDto> findProjectCostOperateValidateList(String[] title,
                                                                                Map<String, String[]> columnMap, String customerId, String total, String detail) {
        List<HumanInventoryImportInfoDto> listInfo = null;
        try {
            List<String> operateList = queryImportFileOperateMessage(customerId,
                    columnMap.get(getFullNameByString(title, PROJECTNAME)), total, detail, UPDATEOPERATE, DELOPERATE);
            listInfo = changeOperateFlagInfoToList(operateList);
        } catch (Exception e) {
            return null;
        }
        return listInfo;
    }

    /**
     * 费用添加操作
     *
     * @param title      导入文件标题数组
     * @param columnMap  每列标题和内容组成的map集合
     * @param organId    组织机构id
     * @param customerId 客户id
     * @param total      页面盘点时间类型
     * @param detail     页面盘点时间
     * @param file       导入文件
     */
    @SuppressWarnings({"unused"})
    public String addProjectCostToDatabase(String[] title, Map<String, String[]> columnMap, String organId,
                                           String customerId, String total, String detail, MultipartFile file) {

        String[] projectInputTypeName = new String[title.length];
        String typeName, proName, principalName, leadOragnName, partakeOrganName, progressName;
        String msg = null;
        try {
            String fileName = file.getOriginalFilename();
            String sufixName = fileName.substring(fileName.lastIndexOf("."));
            Map<Integer, String[]> rowMap;
            if (".xls".equals(sufixName)) {
                rowMap = readProjectCostExcelContentWithXls(file.getInputStream());
            } else {
                rowMap = readProjectCostExcelContentWithXlsx(file.getInputStream());
            }
            if (rowMap.size() > 0) {

                for (int i = 0; i < title.length; i++) {
                    if (title[i].indexOf("万元") != -1 && title[i].indexOf(PROJECTOUTPUT) == -1
                            && title[i].indexOf(PROJECTINPUT) == -1) {
                        projectInputTypeName[i] = title[i];
                    }
                }
                // 获取数组
                String[] projectNameArray = columnMap.get(getFullNameByString(title, PROJECTNAME));
                String[] principalArray = columnMap.get(getFullNameByString(title, PROJECTPRINCIPAL));
                String[] leadArray = columnMap.get(getFullNameByString(title, PROJECTLEADORGAN));
                String[] partakeArray = columnMap.get(getFullNameByString(title, PROJECTPARTAKEORGAN));
                String[] progressArray = columnMap.get(getFullNameByString(title, PROJECTPROGRESS));
                String[] projectInputArray = columnMap.get(getFullNameByString(title, PROJECTINPUT));
                String[] projectOutputArray = columnMap.get(getFullNameByString(title, PROJECTOUTPUT));
                String[] projectStartDateArray = columnMap.get(getFullNameByString(title, PROJECTSTARTDATE));
                String[] projectEndDateArray = columnMap.get(getFullNameByString(title, PROJECTENDDATE));
                projectStartDateArray = changeDateType(projectStartDateArray);
                projectEndDateArray = changeDateType(projectEndDateArray);
                // 项目进度
                List<String> principalList = queryProjectPrincipalIdByName(customerId, principalArray);
                List<String> leadList = queryProjectLeadOrganIdByName(customerId, leadArray);
                List<String> partakeList = queryProjectPartakeOrganIdByName(customerId, partakeArray);
                List<String> progressList = queryProjectProgressBySourceCodeItem(customerId, progressArray);

                for (int i = 0; i < rowMap.size(); i++) {
                    String[] rowArray = rowMap.get(i + 2);
                    typeName = getContentByTitle(title, rowArray, PROJECTTYPE);
                    proName = getContentByTitle(title, rowArray, PROJECTNAME);
                    principalName = getContentByTitle(title, rowArray, PROJECTPRINCIPAL);
                    leadOragnName = getContentByTitle(title, rowArray, PROJECTLEADORGAN);
                    partakeOrganName = getContentByTitle(title, rowArray, PROJECTPARTAKEORGAN);
                    progressName = getContentByTitle(title, rowArray, PROJECTPROGRESS);

                    List<HumanInventoryDto> projectTypeList = humanInventoryDao
                            .queryProjectTypeByCustomerIdAndTypeName(customerId, typeName);
                    String typeId = addOrUpdateProjectTypeTable(customerId, projectTypeList, typeName);
                    if (typeId != null) {
                        String resultProjectId = addProjectMessage(customerId, projectNameArray[i], principalList.get(i),
                                leadList.get(i), typeId, progressList.get(i), projectStartDateArray[i],
                                projectEndDateArray[i]);
                        if (resultProjectId != null) {
                            boolean resultDel = deleteProjectDataByProjectId(customerId, projectNameArray, total,
                                    detail);
                            if (resultDel) {
                                int type = Integer.parseInt(getDateAndType(total, detail).split("#")[0]);
                                String date = getDateAndType(total, detail).split("#")[1];
                                boolean resultAddRelation = addLeadAndPartakeProjectRelation(customerId,
                                        resultProjectId, partakeList.get(i));
                                if (resultAddRelation) {
                                    boolean resultAddInputDetail = addProjectInputDetail(customerId, resultProjectId,
                                            projectInputTypeName, rowArray, date, type);
                                    if (resultAddInputDetail) {
                                        boolean resultAddCost = addProjectCost(customerId, resultProjectId,
                                                projectInputArray[i], projectOutputArray[i], date, type);
                                        if (resultAddCost) {
                                            msg = (i + 1) + "#" + SUCCESS;
                                        } else {
                                            return (i + 1) + "#" + ERROR;
                                        }
                                    } else {
                                        return (i + 1) + "#" + ERROR;
                                    }
                                } else {
                                    return (i + 1) + "#" + ERROR;
                                }
                            } else {
                                return (i + 1) + "#" + ERROR;
                            }
                        } else {
                            return (i + 1) + "#" + ERROR;
                        }
                    } else {
                        return (i + 1) + "#" + ERROR;
                    }
                }
            } else {
                return "0#" + SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return msg;
    }

    /**
     * 项目表添加操作
     *
     * @param customerId  用户id
     * @param projectName 项目名称
     * @param principalId 项目负责人id
     * @param leadId      主导组织id
     * @param typeId      项目类型id
     * @param progressId  项目进度id
     * @param startDate   项目开始时间
     * @param endDate     项目结束时间
     */
    public String addProjectMessage(String customerId, String projectName, String principalId, String leadId,
                                    String typeId, String progressId, String startDate, String endDate) {
        HumanInventoryDto dto;
        HumanInventoryDto dto2;
        List<HumanInventoryDto> list;
        String resultProjectId = null;
        if (projectName.indexOf("-") != -1) {
            String[] nameArray = projectName.split("-");
            String parentName = nameArray[nameArray.length - 2];
            String childrenName = nameArray[nameArray.length - 1];
            list = humanInventoryDao.queryProjectMessageByName(customerId, childrenName);
            if (list != null && list.size() > 0) {
                String projectId = list.get(0).getProjectId();
                dto = new HumanInventoryDto();
                dto.setProjectId(projectId);
                dto.setProjectName(childrenName);
                dto.setEmpId(principalId);
                dto.setOrganId(leadId);
                dto.setProjectTypeId(typeId);
                dto.setProjectProgressId(progressId);
                dto.setStartDate(startDate);
                dto.setEndDate(endDate);
                dto.setRefresh(DateUtil.getDBNow().substring(0, 10));
                try {
                    humanInventoryDao.updateProjectById(customerId, dto);
                    resultProjectId = projectId;
                } catch (Exception e) {
                    return null;
                }
            } else {
                list = humanInventoryDao.queryProjectMessageByName(customerId, parentName);
                if (list != null && list.size() > 0) {
                    String parentId = list.get(0).getProjectId();
                    dto = new HumanInventoryDto();
                    String projectId = Identities.uuid2();
                    dto.setProjectId(projectId);
                    dto.setCustomerId(customerId);
                    dto.setProjectKey(projectId);
                    dto.setProjectName(childrenName);
                    dto.setEmpId(principalId);
                    dto.setOrganId(leadId);
                    dto.setProjectTypeId(typeId);
                    dto.setProjectProgressId(progressId);
                    dto.setProjectParentId(parentId);
                    dto.setFullPath(list.get(0).getFullPath() + "_" + projectId);
                    dto.setHasChildren(0);// 默认没有子节点
                    dto.setStartDate(startDate);
                    dto.setEndDate(endDate);
                    dto.setRefresh(DateUtil.getDBNow().substring(0, 10));
                    dto2 = new HumanInventoryDto();
                    dto2.setProjectId(parentId);
                    dto2.setHasChildren(1);
                    try {
                        humanInventoryDao.addProject(customerId, dto);
                        humanInventoryDao.updateProjectById(customerId, dto2);
                        resultProjectId = projectId;
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
        } else {
            list = humanInventoryDao.queryProjectMessageByName(customerId, projectName);
            if (list != null && list.size() > 0) {
                String projectId = list.get(0).getProjectId();
                dto = new HumanInventoryDto();
                dto.setProjectId(projectId);
                dto.setProjectName(projectName);
                dto.setEmpId(principalId);
                dto.setOrganId(leadId);
                dto.setProjectTypeId(typeId);
                dto.setProjectProgressId(progressId);
                dto.setStartDate(startDate);
                dto.setEndDate(endDate);
                dto.setRefresh(DateUtil.getDBNow().substring(0, 10));
                try {
                    humanInventoryDao.updateProjectById(customerId, dto);
                    resultProjectId = projectId;
                } catch (Exception e) {
                    return null;
                }
            } else {
                dto = new HumanInventoryDto();
                String projectId = Identities.uuid2();
                dto.setProjectId(projectId);
                dto.setCustomerId(customerId);
                dto.setProjectKey(projectId);
                dto.setProjectName(projectName);
                dto.setEmpId(principalId);
                dto.setOrganId(leadId);
                dto.setProjectTypeId(typeId);
                dto.setProjectProgressId(progressId);
                dto.setProjectParentId("-1");
                dto.setFullPath(projectId);
                dto.setHasChildren(0);// 默认没有子节点
                dto.setStartDate(startDate);
                dto.setEndDate(endDate);
                dto.setRefresh(DateUtil.getDBNow().substring(0, 10));
                try {
                    humanInventoryDao.addProject(customerId, dto);
                    resultProjectId = projectId;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return resultProjectId;
    }

    /**
     * 根据id删除项目表数据
     *
     * @param customerId       客户id
     * @param projectNameArray 项目id数组
     * @param total            页面盘点时间类型
     * @param detail           页面盘点时间
     */
    public boolean deleteProjectDataByProjectId(String customerId, String[] projectNameArray, String total,
                                                String detail) {
        List<String> delProjectIdList = findProjectDelMessage(customerId, projectNameArray, total, detail);
        if (delProjectIdList != null && delProjectIdList.size() > 0) {
            for (String l : delProjectIdList) {
                try {
                    humanInventoryDao.deleteProjectDataByProjectId(customerId, l);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 项目费用明细表删除操作
     *
     * @param customerId 客户id
     * @param projectId  项目id
     * @param date       盘点日期
     * @param type       类型
     */
    public boolean deleteProjectCostByDate(String customerId, String projectId, String date, int type) {
        try {
            humanInventoryDao.deleteProjectCostByDate(customerId, projectId, date, type);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据项目id删除主导项目与参与项目关系表操作
     *
     * @param customerId 客户id
     * @param projectId  项目id
     */
    public boolean deleteProjectPartakeRelationByProjectId(String customerId, String projectId) {
        try {
            humanInventoryDao.deleteProjectPartakeRelationByProjectId(customerId, projectId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 项目费用明细表添加操作
     *
     * @param customerId 客户id
     * @param projectId  项目id
     * @param input      项目投入
     * @param output     项目产出
     * @param date       盘点日期
     * @param type       类型
     */
    public boolean addProjectCost(String customerId, String projectId, String input, String output, String date,
                                  int type) {
        boolean delRs = deleteProjectCostByDate(customerId, projectId, date.substring(0, 7), type);
        if (delRs) {
            HumanInventoryDto dto = new HumanInventoryDto();
            dto.setProjectCostId(Identities.uuid2());
            dto.setCustomerId(customerId);
            if (input != null && !"".equals(input)) {
                dto.setInput(Double.parseDouble(input));
            }
            if (output != null && !"".equals(output)) {
                dto.setOutput(Double.parseDouble(output));
            }
            if (input != null && !"".equals(input) && output != null && !"".equals(output)) {
                dto.setGain(Double.parseDouble(input) - Double.parseDouble(output));
            }
            dto.setProjectId(projectId);
            dto.setDate(date);
            dto.setType(type);
            try {
                humanInventoryDao.addProjectCost(customerId, dto);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 项目投入费用明细表删除操作
     *
     * @param customerId 客户id
     * @param projectId  项目id
     * @param date       盘点日期
     * @param type       类型
     */
    public boolean deleteProjectInputDetailByDate(String customerId, String projectId, String date, int type) {
        try {
            humanInventoryDao.deleteProjectInputDetailByDate(customerId, projectId, date, type);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 项目投入费用明细表添加操作
     *
     * @param customerId           客户id
     * @param projectId            项目id
     * @param projectInputTypeName 投入类型id
     * @param outlay               花费
     * @param date                 盘点日期
     * @param type                 类型
     */
    public boolean addProjectInputDetail(String customerId, String projectId, String[] projectInputTypeName,
                                         String[] outlay, String date, int type) {
        boolean delRs = deleteProjectInputDetailByDate(customerId, projectId, date.substring(0, 7), type);
        if (delRs) {
            HumanInventoryDto dto;
            List<HumanInventoryDto> list = humanInventoryDao.queryProjectCostTypeNames(customerId);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String typeId = list.get(i).getProjectInputTypeId();
                    String typeName = list.get(i).getProjectInputTypeName();
                    if (projectInputTypeName != null && projectInputTypeName.length > 0) {
                        for (int j = 0; j < projectInputTypeName.length; j++) {
                            if (projectInputTypeName[j] != null && !"".equals(projectInputTypeName) && projectInputTypeName[j].indexOf(typeName) != -1) {
                                dto = new HumanInventoryDto();
                                dto.setProjectInputDetailId(Identities.uuid2());
                                dto.setCustomerId(customerId);
                                dto.setProjectId(projectId);
                                dto.setProjectInputTypeId(typeId);
                                if (isDouble(outlay[j])) {
                                    dto.setOutlay(Double.parseDouble(outlay[j]));
                                } else {
                                    dto.setOutlay(Double.parseDouble("0"));
                                }
                                dto.setDate(date);
                                dto.setType(type);
                                try {
                                    humanInventoryDao.addProjectInputDetail(customerId, dto);
                                } catch (Exception e) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 主导项目参与项目关系表添加操作
     *
     * @param customerId  客户id
     * @param projectId   项目id
     * @param partakeList 参与项目id list
     */
    public boolean addLeadAndPartakeProjectRelation(String customerId, String projectId, String partakeId) {
        boolean result = deleteProjectPartakeRelationByProjectId(customerId, projectId);
        if (result) {
            HumanInventoryDto dto = new HumanInventoryDto();
            dto.setCustomerId(customerId);
            dto.setProjectId(projectId);
            dto.setRefresh(DateUtil.getDBNow().substring(0, 10));
            if (partakeId.indexOf("#") != -1) {
                String[] array = partakeId.split("#");
                for (String arr : array) {
                    if (arr != null && !"".equals(arr)) {
                        dto.setProjectPartakeId(Identities.uuid2());
                        dto.setOrganId(arr);
                        try {
                            humanInventoryDao.addLeadAndPartakeProjectRelation(customerId, dto);
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }
            } else if (partakeId.indexOf("#") == -1) {
                dto.setProjectPartakeId(Identities.uuid2());
                dto.setOrganId(partakeId);
                try {
                    humanInventoryDao.addLeadAndPartakeProjectRelation(customerId, dto);
                } catch (Exception e) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 通过比较，根据标题获取内容
     *
     * @param title    标题数组
     * @param rowArray 内容行数组
     * @param name     比较名称
     */
    public String getContentByTitle(String[] title, String[] rowArray, String name) {
        String msg = null;
        for (int j = 0; j < title.length; j++) {
            if (name.equals(title[j])) {
                msg = rowArray[j];
            }
        }
        return msg;
    }

    /**
     * 区分导入模板
     *
     * @param title 标题数组
     */
    public String getFileType(String[] title) {
        String type = "";
        if (title != null && title.length > 0) {
            for (String t : title) {
                if (PARTAKEEMP.equals(t) || INPUTEMP.equals(t) || WORKCONTENT.equals(t)) {
                    type = "optionPerson";
                } else if (PROJECTPRINCIPAL.equals(t) || PROJECTLEADORGAN.equals(t) || PROJECTPARTAKEORGAN.equals(t)
                        || type.equals(t) || PROJECTPROGRESS.equals(t)) {
                    type = "optionCost";
                }
            }
        }
        return type;
    }

    public void addProjectManpowerInfo(List<HumanInventoryDto> list, String customerId, int date, int totalType) {
        List<HumanInventoryDto> listImport = CollectionKit.newList();

        for (int i = 0; i < list.size(); i++) {
            String projectName = list.get(i).getProjectName();
            String projectId = humanInventoryDao.queryProjectId(customerId, projectName);
            String sub = list.get(i).getSubProjectName();
            String subProjectName = sub.substring(sub.lastIndexOf("-") + 1).trim();
            String subProjectId = humanInventoryDao.queryProjectId(customerId, subProjectName);
            String userName = list.get(i).getEmpName().trim();
//            String[] id = userName.split("、");
//            for(int j = 0; j < id.length; j ++) {
            HumanInventoryDto importDto = new HumanInventoryDto();
            importDto.setId(Identities.uuid2());
            importDto.setCustomerId(customerId);
            importDto.setManpowerInput(list.get(i).getManpowerInput());
            importDto.setWorkContent(list.get(i).getWorkContent());
            importDto.setProjectId(projectId);
            importDto.setSubProjectId(subProjectId);
            importDto.setYearMonth(date);
            importDto.setType(totalType);
            String empId = humanInventoryDao.queryEmpId(customerId, userName);
            importDto.setEmpId(empId);
            listImport.add(importDto);
//            }
        }
        humanInventoryDao.addProjectManpowerInfo(listImport);
    }

    /**
     * 根据文档，生成新的时间和类型
     *
     * @param total  页面盘点时间类型
     * @param detail 页面盘点时间
     */
    public String getDateAndType(String total, String detail) {
//        int type = 0;
        Integer type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XMRLPD_inventoryDate);
        if (total != null && !"".equals(total)) {
            switch (total) {
                case "month":
                    type = 2;
                    break;
                case "quarter":
                    type = 1;
                    break;
                case "halfYear":
                    type = 3;
                    break;
                case "year":
                    type = 4;
                    break;
            }
        }
        String newDate;
        if (detail.toLowerCase().indexOf("q") != -1) {
            String year = detail.split("q")[0];
            String month = "";
            String quarter = detail.split("q")[1];
            switch (quarter) {
                case "1":
                    month = "01-01";
                    break;
                case "2":
                    month = "04-01";
                    break;
                case "3":
                    month = "07-01";
                    break;
                case "4":
                    month = "10-01";
                    break;
            }
            newDate = year + "-" + month;
        } else {
            newDate = detail.substring(0, 4) + "-" + detail.substring(4, 6) + "-01";
        }
        return type + "#" + newDate;
    }

    /**
     * 根据项目名称id List，获取一个organid
     *
     * @param nameList 项目名称
     */
    public List<String> getProjectIdWithLast(List<String> nameList) {
        List<String> linkedList = CollectionKit.newLinkedList();
        for (String name : nameList) {
            if (name.indexOf("#") != -1) {
                String[] arr = name.split("#");
                linkedList.add(arr[arr.length - 1]);
            } else {
                linkedList.add(name);
            }
        }
        return linkedList;
    }

    /**
     * 项目类型维度表添加或修改操作
     *
     * @param customerId      客户id
     * @param projectTypeList 项目类型id list
     * @param typeName        类型名称
     */
    public String addOrUpdateProjectTypeTable(String customerId, List<HumanInventoryDto> projectTypeList,
                                              String typeName) {
        String typeId = null;
        HumanInventoryDto dto;
        if (projectTypeList != null && projectTypeList.size() > 0) {
            typeId = projectTypeList.get(0).getProjectTypeId();
        } else {
            try {
                List<HumanInventoryDto> list = humanInventoryDao.queryProjectTypeByDimProjectType(customerId);
                if (list != null && list.size() > 0) {
                    typeId = Identities.uuid2();
                    dto = new HumanInventoryDto();
                    dto.setProjectTypeId(typeId);
                    dto.setCustomerId(customerId);
                    dto.setProjectTypeName(typeName);
                    dto.setShowIndex(list.get(list.size() - 1).getShowIndex() + 1);
                    dto.setRefresh(DateUtil.getDBNow().substring(0, 10));
                    try {
                        humanInventoryDao.addDimProjectType(customerId, dto);
                    } catch (Exception e) {
                        return null;
                    }
                } else {
                    typeId = Identities.uuid2();
                    dto = new HumanInventoryDto();
                    dto.setProjectTypeId(typeId);
                    dto.setCustomerId(customerId);
                    dto.setProjectTypeName(typeName);
                    dto.setShowIndex(1);
                    dto.setRefresh(DateUtil.getDBNow().substring(0, 10));
                    try {
                        humanInventoryDao.addDimProjectType(customerId, dto);
                    } catch (Exception e) {
                        return null;
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return typeId;
    }

    /**
     * 根据项目进度名称获取id
     *
     * @param customerId 客户id
     * @param array      项目进度数组
     */
    public List<String> queryProjectProgressBySourceCodeItem(String customerId, String[] array) {
        List<HumanInventoryDto> list = humanInventoryDao.queryProjectProgressBySourceCodeItem(customerId);
        List<String> linkedList = CollectionKit.newLinkedList();
        String msg = "";
        if (array != null && array.length > 0) {
            for (String arr : array) {
                for (HumanInventoryDto l : list) {
                    if (l.getCodeItemName().equals(arr)) {
                        msg = l.getCodeItemId();
                    }
                }
                linkedList.add(msg);
            }
        }
        return linkedList;
    }

    /**
     * 根据主导组织名称获取id
     *
     * @param customerId 客户id
     * @param array      主导组织数组
     */
    public List<String> queryProjectLeadOrganIdByName(String customerId, String[] array) {
        List<String> linkedList = CollectionKit.newLinkedList();
        String name = "";
        for (String arr : array) {
            if (arr.indexOf("-") != -1) {
                String[] newArr = arr.split("-");
                String name1 = "";
                for (int n = newArr.length - 1; n > 0; n--) {
                    List<HumanInventoryDto> nameList = humanInventoryDao.queryProjectOrganMessageByName(customerId, newArr[n]);
                    if (nameList != null && nameList.size() > 0) {
                        if (nameList.size() == 1) {
                            name1 = nameList.get(0).getOrganId();
                            break;
                        } else {
                            List<HumanInventoryDto> nameList2 = humanInventoryDao.queryProjectOrganMessageByParentName(customerId, newArr[n], newArr[n - 1]);
                            if (nameList2 != null && nameList2.size() == 1) {
                                name1 = nameList2.get(0).getOrganId();
                                break;
                            }
                        }
                    }
                }
                name = name1;
            } else {
                List<HumanInventoryDto> nameList = humanInventoryDao.queryProjectOrganMessageByName(customerId, arr);
                if (nameList != null && nameList.size() == 1) {
                    name = nameList.get(0).getOrganId();
                }
            }
            linkedList.add(name);
        }
        return linkedList;
    }

    /**
     * 根据参与组织名称获取id
     *
     * @param customerId 客户id
     * @param array      参与组织数组
     */
    public List<String> queryProjectPartakeOrganIdByName(String customerId, String[] array) {
        List<String> linkedList = CollectionKit.newLinkedList();
        String nameLast = "";
        for (String arr : array) {
            if (arr.indexOf("\r\n") != -1 || arr.indexOf("\n") != -1) {
                String[] newArr = {};
                if (arr.indexOf("\r\n") != -1) {
                    newArr = arr.split("\r\n");
                } else if (arr.indexOf("\n") != -1) {
                    newArr = arr.split("\n");
                }
                String name = "", name1 = "";
                for (int a = 0; a < newArr.length; a++) {
                    if (newArr[a].indexOf("-") != -1) {
                        String[] nar = newArr[a].split("-");
                        for (int n = nar.length - 1; n > 0; n--) {
                            List<HumanInventoryDto> nameList = humanInventoryDao.queryProjectOrganMessageByName(customerId, nar[n]);
                            if (nameList != null && nameList.size() > 0) {
                                if (nameList.size() == 1) {
                                    name1 = nameList.get(0).getOrganId();
                                    break;
                                } else {
                                    List<HumanInventoryDto> nameList2 = humanInventoryDao.queryProjectOrganMessageByParentName(customerId, nar[n], nar[n - 1]);
                                    if (nameList2 != null && nameList2.size() == 1) {
                                        name1 = nameList2.get(0).getOrganId();
                                        break;
                                    } else {
                                    	name1 = "";
                                    }
                                }
                            }
                        }
                        name += name1 + "#";
                    } else if (newArr[a].indexOf("-") == -1) {
                        List<HumanInventoryDto> nameList = humanInventoryDao.queryProjectOrganMessageByName(customerId,
                                newArr[a]);
                        if (nameList != null && nameList.size() == 1) {
                            name += nameList.get(0).getOrganId() + "#";
                        }
                    }
                }
                nameLast = name;
            } else if (arr.indexOf("-") != -1) {
                String[] newArr = arr.split("-");
                String name1 = "";
                for (int n = newArr.length - 1; n > 0; n--) {
                    List<HumanInventoryDto> nameList = humanInventoryDao.queryProjectOrganMessageByName(customerId,
                            newArr[n]);
                    if (nameList != null && nameList.size() > 0) {
                        if (nameList.size() == 1) {
                            name1 = nameList.get(0).getOrganId();
                            break;
                        } else {
                            List<HumanInventoryDto> nameList2 = humanInventoryDao.queryProjectOrganMessageByParentName(customerId, newArr[n], newArr[n - 1]);
                            if (nameList2 != null && nameList2.size() == 1) {
                                name1 = nameList2.get(0).getOrganId();
                                break;
                            } else {
                            	name1 = "";
                            }
                        }
                    }
                }
                nameLast = name1;
            } else {
                List<HumanInventoryDto> nameList = humanInventoryDao.queryProjectOrganMessageByName(customerId, arr);
                if (nameList != null && nameList.size() == 1) {
                    nameLast = nameList.get(0).getOrganId();
                } else {
                	nameLast = "";
                }
            }
            linkedList.add(nameLast);
        }
        return linkedList;
    }

    /**
     * 根据项目负责人名称获取id
     *
     * @param customerId 客户id
     * @param array      项目负责人数组
     */
    public List<String> queryProjectPrincipalIdByName(String customerId, String[] array) {
        List<HumanInventoryDto> list = humanInventoryDao.queryProjectPrincipalIdByName(customerId);
        List<String> linkedList = CollectionKit.newLinkedList();
        String msg = "";
        for (String arr : array) {
            for (HumanInventoryDto l : list) {
                if (l.getUserKey().equals(arr)) {
                    msg = l.getEmpId();
                }
            }
            linkedList.add(msg);
        }
        return linkedList;
    }

    /**
     * 遍历错误信息数组为页面要求样式
     *
     * @param array 错误信息数组
     */
    public List<HumanInventoryImportErrorDto> changeErrorsFlagInfoToList(String[] array) {
        List<HumanInventoryImportErrorDto> linkedList = CollectionKit.newLinkedList();
        HumanInventoryImportErrorDto hed;
        for (int i = 0; i < array.length; i++) {
            String[] newArr = array[i].split("#");
            hed = new HumanInventoryImportErrorDto();
            hed.setLocationError("第" + newArr[0] + "行");
            if (newArr.length > 2) {
                hed.setErrorMsg(newArr[1] + changeErrorOrEditFlagToChinese(newArr[newArr.length - 1]));
            } else {
                hed.setErrorMsg(changeErrorOrEditFlagToChinese(newArr[1]));
            }
            linkedList.add(hed);
        }
        return linkedList;
    }

    /**
     * 遍历执行动作信息为页面要求样式
     *
     * @param list 错误信息list
     */
    public List<HumanInventoryImportInfoDto> changeOperateFlagInfoToList(List<String> list) {
        List<HumanInventoryImportInfoDto> linkedList = CollectionKit.newLinkedList();
        HumanInventoryImportInfoDto hed;
        for (int i = 0; i < list.size(); i++) {
            String[] arr = list.get(i).split("#");
            hed = new HumanInventoryImportInfoDto();
            hed.setProjectName(arr[0]);
            hed.setAction(changeErrorOrEditFlagToChinese(arr[arr.length - 1]));
            linkedList.add(hed);
        }
        return linkedList;
    }

    /**
     * 去除list中重复的数据
     *
     * @param list
     */
    public List<String> removeSameDataWithList(List<String> list) {
        List<String> tempList = CollectionKit.newLinkedList();
        for (String i : list) {
            if (!tempList.contains(i)) {
                tempList.add(i);
            }
        }
        return tempList;
    }

    /**
     * 删除/更新提示
     *
     * @param customerId 当前客户id
     * @param array      项目名称数组
     * @param total      页面盘点时间类型
     * @param detail     页面盘点时间
     * @param updateFlag 更新提示
     * @param delFlag    删除提示
     * @return
     */
    public List<String> queryImportFileOperateMessage(String customerId, String[] array, String total, String detail,
                                                      String updateFlag, String delFlag) {
        List<String> linkedList = CollectionKit.newLinkedList();
        String[] newDates = getDateAndType(total, detail).split("#");
        String newDate = "";
        if (newDates.length > 1) {
            newDate = newDates[1].substring(0, 7);
        } else if (newDates.length == 1) {
            newDate = newDates[0].substring(0, 7);
        }
        List<HumanInventoryDto> list = humanInventoryDao.queryProjectMessageByManpower(customerId, newDate);
        List<String> newList = changeArrayToListByFlag(array);
        for (int i = 0; i < list.size(); i++) {
            if (newList.contains(list.get(i).getProjectName())) {
                linkedList.add(list.get(i).getProjectName() + "#" + updateFlag);
            } else {
                linkedList.add(list.get(i).getProjectName() + "#" + delFlag);
            }
        }
        return linkedList;
    }

    /**
     * 查找导入文档中不存的项目id
     *
     * @param customerId  客户ID
     * @param projectName 项目名称数组
     * @param total       页面盘点时间类型
     * @param detail      页面盘点时间
     * @return
     */
    public List<String> findProjectDelMessage(String customerId, String[] projectName, String total, String detail) {
        List<String> linkedList = CollectionKit.newLinkedList();
        String[] newDates = getDateAndType(total, detail).split("#");
        String newDate = "";
        if (newDates.length > 1) {
            newDate = newDates[1].substring(0, 7);
        } else if (newDates.length == 1) {
            newDate = newDates[0].substring(0, 7);
        }
        List<HumanInventoryDto> list = humanInventoryDao.queryProjectMessageByManpower(customerId, newDate);
        List<String> newList = changeArrayToListByFlag(projectName);
        for (int i = 0; i < list.size(); i++) {
            if (!newList.contains(list.get(i).getProjectName())) {
                linkedList.add(list.get(i).getProjectId());
            }
        }
        return linkedList;
    }

    /**
     * 根据标识及规则，取数组最后一个，将String[] 转化为list
     *
     * @param array
     */
    public List<String> changeArrayToListByFlag(String[] array) {
        List<String> list = CollectionKit.newList();
        for (String arr : array) {
            if (arr.contains("-")) {
                String[] newArr = arr.split("-");
                list.add(newArr[newArr.length - 1]);
            } else {
                list.add(arr);
            }
        }
        return list;
    }

    /**
     * 比较list和array，筛选出array中不存在于list中数据
     *
     * @param list  比较list
     * @param array 需筛选的数组
     * @param num   开始位置
     * @param flag  标识
     * @return
     */
    public List<String> filterFromArrayByListWithoutData(List<String> list, String[] array, int num, String flag) {
        List<String> lastList;
        List<String> linkedList;
        if (list != null && list.size() > 0) {
            linkedList = CollectionKit.newLinkedList();
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null && !"".equals(array[i]) && !list.contains(array[i]) && array[i].indexOf("\r\n") == -1 && array[i].indexOf("\n") == -1) {
                    linkedList.add((i + num) + "#" + flag);
                }
            }
            lastList = linkedList;
        } else {
            linkedList = CollectionKit.newLinkedList();
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null && !"".equals(array[i]) && array[i].indexOf("\r\n") == -1 && array[i].indexOf("\n") == -1) {
                    linkedList.add((i + num) + "#" + flag);
                }
            }
            lastList = linkedList;
        }
        return lastList;
    }

    /**
     * 比较日期格式是否为空和是否正确
     *
     * @param date  时间日期数组
     * @param num   开始位置
     * @param flag1 空标识
     * @param flag2 格式不正确标识
     */
    public LinkedList<String> compareDateTypeIsNullAndTrue(String[] date, int num, String flag1, String flag2) {
        LinkedList<String> linkedList = CollectionKit.newLinkedList();
        for (int i = 0; i < date.length; i++) {
            if (date[i] != null && !"".equals(date[i])) {
                if (!isValidDate(date[i])) {
                    linkedList.add((i + num) + "#" + flag2);
                }
            } else {
                linkedList.add((i + num) + "#" + flag1);
            }
        }
        return linkedList;
    }

    /**
     * 比较日期格式是否正确
     *
     * @param date 时间日期数组
     * @param num  开始位置
     * @param flag 格式不正确标识
     */
    public LinkedList<String> compareDateTypeIsTrue(String[] date, int num, String flag) {
        LinkedList<String> linkedList = CollectionKit.newLinkedList();
        for (int i = 0; i < date.length; i++) {
            if (date[i] != null && !"".equals(date[i])) {
                if (!isValidDate(date[i])) {
                    linkedList.add((i + num) + "#" + flag);
                }
            }
        }
        return linkedList;
    }

    /**
     * 判断字符串是否日期格式
     *
     * @param str 需要验证的时间日期字符串
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        if (isInt(str) && str.length() > 7) {
            str = str.substring(0, 4) + '-' + str.substring(4, 6) + "-" + str.substring(6);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 转换date类型为yyyy-mm-dd样式
     */
    public String[] changeDateType(String[] date) {
        if (date != null && date.length > 0) {
            for (int i = 0; i < date.length; i++) {
                if (date[i] != null && !"".equals(date[i]) && date[i].indexOf("-") == -1) {
                    date[i] = date[i].substring(0, 4) + "-" + date[i].substring(4, 6) + "-" + date[i].substring(6);
                }
            }
        }
        return date;
    }

    /**
     * 筛选出不符合的组织
     *
     * @param list  比较的list
     * @param array 比较的数组
     * @param num   内容开始位置
     * @param flag  标识
     * @return
     */
    public List<String> filterProjectWithoutOrgans(List<HumanInventoryDto> list, String[] array, int num, String flag) {
        List<String> lastList = CollectionKit.newList();
        List<String> newList;
        List<String> linkedList;
        if (list != null && list.size() > 0) {
            newList = CollectionKit.newLinkedList();
            for (int i = 0; i < list.size(); i++) {
                newList.add(list.get(i).getOrganName());
                newList.add(list.get(i).getOrganFullName());
            }
            linkedList = CollectionKit.newLinkedList();
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null && !"".equals(array[i])) {
                    if (array[i].indexOf("\r\n") != -1 || array[i].indexOf("\n") != -1) {
                        String[] newArr = {};
                        if (array[i].indexOf("\r\n") != -1) {
                            newArr = array[i].split("\r\n");
                        } else if (array[i].indexOf("\n") != -1) {
                            newArr = array[i].split("\n");
                        }
                        for (int a = 0; a < newArr.length; a++) {
                            if (newArr[a].indexOf("-") != -1) {
                                String[] nar = newArr[a].split("-");
                                for (int j = 0; j < nar.length; j++) {
                                    if (!newList.contains(nar[j])) {
                                        linkedList.add((i + num) + "#" + flag);
                                    }
                                }
                            } else if (newArr[a].indexOf("-") == -1) {
                                if (!newList.contains(newArr[a])) {
                                    linkedList.add((i + num) + "#" + flag);
                                }
                            }
                        }
                    } else if (array[i].indexOf("-") != -1) {
                        String[] arr = array[i].split("-");
                        for (int j = 0; j < arr.length; j++) {
                            if (!newList.contains(arr[j])) {
                                linkedList.add((i + num) + "#" + flag);
                            }
                        }
                    } else {
                        if (!newList.contains(array[i])) {
                            linkedList.add((i + num) + "#" + flag);
                        }
                    }
                }
            }
            HashSet<String> hs = new HashSet<String>(linkedList);
            Iterator<String> it = hs.iterator();
            while (it.hasNext()) {
                lastList.add(it.next());
            }
        } else {
            linkedList = CollectionKit.newLinkedList();
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null && !"".equals(array[i])) {
                    linkedList.add((i + num) + "#" + flag);
                }
            }
            lastList = linkedList;
        }
        return lastList;
    }

    /**
     * 筛选出不符合的主导组织
     *
     * @param list  比较的list
     * @param array 比较的数组
     * @param num   内容开始位置
     * @param flag  标识
     * @return
     */
    public List<String> filterProjectWithoutLeaderOrgans(List<HumanInventoryDto> list, String[] array, int num, String flag) {
        List<String> lastList = CollectionKit.newList();
        List<String> newList;
        List<String> linkedList;
        if (list != null && list.size() > 0) {
            newList = CollectionKit.newLinkedList();
            for (int i = 0; i < list.size(); i++) {
                newList.add(list.get(i).getOrganName());
                newList.add(list.get(i).getOrganFullName());
            }
            linkedList = CollectionKit.newLinkedList();
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null && !"".equals(array[i]) && array[i].indexOf("\r\n") == -1 && array[i].indexOf("\n") == -1) {
                    if (array[i].indexOf("-") != -1) {
                        String[] arr = array[i].split("-");
                        for (int j = 0; j < arr.length; j++) {
                            if (!newList.contains(arr[j])) {
                                linkedList.add((i + num) + "#" + flag);
                            }
                        }
                    } else {
                        if (!newList.contains(array[i])) {
                            linkedList.add((i + num) + "#" + flag);
                        }
                    }
                }
            }
            HashSet<String> hs = new HashSet<String>(linkedList);
            Iterator<String> it = hs.iterator();
            while (it.hasNext()) {
                lastList.add(it.next());
            }
        } else {
            linkedList = CollectionKit.newLinkedList();
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null && !"".equals(array[i]) && array[i].indexOf("\r\n") == -1 && array[i].indexOf("\n") == -1) {
                    linkedList.add((i + num) + "#" + flag);
                }
            }
            lastList = linkedList;
        }
        return lastList;
    }

    /**
     * 获取数组中相同值位置
     *
     * @param array 需处理数组
     * @param num   开始位置
     * @param flag  标识
     */
    public List<String> findColumnIsSame(String[] array, int num, String flag) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < array.length; i++) {
            if (array[i].indexOf("\r\n") == -1 && array[i].indexOf("\n") == -1) {
                String a = "";
                if (array[i].indexOf("-") != -1) {
                    String[] arr = array[i].split("-");
                    a = arr[arr.length - 1];
                } else {
                    a = array[i];
                }
                for (int j = i + 1; j < array.length; j++) {
                    String b = "";
                    if (array[j].indexOf("-") != -1) {
                        String[] arr = array[j].split("-");
                        b = arr[arr.length - 1];
                    } else {
                        b = array[j];
                    }
                    if (a.equals(b)) {
                        set.add(i);
                        set.add(j);
                        break;
                    }
                }
            }
        }
        List<String> list = CollectionKit.newList();
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            list.add((it.next() + num) + "#" + flag);
        }
        return list;
    }

    /**
     * 根据列查找单元格为空的位置
     *
     * @param array 需查找的数组
     * @param num   开始位置
     * @param flag  标识
     */
    public List<String> findColumnIsNull(String[] array, int num, String flag) {
        List<String> list = CollectionKit.newList();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null || "".equals(array[i])) {
                list.add((i + num) + "#" + flag);
            }
        }
        return list;
    }

    /**
     * 根据列查找单元格为空的位置
     *
     * @param array 需查找的数组
     * @param num   开始位置
     * @param flag  错误标识
     * @param title 列名
     */
    public List<String> findColumnIsNullForCost(String[] array, int num, String flag, String title) {
        List<String> list = CollectionKit.newLinkedList();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null || "".equals(array[i])) {
                list.add((i + num) + "#" + flag + "#" + title);
            }
        }
        return list;
    }

    /**
     * 根据列查找单元格多行的位置
     *
     * @param array 需查找的数组
     * @param num   开始位置
     * @param flag1 单元格内容为空标识
     * @param flag2 单元格内容多行标识
     */
    public List<String> findColumnIsNullOrMoreLine(String[] array, int num, String flag1, String flag2) {
        List<String> list = CollectionKit.newList();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && !"".equals(array[i])) {
                if (array[i].indexOf("\r\n") != -1 || array[i].indexOf("\n") != -1) {
                    list.add((i + num) + "#" + flag2);
                }
            } else {
                list.add((i + num) + "#" + flag1);
            }
        }
        return list;
    }

    /**
     * 获取数组中值为非数字位置
     *
     * @param array 需查找的数组
     * @param num   开始位置
     * @param flag  错误标识
     * @param title 列名
     */
    public List<String> findCellIsNotNumber(String[] array, int num, String flag, String title) {
        List<String> list = CollectionKit.newList();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && !"".equals(array[i].trim()) && isDouble(array[i]) == false) {
                list.add((i + num) + "#" + flag + "#" + title);
            }
        }
        return list;
    }

    /**
     * 是否能转化成double类型
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * 是否能转化成int类型
     */
    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * 根据分割规则，获取无重复值的list
     *
     * @param array 需查找数组
     */
    public List<String> changeArrayToListWithOnly(String[] array) {
        List<String> list = CollectionKit.newList();
        List<String> lastList = CollectionKit.newList();
        if (array != null && array.length > 0) {
            for (String arr : array) {
                if (arr != null && !"".equals(arr)) {
                    if (arr.indexOf("\r\n") != -1) {
                        String[] newArr = arr.split("\r\n");
                        for (String na : newArr) {
                            if (na.indexOf("-") != -1) {
                                String[] ns = na.split("-");
                                for (String n : ns) {
                                    list.add(n);
                                }
                            } else {
                                list.add(na);
                            }
                        }
                    } else if (arr.indexOf("\n") != -1) {
                        String[] newArr = arr.split("\n");
                        for (String na : newArr) {
                            if (na.indexOf("-") != -1) {
                                String[] ns = na.split("-");
                                for (String n : ns) {
                                    list.add(n);
                                }
                            } else {
                                list.add(na);
                            }
                        }

                    } else if (arr.indexOf("-") != -1) {
                        String[] newArr = arr.split("-");
                        for (String n : newArr) {
                            list.add(n);
                        }
                    } else {
                        list.add(arr);
                    }
                }
            }
        }
        HashSet<String> hs = new HashSet<String>(list);
        Iterator<String> it = hs.iterator();
        while (it.hasNext()) {
            lastList.add(it.next());
        }
        return lastList;
    }

    /**
     * 合并list
     *
     * @param first 起始list
     * @param lists N个list
     * @param <T>   不限类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> concatListAll(List<T> first, List<T>... lists) {
        for (List<T> l : lists) {
            first.addAll(l);
        }
        return first;
    }

    /**
     * 定义比较点
     */
    class Point {
        int x;
        int y;
        String z;
    }

    /**
     * 自定义比较规则
     */
    class MyComprator implements Comparator {
        public int compare(Object arg0, Object arg1) {
            Point t1 = (Point) arg0;
            Point t2 = (Point) arg1;
            if (t1.x != t2.x)
                return t1.x >= t2.x ? 1 : -1;
            else
                return t1.y >= t2.y ? 1 : -1;
        }
    }

    /**
     * 生成比较点数组
     */
    public Point[] makePoint(int length) {
        Point[] arr = new Point[length];
        for (int i = 0; i < length; i++) {
            arr[i] = new Point();
        }
        return arr;
    }

    /**
     * 通过自定义比较规则，生成新的数组
     *
     * @param data 待比较数组
     */
    @SuppressWarnings("unchecked")
    public String[] makeFinalSortArray(String[] data) {
        Point[] point = makePoint(data.length);
        for (int i = 0; i < data.length; i++) {
            String[] arr = data[i].split("#");
            point[i].x = Integer.parseInt(arr[0]);
            point[i].y = arr[1].substring(0, 1).toCharArray()[0] + Integer.parseInt(arr[1].substring(1));
            if (arr.length > 2) {
                point[i].z = arr[arr.length - 1];
            } else {
                point[i].z = "";
            }
        }
        Arrays.sort(point, new MyComprator());
        String[] array = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            array[i] = point[i].x + "#" + point[i].z + "#A" + (point[i].y - 65) + "";
        }
        return array;
    }

    /**
     * 判断导入类型与导入数据模板是否一致
     *
     * @param tempType 页面选择模板类型
     * @param title    导入文件标题数组
     */

    public String compareTemplate(String tempType, String[] title) {
        if (title != null && title.length > 0) {
            // 选择导入类型为项目信息及费用数据
            if ("optionCost".equals(tempType)) {
                boolean rsTrue = compareCostTitleIsTrue(title);
                if (rsTrue) {
                    boolean rsExist = compareCostTitleIsExist(title);
                    if (!rsExist) {
                        return COSTTYPEERROR;
                    }
                } else {
                    return COSTTYPEERROR;
                }
            } else
                // 选择导入类型为项目人员数据
                if ("optionPerson".equals(tempType)) {
                    for (String t : title) {
                        if (PROJECTPRINCIPAL.equals(t) || PROJECTLEADORGAN.equals(t) || PROJECTPARTAKEORGAN.equals(t)
                                || PROJECTTYPE.equals(t) || PROJECTPROGRESS.equals(t) || "".equals(t)) {
                            return PERSONTYPEERROR;
                        }
                    }
                }
        } else {
            // 选择导入类型为项目信息及费用数据
            if ("optionCost".equals(tempType)) {
                return COSTTYPEERROR;
            } else
                // 选择导入类型为项目人员数据
                if ("optionPerson".equals(tempType)) {
                    return PERSONTYPEERROR;
                }
        }
        return "";
    }

    /**
     * 费用模板是否存在正确的字段名
     *
     * @param title 标题数组
     */
    public boolean compareCostTitleIsTrue(String[] title) {
        boolean rs = true;
        for (String t : title) {
            if (PARTAKEEMP.equals(t) || INPUTEMP.equals(t) || WORKCONTENT.equals(t)) {
                rs = false;
            }
        }
        return rs;
    }

    /**
     * 费用模板是否存在必须的字段名
     *
     * @param title 标题数组
     */
    public boolean compareCostTitleIsExist(String[] title) {
        boolean rs = false;
        List<String> list = Arrays.asList(title);
        List<String> linkedList = CollectionKit.newLinkedList();
        linkedList.add(PROJECTNAME);
        linkedList.add(PROJECTPRINCIPAL);
        linkedList.add(PROJECTLEADORGAN);
        linkedList.add(PROJECTPARTAKEORGAN);
        linkedList.add(PROJECTTYPE);
        linkedList.add(PROJECTPROGRESS);
        linkedList.add(PROJECTINPUT);
        linkedList.add(PROJECTOUTPUT);
        linkedList.add(PROJECTSTARTDATE);
        linkedList.add(PROJECTENDDATE);
        if (list.containsAll(linkedList)) {
            rs = true;
        }
        return rs;
    }

    /**
     * 比较时间
     *
     * @param detail
     * @param titleDate
     */

    public List<HumanInventoryImportErrorDto> compareDate(String total, String detail, String titleDate) {
        List<HumanInventoryImportErrorDto> list = CollectionKit.newList();
        HumanInventoryImportErrorDto dto = null;
        String date = titleDate.replaceAll(" ", "");
        if (!(date.matches("^[0-9]*$") || date.toLowerCase().indexOf("q") != -1)) {
            dto = new HumanInventoryImportErrorDto();
            dto.setLocationError("第1行");
            dto.setErrorMsg(changeErrorOrEditFlagToChinese("A2"));
            list.add(dto);
        }
        if (!detail.equals(date)) {
            dto = new HumanInventoryImportErrorDto();
            dto.setLocationError("第1行");
            dto.setErrorMsg(changeErrorOrEditFlagToChinese("A1"));
            list.add(dto);
        }
        return list;
    }

    /**
     * 比较时间
     *
     * @param total             页面盘点时间类型
     * @param detail            页面盘点时间
     * @param titleDate         导入文件盘点时间
     * @param num               开始位置
     * @param dateErrorFlag     时间错误标识
     * @param dateTypeErrorFlag 时间类型错误标识
     */

    public List<String> compareImportAndTypeDate(String total, String detail, String titleDate, int num,
                                                 String dateErrorFlag, String dateTypeErrorFlag) {
        List<String> list = CollectionKit.newLinkedList();
        if (isInt(titleDate)) {
            if (!detail.equals(titleDate)) {
                list.add(num + "#" + dateErrorFlag);
            }
        } else {
            if (titleDate.toLowerCase().indexOf("q") != -1) {
                if (titleDate.length() > 6) {
                    list.add(num + "#" + dateErrorFlag);
                    list.add(num + "#" + dateTypeErrorFlag);
                } else if (!(detail.toLowerCase().equals(titleDate.toLowerCase()))) {
                    list.add(num + "#" + dateErrorFlag);
                }
            } else if (titleDate.indexOf("-") != -1) {
                list.add(num + "#" + dateErrorFlag);
                list.add(num + "#" + dateTypeErrorFlag);
            }
        }
        return list;
    }

    /**
     * 比较数据格式
     */
    public List<HumanInventoryImportErrorDto> compareDataUnique(List<HumanInventoryDto> list, String customerId,
                                                                String organId) {
        List<HumanInventoryImportErrorDto> listError = CollectionKit.newList();
        HumanInventoryImportErrorDto errorDto = null;
        for (int i = 0; i < list.size(); i++) {
            HumanInventoryDto dto = list.get(i);

            String projectName = dto.getProjectName().trim();
            int projectCount = humanInventoryDao.queryProjectCount(customerId, organId, projectName);
            if (projectCount == 0) {
                errorDto = new HumanInventoryImportErrorDto();
                errorDto.setLocationError("第" + (i + 3) + "行");
                errorDto.setErrorMsg(changeErrorOrEditFlagToChinese("B4"));
                listError.add(errorDto);
            }
            String userName = dto.getEmpName().trim();
//            String[] user = userName.split("、");
           /* if (!userName.matches("^(?![0-9])[a-zA-Z0-9]+")) {
                errorDto = new HumanInventoryImportErrorDto();
                errorDto.setLocationError("第" + (i + 3) + "行");
                errorDto.setErrorMsg(changeErrorOrEditFlagToChinese("B5"));
                listError.add(errorDto);
            }*/
//            for(int j = 0; j < user.length; j ++) {
            int empCount = humanInventoryDao.queryEmpCount(customerId, organId, userName);
            if (empCount == 0) {
                errorDto = new HumanInventoryImportErrorDto();
                errorDto.setLocationError("第" + (i + 3) + "行");
                errorDto.setErrorMsg(changeErrorOrEditFlagToChinese("B6"));
                listError.add(errorDto);
            }
//            }
            String subProjectName = dto.getSubProjectName().trim();
            /*if (subProjectName != null && !"".equals(subProjectName) && !subProjectName.matches("^(?!-)(?!.*?-$)[a-zA-Z0-9-\u4e00-\u9fa5]+$")) {
                errorDto = new HumanInventoryImportErrorDto();
                errorDto.setLocationError("第" + (i + 3) + "行");
                errorDto.setErrorMsg(changeErrorOrEditFlagToChinese("B7"));
                listError.add(errorDto);
            }*/
            if (projectName.equals(subProjectName)) {
                errorDto = new HumanInventoryImportErrorDto();
                errorDto.setLocationError("第" + (i + 3) + "行");
                errorDto.setErrorMsg(changeErrorOrEditFlagToChinese("B12"));
                listError.add(errorDto);
            }
            String[] sub = subProjectName.split("-");
            for (int j = 0; j < sub.length; j++) {
                int subprojectCount = humanInventoryDao.queryProjectCount(customerId, organId, sub[j].trim());
                if (subProjectName != null && !"".equals(subProjectName) && subprojectCount == 0) {
                    errorDto = new HumanInventoryImportErrorDto();
                    errorDto.setLocationError("第" + (i + 3) + "行");
                    errorDto.setErrorMsg(changeErrorOrEditFlagToChinese("B11"));
                    listError.add(errorDto);
                }
            }
        }
        return listError;
    }

    /**
     * 比较数据更新信息
     */
    public List<HumanInventoryImportInfoDto> compareDataInfo(List<HumanInventoryDto> list, String customerId,
                                                             String organId, String yearMonth) {
        List<HumanInventoryImportInfoDto> listInfo = CollectionKit.newList();
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            HumanInventoryDto dto = list.get(i);
            String projectName = dto.getProjectName();
            Map<String, Object> map = CollectionKit.newMap();
            map.put("customerId", customerId);
            map.put("yearMonth", yearMonth);
            map.put("projectName", projectName);
            map.put("userName", dto.getEmpName());
            map.put("manpowerInput", dto.getManpowerInput());
            map.put("subProjectName", dto.getSubProjectName());
            map.put("workContent", dto.getWorkContent());
            int count = humanInventoryDao.queryManpowerCount(map); // 检查数据是否重复
            if (count <= 0) {
                Map<String, Object> mapUpdate = CollectionKit.newMap();
                mapUpdate.put("customerId", customerId);
                mapUpdate.put("organId", organId);
                mapUpdate.put("yearMonth", yearMonth);
                mapUpdate.put("projectName", projectName);
                mapUpdate.put("userName", dto.getEmpName());
                // mapUpdate.put("subProjectName", dto.getSubProjectName());
                int countUpdate = humanInventoryDao.queryProjectManpowerCount(mapUpdate); // 检查是否更新
                if (countUpdate > 0) {
                    listInfo = addListinfo(projectName, "B1", listInfo);
                } else {
                    listInfo = addListinfo(projectName, "B2", listInfo);
                }
            } else {
                num++;
                listInfo = addListinfo(projectName, "B1", listInfo);
            }
        }
        if (num == list.size()) {
            isRepeat = true;
        }
        List<HumanInventoryDto> listLast = humanInventoryDao.queryProjectManpowerInfo(customerId, organId, yearMonth);
        for (HumanInventoryDto dto : listLast) {
            boolean flag = false;
            String projectName = dto.getProjectName();
            String empName = dto.getEmpName();
            for (HumanInventoryDto newDto : list) {  //检查是否新增数据
                if ((!StringUtils.isEmpty(projectName) && projectName.equals(newDto.getProjectName()))
                        && (!StringUtils.isEmpty(empName) && empName.equals(newDto.getEmpName()))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) listInfo = addListinfo(projectName, "B3", listInfo);
        }
        return listInfo;
    }

    public List<HumanInventoryImportInfoDto> addListinfo(String projectName, String emu, List<HumanInventoryImportInfoDto> listInfo) {
        if (listInfo != null && listInfo.size() > 0) {
            boolean isTrue = false;
            for (int j = 0; j < listInfo.size(); j++) {
                if (projectName.equals(listInfo.get(j).getProjectName())) {
                    isTrue = true;
                    break;
                }
            }
            if (!isTrue) {
                HumanInventoryImportInfoDto infoDto = new HumanInventoryImportInfoDto();
                infoDto.setProjectName(projectName);
                infoDto.setAction(changeErrorOrEditFlagToChinese(emu));
                listInfo.add(infoDto);
            }
        } else {
            HumanInventoryImportInfoDto infoDto = new HumanInventoryImportInfoDto();
            infoDto.setProjectName(projectName);
            infoDto.setAction(changeErrorOrEditFlagToChinese(emu));
            listInfo.add(infoDto);
        }
        return listInfo;
    }

    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private XSSFRow xssfRow;

    /**
     * 读取Excel2003表格表头的盘点时间
     *
     * @return String 表头内容的数组
     */
    public String readXlsTitleWithDate(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        return getCellFormatValue(row.getCell(1));
    }

    /**
     * 读取Excel2007表格表头的盘点时间
     *
     * @return String 表头内容的数组
     */
    public String readXlsxTitleWithDate(InputStream is) {
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        xssfRow = xssfSheet.getRow(0);
        return getCellFormatValue(xssfRow.getCell(1));
    }

    /**
     * 读取Excel2003表格表头的标题内容
     *
     * @return String 表头内容的数组
     */
    public String[] readXlsTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(1);
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getCellFormatValue(row.getCell(i));
        }
        return title;
    }

    /**
     * 读取Excel2007表格表头的标题内容
     *
     * @return String 表头内容的数组
     */
    public String[] readXlsxTitle(InputStream is) {
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        xssfRow = xssfSheet.getRow(1);
        int colNum = xssfRow.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getCellFormatValue(xssfRow.getCell(i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容,后缀为.xlsx
     *
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<String, String[]> readExcelContentWithXlsx(InputStream is) {
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        int rowNum = xssfSheet.getLastRowNum();
        xssfRow = xssfSheet.getRow(1);
        int colNum = xssfRow.getPhysicalNumberOfCells();
        Map<String, String[]> map = new LinkedHashMap<String, String[]>();
        for (int a = 0; a < colNum; a++) {
            String[] columnNum = new String[rowNum - 1];
            for (int b = 2; b <= rowNum; b++) {
                xssfRow = xssfSheet.getRow(b);
                if (xssfRow != null) {
                    columnNum[b - 2] = getCellFormatValue(xssfRow.getCell(a));
                } else {
                    columnNum[b - 2] = "";
                }
            }
            String key = getCellFormatValue(xssfSheet.getRow(1).getCell(a));
            map.put(key, columnNum);
        }
        return map;
    }

    /**
     * 读取Excel数据内容,后缀为.xls
     *
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<String, String[]> readExcelContentWithXls(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(1);
        int colNum = row.getPhysicalNumberOfCells();
        Map<String, String[]> map = new LinkedHashMap<String, String[]>();
        for (int a = 0; a < colNum; a++) {
            String[] columnNum = new String[rowNum - 1];
            for (int b = 2; b <= rowNum; b++) {
                row = sheet.getRow(b);
                if (row != null) {
                    columnNum[b - 2] = getCellFormatValue(row.getCell(a));
                } else {
                    columnNum[b - 2] = "";
                }
            }
            String key = getCellFormatValue(sheet.getRow(1).getCell(a));
            map.put(key, columnNum);
        }
        return map;
    }

    /**
     * 读取Excel数据内容,后缀为.xlsx
     *
     * @return Map
     */
    public Map<Integer, String[]> readProjectCostExcelContentWithXlsx(InputStream is) {
        Map<Integer, String[]> content = new LinkedHashMap<Integer, String[]>();
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        int rowNum = xssfSheet.getLastRowNum();
        xssfRow = xssfSheet.getRow(1);
        int colNum = xssfRow.getPhysicalNumberOfCells();
        for (int i = 2; i <= rowNum; i++) {
            xssfRow = xssfSheet.getRow(i);
            String[] contentArray = new String[colNum];
            for (int j = 0; j < colNum; j++) {
                if (xssfRow != null) {
                    contentArray[j] = getCellFormatValue(xssfRow.getCell(j)).trim();
                } else {
                    contentArray[j] = "";
                }
            }
            content.put(i, contentArray);
        }
        return content;
    }

    /**
     * 读取Excel数据内容,后缀为.xls
     *
     * @return Map
     */
    public Map<Integer, String[]> readProjectCostExcelContentWithXls(InputStream is) {
        Map<Integer, String[]> content = new LinkedHashMap<Integer, String[]>();
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(1);
        int colNum = row.getPhysicalNumberOfCells();
        for (int i = 2; i <= rowNum; i++) {
            row = sheet.getRow(i);
            String[] contentArray = new String[colNum];
            for (int j = 0; j < colNum; j++) {
                if (row != null) {
                    contentArray[j] = getCellFormatValue(row.getCell(j)).trim();
                } else {
                    contentArray[j] = "";
                }
            }
            content.put(i, contentArray);
        }
        return content;
    }

    /**
     * 读取Excel2003人员数据内容
     *
     * @return List
     */
    public Map<String, Object> readPersonXlsContent(InputStream is) {
        Map<String, Object> map = CollectionKit.newMap();
        HumanInventoryDto humanInventoryDto = null;
        List<HumanInventoryDto> list = CollectionKit.newList();
        List<HumanInventoryImportErrorDto> listError = CollectionKit.newList();
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(1);
        for (int i = 2; i <= rowNum; i++) {
            row = sheet.getRow(i);
            humanInventoryDto = new HumanInventoryDto();
            HumanInventoryImportErrorDto dto = null;
            String projectName = getCellFormatValue(row.getCell(0));
            if (projectName == null || "".equals(projectName)) {
                dto = new HumanInventoryImportErrorDto();
                dto.setLocationError("第 " + (i + 1) + "行");
                dto.setErrorMsg(changeErrorOrEditFlagToChinese("A3"));
                listError.add(dto);
            }
            humanInventoryDto.setProjectName(projectName);
            String empName = getCellFormatValue(row.getCell(1));
            if (empName == null || "".equals(empName)) {
                dto = new HumanInventoryImportErrorDto();
                dto.setLocationError("第 " + (i + 1) + "行");
                dto.setErrorMsg(changeErrorOrEditFlagToChinese("B8"));
                listError.add(dto);
            }
            humanInventoryDto.setEmpName(empName);
            String input = getCellFormatValue(row.getCell(2));
            if (input == null || "".equals(input)) {
                dto = new HumanInventoryImportErrorDto();
                dto.setLocationError("第 " + (i + 1) + "行");
                dto.setErrorMsg(changeErrorOrEditFlagToChinese("B9"));
                listError.add(dto);
            } else if (!input.matches("([0-9]+\\.[0-9]+)||([0-9]+)")) {
                dto = new HumanInventoryImportErrorDto();
                dto.setLocationError("第" + (i + 1) + "行");
                dto.setErrorMsg(changeErrorOrEditFlagToChinese("B10"));
                listError.add(dto);
            } else {
                humanInventoryDto.setManpowerInput(Double.parseDouble(input));
            }
            humanInventoryDto.setSubProjectName(getCellFormatValue(row.getCell(3)));
            humanInventoryDto.setWorkContent(getCellFormatValue(row.getCell(4)));
            list.add(humanInventoryDto);
        }
        map.put("listError", listError);
        map.put("list", list);
        return map;
    }

    /**
     * 读取Excel2007人员数据内容
     *
     * @return List
     */
    public Map<String, Object> readPersonXlsxContent(InputStream is) {
        Map<String, Object> map = CollectionKit.newMap();
        HumanInventoryDto humanInventoryDto = null;
        List<HumanInventoryDto> list = CollectionKit.newList();
        List<HumanInventoryImportErrorDto> listError = CollectionKit.newList();
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        int rowNum = xssfSheet.getLastRowNum();
        xssfRow = xssfSheet.getRow(1);
        for (int i = 2; i <= rowNum; i++) {
            xssfRow = xssfSheet.getRow(i);
            humanInventoryDto = new HumanInventoryDto();
            HumanInventoryImportErrorDto dto = null;
            String projectName = getCellFormatValue(xssfRow.getCell(0));
            if (projectName == null || "".equals(projectName)) {
                dto = new HumanInventoryImportErrorDto();
                dto.setLocationError("第 " + (i + 1) + "行");
                dto.setErrorMsg(changeErrorOrEditFlagToChinese("A3"));
                listError.add(dto);
            }
            humanInventoryDto.setProjectName(projectName);
            String empName = getCellFormatValue(xssfRow.getCell(1));
            if (empName == null || "".equals(empName)) {
                dto = new HumanInventoryImportErrorDto();
                dto.setLocationError("第 " + (i + 1) + "行");
                dto.setErrorMsg(changeErrorOrEditFlagToChinese("B8"));
                listError.add(dto);
            }
            humanInventoryDto.setEmpName(empName);
            String input = getCellFormatValue(xssfRow.getCell(2));
            if (input == null || "".equals(input)) {
                dto = new HumanInventoryImportErrorDto();
                dto.setLocationError("第 " + (i + 1) + "行");
                dto.setErrorMsg(changeErrorOrEditFlagToChinese("B9"));
                listError.add(dto);
            } else if (!input.matches("([0-9]+\\.[0-9]+)||([0-9]+)")) {
                dto = new HumanInventoryImportErrorDto();
                dto.setLocationError("第" + (i + 1) + "行");
                dto.setErrorMsg(changeErrorOrEditFlagToChinese("B10"));
                listError.add(dto);
            } else {
                humanInventoryDto.setManpowerInput(Double.parseDouble(input));
            }
            humanInventoryDto.setSubProjectName(getCellFormatValue(xssfRow.getCell(3)));
            humanInventoryDto.setWorkContent(getCellFormatValue(xssfRow.getCell(4)));
            list.add(humanInventoryDto);
        }
        map.put("listError", listError);
        map.put("list", list);
        return map;
    }

    /**
     * 按行读取Excel数据内容 .xls
     *
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readXlsExcelContentByLine(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(1);
        int colNum = row.getPhysicalNumberOfCells();
        for (int i = 2; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                str += getCellFormatValue(row.getCell(j)).trim() + "";
                j++;
            }
            content.put(i, str);
        }
        return content;
    }

    /**
     * 按行读取Excel数据内容 .xlsx
     *
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readXlsxExcelContentByLine(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xssfSheet = xssfWorkbook.getSheetAt(0);
        int rowNum = xssfSheet.getLastRowNum();
        xssfRow = xssfSheet.getRow(1);
        int colNum = xssfRow.getPhysicalNumberOfCells();
        for (int i = 2; i <= rowNum; i++) {
            xssfRow = xssfSheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                str += getCellFormatValue(xssfRow.getCell(j)).trim() + "";
                j++;
            }
            content.put(i, str);
        }
        return content;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("###.#####");
                    cellvalue = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: {
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    } else {
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case HSSFCell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    @SuppressWarnings({"unused"})
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                result = (cal.get(Calendar.YEAR) + 1900) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    @SuppressWarnings("unused")
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 根据部分名称获取完整名称
     *
     * @param title
     * @param key
     */
    public String getFullNameByString(String[] title, String key) {
        String msg = "";
        for (String t : title) {
            if (t.indexOf(key) != -1) {
                msg = t;
            }
        }
        return msg;
    }

    /**
     * 数据导入获取时间
     */
    @Override
    public List<String> queryDateForImport() {
        List<String> dateList = CollectionKit.newLinkedList();
        List<String> monthList = CollectionKit.newLinkedList();
        List<String> quarterList = CollectionKit.newLinkedList();
        String maxDate = DateUtil.getDBNow().substring(0, 7);
        String minDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date dBegin;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(maxDate));
            changeDateWithMonth(c, -11);
            minDate = sdf.format(c.getTime());
            dBegin = sdf.parse(minDate);
            Date dEnd = sdf.parse(maxDate);
            List<Date> lDate = queryDatesWithMonth(dBegin, dEnd);
            for (Date date : lDate) {
                dateList.add(sdf.format(date));
            }
            String[] dateArray = dateList.toArray(new String[dateList.size()]);
            Arrays.sort(dateArray);
            Integer type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.XMRLPD_inventoryDate);
            if (type == 1) {
                for (int i = dateArray.length - 1; i >= 0; i--) {
                    String month = dateArray[i].substring(0, 4) + "年" + Integer.parseInt(dateArray[i].substring(5)) + "月";
                    monthList.add(month);
                }
                dateList = monthList;
            } else if (type == 2) {
                Set<String> set = new TreeSet<String>();
                for (String arr : dateArray) {
                    String year = arr.substring(0, 4);
                    int month = Integer.parseInt(arr.substring(5));
                    switch (month) {
                        case 1:
                        case 2:
                        case 3:
                            set.add(year + "1");
                            break;
                        case 4:
                        case 5:
                        case 6:
                            set.add(year + "2");
                            break;
                        case 7:
                        case 8:
                        case 9:
                            set.add(year + "3");
                            break;
                        case 10:
                        case 11:
                        case 12:
                            set.add(year + "4");
                            break;
                    }
                }
                String[] date = set.toArray(new String[set.size()]);
                Arrays.sort(date);
                for (int i = date.length - 1; i >= 0; i--) {
                    quarterList.add(date[i].substring(0, 4) + "年" + date[i].substring(5) + "季度");
                }
                dateList = quarterList;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateList;
    }

    /**
     * 获取区间日期
     */
    public static List<Date> queryDatesWithMonth(Date dBegin, Date dEnd) {
        List<Date> lDate = CollectionKit.newLinkedList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 变换日期-月
     */
    public static Calendar changeDateWithMonth(Calendar c, int month) {
        c.add(Calendar.MONTH, month);
        return c;
    }

    /**
     * 根据错误或执行动作标识，转化为文字描述
     */

    @SuppressWarnings("incomplete-switch")
    public String changeErrorOrEditFlagToChinese(String data) {
        String msg = "";
        HumanInventoryEnum dt = HumanInventoryEnum.valueOf(data);
        switch (dt) {
            case A1:
                msg = HumanInventoryEnum.A1.getValue();
                break;
            case A2:
                msg = HumanInventoryEnum.A2.getValue();
                break;
            case A3:
                msg = HumanInventoryEnum.A3.getValue();
                break;
            case A4:
                msg = HumanInventoryEnum.A4.getValue();
                break;
            case A5:
                msg = HumanInventoryEnum.A5.getValue();
                break;
            case A6:
                msg = HumanInventoryEnum.A6.getValue();
                break;
            case A7:
                msg = HumanInventoryEnum.A7.getValue();
                break;
            case A8:
                msg = HumanInventoryEnum.A8.getValue();
                break;
            case A9:
                msg = HumanInventoryEnum.A9.getValue();
                break;
            case A10:
                msg = HumanInventoryEnum.A10.getValue();
                break;
            case A11:
                msg = HumanInventoryEnum.A11.getValue();
                break;
            case A12:
                msg = HumanInventoryEnum.A12.getValue();
                break;
            case A13:
                msg = HumanInventoryEnum.A13.getValue();
                break;
            case A14:
                msg = HumanInventoryEnum.A14.getValue();
                break;
            case A15:
                msg = HumanInventoryEnum.A15.getValue();
                break;
            case A16:
                msg = HumanInventoryEnum.A16.getValue();
                break;
            case A17:
                msg = HumanInventoryEnum.A17.getValue();
                break;
            case A18:
                msg = HumanInventoryEnum.A18.getValue();
                break;
            case A19:
                msg = HumanInventoryEnum.A19.getValue();
                break;
            case A20:
                msg = HumanInventoryEnum.A20.getValue();
                break;
            case A21:
                msg = HumanInventoryEnum.A21.getValue();
                break;
            case A22:
                msg = HumanInventoryEnum.A22.getValue();
                break;
            case A23:
                msg = HumanInventoryEnum.A23.getValue();
                break;
            case A24:
                msg = HumanInventoryEnum.A24.getValue();
                break;
            case B1:
                msg = HumanInventoryEnum.B1.getValue();
                break;
            case B2:
                msg = HumanInventoryEnum.B2.getValue();
                break;
            case B3:
                msg = HumanInventoryEnum.B3.getValue();
                break;
            case B4:
                msg = HumanInventoryEnum.B4.getValue();
                break;
            case B5:
                msg = HumanInventoryEnum.B5.getValue();
                break;
            case B6:
                msg = HumanInventoryEnum.B6.getValue();
                break;
            case B7:
                msg = HumanInventoryEnum.B7.getValue();
                break;
            case B8:
                msg = HumanInventoryEnum.B8.getValue();
                break;
            case B9:
                msg = HumanInventoryEnum.B9.getValue();
                break;
            case B10:
                msg = HumanInventoryEnum.B10.getValue();
                break;
            case B11:
                msg = HumanInventoryEnum.B11.getValue();
                break;
            case B12:
                msg = HumanInventoryEnum.B12.getValue();
                break;
        }
        return msg;
    }

    private String SUCCESS = HumanInventoryEnum.SUCCESS.getValue();
    private String ERROR = HumanInventoryEnum.ERROR.getValue();
    private String COSTTYPEERROR = HumanInventoryEnum.COSTTYPEERROR.getValue();
    private String PERSONTYPEERROR = HumanInventoryEnum.PERSONTYPEERROR.getValue();
    // 文件内容开始行
    private int FILECONTENTSTARTNUM = Integer.parseInt(HumanInventoryEnum.FILECONTENTSTARTNUM.getValue());
    // 错误标识
    private String INVENTORYTIMEERROR = HumanInventoryEnum.INVENTORYTIMEERROR.getValue();
    private String INVENTORYTIMETYPEERROR = HumanInventoryEnum.INVENTORYTIMETYPEERROR.getValue();
    private String PRONAMEISNULLERROR = HumanInventoryEnum.PRONAMEISNULLERROR.getValue();
    private String PRONAMEISSAMEERROR = HumanInventoryEnum.PRONAMEISSAMEERROR.getValue();
    private String PRINCIPALISNULLERROR = HumanInventoryEnum.PRINCIPALISNULLERROR.getValue();
    private String PRINCIPALISNOERROR = HumanInventoryEnum.PRINCIPALISNOERROR.getValue();
    private String LEADORGANISNULLERROR = HumanInventoryEnum.LEADORGANISNULLERROR.getValue();
    private String LEADORGANISNOERROR = HumanInventoryEnum.LEADORGANISNOERROR.getValue();
    //    private String PARTAKEORGANISNULLERROR = HumanInventoryEnum.PARTAKEORGANISNULLERROR.getValue();
    private String PARTAKEORGANISNOERROR = HumanInventoryEnum.PARTAKEORGANISNOERROR.getValue();
    private String TYPEISNULLERROR = HumanInventoryEnum.TYPEISNULLERROR.getValue();
    private String PROGRESSERROR = HumanInventoryEnum.PROGRESSERROR.getValue();
    private String NUMTYPEERROR = HumanInventoryEnum.NUMTYPEERROR.getValue();
    private String DATETYPEERROR = HumanInventoryEnum.DATETYPEERROR.getValue();
    private String STARTDATETYPEERROR = HumanInventoryEnum.STARTDATETYPEERROR.getValue();
    private String ENDDATETYPEERROR = HumanInventoryEnum.ENDDATETYPEERROR.getValue();
    private String PRONAMEISMOREERROR = HumanInventoryEnum.PRONAMEISMOREERROR.getValue();
    private String PRINCIPALISMOREERROR = HumanInventoryEnum.PRINCIPALISMOREERROR.getValue();
    private String LEADORGANISMOREERROR = HumanInventoryEnum.LEADORGANISMOREERROR.getValue();
    private String TYPEISMOREERROR = HumanInventoryEnum.TYPEISMOREERROR.getValue();
    private String PROGRESSISMOREERROR = HumanInventoryEnum.PROGRESSISMOREERROR.getValue();
    private String NUMISNULLERROR = HumanInventoryEnum.NUMISNULLERROR.getValue();
    // 操作标识
    private String UPDATEOPERATE = HumanInventoryEnum.UPDATEOPERATE.getValue();
    private String DELOPERATE = HumanInventoryEnum.DELOPERATE.getValue();
    // 人力标题
    private String PARTAKEEMP = HumanInventoryEnum.PARTAKEEMP.getValue();
    private String INPUTEMP = HumanInventoryEnum.INPUTEMP.getValue();
    private String WORKCONTENT = HumanInventoryEnum.WORKCONTENT.getValue();
    // 费用标题
    private String PROJECTNAME = HumanInventoryEnum.PROJECTNAME.getValue();
    private String PROJECTPRINCIPAL = HumanInventoryEnum.PROJECTPRINCIPAL.getValue();
    private String PROJECTLEADORGAN = HumanInventoryEnum.PROJECTLEADORGAN.getValue();
    private String PROJECTPARTAKEORGAN = HumanInventoryEnum.PROJECTPARTAKEORGAN.getValue();
    private String PROJECTTYPE = HumanInventoryEnum.PROJECTTYPE.getValue();
    private String PROJECTPROGRESS = HumanInventoryEnum.PROJECTPROGRESS.getValue();
    private String PROJECTINPUT = HumanInventoryEnum.PROJECTINPUT.getValue();
    private String PROJECTOUTPUT = HumanInventoryEnum.PROJECTOUTPUT.getValue();
    private String PROJECTSTARTDATE = HumanInventoryEnum.PROJECTSTARTDATE.getValue();
    private String PROJECTENDDATE = HumanInventoryEnum.PROJECTENDDATE.getValue();

}
