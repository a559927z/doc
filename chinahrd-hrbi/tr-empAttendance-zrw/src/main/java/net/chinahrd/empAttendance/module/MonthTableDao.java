package net.chinahrd.empAttendance.module;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.ProcessErrorDto;

/**
 * 
 * @author htpeng on 2017年8月9日
 * @Verdion 1.0 版本
 */
@Repository("monthTableDao")
public interface MonthTableDao {

	/**
	 * 更新考勤月表
	 * 
	 * @param customerId
	 * @param userId
	 * @param p_emp_id
	 * @param p_days
	 * @return
	 */
	List<ProcessErrorDto> callEmpAttendMonth(@Param("p_customer_id") String customerId,
			@Param("p_user_id") String userId, @Param("p_emp_id") String p_emp_id, @Param("p_days") Integer p_days);
}
