package net.chinahrd.etl.impl.allBase;

import java.sql.ResultSet;
import java.util.List;

import net.chinahrd.etl.AbstractEtl;
import net.chinahrd.etl.entity.SourcePopulationRelationEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.db.DatabaseUtil;

public class SourcePopulationRelationEtl extends AbstractEtl<SourcePopulationRelationEntity> {

	private StringBuffer sql = new StringBuffer("SELECT a.* FROM population_relation a  WHERE a.days >'2015-10-01'");
	@Override
	public boolean isActive() {
		return true;
	}
	@Override
	public String getRefresh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBatchQuery(BatchSqlConfig config) {
		config.setBatchSql(URL, USER, PASSWORD, MY_SQL_DRIVER, this.sql.toString());
	}

	@Override
	public List<SourcePopulationRelationEntity> query() throws Throwable {
		sql.delete(0, sql.length());
		sql.append(
				"SELECT a.* FROM population_relation a  WHERE a.days >'2015-10-01'");
		

		List<SourcePopulationRelationEntity> rsList = CollectionKit.newList();
		DatabaseUtil databaseUtil = new DatabaseUtil(URL, USER, PASSWORD, MY_SQL_DRIVER);
		ResultSet rs = databaseUtil.query(getQuerySql(sql.toString()));
		while (rs.next()) {
			rsList.add(new SourcePopulationRelationEntity(rs.getString("customer_id"),rs.getString("emp_id"),rs.getString("emp_id"),rs.getString("organization_id"),rs.getString("days")));
		}
		databaseUtil.close();
		return rsList;
	}

}
