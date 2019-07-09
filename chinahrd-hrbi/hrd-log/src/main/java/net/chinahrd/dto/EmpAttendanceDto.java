package net.chinahrd.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *
 */
@Data
@EqualsAndHashCode
public class EmpAttendanceDto extends BaseEmpDto implements Serializable {
    private static final long serialVersionUID = 2709585366845305859L;

    private String annualTotal;
    private String annualDelayed;
    private String annual;
    private String canLeave;
    private String historyHour;

}
