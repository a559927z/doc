package net.chinahrd.mvc.pc.service.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.RiskDto;
import net.chinahrd.entity.dto.pc.common.RiskItemDto;
import net.chinahrd.utils.holiday.DaysDto;

/**
 * 公共service接口类
 */
public interface CommonService {

	/**
	 * 获取人群集合
	 *
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> queryPopulations(String customerId);

	/**
	 * 获取人才类型
	 *
	 * @param customerId
	 *            客户id
	 * @return
	 */
	List<ItemDto> getTalentType(String customerId);

	/**
	 * 获取所有序列
	 *
	 * @param customerId
	 *            客户id
	 * @return
	 */
	List<ItemDto> getSequence(String customerId);

	/**
	 * 获取职级（能力层级）
	 *
	 * @param customerId
	 *            客户id
	 * @return
	 */
	List<ItemDto> queryAbilityType(String customerId);

	/***
	 * 获取岗位信息
	 *
	 * @param positionName
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> queryPositions(String positionName, String customerId);

	/**
	 * 获取所有绩效
	 *
	 * @param customerId
	 *            客户id
	 * @return
	 */
	List<ItemDto> getPerformance(String customerId);

	/**
	 * 获取所有绩效名称，以‘,’号隔开
	 *
	 * @param customerId
	 *            客户id
	 * @return
	 */
	String getPerformanceStr(String customerId);

	/**
	 * 获取最大的绩效时间
	 * 
	 * @return
	 */
	String queryPerformanceMaxDate(String customerId);

	/**
	 * 筛选条件
	 *
	 * @param customerId
	 * @return
	 */
	List<ItemDto> getSearchBox(String customerId);

	/**
	 * 筛选条件
	 *
	 * @param customerId
	 * @return
	 */
	List<ItemDto> getTalentMapsSearchBox(String customerId);

	/**
	 * 筛选条件
	 *
	 * @param customerId
	 * @return
	 */
	List<ItemDto> getTalentSearchBox(String customerId);

	/**
	 * 绩效变化页面 筛选条件
	 *
	 * @param customerId
	 * @return
	 */
	List<ItemDto> getSearchBoxPerChange(String customerId);

	List<String> getTimeScope(String customerId);

	/**
	 * 查找员工的基本信息
	 *
	 * @param empId
	 * @return
	 */
	EmpDto getEmpBaseInfo(String customerId, String empId);

	/**
	 * 根据员工编码（工号）/用户名判断是否重复，返回重复的数量
	 * 
	 * @param empKey
	 * @param userName
	 * @return
	 */
	Integer existEmp(String empId, String empKey, String userName);

	/**
	 * 根据条件查询员工信息
	 *
	 * @param keyName
	 *            包含（userName/UM）
	 * @param pageDto
	 *            分页对象
	 * @param sidx
	 *            排序字段
	 * @param sord
	 *            排序类型 （asc、desc）
	 * @param customerId
	 * @return
	 */
	PaginationDto<EmpDto> findEmpAll(String keyName, String reqOrgId, PaginationDto<EmpDto> pageDto, String sidx,
			String sord, String customerId);

	/**
	 * 获取所有层级HashMap
	 */
	List<HashMap<String, String>> queryAbilityHashMap(String customerId);

	/**
	 * 获取所有职级HashMap
	 */
	List<HashMap<String, String>> queryAbilityLvHashMap(String customerId);

	boolean addRisk(String customerId, RiskDto riskDto, List<RiskItemDto> list);

	/**
	 * 插入days表，计算工作天、休息天、法定节日
	 * 
	 * @param endDay
	 *            2018-01-01
	 * @param startDay
	 *            2017-01-01
	 * @return
	 * @author jxzhang on 2017-02-13
	 */
	List<DaysDto> insertDays(String endDay, String startDay);

	/**
	 * 插入日志
	 * 
	 * @param userId
	 *            操作者
	 * @param module
	 *            表名称
	 * @param text
	 *            描述
	 * @param startTime
	 *            开始时间
	 * @param isError
	 *            是否错误
	 * @param showIndex
	 *            顺序
	 * @author jxzhang on 2017-02-13
	 * 
	 */
	void insertLog(String userId, String module, String text, Date startTime, int isError, Integer showIndex);

	/**
	 * 检查Days表是否有本年度数据
	 * 
	 * @param year
	 *            本年度
	 * @return
	 * @author jxzhang on 2017-02-14
	 */
	boolean checkDaysTable(String year);

	/**
	 * 插入指定年每天应出勤小时数
	 * 
	 * @param days
	 * @param hour
	 *            应该出勤小时数（单位：小时）
	 * @param ym
	 * @return
	 */
	boolean insertTheoryAttendance(List<DaysDto> days, double hour, int ym, int y);

	/**
	 * 更新带薪假期
	 * 
	 * @param params
	 * @return
	 */
	void updateVacationDays(List<DaysDto> params);

	/**
	 * 查找本月总法定假期（带薪假期） by jxzhang on 2018-08-08
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int findVacationDays(String beginTime, String endTime);

	/**
	 * 
	 * @param ym
	 * @return
	 */
	int checkHistoryOrganization(int ym);

	int insertHistoryOrganization(int ym);
}
