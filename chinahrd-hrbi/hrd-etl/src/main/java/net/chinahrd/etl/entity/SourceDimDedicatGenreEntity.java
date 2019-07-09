package net.chinahrd.etl.entity;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.Column;
import net.chinahrd.etl.Entity;
import net.chinahrd.etl.sql.AbstractSqlAuxiliary;
import net.chinahrd.etl.sql.CustomColumn;

@Table(name = "source_dim_dedicat_genre")
public class SourceDimDedicatGenreEntity implements Entity{

	private String _dedicat_genre_id;
	private String _customer_id;
	private String _dedicat_name;
	private String _dedicat_genre_parent_id;
	private int _is_children;
	private String _create_date;
	private int _show_index;

	public SourceDimDedicatGenreEntity(){
		super();
	}

	public SourceDimDedicatGenreEntity(String _dedicat_genre_id,String _customer_id,String _dedicat_name,String _dedicat_genre_parent_id,int _is_children,String _create_date,int _show_index){
		this._dedicat_genre_id = _dedicat_genre_id;
		this._customer_id = _customer_id;
		this._dedicat_name = _dedicat_name;
		this._dedicat_genre_parent_id = _dedicat_genre_parent_id;
		this._is_children = _is_children;
		this._create_date = _create_date;
		this._show_index = _show_index;
	}

	@Override
	public String toString() {
		return "SourceDimDedicatGenreEntity ["+
			"	dedicat_genre_id="+_dedicat_genre_id+
			"	customer_id="+_customer_id+
			"	dedicat_name="+_dedicat_name+
			"	dedicat_genre_parent_id="+_dedicat_genre_parent_id+
			"	is_children="+_is_children+
			"	create_date="+_create_date+
			"	show_index="+_show_index+
			"]";
	}

	@Column(name = "dedicat_genre_id",type=ColumnType.VARCHAR)
	public String getDedicatGenreId(){
		return this._dedicat_genre_id; 
	}

	public void setDedicatGenreId(String _dedicat_genre_id){
		this._dedicat_genre_id = _dedicat_genre_id;
	}

	@Column(name = "customer_id",type=ColumnType.VARCHAR)
	public String getCustomerId(){
		return this._customer_id; 
	}

	public void setCustomerId(String _customer_id){
		this._customer_id = _customer_id;
	}

	@Column(name = "dedicat_name",type=ColumnType.VARCHAR)
	public String getDedicatName(){
		return this._dedicat_name; 
	}

	public void setDedicatName(String _dedicat_name){
		this._dedicat_name = _dedicat_name;
	}

	@Column(name = "dedicat_genre_parent_id",type=ColumnType.VARCHAR)
	public String getDedicatGenreParentId(){
		return this._dedicat_genre_parent_id; 
	}

	public void setDedicatGenreParentId(String _dedicat_genre_parent_id){
		this._dedicat_genre_parent_id = _dedicat_genre_parent_id;
	}

	@Column(name = "is_children",type=ColumnType.TINYINT)
	public int getIsChildren(){
		return this._is_children; 
	}

	public void setIsChildren(int _is_children){
		this._is_children = _is_children;
	}

	@Column(name = "create_date",type=ColumnType.DATE)
	public String getCreateDate(){
		return this._create_date; 
	}

	public void setCreateDate(String _create_date){
		this._create_date = _create_date;
	}

	@Column(name = "show_index",type=ColumnType.INT)
	public int getShowIndex(){
		return this._show_index; 
	}

	public void setShowIndex(int _show_index){
		this._show_index = _show_index;
	}

	// 实例化内部类
	public static SourceDimDedicatGenreEntityAuxiliary  getEntityAuxiliary(){
		return new SourceDimDedicatGenreEntityAuxiliary();
	}

	public static class SourceDimDedicatGenreEntityAuxiliary extends AbstractSqlAuxiliary{
		public SourceDimDedicatGenreEntityAuxiliary asDedicatGenreId(String colName, CustomColumn<?, ?>... cols){
			setColName("dedicat_genre_id","`dedicat_genre_id`", colName, "setDedicatGenreId", "getString", cols);
			return this;
		}
		public SourceDimDedicatGenreEntityAuxiliary asCustomerId(String colName, CustomColumn<?, ?>... cols){
			setColName("customer_id","`customer_id`", colName, "setCustomerId", "getString", cols);
			return this;
		}
		public SourceDimDedicatGenreEntityAuxiliary asDedicatName(String colName, CustomColumn<?, ?>... cols){
			setColName("dedicat_name","`dedicat_name`", colName, "setDedicatName", "getString", cols);
			return this;
		}
		public SourceDimDedicatGenreEntityAuxiliary asDedicatGenreParentId(String colName, CustomColumn<?, ?>... cols){
			setColName("dedicat_genre_parent_id","`dedicat_genre_parent_id`", colName, "setDedicatGenreParentId", "getString", cols);
			return this;
		}
		public SourceDimDedicatGenreEntityAuxiliary asIsChildren(String colName, CustomColumn<?, ?>... cols){
			setColName("is_children","`is_children`", colName, "setIsChildren", "getInt", cols);
			return this;
		}
		public SourceDimDedicatGenreEntityAuxiliary asCreateDate(String colName, CustomColumn<?, ?>... cols){
			setColName("create_date","`create_date`", colName, "setCreateDate", "getString", cols);
			return this;
		}
		public SourceDimDedicatGenreEntityAuxiliary asShowIndex(String colName, CustomColumn<?, ?>... cols){
			setColName("show_index","`show_index`", colName, "setShowIndex", "getInt", cols);
			return this;
		}
	}
}
