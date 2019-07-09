/**
*net.chinahrd.etl
*/
package net.chinahrd.etl;

import java.sql.ResultSet;
import java.util.List;

import com.esotericsoftware.reflectasm.MethodAccess;

import net.chinahrd.etl.sql.BatchSqlConfig;
import net.chinahrd.etl.sql.SqlAuxiliary;
import net.chinahrd.etl.sql.SqlColModel;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 标准型的抽象数据抽取类
 * 
 * @author htpeng 2017年4月11日下午4:24:02
 */
public abstract class SimpleAbstractEtl<T extends Entity> extends AbstractEtl<T> {

	/**
	 * ResultSet 字节码
	 */
	private static final MethodAccess resultSetAccess = MethodAccess.get(ResultSet.class);

	/**
	 * 实体类的 字节码
	 */
	private MethodAccess entityAccess;



	private SqlAuxiliary sqlAuxiliary;

	/**
	 * 获取查询数据
	 * 
	 * @return
	 */
	public List<T> query() throws Throwable {
		ResultSet rs = databaseUtil.query(getQuerySql(sqlAuxiliary.getSql()));
		List<T> list = CollectionKit.newList();
		List<SqlColModel> colList = sqlAuxiliary.getSqlCol();
		while (rs.next()) {
			T t = clazz.newInstance();
			for (SqlColModel sqlColModel : colList) {
				entityAccess.invoke(t, sqlColModel.getMethodName(), 
						sqlColModel.formatData(
							sqlColModel.isQuery()? 
								resultSetAccess.invoke(rs, sqlColModel.getType(),new Class[] { String.class }, sqlColModel.getName()):
								null));
			}
			list.add(t);
		}
		return list;
	}
	@Override
	protected final void init(){
		databaseUtil = new DatabaseUtil(getUrl(), getUser(), getPassword(), getDriver());
	}
	
	@Override
	public void setBatchQuery(BatchSqlConfig batchQuery) {
		batchQuery.setBatchSql(databaseUtil, sqlAuxiliary.getSql());
	}
	/**
	 * 获取数据库连接 URL
	 * 
	 * @return
	 */
	protected abstract String getUrl();

	/**
	 * 获取数据库连接 User
	 * 
	 * @return
	 */
	protected abstract String getUser();

	/**
	 * 获取数据库连接 Password
	 * 
	 * @return
	 */
	protected abstract String getPassword();

	/**
	 * 获取数据库连接 Driver
	 * 
	 * @return
	 */
	protected abstract String getDriver();

	/**
	 * 获取SQL辅助类
	 * 
	 * @return
	 */
	protected abstract SqlAuxiliary getSqlAuxiliary();


	/**
	 * 通过子类继承本类指定泛型T,得到实体类
	 */
	public SimpleAbstractEtl() {
		if (isActive()) {
			entityAccess = MethodAccess.get(clazz);
			sqlAuxiliary = getSqlAuxiliary();
		}
	}

}
