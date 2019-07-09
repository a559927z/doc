package net.chinahrd.entity.dto.app;

import java.io.Serializable;

/**
 * 界面图标DTO
 * @author guanjian
 *
 */
public class IconItemDto implements Serializable{
	/**  */
	private static final long serialVersionUID = 5235760030645872816L;
	
	private String id; //
	private String name; //
	private String imageUrl; // 图标路径
	private String pageUrl; //
	private boolean scroll=true; // 控制页面是否可以滚动  默认可以
	private boolean showOrgBar=true; // 控制页面是否显示组织机构组件
	protected String backgroundColor; // 背景颜色
	protected String imgColor; // 图片背景颜色

	public IconItemDto() {
		super();
	}

	public IconItemDto(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public IconItemDto(String id, String name, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}
	
	
	
	public IconItemDto(String id, String name, String imageUrl, String pageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.pageUrl = pageUrl;
	}

	public IconItemDto(String id, String name, String imageUrl, String pageUrl, boolean scroll, boolean showOrgBar) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.pageUrl = pageUrl;
		this.scroll = scroll;
		this.showOrgBar = showOrgBar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


	public boolean getScroll() {
		return scroll;
	}

	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}


	public boolean isShowOrgBar() {
		return showOrgBar;
	}

	public void setShowOrgbar(boolean showOrgBar) {
		this.showOrgBar = showOrgBar;
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

}
