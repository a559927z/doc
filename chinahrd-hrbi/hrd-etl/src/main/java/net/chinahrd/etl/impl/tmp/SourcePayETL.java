package net.chinahrd.etl.impl.tmp;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourcePayEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourcePayETL extends SimpleAbstractEtl<SourcePayEntity> {

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
		return SourcePayEntity.getEntityAuxiliary()
			.asId("t.`id`")
			.asCustomerId("t.`customer_id`")
			.asEmpId("t.`emp_id`")
			.asUsreNameCh("t.`usre_name_ch`")
			.asOrganizationId("t.`organization_id`")
			.asFullPath("t.`full_path`")
			.asPostionId("t.`postion_id`")
			.asPayContract("t.`pay_contract`")
			.asPayShould("t.`pay_should`")
			.asPayActual("t.`pay_actual`")
			.asSalaryActual("t.`salary_actual`")
			.asWelfareActual("t.`welfare_actual`")
			.asCrValue("t.`cr_value`")
			.asYearMonth("t.`year_month`")
			.asRefresh("t.`refresh`")
			.asYear("t.`year`")
			.asBonus("t.`bonus`")
			.setSqlBody(" pay t where 1=1");
	}

}
