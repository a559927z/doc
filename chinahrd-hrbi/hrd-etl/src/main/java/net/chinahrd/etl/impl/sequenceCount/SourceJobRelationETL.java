package net.chinahrd.etl.impl.sequenceCount;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceJobRelationEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceJobRelationETL extends SimpleAbstractEtl<SourceJobRelationEntity> implements Runnable {

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	protected String getUrl() {
		return URL;
	}

	@Override
	protected String getUser() {
		return USER;
	}

	@Override
	protected String getPassword() {
		return PASSWORD;
	}

	@Override
	protected String getDriver() {
		return MY_SQL_DRIVER;
	}

	@Override
	protected SqlAuxiliary getSqlAuxiliary() {
		return SourceJobRelationEntity.getEntityAuxiliary()
			.asJobRelationId("t.`job_relation_id`")
			.asCustomerId("t.`customer_id`")
			.asSequenceSubId("t.`sequence_sub_id`")
			.asSequenceId("t.`sequence_id`")
			.asAbilityId("t.`ability_id`")
			.asJobTitleId("t.`job_title_id`")
			.asAbilityLvId("t.`ability_lv_id`")
			.asYear("t.`year`")
			.asType("t.`type`")
			.asRankName("t.`rank_name`")
			.asShowIndex("t.`show_index`")
			.asRefresh("t.`refresh`")
			.setSqlBody(" job_relation t where 1=1");
	}

	@Override
	public void run() {
		this.execute();
	}
	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchNum(100000);
		batchQuery.setBatchSql(databaseUtil, getSqlAuxiliary().getSql());
	}
}
