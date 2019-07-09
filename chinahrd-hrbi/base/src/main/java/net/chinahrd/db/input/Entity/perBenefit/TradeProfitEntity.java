package net.chinahrd.db.input.Entity.perBenefit;

import java.io.Serializable;
import java.math.BigDecimal;

public class TradeProfitEntity implements Serializable {

	private static final long serialVersionUID = 4916675348335540191L;

	private String trade_profit_id;

	private String customer_id;

	private String business_unit_id;

	private String organization_id;

	private BigDecimal gain_amount;

	private BigDecimal sales_amount;

	private Double target_value;

	private int year_month;

	public String getTrade_profit_id() {
		return this.trade_profit_id;
	}

	public void setTrade_profit_id(String trade_profit_id) {
		this.trade_profit_id = trade_profit_id;
	}

	public String getCustomer_id() {
		return this.customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getBusiness_unit_id() {
		return this.business_unit_id;
	}

	public void setBusiness_unit_id(String business_unit_id) {
		this.business_unit_id = business_unit_id;
	}

	public String getOrganization_id() {
		return this.organization_id;
	}

	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}

	public BigDecimal getGain_amount() {
		return this.gain_amount;
	}

	public void setGain_amount(BigDecimal gain_amount) {
		this.gain_amount = gain_amount;
	}

	public BigDecimal getSales_amount() {
		return this.sales_amount;
	}

	public void setSales_amount(BigDecimal sales_amount) {
		this.sales_amount = sales_amount;
	}

	public Double getTarget_value() {
		return this.target_value;
	}

	public void setTarget_value(Double target_value) {
		this.target_value = target_value;
	}

	public int getYear_month() {
		return this.year_month;
	}

	public void setYear_month(int year_month) {
		this.year_month = year_month;
	}

}
