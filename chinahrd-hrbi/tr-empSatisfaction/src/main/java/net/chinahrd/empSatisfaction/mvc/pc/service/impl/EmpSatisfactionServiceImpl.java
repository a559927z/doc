package net.chinahrd.empSatisfaction.mvc.pc.service.impl;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.chinahrd.empSatisfaction.mvc.pc.dao.EmpSatisfactionDao;
import net.chinahrd.empSatisfaction.mvc.pc.service.EmpSatisfactionService;
import net.chinahrd.entity.dto.pc.competency.SatisfactionChartDto;
import net.chinahrd.entity.dto.pc.competency.SatisfactionDto;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.TreeStruDataUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 满意度敬业度Service实现类
 * Created by qpzhu on 16/03/14.
 */
@Service("empStaisfaction")
public class EmpSatisfactionServiceImpl implements EmpSatisfactionService {

	@Autowired
	private EmpSatisfactionDao empSatisfactionDao;
	
	/**
	 * 获取敬业度年度分数以及返回是否是根节点
	 * @param customerId
	 * @return
	 */
	@Override
	public Map<String, Object> getEngagementYearSoure(String customerId, String organizationId) {
		Map<String, Object> rs = CollectionKit.newMap();
		String rootNode=getRootNode(customerId);
		boolean flag=false;
		if(rootNode.equals(organizationId)){
			flag=true;
		}
		List<SatisfactionChartDto> data = empSatisfactionDao.queryEngagementYearSoure(customerId,organizationId,flag);
		rs.put("flag", flag);
		rs.put("data", data);
		return rs;
	}
	
	
	/**
	 * 获取敬业度子节点分数以及公司分数
	 * @param customerId
	 * @return
	 */
	@Override
	public List<SatisfactionChartDto> getEngagementSoure(String customerId, String organizationId) {
		List<SatisfactionChartDto> data = empSatisfactionDao.queryEngagementSoure(customerId, organizationId, getEngagementMaxDay(customerId));
		return data;
	}

	/**
	 * 获取敬业度题目以及分数以及公司分数
	 * @param customerId
	 * @return
	 */
	@Override
	public List<SatisfactionDto> getEngagementSubject(String customerId, String organizationId) {
		List<SatisfactionDto> data = empSatisfactionDao.queryEngagementSubject(customerId, organizationId, getEngagementMaxDay(customerId));
		TreeStruDataUtil<SatisfactionDto> st = new TreeStruDataUtil<SatisfactionDto>();
		return st.constructOrderTree(data, "id", "-1", "parent", "isLeaf", "level", true, "expanded", "true");
	}

//	private List<SatisfactionDto> setTree(List<SatisfactionDto> data){
//		List<SatisfactionDto> tem= CollectionKit.newList();
//		for (SatisfactionDto d:data){
//			if("-1".equals(d.getParent())){
//				d.setParent(null);
//				d.setLevel("0");
//				d.setExpanded("true");
//				d.setIsLeaf(hasLeaf(data, d.getId().toString()));
//				if(Boolean.parseBoolean(d.getIsLeaf())){
//					d.setId(UUID.randomUUID().toString());
//				}
//				tem.add(d);
//				setLevel(tem, data, d.getId(), 0);
//			}
//		}
//		return tem;
//	}
//
//	private void setLevel(List<SatisfactionDto> tem,List<SatisfactionDto> data, String id, Integer level){
//		level++;
//		for (SatisfactionDto d:data){
//			if(id.equals(d.getParent())){
//				d.setExpanded("true");
//				d.setLevel(level.toString());
//				d.setIsLeaf(hasLeaf(data, d.getId().toString()));
//				if(Boolean.parseBoolean(d.getIsLeaf())){
//					d.setId(UUID.randomUUID().toString());
//				}
//				tem.add(d);
//				setLevel(tem, data, d.getId(), level);
//			}
//		}
//	}
//
//	private String hasLeaf(List<SatisfactionDto> data, String id){
//		boolean b=true;
//		for(SatisfactionDto d:data){
//			if(id.equals(d.getParent())){
//				b=false;
//				break;
//			}
//		}
//		return String.valueOf(b).intern();
//	}
//	
	/**
	 * 获取满意度敬业度年度分数以及返回是否是根节点
	 * @param customerId
	 * @return
	 */
	@Override
	public Map<String, Object>  getSatisfactionYearSoure(String customerId, String organizationId) {
		Map<String, Object> rs = CollectionKit.newMap();
		String rootNode=getRootNode(customerId);
		boolean flag=false;
		if(rootNode.equals(organizationId)){
			flag=true;
		}
		List<SatisfactionChartDto> data = empSatisfactionDao.querySatisfactionYearSoure(customerId, organizationId, flag);
		rs.put("flag", flag);
		rs.put("data", data);
		return rs;
	}

	/**
	 * 获取满意度子节点分数以及公司分数
	 * @param customerId
	 * @return
	 */
	@Override
	public List<SatisfactionChartDto> getSatisfactionSoure(String customerId, String organizationId) {
		List<SatisfactionChartDto> data = empSatisfactionDao.querySatisfactionSoure(customerId, organizationId, getSatisfactionMaxDay(customerId));
		return data;
	}

	/**
	 * 获取满意度题目以及分数以及公司分数
	 * @param customerId
	 * @return
	 */
	@Override
	public List<SatisfactionDto> getSatisfactionSubject(String customerId, String organizationId) {
		List<SatisfactionDto> data = empSatisfactionDao.querySatisfactionSubject(customerId,organizationId,getSatisfactionMaxDay(customerId));
		TreeStruDataUtil<SatisfactionDto> treestructure = new TreeStruDataUtil<SatisfactionDto>();
		return treestructure.constructOrderTree(data, "id", "-1", "parent", "isLeaf", "level", true, "expanded", "true");
		//return setTree(data);
	}
	
	public String getEngagementMaxDay(String customerId) {
		return  empSatisfactionDao.queryEngagementMaxDay(customerId);
	}
	
	public String getSatisfactionMaxDay(String customerId) {
		return  empSatisfactionDao.querySatisfactionMaxDay(customerId);
	}
	
	public String getRootNode(String customerId) {
		return  empSatisfactionDao.queryRootNode(customerId);
	}
	
}
