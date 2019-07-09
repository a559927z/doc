package net.chinahrd.etl.impl.allDim;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceDimDismissionWeekEntity;
import net.chinahrd.etl.sql.SqlAuxiliary;

public class SourceDimDissmissionWeekETL extends SimpleAbstractEtl<SourceDimDismissionWeekEntity> {

	public static String url = "jdbc:mysql://172.16.9.50:3369/mup-large";
	public static String user = "mup";
	public static String password = "1q2w3e123";

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	protected String getUrl() {
		return url;
	}

	@Override
	protected String getUser() {
		return user;
	}


	@Override
	protected String getPassword() {
		return password;
	}

	@Override
	protected String getDriver() {
		return MY_SQL_DRIVER;
	}

	@Override
	protected SqlAuxiliary getSqlAuxiliary() {
		return SourceDimDismissionWeekEntity.getEntityAuxiliary()
				.setSqlBody(" dim_organization t where 1=1");
	}

}
