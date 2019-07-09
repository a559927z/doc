package net.chinahrd.dismissRisk.mvc.pc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskReviewDto;
import net.chinahrd.entity.dto.pc.common.RiskDto;
import net.chinahrd.entity.dto.pc.common.RiskTreeDto;

/**
 * 人才流失风险dao
 */
@Repository("dismissRiskDao")
public interface DismissRiskDao {

    /**
     * 查询下级组织员工信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<DismissRiskDto> querySubOrganEmpInfo(@Param("empId") String empId, @Param("customerId") String customerId);

    /**
     * 获取直接下属有离职风险的人员数据
     *
     * @param customerId
     * @param empId      当前登录人id
     * @return
     */
    List<DismissRiskDto> queryDirectRisk(@Param("customerId") String customerId, @Param("empId") String empId);

    /**
     * 查询离职预测回归分析-员工信息
     *
     * @param customerId
     * @param empId
	 * @param period
     * @return
     */
    List<DismissRiskReviewDto> queryDimissRissReviewInfo(@Param("customerId") String customerId,  @Param("empId") String empId, @Param("period") int period);

	/**
	 *	查询员工的离职风险树
	 */
	List<RiskTreeDto> queryEmpRiskDetail(@Param("customerId") String customerId, @Param("empid") String empId);

	List<RiskDto> queryEmpAllRiskDetail(@Param("customerId")String customerId, @Param("empId")String empId);

	/**
	 *	默认评估的离职风险树
	 */
	List<RiskTreeDto> queryEmpRiskDefaultDetail(@Param("customerId") String customerId);

	/**
	 * 查询当前一天有离职风险更新为红灯或者黄灯员工Id
	 * @param customerId
	 * @param condStr	红灯或黄灯 (1,2)
	 * @return
	 */
	List<String> queryRiskEmpIds(@Param("customerId") String customerId, @Param("condList") List<String> condList);
}
