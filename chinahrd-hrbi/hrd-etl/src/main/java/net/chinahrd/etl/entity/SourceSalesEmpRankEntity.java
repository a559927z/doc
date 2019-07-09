package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_emp_rank")
public class SourceSalesEmpRankEntity implements Entity{

	private String _emp_id;
	private String _customer_id;
	private int _emp_rank;
	private String _organization_id;
	private String _rank_date;
	private int _year_month;
	private String _proportion_id;

	public SourceSalesEmpRankEntity(){
		super();
	}

	public SourceSalesEmpRankEntity(String _emp_id,String _customer_id,int _emp_rank,String _organization_id,String _rank_date,int _year_month,String _proportion_id){
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._emp_rank = _emp_rank;
		this._organization_id = _organization_id;
		this._rank_date = _rank_date;
		this._year_month = _year_month;
		this._proportion_id = _proportion_id;
	}

	@Override
	public String toString() {
		return "SourceSalesEmpRankEntity ["+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	emp_rank="+_emp_rank+
			"	organization_id="+_organization_id+
			"	rank_date="+_rank_date+
			"	year_month="+_year_month+
			"	proportion_id="+_proportion_id+
			"]";
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "emp_rank",type=ColumnType.INT)
	public int getEmpRank(){
		return this._emp_rank; 
	}

	public void setEmpRank(int _emp_rank){
		this._emp_rank = _emp_rank;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "rank_date",type=ColumnType.DATE)
	public String getRankDate(){
		return this._rank_date; 
	}

	public void setRankDate(String _rank_date){
		this._rank_date = _rank_date;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	@Column(name = "proportion_id",type=ColumnType.VARCHAR)
	public String getProportionId(){
		return this._proportion_id; 
	}

	public void setProportionId(String _proportion_id){
		this._proportion_id = _proportion_id;
	}

	// 实例化内部类
	public static SourceSalesEmpRankEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesEmpRankEntityAuxiliary();
	}

	public static class SourceSalesEmpRankEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesEmpRankEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceSalesEmpRankEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalesEmpRankEntityAuxiliary asEmpRank(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_rank","`emp_rank`", colName, "setEmpRank", "getInt", cols);
			return this;
		}
		public SourceSalesEmpRankEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceSalesEmpRankEntityAuxiliary asRankDate(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_date","`rank_date`", colName, "setRankDate", "getString", cols);
			return this;
		}
		public SourceSalesEmpRankEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourceSalesEmpRankEntityAuxiliary asProportionId(String colName, CustomColumn<?, ?>... cols){
			setColName("proportion_id","`proportion_id`", colName, "setProportionId", "getString", cols);
			return this;
		}
	}
}
