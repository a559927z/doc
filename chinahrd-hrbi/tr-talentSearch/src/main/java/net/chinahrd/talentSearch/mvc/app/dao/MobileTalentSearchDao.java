package net.chinahrd.talentSearch.mvc.app.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.app.common.EmpDto;

import org.springframework.stereotype.Repository;

/**
 * 人员搜索dao
 * 
 * @author htpeng 2016年3月30日下午5:30:38
 */
@Repository("mobileTalentSearchDao")
public interface MobileTalentSearchDao {

	/**
	 * 根据名称搜索人员
	 *
	 * 
	 * @return
	 */
	List<EmpDto> queryTalentSearch(Map<String, Object> map);

	Integer queryTalentSearchCount(Map<String, Object> map);
}
