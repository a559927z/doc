package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_certificate_info")
public class SourceDimCertificateInfoEntity implements Entity{

	private String _certificate_info_id;
	private String _certificate_name;
	private String _customer_id;
	private String _certificate_type_id;
	private int _curt_name;
	private int _show_index;
	private String _refresh;

	public SourceDimCertificateInfoEntity(){
		super();
	}

	public SourceDimCertificateInfoEntity(String _certificate_info_id,String _certificate_name,String _customer_id,String _certificate_type_id,int _curt_name,int _show_index,String _refresh){
		this._certificate_info_id = _certificate_info_id;
		this._certificate_name = _certificate_name;
		this._customer_id = _customer_id;
		this._certificate_type_id = _certificate_type_id;
		this._curt_name = _curt_name;
		this._show_index = _show_index;
		this._refresh = _refresh;
	}

	@Override
	public String toString() {
		return "SourceDimCertificateInfoEntity ["+
			"	certificate_info_id="+_certificate_info_id+
			"	certificate_name="+_certificate_name+
			"	customer_id="+_customer_id+
			"	certificate_type_id="+_certificate_type_id+
			"	curt_name="+_curt_name+
			"	show_index="+_show_index+
			"	refresh="+_refresh+
			"]";
	}

	@Column(name = "certificate_info_id",type=ColumnType.VARCHAR)
	public String getCertificateInfoId(){
		return this._certificate_info_id; 
	}

	public void setCertificateInfoId(String _certificate_info_id){
		this._certificate_info_id = _certificate_info_id;
	}

	@Column(name = "certificate_name",type=ColumnType.VARCHAR)
	public String getCertificateName(){
		return this._certificate_name; 
	}

	public void setCertificateName(String _certificate_name){
		this._certificate_name = _certificate_name;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "certificate_type_id",type=ColumnType.VARCHAR)
	public String getCertificateTypeId(){
		return this._certificate_type_id; 
	}

	public void setCertificateTypeId(String _certificate_type_id){
		this._certificate_type_id = _certificate_type_id;
	}

	@Column(name = "curt_name",type=ColumnType.TINYINT)
	public int getCurtName(){
		return this._curt_name; 
	}

	public void setCurtName(int _curt_name){
		this._curt_name = _curt_name;
	}

	@Column(name = "show_index",type=ColumnType.TINYINT)
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
	public static SourceDimCertificateInfoEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimCertificateInfoEntityAuxiliary();
	}

	public static class SourceDimCertificateInfoEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimCertificateInfoEntityAuxiliary asCertificateInfoId(String colName, CustomColumn<?, ?>... cols){
			setColName("certificate_info_id","`certificate_info_id`", colName, "setCertificateInfoId", "getString", cols);
			return this;
		}
		public SourceDimCertificateInfoEntityAuxiliary asCertificateName(String colName, CustomColumn<?, ?>... cols){
			setColName("certificate_name","`certificate_name`", colName, "setCertificateName", "getString", cols);
			return this;
		}
		public SourceDimCertificateInfoEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimCertificateInfoEntityAuxiliary asCertificateTypeId(String colName, CustomColumn<?, ?>... cols){
			setColName("certificate_type_id","`certificate_type_id`", colName, "setCertificateTypeId", "getString", cols);
			return this;
		}
		public SourceDimCertificateInfoEntityAuxiliary asCurtName(String colName, CustomColumn<?, ?>... cols){
			setColName("curt_name","`curt_name`", colName, "setCurtName", "getInt", cols);
			return this;
		}
		public SourceDimCertificateInfoEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
		public SourceDimCertificateInfoEntityAuxiliary asRefresh(String colName, CustomColumn<?, ?>... cols){
			setColName("refresh","`refresh`", colName, "setRefresh", "getString", cols);
			return this;
		}
	}
}
