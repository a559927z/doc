package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_organization_change")
public class SourceOrganizationChangeEntity implements Entity{

	private String _organization_change_id;
	private String _customer_id;
	private String _emp_id;
	private String _emp_key;
	private String _user_name_ch;
	private String _change_date;
	private String _change_type_id;
	private String _organization_id;
	private String _organization_name;
	private String _organization_id_ed;
	private String _organization_name_ed;
	private String _position_id;
	private String _position_name;
	private String _sequence_id;
	private String _sequence_name;
	private String _ability_id;
	private String _ability_name;
	private String _note;
	private String _refresh;
	private int _year_month;

	public SourceOrganizationChangeEntity(){
		super();
	}

	public SourceOrganizationChangeEntity(String _organization_change_id,String _customer_id,String _emp_id,String _emp_key,String _user_name_ch,String _change_date,String _change_type_id,String _organization_id,String _organization_name,String _organization_id_ed,String _organization_name_ed,String _position_id,String _position_name,String _sequence_id,String _sequence_name,String _ability_id,String _ability_name,String _note,String _refresh,int _year_month){
		this._organization_change_id = _organization_change_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._emp_key = _emp_key;
		this._user_name_ch = _user_name_ch;
		this._change_date = _change_date;
		this._change_type_id = _change_type_id;
		this._organization_id = _organization_id;
		this._organization_name = _organization_name;
		this._organization_id_ed = _organization_id_ed;
		this._organization_name_ed = _organization_name_ed;
		this._position_id = _position_id;
		this._position_name = _position_name;
		this._sequence_id = _sequence_id;
		this._sequence_name = _sequence_name;
		this._ability_id = _ability_id;
		this._ability_name = _ability_name;
		this._note = _note;
		this._refresh = _refresh;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceOrganizationChangeEntity ["+
			"	organization_change_id="+_organization_change_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	emp_key="+_emp_key+
			"	user_name_ch="+_user_name_ch+
			"	change_date="+_change_date+
			"	change_type_id="+_change_type_id+
			"	organization_id="+_organization_id+
			"	organization_name="+_organization_name+
			"	organization_id_ed="+_organization_id_ed+
			"	organization_name_ed="+_organization_name_ed+
			"	position_id="+_position_id+
			"	position_name="+_position_name+
			"	sequence_id="+_sequence_id+
			"	sequence_name="+_sequence_name+
			"	ability_id="+_ability_id+
			"	ability_name="+_ability_name+
			"	note="+_note+
			"	refresh="+_refresh+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "organization_change_id",type=ColumnType.VARCHAR)
	public String getOrganizationChangeId(){
		return this._organization_change_id; 
	}

	public void setOrganizationChangeId(String _organization_change_id){
		this._organization_change_id = _organization_change_id;
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

	@Column(name = "change_date",type=ColumnType.DATETIME)
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

	@Column(name = "organization_id_ed",type=ColumnType.VARCHAR)
	public String getOrganizationIdEd(){
		return this._organization_id_ed; 
	}

	public void setOrganizationIdEd(String _organization_id_ed){
		this._organization_id_ed = _organization_id_ed;
	}

	@Column(name = "organization_name_ed",type=ColumnType.VARCHAR)
	public String getOrganizationNameEd(){
		return this._organization_name_ed; 
	}

	public void setOrganizationNameEd(String _organization_name_ed){
		this._organization_name_ed = _organization_name_ed;
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
	public static SourceOrganizationChangeEntityAuxiliary  getEntityAuxiliary(){
		return new SourceOrganizationChangeEntityAuxiliary();
	}

	public static class SourceOrganizationChangeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceOrganizationChangeEntityAuxiliary asOrganizationChangeId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_change_id","`organization_change_id`", colName, "setOrganizationChangeId", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asEmpKey(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_key","`emp_key`", colName, "setEmpKey", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asUserNameCh(String colName, CustomColumn<?, ?>... cols){
			setColName("user_name_ch","`user_name_ch`", colName, "setUserNameCh", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asChangeDate(String colName, CustomColumn<?, ?>... cols){
			setColName("change_date","`change_date`", colName, "setChangeDate", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asChangeTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("change_type_id","`change_type_id`", colName, "setChangeTypeId", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asOrganizationName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name","`organization_name`", colName, "setOrganizationName", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asOrganizationIdEd(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id_ed","`organization_id_ed`", colName, "setOrganizationIdEd", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asOrganizationNameEd(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name_ed","`organization_name_ed`", colName, "setOrganizationNameEd", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asPositionName(String colName, CustomColumn<?, ?>... cols){
			setColName("position_name","`position_name`", colName, "setPositionName", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asSequenceName(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_name","`sequence_name`", colName, "setSequenceName", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asAbilityId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_id","`ability_id`", colName, "setAbilityId", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asAbilityName(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_name","`ability_name`", colName, "setAbilityName", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asNote(String colName, CustomColumn<?, ?>... cols){
			setColName("note","`note`", colName, "setNote", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
		public SourceOrganizationChangeEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
