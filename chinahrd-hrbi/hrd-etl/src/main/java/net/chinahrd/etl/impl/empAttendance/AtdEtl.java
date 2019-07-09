//package net.chinahrd.etl.impl.empAttendance;
//
//import java.awt.Toolkit;
//import java.sql.ResultSet;
//import java.util.List;
//
//import net.chinahrd.etl.AbstractEtl;
//import net.chinahrd.etl.entity.SourceEmpAttendanceEntity;
//import net.chinahrd.etl.sql.BatchSqlConfig;
//import net.chinahrd.utils.CollectionKit;
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
//public class AtdEtl extends AbstractEtl<SourceEmpAttendanceEntity>{
//
//	public static String url = "jdbc:jtds:sqlserver://172.16.9.55:1433/a1;instance=SQLEXPRESS";
//	private static String user = "zrw";
//	private static String password = "abcd1234@";
//	private static String driverClassName = "net.sourceforge.jtds.jdbc.Driver";
//	
//	@Override
//	public boolean isActive() {
//		return true;
//	}
//	
//	@Override
//	public List<SourceEmpAttendanceEntity> query() throws Throwable {
//		StringBuffer sql = new StringBuffer();
//		sql.delete(0, sql.length());
//		sql.append(
//				"SELECT a.*, getdate() refreshDate FROM ( SELECT a.CardID, a.EmplName, a.RecDate, MIN (a.RecTime) RecTime FROM ( SELECT rank () OVER ( partition BY cardid, recdate ORDER BY rectime ) show_index, a.CardID, a.EmplName, b.RecDate, b.RecTime FROM HrEmployee a INNER JOIN AtdRecord b ON a.CardID = b.CardNo ) a GROUP BY a.CardID, a.EmplName, a.RecDate UNION SELECT a.CardID, a.EmplName, a.RecDate, MAX (a.RecTime) RecTime FROM ( SELECT rank () OVER ( partition BY cardid, recdate ORDER BY rectime ) show_index, a.CardID, a.EmplName, b.RecDate, b.RecTime FROM HrEmployee a INNER JOIN AtdRecord b ON a.CardID = b.CardNo ) a GROUP BY a.CardID, a.EmplName, a.RecDate ) a WHERE recdate < CONVERT (VARCHAR(100), GETDATE(), 23) AND recdate >= CONVERT ( VARCHAR (100), dateadd(dd ,- 1, GETDATE()), 23 )");
//		
////		SELECT a.*, getdate() refreshDate 
////		FROM 
////			(
////		    SELECT a.CardID, a.EmplName, a.RecDate,min(a.RecTime) RecTime 
////		    from (
////				SELECT rank() over(partition by cardid, recdate order by rectime) show_index, a.CardID, a.EmplName, b.RecDate, b.RecTime 
////				FROM HrEmployee a
////				INNER JOIN AtdRecord b on a.CardID = b.CardNo
////			 ) a
////		    group by  a.CardID, a.EmplName, a.RecDate
////		UNION 
////		    select a.CardID, a.EmplName, a.RecDate,max(a.RecTime) RecTime 
////		    from (
////		        SELECT rank() over(partition by cardid, recdate order by rectime) show_index, a.CardID, a.EmplName, b.RecDate, b.RecTime 
////		        from HrEmployee a
////		        INNER JOIN AtdRecord b on a.CardID = b.CardNo
////		        ) a 
////		    group by a.CardID, a.EmplName, a.RecDate) a 
////		    where recdate<CONVERT(varchar(100), GETDATE(), 23) and recdate>=convert(varchar(100),dateadd(dd,-1,GETDATE()),23)
////		    order by a.RecDate DESC, a.CardID
////		;
//
//		List<SourceEmpAttendanceEntity> rsList = CollectionKit.newList();
//		DatabaseUtil databaseUtil = new DatabaseUtil(url, user, password, driverClassName);
//		ResultSet rs = databaseUtil.query(getQuerySql(sql.toString()));
//		String rsScriptSQL = "";
//		while (rs.next()) {
//			String CardId = rs.getString("CardId");
//			String EmplName = rs.getString("EmplName");
//			String RecDate = rs.getString("RecDate");
//			String RecTime = rs.getString("RecTime");
//			String refreshDate = rs.getString("refreshDate");
//			rsScriptSQL +=  rs.getString("ROWNUMBER")+ " " +CardId + " " + EmplName + " " + RecDate + " " + RecTime + " " + refreshDate;
//			rsScriptSQL += "\r\n";
//			rsList.add(new SourceEmpAttendanceEntity(CardId, EmplName, RecDate, RecTime, refreshDate));
//		}
//		System.out.println(rsScriptSQL);
//		databaseUtil.close();
//		return rsList;
//	}
//
//	@Override
//	public void setBatchQuery(BatchSqlConfig config) {
//		config.setBatchNum(3);
//		config.setBatchSql(url, user, password, driverClassName, "SELECT a.*, getdate() refreshDate FROM ( SELECT a.CardID, a.EmplName, a.RecDate, MIN (a.RecTime) RecTime FROM ( SELECT rank () OVER ( partition BY cardid, recdate ORDER BY rectime ) show_index, a.CardID, a.EmplName, b.RecDate, b.RecTime FROM HrEmployee a INNER JOIN AtdRecord b ON a.CardID = b.CardNo ) a GROUP BY a.CardID, a.EmplName, a.RecDate UNION SELECT a.CardID, a.EmplName, a.RecDate, MAX (a.RecTime) RecTime FROM ( SELECT rank () OVER ( partition BY cardid, recdate ORDER BY rectime ) show_index, a.CardID, a.EmplName, b.RecDate, b.RecTime FROM HrEmployee a INNER JOIN AtdRecord b ON a.CardID = b.CardNo ) a GROUP BY a.CardID, a.EmplName, a.RecDate ) a WHERE recdate < CONVERT (VARCHAR(100), GETDATE(), 23) AND recdate >= CONVERT ( VARCHAR (100), dateadd(dd ,- 1, GETDATE()), 23 ) ");
//		config.setOrderColumn("CardId");
//	}
//
//	public static void main(String...strings){
//		Toolkit.getDefaultToolkit().beep();
//	}
//}
