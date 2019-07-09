package net.chinahrd.etl.impl.talentMaps;

import java.sql.ResultSet;
import java.util.List;

import net.chinahrd.etl.AbstractEtl;
import net.chinahrd.etl.entity.SourceAbilityChangeEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.db.DatabaseUtil;

public class SourceAbilityChangeETL extends AbstractEtl<SourceAbilityChangeEntity> implements Runnable {

	private StringBuffer sql = new StringBuffer("SELECT a.* FROM ability_change a");
	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(URL, USER, PASSWORD, MY_SQL_DRIVER,this.sql.toString());
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void run() {
		this.execute();
		
	}

	@Override
	public List<SourceAbilityChangeEntity> query() throws Throwable {
		sql.delete(0, sql.length());
		sql.append(
				"SELECT a.* FROM ability_change a");
		

		List<SourceAbilityChangeEntity> rsList = CollectionKit.newList();
		DatabaseUtil databaseUtil = new DatabaseUtil(URL, USER, PASSWORD, MY_SQL_DRIVER);
		ResultSet rs = databaseUtil.query(getQuerySql(sql.toString()));
		while (rs.next()) {
			rsList.add(new SourceAbilityChangeEntity(Identities.uuid2(),rs.getString("customer_id"),rs.getString("emp_id"),rs.getString("user_name_ch"),rs.getString("full_path"),rs.getString("organization_parent_id"),rs.getString("organization_id"),rs.getString("sequence_id"),rs.getString("sequence_sub_id"),rs.getString("ability_id"),rs.getInt("age"),rs.getString("sex"),rs.getString("degree_id"),rs.getString("ability_number_id"),rs.getString("update_time"),rs.getInt("year_months")));
		}
		databaseUtil.close();
		return rsList;
	}
	
}
