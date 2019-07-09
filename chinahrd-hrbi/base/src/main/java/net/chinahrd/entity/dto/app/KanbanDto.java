package net.chinahrd.entity.dto.app;

import net.chinahrd.entity.dto.app.IconItemDto;


/**
 * 管理看板DTO
 * 
 * @author guanjian
 * 
 */
public class KanbanDto extends IconItemDto {

	/**  */
	private static final long serialVersionUID = -8915683868313179312L;


	private String text1; //
	private String text2; //
	private String text3; //
	private String textColor; // 文字背景颜色
	public KanbanDto() {
		super();
	}
	
	public KanbanDto(String id, String name) {
		super(id, name);
	}


	public KanbanDto(String id, String name, String imageUrl) {
		super(id, name, imageUrl);
	}

	public KanbanDto(String id, String name, String imageUrl, String backgroundColor, String imgColor, String textColor) {
		super(id, name, imageUrl);
		this.backgroundColor = backgroundColor;
		this.imgColor=imgColor;
		this.textColor=textColor;
	}

	public KanbanDto(String id, String name, String imageUrl, String backgroundColor, String text1, String text2,
			String text3) {
		super(id, name, imageUrl);
		this.backgroundColor = backgroundColor;
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
	}

	

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

}
