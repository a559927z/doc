package net.chinahrd.eis.notice.dao;

import java.util.List;

import net.chinahrd.eis.notice.dto.SimpleEmpDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("noticeDao")
public interface NoticeDao {

	/**
	 * 查询指定员工的上级信息
	 * 
	 * @param customerId
	 * @param empIds
	 * @return
	 */
	List<SimpleEmpDto> queryHFEmpInfos(@Param("customerId") String customerId,
			@Param("empIds") List<String> empIds);

	/**
	 * 查询指定员工的机构负责人的信息 or BP的信息
	 * 
	 * @param customerId
	 * @param empIds
	 * @param role
	 * @return
	 */
	List<SimpleEmpDto> queryOrgHeadOrBPEmpInfos(
			@Param("customerId") String customerId,
			@Param("empIds") List<String> empIds,
			@Param("role") String role);
}
