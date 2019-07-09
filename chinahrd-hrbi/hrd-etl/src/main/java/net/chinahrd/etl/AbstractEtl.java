/**
*net.chinahrd.etl
*/
package net.chinahrd.etl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.joda.time.DateTime;

import net.chinahrd.etl.sql.BatchSqlHandle;
import net.chinahrd.utils.PropertiesUtil;
import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 抽象的数据抽取类
 * 
 * @author htpeng 2017年4月11日下午4:24:02
 */
public abstract class AbstractEtl<T extends Entity> implements Etl ,BatchQuery{
 
	/**
	 * 当前操作Etl的更新时间
	 */
	private String refresh=DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * props数据路径
	 */
	private static String propsPath = "conf/default_database.properties";
	// 读数据连接
	public static final String URL=PropertiesUtil.getProperty(propsPath, "r-url");
	public static final String USER=PropertiesUtil.getProperty(propsPath, "r-user");
	public static final String PASSWORD=PropertiesUtil.getProperty(propsPath, "r-password");
	
	/**
	 * props系统配置路径
	 */
	private static String propsPath2 = "conf/config.properties";
	public static final String CUSTOMER_ID=PropertiesUtil.getProperty(propsPath2, "customer.id");
	
	/**
	 * 分页查询Sql操作类
	 */
	private BatchSqlHandle batchSqlHandle =new BatchSqlHandle();
	
	/**
	 * 数据库操作类
	 */
	protected DatabaseUtil databaseUtil;
	

	/**
	 * 获取查询数据
	 * 
	 * @return
	 */
	public abstract List<T> query() throws Throwable;

	/**
	 * 子类重新该方法设置是否使用PreparedStatement进行插入
	 * 获取是否使用批处理方式
	 * @param sql
	 * @return
	 */
	protected  boolean isPreparedStatementWrite(){
		return false;
	}
	
	/**
	 * 写入表同步类
	 */
	private WriteTable<T> writeTable;
	
	/**
	 * 实体类 Class
	 */
	protected Class<T> clazz;

	/**
	 * 通过子类继承本类指定泛型T,得到实体类
	 */
	@SuppressWarnings("unchecked")
	public AbstractEtl() {
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
			this.writeTable = new WriteTable<T>(this.clazz,isPreparedStatementWrite());
	}
	
	/**
	 * 设置查询Sql
	 * @param sql
	 * @return
	 */
	protected String getQuerySql(String sql){
		return batchSqlHandle.getQuerySql(sql);
	}
	protected void init(){
//		batchSqlHandle=AlloteBatchSql.getBatchSql(driver);
	}
	/**
	 * 执行
	 */
	@Override
	public void execute() {
		if (isActive()) {
			try {
				init();
				setBatchQuery(batchSqlHandle);
				batchSqlHandle.init();
				writeTable.clear();
				while(batchSqlHandle.next()){
					writeTable.write(query());
				}
				writeTable.over(SUCCESS);
			} catch (Throwable e) {
				writeTable.over(ERROR);
				e.printStackTrace();
//				String to = "a559927z@163.com";
//				String title = "ETL Exception";
//				String content = e.getMessage();
//				MailUtil.send(to, title, content);
			}finally{
				batchSqlHandle.close();
				if(null!=databaseUtil){
					databaseUtil.close();
				}
			}
		}else{
			writeTable.over(SUCCESS);
		}
	}

	/**
	 * 获取更新时间
	 */
	@Override
	public String getRefresh() {
		return refresh;
	}
}
