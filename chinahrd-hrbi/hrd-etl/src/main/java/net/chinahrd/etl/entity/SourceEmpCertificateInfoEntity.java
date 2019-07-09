package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_emp_certificate_info")
public class SourceEmpCertificateInfoEntity implements Entity{

	private String _emp_certificate_info_id;
	private String _emp_id;
	private String _customer_id;
	private String _certificate_id;
	private String _obtain_date;

	public SourceEmpCertificateInfoEntity(){
		super();
	}

	public SourceEmpCertificateInfoEntity(String _emp_certificate_info_id,String _emp_id,String _customer_id,String _certificate_id,String _obtain_date){
		this._emp_certificate_info_id = _emp_certificate_info_id;
		this._emp_id = _emp_id;
		this._customer_id = _customer_id;
		this._certificate_id = _certificate_id;
		this._obtain_date = _obtain_date;
	}

	@Override
	public String toString() {
		return "SourceEmpCertificateInfoEntity ["+
			"	emp_certificate_info_id="+_emp_certificate_info_id+
			"	emp_id="+_emp_id+
			"	customer_id="+_customer_id+
			"	certificate_id="+_certificate_id+
			"	obtain_date="+_obtain_date+
			"]";
	}

	@Column(name = "emp_certificate_info_id",type=ColumnType.VARCHAR)
	public String getEmpCertificateInfoId(){
		return this._emp_certificate_info_id; 
	}

	public void setEmpCertificateInfoId(String _emp_certificate_info_id){
		this._emp_certificate_info_id = _emp_certificate_info_id;
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

	@Column(name = "certificate_id",type=ColumnType.VARCHAR)
	public String getCertificateId(){
		return this._certificate_id; 
	}

	public void setCertificateId(String _certificate_id){
		this._certificate_id = _certificate_id;
	}

	@Column(name = "obtain_date",type=ColumnType.DATE)
	public String getObtainDate(){
		return this._obtain_date; 
	}

	public void setObtainDate(String _obtain_date){
		this._obtain_date = _obtain_date;
	}

	// 实例化内部类
	public static SourceEmpCertificateInfoEntityAuxiliary  getEntityAuxiliary(){
		return new SourceEmpCertificateInfoEntityAuxiliary();
	}

	public static class SourceEmpCertificateInfoEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceEmpCertificateInfoEntityAuxiliary asEmpCertificateInfoId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_certificate_info_id","`emp_certificate_info_id`", colName, "setEmpCertificateInfoId", "getString", cols);
			return this;
		}
		public SourceEmpCertificateInfoEntityAuxiliary asEmpId(String colName, CustomColumn<?, ?>... cols){
			setColName("emp_id","`emp_id`", colName, "setEmpId", "getString", cols);
			return this;
		}
		public SourceEmpCertificateInfoEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceEmpCertificateInfoEntityAuxiliary asCertificateId(String colName, CustomColumn<?, ?>... cols){
			setColName("certificate_id","`certificate_id`", colName, "setCertificateId", "getString", cols);
			return this;
		}
		public SourceEmpCertificateInfoEntityAuxiliary asObtainDate(String colName, CustomColumn<?, ?>... cols){
			setColName("obtain_date","`obtain_date`", colName, "setObtainDate", "getString", cols);
			return this;
		}
	}
}
