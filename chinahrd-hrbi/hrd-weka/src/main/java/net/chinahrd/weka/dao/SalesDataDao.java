package net.chinahrd.weka.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("salesDataDao")
public interface SalesDataDao {
	
	Map<String, Object> queryEmpSalesData(@Param("empId") String empId);

}
