/**
*net.chinahrd.core.menu.modle
*/
package net.chinahrd.core.menu.model;

import java.util.List;


/**
 * 菜单--数据表  实体类
 * @author htpeng
 *2016年10月12日下午2:04:39
 */
public class MenuEntity {
	private String functionId;
	private String customerId;
	private String functionKey;
	private String functionName;
	private String functionParentId;
	private String url;
	private int isMenu;
	private String fullPath;
	private String note;
	private int showIndex;
	private String quotaOrFun;
	private String createUserId;
	private String modifyUserId;
	private String createTime;
	private String modifyTime;
	List<BlockEntity> blocks;
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFunctionKey() {
		return functionKey;
	}
	public void setFunctionKey(String functionKey) {
		this.functionKey = functionKey;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionParentId() {
		return functionParentId;
	}
	public void setFunctionParentId(String functionParentId) {
		this.functionParentId = functionParentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(int isMenu) {
		this.isMenu = isMenu;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(int showIndex) {
		this.showIndex = showIndex;
	}
	public String getQuotaOrFun() {
		return quotaOrFun;
	}
	public void setQuotaOrFun(String quotaOrFun) {
		this.quotaOrFun = quotaOrFun;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public List<BlockEntity> getBlocks() {
		return blocks;
	}
	public void setBlocks(List<BlockEntity> blocks) {
		this.blocks = blocks;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str= "\n菜单："+this.functionId+","+this.functionName+","+this.note+","+this.url;
		for(BlockEntity b:blocks){
			str+="\n块："+b;
		}
		return str;
	}

	
	
	

}
