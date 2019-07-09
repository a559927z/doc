package net.chinahrd.mvc.pc.dao.admin;

import java.util.List;

import net.chinahrd.entity.dto.pc.admin.OrganTypeDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by jxzhang on 15/6/19.
 */
@Repository("organTypeDao")
public interface OrganTypeDao {

	
	/**
	 * 按照条件查询集合
	 * @param org
	 * @return
	 */
	List<OrganTypeDto> findList(@Param("orgType") OrganTypeDto orgType);

	
}
