package net.chinahrd.entity.dto.pc.manage;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 预警人员大纲数据Dto
 * Created by htpeng on 15/12/08 0012.
 */
public class WarnSynopsisDto implements Serializable {
    private static final long serialVersionUID = 5021154907941311305L;

    private String id;                           
    private String customerId;                     
    private String organizationId;                        
    private String steward;                //管理者
    private String underEmp;                  //下属员工
    private int runOffNum;                    //离职风险
    private int highPerformanceNum;            //高绩效
    private int lowPerformanceNum;              //低绩效
    private int lifeNotGoodNum;                 //生活水平
    private Timestamp refresh;                  //刷新日期
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getSteward() {
		return steward;
	}
	public void setSteward(String steward) {
		this.steward = steward;
	}
	public String getUnderEmp() {
		return underEmp;
	}
	public void setUnderEmp(String underEmp) {
		this.underEmp = underEmp;
	}
	public int getRunOffNum() {
		return runOffNum;
	}
	public void setRunOffNum(int runOffNum) {
		this.runOffNum = runOffNum;
	}
	public int getHighPerformanceNum() {
		return highPerformanceNum;
	}
	public void setHighPerformanceNum(int highPerformanceNum) {
		this.highPerformanceNum = highPerformanceNum;
	}
	public int getLowPerformanceNum() {
		return lowPerformanceNum;
	}
	public void setLowPerformanceNum(int lowPerformanceNum) {
		this.lowPerformanceNum = lowPerformanceNum;
	}
	public int getLifeNotGoodNum() {
		return lifeNotGoodNum;
	}
	public void setLifeNotGoodNum(int lifeNotGoodNum) {
		this.lifeNotGoodNum = lifeNotGoodNum;
	}
	public Timestamp getRefresh() {
		return refresh;
	}
	public void setRefresh(Timestamp refresh) {
		this.refresh = refresh;
	}

    
}
