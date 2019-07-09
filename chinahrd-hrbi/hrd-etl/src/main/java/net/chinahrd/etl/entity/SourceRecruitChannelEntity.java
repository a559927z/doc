package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_recruit_channel")
public class SourceRecruitChannelEntity implements Entity{

	private String _recruit_channel_id;
	private String _customer_id;
	private String _channel_id;
	private String _position_id;
	private int _employ_num;
	private Double _outlay;
	private String _start_date;
	private String _end_date;
	private int _days;
	private String _recruit_public_id;
	private int _year;
	private String _refresh;
	private String _c_id;

	public SourceRecruitChannelEntity(){
		super();
	}

	public SourceRecruitChannelEntity(String _recruit_channel_id,String _customer_id,String _channel_id,String _position_id,int _employ_num,Double _outlay,String _start_date,String _end_date,int _days,String _recruit_public_id,int _year,String _refresh,String _c_id){
		this._recruit_channel_id = _recruit_channel_id;
		this._customer_id = _customer_id;
		this._channel_id = _channel_id;
		this._position_id = _position_id;
		this._employ_num = _employ_num;
		this._outlay = _outlay;
		this._start_date = _start_date;
		this._end_date = _end_date;
		this._days = _days;
		this._recruit_public_id = _recruit_public_id;
		this._year = _year;
		this._refresh = _refresh;
		this._c_id = _c_id;
	}

	@Override
	public String toString() {
		return "SourceRecruitChannelEntity ["+
			"	recruit_channel_id="+_recruit_channel_id+
			"	customer_id="+_customer_id+
			"	channel_id="+_channel_id+
			"	position_id="+_position_id+
			"	employ_num="+_employ_num+
			"	outlay="+_outlay+
			"	start_date="+_start_date+
			"	end_date="+_end_date+
			"	days="+_days+
			"	recruit_public_id="+_recruit_public_id+
			"	year="+_year+
			"	refresh="+_refresh+
			"	c_id="+_c_id+
			"]";
	}

	@Column(name = "recruit_channel_id",type=ColumnType.VARCHAR)
	public String getRecruitChannelId(){
		return this._recruit_channel_id; 
	}

	public void setRecruitChannelId(String _recruit_channel_id){
		this._recruit_channel_id = _recruit_channel_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "channel_id",type=ColumnType.VARCHAR)
	public String getChannelId(){
		return this._channel_id; 
	}

	public void setChannelId(String _channel_id){
		this._channel_id = _channel_id;
	}

	@Column(name = "position_id",type=ColumnType.VARCHAR)
	public String getPositionId(){
		return this._position_id; 
	}

	public void setPositionId(String _position_id){
		this._position_id = _position_id;
	}

	@Column(name = "employ_num",type=ColumnType.INT)
	public int getEmployNum(){
		return this._employ_num; 
	}

	public void setEmployNum(int _employ_num){
		this._employ_num = _employ_num;
	}

	@Column(name = "outlay",type=ColumnType.DOUBLE)
	public Double getOutlay(){
		return this._outlay; 
	}

	public void setOutlay(Double _outlay){
		this._outlay = _outlay;
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

	@Column(name = "days",type=ColumnType.INT)
	public int getDays(){
		return this._days; 
	}

	public void setDays(int _days){
		this._days = _days;
	}

	@Column(name = "recruit_public_id",type=ColumnType.VARCHAR)
	public String getRecruitPublicId(){
		return this._recruit_public_id; 
	}

	public void setRecruitPublicId(String _recruit_public_id){
		this._recruit_public_id = _recruit_public_id;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	@Column(name = "c_id",type=ColumnType.VARCHAR)
	public String getCId(){
		return this._c_id; 
	}

	public void setCId(String _c_id){
		this._c_id = _c_id;
	}

	// 实例化内部类
	public static SourceRecruitChannelEntityAuxiliary  getEntityAuxiliary(){
		return new SourceRecruitChannelEntityAuxiliary();
	}

	public static class SourceRecruitChannelEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceRecruitChannelEntityAuxiliary asRecruitChannelId(String colName, CustomColumn<?, ?>... cols){
			setColName("recruit_channel_id","`recruit_channel_id`", colName, "setRecruitChannelId", "getString", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asChannelId(String colName, CustomColumn<?, ?>... cols){
			setColName("channel_id","`channel_id`", colName, "setChannelId", "getString", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asEmployNum(String colName, CustomColumn<?, ?>... cols){
			setColName("employ_num","`employ_num`", colName, "setEmployNum", "getInt", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asOutlay(String colName, CustomColumn<?, ?>... cols){
			setColName("outlay","`outlay`", colName, "setOutlay", "getDouble", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asEndDate(String colName, CustomColumn<?, ?>... cols){
			setColName("end_date","`end_date`", colName, "setEndDate", "getString", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asDays(String colName, CustomColumn<?, ?>... cols){
			setColName("days","`days`", colName, "setDays", "getInt", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asRecruitPublicId(String colName, CustomColumn<?, ?>... cols){
			setColName("recruit_public_id","`recruit_public_id`", colName, "setRecruitPublicId", "getString", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourceRecruitChannelEntityAuxiliary asCId(String colName, CustomColumn<?, ?>... cols){
			setColName("c_id","`c_id`", colName, "setCId", "getString", cols);
			return this;
		}
	}
}
