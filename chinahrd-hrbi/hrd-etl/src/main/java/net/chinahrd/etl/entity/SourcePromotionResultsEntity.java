package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_promotion_results")
public class SourcePromotionResultsEntity implements Entity{

	private String _promotion_results_id;
	private String _emp_id;
	private int _company_age;
	private String _oranization_parent_id;
	private String _organization_id;
	private String _full_path;
	private String _performance_id;
	private String _sequence_id;
	private int _is_key_talent;
	private String _customer_id;
	private String _entry_date;
	private int _show_index;
	private String _rank_name;
	private int _show_index_af;
	private String _rank_name_af;
	private int _status;
	private String _status_date;
	private String _promotion_date;

	public SourcePromotionResultsEntity(){
		super();
	}

	public SourcePromotionResultsEntity(String _promotion_results_id,String _emp_id,int _company_age,String _oranization_parent_id,String _organization_id,String _full_path,String _performance_id,String _sequence_id,int _is_key_talent,String _customer_id,String _entry_date,int _show_index,String _rank_name,int _show_index_af,String _rank_name_af,int _status,String _status_date,String _promotion_date){
		this._promotion_results_id = _promotion_results_id;
		this._emp_id = _emp_id;
		this._company_age = _company_age;
		this._oranization_parent_id = _oranization_parent_id;
		this._organization_id = _organization_id;
		this._full_path = _full_path;
		this._performance_id = _performance_id;
		this._sequence_id = _sequence_id;
		this._is_key_talent = _is_key_talent;
		this._customer_id = _customer_id;
		this._entry_date = _entry_date;
		this._show_index = _show_index;
		this._rank_name = _rank_name;
		this._show_index_af = _show_index_af;
		this._rank_name_af = _rank_name_af;
		this._status = _status;
		this._status_date = _status_date;
		this._promotion_date = _promotion_date;
	}

	@Override
	public String toString() {
		return "SourcePromotionResultsEntity ["+
			"	promotion_results_id="+_promotion_results_id+
			"	emp_id="+_emp_id+
			"	company_age="+_company_age+
			"	oranization_parent_id="+_oranization_parent_id+
			"	organization_id="+_organization_id+
			"	full_path="+_full_path+
			"	performance_id="+_performance_id+
			"	sequence_id="+_sequence_id+
			"	is_key_talent="+_is_key_talent+
			"	customer_id="+_customer_id+
			"	entry_date="+_entry_date+
			"	show_index="+_show_index+
			"	rank_name="+_rank_name+
			"	show_index_af="+_show_index_af+
			"	rank_name_af="+_rank_name_af+
			"	status="+_status+
			"	status_date="+_status_date+
			"	promotion_date="+_promotion_date+
			"]";
	}

	@Column(name = "promotion_results_id",type=ColumnType.VARCHAR)
	public String getPromotionResultsId(){
		return this._promotion_results_id; 
	}

	public void setPromotionResultsId(String _promotion_results_id){
		this._promotion_results_id = _promotion_results_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "company_age",type=ColumnType.INT)
	public int getCompanyAge(){
		return this._company_age; 
	}

	public void setCompanyAge(int _company_age){
		this._company_age = _company_age;
	}

	@Column(name = "oranization_parent_id",type=ColumnType.VARCHAR)
	public String getOranizationParentId(){
		return this._oranization_parent_id; 
	}

	public void setOranizationParentId(String _oranization_parent_id){
		this._oranization_parent_id = _oranization_parent_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "full_path",type=ColumnType.VARCHAR)
	public String getFullPath(){
		return this._full_path; 
	}

	public void setFullPath(String _full_path){
		this._full_path = _full_path;
	}

	@Column(name = "performance_id",type=ColumnType.VARCHAR)
	public String getPerformanceId(){
		return this._performance_id; 
	}

	public void setPerformanceId(String _performance_id){
		this._performance_id = _performance_id;
	}

	@Column(name = "sequence_id",type=ColumnType.VARCHAR)
	public String getSequenceId(){
		return this._sequence_id; 
	}

	public void setSequenceId(String _sequence_id){
		this._sequence_id = _sequence_id;
	}

	@Column(name = "is_key_talent",type=ColumnType.INT)
	public int getIsKeyTalent(){
		return this._is_key_talent; 
	}

	public void setIsKeyTalent(int _is_key_talent){
		this._is_key_talent = _is_key_talent;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "entry_date",type=ColumnType.DATE)
	public String getEntryDate(){
		return this._entry_date; 
	}

	public void setEntryDate(String _entry_date){
		this._entry_date = _entry_date;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	@Column(name = "rank_name",type=ColumnType.VARCHAR)
	public String getRankName(){
		return this._rank_name; 
	}

	public void setRankName(String _rank_name){
		this._rank_name = _rank_name;
	}

	@Column(name = "show_index_af",type=ColumnType.INT)
	public int getShowIndexAf(){
		return this._show_index_af; 
	}

	public void setShowIndexAf(int _show_index_af){
		this._show_index_af = _show_index_af;
	}

	@Column(name = "rank_name_af",type=ColumnType.VARCHAR)
	public String getRankNameAf(){
		return this._rank_name_af; 
	}

	public void setRankNameAf(String _rank_name_af){
		this._rank_name_af = _rank_name_af;
	}

	@Column(name = "status",type=ColumnType.INT)
	public int getStatus(){
		return this._status; 
	}

	public void setStatus(int _status){
		this._status = _status;
	}

	@Column(name = "status_date",type=ColumnType.DATE)
	public String getStatusDate(){
		return this._status_date; 
	}

	public void setStatusDate(String _status_date){
		this._status_date = _status_date;
	}

	@Column(name = "promotion_date",type=ColumnType.DATE)
	public String getPromotionDate(){
		return this._promotion_date; 
	}

	public void setPromotionDate(String _promotion_date){
		this._promotion_date = _promotion_date;
	}

	// 实例化内部类
	public static SourcePromotionResultsEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePromotionResultsEntityAuxiliary();
	}

	public static class SourcePromotionResultsEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePromotionResultsEntityAuxiliary asPromotionResultsId(String colName, CustomColumn<?, ?>... cols){
			setColName("promotion_results_id","`promotion_results_id`", colName, "setPromotionResultsId", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asCompanyAge(String colName, CustomColumn<?, ?>... cols){
			setColName("company_age","`company_age`", colName, "setCompanyAge", "getInt", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asOranizationParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("oranization_parent_id","`oranization_parent_id`", colName, "setOranizationParentId", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asFullPath(String colName, CustomColumn<?, ?>... cols){
			setColName("full_path","`full_path`", colName, "setFullPath", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asPerformanceId(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_id","`performance_id`", colName, "setPerformanceId", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asIsKeyTalent(String colName, CustomColumn<?, ?>... cols){
			setColName("is_key_talent","`is_key_talent`", colName, "setIsKeyTalent", "getInt", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asEntryDate(String colName, CustomColumn<?, ?>... cols){
			setColName("entry_date","`entry_date`", colName, "setEntryDate", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asRankName(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name","`rank_name`", colName, "setRankName", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asShowIndexAf(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index_af","`show_index_af`", colName, "setShowIndexAf", "getInt", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asRankNameAf(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name_af","`rank_name_af`", colName, "setRankNameAf", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asStatus(String colName, CustomColumn<?, ?>... cols){
			setColName("status","`status`", colName, "setStatus", "getInt", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asStatusDate(String colName, CustomColumn<?, ?>... cols){
			setColName("status_date","`status_date`", colName, "setStatusDate", "getString", cols);
			return this;
		}
		public SourcePromotionResultsEntityAuxiliary asPromotionDate(String colName, CustomColumn<?, ?>... cols){
			setColName("promotion_date","`promotion_date`", colName, "setPromotionDate", "getString", cols);
			return this;
		}
	}
}
