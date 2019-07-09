package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_performance_change")
public class SourcePerformanceChangeEntity implements Entity{

	private String _performance_change_id;
	private String _customer_id;
	private String _emp_id;
	private String _emp_key;
	private String _organization_parent_id;
	private String _organzation_parent_name;
	private String _organization_id;
	private String _organization_name;
	private String _position_id;
	private String _position_name;
	private String _performance_id;
	private String _performance_name;
	private String _rank_name;
	private String _rank_name_past;
	private int _year_month;
	private int _type;

	public SourcePerformanceChangeEntity(){
		super();
	}

	public SourcePerformanceChangeEntity(String _performance_change_id,String _customer_id,String _emp_id,String _emp_key,String _organization_parent_id,String _organzation_parent_name,String _organization_id,String _organization_name,String _position_id,String _position_name,String _performance_id,String _performance_name,String _rank_name,String _rank_name_past,int _year_month,int _type){
		this._performance_change_id = _performance_change_id;
		this._customer_id = _customer_id;
		this._emp_id = _emp_id;
		this._emp_key = _emp_key;
		this._organization_parent_id = _organization_parent_id;
		this._organzation_parent_name = _organzation_parent_name;
		this._organization_id = _organization_id;
		this._organization_name = _organization_name;
		this._position_id = _position_id;
		this._position_name = _position_name;
		this._performance_id = _performance_id;
		this._performance_name = _performance_name;
		this._rank_name = _rank_name;
		this._rank_name_past = _rank_name_past;
		this._year_month = _year_month;
		this._type = _type;
	}

	@Override
	public String toString() {
		return "SourcePerformanceChangeEntity ["+
			"	performance_change_id="+_performance_change_id+
			"	customer_id="+_customer_id+
			"	emp_id="+_emp_id+
			"	emp_key="+_emp_key+
			"	organization_parent_id="+_organization_parent_id+
			"	organzation_parent_name="+_organzation_parent_name+
			"	organization_id="+_organization_id+
			"	organization_name="+_organization_name+
			"	position_id="+_position_id+
			"	position_name="+_position_name+
			"	performance_id="+_performance_id+
			"	performance_name="+_performance_name+
			"	rank_name="+_rank_name+
			"	rank_name_past="+_rank_name_past+
			"	year_month="+_year_month+
			"	type="+_type+
			"]";
	}

	@Column(name = "performance_change_id",type=ColumnType.VARCHAR)
	public String getPerformanceChangeId(){
		return this._performance_change_id; 
	}

	public void setPerformanceChangeId(String _performance_change_id){
		this._performance_change_id = _performance_change_id;
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

	@Column(name = "organization_parent_id",type=ColumnType.VARCHAR)
	public String getOrganizationParentId(){
		return this._organization_parent_id; 
	}

	public void setOrganizationParentId(String _organization_parent_id){
		this._organization_parent_id = _organization_parent_id;
	}

	@Column(name = "organzation_parent_name",type=ColumnType.VARCHAR)
	public String getOrganzationParentName(){
		return this._organzation_parent_name; 
	}

	public void setOrganzationParentName(String _organzation_parent_name){
		this._organzation_parent_name = _organzation_parent_name;
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

	@Column(name = "performance_id",type=ColumnType.VARCHAR)
	public String getPerformanceId(){
		return this._performance_id; 
	}

	public void setPerformanceId(String _performance_id){
		this._performance_id = _performance_id;
	}

	@Column(name = "performance_name",type=ColumnType.VARCHAR)
	public String getPerformanceName(){
		return this._performance_name; 
	}

	public void setPerformanceName(String _performance_name){
		this._performance_name = _performance_name;
	}

	@Column(name = "rank_name",type=ColumnType.VARCHAR)
	public String getRankName(){
		return this._rank_name; 
	}

	public void setRankName(String _rank_name){
		this._rank_name = _rank_name;
	}

	@Column(name = "rank_name_past",type=ColumnType.VARCHAR)
	public String getRankNamePast(){
		return this._rank_name_past; 
	}

	public void setRankNamePast(String _rank_name_past){
		this._rank_name_past = _rank_name_past;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	@Column(name = "type",type=ColumnType.INT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	// 实例化内部类
	public static SourcePerformanceChangeEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePerformanceChangeEntityAuxiliary();
	}

	public static class SourcePerformanceChangeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePerformanceChangeEntityAuxiliary asPerformanceChangeId(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_change_id","`performance_change_id`", colName, "setPerformanceChangeId", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asEmpKey(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_key","`emp_key`", colName, "setEmpKey", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asOrganizationParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_parent_id","`organization_parent_id`", colName, "setOrganizationParentId", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asOrganzationParentName(String colName, CustomColumn<?, ?>... cols){
			setColName("organzation_parent_name","`organzation_parent_name`", colName, "setOrganzationParentName", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asOrganizationName(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_name","`organization_name`", colName, "setOrganizationName", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asPositionId(String colName, CustomColumn<?, ?>... cols){
			setColName("position_id","`position_id`", colName, "setPositionId", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asPositionName(String colName, CustomColumn<?, ?>... cols){
			setColName("position_name","`position_name`", colName, "setPositionName", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asPerformanceId(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_id","`performance_id`", colName, "setPerformanceId", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asPerformanceName(String colName, CustomColumn<?, ?>... cols){
			setColName("performance_name","`performance_name`", colName, "setPerformanceName", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asRankName(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name","`rank_name`", colName, "setRankName", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asRankNamePast(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name_past","`rank_name_past`", colName, "setRankNamePast", "getString", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
		public SourcePerformanceChangeEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
	}
}
