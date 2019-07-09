package net.chinahrd.employeePerformance.mvc.app.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.employeePerformance.mvc.app.dao.MobilePerChangeDao;
import net.chinahrd.employeePerformance.mvc.app.service.MobilePerChangeService;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCardDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeQueryDto;
import net.chinahrd.entity.dto.app.performance.PreChangeStatusDto;
import net.chinahrd.entity.dto.app.performance.PreDetailDto;
import net.chinahrd.entity.dto.app.performance.PreStarCountDto;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;

import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 员工绩效 app
 * @author htpeng
 *2016年6月6日上午11:13:04
 */
@Service("mobilePerChangeService")
public class MobilePerChangeServiceImpl implements MobilePerChangeService {

    @Autowired
    private MobilePerChangeDao perChangeDao;

   

    @Override
	public PerfChangeCardDto queryHighLowPreByMonth(String organizationId,
			Integer yearMonth, String customerId) {
    	Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
    	List<Integer> highList =  CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_HEIGHTPERFMAN);
    	List<Integer> lowList =  CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);

		PerfChangeQueryDto queryDto = new PerfChangeQueryDto();
		queryDto.setCustomerId(customerId);
		if(highList!=null){
			if(highList.size()>0){
				queryDto.setHighL(highList.get(0));
				queryDto.setHighH(highList.get(0));
			}
			if(highList.size()>1){
				queryDto.setHighH(highList.get(1));
			}
		}
		if(lowList!=null){
			if(lowList.size()>0){
				queryDto.setLowL(lowList.get(0));
				queryDto.setLowH(lowList.get(0));
			}
			if(lowList.size()>1){
				queryDto.setLowH(lowList.get(1));
			}
		}
		queryDto.setOrganizationId(organizationId);
		queryDto.setYearMonth(yearMonth);
		queryDto.setPreWeek(preWeek);
		
		PerfChangeCardDto perfChangeDto = 
				perChangeDao.queryHighLowPreByMonth(queryDto);
		
		return perfChangeDto;
	}
    
    
    
    
    
    
    
    

    @Override
    public List<Integer> queryPreYearMonthByCustomerId(String customerId,Integer perWeek) {
        return perChangeDao.queryPreYearMonthByCustomerId(customerId, perWeek);
    }

    @Override
    public PreChangeStatusDto queryPreChangeCountByMonth(String organizationId, Integer beginYearMonth,
                                                         Integer endYearMonth, Integer changeNum, String customerId) {
        // 从缓存的配置表取 by jxzhang
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        return perChangeDao.queryPreChangeCountByMonth(organizationId, beginYearMonth, endYearMonth, changeNum, customerId, preWeek);
    }

  
    @Override
    public Map<String,Object> queryPreStarCountByMonth(String organizationId, String yearMonth, String customerId) {
        // 从缓存的配置表取 by jxzhang
		Map<String,Object> result=CollectionKit.newMap();
        Integer preWeek = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.GRJXJBH_PERFMANWEEK);
        String time = getYeatMonthDay(yearMonth);
        PerfChangeCountDto dto=perChangeDao.queryEmpCount(customerId, organizationId, time);

        List<PreStarCountDto> dto1=perChangeDao.queryPreStarCountByMonth(organizationId, yearMonth, customerId, preWeek);
		result.put("count", dto);
		result.put("detail", dto1);
        return result;
    }

    @Override
    public PaginationDto<PreDetailDto> queryPerformanceDetail(String customerId, String organizationId, PaginationDto<PreDetailDto> dto,
                                                              String yearMonth) {
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("customerId", customerId);
        mapParam.put("organizationId", organizationId);
        mapParam.put("rowBounds", rowBounds);
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
        int count = perChangeDao.queryPerformanceDetailCount(mapParam);
        List<PreDetailDto> dtos = perChangeDao.queryPerformanceDetail(mapParam);

        dto.setRecords(count);
        dto.setRows(dtos);

        return dto;
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

}
