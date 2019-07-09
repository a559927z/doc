package net.chinahrd.employeePerformance.mvc.pc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chinahrd.employeePerformance.mvc.pc.dao.PerChangeDao;
import net.chinahrd.employeePerformance.mvc.pc.service.PerChangeService;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeEmpDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeQueryDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreChangeStatusDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreDetailDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreStarCountDto;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;


@Service("perChangeService")
public class PerChangeServiceImpl implements PerChangeService {

    @Autowired
    private PerChangeDao perChangeDao;

    @Override
    public KVItemDto queryHighLow2MonthCount(String organId, List<Integer> yearMonths, Boolean iHighLow, String customerId) {
        List<Integer> ranks;
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        if (iHighLow) {
            ranks = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);
        } else {
            ranks = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN);
        }
        Map<String, Object> map = CollectionKit.newMap();
        map.put("customerId", customerId);
        map.put("yearMonths", yearMonths);
        map.put("ranks", ranks);
        map.put("organId", organId);
        map.put("preWeek", preWeek);

        return perChangeDao.queryHighLow2MonthCount(map);
    }

    @Override
    public KVItemDto queryHighLowMonthCount(String organId, Integer yearMonth, Boolean iHighLow, String customerId) {
        List<Integer> ranks;
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        if (iHighLow) {
            ranks = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);
        } else {
            ranks = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN);
        }

        Map<String, Object> map = CollectionKit.newMap();
        map.put("customerId", customerId);
        map.put("yearMonth", yearMonth);
        map.put("ranks", ranks);
        map.put("organId", organId);
        map.put("preWeek", preWeek);

        return perChangeDao.queryHighLowMonthCount(map);
    }

    @Override
    public PaginationDto<PerfChangeEmpDto> queryLowPre(String organizationId, Integer yearMonth, Integer prevYearMonth, Boolean isManyPerf, String customerId, PaginationDto<PerfChangeEmpDto> dto) {
        // 从缓存的配置表取 by jxzhang
        List<Integer> lowPreVals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        if (null == lowPreVals) {
            return null;
        }

        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());

        Map<String, Object> map = CollectionKit.newMap();
        map.put("customerId", customerId);
        map.put("yearMonth", yearMonth);
        map.put("prevYearMonth", prevYearMonth);
        map.put("organizationId", organizationId);
        map.put("ranks", lowPreVals);
        map.put("manyPerf", isManyPerf);
        map.put("preWeek", preWeek);

        List<PerfChangeEmpDto> dtos = perChangeDao.queryHighLowPreByMonth(map, rowBounds);
        dto.setRows(dtos);
        return dto;
    }

    @Override
    public PaginationDto<PerfChangeEmpDto> queryHighPre(String organizationId, Integer yearMonth, Integer prevYearMonth, Boolean isManyPerf, String customerId, PaginationDto<PerfChangeEmpDto> dto) {
        // 从缓存的配置表取 by jxzhang
        List<Integer> highPreVals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN);
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        if (null == highPreVals) {
            return null;
        }
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());

        Map<String, Object> map = CollectionKit.newMap();
        map.put("customerId", customerId);
        map.put("yearMonth", yearMonth);
        map.put("prevYearMonth", prevYearMonth);
        map.put("organizationId", organizationId);
        map.put("ranks", highPreVals);
        map.put("manyPerf", isManyPerf);
        map.put("preWeek", preWeek);

        List<PerfChangeEmpDto> dtos = perChangeDao.queryHighLowPreByMonth(map, rowBounds);
        dto.setRows(dtos);
        return dto;
    }

    @Override
    public List<KVItemDto> queryHighLowPreByMonth(String organizationId,
                                                  Integer yearMonth, Integer prevYearMonth, String customerId) {
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);

        PerfChangeQueryDto queryDto = new PerfChangeQueryDto();
        queryDto.setCustomerId(customerId);
        queryDto.setPrevYearMonth(prevYearMonth);
        queryDto.setOrganizationId(organizationId);
        queryDto.setYearMonth(yearMonth);
        queryDto.setPreWeek(preWeek);

        List<PerfChangeEmpDto> list = perChangeDao.queryHighLowPreByMonth2(queryDto);

        List<List<PerfChangeEmpDto>> result = CollectionKit.newList();
        List<PerfChangeEmpDto> list1 = CollectionKit.newList();
        List<PerfChangeEmpDto> list2 = CollectionKit.newList();
        List<PerfChangeEmpDto> list3 = CollectionKit.newList();
        List<PerfChangeEmpDto> list4 = CollectionKit.newList();

        if (null == list) {
            return null;
        }
        String v1 = CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.GRJXJBH_V1);
        String v2 = CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.GRJXJBH_V2);
        String v3 = CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.GRJXJBH_V3);
        String v4 = CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.GRJXJBH_V4);
        for (PerfChangeEmpDto dto : list) {
            String perName = dto.getPerformanceName();
            String prevName = dto.getPrevPerformanceName();
            //查询当前低绩效
            if (perName.equals(v1) || perName.equals(v2)) {
                list2.add(dto);
                //查询连续两次低绩效
                if (prevName.equals(v1) || prevName.equals(v2)) {
                    list1.add(dto);
                }
            }
            //查询当前高绩效
            if (perName.equals(v3) || perName.equals(v4)) {
                list4.add(dto);
                //查询连续两次高绩效
                if (prevName.equals(v3) || prevName.equals(v4)) {
                    list3.add(dto);
                }
            }
        }
        result.add(list1);
        result.add(list2);
        result.add(list3);
        result.add(list4);
        List<KVItemDto> kvItemDtos = CollectionKit.newList();
        int list1Len = list1.size(), list2len = list2.size(), list3len = list3.size(), list4len = list4.size();

        kvItemDtos.add(new KVItemDto(String.valueOf(list1Len), listToString(list1.subList(0, list1Len > 3 ? 3 : list1Len))));
        kvItemDtos.add(new KVItemDto(String.valueOf(list2len), listToString(list2.subList(0, list2len > 3 ? 3 : list2len))));
        kvItemDtos.add(new KVItemDto(String.valueOf(list3len), listToString(list3.subList(0, list3len > 3 ? 3 : list3len))));
        kvItemDtos.add(new KVItemDto(String.valueOf(list4len), listToString(list4.subList(0, list4len > 3 ? 3 : list4len))));

        return kvItemDtos;
    }

    @Override
    public List<Integer> queryPreYearMonthByCustomerId(String customerId, Integer perWeek) {
        return perChangeDao.queryPreYearMonthByCustomerId(customerId, perWeek);
    }

    @Override
    public PreChangeStatusDto queryPreChangeCountByMonth(String organizationId, Integer beginYearMonth,
                                                         Integer endYearMonth, Integer changeNum, String customerId, List<String> crowds) {
        // 从缓存的配置表取 by jxzhang
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        return perChangeDao.queryPreChangeCountByMonth(organizationId, beginYearMonth, endYearMonth, changeNum, customerId, preWeek, crowds);
    }

    @Override
    public List<PreStarCountDto> queryPerfDistributeCount(String organizationId, Integer currYearMonth, Integer empType, List<String> crowds, String customerId) {
        List<Integer> hVals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN);
        List<Integer> lVals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);

        PerfChangeQueryDto queryDto = new PerfChangeQueryDto();
        queryDto.setCustomerId(customerId);
        queryDto.setOrganizationId(organizationId);
        queryDto.setEmpType(empType);
        queryDto.setPreWeek(preWeek);
        queryDto.setYearMonth(currYearMonth);
        queryDto.setCrowds(crowds);
        queryDto.setHeightPerfs(hVals);
        queryDto.setLowPerfs(lVals);
        return perChangeDao.queryPerfDistributeCount(queryDto);
    }

    @Override
    public PaginationDto<PerfChangeEmpDto> queryPerfDistributeEmp(String organizationId, Integer yearMonth, Integer empType, List<String> crowds, Integer idx, String customerId, PaginationDto<PerfChangeEmpDto> dto) {
        List<Integer> hVals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN);
        List<Integer> lVals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);

        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        List<Integer> ranks = CollectionKit.newList();
        boolean hasNotKey = false;
        if (idx == 0) {
            ranks.addAll(hVals);
        } else if (idx >= 2) {
            ranks.addAll(lVals);
        } else {
            ranks.addAll(lVals);
            ranks.addAll(hVals);
            hasNotKey = true;
        }
        Map<String, Object> map = CollectionKit.newMap();
        map.put("customerId", customerId);
        map.put("organizationId", organizationId);
        map.put("yearMonth", yearMonth);
        map.put("empType", empType);
        map.put("crowds", crowds);
        map.put("preWeek", preWeek);
        map.put("hasNotKey", hasNotKey);
        map.put("ranks", ranks);

        List<PerfChangeEmpDto> dtos = perChangeDao.queryPerfDistributeEmp(map, rowBounds);
        dto.setRows(dtos);
        return dto;
    }

    @Override
    public List<List<PerfChangeEmpDto>> queryPreDistributionListByMonth(String organizationId, Integer currYearMonth, Integer prevYearMonth, Integer empType, String customerId, List<String> crowds) {
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);

        PerfChangeQueryDto queryDto = new PerfChangeQueryDto();
        queryDto.setCustomerId(customerId);
        queryDto.setOrganizationId(organizationId);
        queryDto.setEmpType(empType);
        queryDto.setPreWeek(preWeek);
        queryDto.setYearMonth(currYearMonth);
        queryDto.setPrevYearMonth(prevYearMonth);
        queryDto.setCrowds(crowds);
        List<PerfChangeEmpDto> list = perChangeDao.queryPreDistributionListByMonth(queryDto);
        // 解决bug418
        List<PerfChangeEmpDto> listPre = CollectionKit.newList();
        List<PerfChangeEmpDto> listLowPre = CollectionKit.newList();
        List<PerfChangeEmpDto> listHightPre = CollectionKit.newList();
        List<List<PerfChangeEmpDto>> rs = new ArrayList<List<PerfChangeEmpDto>>();

        List<Integer> hVals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN);
        List<Integer> lVals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);

        for (PerfChangeEmpDto d : list) {
            if (hVals.contains(d.getPerKey())) {
                listHightPre.add(d);
            } else if (lVals.contains(d.getPerKey())) {
                listLowPre.add(d);
            } else {
                listPre.add(d);
            }
        }
        // 以下List顺固定，高，中，低 @see perChange.js 行 378
        rs.add(listHightPre);
        rs.add(listPre);
        rs.add(listLowPre);
        return rs;
    }

    @Override
    public List<PreStarCountDto> queryPreStarCountByMonth(String organizationId, Integer yearMonth, String customerId, List<String> crowds) {
        // 从缓存的配置表取 by jxzhang
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        return perChangeDao.queryPreStarCountByMonth(organizationId, yearMonth, customerId, preWeek, crowds);
    }

    @Override
    public PaginationDto<PreDetailDto> queryPerformanceDetail(String queryType, String keyName, String customerId, String organizationId, PaginationDto<PreDetailDto> dto,
                                                              String yearMonth, String sequenceId, String abilityId, String performanceKey) {
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("customerId", customerId);
        mapParam.put("organizationId", organizationId);
        mapParam.put("rowBounds", rowBounds);
        //if(queryType.equals("2")){
        List<String> sequenceIdlist = CollectionKit.strToList(sequenceId, ",");
        List<String> abilityIdlist = CollectionKit.strToList(abilityId, ",");
        List<String> performanceKeylist = CollectionKit.strToList(performanceKey, ",");
        mapParam.put("yearMonth1", yearMonth);


        // 绩效周期使用配置 by jxhznag
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        mapParam.put("preWeek", preWeek);
        if (preWeek == 0) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMM");
        DateTime currDate = DateTime.parse(yearMonth, formatter);
        switch (preWeek) {
            case 1:
                // 年度在数据库里格式为：201501, 201401, 201301,....n01
                mapParam.put("yearMonth2", currDate.minusMonths(12).toString("yyyyMM"));
                mapParam.put("yearMonth3", currDate.minusMonths(24).toString("yyyyMM"));
                break;
            case 2:
                mapParam.put("yearMonth2", currDate.minusMonths(6).toString("yyyyMM"));
                mapParam.put("yearMonth3", currDate.minusMonths(12).toString("yyyyMM"));
                break;
            case 3:
                // 季度在数据库里格式为：Q1 = 201501,	Q2 = 201504,	Q3 = 201507,	Q4 = 201510
                mapParam.put("yearMonth2", currDate.minusMonths(3).toString("yyyyMM"));
                mapParam.put("yearMonth3", currDate.minusMonths(6).toString("yyyyMM"));
                break;
        }
        mapParam.put("sequenceIdlist", sequenceIdlist);
        mapParam.put("abilityIdlist", abilityIdlist);
        mapParam.put("performanceKeylist", performanceKeylist);
        mapParam.put("keyName", keyName == null ? "" : keyName);


        int count = perChangeDao.queryPerformanceDetailCount(mapParam);
        List<PreDetailDto> dtos = perChangeDao.queryPerformanceDetail(mapParam);

        dto.setRecords(count);
        dto.setRows(dtos);

        return dto;
    }


    @Override
    public Integer queryEmpCount(String customerId, String organizationId,
                                 String yearMonth, String empType, List<String> crowds) {
        // TODO 获取所选绩效最后一天
        String time = getYeatMonthDay(yearMonth);
        if (time == null) {
            return 0;
        }
        Map<String, Object> mapParam = CollectionKit.newMap();
        mapParam.put("customerId", customerId);
        mapParam.put("organizationId", organizationId);
        mapParam.put("yearMonth", yearMonth);
        mapParam.put("time", time);
        mapParam.put("empType", empType);
        mapParam.put("crowds", crowds);
        return perChangeDao.queryEmpCount(mapParam);
    }

    private String getYeatMonthDay(String yearMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        // 获取Calendar
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(yearMonth));
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
            return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    private String listToString(List<PerfChangeEmpDto> dtos) {
        String result = "";
        for (PerfChangeEmpDto dto : dtos) {
            if (result != "") result += "、";
            result += dto.getUserName();
        }
        return result;
    }
    
    public List<PerfChangeCountDto> queryPerchangeByOrgan(String customerId, String organId, Integer yearMonth) {
    	return perChangeDao.queryPerchangeByOrgan(customerId, organId, yearMonth);
    }
}
