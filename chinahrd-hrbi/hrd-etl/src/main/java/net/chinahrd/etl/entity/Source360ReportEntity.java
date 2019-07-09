package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_360_report")
public class Source360ReportEntity implements Entity{

	private String _360_report_id;
	private String _emp_id;
	private String _customer_id;
	private String _360_ability_id;
	private String _360_ability_name;
	private String _360_ability_lv_id;
	private String _360_ability_lv_name;
	private Double _standard_score;
	private Double _gain_score;
	private Double _score;
	private int _module_type;
	private String _360_time_id;

	public Source360ReportEntity(){
		super();
	}

	public Source360ReportEntity(String _360_report_id,String _emp_id,String _customer_id,String _360_ability_id,String _360_ability_name,String _360_ability_lv_id,String _360_ability_lv_name,Double _standard_score,Double _gain_score,Double _score,int _module_type,String _360_time_id){
		this._360_report_id = _360_report_id;
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._360_ability_id = _360_ability_id;
		this._360_ability_name = _360_ability_name;
		this._360_ability_lv_id = _360_ability_lv_id;
		this._360_ability_lv_name = _360_ability_lv_name;
		this._standard_score = _standard_score;
		this._gain_score = _gain_score;
		this._score = _score;
		this._module_type = _module_type;
		this._360_time_id = _360_time_id;
	}

	@Override
	public String toString() {
		return "Source360ReportEntity ["+
			"	360_report_id="+_360_report_id+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	360_ability_id="+_360_ability_id+
			"	360_ability_name="+_360_ability_name+
			"	360_ability_lv_id="+_360_ability_lv_id+
			"	360_ability_lv_name="+_360_ability_lv_name+
			"	standard_score="+_standard_score+
			"	gain_score="+_gain_score+
			"	score="+_score+
			"	module_type="+_module_type+
			"	360_time_id="+_360_time_id+
			"]";
	}

	@Column(name = "360_report_id",type=ColumnType.VARCHAR)
	public String get360ReportId(){
		return this._360_report_id; 
	}

	public void set360ReportId(String _360_report_id){
		this._360_report_id = _360_report_id;
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

	@Column(name = "360_ability_id",type=ColumnType.VARCHAR)
	public String get360AbilityId(){
		return this._360_ability_id; 
	}

	public void set360AbilityId(String _360_ability_id){
		this._360_ability_id = _360_ability_id;
	}

	@Column(name = "360_ability_name",type=ColumnType.VARCHAR)
	public String get360AbilityName(){
		return this._360_ability_name; 
	}

	public void set360AbilityName(String _360_ability_name){
		this._360_ability_name = _360_ability_name;
	}

	@Column(name = "360_ability_lv_id",type=ColumnType.VARCHAR)
	public String get360AbilityLvId(){
		return this._360_ability_lv_id; 
	}

	public void set360AbilityLvId(String _360_ability_lv_id){
		this._360_ability_lv_id = _360_ability_lv_id;
	}

	@Column(name = "360_ability_lv_name",type=ColumnType.VARCHAR)
	public String get360AbilityLvName(){
		return this._360_ability_lv_name; 
	}

	public void set360AbilityLvName(String _360_ability_lv_name){
		this._360_ability_lv_name = _360_ability_lv_name;
	}

	@Column(name = "standard_score",type=ColumnType.DOUBLE)
	public Double getStandardScore(){
		return this._standard_score; 
	}

	public void setStandardScore(Double _standard_score){
		this._standard_score = _standard_score;
	}

	@Column(name = "gain_score",type=ColumnType.DOUBLE)
	public Double getGainScore(){
		return this._gain_score; 
	}

	public void setGainScore(Double _gain_score){
		this._gain_score = _gain_score;
	}

	@Column(name = "score",type=ColumnType.DOUBLE)
	public Double getScore(){
		return this._score; 
	}

	public void setScore(Double _score){
		this._score = _score;
	}

	@Column(name = "module_type",type=ColumnType.INT)
	public int getModuleType(){
		return this._module_type; 
	}

	public void setModuleType(int _module_type){
		this._module_type = _module_type;
	}

	@Column(name = "360_time_id",type=ColumnType.VARCHAR)
	public String get360TimeId(){
		return this._360_time_id; 
	}

	public void set360TimeId(String _360_time_id){
		this._360_time_id = _360_time_id;
	}

	// 实例化内部类
	public static Source360ReportEntityAuxiliary  getEntityAuxiliary(){
		return new Source360ReportEntityAuxiliary();
	}

	public static class Source360ReportEntityAuxiliary extends AbstractSqlAuxiliary{
		public Source360ReportEntityAuxiliary as360ReportId(String colName, CustomColumn<?, ?>... cols){
			setColName("360_report_id","`360_report_id`", colName, "set360ReportId", "getString", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary as360AbilityId(String colName, CustomColumn<?, ?>... cols){
			setColName("360_ability_id","`360_ability_id`", colName, "set360AbilityId", "getString", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary as360AbilityName(String colName, CustomColumn<?, ?>... cols){
			setColName("360_ability_name","`360_ability_name`", colName, "set360AbilityName", "getString", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary as360AbilityLvId(String colName, CustomColumn<?, ?>... cols){
			setColName("360_ability_lv_id","`360_ability_lv_id`", colName, "set360AbilityLvId", "getString", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary as360AbilityLvName(String colName, CustomColumn<?, ?>... cols){
			setColName("360_ability_lv_name","`360_ability_lv_name`", colName, "set360AbilityLvName", "getString", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary asStandardScore(String colName, CustomColumn<?, ?>... cols){
			setColName("standard_score","`standard_score`", colName, "setStandardScore", "getDouble", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary asGainScore(String colName, CustomColumn<?, ?>... cols){
			setColName("gain_score","`gain_score`", colName, "setGainScore", "getDouble", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary asScore(String colName, CustomColumn<?, ?>... cols){
			setColName("score","`score`", colName, "setScore", "getDouble", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary asModuleType(String colName, CustomColumn<?, ?>... cols){
			setColName("module_type","`module_type`", colName, "setModuleType", "getInt", cols);
			return this;
		}
		public Source360ReportEntityAuxiliary as360TimeId(String colName, CustomColumn<?, ?>... cols){
			setColName("360_time_id","`360_time_id`", colName, "set360TimeId", "getString", cols);
			return this;
		}
	}
}
