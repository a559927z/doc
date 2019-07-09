package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;

/**
 * 
 */
public class CompareDto implements Serializable {
	private static final long serialVersionUID = -4189727947944226945L;
	
	private Integer cur;
    private Integer prev;
    private Integer date;

    public CompareDto() {
    }

	public CompareDto(Integer cur, Integer prev) {
		this.cur = cur;
		this.prev = prev;
	}


	public Integer getCur() {
		return cur;
	}

	public void setCur(Integer cur) {
		this.cur = cur;
	}

	public Integer getPrev() {
		return prev;
	}

	public void setPrev(Integer prev) {
		this.prev = prev;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}
	

}
