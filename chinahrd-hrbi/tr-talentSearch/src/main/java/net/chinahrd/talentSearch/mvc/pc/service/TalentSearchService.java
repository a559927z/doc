package net.chinahrd.talentSearch.mvc.pc.service;

import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.emp.TalentSearch;

import com.alibaba.fastjson.JSONObject;


/**
 * 人才搜索Service接口类
 * Created by qpzhu on 16/03/02.
 */
public interface TalentSearchService {

	/**
     * 根据员工ID查询员工详细信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    EmpDetailDto findEmpDetail(String empId, String customerId);
    
    /**
	 * 根据条件查询员工信息
	 * @param talentSearch 
	 * @param pageDto 分页对象
	 * @param sidx 排序字段
	 * @param sord 排序类型 （asc、desc）
	 * @param customerId
	 * @return
	 */
	PaginationDto<EmpDto> findEmpAdvancedAll(TalentSearch talentSearch, PaginationDto<EmpDto> pageDto,String sidx, String sord,String customerId);
	
	
	/**
	 * 根据前端传来的json数据处理
	 * @param json 
	 * @return TalentSearch
	 */
	TalentSearch parameterHandling(JSONObject json);
	

	/**
	 * 数据导出
	 * @param json 
	 * @return TalentSearch
	 */
	void dataResultExport(TalentSearch talentSearch,String customerId,HttpServletResponse response);

}
