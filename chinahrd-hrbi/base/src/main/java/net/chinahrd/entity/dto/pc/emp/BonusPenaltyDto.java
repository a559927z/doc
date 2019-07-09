package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;

/**
 * 奖惩信息dto
 */
public class BonusPenaltyDto  implements Serializable {
	
	private static final long serialVersionUID = 773395537008267262L;
	
	private String id;
    /**名称 */
    private String bonusPenaltyName;
    /**类型 */
    private String typeName;
	/** 授予单位 */
	private String grantUnit;
	/** 证明人 */
	private String witnessName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getBonusPenaltyName() {
		return bonusPenaltyName;
	}
	public void setBonusPenaltyName(String bonusPenaltyName) {
		this.bonusPenaltyName = bonusPenaltyName;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getGrantUnit() {
		return grantUnit;
	}
	public void setGrantUnit(String grantUnit) {
		this.grantUnit = grantUnit;
	}
	
	public String getWitnessName() {
		return witnessName;
	}
	public void setWitnessName(String witnessName) {
		this.witnessName = witnessName;
	}
	
}
