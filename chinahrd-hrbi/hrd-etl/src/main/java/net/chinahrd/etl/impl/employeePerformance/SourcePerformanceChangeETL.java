package net.chinahrd.etl.impl.employeePerformance;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import net.chinahrd.etl.AbstractEtl;
import net.chinahrd.etl.Etl;
import net.chinahrd.etl.entity.SourcePerformanceChangeEntity;
import net.chinahrd.etl.impl.DataHandling;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.db.DatabaseUtil;

public class SourcePerformanceChangeETL extends AbstractEtl<SourcePerformanceChangeEntity> {

	/**
	 * 查客户数据
	 */
	private final String SQL = "SELECT * FROM performance_change t where 1=1";

	/**
	 * 所有rank值
	 */
	private Map<String, String> rankAll = null;

	public SourcePerformanceChangeETL() {
		super();
		try {
			rankAll = DataHandling.getRankAll();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void setBatchQuery(BatchSqlConfig config) {
		config.setBatchSql(URL, USER, PASSWORD, MY_SQL_DRIVER, SQL);
	}

	@Override
	public List<SourcePerformanceChangeEntity> query() throws Throwable {
		
		List<SourcePerformanceChangeEntity> rsList = CollectionKit.newList();
		DatabaseUtil databaseUtil = new DatabaseUtil(URL, USER, PASSWORD, Etl.MY_SQL_DRIVER);
		ResultSet rs = databaseUtil.query(getQuerySql(SQL));
		while (rs.next()) {
			
			String empId = rs.getString("emp_id");
			String rank = rankAll.get(empId);
			
			rsList.add(new SourcePerformanceChangeEntity(
					Identities.uuid2(), 
					CUSTOMER_ID, 
					empId, 
					rs.getString("emp_key"),
					rs.getString("organization_parent_id"),
					rs.getString("organization_parent_name"),
					rs.getString("organization_id"),
					rs.getString("organization_name"),
					rs.getString("position_id"),
					rs.getString("position_name"),
					rs.getString("performance_id"),
					rs.getString("performance_name"),
					rank,
					rs.getString("rank_name_past"),
					rs.getInt("year_month"),
					rs.getInt("type")
					));
		}
		
//		for (SourcePerformanceChangeEntity entity : rsList) {
//			System.out.println(entity.toString());
//		}
		
		databaseUtil.close();
		return rsList;
	}
}