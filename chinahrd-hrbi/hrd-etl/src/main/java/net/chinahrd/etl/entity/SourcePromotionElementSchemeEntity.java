package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_promotion_element_scheme")
public class SourcePromotionElementSchemeEntity implements Entity{

	private String _scheme_id;
	private String _customer_id;
	private String _scheme_name;
	private int _company_age;
	private String _curt_name_per;
	private int _curt_name_eval;
	private String _certificate_id;
	private String _certificate_type_id;
	private String _create_user_id;
	private String _modify_user_id;
	private String _create_time;
	private String _modify_time;
	private String _start_date;
	private String _invalid_date;

	public SourcePromotionElementSchemeEntity(){
		super();
	}

	public SourcePromotionElementSchemeEntity(String _scheme_id,String _customer_id,String _scheme_name,int _company_age,String _curt_name_per,int _curt_name_eval,String _certificate_id,String _certificate_type_id,String _create_user_id,String _modify_user_id,String _create_time,String _modify_time,String _start_date,String _invalid_date){
		this._scheme_id = _scheme_id;
		this._customer_id = _customer_id;
		this._scheme_name = _scheme_name;
		this._company_age = _company_age;
		this._curt_name_per = _curt_name_per;
		this._curt_name_eval = _curt_name_eval;
		this._certificate_id = _certificate_id;
		this._certificate_type_id = _certificate_type_id;
		this._create_user_id = _create_user_id;
		this._modify_user_id = _modify_user_id;
		this._create_time = _create_time;
		this._modify_time = _modify_time;
		this._start_date = _start_date;
		this._invalid_date = _invalid_date;
	}

	@Override
	public String toString() {
		return "SourcePromotionElementSchemeEntity ["+
			"	scheme_id="+_scheme_id+
			"	customer_id="+_customer_id+
			"	scheme_name="+_scheme_name+
			"	company_age="+_company_age+
			"	curt_name_per="+_curt_name_per+
			"	curt_name_eval="+_curt_name_eval+
			"	certificate_id="+_certificate_id+
			"	certificate_type_id="+_certificate_type_id+
			"	create_user_id="+_create_user_id+
			"	modify_user_id="+_modify_user_id+
			"	create_time="+_create_time+
			"	modify_time="+_modify_time+
			"	start_date="+_start_date+
			"	invalid_date="+_invalid_date+
			"]";
	}

	@Column(name = "scheme_id",type=ColumnType.VARCHAR)
	public String getSchemeId(){
		return this._scheme_id; 
	}

	public void setSchemeId(String _scheme_id){
		this._scheme_id = _scheme_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "scheme_name",type=ColumnType.VARCHAR)
	public String getSchemeName(){
		return this._scheme_name; 
	}

	public void setSchemeName(String _scheme_name){
		this._scheme_name = _scheme_name;
	}

	@Column(name = "company_age",type=ColumnType.INT)
	public int getCompanyAge(){
		return this._company_age; 
	}

	public void setCompanyAge(int _company_age){
		this._company_age = _company_age;
	}

	@Column(name = "curt_name_per",type=ColumnType.VARCHAR)
	public String getCurtNamePer(){
		return this._curt_name_per; 
	}

	public void setCurtNamePer(String _curt_name_per){
		this._curt_name_per = _curt_name_per;
	}

	@Column(name = "curt_name_eval",type=ColumnType.INT)
	public int getCurtNameEval(){
		return this._curt_name_eval; 
	}

	public void setCurtNameEval(int _curt_name_eval){
		this._curt_name_eval = _curt_name_eval;
	}

	@Column(name = "certificate_id",type=ColumnType.VARCHAR)
	public String getCertificateId(){
		return this._certificate_id; 
	}

	public void setCertificateId(String _certificate_id){
		this._certificate_id = _certificate_id;
	}

	@Column(name = "certificate_type_id",type=ColumnType.VARCHAR)
	public String getCertificateTypeId(){
		return this._certificate_type_id; 
	}

	public void setCertificateTypeId(String _certificate_type_id){
		this._certificate_type_id = _certificate_type_id;
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

	@Column(name = "start_date",type=ColumnType.DATETIME)
	public String getStartDate(){
		return this._start_date; 
	}

	public void setStartDate(String _start_date){
		this._start_date = _start_date;
	}

	@Column(name = "invalid_date",type=ColumnType.DATETIME)
	public String getInvalidDate(){
		return this._invalid_date; 
	}

	public void setInvalidDate(String _invalid_date){
		this._invalid_date = _invalid_date;
	}

	// 实例化内部类
	public static SourcePromotionElementSchemeEntityAuxiliary  getEntityAuxiliary(){
		return new SourcePromotionElementSchemeEntityAuxiliary();
	}

	public static class SourcePromotionElementSchemeEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourcePromotionElementSchemeEntityAuxiliary asSchemeId(String colName, CustomColumn<?, ?>... cols){
			setColName("scheme_id","`scheme_id`", colName, "setSchemeId", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asSchemeName(String colName, CustomColumn<?, ?>... cols){
			setColName("scheme_name","`scheme_name`", colName, "setSchemeName", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asCompanyAge(String colName, CustomColumn<?, ?>... cols){
			setColName("company_age","`company_age`", colName, "setCompanyAge", "getInt", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asCurtNamePer(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name_per","`curt_name_per`", colName, "setCurtNamePer", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asCurtNameEval(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name_eval","`curt_name_eval`", colName, "setCurtNameEval", "getInt", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asCertificateId(String colName, CustomColumn<?, ?>... cols){
			setColName("certificate_id","`certificate_id`", colName, "setCertificateId", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asCertificateTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("certificate_type_id","`certificate_type_id`", colName, "setCertificateTypeId", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asCreateUserId(String colName, CustomColumn<?, ?>... cols){
			setColName("create_user_id","`create_user_id`", colName, "setCreateUserId", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asModifyUserId(String colName, CustomColumn<?, ?>... cols){
			setColName("modify_user_id","`modify_user_id`", colName, "setModifyUserId", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asCreateTime(String colName, CustomColumn<?, ?>... cols){
			setColName("create_time","`create_time`", colName, "setCreateTime", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asModifyTime(String colName, CustomColumn<?, ?>... cols){
			setColName("modify_time","`modify_time`", colName, "setModifyTime", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asStartDate(String colName, CustomColumn<?, ?>... cols){
			setColName("start_date","`start_date`", colName, "setStartDate", "getString", cols);
			return this;
		}
		public SourcePromotionElementSchemeEntityAuxiliary asInvalidDate(String colName, CustomColumn<?, ?>... cols){
			setColName("invalid_date","`invalid_date`", colName, "setInvalidDate", "getString", cols);
			return this;
		}
	}
}
