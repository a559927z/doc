package net.chinahrd.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

/**
 * @author jxzhang
 */
@ToString
@Data
@EqualsAndHashCode
public class BaseEmpDto extends BaseUserDto implements Serializable {

    private static final long serialVersionUID = -2907317845583823198L;

    private String empId;
    private String empKey;

    /**
     * 父级机构名称
     */
    private String orgParName;
    /**
     * 所在机构名称
     */
    private String orgName;
    /**
     * 角色字符串拼接
     */
    private String roles;

    /**
     * 角色集
     */
    private List<String> roleKeyList;


    @Test
    public void testToString() {
        BaseEmpDto baseEmpDto = new BaseEmpDto();
        baseEmpDto.setEmpId("11");

        System.out.println(baseEmpDto);
    }

}
