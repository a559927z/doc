package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_train_plan")
public class SourceTrainPlanEntity implements Entity{

	private String _train_plan_id;
	private String _customer_id;
	private String _organization_id;
	private String _train_name;
	private int _train_num;
	private String _date;
	private String _start_date;
	private String _end_date;
	private int _year;
	private int _status;

	public SourceTrainPlanEntity(){
		super();
	}

	public SourceTrainPlanEntity(String _train_plan_id,String _customer_id,String _organization_id,String _train_name,int _train_num,String _date,String _start_date,String _end_date,int _year,int _status){
		this._train_plan_id = _train_plan_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._train_name = _train_name;
		this._train_num = _train_num;
		this._date = _date;
		this._start_date = _start_date;
		this._end_date = _end_date;
		this._year = _year;
		this._status = _status;
	}

	@Override
	public String toString() {
		return "SourceTrainPlanEntity ["+
			"	train_plan_id="+_train_plan_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	train_name="+_train_name+
			"	train_num="+_train_num+
			"	date="+_date+
			"	start_date="+_start_date+
			"	end_date="+_end_date+
			"	year="+_year+
			"	status="+_status+
			"]";
	}

	@Column(name = "train_plan_id",type=ColumnType.VARCHAR)
	public String getTrainPlanId(){
		return this._train_plan_id; 
	}

	public void setTrainPlanId(String _train_plan_id){
		this._train_plan_id = _train_plan_id;
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

	@Column(name = "train_name",type=ColumnType.VARCHAR)
	public String getTrainName(){
		return this._train_name; 
	}

	public void setTrainName(String _train_name){
		this._train_name = _train_name;
	}

	@Column(name = "train_num",type=ColumnType.INT)
	public int getTrainNum(){
		return this._train_num; 
	}

	public void setTrainNum(int _train_num){
		this._train_num = _train_num;
	}

	@Column(name = "date",type=ColumnType.DATETIME)
	public String getDate(){
		return this._date; 
	}

	public void setDate(String _date){
		this._date = _date;
	}

	@Column(name = "start_date",type=ColumnType.DATETIME)
	public String getStartDate(){
		return this._start_date; 
	}

	public void setStartDate(String _start_date){
		this._start_date = _start_date;
	}

	@Column(name = "end_date",type=ColumnType.DATETIME)
	public String getEndDate(){
		return this._end_date; 
	}

	public void setEndDate(String _end_date){
		this._end_date = _end_date;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "status",type=ColumnType.TINYINT)
	public int getStatus(){
		return this._status; 
	}

	public void setStatus(int _status){
		this._status = _status;
	}

	// 实例化内部类
	public static SourceTrainPlanEntityAuxiliary  getEntityAuxiliary(){
		return new SourceTrainPlanEntityAuxiliary();
	}

	public static class SourceTrainPlanEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceTrainPlanEntityAuxiliary asTrainPlanId(String colName, CustomColumn<?, ?>... cols){
			setColName("train_plan_id","`train_plan_id`", colName, "setTrainPlanId", "getString", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asTrainName(String colName, CustomColumn<?, ?>... cols){
			setColName("train_name","`train_name`", colName, "setTrainName", "getString", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asTrainNum(String colName, CustomColumn<?, ?>... cols){
			setColName("train_num","`train_num`", colName, "setTrainNum", "getInt", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asEndDate(String colName, CustomColumn<?, ?>... cols){
			setColName("end_date","`end_date`", colName, "setEndDate", "getString", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceTrainPlanEntityAuxiliary asStatus(String colName, CustomColumn<?, ?>... cols){
			setColName("status","`status`", colName, "setStatus", "getInt", cols);
			return this;
		}
	}
}
