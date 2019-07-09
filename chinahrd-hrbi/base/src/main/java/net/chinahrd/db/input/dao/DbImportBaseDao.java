package net.chinahrd.db.input.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.db.input.Entity.CheckInputdataStatusEntity;
import net.chinahrd.db.input.Entity.SoureDimOrganizationEntity;
import net.chinahrd.entity.dto.KVItemDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("dbImportBaseDao")
public interface DbImportBaseDao {
	
	/* ============================================================== */
	/* 控制抽取数据状态 */
	/* ============================================================== */
	CheckInputdataStatusEntity findCheckInputdataStatus(
			@Param("customer_id") String customer_id,
			@Param("curDate") String curDate);

	void updateCheckInputdataStatus(@Param("id") String id,
			@Param("customer_id") String customer_id, @Param("code") int code,
			@Param("note") String note);
	
	
	/* ============================================================== */
	/* 机构 */
	/* ============================================================== */
	List<SoureDimOrganizationEntity> querySoureDimOrganization(
			@Param("customer_id") String customer_id);

	List<SoureDimOrganizationEntity> queryDimOrganization(
			@Param("customer_id") String customer_id,
			@Param("list") List<String> changeIds);

	void deleteDimOrganization(@Param("customer_id") String customer_id,
			@Param("list") List<SoureDimOrganizationEntity> list);

	List<SoureDimOrganizationEntity> queryDimOrganizationNew(
			@Param("customer_id") String customer_id);

	void replaceDimOrganization(
			@Param("list") List<SoureDimOrganizationEntity> list);
	void updateAttr(@Param("list") List<SoureDimOrganizationEntity> updateAttr);
	
	// 数据权限
	List<KVItemDto> queryRoleOrgan(@Param("customer_id") String customer_id);
	List<KVItemDto> queryUserOrgan(@Param("customer_id") String customer_id);
	void deleteRoleOrgan(@Param("customer_id") String customer_id);
	void addRoleOrganRelation(@Param("list") List<Map<String, String>> addRoList);
	void deleteUserOrgan(@Param("customer_id") String customer_id);
	void addUserOrganRelation(@Param("list") List<Map<String, String>> addUoList);
	
	void callUpdateFullpathOrg(Map<String, String> mp);
	void callDimOrganizationDays(Map<String, String> mp);
	
	
	/* ============================================================== */
	/* 岗位 */
	/* ============================================================== */
	void callDimPosition(Map<String, String> mp);
	
	
	
	
	
	void callDimTable(Map<String, Object> mp);
	int findLogInfo(Map<String, Object> mp);

	void callDimAbilityLv(Map<String, String> mp);

	void callDimAbility(Map<String, String> mp);

	void callDimKeyTalentType(Map<String, String> mp);

	void callDimSequence(Map<String, String> mp);

	void callDimSequenceSub(Map<String, String> mp);

	void callDimJobTitle(Map<String, String> mp);

	void callDimCourseType(Map<String, String> mp);

	void callDimRunOff(Map<String, String> mp);

	void callDimStructure(Map<String, String> mp);

	void callDimProjectType(Map<String, String> mp);

	void callDimCourse(Map<String, String> mp);

	void callDimProjectInputType(Map<String, String> mp);
	
	void callDimChangeType(Map<String, String> mp);

	void callDimChannel(Map<String, String> mp);
	
	void callDimDismissionWeek(Map<String, String> mp);
	
	void callDimEncourages(Map<String, String> mp);
	
	void callDimPerformance(Map<String, String> mp);
	
	void callDimPopulation(Map<String, String> mp);
	
	void callDimQuality(Map<String, String> mp);
	
	
	void callDimCheckworktype(Map<String, String> mp);
	
}
