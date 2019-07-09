package net.chinahrd.mvc.app.service;

import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;


/**
 * @author guanjian
 *
 */
public interface KanbanConfigService {
	/**
	 * 查询用户管理看板的指标列表
	 * @param customerId
	 * @param empId
	 * @return
	 */
	public List<KanbanConfigMobileDto> getKanbanList(String customerId,String empId);
	
	/**
	 * 查询用户已经添加的指标
	 * @param customerId
	 * @param empId
	 * @return
	 */
	public List<KanbanConfigMobileDto> getAlreadyAddedList(String customerId,String empId);
	
	/**
	 * 查询没有添加的指标
	 * @param customerId
	 * @param empId
	 * @return
	 */
	public List<KanbanConfigMobileDto> getNotAddedList(String customerId,String empId);
	
	
	/**
	 * 用户向管理看板添加指标
	 * @param dto
	 * @return
	 */
	public boolean addKanbanItem(KanbanConfigMobileDto dto);
	
	/**
	 * 用户向管理看板添加指标
	 * @param customerId
	 * @param empId
	 * @param functionId
	 * @return
	 */
	public boolean addKanbanItem(String customerId,String empId,String functionId);
	
	
	/**
	 * 用户删除管理看板的指标
	 * @param functionConfigId
	 * @return
	 */
	public boolean deleteKanbanItem(String functionConfigId);
	
	/**
	 * 修改管理看板指标的顺序
	 * @param customerId
	 * @param empId
	 * @param dtos 
	 * @return
	 */
	public void updateKanbanItem(String customerId,String empId,List<KanbanConfigMobileDto> dtos);
}
