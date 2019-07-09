/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

import org.apache.ibatis.session.SqlSessionFactory;

/**数据库session管理
 * @author htpeng
 *2016年10月12日上午12:41:45
 */
public class CacheSessionManager {
	private static SqlSessionFactory  ssf=null;
	public static void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		if(null!=sqlSessionFactory){
			ssf=sqlSessionFactory;
		}
	}
	static SqlSessionFactory getSqlSessionFactory(){
		return ssf;
	}
}
