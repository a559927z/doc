package net.chinahrd.etl.impl.empAttendance;

import java.sql.ResultSet;
import java.util.List;

import net.chinahrd.etl.AbstractEtl;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.entity.SourceEmpAttendanceDayEntity;
import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 考勤表-日
 * 
 * @author jxzhang on 2017年4月19日
 * @Verdion 1.0 版本
 */
public class EmpAttendAnceDayEtl extends AbstractEtl<SourceEmpAttendanceDayEntity> {
	
	 // 正常上班ID
	private final String NORMAL = "b90bb95e3c01413b80899b49ba13392e";
	
	
	public static String url = "jdbc:mysql://42.62.24.7:3369/mup-zrw";
	public static String user = "mup";
	public static String password = "1a2s3d123";
	public static String driverClassName = "com.mysql.jdbc.Driver";

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public List<SourceEmpAttendanceDayEntity> query() throws Throwable {
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT  a.emp_key, a.emp_id, a.user_name_ch,                                                                              			 ");
		sql.append("			CASE                                                                                                                                                     ");
		sql.append("			WHEN a.clock_in_am <= concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00') THEN 7.5                                             ");
		sql.append("			ELSE                                                                                                                                                     ");
		sql.append("				CASE                                                                                                                                                 ");
		sql.append("				WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 13:30:00')                                                   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, concat(a.days, ' 09:00:00'), a.clock_out_pm) / 60 - 1.5                                                                   ");
		sql.append("				WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 12:00:00')                                                   ");
		sql.append("				THEN 3                                                                                                                                               ");
		sql.append("				WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm < concat(a.days, ' 12:00:00')                                                    ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, concat(a.days, ' 09:00:00'), a.clock_out_pm) / 60                                                                         ");
		sql.append("	                                                                                                                                                                 ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 13:30:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')                                                   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60                                                                                       ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 12:00:00') AND a.clock_in_am <= concat(a.days, ' 13:30:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')  ");
		sql.append("				THEN 4.5                                                                                                                                             ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 10:00:00') AND a.clock_in_am <= concat(a.days, ' 12:00:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')  ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, concat(a.days, ' 18:00:00')) / 60 - 1.5                                                                    ");
		sql.append("	                                                                                                                                                                 ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 13:30:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60                                                                                       ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 12:00:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, concat(a.days, ' 13:30:00'), a.clock_out_pm) / 60                                                                         ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 10:00:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60 - 1.5                                                                                 ");
		sql.append("				ELSE 0                                                                                                                                               ");
		sql.append("				END                                                                                                                                                  ");
		sql.append("			END as hour_count,                                                                                                                                       ");
		sql.append("				t1.hour_count AS theory_hour_count, a.organization_id, @checkworkTypeId as checkwork_type_id, a.days, a.`year_month` as `year_months`                ");
		sql.append("		FROM emp_attendance a                                                                                                                                        ");
		sql.append("        INNER JOIN theory_attendance t1 on a.days = t1.days                                                                                                  		 ");
		sql.append("        INNER JOIN days t2 ON a.days = t2.days AND t2.is_work_flag = 1                                                                                       		 ");
		sql.append("        WHERE a.days = fn_getYesterday_li() AND a.clock_in_am IS NOT NULL AND a.clock_out_pm IS NOT null                                                             ");

		
		List<SourceEmpAttendanceDayEntity> inAtdEmpList = CollectionKit.newList();
		DatabaseUtil databaseUtil = new DatabaseUtil(url, user, password, driverClassName);
		ResultSet rs = databaseUtil.query(getQuerySql(sql.toString()));
		while (rs.next()) {
			String v1 = Identities.uuid2();
			String v2 = Entity.CUSTOMER_ID;
			String v3 = rs.getString("emp_key");
			String v4 = rs.getString("emp_id");
			String v5 = rs.getString("user_name_ch");
			Double v6 = rs.getDouble("hour_count");
			Double v7 = rs.getDouble("theory_hour_count");
			String v8 = rs.getString("organization_id");
			String v9 = rs.getString("checkwork_type_id");
			String v10 = rs.getString("days");
			Integer v11 = rs.getInt("year_months");

		
			inAtdEmpList.add(new SourceEmpAttendanceDayEntity(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11));
		}
		databaseUtil.close();
//		return getNotInAtdEmpList(inAtdEmpList);
		return inAtdEmpList;
	}
	
	/**
	 * 处理：不在打卡机里的员工，把他们的名单都加入 day表
	 * 
	 * @param inAtdEmpList
	 *            在打卡机里有记录的员工
	 * @return
	 */
	private List<SourceEmpAttendanceDayEntity> getNotInAtdEmpList(List<SourceEmpAttendanceDayEntity> inAtdEmpList) throws Throwable {
		StringBuffer sql = new StringBuffer();
		sql.append("  		SELECT                                                                                                       		 			");
		sql.append("  			vde.emp_key, vde.emp_id, vde.user_name_ch, 																	 	 			");
		sql.append("  			0 as hour_count, t1.hour_count as theory_hour_count, vde.organization_id, '").append(NORMAL).append("'  as checkwork_type_id, ");		// 默认赋予，正常上班
		sql.append("  			fn_getYesterday_li() as days, DATE_FORMAT(fn_getYesterday_li(), '%Y%m') as year_months 									 			");
		sql.append("  		FROM v_dim_emp vde                                                                                           		 			");
		sql.append("  		INNER JOIN theory_attendance t1 ON t1.days = fn_getYesterday_li()                                                    			");
		sql.append("  		INNER JOIN days t2 ON t2.days = t1.days AND t2.is_work_flag = 1                                              		 			");
		sql.append("  		WHERE NOT EXISTS ( SELECT 1 FROM emp_attendance ead WHERE ead.days = fn_getYesterday_li() AND ead.emp_id = vde.emp_id  );    	");
		List<SourceEmpAttendanceDayEntity> notInAtdEmpList = CollectionKit.newList();
		DatabaseUtil databaseUtil = new DatabaseUtil(url, user, password, driverClassName);
		ResultSet rs = databaseUtil.query(sql.toString());
		if(null == rs){
			return inAtdEmpList;
		}else{
			while (rs.next()) {
				String v1 = Identities.uuid2();
				String v2 = Entity.CUSTOMER_ID;
				String v3 = rs.getString("emp_key");
				String v4 = rs.getString("emp_id");
				String v5 = rs.getString("user_name_ch");
				Double v6 = rs.getDouble("hour_count");
				Double v7 = rs.getDouble("theory_hour_count");
				String v8 = rs.getString("organization_id");
				String v9 = rs.getString("checkwork_type_id");
				String v10 = rs.getString("days");
				Integer v11 = rs.getInt("year_months");

				notInAtdEmpList.add(new SourceEmpAttendanceDayEntity(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11));
			}
			databaseUtil.close();
			List<SourceEmpAttendanceDayEntity> union = CollectionKit.union(notInAtdEmpList, inAtdEmpList);
			return union;
		}
	}

	@Override
	public void setBatchQuery(BatchSqlConfig config) {
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT  a.emp_key, a.emp_id, a.user_name_ch,                                                                              			 ");
		sql.append("			CASE                                                                                                                                                     ");
		sql.append("			WHEN a.clock_in_am <= concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00') THEN 7.5                                             ");
		sql.append("			ELSE                                                                                                                                                     ");
		sql.append("				CASE                                                                                                                                                 ");
		sql.append("				WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 13:30:00')                                                   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, concat(a.days, ' 09:00:00'), a.clock_out_pm) / 60 - 1.5                                                                   ");
		sql.append("				WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 12:00:00')                                                   ");
		sql.append("				THEN 3                                                                                                                                               ");
		sql.append("				WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm < concat(a.days, ' 12:00:00')                                                    ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, concat(a.days, ' 09:00:00'), a.clock_out_pm) / 60                                                                         ");
		sql.append("	                                                                                                                                                                 ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 13:30:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')                                                   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60                                                                                       ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 12:00:00') AND a.clock_in_am <= concat(a.days, ' 13:30:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')  ");
		sql.append("				THEN 4.5                                                                                                                                             ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 10:00:00') AND a.clock_in_am <= concat(a.days, ' 12:00:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')  ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, concat(a.days, ' 18:00:00')) / 60 - 1.5                                                                    ");
		sql.append("	                                                                                                                                                                 ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 13:30:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60                                                                                       ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 12:00:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, concat(a.days, ' 13:30:00'), a.clock_out_pm) / 60                                                                         ");
		sql.append("				WHEN a.clock_in_am > concat(a.days, ' 10:00:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')   ");
		sql.append("				THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60 - 1.5                                                                                 ");
		sql.append("				ELSE 0                                                                                                                                               ");
		sql.append("				END                                                                                                                                                  ");
		sql.append("			END as hour_count,                                                                                                                                       ");
		sql.append("				t1.hour_count AS theory_hour_count, a.organization_id, @checkworkTypeId as checkwork_type_id, a.days, a.`year_month` as `year_months`                ");
		sql.append("		FROM emp_attendance a                                                                                                                                        ");
		sql.append("        INNER JOIN theory_attendance t1 on a.days = t1.days                                                                                                  		 ");
		sql.append("        INNER JOIN days t2 ON a.days = t2.days AND t2.is_work_flag = 1                                                                                       		 ");
		sql.append("        WHERE a.days = fn_getYesterday_li() AND a.clock_in_am IS NOT NULL AND a.clock_out_pm IS NOT null                                                             ");
		config.setBatchNum(2);
		config.setBatchSql(url, user, password, driverClassName, sql.toString());
	}
}
/*

SELECT fn_getId(), 1 as customerId, a.emp_key, a.emp_id, a.user_name_ch,

	CASE
	WHEN a.clock_in_am <= concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00') THEN 7.5
	ELSE
	CASE
	WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 13:30:00')
	THEN TIMESTAMPDIFF(MINUTE, concat(a.days, " 09:00:00"), a.clock_out_pm) / 60 - 1.5
	WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 12:00:00')
	THEN 3
	WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm < concat(a.days, ' 12:00:00')
	THEN TIMESTAMPDIFF(MINUTE, concat(a.days, " 09:00:00"), a.clock_out_pm) / 60
	
	WHEN a.clock_in_am > concat(a.days, ' 13:30:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')
	THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60
	WHEN a.clock_in_am > concat(a.days, ' 12:00:00') AND a.clock_in_am <= concat(a.days, ' 13:30:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')
	THEN 4.5
	WHEN a.clock_in_am > concat(a.days, ' 10:00:00') AND a.clock_in_am <= concat(a.days, ' 12:00:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')
	THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, concat(a.days, ' 18:00:00')) / 60 - 1.5
	
	WHEN a.clock_in_am > concat(a.days, ' 13:30:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')
	THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60
	WHEN a.clock_in_am > concat(a.days, ' 12:00:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')
	THEN TIMESTAMPDIFF(MINUTE, concat(a.days, ' 13:30:00'), a.clock_out_pm) / 60
	WHEN a.clock_in_am > concat(a.days, ' 10:00:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')
	THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60 - 1.5
	ELSE 0
	END
	END as hour_count,

t1.hour_count AS theory_hour_count, a.organization_id, @checkworkTypeId, a.days, a.`year_month` as year_months
FROM emp_attendance a
INNER JOIN theory_attendance t1 on a.days = t1.days
INNER JOIN days t2 ON a.days = t2.days AND t2.is_work_flag = 1
-- 					ON DUPLICATE KEY UPDATE
-- 							customer_id = t.customer_id,
-- 							emp_key = t.emp_key,
-- 							emp_id = t.emp_id,
-- 							user_name_ch = t.user_name_ch,
-- 							hour_count = (TIMESTAMPDIFF(MINUTE ,clock_in, clock_out) / 60) -1,
-- 							theory_hour_count = t1.hour_count,
-- 							organization_id = t.organization_id,
-- 							checkwork_type_id = t.checkwork_type_id,
-- 							days = t.days
WHERE a.days = yesterday AND a.clock_in_am IS NOT NULL AND a.clock_out_pm IS NOT null	 




SELECT
	vde.emp_key, vde.emp_id, vde.user_name_ch, 0, t1.hour_count, vde.organization_id, @checkworkTypeId, yesterday, DATE_FORMAT(yesterday, '%Y%m')
FROM v_dim_emp vde
INNER JOIN theory_attendance t1 ON t1.days = yesterday
INNER JOIN days t2 ON t2.days = t1.days AND t2.is_work_flag = 1
WHERE NOT EXISTS ( SELECT 1 FROM emp_attendance_day ead WHERE ead.days = yesterday AND ead.emp_id = vde.emp_id );
* */
