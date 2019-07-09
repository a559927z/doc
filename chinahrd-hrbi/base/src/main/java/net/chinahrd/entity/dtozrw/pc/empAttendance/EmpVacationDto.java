package net.chinahrd.entity.dtozrw.pc.empAttendance;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jxzhang 2018年12月03日下午14:33:00
 */
@Data
public class EmpVacationDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5353835241577095880L;

    private String id;
    private String empId;
    private String userName;
    private float annualTotal;
    private float annualDelayed;
    private int year;

}
