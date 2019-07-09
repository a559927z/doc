package net.chinahrd.mvc.pc.dao.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.RiskDto;
import net.chinahrd.entity.dto.pc.common.RiskItemDto;
import net.chinahrd.entity.dto.pc.laborEfficiency.TheoryAttendanceDto;
import net.chinahrd.utils.holiday.DaysDto;

/**
 * 公共Dao接口类
 */
@Repository("commonDao")
public interface CommonDao {

	/**
	 * 获取人群集合
	 */
	List<KVItemDto> queryPopulations(@Param("customerId") String customerId);

	/**
	 * 获取人才类型
	 */
	List<ItemDto> queryTalentType(String customerId);

	/**
	 * 获取所有序列
	 */
	List<ItemDto> querySequence(String customerId);

	/**
	 * 获取所有子序列
	 */
	List<ItemDto> querySequenceSub(String customerId);

	/**
	 * 获取所有层级
	 */
	List<ItemDto> queryAbility(String customerId);

	/**
	 * 获取所有层级HashMap
	 */
	List<HashMap<String, String>> queryAbilityHashMap(String customerId);

	/**
	 * 获取所有层级
	 */
	List<ItemDto> queryAbilityType(String customerId);

	/**
	 * 获取所有职级HashMap
	 */
	List<HashMap<String, String>> queryAbilityLvHashMap(String customerId);

	/***
	 * 获取岗位信息
	 *
	 * @param positionName
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> queryPositions(@Param("positionName") String positionName, @Param("customerId") String customerId);

	/**
	 * 获取所有职衔
	 */
	List<ItemDto> queryJobTitle(String customerId);

	/**
	 * 获取所有绩效时间
	 */
	List<ItemDto> queryPerformanceDate(String customerId);

	/**
	 * 获取所有绩效
	 */
	List<ItemDto> queryPerformance(String customerId);

	/**
	 * 获取所有学历
	 */

	List<ItemDto> queryEdu(String customerId);

	/**
	 * 获取数据早开始时间
	 *
	 * @param customerId
	 * @return
	 */
	Date findDataSartTime(String customerId);

	/**
	 * 查询员工基本信息
	 *
	 * @param empId
	 * @return
	 */
	EmpDto findEmpBaseInfo(@Param("customerId") String customerId, @Param("empId") String empId);

	/**
	 * 根据员工编码（工号）/用户名判断是否重复，返回重复的数量
	 * 
	 * @param empKey
	 * @param userName
	 * @return
	 */
	Integer existEmp(@Param("empId") String empId, @Param("empKey") String empKey, @Param("userName") String userName);

	/**
	 * 根据条件查询员工信息
	 *
	 * @param organPermitIds
	 *            TODO
	 * @param keyName
	 *            包含（userName/empId/UM）
	 * @param customerId
	 * @param offset
	 *            TODO
	 * @param limit
	 *            TODO
	 * @param orgIds
	 *            TODO
	 * @return
	 */
	List<EmpDto> findEmpAll(@Param("organPermitIds") List<String> organPermitIds, @Param("keyName") String keyName,
			@Param("reqOrgId") String reqOrgId, @Param("sidx") String sidx, @Param("sord") String sord,
			@Param("customerId") String customerId, @Param("offset") Integer offset, @Param("limit") Integer limit);

	/**
	 * 根据条件查询员工总人数
	 *
	 * @param keyName
	 * @param customerId
	 * @param orgIds
	 *            TODO
	 * @return
	 */
	int findEmpAllCount(@Param("keyName") String keyName, @Param("reqOrgId") String reqOrgId,
			@Param("customerId") String customerId, @Param("organPermitIds") List<String> organPermitIds);

	/**
	 * 将之前历史记录标记为0
	 *
	 * @param dto
	 */
	void updateRiskState(@Param("customerId") String customerId, @Param("empId") String empId);

	/**
	 * 新增风险评估
	 *
	 * @param dto
	 */
	void addRisk(@Param("dto") RiskDto dto);

	/**
	 * 新增风险评估子项
	 *
	 * @param list
	 */
	void addRiskItem(@Param("list") List<RiskItemDto> list);

	/**
	 * 检查Days表是否有本年度数据
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer checkDaysTable(Map<String, String> paramMap);

	/**
	 * 插入新的一年每天工作或假期情况
	 */
	void insertDays(@Param("list") List<DaysDto> days);

	/**
	 * 插入日志
	 * 
	 * @param paramMap
	 * @author jxzhang on 2017-02-11
	 */
	void insertLog(Map<String, Object> paramMap);

	/**
	 * 插入指定年每天应出勤小时数
	 * 
	 * @param dtoList
	 */
	int insertTheoryAttendance(List<TheoryAttendanceDto> dtoList);

	/**
	 * 更新带薪假期
	 * 
	 * @param params
	 * @return
	 */
	void updateVacationDays(List<DaysDto> list);

	/**
	 * 查找本月总法定假期（带薪假期）
	 * 
	 * @param params
	 * @return
	 */
	int findVacationDays(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

	/**
	 * 检查指定年月历史机构表是否有数据
	 * 
	 * @param ym
	 * @return
	 */
	@Select("SELECT count(1) FROM history_dim_organization_month WHERE `year_month` = #{ym}")
	int checkHistoryOrganization(@Param("ym") int ym);

	/**
	 * 从维度表复制数据到历史机构表-月
	 * 
	 * @param ym
	 * @return
	 */
	@Select("INSERT INTO history_dim_organization_month ("
			+ "history_dim_organization_month_id, organization_id, customer_id, organization_company_id, organization_type_id, organization_key, organization_parent_key, organization_parent_id, organization_name, organization_name_full, note, is_single, full_path, has_children, depth, refresh, profession_id, `year_month` )"
			+ " SELECT replace(uuid(),'-',''), organization_id, customer_id, organization_company_id, organization_type_id, organization_key, organization_parent_key, organization_parent_id, organization_name, organization_name_full, note, is_single, full_path, has_children, depth, NOW(), profession_id, #{ym}"
			+ " FROM dim_organization")
	Integer insertHistoryOrganization(@Param("ym") int ym);

	/**
	 * 给没有事务的表引擎回滚事务
	 * 
	 * @param ym
	 */
	@Select("DELETE FROM history_dim_organization_month WHERE `year_month` = #{ym}")
	void delectHistoryOrganization(@Param("ym") int ym);
}
