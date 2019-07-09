package net.chinahrd.dao;

import java.util.Map;

public class LogProvider {

    public String countAllLogSQL() {
        String sql = "SELECT count(1) FROM 	operate_log t";
        return sql;
    }
}
