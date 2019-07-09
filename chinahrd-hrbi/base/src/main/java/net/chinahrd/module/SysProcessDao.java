/**
 * net.chinahrd.cache
 */
package net.chinahrd.module;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.ProcessErrorDto;

/**
 * sys缓存
 *
 * @author jxzhang on 2016-11-07
 */
@Repository("sysProcessDao")
public interface SysProcessDao {
	
	/**
	 * 检查系统过程执行状态
	 * @return
	 */
	int checkSysProcessStatus();
	/**
     * 查询系统所依赖的原表
     *
     * @return
     */
    List<String> querySysSourceTable();

	/**
     * 查询系统所依赖的原表发生错误的数量
     *
     * @return
     */
    int querySysSourceTableErrorNum(@Param("list")List<String> list);
    /**
     * 查询系统所依赖的原表抽取完成的数量
     *
     * @return
     */
    int querySysSourceTableCompleteNum(@Param("list")List<String> list);

    /**
     * 执行存储过程
     *
     * @return
     */
    List<ProcessErrorDto> callSysProcess(@Param("p_customer_id")String customerId,@Param("p_user_id")String userId);

}
