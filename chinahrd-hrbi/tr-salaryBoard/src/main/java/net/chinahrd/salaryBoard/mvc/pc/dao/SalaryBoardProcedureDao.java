package net.chinahrd.salaryBoard.mvc.pc.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 薪酬看板Dao
 * 
 * @author qpzhu by 2016-04-06
 */
@Repository("salaryBoardProcedureDao")
public interface SalaryBoardProcedureDao {
	/**
	 * 获取特定组织某天的员工数
	 * @result
	 */
	List<Map<String, Object>> hisEmpCount(@Param("day")String day, @Param("customerId")String customerId);
	

	/**
	 * 获取特定组织按应发薪酬排序的中间值，某月
	 */
	String findCenterPayShould(@Param("customerId")String customerId, @Param("p_ym")String p_ym, @Param("fullPath")String fullPath, @Param("limitStart")String limitStart, @Param("oddEven")String oddEven);
	
	/**
	 * 更新平均薪酬pay_collect.quantile_value
	 */
	void batchUpateQuanValuePayCollect(@Param("list") List<Map<String, String>> list);
	/**
	 * 批量更新pay_collect.quantile_value
	 */
	void callmakePayCollectYear(@Param("p_customer_id") String customerId,@Param("p_user_id") String userId, @Param("p_ym") String pym, @Param("p_key2") String pkey2);
}
