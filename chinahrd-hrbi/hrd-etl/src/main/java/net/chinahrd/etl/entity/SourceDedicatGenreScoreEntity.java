package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dedicat_genre_score")
public class SourceDedicatGenreScoreEntity implements Entity{

	private String _dedicat_score_id;
	private String _customer_id;
	private String _organization_id;
	private String _dedicat_genre_id;
	private Double _score;
	private String _date;

	public SourceDedicatGenreScoreEntity(){
		super();
	}

	public SourceDedicatGenreScoreEntity(String _dedicat_score_id,String _customer_id,String _organization_id,String _dedicat_genre_id,Double _score,String _date){
		this._dedicat_score_id = _dedicat_score_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._dedicat_genre_id = _dedicat_genre_id;
		this._score = _score;
		this._date = _date;
	}

	@Override
	public String toString() {
		return "SourceDedicatGenreScoreEntity ["+
			"	dedicat_score_id="+_dedicat_score_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	dedicat_genre_id="+_dedicat_genre_id+
			"	score="+_score+
			"	date="+_date+
			"]";
	}

	@Column(name = "dedicat_score_id",type=ColumnType.VARCHAR)
	public String getDedicatScoreId(){
		return this._dedicat_score_id; 
	}

	public void setDedicatScoreId(String _dedicat_score_id){
		this._dedicat_score_id = _dedicat_score_id;
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

	@Column(name = "dedicat_genre_id",type=ColumnType.VARCHAR)
	public String getDedicatGenreId(){
		return this._dedicat_genre_id; 
	}

	public void setDedicatGenreId(String _dedicat_genre_id){
		this._dedicat_genre_id = _dedicat_genre_id;
	}

	@Column(name = "score",type=ColumnType.DOUBLE)
	public Double getScore(){
		return this._score; 
	}

	public void setScore(Double _score){
		this._score = _score;
	}

	@Column(name = "date",type=ColumnType.DATE)
	public String getDate(){
		return this._date; 
	}

	public void setDate(String _date){
		this._date = _date;
	}

	// 实例化内部类
	public static SourceDedicatGenreScoreEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDedicatGenreScoreEntityAuxiliary();
	}

	public static class SourceDedicatGenreScoreEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDedicatGenreScoreEntityAuxiliary asDedicatScoreId(String colName, CustomColumn<?, ?>... cols){
			setColName("dedicat_score_id","`dedicat_score_id`", colName, "setDedicatScoreId", "getString", cols);
			return this;
		}
		public SourceDedicatGenreScoreEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDedicatGenreScoreEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceDedicatGenreScoreEntityAuxiliary asDedicatGenreId(String colName, CustomColumn<?, ?>... cols){
			setColName("dedicat_genre_id","`dedicat_genre_id`", colName, "setDedicatGenreId", "getString", cols);
			return this;
		}
		public SourceDedicatGenreScoreEntityAuxiliary asScore(String colName, CustomColumn<?, ?>... cols){
			setColName("score","`score`", colName, "setScore", "getDouble", cols);
			return this;
		}
		public SourceDedicatGenreScoreEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
	}
}
