package net.chinahrd.benefit.mvc.app.service.impl;

import java.util.List;

import net.chinahrd.benefit.mvc.app.dao.MobilePerBenefitDao;
import net.chinahrd.benefit.mvc.app.service.MobilePerBenefitService;
import net.chinahrd.entity.dto.app.benefit.BenefitDto;
import net.chinahrd.entity.dto.app.common.ResultTimeDto;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  人均效益service
 * @author htpeng
 *2016年3月30日下午5:30:59
 */
@Service("mobilePerBenefitService")
public class MobilePerBenefitServiceImpl implements MobilePerBenefitService {

	@Autowired
	private MobilePerBenefitDao mobilePerBenefitDao;

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobilePerBenefitService#getPerBenefitData(java.lang.String, java.lang.String)
	 */
	@Override
	public BenefitDto getPerBenefitData(String customerId,
			String organizationId,String year) {
		List<BenefitDto> list = mobilePerBenefitDao.queryTrendByMonth(customerId,organizationId,year, 1);
		if(null!=list&&list.size()>0){
        	return list.get(0);
        }  
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobilePerBenefitService#getAvgValueData(java.lang.String, java.lang.String)
	 */
	@Override
	public Integer getAvgValueData(String customerId, String organizationId) {
		// TODO Auto-generated method stub
		return mobilePerBenefitDao.queryAvgValue(customerId,organizationId);
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobilePerBenefitService#getPerBenefitInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<BenefitDto> getPerBenefitInfo(String customerId, String organId,String year) {

		List<BenefitDto> trendData = mobilePerBenefitDao.queryTrendByMonth(customerId,organId,year, 6);
		List<BenefitDto> orderList=CollectionKit.newList();
		for(BenefitDto p:trendData){
			orderList.add(0, p);
		}
		return orderList;
	}
	
	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobilePerBenefitService#getTrendByMonth(java.lang.String, java.lang.String)
	 */
	@Override
	public List<BenefitDto> getTrendByMonth(String customerId,
			String organizationId,String year) {
		
		List<BenefitDto> trendData = mobilePerBenefitDao.queryTrendByMonth(customerId,organizationId, year, 7);
//		List<PerBenefitDto> orderList=CollectionKit.newList();
//		for(PerBenefitDto p:trendData){
//			orderList.add(0, p);
//		}
		return trendData;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobilePerBenefitService#getOrgBenefitData(java.lang.String, java.lang.String)
	 */
	@Override
	public List<BenefitDto> getOrgBenefitData(String customerId,
			String organizationId) {
//		List<BenefitDto>  order=new Sort<BenefitDto>().asc(mobilePerBenefitDao.queryOrgBenefit(customerId,organizationId))
		
		return  mobilePerBenefitDao.queryOrgBenefit(customerId,organizationId);
	}

    private ResultTimeDto hand(String startDate,ResultTimeDto resultTimeDto){
    	return resultTimeDto;
    }

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.MobilePerBenefitService#queryQuarterLastDay(java.lang.String)
	 */
	@Override
	public String queryLastMonth(String customerId) {
		// TODO Auto-generated method stub
		return mobilePerBenefitDao.queryLastMonth(customerId);
	}
}
