package net.chinahrd.dismissRisk.mvc.pc.service;

import java.util.List;

import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskReviewDto;
import net.chinahrd.entity.dto.pc.common.RiskDto;
import net.chinahrd.entity.dto.pc.common.RiskTreeDto;

/**
 *	人才流失风险Controller
 */
public interface DismissRiskService {

	public final static String CFG_PREFIX = "RCLSFX-";

	public final static String RISK_WARNING_PERIOD = CFG_PREFIX + "hasMessage";

	/**
	 * 获取直接下属有离职风险的人员数据
	 * @param customerId
	 * @param empId 当前登录人id
	 * @return
	 */
	List<DismissRiskDto> getDirectRiskData(String customerId,String empId);

	/**
	 * 查询下级组织员工信息
	 * @param empId
	 * @param customerId
	 * @return
	 */
	List<DismissRiskDto> querySubOrganEmpInfo(String empId,String customerId);

	/**
	 *	查询离职预测回归分析-员工信息
	 * @param customerId
	 * @param empId TODO
	 * @return
	 */
	List<DismissRiskReviewDto> queryDimissRissReviewInfo(String customerId, String empId);

	/**
	 *	查询员工的离职风险树
	 */
	List<RiskTreeDto> getEmpRiskDetail(String customerId,String empId);

	 /**
     * 查询离职风险历史数据
     *
     * @return
     */
	List<RiskDto> getEmpAllRiskDetail(String customerId, String empId);

	/**
	 *	默认评估的离职风险树
	 */
	List<RiskTreeDto> queryEmpRiskDefaultDetail(String customerId);

	/**
	 * 查询符合条件的离职风险员工信息
	 * @param customerId
	 * @param condStr	条件：risk_flag 1:红,2:黄,3:绿,逗号隔开
	 * @return
	 */
	List<String> queryRiskEmpIds(String customerId, String condStr);
}
