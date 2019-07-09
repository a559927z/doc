package net.chinahrd.modules.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.chinahrd.common.BusinessEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author bright
 * @since 2019-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("demo_emp_info")
public class EmpInfo extends BusinessEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工Id
     */
    private String empId;
    /**
     * 员工名称
     */
    private String empName;


}
