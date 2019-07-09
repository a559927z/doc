package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_recruit_public")
public class SourceRecruitPublicEntity implements Entity{

	private String _recruit_public_id;
	private String _customer_id;
	private String _organization_id;
	private String _position_id;
	private int _employ_num;
	private int _plan_num;
	private String _start_date;
	private String _end_date;
	private int _days;
	private int _resume_num;
	private int _interview_num;
	private int _offer_num;
	private int _entry_num;
	private int _is_public;
	private int _year;
	private String _refresh;

	public SourceRecruitPublicEntity(){
		super();
	}

	public SourceRecruitPublicEntity(String _recruit_public_id,String _customer_id,String _organization_id,String _position_id,int _employ_num,int _plan_num,String _start_date,String _end_date,int _days,int _resume_num,int _interview_num,int _offer_num,int _entry_num,int _is_public,int _year,String _refresh){
		this._recruit_public_id = _recruit_public_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._position_id = _position_id;
		this._employ_num = _employ_num;
		this._plan_num = _plan_num;
		this._start_date = _start_date;
		this._end_date = _end_date;
		this._days = _days;
		this._resume_num = _resume_num;
		this._interview_num = _interview_num;
		this._offer_num = _offer_num;
		this._entry_num = _entry_num;
		this._is_public = _is_public;
		this._year = _year;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceRecruitPublicEntity ["+
			"	recruit_public_id="+_recruit_public_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	position_id="+_position_id+
			"	employ_num="+_employ_num+
			"	plan_num="+_plan_num+
			"	start_date="+_start_date+
			"	end_date="+_end_date+
			"	days="+_days+
			"	resume_num="+_resume_num+
			"	interview_num="+_interview_num+
			"	offer_num="+_offer_num+
			"	entry_num="+_entry_num+
			"	is_public="+_is_public+
			"	year="+_year+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "recruit_public_id",type=ColumnType.VARCHAR)
	public String getRecruitPublicId(){
		return this._recruit_public_id; 
	}

	public void setRecruitPublicId(String _recruit_public_id){
		this._recruit_public_id = _recruit_public_id;
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

	@Column(name = "plan_num",type=ColumnType.INT)
	public int getPlanNum(){
		return this._plan_num; 
	}

	public void setPlanNum(int _plan_num){
		this._plan_num = _plan_num;
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

	@Column(name = "resume_num",type=ColumnType.INT)
	public int getResumeNum(){
		return this._resume_num; 
	}

	public void setResumeNum(int _resume_num){
		this._resume_num = _resume_num;
	}

	@Column(name = "interview_num",type=ColumnType.INT)
	public int getInterviewNum(){
		return this._interview_num; 
	}

	public void setInterviewNum(int _interview_num){
		this._interview_num = _interview_num;
	}

	@Column(name = "offer_num",type=ColumnType.INT)
	public int getOfferNum(){
		return this._offer_num; 
	}

	public void setOfferNum(int _offer_num){
		this._offer_num = _offer_num;
	}

	@Column(name = "entry_num",type=ColumnType.INT)
	public int getEntryNum(){
		return this._entry_num; 
	}

	public void setEntryNum(int _entry_num){
		this._entry_num = _entry_num;
	}

	@Column(name = "is_public",type=ColumnType.TINYINT)
	public int getIsPublic(){
		return this._is_public; 
	}

	public void setIsPublic(int _is_public){
		this._is_public = _is_public;
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

	// 实例化内部类
	public static SourceRecruitPublicEntityAuxiliary  getEntityAuxiliary(){
		return new SourceRecruitPublicEntityAuxiliary();
	}

	public static class SourceRecruitPublicEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceRecruitPublicEntityAuxiliary asRecruitPublicId(String colName, CustomColumn<?, ?>... cols){
			setColName("recruit_public_id","`recruit_public_id`", colName, "setRecruitPublicId", "getString", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asEmployNum(String colName, CustomColumn<?, ?>... cols){
			setColName("employ_num","`employ_num`", colName, "setEmployNum", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asPlanNum(String colName, CustomColumn<?, ?>... cols){
			setColName("plan_num","`plan_num`", colName, "setPlanNum", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asEndDate(String colName, CustomColumn<?, ?>... cols){
			setColName("end_date","`end_date`", colName, "setEndDate", "getString", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asDays(String colName, CustomColumn<?, ?>... cols){
			setColName("days","`days`", colName, "setDays", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asResumeNum(String colName, CustomColumn<?, ?>... cols){
			setColName("resume_num","`resume_num`", colName, "setResumeNum", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asInterviewNum(String colName, CustomColumn<?, ?>... cols){
			setColName("interview_num","`interview_num`", colName, "setInterviewNum", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asOfferNum(String colName, CustomColumn<?, ?>... cols){
			setColName("offer_num","`offer_num`", colName, "setOfferNum", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asEntryNum(String colName, CustomColumn<?, ?>... cols){
			setColName("entry_num","`entry_num`", colName, "setEntryNum", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asIsPublic(String colName, CustomColumn<?, ?>... cols){
			setColName("is_public","`is_public`", colName, "setIsPublic", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceRecruitPublicEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
