package net.chinahrd.weka.mybatis;

import java.sql.Connection;

import weka.experiment.InstanceQuery;

public class HrdInstanceQuery extends InstanceQuery {
	private static final long serialVersionUID = 8454500771179985293L;

	public HrdInstanceQuery(Connection connection) throws Exception {
		super();
		m_Connection = connection;
	}
	
	

}
