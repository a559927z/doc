package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_job_change")
public class SourceJobChangeEntity implements Entity{

	private String _emp_job_change_id;
	private String _customer_id;
	private String _emp_id;
	private String _emp_key;
	private String _user_name_ch;
	private String _change_date;
	private String _change_type_id;
	private int _change_type;
	private String _organization_id;
	private String _organization_name;
	private String _position_id;
	private String _position_name;
	private String _sequence_id;
	private String _sequence_name;
	private String _sequence_sub_id;
	private String _sequence_sub_name;
	private String _ability_id;
	private String _ability_name;
	private String _ability_lv_id;
	private String _ability_lv_name;
	private String _job_title_id;
	private String _job_title_name;
	private String _rank_name;
	private String _rank_name_ed;
	private String _position_id_ed;
	private String _position_name_ed;
	private String _note;
	private String _refresh;
	private int _year_month;

	public SourceJobChangeEntity(){
		super();
	}

	public SourceJobChangeEntity(String _emp_job_change_id,String _customer_id,String _emp_id,String _emp_key,String _user_name_ch,String _change_date,String _change_type_id,int _change_type,String _organization_id,String _organization_name,String _position_id,String _position_name,String _sequence_id,String _sequence_name,String _sequence_sub_id,String _sequence_sub_name,String _ability_id,String _ability_name,String _ability_lv_id,String _ability_lv_name,String _job_title_id,String _job_title_name,String _rank_name,String _rank_name_ed,String _position_id_ed,String _position_name_ed,String _note,String _refresh,int _year_month){
		this._emp_job_change_id = _emp_job_change_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._emp_key = _emp_key;
		this._user_name_ch = _user_name_ch;
		this._change_date = _change_date;
		this._change_type_id = _change_type_id;
		this._change_type = _change_type;
		this._organization_id = _organization_id;
		this._organization_name = _organization_name;
		this._position_id = _position_id;
		this._position_name = _position_name;
		this._sequence_id = _sequence_id;
		this._sequence_name = _sequence_name;
		this._sequence_sub_id = _sequence_sub_id;
		this._sequence_sub_name = _sequence_sub_name;
		this._ability_id = _ability_id;
		this._ability_name = _ability_name;
		this._ability_lv_id = _ability_lv_id;
		this._ability_lv_name = _ability_lv_name;
		this._job_title_id = _job_title_id;
		this._job_title_name = _job_title_name;
		this._rank_name = _rank_name;
		this._rank_name_ed = _rank_name_ed;
		this._position_id_ed = _position_id_ed;
		this._position_name_ed = _position_name_ed;
		this._note = _note;
		this._refresh = _refresh;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceJobChangeEntity ["+
			"	emp_job_change_id="+_emp_job_change_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	emp_key="+_emp_key+
			"	user_name_ch="+_user_name_ch+
			"	change_date="+_change_date+
			"	change_type_id="+_change_type_id+
			"	change_type="+_change_type+
			"	organization_id="+_organization_id+
			"	organization_name="+_organization_name+
			"	position_id="+_position_id+
			"	position_name="+_position_name+
			"	sequence_id="+_sequence_id+
			"	sequence_name="+_sequence_name+
			"	sequence_sub_id="+_sequence_sub_id+
			"	sequence_sub_name="+_sequence_sub_name+
			"	ability_id="+_ability_id+
			"	ability_name="+_ability_name+
			"	ability_lv_id="+_ability_lv_id+
			"	ability_lv_name="+_ability_lv_name+
			"	job_title_id="+_job_title_id+
			"	job_title_name="+_job_title_name+
			"	rank_name="+_rank_name+
			"	rank_name_ed="+_rank_name_ed+
			"	position_id_ed="+_position_id_ed+
			"	position_name_ed="+_position_name_ed+
			"	note="+_note+
			"	refresh="+_refresh+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "emp_job_change_id",type=ColumnType.VARCHAR)
	public String getEmpJobChangeId(){
		return this._emp_job_change_id; 
	}

	public void setEmpJobChangeId(String _emp_job_change_id){
		this._emp_job_change_id = _emp_job_change_id;
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

	@Column(name = "emp_key",type=ColumnType.VARCHAR)
	public String getEmpKey(){
		return this._emp_key; 
	}

	public void setEmpKey(String _emp_key){
		this._emp_key = _emp_key;
	}

	@Column(name = "user_name_ch",type=ColumnType.VARCHAR)
	public String getUserNameCh(){
		return this._user_name_ch; 
	}

	public void setUserNameCh(String _user_name_ch){
		this._user_name_ch = _user_name_ch;
	}

	@Column(name = "change_date",type=ColumnType.DATE)
	public String getChangeDate(){
		return this._change_date; 
	}

	public void setChangeDate(String _change_date){
		this._change_date = _change_date;
	}

	@Column(name = "change_type_id",type=ColumnType.VARCHAR)
	public String getChangeTypeId(){
		return this._change_type_id; 
	}

	public void setChangeTypeId(String _change_type_id){
		this._change_type_id = _change_type_id;
	}

	@Column(name = "change_type",type=ColumnType.INT)
	public int getChangeType(){
		return this._change_type; 
	}

	public void setChangeType(int _change_type){
		this._change_type = _change_type;
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

	@Column(name = "position_id",type=ColumnType.VARCHAR)
	public String getPositionId(){
		return this._position_id; 
	}

	public void setPositionId(String _position_id){
		this._position_id = _position_id;
	}

	@Column(name = "position_name",type=ColumnType.VARCHAR)
	public String getPositionName(){
		return this._position_name; 
	}

	public void setPositionName(String _position_name){
		this._position_name = _position_name;
	}

	@Column(name = "sequence_id",type=ColumnType.VARCHAR)
	public String getSequenceId(){
		return this._sequence_id; 
	}

	public void setSequenceId(String _sequence_id){
		this._sequence_id = _sequence_id;
	}

	@Column(name = "sequence_name",type=ColumnType.VARCHAR)
	public String getSequenceName(){
		return this._sequence_name; 
	}

	public void setSequenceName(String _sequence_name){
		this._sequence_name = _sequence_name;
	}

	@Column(name = "sequence_sub_id",type=ColumnType.VARCHAR)
	public String getSequenceSubId(){
		return this._sequence_sub_id; 
	}

	public void setSequenceSubId(String _sequence_sub_id){
		this._sequence_sub_id = _sequence_sub_id;
	}

	@Column(name = "sequence_sub_name",type=ColumnType.VARCHAR)
	public String getSequenceSubName(){
		return this._sequence_sub_name; 
	}

	public void setSequenceSubName(String _sequence_sub_name){
		this._sequence_sub_name = _sequence_sub_name;
	}

	@Column(name = "ability_id",type=ColumnType.VARCHAR)
	public String getAbilityId(){
		return this._ability_id; 
	}

	public void setAbilityId(String _ability_id){
		this._ability_id = _ability_id;
	}

	@Column(name = "ability_name",type=ColumnType.VARCHAR)
	public String getAbilityName(){
		return this._ability_name; 
	}

	public void setAbilityName(String _ability_name){
		this._ability_name = _ability_name;
	}

	@Column(name = "ability_lv_id",type=ColumnType.VARCHAR)
	public String getAbilityLvId(){
		return this._ability_lv_id; 
	}

	public void setAbilityLvId(String _ability_lv_id){
		this._ability_lv_id = _ability_lv_id;
	}

	@Column(name = "ability_lv_name",type=ColumnType.VARCHAR)
	public String getAbilityLvName(){
		return this._ability_lv_name; 
	}

	public void setAbilityLvName(String _ability_lv_name){
		this._ability_lv_name = _ability_lv_name;
	}

	@Column(name = "job_title_id",type=ColumnType.VARCHAR)
	public String getJobTitleId(){
		return this._job_title_id; 
	}

	public void setJobTitleId(String _job_title_id){
		this._job_title_id = _job_title_id;
	}

	@Column(name = "job_title_name",type=ColumnType.VARCHAR)
	public String getJobTitleName(){
		return this._job_title_name; 
	}

	public void setJobTitleName(String _job_title_name){
		this._job_title_name = _job_title_name;
	}

	@Column(name = "rank_name",type=ColumnType.VARCHAR)
	public String getRankName(){
		return this._rank_name; 
	}

	public void setRankName(String _rank_name){
		this._rank_name = _rank_name;
	}

	@Column(name = "rank_name_ed",type=ColumnType.VARCHAR)
	public String getRankNameEd(){
		return this._rank_name_ed; 
	}

	public void setRankNameEd(String _rank_name_ed){
		this._rank_name_ed = _rank_name_ed;
	}

	@Column(name = "position_id_ed",type=ColumnType.VARCHAR)
	public String getPositionIdEd(){
		return this._position_id_ed; 
	}

	public void setPositionIdEd(String _position_id_ed){
		this._position_id_ed = _position_id_ed;
	}

	@Column(name = "position_name_ed",type=ColumnType.VARCHAR)
	public String getPositionNameEd(){
		return this._position_name_ed; 
	}

	public void setPositionNameEd(String _position_name_ed){
		this._position_name_ed = _position_name_ed;
	}

	@Column(name = "note",type=ColumnType.VARCHAR)
	public String getNote(){
		return this._note; 
	}

	public void setNote(String _note){
		this._note = _note;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceJobChangeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceJobChangeEntityAuxiliary();
	}

	public static class SourceJobChangeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceJobChangeEntityAuxiliary asEmpJobChangeId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_job_change_id","`emp_job_change_id`", colName, "setEmpJobChangeId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asEmpKey(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_key","`emp_key`", colName, "setEmpKey", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asChangeDate(String colName, CustomColumn<?, ?>... cols){
			setColName("change_date","`change_date`", colName, "setChangeDate", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asChangeTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("change_type_id","`change_type_id`", colName, "setChangeTypeId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asChangeType(String colName, CustomColumn<?, ?>... cols){
			setColName("change_type","`change_type`", colName, "setChangeType", "getInt", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asOrganizationName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name","`organization_name`", colName, "setOrganizationName", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asPositionName(String colName, CustomColumn<?, ?>... cols){
			setColName("position_name","`position_name`", colName, "setPositionName", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asSequenceName(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_name","`sequence_name`", colName, "setSequenceName", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asSequenceSubId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_id","`sequence_sub_id`", colName, "setSequenceSubId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asSequenceSubName(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_name","`sequence_sub_name`", colName, "setSequenceSubName", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asAbilityId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_id","`ability_id`", colName, "setAbilityId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asAbilityName(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_name","`ability_name`", colName, "setAbilityName", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asAbilityLvId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_lv_id","`ability_lv_id`", colName, "setAbilityLvId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asAbilityLvName(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_lv_name","`ability_lv_name`", colName, "setAbilityLvName", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asJobTitleId(String colName, CustomColumn<?, ?>... cols){
			setColName("job_title_id","`job_title_id`", colName, "setJobTitleId", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asJobTitleName(String colName, CustomColumn<?, ?>... cols){
			setColName("job_title_name","`job_title_name`", colName, "setJobTitleName", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asRankName(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name","`rank_name`", colName, "setRankName", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asRankNameEd(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name_ed","`rank_name_ed`", colName, "setRankNameEd", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asPositionIdEd(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id_ed","`position_id_ed`", colName, "setPositionIdEd", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asPositionNameEd(String colName, CustomColumn<?, ?>... cols){
			setColName("position_name_ed","`position_name_ed`", colName, "setPositionNameEd", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourceJobChangeEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
