package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_pq_relation")
public class SourceEmpPqRelationEntity implements Entity{

	private String _emp_pq_relation_id;
	private String _quality_id;
	private String _matching_score_id;
	private String _demands_matching_score_id;
	private String _customer_id;
	private String _emp_id;
	private String _date;
	private String _refresh;
	private int _year_month;

	public SourceEmpPqRelationEntity(){
		super();
	}

	public SourceEmpPqRelationEntity(String _emp_pq_relation_id,String _quality_id,String _matching_score_id,String _demands_matching_score_id,String _customer_id,String _emp_id,String _date,String _refresh,int _year_month){
		this._emp_pq_relation_id = _emp_pq_relation_id;
		this._quality_id = _quality_id;
		this._matching_score_id = _matching_score_id;
		this._demands_matching_score_id = _demands_matching_score_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._date = _date;
		this._refresh = _refresh;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceEmpPqRelationEntity ["+
			"	emp_pq_relation_id="+_emp_pq_relation_id+
			"	quality_id="+_quality_id+
			"	matching_score_id="+_matching_score_id+
			"	demands_matching_score_id="+_demands_matching_score_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	date="+_date+
			"	refresh="+_refresh+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "emp_pq_relation_id",type=ColumnType.VARCHAR)
	public String getEmpPqRelationId(){
		return this._emp_pq_relation_id; 
	}

	public void setEmpPqRelationId(String _emp_pq_relation_id){
		this._emp_pq_relation_id = _emp_pq_relation_id;
	}

	@Column(name = "quality_id",type=ColumnType.VARCHAR)
	public String getQualityId(){
		return this._quality_id; 
	}

	public void setQualityId(String _quality_id){
		this._quality_id = _quality_id;
	}

	@Column(name = "matching_score_id",type=ColumnType.VARCHAR)
	public String getMatchingScoreId(){
		return this._matching_score_id; 
	}

	public void setMatchingScoreId(String _matching_score_id){
		this._matching_score_id = _matching_score_id;
	}

	@Column(name = "demands_matching_score_id",type=ColumnType.VARCHAR)
	public String getDemandsMatchingScoreId(){
		return this._demands_matching_score_id; 
	}

	public void setDemandsMatchingScoreId(String _demands_matching_score_id){
		this._demands_matching_score_id = _demands_matching_score_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "date",type=ColumnType.DATE)
	public String getDate(){
		return this._date; 
	}

	public void setDate(String _date){
		this._date = _date;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceEmpPqRelationEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpPqRelationEntityAuxiliary();
	}

	public static class SourceEmpPqRelationEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpPqRelationEntityAuxiliary asEmpPqRelationId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_pq_relation_id","`emp_pq_relation_id`", colName, "setEmpPqRelationId", "getString", cols);
			return this;
		}
		public SourceEmpPqRelationEntityAuxiliary asQualityId(String colName, CustomColumn<?, ?>... cols){
			setColName("quality_id","`quality_id`", colName, "setQualityId", "getString", cols);
			return this;
		}
		public SourceEmpPqRelationEntityAuxiliary asMatchingScoreId(String colName, CustomColumn<?, ?>... cols){
			setColName("matching_score_id","`matching_score_id`", colName, "setMatchingScoreId", "getString", cols);
			return this;
		}
		public SourceEmpPqRelationEntityAuxiliary asDemandsMatchingScoreId(String colName, CustomColumn<?, ?>... cols){
			setColName("demands_matching_score_id","`demands_matching_score_id`", colName, "setDemandsMatchingScoreId", "getString", cols);
			return this;
		}
		public SourceEmpPqRelationEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpPqRelationEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpPqRelationEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceEmpPqRelationEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourceEmpPqRelationEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
