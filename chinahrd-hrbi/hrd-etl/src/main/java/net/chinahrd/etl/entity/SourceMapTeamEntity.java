package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_map_team")
public class SourceMapTeamEntity implements Entity{

	private String _map_team_id;
	private String _emp_id;
	private String _customer_id;
	private String _team_name;
	private String _requirement;
	private int _pk_view;
	private String _create_time;

	public SourceMapTeamEntity(){
		super();
	}

	public SourceMapTeamEntity(String _map_team_id,String _emp_id,String _customer_id,String _team_name,String _requirement,int _pk_view,String _create_time){
		this._map_team_id = _map_team_id;
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._team_name = _team_name;
		this._requirement = _requirement;
		this._pk_view = _pk_view;
		this._create_time = _create_time;
	}

	@Override
	public String toString() {
		return "SourceMapTeamEntity ["+
			"	map_team_id="+_map_team_id+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	team_name="+_team_name+
			"	requirement="+_requirement+
			"	pk_view="+_pk_view+
			"	create_time="+_create_time+
			"]";
	}

	@Column(name = "map_team_id",type=ColumnType.VARCHAR)
	public String getMapTeamId(){
		return this._map_team_id; 
	}

	public void setMapTeamId(String _map_team_id){
		this._map_team_id = _map_team_id;
	}

	@Column(name = "emp_id",type=ColumnType.VARCHAR)
	public String getEmpId(){
		return this._emp_id; 
	}

	public void setEmpId(String _emp_id){
		this._emp_id = _emp_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "team_name",type=ColumnType.VARCHAR)
	public String getTeamName(){
		return this._team_name; 
	}

	public void setTeamName(String _team_name){
		this._team_name = _team_name;
	}

	@Column(name = "requirement",type=ColumnType.VARCHAR)
	public String getRequirement(){
		return this._requirement; 
	}

	public void setRequirement(String _requirement){
		this._requirement = _requirement;
	}

	@Column(name = "pk_view",type=ColumnType.INT)
	public int getPkView(){
		return this._pk_view; 
	}

	public void setPkView(int _pk_view){
		this._pk_view = _pk_view;
	}

	@Column(name = "create_time",type=ColumnType.DATE)
	public String getCreateTime(){
		return this._create_time; 
	}

	public void setCreateTime(String _create_time){
		this._create_time = _create_time;
	}

	// 实例化内部类
	public static SourceMapTeamEntityAuxiliary  getEntityAuxiliary(){
		return new SourceMapTeamEntityAuxiliary();
	}

	public static class SourceMapTeamEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceMapTeamEntityAuxiliary asMapTeamId(String colName, CustomColumn<?, ?>... cols){
			setColName("map_team_id","`map_team_id`", colName, "setMapTeamId", "getString", cols);
			return this;
		}
		public SourceMapTeamEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceMapTeamEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceMapTeamEntityAuxiliary asTeamName(String colName, CustomColumn<?, ?>... cols){
			setColName("team_name","`team_name`", colName, "setTeamName", "getString", cols);
			return this;
		}
		public SourceMapTeamEntityAuxiliary asRequirement(String colName, CustomColumn<?, ?>... cols){
			setColName("requirement","`requirement`", colName, "setRequirement", "getString", cols);
			return this;
		}
		public SourceMapTeamEntityAuxiliary asPkView(String colName, CustomColumn<?, ?>... cols){
			setColName("pk_view","`pk_view`", colName, "setPkView", "getInt", cols);
			return this;
		}
		public SourceMapTeamEntityAuxiliary asCreateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("create_time","`create_time`", colName, "setCreateTime", "getString", cols);
			return this;
		}
	}
}
