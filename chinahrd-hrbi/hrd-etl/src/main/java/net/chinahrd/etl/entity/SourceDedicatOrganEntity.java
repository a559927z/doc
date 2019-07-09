package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dedicat_organ")
public class SourceDedicatOrganEntity implements Entity{

	private String _dedicat_organ_id;
	private String _customer_id;
	private String _organization_id;
	private String _organization_name;
	private Double _score;
	private String _date;

	public SourceDedicatOrganEntity(){
		super();
	}

	public SourceDedicatOrganEntity(String _dedicat_organ_id,String _customer_id,String _organization_id,String _organization_name,Double _score,String _date){
		this._dedicat_organ_id = _dedicat_organ_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._organization_name = _organization_name;
		this._score = _score;
		this._date = _date;
	}

	@Override
	public String toString() {
		return "SourceDedicatOrganEntity ["+
			"	dedicat_organ_id="+_dedicat_organ_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	organization_name="+_organization_name+
			"	score="+_score+
			"	date="+_date+
			"]";
	}

	@Column(name = "dedicat_organ_id",type=ColumnType.VARCHAR)
	public String getDedicatOrganId(){
		return this._dedicat_organ_id; 
	}

	public void setDedicatOrganId(String _dedicat_organ_id){
		this._dedicat_organ_id = _dedicat_organ_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "organization_name",type=ColumnType.VARCHAR)
	public String getOrganizationName(){
		return this._organization_name; 
	}

	public void setOrganizationName(String _organization_name){
		this._organization_name = _organization_name;
	}

	@Column(name = "score",type=ColumnType.DOUBLE)
	public Double getScore(){
		return this._score; 
	}

	public void setScore(Double _score){
		this._score = _score;
	}

	@Column(name = "date",type=ColumnType.DATE)
	public String getDate(){
		return this._date; 
	}

	public void setDate(String _date){
		this._date = _date;
	}

	// 实例化内部类
	public static SourceDedicatOrganEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDedicatOrganEntityAuxiliary();
	}

	public static class SourceDedicatOrganEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDedicatOrganEntityAuxiliary asDedicatOrganId(String colName, CustomColumn<?, ?>... cols){
			setColName("dedicat_organ_id","`dedicat_organ_id`", colName, "setDedicatOrganId", "getString", cols);
			return this;
		}
		public SourceDedicatOrganEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDedicatOrganEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceDedicatOrganEntityAuxiliary asOrganizationName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name","`organization_name`", colName, "setOrganizationName", "getString", cols);
			return this;
		}
		public SourceDedicatOrganEntityAuxiliary asScore(String colName, CustomColumn<?, ?>... cols){
			setColName("score","`score`", colName, "setScore", "getDouble", cols);
			return this;
		}
		public SourceDedicatOrganEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
	}
}
