package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_promotion_plan")
public class SourcePromotionPlanEntity implements Entity{

	private String _promotion_plan_id;
	private String _customer_id;
	private String _rank_name_af;
	private String _scheme_id;
	private String _create_user_id;
	private String _modify_user_id;
	private String _create_time;
	private String _modify_time;

	public SourcePromotionPlanEntity(){
		super();
	}

	public SourcePromotionPlanEntity(String _promotion_plan_id,String _customer_id,String _rank_name_af,String _scheme_id,String _create_user_id,String _modify_user_id,String _create_time,String _modify_time){
		this._promotion_plan_id = _promotion_plan_id;
		this._customer_id = _customer_id;
		this._rank_name_af = _rank_name_af;
		this._scheme_id = _scheme_id;
		this._create_user_id = _create_user_id;
		this._modify_user_id = _modify_user_id;
		this._create_time = _create_time;
		this._modify_time = _modify_time;
	}

	@Override
	public String toString() {
		return "SourcePromotionPlanEntity ["+
			"	promotion_plan_id="+_promotion_plan_id+
			"	customer_id="+_customer_id+
			"	rank_name_af="+_rank_name_af+
			"	scheme_id="+_scheme_id+
			"	create_user_id="+_create_user_id+
			"	modify_user_id="+_modify_user_id+
			"	create_time="+_create_time+
			"	modify_time="+_modify_time+
			"]";
	}

	@Column(name = "promotion_plan_id",type=ColumnType.VARBINARY)
	public String getPromotionPlanId(){
		return this._promotion_plan_id; 
	}

	public void setPromotionPlanId(String _promotion_plan_id){
		this._promotion_plan_id = _promotion_plan_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "rank_name_af",type=ColumnType.VARBINARY)
	public String getRankNameAf(){
		return this._rank_name_af; 
	}

	public void setRankNameAf(String _rank_name_af){
		this._rank_name_af = _rank_name_af;
	}

	@Column(name = "scheme_id",type=ColumnType.VARCHAR)
	public String getSchemeId(){
		return this._scheme_id; 
	}

	public void setSchemeId(String _scheme_id){
		this._scheme_id = _scheme_id;
	}

	@Column(name = "create_user_id",type=ColumnType.VARCHAR)
	public String getCreateUserId(){
		return this._create_user_id; 
	}

	public void setCreateUserId(String _create_user_id){
		this._create_user_id = _create_user_id;
	}

	@Column(name = "modify_user_id",type=ColumnType.VARCHAR)
	public String getModifyUserId(){
		return this._modify_user_id; 
	}

	public void setModifyUserId(String _modify_user_id){
		this._modify_user_id = _modify_user_id;
	}

	@Column(name = "create_time",type=ColumnType.DATETIME)
	public String getCreateTime(){
		return this._create_time; 
	}

	public void setCreateTime(String _create_time){
		this._create_time = _create_time;
	}

	@Column(name = "modify_time",type=ColumnType.DATETIME)
	public String getModifyTime(){
		return this._modify_time; 
	}

	public void setModifyTime(String _modify_time){
		this._modify_time = _modify_time;
	}

	// 实例化内部类
	public static SourcePromotionPlanEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePromotionPlanEntityAuxiliary();
	}

	public static class SourcePromotionPlanEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePromotionPlanEntityAuxiliary asPromotionPlanId(String colName, CustomColumn<?, ?>... cols){
			setColName("promotion_plan_id","`promotion_plan_id`", colName, "setPromotionPlanId", "getString", cols);
			return this;
		}
		public SourcePromotionPlanEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePromotionPlanEntityAuxiliary asRankNameAf(String colName, CustomColumn<?, ?>... cols){
			setColName("rank_name_af","`rank_name_af`", colName, "setRankNameAf", "getString", cols);
			return this;
		}
		public SourcePromotionPlanEntityAuxiliary asSchemeId(String colName, CustomColumn<?, ?>... cols){
			setColName("scheme_id","`scheme_id`", colName, "setSchemeId", "getString", cols);
			return this;
		}
		public SourcePromotionPlanEntityAuxiliary asCreateUserId(String colName, CustomColumn<?, ?>... cols){
			setColName("create_user_id","`create_user_id`", colName, "setCreateUserId", "getString", cols);
			return this;
		}
		public SourcePromotionPlanEntityAuxiliary asModifyUserId(String colName, CustomColumn<?, ?>... cols){
			setColName("modify_user_id","`modify_user_id`", colName, "setModifyUserId", "getString", cols);
			return this;
		}
		public SourcePromotionPlanEntityAuxiliary asCreateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("create_time","`create_time`", colName, "setCreateTime", "getString", cols);
			return this;
		}
		public SourcePromotionPlanEntityAuxiliary asModifyTime(String colName, CustomColumn<?, ?>... cols){
			setColName("modify_time","`modify_time`", colName, "setModifyTime", "getString", cols);
			return this;
		}
	}
}
