package net.chinahrd.benefit.mvc.pc.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.chinahrd.benefit.mvc.pc.dao.PerBenefitDao;
import net.chinahrd.benefit.mvc.pc.service.PerBenefitService;
import net.chinahrd.entity.dto.pc.benefit.PerBenefitAmountDto;
import net.chinahrd.entity.dto.pc.benefit.PerBenefitDto;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("perBenefitService")
public class PerBenefitServiceImpl implements PerBenefitService {

	  @Autowired
	    private PerBenefitDao perBenefitDao;

	  @Override
	    public List<PerBenefitDto> getTrendByMonth(String customerId, String organizationId) {

	        List<PerBenefitDto> trendData = perBenefitDao.queryTrendByMonth(customerId, organizationId, 13);
	        return trendData;
	    }

	    @Override
	    public List<PerBenefitDto> getTrendByYear(String customerId, String organizationId) {
	        return perBenefitDao.queryTrendByYear(customerId, organizationId);
	    }

	    @Override
	    public List<PerBenefitDto> getPerBenefitData(String customerId, String organizationId) {
	        List<PerBenefitDto> trendData = perBenefitDao.queryTrendByMonth(customerId, organizationId, 2);
	        return trendData;
	    }

	    @Override
	    public List<PerBenefitDto> getOrgBenefitData(String customerId, String organizationId) {
	        return perBenefitDao.queryOrgBenefit(customerId, organizationId);
	    }

	    @Override
	    public PerBenefitAmountDto getOrgRecentData(String customerId, String organizationId) {
	        Integer fteMaxDate = perBenefitDao.findFteMaxDate(customerId, organizationId);
	        if (fteMaxDate == null) {
	            return null;
	        }
	        int currBeginYearMonth = DateUtil.getPreYearMonth(fteMaxDate, 11);
	        int yoyEndYearMontth = DateUtil.getPreYearMonth(currBeginYearMonth, 1);
	        int yoyBeginYearMonth = DateUtil.getPreYearMonth(yoyEndYearMontth, 11);

	        Map<String,Object> map = CollectionKit.newMap();
	        map.put("currBeginYearMonth",currBeginYearMonth);
	        map.put("currEndYearMonth",fteMaxDate);
	        map.put("yoyBeginYearMonth",yoyBeginYearMonth);
	        map.put("yoyEndYearMontth",yoyEndYearMontth);
	        map.put("organizationId",organizationId);
	        map.put("customerId",customerId);

	        PerBenefitAmountDto dto = perBenefitDao.queryOrgRecentData(map);
	        return dto;
	    }

	    @Override
	    public Double getAvgValueData(String customerId, String organizationId) {
	        return perBenefitDao.queryAvgValue(customerId, organizationId);
	    }

	    @Override
	    public Map<Integer, List<PerBenefitDto>> queryVariationRange(String customerId, String organizationId,
	                                                                 boolean upgrade) {

	        Integer maxYm = perBenefitDao.findFteMaxDate(customerId, organizationId);
	        if (maxYm == null) {
	            return Collections.emptyMap();
	        }
	        int preYm = DateUtil.getPreYearMonth(maxYm, 1);
	        List<PerBenefitDto> rs = perBenefitDao.queryVariationRange(customerId,
	                organizationId, upgrade, maxYm, preYm);
	        if (CollectionKit.isEmpty(rs)) {
	            return Collections.emptyMap();
	        }
	        //最新月份的数据集合
	        List<PerBenefitDto> newlyList = getNewlyList(maxYm, rs);
	        //对再上一个月的数据，以最新月的机构顺序 进行排序
	        List<PerBenefitDto> preList = getPreList(maxYm, rs, newlyList);
	        Map<Integer, List<PerBenefitDto>> processMap = CollectionKit.newMap();
	        processMap.put(preYm, preList);
	        processMap.put(maxYm, newlyList);
	        return processMap;
	    }

	    /**
	     * 对再上一个月的数据，以最新月的机构顺序 进行排序
	     *
	     * @param maxYm     最新月份yearMonth
	     * @param rs        所有结果集
	     * @param newlyList 最新月份结果集
	     * @return
	     */
	    private List<PerBenefitDto> getPreList(Integer maxYm,
	                                           List<PerBenefitDto> rs, List<PerBenefitDto> newlyList) {
	        List<PerBenefitDto> preList = CollectionKit.newList();
	        for (PerBenefitDto newlyDto : newlyList) {
	            for (PerBenefitDto rsDto : rs) {
	                //当前数据是最新月份，或不等于外层迭代的机构id，则不放进结果集
	                if ((maxYm.equals(rsDto.getYearMonth())) || !(rsDto.getOrganizationId().equals(newlyDto.getOrganizationId()))) {
	                    continue;
	                }
	                preList.add(rsDto);
	                break;
	            }
	        }
	        return preList;
	    }

	    /**
	     * 获取最新月份的数据集合
	     *
	     * @param maxYm 最新月份yearMonth
	     * @param rs    所有结果集
	     * @return
	     */
	    private List<PerBenefitDto> getNewlyList(Integer maxYm,
	                                             List<PerBenefitDto> rs) {
	        //最新月的集合
	        List<PerBenefitDto> newlyList = CollectionKit.newList();
	        //将结果集为最新月份的数据提取出来
	        for (PerBenefitDto perBenefitDto : rs) {
	            if (maxYm.equals(perBenefitDto.getYearMonth())) {
	                newlyList.add(perBenefitDto);
	            }
	        }
	        return newlyList;
	    }

}
