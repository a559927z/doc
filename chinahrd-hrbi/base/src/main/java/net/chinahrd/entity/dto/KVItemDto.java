package net.chinahrd.entity.dto;

import java.io.Serializable;





/**
 * 键值类
 * @author jxzhang
 *
 */
public class KVItemDto implements Serializable  ,Comparable<KVItemDto>{
	/** */
	private static final long serialVersionUID = 5823266214178936888L;
	/** 键 */
	private String k;
	/** 值 */
	private String v;
	
	public KVItemDto() {
		
	}
	
	public KVItemDto(String k, String v) {
		this.k = k;
		this.v = v;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	private int getCompareV() {
		try {
			return Integer.parseInt(this.v);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(KVItemDto o) {
		int thisV=this.getCompareV();
		int thatV=o.getCompareV();
		return thisV>thatV?1:thisV==thatV?0:-1;
	}
	
}
