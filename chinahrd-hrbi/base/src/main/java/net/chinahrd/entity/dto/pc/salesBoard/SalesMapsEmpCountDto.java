package net.chinahrd.entity.dto.pc.salesBoard;

import java.io.Serializable;

/**
 * 销售地图人员统计Dto
 * Created by malong on 2016-11-16.
 */
public class SalesMapsEmpCountDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 机构ID
     */
    private String organId;
    /**
     * 机构名称
     */
    private String organName;
    /**
     * 人员统计信息
     */
    private Integer empCount;

    public SalesMapsEmpCountDto() {
    }

    public SalesMapsEmpCountDto(String organId, String organName) {
        this.organId = organId;
        this.organName = organName;
    }

    public Integer getEmpCount() {
        return null == empCount ? 0 : empCount;
    }

    public void setEmpCount(Integer empCount) {
        this.empCount = empCount;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}
