package net.chinahrd.benefit.mvc.app.dao;

import java.util.List;

import net.chinahrd.entity.dto.app.benefit.BenefitDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 人均效益dao
 * @author htpeng
 *2016年3月30日下午5:30:02
 */
@Repository("mobilePerBenefitDao")
public interface MobilePerBenefitDao {

	/**
	 * 查询人均效益数据（按月统计）
	 * @param organizationId 架构id
	 * @param limitNum 要查的月份个数
	 * @return
	 */
	List<BenefitDto> queryTrendByMonth(@Param("customerId") String customerId,@Param("organizationId")String organizationId,@Param("year")String year, @Param("limitNum")int limitNum);

	/**
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	Integer queryAvgValue(@Param("customerId")String customerId, @Param("organizationId")String organizationId);

	/**
	 * 查询"当前组织人均效益"（包含子节点为独立核算的部门）,结果集已按照level排序
	 * @param organizationId 当前架构id
	 * @param beginYearMonth 数据开始日期
	 * @param endYearMonth 数据结束日期
	 * @return 当前架构的人均效益相关数据和其子架构（具有独立核算资格）的人均效益相关数据
	 */
	List<BenefitDto> queryOrgBenefit(@Param("customerId") String customerId,@Param("organizationId")String organizationId);

	/**
	 * @param customerId
	 * @return
	 */
	String queryLastMonth(@Param("customerId")String customerId);
	
}
