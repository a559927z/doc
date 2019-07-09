package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_bonus_penalty")
public class SourceEmpBonusPenaltyEntity implements Entity{

	private String _emp_bonus_penalty_id;
	private String _customer_id;
	private String _emp_id;
	private String _emp_bonus_penalty_name;
	private int _type;
	private String _grant_unit;
	private String _witness_name;
	private String _bonus_penalty_date;
	private String _c_id;

	public SourceEmpBonusPenaltyEntity(){
		super();
	}

	public SourceEmpBonusPenaltyEntity(String _emp_bonus_penalty_id,String _customer_id,String _emp_id,String _emp_bonus_penalty_name,int _type,String _grant_unit,String _witness_name,String _bonus_penalty_date,String _c_id){
		this._emp_bonus_penalty_id = _emp_bonus_penalty_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._emp_bonus_penalty_name = _emp_bonus_penalty_name;
		this._type = _type;
		this._grant_unit = _grant_unit;
		this._witness_name = _witness_name;
		this._bonus_penalty_date = _bonus_penalty_date;
		this._c_id = _c_id;
	}

	@Override
	public String toString() {
		return "SourceEmpBonusPenaltyEntity ["+
			"	emp_bonus_penalty_id="+_emp_bonus_penalty_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	emp_bonus_penalty_name="+_emp_bonus_penalty_name+
			"	type="+_type+
			"	grant_unit="+_grant_unit+
			"	witness_name="+_witness_name+
			"	bonus_penalty_date="+_bonus_penalty_date+
			"	c_id="+_c_id+
			"]";
	}

	@Column(name = "emp_bonus_penalty_id",type=ColumnType.VARCHAR)
	public String getEmpBonusPenaltyId(){
		return this._emp_bonus_penalty_id; 
	}

	public void setEmpBonusPenaltyId(String _emp_bonus_penalty_id){
		this._emp_bonus_penalty_id = _emp_bonus_penalty_id;
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

	@Column(name = "emp_bonus_penalty_name",type=ColumnType.VARCHAR)
	public String getEmpBonusPenaltyName(){
		return this._emp_bonus_penalty_name; 
	}

	public void setEmpBonusPenaltyName(String _emp_bonus_penalty_name){
		this._emp_bonus_penalty_name = _emp_bonus_penalty_name;
	}

	@Column(name = "type",type=ColumnType.INT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	@Column(name = "grant_unit",type=ColumnType.VARCHAR)
	public String getGrantUnit(){
		return this._grant_unit; 
	}

	public void setGrantUnit(String _grant_unit){
		this._grant_unit = _grant_unit;
	}

	@Column(name = "witness_name",type=ColumnType.VARCHAR)
	public String getWitnessName(){
		return this._witness_name; 
	}

	public void setWitnessName(String _witness_name){
		this._witness_name = _witness_name;
	}

	@Column(name = "bonus_penalty_date",type=ColumnType.DATETIME)
	public String getBonusPenaltyDate(){
		return this._bonus_penalty_date; 
	}

	public void setBonusPenaltyDate(String _bonus_penalty_date){
		this._bonus_penalty_date = _bonus_penalty_date;
	}

	@Column(name = "c_id",type=ColumnType.VARCHAR)
	public String getCId(){
		return this._c_id; 
	}

	public void setCId(String _c_id){
		this._c_id = _c_id;
	}

	// 实例化内部类
	public static SourceEmpBonusPenaltyEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpBonusPenaltyEntityAuxiliary();
	}

	public static class SourceEmpBonusPenaltyEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpBonusPenaltyEntityAuxiliary asEmpBonusPenaltyId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_bonus_penalty_id","`emp_bonus_penalty_id`", colName, "setEmpBonusPenaltyId", "getString", cols);
			return this;
		}
		public SourceEmpBonusPenaltyEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpBonusPenaltyEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpBonusPenaltyEntityAuxiliary asEmpBonusPenaltyName(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_bonus_penalty_name","`emp_bonus_penalty_name`", colName, "setEmpBonusPenaltyName", "getString", cols);
			return this;
		}
		public SourceEmpBonusPenaltyEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
		public SourceEmpBonusPenaltyEntityAuxiliary asGrantUnit(String colName, CustomColumn<?, ?>... cols){
			setColName("grant_unit","`grant_unit`", colName, "setGrantUnit", "getString", cols);
			return this;
		}
		public SourceEmpBonusPenaltyEntityAuxiliary asWitnessName(String colName, CustomColumn<?, ?>... cols){
			setColName("witness_name","`witness_name`", colName, "setWitnessName", "getString", cols);
			return this;
		}
		public SourceEmpBonusPenaltyEntityAuxiliary asBonusPenaltyDate(String colName, CustomColumn<?, ?>... cols){
			setColName("bonus_penalty_date","`bonus_penalty_date`", colName, "setBonusPenaltyDate", "getString", cols);
			return this;
		}
		public SourceEmpBonusPenaltyEntityAuxiliary asCId(String colName, CustomColumn<?, ?>... cols){
			setColName("c_id","`c_id`", colName, "setCId", "getString", cols);
			return this;
		}
	}
}
