package net.chinahrd.salaryBoard.mvc.pc.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryEmpCRDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryEmpSharesDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalarySharesDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryWageDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryWelfareDto;
import net.chinahrd.salaryBoard.mvc.pc.dao.SalaryBoardDao;
import net.chinahrd.salaryBoard.mvc.pc.dao.SalaryBoardProcedureDao;
import net.chinahrd.salaryBoard.mvc.pc.service.SalaryBoardService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Sort;

import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 薪酬看板 实现
 *
 * @author qpzhu by 2016-04-06
 */
@Service("salaryBoardService")
public class SalaryBoardServiceImpl implements SalaryBoardService {

    private final String YEAR_BEGIN_SUFFIX = ".1";

    @Autowired
    SalaryBoardDao salaryBoardDao;
    @Autowired
    private SalaryBoardProcedureDao salaryboarProDao;

    
    /**
     * 薪酬年度预算与去年的比较
     *
     * @param organId
     * @return
     */
    @Override
    public SalaryBoardDto getSalaryBudgetYear(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        SalaryBoardDto dto = new SalaryBoardDto();
        dto.setYear(year);
        SalaryBoardDto dto1 = salaryBoardDao.querySalaryBudgetYear(customerId, organId, year);
        if (dto1 != null) {
            dto.setPayValue(dto1.getPayValue());
            dto.setCompareValue(dto1.getCompareValue());
        }
        SalaryBoardDto dto2 = salaryBoardDao.querySalarySumThisYear(customerId, organId, year);
        if (dto2 == null) {
            return dto;
        }
        dto.setSumPay(dto2.getSumPay());
        String yearMonth = dto2.getYearMonth();
        String beginDate = Integer.parseInt(year) - 1 + "01";
        String endDate = Integer.parseInt(year) - 1 + yearMonth.substring(yearMonth.length() - 2, yearMonth.length());
        SalaryBoardDto dto3 = salaryBoardDao.querySalarySumLastYear(customerId, organId, beginDate, endDate);
        if (dto3 != null) {
            dto.setTotalCompareValue((dto2.getSumPay() - dto3.getSumPay()) / dto3.getSumPay() * 100);
        }
        return dto;
    }


    /**
     * 薪酬占人力成本比
     *
     * @param organId
     * @return
     */
    @Override
    public Map<String, Object> getSalaryProportion(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        //查询薪酬总费用
        SalaryBoardDto dto1 = salaryBoardDao.querySalarySumThisYear(customerId, organId, year);
        //查询人力成本总费用
        SalaryBoardDto dto2 = salaryBoardDao.queryCostTotal(customerId, organId, year);
        if (dto1 == null || dto2 == null) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("year", year);
        rs.put("proportion", dto1.getSumPay() / dto2.getCost() * 100);
        return rs;
    }

    /**
     * 投资回报率
     *
     * @param organId
     * @return
     */
    @Override
    public Map<String, Object> getSalaryRateOfReturn(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        //查询营业收入，支出
        SalaryBoardDto dto = salaryBoardDao.queryIncomeExpenditureYear(customerId, organId, year);
        //查询薪酬福利费用
        SalaryBoardDto dto1 = salaryBoardDao.querysalaryWelfareYear(customerId, organId, year);
        if (dto1 == null || dto == null) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("year", year);
        rs.put("rateOfReturn", (dto.getSalesAmount() - (dto.getExpenditure() - dto1.getCost())) / dto1.getCost());
        return rs;
    }

    /**
     * 薪酬总额统计
     *
     * @param organId
     * @return
     */
    @Override
    public Map<String, Object> getSalaryPayTotal(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        Map<String, Object> rs = CollectionKit.newMap();
        //预算
        SalaryBoardDto dto = salaryBoardDao.querySalaryBudgetYear(customerId, organId, year);
        if (dto != null) {
            rs.put("payValue", dto.getPayValue());
        } else {
            rs.put("payValue", null);
        }
        //累计
        SalaryBoardDto dto1 = salaryBoardDao.querySalarySumThisYear(customerId, organId, year);
        //薪酬月份统计
        if (dto1 != null) {
            rs.put("sumPay", dto1.getSumPay());
        } else {
            rs.put("sumPay", null);
        }
        List<SalaryBoardDto> list = salaryBoardDao.querySalaryPayTotal(customerId, organId, year);
        for (int i = 0; i < list.size(); i++) {
            SalaryBoardDto dto2 = list.get(i);
            String yearMonth = dto2.getYearMonth();
            Integer month = Integer.parseInt(yearMonth.substring(yearMonth.length() - 2, yearMonth.length()));
            dto2.setMonth(month + "月");
            dto2.setSumPay(list.get(i).getSumPay());
        }
        rs.put("list", list);
        return rs;
    }

    /**
     * 下级组织薪酬统计
     *
     * @param organId
     * @return
     */
    @Override
    public List<SalaryBoardDto> getSalarySubOrganization(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryBoardDto> list = salaryBoardDao.querySalarySubOrganization(customerId, organId, year);
        return list;
    }


    /**
     * 组织KPI达标率、人力成本、薪酬总额的年度趋势
     */
    @Override
    public List<SalaryBoardDto> getSalaryCostKpi(String customerId, String organId) {
        List<SalaryBoardDto> list = salaryBoardDao.querySalaryCostKpi(customerId, organId);
        return list;
    }

    /**
     * 营业额、利润、人力成本及薪酬总额的月度趋势
     */
    @Override
    public List<SalaryBoardDto> getSalaryCostSalesProfit(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryBoardDto> list = salaryBoardDao.querySalaryCostSalesProfit(customerId, organId, year);
        return list;
    }
    
    /**
     * 下级组织人力资本投资回报率
     */
    @Override
    public List<SalaryBoardDto> getSalaryOrganRateOfReturn(String customerId, String organId, String yearMonth) {
        List<SalaryBoardDto> list = salaryBoardDao.querySalaryOrganRateOfReturn(customerId, organId, yearMonth);
        List<SalaryBoardDto> newList = CollectionKit.newList();
        for(SalaryBoardDto dto : list) {
        	if(dto.getOrganId().equals(organId)) {
        		newList.add(dto);
        	}
        }
        for(SalaryBoardDto dto : list) {
        	if(!dto.getOrganId().equals(organId)) {
        		newList.add(dto);
        	}
        }
        return newList;
    }

    /**
     * 人力资本投资回报率月度趋势
     */
    @Override
    public List<SalaryBoardDto> getSalaryMonthRateOfReturn(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryBoardDto> list = salaryBoardDao.querySalaryMonthRateOfReturn(customerId, organId, year);
        return list;
    }

    /**
     * 行业分位值年度趋势
     */
    @Override
    public List<SalaryBoardDto> getSalaryBitValueYear(String customerId, String organId) {
        List<SalaryBoardDto> bitList = new ArrayList<SalaryBoardDto>();
        //获取组织年度人均薪酬
        List<SalaryBoardDto> avgList = salaryBoardDao.querySalaryAvgPayList(customerId, organId);
        for (int i = 0; i < avgList.size(); i++) {
            SalaryBoardDto avgDto = new SalaryBoardDto();
            //获取组织的分位值范围
            List<SalaryBoardDto> list = salaryBoardDao.querySalaryBitValueYear(customerId, organId, avgList.get(i).getYear());
            for (int j = 0; j < list.size(); j++) {
                SalaryBoardDto dto = list.get(j);
                //组织人均薪酬大于分位绝对值
                if (dto.getQuantileValue() > avgList.get(i).getAvgPay()) {
                    //如果是第一个分位值，取0到第1个分位值之间的均值，如果是最后一个分位置，则取分位值到100之间的均值，否则取该分位值和下一个分位值之间的均值
                    if (j == 0) {
                        avgDto.setBitValue((0 + dto.getBitValue()) / 2);
                    } else if (j == (list.size() - 1)) {
                        avgDto.setBitValue((100+dto.getBitValue())/2);
                    } else {
                        avgDto.setBitValue((dto.getBitValue() + list.get(j - 1).getBitValue())/2);
                    }
                    avgDto.setYear(dto.getYear());
                    bitList.add(avgDto);
                    break;
                }
                //人均值等于分位绝对值
                else if (dto.getQuantileValue() == avgList.get(i).getAvgPay()) {
                    avgDto.setBitValue(dto.getBitValue());
                    bitList.add(avgDto);
                    break;
                } else {
                    if (j == (list.size() - 1)) {
                        avgDto.setYear(dto.getYear());
                        avgDto.setBitValue((100+dto.getBitValue())/2);
                        bitList.add(avgDto);
                        break;
                    }
                }
            }
        }
        return bitList;
    }

    /**
     * 薪酬差异度岗位表
     */
    @Override
    public Map<String, Object> getSalaryDifferencePost(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        //获取解密钥匙
        String cryptKey = EisWebContext.getCryptKey();
        List<SalaryBoardDto> list = salaryBoardDao.querySalaryDifferencePost(customerId, organId, year, cryptKey);
        Map<String, Object> rs = CollectionKit.newMap();
        if (list.size() <= 0) {
            return null;
        }
        rs.put("date", list.get(0).getYear().substring(0, 4) + "年" + Integer.parseInt(list.get(0).getYear().substring(4, 6)) + "月");
        rs.put("list", list);
        return rs;
    }

    /**
     * 员工CR值
     */
    @Override
    public Map<String, Object> getSalaryEmpCR(String customerId, String organId) {
        //获取解密钥匙
        String cryptKey = EisWebContext.getCryptKey();
        List<SalaryBoardDto> list = salaryBoardDao.querySalaryEmpCR(customerId, organId, cryptKey);

        Map<String, Object> rs = CollectionKit.newMap();
        if (list.size() <= 0) {
            return null;
        }
        rs.put("date", list.get(0).getYear().substring(0, 4) + "年" + Integer.parseInt(list.get(0).getYear().substring(4, 6)) + "月");
        rs.put("list", list);
        return rs;
    }

    /**
     * 员工CR值分页
     */
    @Override
    public PaginationDto<SalaryEmpCRDto> findSalaryEmpCR(String organId, PaginationDto<SalaryEmpCRDto> dto, String sidx,
                                                         String sord, String customerId) {
        //获取解密钥匙
        String cryptKey = EisWebContext.getCryptKey();
        Map<String, Object> map = CollectionKit.newMap();
        map.put("organId", organId);
        map.put("customerId", customerId);
        map.put("cryptKey", cryptKey);
        int count = salaryBoardDao.findSalaryEmpCRCount(map);
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        map.put("sidx", sidx);
        map.put("sord", sord);
        List<SalaryEmpCRDto> dtos = salaryBoardDao.findSalaryEmpCR(map, rowBounds);

        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 登录人所有数据权限ID集	by jxzhang
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

    /**
     * 工资统计
     */
    @Override
    public Map<String, Object> getSalaryWageStatistics(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        SalaryBoardDto dto = salaryBoardDao.querySalarySumThisYear(customerId, organId, year);
        if (null == dto) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("wages", dto.getSumSalary());//工资总额
        rs.put("wageShare", dto.getSumSalary() / dto.getSumPay() * 100);//工资占薪酬比
        return rs;
    }

    /**
     * 工资总额月度趋势
     */
    @Override
    public List<SalaryWageDto> getSalaryWagesMonth(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryWageDto> list = salaryBoardDao.querySalaryWagesMonth(customerId, organId, year);
        return list;
    }

    /**
     * 工资总额及占薪酬比年度趋势
     */
    @Override
    public List<SalaryWageDto> getSalaryWagesYear(String customerId, String organId) {
        List<SalaryWageDto> list = salaryBoardDao.querySalaryWagesYear(customerId, organId);
        return list;
    }

    /**
     * 下级组织工资对比
     */
    @Override
    public Map<String, Object> getSalarySubOrgWages(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryWageDto> list = salaryBoardDao.querySalarySubOrgWages(customerId, organId, year);
        if (list.size() <= 0) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 7).replaceAll("-", "."));
        rs.put("list", list);
        return rs;
    }

    /**
     * 工资结构
     */
    @Override
    public Map<String, Object> getSalaryWageStructure(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
       // List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        List<SalaryWageDto> list = salaryBoardDao.querySalaryWageStructure(customerId, organId, year, subOrganIdList);
        if (list.size() <= 0) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 7).replaceAll("-", "."));
        rs.put("list", list);
        return rs;
    }

    /**
     * 固定与浮动薪酬比
     */
    @Override
    public Map<String, Object> getSalaryFixedProportion(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//        List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        SalaryWageDto dto = salaryBoardDao.querySalaryFixedProportion(customerId, organId, year, subOrganIdList);
        Map<String, Object> rs = CollectionKit.newMap();
        if (null == dto) {
            return null;
        }
        rs.put("fixed", dto.getFixed());
        rs.put("flot", 1-dto.getFixed());
        return rs;
    }

    /**
     * 职位序列固浮比统计
     */
    @Override
    public Map<String, Object> getSalarySequenceFixed(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
//        List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        List<SalaryWageDto> list = salaryBoardDao.querySalarySequenceFixed(customerId, organId, year, subOrganIdList);
        if (list.size() <= 0) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 7).replaceAll("-", "."));
        rs.put("list", list);
        return rs;
    }

    /**
     * 职位层级固浮比统计
     */
    @Override
    public List<SalaryWageDto> getSalaryAbilityFixed(String customerId, String positionId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
//        List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        List<SalaryWageDto> list = salaryBoardDao.querySalaryAbilityFixed(customerId, positionId, organId, year, subOrganIdList);
        return list;
    }

    /**
     * 下级组织固浮比统计
     */
    @Override
    public Map<String, Object> getSalarySubOrgFixed(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
//        List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        List<SalaryWageDto> list = salaryBoardDao.querySubOrganization(customerId, organId);
        List<SalaryWageDto> swList = CollectionKit.newList();
        for(int i=0;i<list.size();i++){
        	SalaryWageDto dto = salaryBoardDao.querySalaryFixedProportion(customerId, list.get(i).getOrganizationId(), year, subOrganIdList);
        	if(dto != null) {
        		dto.setFlot(1-dto.getFixed());
            	dto.setOrganizationName(list.get(i).getOrganizationName());
            	swList.add(dto);
        	}
        }
        if (swList.size() <= 0) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 7).replaceAll("-", "."));
        rs.put("list", swList);
        return rs;
    }

    /**
     * 下级组织固浮比统计列表
     */
    @Override
    public List<SalaryWageDto> getSalarySubOrgFixedList(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
      //  List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        List<SalaryWageDto> list = salaryBoardDao.querySubOrganization(customerId, organId);
        List<SalaryWageDto> swList = CollectionKit.newList();
        for(int i=0;i<list.size();i++){
        	SalaryWageDto dto = salaryBoardDao.querySalaryFixedProportion(customerId, list.get(i).getOrganizationId(), year, subOrganIdList);
        	if(dto != null) {
        		dto.setFlot(1-dto.getFixed());
            	dto.setFlotSalary(dto.getSalaryValue()-dto.getFixedSalary());
            	dto.setOrganizationName(list.get(i).getOrganizationName());
            	swList.add(dto);
        	}
        }
        return swList;
    }

    /**
     * 年终奖金总额与利润的年度趋势
     */
    @Override
    public List<SalaryWageDto> getSalaryBonusProfit(String customerId, String organId) {
        List<SalaryWageDto> list = salaryBoardDao.querySalaryBonusProfit(customerId, organId);
        return list;
    }

    /**
     * 年终奖金总额与利润的年度列表
     */
    @Override
    public List<SalaryWageDto> getSalaryBonusProfitList(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryWageDto> list = salaryBoardDao.querySalaryBonusProfitList(customerId, organId, year);
        return list;
    }

    /**
     * 福利费用统计
     */
    @Override
    public Map<String, Object> getSalaryWelfare(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        SalaryBoardDto dto = salaryBoardDao.querySalaryWelfare(customerId, organId, year);
        if (dto == null) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("wages", dto.getSumWelfare());//福利总额
        rs.put("wageShare", dto.getSumWelfare() / dto.getSumPay()*100);//福利占薪酬比
        return rs;
    }

    /**
     * 福利费用月度趋势
     */
    @Override
    public List<SalaryWelfareDto> getSalaryWelfareMonth(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryWelfareDto> list = salaryBoardDao.querySalaryWelfareMonth(customerId, organId, year);
        return list;
    }

    /**
     * 福利费用及占薪酬比年度趋势
     */
    @Override
    public List<SalaryWelfareDto> getSalaryWelfareYear(String customerId, String organId) {
        List<SalaryWelfareDto> list = salaryBoardDao.querySalaryWelfareYear(customerId, organId);
        return list;
    }

    /**
     * 下级组织福利对比
     */
    @Override
    public Map<String, Object> getSalarySubOrgWelfare(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryWelfareDto> list = salaryBoardDao.querySalarySubOrgWelfare(customerId, organId, year);
        if (list.size() <= 0) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 7).replaceAll("-", "."));
        rs.put("list", list);
        return rs;
    }

    /**
     * 下级组织平均福利对比
     */
    @Override
    public Map<String, Object> getSalarySubOrgAvgWelfare(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryWelfareDto> list = salaryBoardDao.querySalarySubOrgAvgWelfare(customerId, organId, year);
        if (list.size() <= 0) {
            return null;
        }
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 7).replaceAll("-", "."));
        rs.put("list", list);
        return rs;
    }

    /**
     * 国家固定福利
     */
    @Override
    public List<SalaryWelfareDto> getSalaryFixedBenefits(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        
//        String quit="0";
//        int empCount = salaryBoardDao.queryEmpCount(customerId, organId,quit);
//        List<SalaryWelfareDto> list = salaryBoardDao.querySalaryFixedBenefits(customerId, organId, year, empCount);
        
        // by jxzhang on 2016-08-09
        List<SalaryWelfareDto> list = salaryBoardDao.querySalaryFixedBenefits2(customerId, organId, year);
        return list;
    }

    /**
     * 国家固定福利明细
     */
    @Override
    public PaginationDto<SalaryWelfareDto> findSalaryBenefitsDetailed(String customerId, String organId, String keyName, String welfareKey, String yearMonth, PaginationDto<SalaryWelfareDto> dto) {
    	// by jxzhang 2016-07-26
        int yearHead = DateUtil.getYearHead();
        int yearLast = DateUtil.getYearLast();
        
        String year = DateUtil.getDBNow().substring(0, 4);
//        RowBounds rb = new RowBounds(dto.getOffset(), dto.getLimit());
//        List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        Map<String, Object> map = CollectionKit.newMap();
        map.put("keyName", keyName);
        map.put("organId", organId);
        map.put("customerId", customerId);
        map.put("welfareKey", welfareKey);
        map.put("yearHead", yearHead);
        map.put("yearLast", yearLast);
        map.put("year", year);
        map.put("subOrganIdList", subOrganIdList);
        map.put("yearMonth", yearMonth);
        map.put("limit", dto.getLimit());
        map.put("offset", dto.getOffset());
        // TODO 硬编码总数 
        int count = salaryBoardDao.findSalaryBenefitsDetailedCount(map);
        dto.setRecords(count);
        List<SalaryWelfareDto> dtos = salaryBoardDao.findSalaryBenefitsDetailed(map);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 企业福利货币
     */
    @Override
    public List<SalaryWelfareDto> getSalaryBenefitsCurrency(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
//        String quit="0";
//        int empCount = salaryBoardDao.queryEmpCount(customerId, organId,quit);
//        List<SalaryWelfareDto> list = salaryBoardDao.querySalaryBenefitsCurrency(customerId, organId, year, empCount);
        
        // by jxzhang on 2016-08-09
        List<SalaryWelfareDto> list = salaryBoardDao.querySalaryBenefitsCurrency2(customerId, organId, year);
        return list;
    }

    /**
     * 企业福利货币明细
     */
    @Override
    public PaginationDto<SalaryWelfareDto> findSalaryCurrencyDetailed(String customerId, String organId, String keyName,
                                            	String welfareKey, String yearMonth, PaginationDto<SalaryWelfareDto> dto) {
		// by jxzhang 2016-07-26
        int yearHead = DateUtil.getYearHead();
        int yearLast = DateUtil.getYearLast();
        
        String year = DateUtil.getDBNow().substring(0, 4);
//        List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        Map<String, Object> map = CollectionKit.newMap();
        map.put("keyName", keyName);
        map.put("organId", organId);
        map.put("customerId", customerId);
        map.put("welfareKey", welfareKey);
        map.put("yearHead", yearHead);
        map.put("yearLast", yearLast);
        map.put("year", year);
        map.put("subOrganIdList", subOrganIdList);
        map.put("yearMonth", yearMonth);
        map.put("offset", dto.getOffset());
        map.put("limit", dto.getLimit());
//        RowBounds rb = new RowBounds(dto.getOffset(), dto.getLimit());
        
        // TODO 硬编码总数 
        int count = salaryBoardDao.findSalaryCurrencyDetailedCount(map);
        List<SalaryWelfareDto> dtos = salaryBoardDao.findSalaryCurrencyDetailed(map);
        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 企业福利非货币
     */
	@Override
    public List<SalaryWelfareDto> getSalaryBenefitsNoCurrency(String customerId, String organId) {

		String year = DateUtil.getDBNow().substring(0, 4);
		// by jxzhang 2016-07-26
        int yearHead = DateUtil.getYearHead();
        int yearLast = DateUtil.getYearLast();
//        List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        String quit="0";
        int empCount = salaryBoardDao.queryEmpCount(customerId, organId, quit);
//        List<SalaryWelfareDto> list = salaryBoardDao.querySalaryBenefitsNoCurrency(customerId, organId, year, empCount);
    	List<SalaryWelfareDto> list =new Sort<SalaryWelfareDto>().
				desc(salaryBoardDao.querySalaryBenefitsNoCurrency(customerId, organId, yearHead,yearLast, empCount, subOrganIdList));
    	return list;
    }

    /**
     * 企业福利非货币明细
     */
    @Override
    public PaginationDto<SalaryWelfareDto> findSalaryNoCurrencyDetailed(String customerId, String organId, String keyName,
                                               String welfareKey, String yearMonth, PaginationDto<SalaryWelfareDto> dto) {
		// by jxzhang 2016-07-26
        int yearHead = DateUtil.getYearHead();
        int yearLast = DateUtil.getYearLast();
        
//        String year = DateUtil.getDBNow().substring(0, 4);
//        List<String> subOrganIdList = EisWebContext.getSubOrganIdList(organId);
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        Map<String, Object> map = CollectionKit.newMap();
        map.put("keyName", keyName);
        map.put("organId", organId);
        map.put("customerId", customerId);
        map.put("welfareKey", welfareKey);
        map.put("yearHead", yearHead);
        map.put("yearLast", yearLast);
        map.put("yearMonth", yearMonth);
        map.put("subOrganIdList", subOrganIdList);
        map.put("offset", dto.getOffset());
        map.put("limit", dto.getLimit());
//        RowBounds rb = new RowBounds(dto.getOffset(), dto.getLimit());
        // TODO 硬编码总数 
        int count = salaryBoardDao.findSalaryNoCurrencyDetailedCount(map);
        List<SalaryWelfareDto> dtos = salaryBoardDao.findSalaryNoCurrencyDetailed(map);
        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 持股统计
     */
    @Override
    public SalarySharesDto getSalaryShares(String customerId, String organId) {
    	String year = DateUtil.getDBNow().substring(0, 4);
    	String quit="0";
        int empCount = salaryBoardDao.queryEmpCount(customerId, organId,quit);
        return salaryBoardDao.querySalaryShares(customerId, organId, empCount,year);
    }

    /**
     * 持股员工总数年度趋势
     */
    @Override
    public List<SalarySharesDto> getSalaryEmpShares(String customerId, String organId) {
        List<SalarySharesDto> list = salaryBoardDao.querySalaryEmpShares(customerId, organId);
        return list;
    }

    /**
     * 持股数量年度趋势
     */
    @Override
    public List<SalarySharesDto> getSalarySumShares(String customerId, String organId) {
        List<SalarySharesDto> list = salaryBoardDao.querySalarySumShares(customerId, organId);
        return list;
    }

    /**
     * 下级组织持股员工数
     */
    @Override
    public List<SalarySharesDto> getSalarySubOrgShares(String customerId, String organId) {
    	String year = DateUtil.getDBNow().substring(0, 4);
        List<SalarySharesDto> list = salaryBoardDao.querySalarySubOrgShares(customerId, organId,year);
        return list;
    }

    /**
     * 下级组织持股数量
     */
    @Override
    public List<SalarySharesDto> getSalarySubOrgSumShares(String customerId, String organId) {
    	String year = DateUtil.getDBNow().substring(0, 4);
        List<SalarySharesDto> list = salaryBoardDao.querySalarySubOrgSumShares(customerId, organId,year);
        return list;
    }

    /**
     * 员工股票期权
     *
     * @param keyName
     * @param organId
     * @param dto
     * @param sidx
     * @param sord
     * @param customerId
     * @return
     */
    @Override
    public PaginationDto<SalaryEmpSharesDto> findSalaryEmpShares(String keyName, String organId,
                                                                 PaginationDto<SalaryEmpSharesDto> dto, String sidx, String sord, String customerId) {
        List<String> organPermitIds = getOrganPermitId();
        if (null == organPermitIds || organPermitIds.isEmpty()) {
            return null;
        }
        Map<String, Object> map = CollectionKit.newMap();
        map.put("keyName", keyName);
        map.put("organId", organId);
        map.put("customerId", customerId);
        map.put("organPermitIds", organPermitIds);
        int count = salaryBoardDao.findSalaryEmpSharesCount(map);
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        map.put("sidx", sidx);
        map.put("sord", sord);
        List<SalaryEmpSharesDto> dtos = salaryBoardDao.findSalaryEmpShares(map, rowBounds);

        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 福利类别信息
     *
     * @param customerId
     * @param welfareType
     * @return
     */
    @Override
    public List<SalaryWelfareDto> getSalaryWelfareCategory(String customerId, String welfareType) {
        List<SalaryWelfareDto> list = salaryBoardDao.querySalaryWelfareCategory(customerId, welfareType);
        return list;
    }

    /**
     * 时间
     * @param customerId
     * @return
     */
    @Override
    public List<Integer> getSalaryTime(String customerId) {
    	String year = DateUtil.getDBNow().substring(0, 4);
    	return salaryBoardDao.querySalaryTime(customerId, year);
    }


    /**
     * 固定福利月统计
     */
	@Override
	public void setUpWelfareNfbTotal() {
		//获取customerId内所有组织，获取到所有组织的最近一个月
		
	}


	@Override
	public void proFetchPayCollectYear() {
		String p_key= "hrbi";
		String customerId = "b5c9410dc7e4422c8e0189c7f8056b5f";
		String cureDate = "2015-12-17";
		List<Map<String, String>> resultlist = new ArrayList<Map<String,String>>();
//		String cureDate = DateUtil.dateToStr(new Date(), "YYYY-MM-dd");
		
		//为薪资年汇总表写入（pay_collect）
		salaryboarProDao.callmakePayCollectYear(customerId,"",DateUtil.dateToStr(new DateTime(cureDate).toDate(), "YYYYMM"),p_key);
		//获取某天所有的组织下的总人数集合
		//organizationId,fullPath,empCountSum
		List<Map<String, Object>> dayOfEmpCountList = salaryboarProDao.hisEmpCount(cureDate, customerId);
		for(Map<String, Object> tg: dayOfEmpCountList){
			String day = tg.get("day") + "";
			String empCountSum = tg.get("emp_count_sum") + "";
			String organizationId = tg.get("organization_id") + "";
			String fullPath = tg.get("full_path") + "";

			resultlist.add(CalCenterFetch(day, empCountSum, organizationId, fullPath, customerId));
		}
		
		//更新到pay_collect.quantile_value
		salaryboarProDao.batchUpateQuanValuePayCollect(resultlist);
	}
	/**
	 * 组装结果集，计算薪酬汇总，获取组织某月的中间值quantile_value
	 * @param day
	 * @param empCountSum
	 * @param OrganizationId
	 * @param fullPath
	 * @param customerId
	 */
	private Map<String, String> CalCenterFetch(String day, String empCountSum, String OrganizationId, String fullPath, String customerId){
		//判断非空
		if(empCountSum.equals("0")){
			//更新为零
		}
		double sum = Double.valueOf(empCountSum);
		String p_ym = DateUtil.dateToStr(new DateTime(day).toDate(), "YYYYMM");
		String limitStart = "";
		//结果集
		Map<String, String> resultmap = new HashMap<String, String>();
		resultmap.put("orgid", OrganizationId);
		resultmap.put("yearmonth", p_ym);
		resultmap.put("customerid", customerId);
		if(sum % 2 == 1){
			limitStart = (sum / 2 - 0.5) + "";
		}
		else{
			limitStart = (sum / 2) + "";
		}
		resultmap.put("centerPay", salaryboarProDao.findCenterPayShould(customerId, p_ym, fullPath, limitStart.substring(0, limitStart.indexOf('.')), "even")+"");
		return resultmap;
		
	}
    
}
