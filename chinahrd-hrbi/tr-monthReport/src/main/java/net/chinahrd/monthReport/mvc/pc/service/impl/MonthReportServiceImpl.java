package net.chinahrd.monthReport.mvc.pc.service.impl;

import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.manage.HomeConfigDto;
import net.chinahrd.entity.dto.pc.monthReport.*;
import net.chinahrd.monthReport.mvc.pc.dao.MonthReportDao;
import net.chinahrd.monthReport.mvc.pc.service.MonthReportService;
import net.chinahrd.mvc.pc.dao.admin.FunctionDao;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.CompareUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.eMail.MailUtil;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 月报Service实现类
 * Created by wqcai on 16/08/24 024.
 */
@Service("monthReportService")
public class MonthReportServiceImpl implements MonthReportService {

	@Autowired
    private MailUtil mailUtil;
	
    @Autowired
    private FunctionDao functionDao;

    @Autowired
    private MonthReportDao monthReportDao;


    @Override
    public List<HomeConfigDto> queryUserConfig(String functionId, String empId, String customerId) {
        return functionDao.queryHomeConfig(functionId, empId, customerId);
    }

    @Override
    public List<MonthReportChangesDto> queryChangesToSubOrgan(String organId, String yearMonth, String customerId) {
        List<MonthReportChangesDto> list = monthReportDao.queryChangesToSubOrgan(organId, yearMonth, customerId);
        //排序
        Collections.sort(list, CompareUtil.createComparator(1, "pureFlow"));
        addTotalToList(list);
        return list;
    }

    @Override
    public List<MonthReportChangesDto> queryChangesToAbility(String organId, String yearMonth, String customerId) {
        List<MonthReportChangesDto> list = monthReportDao.queryChangesToAbility(organId, yearMonth, customerId);
        addTotalToList(list);
        return list;
    }

    @Override
    public PaginationDto<MonthReportChangesDetailDto> queryChangesDetails(String itemId, String organId, String pos, Integer yearMonth, String customerId, PaginationDto dto) {
        Integer changesPos = getChangesPos(pos);    //异常类型，位置根据前端来展示
        int count = monthReportDao.queryChangesDetailsCount(changesPos, itemId, organId, yearMonth, customerId);
        dto.setRecords(count);
        if (count == 0) {
            return dto;
        }
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        List<MonthReportChangesDetailDto> roleList = monthReportDao.queryChangesDetails(changesPos, itemId, organId, yearMonth, customerId, rowBounds);
        dto.setRows(roleList);
        return dto;
    }

    @Override
    public PaginationDto<DismissRiskDto> queryDimissionEmpsList(String organId, Integer yearMonth, String customerId, PaginationDto<DismissRiskDto> dto) {
        int count = monthReportDao.queryDimissionEmpsCount(1, organId, yearMonth, customerId);
        dto.setRecords(count);
        if (count == 0) {
            return dto;
        }
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        List<DismissRiskDto> roleList = monthReportDao.queryDimissionEmpsList(1, organId, yearMonth, customerId, rowBounds);
        
        dto.setRows(roleList);
        return dto;
    }
    
    @Override
    public List<DismissRiskDto> queryDimissionEmpsListNoPage(String organId, Integer yearMonth, String customerId) {
    	List<DismissRiskDto> roleList = monthReportDao.queryDimissionEmpsListNoPage(1, organId, yearMonth, customerId);
        return roleList;
    }

    @Override
    public List<MonthReportTrainGeneralDto> queryTrainGeneral(String organId, Integer year, String customerId) {
        return monthReportDao.queryTrainGeneral(organId, year, customerId);
    }

    @Override
    public List<MonthReportEmpCountDto> queryInJobsEmpCount(String organId, String customerId) {
        return monthReportDao.queryInJobsEmpCount(organId, customerId);
    }

    @Override
    public List<MonthReportSalesCountDto> querySalesCount(Integer type, String organId, Integer yearMonth, String customerId) {
        if (type.intValue() == 1) {
            return monthReportDao.querySalesCountByProduct(organId, yearMonth, customerId);
        }
        return monthReportDao.querySalesCountByOrgan(organId, yearMonth, customerId);
    }

    @Override
    public List<MonthReportSalesCountDto> querySalesCountByMonth(Integer type, String itemId, String organId, Integer beginTime, Integer endTime, String customerId) {
        if (type.intValue() == 1) {
            return monthReportDao.queryProductSalesCountByMonth(itemId, organId, beginTime, endTime, customerId);
        }
        return monthReportDao.queryOrganSalesCountByMonth(organId, beginTime, endTime, customerId);
    }

    @Override
    public void insertFavorites(MonthReportSavaDto savaDto) {
        monthReportDao.insertFavorites(savaDto);
    }

    @Override
    public MultiValueMap<Integer, MonthReportSavaDto> queryFavorites(String empId, String customerId) {
        List<MonthReportSavaDto> dtoList = monthReportDao.queryFavorites(empId, customerId);
        MultiValueMap<Integer, MonthReportSavaDto> multiValue = new LinkedMultiValueMap<Integer, MonthReportSavaDto>();
        for (MonthReportSavaDto dto : dtoList) {
            multiValue.add(dto.getYearMonth(), dto);
        }
        return multiValue;
    }

    @Override
    public MultiValueMap<Integer, MonthReportShareDto> queryShare(String empId, String customerId) {
        List<MonthReportShareDto> dtoList = monthReportDao.queryShare(empId, customerId, null);
        MultiValueMap<Integer, MonthReportShareDto> multiValue = new LinkedMultiValueMap<Integer, MonthReportShareDto>();
        for (MonthReportShareDto dto : dtoList) {
            multiValue.add(dto.getYearMonth(), dto);
        }
        return multiValue;
    }

    @Override
    public MonthReportSavaDto findFavoritesInfo(String empId, String organId, Integer yearMonth, String customerId) {
        return monthReportDao.findFavoritesInfo(empId, organId, yearMonth, customerId);
    }

    @Override
    public void deleteFavorites(String savaId, String customerId, String path) {
    	File file = new File(path);
    	file.delete();
        monthReportDao.deleteFavorites(savaId, customerId);
    }

    /**
     * 获取异常类型（根据前端展示来）
     *
     * @param pos
     * @return
     */
    private Integer getChangesPos(String pos) {
        Integer posNumber = Integer.parseInt(pos);
        if (posNumber == null) return null;
        Integer changesPos = null;
        switch (posNumber.intValue()) {
            case 1:
                changesPos = 3;
                break;
            case 2:
                changesPos = 2;
                break;
            case 3:
                changesPos = 4;
                break;
            case 4:
                changesPos = 5;
                break;

        }
        return changesPos;
    }

    /***
     * 添加合计数据
     * @param list
     */
    private void addTotalToList(List<MonthReportChangesDto> list) {
        int entryTotal = 0, transferInTotal = 0, transferOutTotal = 0, dimissionTotal = 0;
        for (MonthReportChangesDto dto : list) {
            entryTotal += dto.getEntry();
            transferInTotal += dto.getTransferIn();
            transferOutTotal += dto.getTransferOut();
            dimissionTotal += dto.getDimission();
        }

        //合计
        MonthReportChangesDto totalDto = new MonthReportChangesDto();
        totalDto.setItemId("total");
        totalDto.setItemName("合计");
        totalDto.setEntry(entryTotal);
        totalDto.setTransferIn(transferInTotal);
        totalDto.setTransferOut(transferOutTotal);
        totalDto.setDimission(dimissionTotal);
        list.add(totalDto);
    }

    public List<MonthReportPromotionDto> queryPromotionGeneral(String customerId, String organId, Integer yearMonth) {
    	return monthReportDao.queryPromotionGeneral(customerId, organId, yearMonth);
    }
    
    public List<MonthReportConfigDto> queryMonthReportConfig(String customerId, String empId) {
    	return monthReportDao.queryMonthReportConfig(customerId, empId);
    }
    
    public void updateMonthReportConfig(List<MonthReportConfigDto> list) {
    	monthReportDao.updateMonthReportConfig(list);
    }
    
    public List<MonthReportShareDto> checkEmpInfo(String customerId, String keyName) {
        return monthReportDao.checkEmpInfo(customerId, keyName);
    }
    
    public String addMonthReportShare(Map<String, Object> map) {
    	Integer sendMail = (Integer) map.get("sendMail");
    	String toEmpId = (String) map.get("toEmpId");
    	String customerId = (String) map.get("customerId");
    	String reportContent = (String) map.get("reportContent");
    	Integer yearMonth = (Integer) map.get("yearMonth");
    	
    	List<MonthReportShareDto> list = monthReportDao.queryShare(toEmpId, customerId, yearMonth);
    	if(list.size() <= 0) {
    		monthReportDao.addMonthReportShare(map);
    	} else {
    		return "repeat";
    	}
    	String email = monthReportDao.queryEmpEmail(customerId, toEmpId);
    	List<String> rs = CollectionKit.newList();
    	rs.add(email);
    	if(sendMail == 1) {
    		mailUtil.send(rs, "月报分享", telmplet(reportContent));
    	}
    	return "1";
    }
    
    public void deleteMonthReportShare(String customerId, String shareId) {
    	monthReportDao.deleteMonthReportShare(customerId, shareId);
    }
    
    private String telmplet(String reportContent) {
		StringBuffer buf = new StringBuffer();
		buf.append("<HTML><head></head><body>");
		buf.append("<p>");
		buf.append("    &nbsp;您好！<br/>");
		buf.append("</p>");
		buf.append("<div>");
		buf.append("    <br/>《" + reportContent + "》已经分享到你的才报平台上，请及时查看！");
		buf.append("</div>");
		buf.append("<div>");
		buf.append("    <br/><a href='http://zrw.gz.chinahrd.net/login'>http://zrw.gz.chinahrd.net/login</a>");
		buf.append("</div>");
		buf.append("</body></html>");
		return buf.toString();
	}
    
    /**
     * 流失率分析
     * @param map
     * @return
     */
    public List<MonthReportDimissionEmpDto> accordDismissAnalysis(String customerId, String organId, String yearMonth) {
    	Map<String, Object> map = CollectionKit.newMap();
    	Integer year = Integer.parseInt(yearMonth.substring(0, 4));
    	Integer beginYearMonth = Integer.parseInt(year + "01");
    	Integer lastYearMonth = Integer.parseInt(yearMonth) - 100;
    	Integer lastMonth = DateUtil.getPreYearMonth(Integer.parseInt(yearMonth), 1);
    	map.put("customerId", customerId);
    	map.put("organId", organId);
    	map.put("year", year);
    	map.put("beginYearMonth", beginYearMonth);
    	map.put("endYearMonth", Integer.parseInt(yearMonth));
    	map.put("lastYearMonth", lastYearMonth);
    	map.put("lastMonth", lastMonth);
    	return monthReportDao.accordDismissAnalysis(map);
    }
    
    public Map<String, Object> accordDismissByYearMonth(String customerId, String organId, String yearMonth) {
    	Map<String, Object> map = CollectionKit.newMap();
    	String year = yearMonth.substring(0, 4);
    	String LastYear = String.valueOf(Integer.parseInt(yearMonth.substring(0, 4)) - 1);
    	List<MonthReportDimissionEmpDto> thisYear = monthReportDao.accordDismissByYearMonth(customerId, organId, year);
    	List<MonthReportDimissionEmpDto> lastYear = monthReportDao.accordDismissByYearMonth(customerId, organId, LastYear);
    	map.put("thisYear", thisYear);
    	map.put("lastYear", lastYear);
    	return map;
    }
    
    public Double accordDismissInYear(String customerId, String organId, String yearMonth) {
    	Map<String, Object> map = CollectionKit.newMap();
    	Integer year = Integer.parseInt(yearMonth.substring(0, 4));
    	Integer beginYearMonth = Integer.parseInt(year + "01");
    	map.put("customerId", customerId);
    	map.put("organId", organId);
    	map.put("year", year);
    	map.put("beginYearMonth", beginYearMonth);
    	map.put("endYearMonth", Integer.parseInt(yearMonth));
    	return monthReportDao.accordDismissInYear(map);
    }
    
    public List<MonthReportManpowerCostDto> manpowerCostInfo(String customerId, String organId, String yearMonth) {
    	Map<String, Object> map = CollectionKit.newMap();
    	Integer year = Integer.parseInt(yearMonth.substring(0, 4));
    	Integer lastYearMonth = Integer.parseInt(yearMonth) - 100;
    	Integer lastMonth = DateUtil.getPreYearMonth(Integer.parseInt(yearMonth), 1);
    	map.put("customerId", customerId);
    	map.put("organId", organId);
    	map.put("year", year);
    	map.put("yearMonth", yearMonth);
    	map.put("lastYearMonth", lastYearMonth);
    	map.put("lastMonth", lastMonth);
    	return monthReportDao.manpowerCostInfo(map);
    }
}
