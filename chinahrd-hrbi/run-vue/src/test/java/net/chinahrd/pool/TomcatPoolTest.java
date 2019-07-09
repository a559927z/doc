package net.chinahrd.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * Tomcat jdbc pool测试
 * 
 * @author jxzhang on 2016年12月25日
 * @Verdion 1.0 版本
 */
public class TomcatPoolTest {

	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://127.0.0.1:3369/ks";
		String user = "root";
		String password = "root";
		String sql = "select * from demo_tickets64";

		url = "jdbc:mysql://42.62.24.7:3369/mup-zrw";
		user = "mup";
		password = "1a2s3d123";
		sql = "select * from v_dim_emp";

		PoolProperties p = new PoolProperties();
		p.setUrl(url);
		p.setDriverClassName("com.mysql.jdbc.Driver");
		p.setUsername(user);
		p.setPassword(password);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);

		Connection con = null;
		try {
			con = datasource.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int cnt = 1;
			while (rs.next()) {
				System.out.println((cnt++) + ". emp_key:" + rs.getString("emp_key") + " user_name_ch:"
						+ rs.getString("user_name_ch"));
			}
			rs.close();
			st.close();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (Exception ignore) {
				}
		}
	}

}