package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_satfac_organ")
public class SourceSatfacOrganEntity implements Entity{

	private String _satfac_organ_id;
	private String _customer_id;
	private String _organization_id;
	private String _organization_name;
	private Double _score;
	private String _date;

	public SourceSatfacOrganEntity(){
		super();
	}

	public SourceSatfacOrganEntity(String _satfac_organ_id,String _customer_id,String _organization_id,String _organization_name,Double _score,String _date){
		this._satfac_organ_id = _satfac_organ_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._organization_name = _organization_name;
		this._score = _score;
		this._date = _date;
	}

	@Override
	public String toString() {
		return "SourceSatfacOrganEntity ["+
			"	satfac_organ_id="+_satfac_organ_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	organization_name="+_organization_name+
			"	score="+_score+
			"	date="+_date+
			"]";
	}

	@Column(name = "satfac_organ_id",type=ColumnType.VARCHAR)
	public String getSatfacOrganId(){
		return this._satfac_organ_id; 
	}

	public void setSatfacOrganId(String _satfac_organ_id){
		this._satfac_organ_id = _satfac_organ_id;
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
	public static SourceSatfacOrganEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSatfacOrganEntityAuxiliary();
	}

	public static class SourceSatfacOrganEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSatfacOrganEntityAuxiliary asSatfacOrganId(String colName, CustomColumn<?, ?>... cols){
			setColName("satfac_organ_id","`satfac_organ_id`", colName, "setSatfacOrganId", "getString", cols);
			return this;
		}
		public SourceSatfacOrganEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSatfacOrganEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceSatfacOrganEntityAuxiliary asOrganizationName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name","`organization_name`", colName, "setOrganizationName", "getString", cols);
			return this;
		}
		public SourceSatfacOrganEntityAuxiliary asScore(String colName, CustomColumn<?, ?>... cols){
			setColName("score","`score`", colName, "setScore", "getDouble", cols);
			return this;
		}
		public SourceSatfacOrganEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
	}
}
