package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_promotion_total")
public class SourcePromotionTotalEntity implements Entity{

	private String _promotion_total_id;
	private String _scheme_id;
	private String _emp_id;
	private String _customer_id;
	private String _rank_name;
	private String _rank_name_af;
	private String _organization_id;
	private int _status;
	private Double _condition_prop;
	private String _total_date;

	public SourcePromotionTotalEntity(){
		super();
	}

	public SourcePromotionTotalEntity(String _promotion_total_id,String _scheme_id,String _emp_id,String _customer_id,String _rank_name,String _rank_name_af,String _organization_id,int _status,Double _condition_prop,String _total_date){
		this._promotion_total_id = _promotion_total_id;
		this._scheme_id = _scheme_id;
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._rank_name = _rank_name;
		this._rank_name_af = _rank_name_af;
		this._organization_id = _organization_id;
		this._status = _status;
		this._condition_prop = _condition_prop;
		this._total_date = _total_date;
	}

	@Override
	public String toString() {
		return "SourcePromotionTotalEntity ["+
			"	promotion_total_id="+_promotion_total_id+
			"	scheme_id="+_scheme_id+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	rank_name="+_rank_name+
			"	rank_name_af="+_rank_name_af+
			"	organization_id="+_organization_id+
			"	status="+_status+
			"	condition_prop="+_condition_prop+
			"	total_date="+_total_date+
			"]";
	}

	@Column(name = "promotion_total_id",type=ColumnType.VARCHAR)
	public String getPromotionTotalId(){
		return this._promotion_total_id; 
	}

	public void setPromotionTotalId(String _promotion_total_id){
		this._promotion_total_id = _promotion_total_id;
	}

	@Column(name = "scheme_id",type=ColumnType.VARBINARY)
	public String getSchemeId(){
		return this._scheme_id; 
	}

	public void setSchemeId(String _scheme_id){
		this._scheme_id = _scheme_id;
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

	@Column(name = "rank_name",type=ColumnType.VARCHAR)
	public String getRankName(){
		return this._rank_name; 
	}

	public void setRankName(String _rank_name){
		this._rank_name = _rank_name;
	}

	@Column(name = "rank_name_af",type=ColumnType.VARCHAR)
	public String getRankNameAf(){
		return this._rank_name_af; 
	}

	public void setRankNameAf(String _rank_name_af){
		this._rank_name_af = _rank_name_af;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "status",type=ColumnType.INT)
	public int getStatus(){
		return this._status; 
	}

	public void setStatus(int _status){
		this._status = _status;
	}

	@Column(name = "condition_prop",type=ColumnType.DOUBLE)
	public Double getConditionProp(){
		return this._condition_prop; 
	}

	public void setConditionProp(Double _condition_prop){
		this._condition_prop = _condition_prop;
	}

	@Column(name = "total_date",type=ColumnType.DATE)
	public String getTotalDate(){
		return this._total_date; 
	}

	public void setTotalDate(String _total_date){
		this._total_date = _total_date;
	}

	// 实例化内部类
	public static SourcePromotionTotalEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePromotionTotalEntityAuxiliary();
	}

	public static class SourcePromotionTotalEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePromotionTotalEntityAuxiliary asPromotionTotalId(String colName, CustomColumn<?, ?>... cols){
			setColName("promotion_total_id","`promotion_total_id`", colName, "setPromotionTotalId", "getString", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asSchemeId(String colName, CustomColumn<?, ?>... cols){
			setColName("scheme_id","`scheme_id`", colName, "setSchemeId", "getString", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asRankName(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name","`rank_name`", colName, "setRankName", "getString", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asRankNameAf(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name_af","`rank_name_af`", colName, "setRankNameAf", "getString", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asStatus(String colName, CustomColumn<?, ?>... cols){
			setColName("status","`status`", colName, "setStatus", "getInt", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asConditionProp(String colName, CustomColumn<?, ?>... cols){
			setColName("condition_prop","`condition_prop`", colName, "setConditionProp", "getDouble", cols);
			return this;
		}
		public SourcePromotionTotalEntityAuxiliary asTotalDate(String colName, CustomColumn<?, ?>... cols){
			setColName("total_date","`total_date`", colName, "setTotalDate", "getString", cols);
			return this;
		}
	}
}
