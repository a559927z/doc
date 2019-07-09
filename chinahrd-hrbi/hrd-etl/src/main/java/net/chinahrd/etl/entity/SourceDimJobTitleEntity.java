package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_job_title")
public class SourceDimJobTitleEntity implements Entity{

	private String _job_title_id;
	private String _customer_id;
	private String _job_title_key;
	private String _job_title_name;
	private String _curt_name;
	private int _show_index;

	public SourceDimJobTitleEntity(){
		super();
	}

	public SourceDimJobTitleEntity(String _job_title_id,String _customer_id,String _job_title_key,String _job_title_name,String _curt_name,int _show_index){
		this._job_title_id = _job_title_id;
		this._customer_id = _customer_id;
		this._job_title_key = _job_title_key;
		this._job_title_name = _job_title_name;
		this._curt_name = _curt_name;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimJobTitleEntity ["+
			"	job_title_id="+_job_title_id+
			"	customer_id="+_customer_id+
			"	job_title_key="+_job_title_key+
			"	job_title_name="+_job_title_name+
			"	curt_name="+_curt_name+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "job_title_id",type=ColumnType.VARCHAR)
	public String getJobTitleId(){
		return this._job_title_id; 
	}

	public void setJobTitleId(String _job_title_id){
		this._job_title_id = _job_title_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "job_title_key",type=ColumnType.VARCHAR)
	public String getJobTitleKey(){
		return this._job_title_key; 
	}

	public void setJobTitleKey(String _job_title_key){
		this._job_title_key = _job_title_key;
	}

	@Column(name = "job_title_name",type=ColumnType.VARCHAR)
	public String getJobTitleName(){
		return this._job_title_name; 
	}

	public void setJobTitleName(String _job_title_name){
		this._job_title_name = _job_title_name;
	}

	@Column(name = "curt_name",type=ColumnType.VARCHAR)
	public String getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(String _curt_name){
		this._curt_name = _curt_name;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimJobTitleEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimJobTitleEntityAuxiliary();
	}

	public static class SourceDimJobTitleEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimJobTitleEntityAuxiliary asJobTitleId(String colName, CustomColumn<?, ?>... cols){
			setColName("job_title_id","`job_title_id`", colName, "setJobTitleId", "getString", cols);
			return this;
		}
		public SourceDimJobTitleEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimJobTitleEntityAuxiliary asJobTitleKey(String colName, CustomColumn<?, ?>... cols){
			setColName("job_title_key","`job_title_key`", colName, "setJobTitleKey", "getString", cols);
			return this;
		}
		public SourceDimJobTitleEntityAuxiliary asJobTitleName(String colName, CustomColumn<?, ?>... cols){
			setColName("job_title_name","`job_title_name`", colName, "setJobTitleName", "getString", cols);
			return this;
		}
		public SourceDimJobTitleEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getString", cols);
			return this;
		}
		public SourceDimJobTitleEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
