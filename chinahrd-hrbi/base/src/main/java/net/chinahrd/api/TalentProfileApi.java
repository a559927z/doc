///**
//*net.chinahrd.entity.module
//*/
//package net.chinahrd.api;
//
//
//
//import net.chinahrd.api.module.Modules;
//import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;
//import net.chinahrd.core.api.define.ApiDoc;
//
/**
 * 人才剖象的 Api 接口
 * @author zwliang
 *2016年11月8日上午1050
 */

/**
*net.chinahrd.entity.module
*/
package net.chinahrd.api;



import java.util.List;

import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.JobChangeDto;

/**
 * 主动流失率定义的 Api 接口
 * @author htpeng
 *2016年10月17日下午6:09:24
 */
public interface TalentProfileApi {

    /**
     * 根据员工ID查询工作异动信息（多个员工id用“,”号隔开）
     *
     * @param empId
     * @param customerId
     * @param changeType 异动类型  1：晋升，2：调入，3：入职，4：调出，5：离职
     * @return
     */
    List<JobChangeDto> queryJobChange(String empId, String customerId,Integer changeType);

    /**
     * 根据员工ID查询员工详细信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    EmpDetailDto findEmpDetail(String empId, String customerId);
    
}
