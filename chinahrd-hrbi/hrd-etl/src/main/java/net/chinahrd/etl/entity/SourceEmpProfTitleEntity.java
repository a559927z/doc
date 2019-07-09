package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_prof_title")
public class SourceEmpProfTitleEntity implements Entity{

	private String _emp_prof_title_id;
	private String _customer_id;
	private String _emp_id;
	private String _gain_date;
	private String _emp_prof_title_name;
	private String _prof_lv;
	private String _award_unit;
	private String _effect_date;

	public SourceEmpProfTitleEntity(){
		super();
	}

	public SourceEmpProfTitleEntity(String _emp_prof_title_id,String _customer_id,String _emp_id,String _gain_date,String _emp_prof_title_name,String _prof_lv,String _award_unit,String _effect_date){
		this._emp_prof_title_id = _emp_prof_title_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._gain_date = _gain_date;
		this._emp_prof_title_name = _emp_prof_title_name;
		this._prof_lv = _prof_lv;
		this._award_unit = _award_unit;
		this._effect_date = _effect_date;
	}

	@Override
	public String toString() {
		return "SourceEmpProfTitleEntity ["+
			"	emp_prof_title_id="+_emp_prof_title_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	gain_date="+_gain_date+
			"	emp_prof_title_name="+_emp_prof_title_name+
			"	prof_lv="+_prof_lv+
			"	award_unit="+_award_unit+
			"	effect_date="+_effect_date+
			"]";
	}

	@Column(name = "emp_prof_title_id",type=ColumnType.VARCHAR)
	public String getEmpProfTitleId(){
		return this._emp_prof_title_id; 
	}

	public void setEmpProfTitleId(String _emp_prof_title_id){
		this._emp_prof_title_id = _emp_prof_title_id;
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

	@Column(name = "gain_date",type=ColumnType.DATETIME)
	public String getGainDate(){
		return this._gain_date; 
	}

	public void setGainDate(String _gain_date){
		this._gain_date = _gain_date;
	}

	@Column(name = "emp_prof_title_name",type=ColumnType.VARCHAR)
	public String getEmpProfTitleName(){
		return this._emp_prof_title_name; 
	}

	public void setEmpProfTitleName(String _emp_prof_title_name){
		this._emp_prof_title_name = _emp_prof_title_name;
	}

	@Column(name = "prof_lv",type=ColumnType.VARCHAR)
	public String getProfLv(){
		return this._prof_lv; 
	}

	public void setProfLv(String _prof_lv){
		this._prof_lv = _prof_lv;
	}

	@Column(name = "award_unit",type=ColumnType.VARCHAR)
	public String getAwardUnit(){
		return this._award_unit; 
	}

	public void setAwardUnit(String _award_unit){
		this._award_unit = _award_unit;
	}

	@Column(name = "effect_date",type=ColumnType.DATETIME)
	public String getEffectDate(){
		return this._effect_date; 
	}

	public void setEffectDate(String _effect_date){
		this._effect_date = _effect_date;
	}

	// 实例化内部类
	public static SourceEmpProfTitleEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpProfTitleEntityAuxiliary();
	}

	public static class SourceEmpProfTitleEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpProfTitleEntityAuxiliary asEmpProfTitleId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_prof_title_id","`emp_prof_title_id`", colName, "setEmpProfTitleId", "getString", cols);
			return this;
		}
		public SourceEmpProfTitleEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpProfTitleEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpProfTitleEntityAuxiliary asGainDate(String colName, CustomColumn<?, ?>... cols){
			setColName("gain_date","`gain_date`", colName, "setGainDate", "getString", cols);
			return this;
		}
		public SourceEmpProfTitleEntityAuxiliary asEmpProfTitleName(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_prof_title_name","`emp_prof_title_name`", colName, "setEmpProfTitleName", "getString", cols);
			return this;
		}
		public SourceEmpProfTitleEntityAuxiliary asProfLv(String colName, CustomColumn<?, ?>... cols){
			setColName("prof_lv","`prof_lv`", colName, "setProfLv", "getString", cols);
			return this;
		}
		public SourceEmpProfTitleEntityAuxiliary asAwardUnit(String colName, CustomColumn<?, ?>... cols){
			setColName("award_unit","`award_unit`", colName, "setAwardUnit", "getString", cols);
			return this;
		}
		public SourceEmpProfTitleEntityAuxiliary asEffectDate(String colName, CustomColumn<?, ?>... cols){
			setColName("effect_date","`effect_date`", colName, "setEffectDate", "getString", cols);
			return this;
		}
	}
}
