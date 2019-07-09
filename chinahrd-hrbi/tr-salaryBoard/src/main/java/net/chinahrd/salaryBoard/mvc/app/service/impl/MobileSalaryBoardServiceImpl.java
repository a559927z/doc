package net.chinahrd.salaryBoard.mvc.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryEmpCRDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryWageDto;
import net.chinahrd.salaryBoard.mvc.app.dao.MobileSalaryBoardDao;
import net.chinahrd.salaryBoard.mvc.app.service.MobileSalaryBoardService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 薪酬看板 实现
 *
 * @author hjli
 */
@Service("mobileSalaryBoardService")
public class MobileSalaryBoardServiceImpl implements MobileSalaryBoardService {
	  @Autowired
	  private MobileSalaryBoardDao mobileSalaryBoardDao;
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
	        SalaryBoardDto dto1 = mobileSalaryBoardDao.querySalarySumThisYear(customerId, organId, year);
	        //查询人力成本总费用
	        SalaryBoardDto dto2 = mobileSalaryBoardDao.queryCostTotal(customerId, organId, year);
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
	        SalaryBoardDto dto = mobileSalaryBoardDao.queryIncomeExpenditureYear(customerId, organId, year);
	        //查询薪酬福利费用
	        SalaryBoardDto dto1 = mobileSalaryBoardDao.querysalaryWelfareYear(customerId, organId, year);
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
        SalaryBoardDto dto = mobileSalaryBoardDao.querySalaryBudgetYear(customerId, organId, year);
        if (dto != null) {
            rs.put("payValue", dto.getPayValue());
        } else {
            rs.put("payValue", null);
        }
        //累计
        SalaryBoardDto dto1 = mobileSalaryBoardDao.querySalarySumThisYear(customerId, organId, year);
        //薪酬月份统计
        if (dto1 != null) {
            rs.put("sumPay", dto1.getSumPay());
        } else {
            rs.put("sumPay", null);
        }
        List<SalaryBoardDto> list = mobileSalaryBoardDao.querySalaryPayTotal(customerId, organId, year);
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
        List<SalaryBoardDto> list = mobileSalaryBoardDao.querySalarySubOrganization(customerId, organId, year);
        return list;
    }
    
    /**
     * 人力资本投资回报率月度趋势
     */
    @Override
    public List<SalaryBoardDto> getSalaryMonthRateOfReturn(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryBoardDto> list = mobileSalaryBoardDao.querySalaryMonthRateOfReturn(customerId, organId, year);
        return list;
    }
    
    /**
     * 组织KPI达标率、人力成本、薪酬总额的年度趋势
     */
    @Override
    public List<SalaryBoardDto> getSalaryCostKpi(String customerId, String organId) {
        List<SalaryBoardDto> list = mobileSalaryBoardDao.querySalaryCostKpi(customerId, organId);
        return list;
    }
    
    /**
     * 营业额、利润、人力成本及薪酬总额的月度趋势
     */
    @Override
    public List<SalaryBoardDto> getSalaryCostSalesProfit(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<SalaryBoardDto> list = mobileSalaryBoardDao.querySalaryCostSalesProfit(customerId, organId, year);
        return list;
    }
    
    
    /**
     * 行业分位值年度趋势
     */
    @Override
    public List<SalaryBoardDto> getSalaryBitValueYear(String customerId, String organId) {
        List<SalaryBoardDto> bitList = new ArrayList<SalaryBoardDto>();
        //获取组织年度人均薪酬
        List<SalaryBoardDto> avgList = mobileSalaryBoardDao.querySalaryAvgPayList(customerId, organId);
        for (int i = 0; i < avgList.size(); i++) {
            SalaryBoardDto avgDto = new SalaryBoardDto();
            //获取组织的分位值范围
            List<SalaryBoardDto> list = mobileSalaryBoardDao.querySalaryBitValueYear(customerId, organId, avgList.get(i).getYear());
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
     * 员工CR值
     */
    @Override
    public Map<String, Object> getSalaryEmpCR(String customerId, String organId) {
        //获取解密钥匙
        String cryptKey = EisWebContext.getCryptKey();
        List<SalaryBoardDto> list = mobileSalaryBoardDao.querySalaryEmpCR(customerId, organId, cryptKey);

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
        int count = mobileSalaryBoardDao.findSalaryEmpCRCount(map);
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        map.put("sidx", sidx);
        map.put("sord", sord);
        List<SalaryEmpCRDto> dtos = mobileSalaryBoardDao.findSalaryEmpCR(map, rowBounds);

        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }
    
    /**
     * 薪酬差异度岗位表
     */
    @Override
    public PaginationDto<SalaryBoardDto> getSalaryDifferencePost(String customerId, String organId, int page, int rows) {
        String year = DateUtil.getDBNow().substring(0, 4);
        //获取解密钥匙
        String cryptKey = EisWebContext.getCryptKey();
/*        List<SalaryBoardDto> list = mobileSalaryBoardDao.querySalaryDifferencePost(customerId, organId, year, cryptKey);
        Map<String, Object> rs = CollectionKit.newMap();
        if (list.size() <= 0) {
            return null;
        }
        rs.put("date", list.get(0).getYear().substring(0, 4) + "年" + Integer.parseInt(list.get(0).getYear().substring(4, 6)) + "月");
        rs.put("list", list);
        */
        
    		PaginationDto<SalaryBoardDto> dto = new PaginationDto<SalaryBoardDto>(page, rows);
    		Map<String, Object> map = CollectionKit.newMap();
    		map.put("customerId", customerId);
    		map.put("organId", organId);
    		map.put("year", year);
    		map.put("cryptKey", cryptKey);
    		map.put("start", dto.getOffset());
    		map.put("rows", dto.getLimit());
    		int count =mobileSalaryBoardDao.querySalaryDifferencePostCount(map);
            List<SalaryBoardDto> dtoList = mobileSalaryBoardDao.querySalaryDifferencePost(map);
    		int records = count > 0 ? count : 1;
    		dto.setRecords(records);
    		dto.setRows(dtoList);
    	return dto;
    }
    
    /**
     * 年终奖金总额与利润的年度趋势
     */
    @Override
    public List<SalaryWageDto> getSalaryBonusProfit(String customerId, String organId) {
        List<SalaryWageDto> list = mobileSalaryBoardDao.querySalaryBonusProfit(customerId, organId);
        return list;
    }
}
