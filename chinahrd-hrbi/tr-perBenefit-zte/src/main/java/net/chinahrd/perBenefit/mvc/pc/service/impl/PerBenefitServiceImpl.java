package net.chinahrd.perBenefit.mvc.pc.service.impl;

import java.util.Date;
import java.util.List;

import net.chinahrd.entity.dtozte.pc.probenefit.AvgBenefitDto;
import net.chinahrd.entity.dtozte.pc.probenefit.AvgBenefitTrendDto;
import net.chinahrd.entity.dtozte.pc.probenefit.PerBenefitDto;
import net.chinahrd.entity.dtozte.pc.probenefit.PerBenefitResultDto;
import net.chinahrd.perBenefit.mvc.pc.dao.PerBenefitDao;
import net.chinahrd.perBenefit.mvc.pc.service.PerBenefitService;
import net.chinahrd.utils.DateUtil;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("perBenefitService")
public class PerBenefitServiceImpl implements PerBenefitService {

    @Autowired
    private PerBenefitDao perBenefitDao;

    @Override
    public PerBenefitResultDto findPerBenefitResult(String organizationId, String customerId) {
        Integer year = perBenefitDao.findBenefitResultYear(organizationId, customerId);
        if (null == year) {
            year = new DateTime(DateUtil.getDate()).getYear();
        }
        PerBenefitResultDto dto = perBenefitDao.findPerBenefitResult(organizationId, year, customerId);
        if (null == dto) {
            dto = new PerBenefitResultDto();
            dto.setYear(year);
        }
        return dto;
    }

    @Override
    public AvgBenefitDto findAvgBenefit(String organizationId, String customerId) {
        Integer yearMonth = perBenefitDao.findFteMaxDate(organizationId, customerId);
        if (null == yearMonth) {
            Date date = DateUtil.getDate();
            yearMonth = DateUtil.convertToIntYearMonth(date);
        }
        int prevYearMonth = DateUtil.getPreYearMonth(yearMonth, 1);
        return perBenefitDao.findAvgBenefit(organizationId, yearMonth, prevYearMonth, customerId);
    }

    @Override
    public List<AvgBenefitTrendDto> queryAvgBenefitTrend(String organizationId, int type, String minYearMonth, String maxYearMonth, String customerId) {
        List<AvgBenefitTrendDto> trendData = perBenefitDao.queryAvgBenefitTrendList(organizationId, type, minYearMonth, maxYearMonth, customerId);
        return trendData;
    }

    @Override
    public List<AvgBenefitTrendDto> queryPayTrend(String organizationId, int type, String minYearMonth, String maxYearMonth, String customerId) {
        List<AvgBenefitTrendDto> trendData = perBenefitDao.queryPayTrendList(organizationId, type, minYearMonth, maxYearMonth, customerId);
        return trendData;
    }

    @Override
    public List<PerBenefitDto> querySubOrganPay(String organizationId, int type, String minYearMonth, String maxYearMonth, String customerId) {
        List<PerBenefitDto> resultDtos = perBenefitDao.querySubOrganPay(organizationId, type, minYearMonth, maxYearMonth, customerId);
        return resultDtos;
    }

    @Override
    public List<AvgBenefitTrendDto> queryExecuteRateTrend(String organizationId, String minYearMonth, String maxYearMonth, String customerId) {
        List<AvgBenefitTrendDto> trendData = perBenefitDao.queryExecuteRateTrendList(organizationId, minYearMonth, maxYearMonth, customerId);
        return trendData;
    }

    @Override
    public List<PerBenefitDto> querySubOrganExecuteRate(String organizationId, String minYearMonth, String maxYearMonth, String customerId) {
        List<PerBenefitDto> resultDtos = perBenefitDao.querySubOrganExecuteRate(organizationId, minYearMonth, maxYearMonth, customerId);
        return resultDtos;
    }

    @Override
    public List<PerBenefitDto> getOrgBenefitData(String organizationId, int type, String minYearMonth, String maxYearMonth, String customerId) {
        if (null == maxYearMonth) {
            maxYearMonth = perBenefitDao.findFteMaxDate(organizationId, customerId).toString();
        }
        return perBenefitDao.queryOrgBenefit(organizationId, type, minYearMonth, maxYearMonth, customerId);
    }

}
