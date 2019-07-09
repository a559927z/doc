//package net.chinahrd.etl.impl.empAttendance;
//
//import java.sql.ResultSet;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import java.util.Map.Entry;
//
//import org.joda.time.DateTime;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import net.chinahrd.etl.AbstractEtl;
//import net.chinahrd.etl.Entity;
//import net.chinahrd.etl.entity.SourceEmpAttendanceJavaEntity;
//import net.chinahrd.etl.sql.BatchSqlConfig;
//import net.chinahrd.etl.tmp.EmpAttendanceDto;
//import net.chinahrd.utils.CollectionKit;
//import net.chinahrd.utils.Identities;
//import net.chinahrd.utils.db.DatabaseUtil;
//
///**
// * 连接考勤机<br>
// * ip:172.16.9.55<br>
// * post:1433<br>
// * dbname:a1<br>
// * 
// * @author jxzhang on 2017年4月11日
// * @Verdion 1.0 版本
// */
//public class EmpAttendAnceEtl extends AbstractEtl<SourceEmpAttendanceJavaEntity> {
//
//	public static String url = "jdbc:jtds:sqlserver://172.16.9.55:1433/a1;instance=SQLEXPRESS";
//	private static String user = "zrw";
//	private static String password = "abcd1234@";
//
//	// 正常上班ID
//	private final String NORMAL = "b90bb95e3c01413b80899b49ba13392e";
//
//	public static String url2 = "jdbc:mysql://42.62.24.7:3369/mup-zrw";
//	public static String user2 = "mup";
//	public static String password2 = "1a2s3d123";
//
//	@Override
//	public boolean isActive() {
//		return true;
//	}
//
//	@Override
//	public List<SourceEmpAttendanceJavaEntity> query() throws Throwable {
//		StringBuffer sql = new StringBuffer();
//		sql.delete(0, sql.length());
//		sql.append(
//				"SELECT a.*, getdate() refreshDate FROM ( SELECT a.CardID, a.EmplName, a.RecDate, MIN (a.RecTime) RecTime FROM ( SELECT rank () OVER ( partition BY cardid, recdate ORDER BY rectime ) show_index, a.CardID, a.EmplName, b.RecDate, b.RecTime FROM HrEmployee a INNER JOIN AtdRecord b ON a.CardID = b.CardNo ) a GROUP BY a.CardID, a.EmplName, a.RecDate UNION SELECT a.CardID, a.EmplName, a.RecDate, MAX (a.RecTime) RecTime FROM ( SELECT rank () OVER ( partition BY cardid, recdate ORDER BY rectime ) show_index, a.CardID, a.EmplName, b.RecDate, b.RecTime FROM HrEmployee a INNER JOIN AtdRecord b ON a.CardID = b.CardNo ) a GROUP BY a.CardID, a.EmplName, a.RecDate ) a WHERE recdate < CONVERT (VARCHAR(100), GETDATE(), 23) AND recdate >= CONVERT ( VARCHAR (100), dateadd(dd ,- 1, getdate()), 23 ) ORDER BY a.RecDate DESC, a.CardID");
//		List<EmpAttendanceDto> rsLv1 = CollectionKit.newList();
//		DatabaseUtil databaseUtil = new DatabaseUtil(url, user, password, SQL_SERVER_DRIVER);
//		ResultSet rs = databaseUtil.query(sql.toString());
//		String rsScriptSQL = "";
//		while (rs.next()) {
//			String CardId = rs.getString("CardId");
//			String EmplName = rs.getString("EmplName");
//			String RecDate = rs.getString("RecDate");
//			String RecTime = rs.getString("RecTime");
//			String refreshDate = rs.getString("refreshDate");
//			rsScriptSQL += CardId + " " + EmplName + " " + RecDate + " " + RecTime + " " + refreshDate;
//			rsScriptSQL += "\r\n";
//
//			rsLv1.add(new EmpAttendanceDto(CardId, EmplName, RecDate, RecTime, refreshDate));
//		}
//		System.out.println(rsScriptSQL);
//
//		List<EmpAttendanceDto> rsLv2 = getEmpId(rsLv1);
//		List<SourceEmpAttendanceJavaEntity> rsLv3 = CollectionKit.newList();
//
//		MultiValueMap<String, EmpAttendanceDto> map = getMapOfMultiValueMap(rsLv2);
//		for (Entry<String, List<EmpAttendanceDto>> empEntry : map.entrySet()) {
//
//			List<EmpAttendanceDto> groupByList = empEntry.getValue();
//			String emp_attendance_id = Identities.uuid2();
//			String customer_id = Entity.CUSTOMER_ID;
//			String emp_key = groupByList.get(0).getEmp_key();
//			String emp_id = groupByList.get(0).getEmp_id();
//			String user_name_ch = groupByList.get(0).getUser_name_ch();
//			String clock_in_am = null;
//			String clock_out_am = null;
//			String clock_in_pm = null;
//			String clock_out_pm = null;
//			String opt_in = null;
//			String opt_out = null;
//			String opt_reason = null;
//			String cal_hour = null;
//			String organization_id = groupByList.get(0).getOrganization_id();
//			String checkwork_type_id = NORMAL;
//			String note = null;
//			String days = groupByList.get(0).getRecDate();
//			Integer year_month = Integer.valueOf(new DateTime(groupByList.get(0).getRecDate()).toString("yyyyMM"));
//			String refresh = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
//
//			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//			if (groupByList.size() > 1) {
//				String v1 = groupByList.get(0).getRecTime();
//				String v2 = groupByList.get(1).getRecTime();
//				if (sdf.parse(v1).getTime() < sdf.parse(v2).getTime()) {
//					clock_in_am =groupByList.get(0).getRecDate() + " "+ v1;
//					clock_out_pm =groupByList.get(1).getRecDate() + " "+  v2;
//				} else {
//					clock_in_am =groupByList.get(1).getRecDate() + " "+  v2;
//					clock_out_pm = groupByList.get(0).getRecDate() + " "+ v1;
//				}
//			} else {
//				clock_in_am = groupByList.get(0).getRecDate() + " "+ groupByList.get(0).getRecTime();
//			}
//
//			rsLv3.add(new SourceEmpAttendanceJavaEntity(
//					emp_attendance_id, customer_id, emp_key, emp_id, user_name_ch,
//					clock_in_am, clock_out_am, clock_in_pm, clock_out_pm, opt_in, opt_out, opt_reason, cal_hour,
//					checkwork_type_id, note, days, year_month));
//		}
//		databaseUtil.close();
//		return rsLv3;
//	}
//
//	/**
//	 * 对传入来list，补上empId和empKey值
//	 * 
//	 * @param list
//	 * @return
//	 * @throws Throwable
//	 */
//	public List<EmpAttendanceDto> getEmpId(List<EmpAttendanceDto> list) throws Throwable {
//		StringBuffer sql = new StringBuffer();
//		sql.delete(0, sql.length());
//		sql.append("SELECT emp_id, emp_key, user_name_ch, organization_id from v_dim_emp");
//
//		List<EmpAttendanceDto> rsList = CollectionKit.newList();
//		DatabaseUtil databaseUtil = new DatabaseUtil(url2, user2, password2, MY_SQL_DRIVER);
//		ResultSet rs = databaseUtil.query(sql.toString());
//		while (rs.next()) {
//			String v1 = rs.getString("emp_id");
//			String v2 = rs.getString("emp_key");
//			String v3 = rs.getString("user_name_ch");
//			String v4 = rs.getString("organization_id");
//			rsList.add(new EmpAttendanceDto(v1, v2, v3, v4));
//		}
//		for (EmpAttendanceDto dto : list) {
//			for (EmpAttendanceDto rsItem : rsList) {
//				if (dto.getEmpIName().equals(rsItem.getUser_name_ch())) {
//					dto.setEmp_id(rsItem.getEmp_id());
//					dto.setEmp_key(rsItem.getEmp_key());
//					break;
//				}
//			}
//		}
//		databaseUtil.close();
//		return list;
//	}
//
//	/**
//	 * empKey 分组
//	 * 
//	 * @param rs
//	 * @return
//	 */
//	private MultiValueMap<String, EmpAttendanceDto> getMapOfMultiValueMap(List<EmpAttendanceDto> rs) {
//		MultiValueMap<String, EmpAttendanceDto> m = new LinkedMultiValueMap<String, EmpAttendanceDto>();
//		for (EmpAttendanceDto dto : rs) {
//			m.add(dto.getEmp_key(), dto);
//		}
//		return m;
//	}
//
//	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//		System.out.println(sdf.parse("09:39").getTime() < sdf.parse("18:25").getTime());
//	}
//
//	@Override
//	public void setBatchQuery(BatchSqlConfig batchQuery) {
//	}
//}
