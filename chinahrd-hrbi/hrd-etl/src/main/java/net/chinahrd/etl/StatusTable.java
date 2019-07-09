/**
*net.chinahrd.etl
*/
package net.chinahrd.etl;

/**状态表操作表
 * @author htpeng
 *2017年4月28日上午10:20:47
 */
public class StatusTable {
	private static final String STATUS_SQL = "UPDATE `mup-large`.sys_table_status set status =";
	
	/**
	 * 将系统过程 状态全部置为 1-未开始状态
	 */
	public static final String PSROCESS_STATUS_SQL="UPDATE `mup-large`.sys_sync_status set status = 1";

	
	/**
	 * 全部状态改为开始
	 * @return
	 */
	public String updateAllStart(){
		StringBuffer ss = new StringBuffer(STATUS_SQL);
		ss.append(" ");
		ss.append(Etl.START);
		return ss.toString();
	}
	
	/**
	 * 修改某表的状态
	 * @param status
	 * @param tableName
	 * @return
	 */
	public String update(int status,String tableName){
		StringBuffer ss = new StringBuffer(STATUS_SQL);
		ss.append(" ");
		ss.append(status);
		ss.append(" WHERE table_name = '");
		ss.append(tableName);
		ss.append("'");
		return ss.toString();
	}
	
	
	
	
}
