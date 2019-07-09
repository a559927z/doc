package net.chinahrd.pagination;


import java.io.Serializable;
import java.util.List;

import net.chinahrd.utils.ArithUtil;

/**
 * jqGrid组件DTO
 * Created by wqcai on 15/6/9.
 */
public class PaginationDto<E> implements Serializable {

    private static final long serialVersionUID = -8103216884411929434L;

    private int page;       //页面
    private int row;        //显示条数
    private int total = 1;      //总页数
    private int records;    //总条数
    private List<E> rows;
    private E userdata;
    

    /**
     * sql用
     **/
    private int offset;
    private int limit;

    public PaginationDto() {
    }

    public PaginationDto(int page, int row) {
        this.page = page <= 0 ? 1 : page;
        this.row = row <= 0 ? 1 : row;
        this.limit = row < 0 ? 0 : row;
        this.offset = page <= 0 ? 1 : (int) ArithUtil.mul(limit, page - 1);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return limit != 0 ? (records % limit == 0 ? records / limit : records / limit + 1) : total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public E getUserdata() {
        return userdata;
    }

    public void setUserdata(E userdata) {
        this.userdata = userdata;
    }
}
