/**
*net.chinahrd.etl.writeTable
*/
package net.chinahrd.etl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.esotericsoftware.reflectasm.MethodAccess;

import net.chinahrd.annotation.configure.ColumnModel;
import net.chinahrd.annotation.configure.EntityAnnotationModel;
import net.chinahrd.annotation.core.AnnotationCore;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.PropertiesUtil;
import net.chinahrd.utils.db.DatabaseUtil;

/**
 * @author htpeng 2017年4月13日下午3:02:57
 */
public class WriteTable<T extends Entity> {
	/**
	 * props路径
	 */
	private String propsPath = "conf/default_database.properties";
	// 写数据连接
	public final String URL=PropertiesUtil.getProperty(propsPath, "w-url");
	public final String USER=PropertiesUtil.getProperty(propsPath, "w-user");
	public final String PASSWORD=PropertiesUtil.getProperty(propsPath, "w-password");
	public final String DRIVER =PropertiesUtil.getProperty(propsPath, "w-driver");
	
	private static final String inster = "INSERT INTO ";
	private static final String delete = "DELETE FROM ";

	
//	private static final String statusSqlHead = "UPDATE `mup-large`.sys_table_status set status =";
	
//	/**
//	 * 实体类对应Class
////	 */
//	private Class<? extends Entity> clazz;
	
	/**
	 * 实体类对应MethodAccess
	 */
	private MethodAccess access ;

	/**
	 * 状态表操作类
	 */
	private StatusTable statusTable;
	
	/**
	 * 实体类注解信息封装模型
	 */
	private EntityAnnotationModel entityAnnotationModel;

	/**
	 * 数据库操作类
	 */
	private DatabaseUtil databaseUtil;
	/**
	 * 删除表SQL
	 */
	private String deleteSql;
	/**
	 * Mysql 批量插入
	 */
	private PreparedStatement psts;
	
	
	/**
	 * 构造方法
	 * 
	 * @param clazz
	 *            实体类的calss
	 */
	public WriteTable(Class<T> clazz,boolean isPreparedStatementWrite) {
		access = MethodAccess.get(clazz);
		try {
			statusTable=new StatusTable();
			entityAnnotationModel = AnnotationCore.getInstance().getEntityAnnotationModel(clazz);
			StringBuffer sb = new StringBuffer(delete);
			sb.append(entityAnnotationModel.getTableName());
			this.deleteSql = sb.toString();
			this.databaseUtil = new DatabaseUtil(URL, USER, PASSWORD, DRIVER);
			if(isPreparedStatementWrite){
				 psts = databaseUtil.getConnection().prepareStatement(getBatchWriteSql());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取插入SQL语句
	 * 
	 * @param list
	 * @return
	 */
	private String getInsertSql(List<? extends Entity> list) {
		StringBuffer instersql = new StringBuffer(inster);
		instersql.append(entityAnnotationModel.getTableName());
		instersql.append("(");
		for (int i = 0; i < entityAnnotationModel.getColList().size(); i++) {
			ColumnModel columnModel = entityAnnotationModel.getColList().get(i);
			if (i > 0) {
				instersql.append(",");
			}
			instersql.append("`");
			instersql.append(columnModel.getColname());
			instersql.append("`");
		}
		instersql.append(") VALUES");
		for (int j = 0; j < list.size(); j++) {
			Entity entity = list.get(j);
			if (j > 0) {
				instersql.append(",");
			}
			instersql.append("(");
			entity.getClass().getDeclaredMethods();
			for (int i = 0; i < entityAnnotationModel.getColList().size(); i++) {
				ColumnModel columnModel = entityAnnotationModel.getColList().get(i);
				Object value = access.invoke(entity, columnModel.getMethodName());
				if (i > 0) {
					instersql.append(",");
				}
				instersql.append(columnModel.getValue(value));
			}
			instersql.append(")");
			entity=null;
		}
		instersql.append(";");
		return instersql.toString();
	}
	
	
	private String getBatchWriteSql(){
		StringBuffer instersql = new StringBuffer(inster);
		instersql.append(entityAnnotationModel.getTableName());
		instersql.append("(");
		StringBuffer instersqlParm = new StringBuffer();
		for (int i = 0; i < entityAnnotationModel.getColList().size(); i++) {
			ColumnModel columnModel = entityAnnotationModel.getColList().get(i);
			if (i > 0) {
				instersql.append(",");
				instersqlParm.append(",");
			}
			instersql.append("`");
			instersql.append(columnModel.getColname());
			instersql.append("`");
			instersqlParm.append("?");
		}
		instersql.append(") VALUES(").append(instersqlParm.toString()).append(")");
		System.out.println("write sql:"+instersql.toString());
		return instersql.toString();
	}
	
	/**
	 * 批量插入
	 * 
	 * @param list
	 * @return
	 * @throws SQLException 
	 */
	private void batchInsert(List<? extends Entity> list) throws SQLException {
//		MethodAccess pstsAccess = MethodAccess.get(PreparedStatement.class);
		for (int j = 0; j < list.size(); j++) {
			Entity entity = list.get(j);
			for (int i = 0; i < entityAnnotationModel.getColList().size(); i++) {
				ColumnModel columnModel = entityAnnotationModel.getColList().get(i);
//				pstsAccess.invoke(psts, columnModel.getType().getSetMethodName(), i+1,
//						access.invoke(entity, columnModel.getMethodName()));
				psts.setObject(i+1,access.invoke(entity, columnModel.getMethodName()));
			}
			psts.addBatch();
			if((j!=0 && j%200==0)){//可以设置不同的大小；如50，100，200，500，1000等等  
				psts.executeBatch();  
                //优化插入第三步       提交，批量插入数据库中。
                psts.clearBatch();        //提交后，Batch清空。
            }
			entity=null;
		}
		psts.executeBatch();
	}
	
	/**
	 * 写入表操作
	 * 
	 * @param list
	 * @return
	 */
	public void write(List<T> list) throws Throwable {
		if (!(CollectionKit.isEmpty(list))) {
			if(null!=psts){
				 batchInsert(list);
			}else{
				databaseUtil.saveOrUpdate(getInsertSql(list));
			}
		}
	}
	
	/**
	 * 清空原表数据
	 * @throws Throwable
	 */
	public void clear() throws Throwable {
		databaseUtil.saveOrUpdate(deleteSql);
	}

	/**
	 * 写入完成
	 */
	public void over(int status) {
		try {
			databaseUtil.saveOrUpdate(statusTable.update(status,entityAnnotationModel.getTableName()));
		} catch (SQLException e) {
			
			try {
				databaseUtil=new DatabaseUtil(URL, USER, PASSWORD, DRIVER);
				databaseUtil.saveOrUpdate(statusTable.update(status,entityAnnotationModel.getTableName()));
			} catch (SQLException e1) {
				
			}
		} finally {
			databaseUtil.close();
			if(null!=psts){
				try {
					psts.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
//	private String getStatusSql(int status) {
//		StringBuffer ss = new StringBuffer(statusSqlHead);
//		ss.append(" ");
//		ss.append(status);
//		ss.append(" WHERE table_name = '");
//		ss.append(entityAnnotationModel.getTableName());
//		ss.append("'");
//		System.out.println(ss);
//		return ss.toString();
//	}
}
