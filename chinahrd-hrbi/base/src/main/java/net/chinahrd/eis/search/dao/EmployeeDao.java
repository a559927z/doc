package net.chinahrd.eis.search.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.eis.search.dto.Employee;
import net.chinahrd.eis.search.dto.PastResume;
import net.chinahrd.eis.search.dto.TrainExperience;
/**
 * 员工信息数据接口
 * @author bright
 *
 */
@Repository("employeeDao")
public interface EmployeeDao {
	
	/**
	 * 查询未被索引的员工
	 * @return
	 */
	List<Employee> findUnIndexedEmp(@Param("batchSize") int batchSize);
	/**
	 * 更新员工索引标记
	 * @param emps
	 */
	void updateMark(List<Employee> emps);
	/**
	 * 查询未被索引的培训经历
	 * @return
	 */
	List<TrainExperience> findUnIndexedTrainExperience(@Param("batchSize")int batchSize);
	
	/**
	 * 更新培训经历索引标记
	 * @param emps
	 */
	void updateTrainExperienceMark(List<TrainExperience> dtos);

	
	/**
	 * 查询未被索引的过往履历
	 * @return
	 */
	List<PastResume> findUnIndexedPastResume(@Param("batchSize")int batchSize);
	
	/**
	 * 更新过往履历索引标记
	 * @param emps
	 */
	void updatePastResumeMark(List<PastResume> dtos);

}
