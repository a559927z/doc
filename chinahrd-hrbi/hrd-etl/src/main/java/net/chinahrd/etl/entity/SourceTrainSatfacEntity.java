package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_train_satfac")
public class SourceTrainSatfacEntity implements Entity{

	private String _train_satfac_id;
	private String _customer_id;
	private String _organization_id;
	private Double _soure;
	private int _year;

	public SourceTrainSatfacEntity(){
		super();
	}

	public SourceTrainSatfacEntity(String _train_satfac_id,String _customer_id,String _organization_id,Double _soure,int _year){
		this._train_satfac_id = _train_satfac_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._soure = _soure;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "SourceTrainSatfacEntity ["+
			"	train_satfac_id="+_train_satfac_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	soure="+_soure+
			"	year="+_year+
			"]";
	}

	@Column(name = "train_satfac_id",type=ColumnType.VARCHAR)
	public String getTrainSatfacId(){
		return this._train_satfac_id; 
	}

	public void setTrainSatfacId(String _train_satfac_id){
		this._train_satfac_id = _train_satfac_id;
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

	@Column(name = "soure",type=ColumnType.DOUBLE)
	public Double getSoure(){
		return this._soure; 
	}

	public void setSoure(Double _soure){
		this._soure = _soure;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static SourceTrainSatfacEntityAuxiliary  getEntityAuxiliary(){
		return new SourceTrainSatfacEntityAuxiliary();
	}

	public static class SourceTrainSatfacEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceTrainSatfacEntityAuxiliary asTrainSatfacId(String colName, CustomColumn<?, ?>... cols){
			setColName("train_satfac_id","`train_satfac_id`", colName, "setTrainSatfacId", "getString", cols);
			return this;
		}
		public SourceTrainSatfacEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceTrainSatfacEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceTrainSatfacEntityAuxiliary asSoure(String colName, CustomColumn<?, ?>... cols){
			setColName("soure","`soure`", colName, "setSoure", "getDouble", cols);
			return this;
		}
		public SourceTrainSatfacEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
