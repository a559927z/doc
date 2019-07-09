package net.chinahrd.mvc.app.dao;

import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("kanbanConfigDao")
public interface KanbanConfigDao {
	/**
	 * 查询用户管理看板的指标列表
	 * @param customerId
	 * @param empId
	 * @return
	 */
	public List<KanbanConfigMobileDto> getKanbanList(@Param("customerId")String customerId,@Param("empId")String empId);
	
	/**
	 * 查询用户已经添加的指标
	 * @param customerId
	 * @param empId
	 * @return
	 */
	public List<KanbanConfigMobileDto> getAlreadyAddedList(@Param("customerId")String customerId,@Param("empId")String empId);
	
	/**
	 * 查询没有添加的指标
	 * @param customerId
	 * @param empId
	 * @return
	 */
	public List<KanbanConfigMobileDto> getNotAddedList(@Param("customerId")String customerId,@Param("empId")String empId);
	
	
	/**
	 * 用户向管理看板添加指标
	 * @param customerId
	 * @param dto
	 * @return
	 */
	public boolean addKanbanItem(@Param("dto")KanbanConfigMobileDto dto);
	
	
	/**
	 * 用户删除管理看板的指标
	 * @param functionConfigMobileId
	 * @return
	 */
	public boolean deleteKanbanItem(@Param("functionConfigMobileId")String functionConfigMobileId);
	
	/**
	 * 修改管理看板指标的顺序
	 * @param customerId
	 * @param empId
	 * @param list 
	 * @return
	 */
	public void updateKanbanItem(@Param("customerId")String customerId,
											@Param("empId")String empId,
											@Param("list")List<KanbanConfigMobileDto> dtos);
}
