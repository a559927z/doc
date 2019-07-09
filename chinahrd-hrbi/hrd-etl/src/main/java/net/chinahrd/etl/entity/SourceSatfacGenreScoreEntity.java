package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_satfac_genre_score")
public class SourceSatfacGenreScoreEntity implements Entity{

	private String _satfac_score_id;
	private String _customer_id;
	private String _organization_id;
	private String _satfac_genre_id;
	private Double _score;
	private String _date;

	public SourceSatfacGenreScoreEntity(){
		super();
	}

	public SourceSatfacGenreScoreEntity(String _satfac_score_id,String _customer_id,String _organization_id,String _satfac_genre_id,Double _score,String _date){
		this._satfac_score_id = _satfac_score_id;
		this._customer_id = _customer_id;
		this._organization_id = _organization_id;
		this._satfac_genre_id = _satfac_genre_id;
		this._score = _score;
		this._date = _date;
	}

	@Override
	public String toString() {
		return "SourceSatfacGenreScoreEntity ["+
			"	satfac_score_id="+_satfac_score_id+
			"	customer_id="+_customer_id+
			"	organization_id="+_organization_id+
			"	satfac_genre_id="+_satfac_genre_id+
			"	score="+_score+
			"	date="+_date+
			"]";
	}

	@Column(name = "satfac_score_id",type=ColumnType.VARCHAR)
	public String getSatfacScoreId(){
		return this._satfac_score_id; 
	}

	public void setSatfacScoreId(String _satfac_score_id){
		this._satfac_score_id = _satfac_score_id;
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

	@Column(name = "satfac_genre_id",type=ColumnType.VARCHAR)
	public String getSatfacGenreId(){
		return this._satfac_genre_id; 
	}

	public void setSatfacGenreId(String _satfac_genre_id){
		this._satfac_genre_id = _satfac_genre_id;
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
	public static SourceSatfacGenreScoreEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSatfacGenreScoreEntityAuxiliary();
	}

	public static class SourceSatfacGenreScoreEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSatfacGenreScoreEntityAuxiliary asSatfacScoreId(String colName, CustomColumn<?, ?>... cols){
			setColName("satfac_score_id","`satfac_score_id`", colName, "setSatfacScoreId", "getString", cols);
			return this;
		}
		public SourceSatfacGenreScoreEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSatfacGenreScoreEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceSatfacGenreScoreEntityAuxiliary asSatfacGenreId(String colName, CustomColumn<?, ?>... cols){
			setColName("satfac_genre_id","`satfac_genre_id`", colName, "setSatfacGenreId", "getString", cols);
			return this;
		}
		public SourceSatfacGenreScoreEntityAuxiliary asScore(String colName, CustomColumn<?, ?>... cols){
			setColName("score","`score`", colName, "setScore", "getDouble", cols);
			return this;
		}
		public SourceSatfacGenreScoreEntityAuxiliary asDate(String colName, CustomColumn<?, ?>... cols){
			setColName("date","`date`", colName, "setDate", "getString", cols);
			return this;
		}
	}
}
