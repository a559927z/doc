package net.chinahrd.db.input.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.db.input.Entity.HistoryEmpCountEntity;
import net.chinahrd.db.input.Entity.KeyTalentDto;
import net.chinahrd.db.input.Entity.LogEntity;
import net.chinahrd.db.input.Entity.SoureJobChangeEntity;
import net.chinahrd.db.input.Entity.SoureVDimEmpEntity;
import net.chinahrd.entity.dto.KVItemDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("dbImportDao")
public interface DbImportDao {

	/* ============================================================== */
	/* 查询 */
	/* ============================================================== */
	/**
	 * 查出源表数据
	 * @param string 
	 * 
	 * @return
	 */
	List<SoureVDimEmpEntity> querySoureVDimEmp(@Param("customerId") String customerId);


	List<SoureVDimEmpEntity> queryVDimEmp(
			@Param("customer_id") String customer_id);
	List<SoureVDimEmpEntity> queryVDimEmpWorking(String customerId);

	/* ============================================================== */
	/* 刷新 */
	/* ============================================================== */
	void deleteHistoryEmpCountByDay(@Param("day") String day);
	/**
	 * 
	 * 
	 * @param list
	 */
	void replaceHistoryEmpCountByDay(
			@Param("list") List<HistoryEmpCountEntity> list);

	/**
	 * 
	 * @param customer_id
	 * @param year_month
	 */
	void deleteHistoryEmpCountMonthByDay(Map<String, Object> param);

	void insertKeyTalent(@Param("list") List<KeyTalentDto> list);
	void deleteKeyTalent(@Param("list") List<String> list,
			@Param("customer_id") String customer_id);

	/**
	 * 我的下属
	 * 
	 * @param mp
	 *            TODO
	 * @return
	 */
	void callUnderling(Map<String, String> mp);

	/**
	 * 总人数
	 * 
	 * @param mp
	 */
	void callHistoryEmpCount(Map<String, Object> mp);
	void replaceHistoryEmpCountMonthByFirst(Map<String, Object> param);
	void replaceHistoryEmpCountMonthByToday(Map<String, Object> param);
	void replaceHistoryEmpCountMonthByLast(Map<String, Object> param);

	/**
	 * 员工表
	 * 
	 * @param list
	 */
	void replaceVDimEmp(@Param("list") List<SoureVDimEmpEntity> list);
	void deleteVdimEmp(@Param("list") List<SoureVDimEmpEntity> list);
	void updateReportRelation(@Param("list") List<SoureVDimEmpEntity> list);

	/**
	 * 是否是关键人才
	 * 
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> queryIsTT(@Param("customerId") String customerId);

	/**
	 * 员工加班记录 </br>
	 * 
	 * 每天插入emp_overtime_day</br>
	 * 如果每月月尾，要把上个月所有值统计保存到emp_overtime;</br>
	 * 
	 * @param mp
	 */
	void callEmpOvertimeDay(Map<String, Object> mp);

	/**
	 * 日志
	 * 
	 * @param log
	 */
	void insertLog(@Param("log") LogEntity log);


	/**
	 * 工作异动
	 * @param customerId
	 * @param string 
	 * @return
	 */
	void callJobChange(Map<String, String> mp);
	List<SoureJobChangeEntity> queryJobChange(String customer_id);
	void replaceJobChange(@Param("position_id_ed") String position_id_ed,
			@Param("position_name_ed") String position_name_ed,
			@Param("rank_name_ed") String rank_name_ed,
			@Param("emp_job_change_id") String emp_job_change_id,
			@Param("customer_id") String customer_id);

	List<SoureVDimEmpEntity> queryVDimEmpRFD(@Param("customer_id") String customer_id,
			@Param("list") List<String> rfdEmp);

	/**
	 * 员工日切片
	 * @param customerId
	 * @param string
	 */
	void callDimEmpDays(Map<String, String> mp);

	


	
	
	/**
	 * 插入历史员工日切片数据 
	 * @param customerId
	 * @return
	 */
	List<SoureVDimEmpEntity> queryVDimEmp2(@Param("customer_id") String customer_id);
	
	void addDimEmpDays(@Param("list") List<SoureVDimEmpEntity> list, @Param("day")String day);
	
}







		
		