/**
*net.chinahrd.core.cache
*/
package net.chinahrd.core.cache;

/**自定义格式化缓存块
 * @author htpeng
 *2016年10月9日下午4:47:45
 */
public interface  CustomBlock<T> {

	/**格式化返回数据
	 * @param obj
	 * @return
	 */
	 T formatData(Object obj);

}
