/**
*net.chinahrd.biz.paper.mobile.dao
*/
package net.chinahrd.mvc.app.dao.home;

import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.entity.dto.app.talent.structure.TalentstructureDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTrendDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author htpeng
 *2016年7月14日下午4:14:17
 */
@Repository("mobileHomeDao")
public interface MobileHomeDao {

	/**
	 * @param customerId
	 * @param empId
	 * @return
	 */
	public List<KanbanConfigMobileDto> getAlreadyAddedList(@Param("customerId")String customerId,
			@Param("empId")String empId) ;

	/**
	 * @param customerId
	 * @param empId
	 * @return
	 */
	public List<KanbanConfigMobileDto> getFunctionList(@Param("customerId")String customerId) ;

	
	/**
	 * @param customerId
	 * @return
	 */
	public String queryQuarterLastDay(@Param("customerId")String customerId) ;

	/**
	 * 主动流失率
	 * @param organId
	 * @param time
	 * @param customerId
	 * @return
	 */
	public List<DismissTrendDto> queryDisminss(@Param("organId")String organId, @Param("quarterLastDay")String time,
			@Param("customerId")String customerId);

	
	/**关键人才库  离职预警人数
	 * @param organId
	 * @param time
	 * @param customerId
	 * @return
	 */
	public Integer queryRunoffRiskCount(@Param("customerId")String customerId,@Param("list")List<String> organPermitIds);

	/**人员结构  可用编制数
	 * @param organId
	 * @param time
	 * @param customerId
	 * @return
	 */
	public TalentstructureDto findBudgetAnalyse(@Param("organId") String organId,
			@Param("customerId") String customerId, @Param("now") String now,
			@Param("personTypeKey") List<Integer> personTypeKey);

	/**团队画像  总人数
	 * @param organId
	 * @param time
	 * @param customerId
	 * @return
	 */
	public Integer queryTeamImgEmpCount(@Param("organId")String organId, @Param("customerId") String customerId);

	/**人均效益
	 * @param organId
	 * @param time
	 * @param customerId
	 * @return
	 */
	public String queryBenfitLastMonth(@Param("customerId")String customerId);
	public Double queryBenefitValue(@Param("customerId") String customerId,@Param("organId")String organId,@Param("year")String year);

	
	/**员工绩效  低绩效
	 * @param organId
	 * @param time
	 * @param customerId
	 * @return
	 */
	public Integer queryPreYearMonth(@Param("customerId")String customerId,@Param("perWeek")int perWeek);
	public Integer queryLowPerCount(@Param("customerId")String customerId,
			@Param("organId")String organId,
			@Param("yearMonth")int yearMonth,
			@Param("preWeek")int preWeek,
			@Param("lowL")int lowL,
			@Param("lowH")int lowH);

	
	/**人力成本 执行率
	 * @param organId
	 * @param time
	 * @param customerId
	 * @return
	 */
	public List<Double> queryYearTotalAndBudget(@Param("customerId")String customerId,@Param("organId")String organId,@Param("year")int year);

	
	/**培训看板 执行率
	 * @param organId
	 * @param time
	 * @param customerId
	 * @return
	 */
	public Double queryTrainRate(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") int year);

	/**
	 * @param customerId
	 * @param empId
	 * @param name
	 * @return
	 */
	public List<KanbanConfigMobileDto> getQuotaByName(@Param("customerId")String customerId,@Param("name")String name);

}
