/**
*net.chinahrd.biz.paper.mobile.dto.talentProfitLoss
*/
package net.chinahrd.entity.dto.app.talentProfitLoss;

import java.io.Serializable;

/**
 * @author htpeng
 *2016年8月24日下午4:13:40
 */
public class TalentProfitLossConfigDto implements Serializable{
	private static final long serialVersionUID = -2295661822800427704L;
	private int type;       //0  流入  1 流出
	private String name;
	private String code;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
