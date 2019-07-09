package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_job_relation")
public class SourceJobRelationEntity implements Entity{

	private String _job_relation_id;
	private String _customer_id;
	private String _sequence_sub_id;
	private String _sequence_id;
	private String _ability_id;
	private String _job_title_id;
	private String _ability_lv_id;
	private int _year;
	private int _type;
	private String _rank_name;
	private int _show_index;
	private String _refresh;

	public SourceJobRelationEntity(){
		super();
	}

	public SourceJobRelationEntity(String _job_relation_id,String _customer_id,String _sequence_sub_id,String _sequence_id,String _ability_id,String _job_title_id,String _ability_lv_id,int _year,int _type,String _rank_name,int _show_index,String _refresh){
		this._job_relation_id = _job_relation_id;
		this._customer_id = _customer_id;
		this._sequence_sub_id = _sequence_sub_id;
		this._sequence_id = _sequence_id;
		this._ability_id = _ability_id;
		this._job_title_id = _job_title_id;
		this._ability_lv_id = _ability_lv_id;
		this._year = _year;
		this._type = _type;
		this._rank_name = _rank_name;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceJobRelationEntity ["+
			"	job_relation_id="+_job_relation_id+
			"	customer_id="+_customer_id+
			"	sequence_sub_id="+_sequence_sub_id+
			"	sequence_id="+_sequence_id+
			"	ability_id="+_ability_id+
			"	job_title_id="+_job_title_id+
			"	ability_lv_id="+_ability_lv_id+
			"	year="+_year+
			"	type="+_type+
			"	rank_name="+_rank_name+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "job_relation_id",type=ColumnType.VARCHAR)
	public String getJobRelationId(){
		return this._job_relation_id; 
	}

	public void setJobRelationId(String _job_relation_id){
		this._job_relation_id = _job_relation_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "sequence_sub_id",type=ColumnType.VARCHAR)
	public String getSequenceSubId(){
		return this._sequence_sub_id; 
	}

	public void setSequenceSubId(String _sequence_sub_id){
		this._sequence_sub_id = _sequence_sub_id;
	}

	@Column(name = "sequence_id",type=ColumnType.VARCHAR)
	public String getSequenceId(){
		return this._sequence_id; 
	}

	public void setSequenceId(String _sequence_id){
		this._sequence_id = _sequence_id;
	}

	@Column(name = "ability_id",type=ColumnType.VARCHAR)
	public String getAbilityId(){
		return this._ability_id; 
	}

	public void setAbilityId(String _ability_id){
		this._ability_id = _ability_id;
	}

	@Column(name = "job_title_id",type=ColumnType.VARCHAR)
	public String getJobTitleId(){
		return this._job_title_id; 
	}

	public void setJobTitleId(String _job_title_id){
		this._job_title_id = _job_title_id;
	}

	@Column(name = "ability_lv_id",type=ColumnType.VARCHAR)
	public String getAbilityLvId(){
		return this._ability_lv_id; 
	}

	public void setAbilityLvId(String _ability_lv_id){
		this._ability_lv_id = _ability_lv_id;
	}

	@Column(name = "year",type=ColumnType.INT)
	public int getYear(){
		return this._year; 
	}

	public void setYear(int _year){
		this._year = _year;
	}

	@Column(name = "type",type=ColumnType.INT)
	public int getType(){
		return this._type; 
	}

	public void setType(int _type){
		this._type = _type;
	}

	@Column(name = "rank_name",type=ColumnType.VARCHAR)
	public String getRankName(){
		return this._rank_name; 
	}

	public void setRankName(String _rank_name){
		this._rank_name = _rank_name;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	@Column(name = "refresh",type=ColumnType.DATETIME)
	public String getRefresh(){
		return this._refresh; 
	}

	public void setRefresh(String _refresh){
		this._refresh = _refresh;
	}

	// 实例化内部类
	public static SourceJobRelationEntityAuxiliary  getEntityAuxiliary(){
		return new SourceJobRelationEntityAuxiliary();
	}

	public static class SourceJobRelationEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceJobRelationEntityAuxiliary asJobRelationId(String colName, CustomColumn<?, ?>... cols){
			setColName("job_relation_id","`job_relation_id`", colName, "setJobRelationId", "getString", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asSequenceSubId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_sub_id","`sequence_sub_id`", colName, "setSequenceSubId", "getString", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asSequenceId(String colName, CustomColumn<?, ?>... cols){
			setColName("sequence_id","`sequence_id`", colName, "setSequenceId", "getString", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asAbilityId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_id","`ability_id`", colName, "setAbilityId", "getString", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asJobTitleId(String colName, CustomColumn<?, ?>... cols){
			setColName("job_title_id","`job_title_id`", colName, "setJobTitleId", "getString", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asAbilityLvId(String colName, CustomColumn<?, ?>... cols){
			setColName("ability_lv_id","`ability_lv_id`", colName, "setAbilityLvId", "getString", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asYear(String colName, CustomColumn<?, ?>... cols){
			setColName("year","`year`", colName, "setYear", "getInt", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asType(String colName, CustomColumn<?, ?>... cols){
			setColName("type","`type`", colName, "setType", "getInt", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asRankName(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name","`rank_name`", colName, "setRankName", "getString", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceJobRelationEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
