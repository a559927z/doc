/**
*net.chinahrd.util
*/
package net.chinahrd.util;

/**
 * @author htpeng 2017年4月19日下午2:40:06
 */
public class EntityDev {
	public static void main(String[] f) {
		devEntity();
		devSourceTable();
//		devETL();
	}

	/**
	 * 生成实体类
	 */
	private static void devEntity() {
		ConstuctorDataBaseEntity.init("D:\\MyXFiles\\svn\\workspace\\chinahrd-hrbi\\hrd-etl\\src\\main\\java",
				"net.chinahrd.etl.entity");
	}

	private static void devETL() {
		ConstuctorETL.init("D:\\MyXFiles\\svn\\workspace\\chinahrd-hrbi\\hrd-etl\\src\\main\\java",
				"net.chinahrd.etl.impl.tmp");
	}

	/**
	 * 插入原表
	 */
	private static void devSourceTable() {
		SourceTableStatus.init();
	}
}
