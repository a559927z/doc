package net.chinahrd.manpowerCost.mvc.pc.dao;

import java.util.List;
import java.util.Map;







import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerItemDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerOrganDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 人力成本Dao
 * @author htpeng
 *2015年12月30日上午11:25:54
 */

@Repository("manpowerCostDao")
public interface ManpowerCostDao {

	/**
	 * 成本和人均成本（按年同比）
	 * @param customerId
	 * @param organId
	 * @param year1
	 * @param year2
	 * @return
	 */
	List<ManpowerCompareDto> queryCompareYear(Map<String,String> map );
	
	
	/**
	 * 成本和人均成本（按月环比）
	 * @param customerId
	 * @param organId
	 * @param yearMonth1
	 * @param yearMonth2
	 * @return
	 */
	List<ManpowerCompareDto> queryCompareMonth(@Param("customerId")String customerId, 
			@Param("organId")String organId,@Param("yearMonth1")String yearMonth1,
			@Param("yearMonth2")String yearMonth2);

	
	/**
	 * 成本月度趋势
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<ManpowerDto> queryTrendByMonth(@Param("customerId")String customerId, 
			@Param("organId")String organId,@Param("year")int year);
	
	/**
	 * 年度预算
	 * @param customerId
	 * @param organId
	 * @param yearMonth
	 * @return
	 */
	Double queryYearBudget(@Param("customerId")String customerId, 
			@Param("organId")String organId,@Param("year")int year);
	
	/**
	 * 人均成本月度趋势
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<ManpowerDto> queryAvgTrendByMonth(@Param("customerId")String customerId, 
			@Param("organId")String organId,@Param("year")int year);
	
	
	/**
	 * 人力成本结构
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<ManpowerItemDto> queryItemDetail(@Param("customerId")String customerId, 
			@Param("organId")String organId,@Param("year")int year);
	
	/**
	 * 各架构人力成本
	 * @param customerId
	 * @param organId
	 * @param year
	 * @return
	 */
	List<ManpowerOrganDto> queryOrganCost(@Param("customerId")String customerId, 
			@Param("organId")String organId,@Param("year")int year);
	
	
	
	
	/**
	 * 人力成本占比（按月环比）
	 * @param customerId
	 * @param organId
	 * @param yearMonth1
	 * @param yearMonth2
	 * @return
	 */
	List<ManpowerCompareDto> queryProportionYear(@Param("customerId")String customerId,
			@Param("organId")String organId,@Param("year1")String yearMonth1,
			@Param("year2")String yearMonth2);
	
	/**
	 * 人力成本占比（按年同比）
	 * @param customerId
	 * @param organId
	 * @param year1
	 * @param year2
	 * @return
	 */
	List<ManpowerCompareDto> queryProportionMonth(@Param("customerId")String customerId, 
			@Param("organId")String organId,@Param("yearMonth1")String yearMonth1,
			@Param("yearMonth2")String yearMonth2);
	
	/**
	 * 行业均值
	 * @param customerId
	 * @param organId
	 * @return
	 */
	Double queryAvgValue(@Param("customerId")String customerId, @Param("organId")String organId);

	/**
	 * 销售 成本  利润 明细
	 * @param customerId
	 * @param organId
	 * @return
	 */
	List<ManpowerDto> queryAllDetailData(@Param("customerId")String customerId,
			@Param("organId")String organId, @Param("year")int year);
	
	

}
