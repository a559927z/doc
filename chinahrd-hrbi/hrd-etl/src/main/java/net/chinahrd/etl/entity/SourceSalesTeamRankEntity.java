package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_sales_team_rank")
public class SourceSalesTeamRankEntity implements Entity{

	private String _team_id;
	private String _organization_id;
	private String _customer_id;
	private int _team_rank;
	private String _rank_date;
	private int _year_month;

	public SourceSalesTeamRankEntity(){
		super();
	}

	public SourceSalesTeamRankEntity(String _team_id,String _organization_id,String _customer_id,int _team_rank,String _rank_date,int _year_month){
		this._team_id = _team_id;
		this._organization_id = _organization_id;
		this._customer_id = _customer_id;
		this._team_rank = _team_rank;
		this._rank_date = _rank_date;
		this._year_month = _year_month;
	}

	@Override
	public String toString() {
		return "SourceSalesTeamRankEntity ["+
			"	team_id="+_team_id+
			"	organization_id="+_organization_id+
			"	customer_id="+_customer_id+
			"	team_rank="+_team_rank+
			"	rank_date="+_rank_date+
			"	year_month="+_year_month+
			"]";
	}

	@Column(name = "team_id",type=ColumnType.VARCHAR)
	public String getTeamId(){
		return this._team_id; 
	}

	public void setTeamId(String _team_id){
		this._team_id = _team_id;
	}

	@Column(name = "organization_id",type=ColumnType.VARCHAR)
	public String getOrganizationId(){
		return this._organization_id; 
	}

	public void setOrganizationId(String _organization_id){
		this._organization_id = _organization_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "team_rank",type=ColumnType.INT)
	public int getTeamRank(){
		return this._team_rank; 
	}

	public void setTeamRank(int _team_rank){
		this._team_rank = _team_rank;
	}

	@Column(name = "rank_date",type=ColumnType.DATE)
	public String getRankDate(){
		return this._rank_date; 
	}

	public void setRankDate(String _rank_date){
		this._rank_date = _rank_date;
	}

	@Column(name = "year_month",type=ColumnType.INT)
	public int getYearMonth(){
		return this._year_month; 
	}

	public void setYearMonth(int _year_month){
		this._year_month = _year_month;
	}

	// 实例化内部类
	public static SourceSalesTeamRankEntityAuxiliary  getEntityAuxiliary(){
		return new SourceSalesTeamRankEntityAuxiliary();
	}

	public static class SourceSalesTeamRankEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceSalesTeamRankEntityAuxiliary asTeamId(String colName, CustomColumn<?, ?>... cols){
			setColName("team_id","`team_id`", colName, "setTeamId", "getString", cols);
			return this;
		}
		public SourceSalesTeamRankEntityAuxiliary asOrganizationId(String colName, CustomColumn<?, ?>... cols){
			setColName("organization_id","`organization_id`", colName, "setOrganizationId", "getString", cols);
			return this;
		}
		public SourceSalesTeamRankEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceSalesTeamRankEntityAuxiliary asTeamRank(String colName, CustomColumn<?, ?>... cols){
			setColName("team_rank","`team_rank`", colName, "setTeamRank", "getInt", cols);
			return this;
		}
		public SourceSalesTeamRankEntityAuxiliary asRankDate(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_date","`rank_date`", colName, "setRankDate", "getString", cols);
			return this;
		}
		public SourceSalesTeamRankEntityAuxiliary asYearMonth(String colName, CustomColumn<?, ?>... cols){
			setColName("year_month","`year_month`", colName, "setYearMonth", "getInt", cols);
			return this;
		}
	}
}
