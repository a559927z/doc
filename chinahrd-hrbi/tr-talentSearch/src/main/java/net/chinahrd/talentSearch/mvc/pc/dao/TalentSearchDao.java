package net.chinahrd.talentSearch.mvc.pc.dao;

import java.util.List;

import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.pc.emp.TalentSearch;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 人才搜索dao接口类
 * Created by qpzhu on 16/03/02.
 */
@Repository("talentSearchDao")
public interface TalentSearchDao {

	/**
     * 根据员工ID查询员工详细信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    EmpDetailDto findEmpDetail(@Param("empId") String empId, @Param("customerId") String customerId);
    
    /**
     * 获取搜索的人数 
     *
     * @param empId
     * @param customerId
     * @return
     */
	Integer findEmpAdvancedAllCount(@Param("talentSearch")TalentSearch talentSearch, @Param("customerId")String customerId, @Param("organPermitIds")List<String> organPermitIds);
	
	/**
     * 获取搜索的人员信息
     *
     * @param empId
     * @param customerId
     * @return
     */
	List<EmpDto> findEmpAdvancedAll( @Param("organPermitIds")List<String> organPermitIds, @Param("talentSearch")TalentSearch talentSearch, @Param("sidx") String sidx, @Param("sord") String sord, @Param("customerId") String customerId, 
	    		@Param("offset")Integer offset, @Param("limit")Integer limit);
}
