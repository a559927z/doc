package net.chinahrd.benefit.mvc.app.service;

import java.util.List;

import net.chinahrd.entity.dto.app.benefit.BenefitDto;



/**
 * 
 * @author htpeng
 *2016年3月30日下午5:31:39
 */
public interface MobilePerBenefitService {


   

	public BenefitDto getPerBenefitData(String customerId,
			String organizationId,String year);

	/**
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	public Integer getAvgValueData(String customerId, String organizationId);

	/**
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	public List<BenefitDto> getPerBenefitInfo(String customerId, String organizationId,String year);

	/**
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	public List<BenefitDto> getTrendByMonth(String customerId,
			String organizationId,String year);

	/**
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	public List<BenefitDto> getOrgBenefitData(String customerId,
			String organizationId);

	/**
	 * @param customerId
	 * @return
	 */
	public String queryLastMonth(String customerId);

}
