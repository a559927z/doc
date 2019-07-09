/**
*net.chinahrd.etl
*/
package net.chinahrd.etl;

/**
 * 数据抽取接口
 * 
 * @author htpeng 2017年4月11日下午4:24:02
 */
public interface Etl {

	/**
	 * SQL SERVER 驱动类
	 */
	final String SQL_SERVER_DRIVER="net.sourceforge.jtds.jdbc.Driver";
	
	/**
	 * MYSQL 驱动类
	 */
	final String MY_SQL_DRIVER="com.mysql.jdbc.Driver";
	
	/**
	 * ORACLE 驱动类
	 */
	final String ORACLE_DRIVER="oracle.jdbc.driver.OracleDriver";
	
	/**
	 * 开始写入
	 */
	final int START =1;

	/**
	 * 写入成功
	 */
	final int SUCCESS =2;
	
	/**
	 * 写入失败
	 */
	final int ERROR =3;
	/**
	 * 执行各指标抽取操作
	 * 
	 */
	void execute();
	
	
	/**
	 * 是否激活
	 * 
	 * @return
	 */
	public abstract boolean isActive();

	/**
	 * 获取更新时间
	 * 
	 * @return
	 */
	String getRefresh();
}
