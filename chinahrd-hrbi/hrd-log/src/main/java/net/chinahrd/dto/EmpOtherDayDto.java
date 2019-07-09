package net.chinahrd.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
@Data
public class EmpOtherDayDto extends BaseEmpDto implements Serializable {
    private static final long serialVersionUID = 2709585366845305859L;
    private String typeName;
    private String num;
    private String days;

}
