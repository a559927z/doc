package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class PromotionForewarningDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    private int number;
    private String year;
    private List<String> list;
}
