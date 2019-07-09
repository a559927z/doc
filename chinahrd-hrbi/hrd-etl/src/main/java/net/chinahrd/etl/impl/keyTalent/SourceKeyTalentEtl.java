/**
*net.chinahrd.etl.impl.keyTalent
*/
package net.chinahrd.etl.impl.keyTalent;

import net.chinahrd.etl.SimpleAbstractEtl;
import net.chinahrd.etl.entity.SourceKeyTalentEntity;
import net.chinahrd.etl.sql.FormatColumn;
import net.chinahrd.etl.sql.SqlAuxiliary;

/**
 * @author htpeng 2017年5月26日下午6:24:42
 */
public class SourceKeyTalentEtl extends SimpleAbstractEtl<SourceKeyTalentEntity> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.etl.Etl#isActive()
	 */
	@Override
	public boolean isActive() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.etl.SimpleAbstractEtl#getUrl()
	 */
	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return URL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.etl.SimpleAbstractEtl#getUser()
	 */
	@Override
	protected String getUser() {
		// TODO Auto-generated method stub
		return USER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.etl.SimpleAbstractEtl#getPassword()
	 */
	@Override
	protected String getPassword() {
		// TODO Auto-generated method stub
		return PASSWORD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.etl.SimpleAbstractEtl#getDriver()
	 */
	@Override
	protected String getDriver() {
		// TODO Auto-generated method stub
		return MY_SQL_DRIVER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.etl.SimpleAbstractEtl#getSqlAuxiliary()
	 */
	@Override
	protected SqlAuxiliary getSqlAuxiliary() {
		// TODO Auto-generated method stub
		return SourceKeyTalentEntity.getEntityAuxiliary()
				.asEmpId("t.emp_id")
				.asKeyTalentTypeKey("t.key_talent_type_id")
				.asCustomerId(null,new FormatColumn<String, String>() {
					@Override
					public String formatData(Object obj) {
						return CUSTOMER_ID;
					}
					
				})
				.setSqlBody(" key_talent t");
	}

}
