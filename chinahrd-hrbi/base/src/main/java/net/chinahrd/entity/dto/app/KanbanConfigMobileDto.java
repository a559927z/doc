package net.chinahrd.entity.dto.app;

import java.io.Serializable;
import java.sql.Timestamp;

import net.chinahrd.mvc.app.util.MobileHomeAuxiliary;

public class KanbanConfigMobileDto implements Serializable{
	
	private static final long serialVersionUID = -6390736812071193623L;
	
	private String functionConfigMobileId;//配置表主键
	 private String customerId            ;//
	 private String empId                 ;//用户ID
	 private String functionId            ;//指标或功能ID
	 private String functionName            ;//指标或功能ID
	 private String functionCode          ;//功能编码
	 private boolean isView               ;//是否显示
	 private String showIndex             ;//显示顺序
	 private String imageUrl              ;//图片路径
	 private String pageUrl               ;//跳转URL路径
	 private String backgroundColor=MobileHomeAuxiliary.NORMAL_BACKGROUD_CORLOR        ;//背景颜色
	 private String imgColor=MobileHomeAuxiliary.NORMAL_CORLOR              ;//图片颜色
//	 private String textColor             ;//文字颜色
	 private String totalValue=""            ;//值
	 private String totalUnits=""            ;//单位
	 private String totalDate             ;//统计日期
	 private Timestamp createTime         ;//创建日期
	 private int showOrgBar=1             ;//是否显示机构组件
     private int scroll=1				  ; // 控制页面是否可以滚动  默认可以
	 
     
     
	public int getScroll() {
		return scroll;
	}
	public void setScroll(int scroll) {
		this.scroll = scroll;
	}
	public String getFunctionConfigMobileId() {
		return functionConfigMobileId;
	}
	public void setFunctionConfigMobileId(String functionConfigMobileId) {
		this.functionConfigMobileId = functionConfigMobileId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public boolean isView() {
		return isView;
	}
	public void setView(boolean isView) {
		this.isView = isView;
	}
	public String getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(String showIndex) {
		this.showIndex = showIndex;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public int getShowOrganBar() {
		return showOrgBar;
	}
	public void setShowOrganBar(int showOrganBar) {
		this.showOrgBar = showOrganBar;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getImgColor() {
		return imgColor;
	}
	public void setImgColor(String imgColor) {
		this.imgColor = imgColor;
	}
//	public String getTextColor() {
//		return textColor;
//	}
//	public void setTextColor(String textColor) {
//		this.textColor = textColor;
//	}
	public String getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	public String getTotalUnits() {
		return totalUnits;
	}
	public void setTotalUnits(String totalUnits) {
		this.totalUnits = totalUnits;
	}
	public String getTotalDate() {
		return totalDate;
	}
	public void setTotalDate(String totalDate) {
		this.totalDate = totalDate;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	 
	 

}
