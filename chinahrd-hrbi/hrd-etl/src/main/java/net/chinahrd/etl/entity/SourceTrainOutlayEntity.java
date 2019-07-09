package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_train_outlay")
public class SourceTrainOutlayEntity implements Entity{

	private String _train_outlay_id;
	private String _customer_id;
	private String _organization_id;
	private Double _outlay;
	private String _date;
	private String _note;
	private int _year;

	public SourceTrainOutlayEntity(){
		super();
	}

	public SourceTrainOutlayEntity(String _train_outlay_id,String _customer_id,String _organization_id,Double _outlay,String _date,String _note,int _year){
		this._train_outlay_id = _train_outlay_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._outlay = _outlay;
		this._date = _date;
		this._note = _note;
		this._year = _year;
	}

	@Override
	public String toString() {
		return "SourceTrainOutlayEntity ["+
			"	train_outlay_id="+_train_outlay_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	outlay="+_outlay+
			"	date="+_date+
			"	note="+_note+
			"	year="+_year+
			"]";
	}

	@Column(name = "train_outlay_id",type=ColumnType.VARCHAR)
	public String getTrainOutlayId(){
		return this._train_outlay_id; 
	}

	public void setTrainOutlayId(String _train_outlay_id){
		this._train_outlay_id = _train_outlay_id;
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

	@Column(name = "outlay",type=ColumnType.DOUBLE)
	public Double getOutlay(){
		return this._outlay; 
	}

	public void setOutlay(Double _outlay){
		this._outlay = _outlay;
	}

	@Column(name = "date",type=ColumnType.DATETIME)
	public String getDate(){
		return this._date; 
	}

	public void setDate(String _date){
		this._date = _date;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	// 实例化内部类
	public static SourceTrainOutlayEntityAuxiliary  getEntityAuxiliary(){
		return new SourceTrainOutlayEntityAuxiliary();
	}

	public static class SourceTrainOutlayEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceTrainOutlayEntityAuxiliary asTrainOutlayId(String colName, CustomColumn<?, ?>... cols){
			setColName("train_outlay_id","`train_outlay_id`", colName, "setTrainOutlayId", "getString", cols);
			return this;
		}
		public SourceTrainOutlayEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceTrainOutlayEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceTrainOutlayEntityAuxiliary asOutlay(String colName, CustomColumn<?, ?>... cols){
			setColName("outlay","`outlay`", colName, "setOutlay", "getDouble", cols);
			return this;
		}
		public SourceTrainOutlayEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceTrainOutlayEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceTrainOutlayEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
	}
}
