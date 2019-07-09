package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;

/**
 * 关键人才库核心激励要素dto
 * Created by htpeng on 16/01/21 0021.
 */
public class EncourageDto implements Serializable {
 
	private static final long serialVersionUID = 353776220564567415L;
	
    private String encourageId;                       //关键人才要素维度ID

    private String content;                     //内容
    private int select;                     //是否选中   0-未选中  1-选中
	public String getEncourageId() {
		return encourageId;
	}
	public void setEncourageId(String encourageId) {
		this.encourageId = encourageId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getSelect() {
		return select;
	}
	public void setSelect(int select) {
		this.select = select;
	}
    
    
    
    
}
