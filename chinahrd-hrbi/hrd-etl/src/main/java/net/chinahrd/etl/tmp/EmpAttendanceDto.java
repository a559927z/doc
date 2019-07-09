package net.chinahrd.etl.tmp;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;

@Table(name = "source_emp_attendance")
public class EmpAttendanceDto implements Entity {
	private String _CardID;

	private String _EmpIName;

	private String _RecDate;

	private String _RecTime;

	private String _refresh_date;

	private String emp_id;
	private String emp_key;
	private String user_name_ch;
	private String organization_id;

	public EmpAttendanceDto(String emp_id, String emp_key, String user_name_ch, String organization_id) {
		super();
		this.emp_id = emp_id;
		this.emp_key = emp_key;
		this.user_name_ch = user_name_ch;
		this.organization_id = organization_id;
	}

	public EmpAttendanceDto() {

	}

	public EmpAttendanceDto(String _CardID, String _EmpIName, String _RecDate, String _RecTime, String _refresh_date) {
		this._CardID = _CardID;
		this._EmpIName = _EmpIName;
		this._RecDate = _RecDate;
		this._RecTime = _RecTime;
		this._refresh_date = _refresh_date;
	}

	public String getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}

	public String getUser_name_ch() {
		return user_name_ch;
	}

	public void setUser_name_ch(String user_name_ch) {
		this.user_name_ch = user_name_ch;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_key() {
		return emp_key;
	}

	public void setEmp_key(String emp_key) {
		this.emp_key = emp_key;
	}

	@Column(name = "CardID", type = ColumnType.CHAR)
	public String getCardID() {
		return this._CardID;
	}

	public void setCardID(String _CardID) {
		this._CardID = _CardID;
	}

	@Column(name = "EmpIName", type = ColumnType.VARCHAR)
	public String getEmpIName() {
		return this._EmpIName;
	}

	public void setEmpIName(String _EmpIName) {
		this._EmpIName = _EmpIName;
	}

	@Column(name = "RecDate", type = ColumnType.CHAR)
	public String getRecDate() {
		return this._RecDate;
	}

	public void setRecDate(String _RecDate) {
		this._RecDate = _RecDate;
	}

	@Column(name = "RecTime", type = ColumnType.CHAR)
	public String getRecTime() {
		return this._RecTime;
	}

	public void setRecTime(String _RecTime) {
		this._RecTime = _RecTime;
	}

	@Column(name = "refresh_date", type = ColumnType.DATETIME)
	public String getRefreshDate() {
		return this._refresh_date;
	}

	public void setRefreshDate(String _refresh_date) {
		this._refresh_date = _refresh_date;
	}

}
