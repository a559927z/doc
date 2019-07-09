package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_key_talent")
public class SourceKeyTalentEntity implements Entity{

	private String _emp_id;
	private String _key_talent_type_key;
	private String _customer_id;

	public SourceKeyTalentEntity(){
		super();
	}

	public SourceKeyTalentEntity(String _emp_id,String _key_talent_type_key,String _customer_id){
		this._emp_id = _emp_id;
		this._key_talent_type_key = _key_talent_type_key;
		this._customer_id = _customer_id;
	}

	@Override
	public String toString() {
		return "SourceKeyTalentEntity ["+
			"	emp_id="+_emp_id+
			"	key_talent_type_key="+_key_talent_type_key+
			"	customer_id="+_customer_id+
			"]";
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "key_talent_type_key",type=ColumnType.VARCHAR)
	public String getKeyTalentTypeKey(){
		return this._key_talent_type_key; 
	}

	public void setKeyTalentTypeKey(String _key_talent_type_key){
		this._key_talent_type_key = _key_talent_type_key;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	// 实例化内部类
	public static SourceKeyTalentEntityAuxiliary  getEntityAuxiliary(){
		return new SourceKeyTalentEntityAuxiliary();
	}

	public static class SourceKeyTalentEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceKeyTalentEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceKeyTalentEntityAuxiliary asKeyTalentTypeKey(String colName, CustomColumn<?, ?>... cols){
			setColName("key_talent_type_key","`key_talent_type_key`", colName, "setKeyTalentTypeKey", "getString", cols);
			return this;
		}
		public SourceKeyTalentEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
	}
}
