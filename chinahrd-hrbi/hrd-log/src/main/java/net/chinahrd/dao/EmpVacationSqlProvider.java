package net.chinahrd.dao;

import java.util.List;
import java.util.Map;
import net.chinahrd.dto.EmpVacation;
import net.chinahrd.dto.EmpVacationExample.Criteria;
import net.chinahrd.dto.EmpVacationExample.Criterion;
import net.chinahrd.dto.EmpVacationExample;
import org.apache.ibatis.jdbc.SQL;

public class EmpVacationSqlProvider {

    public String countByExample(EmpVacationExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("emp_vacation");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(EmpVacationExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("emp_vacation");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(EmpVacation record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("emp_vacation");
        
        if (record.getEmpId() != null) {
            sql.VALUES("emp_id", "#{empId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerId() != null) {
            sql.VALUES("customer_id", "#{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getEmpKey() != null) {
            sql.VALUES("emp_key", "#{empKey,jdbcType=VARCHAR}");
        }
        
        if (record.getUserNameCh() != null) {
            sql.VALUES("user_name_ch", "#{userNameCh,jdbcType=VARCHAR}");
        }
        
        if (record.getAnnualTotal() != null) {
            sql.VALUES("annual_total", "#{annualTotal,jdbcType=VARCHAR}");
        }
        
        if (record.getAnnual() != null) {
            sql.VALUES("annual", "#{annual,jdbcType=VARCHAR}");
        }
        
        if (record.getCanLeave() != null) {
            sql.VALUES("can_leave", "#{canLeave,jdbcType=VARCHAR}");
        }
        
        if (record.getHistoryHour() != null) {
            sql.VALUES("history_hour", "#{historyHour,jdbcType=VARCHAR}");
        }
        
        if (record.getRefresh() != null) {
            sql.VALUES("refresh", "#{refresh,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(EmpVacationExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("emp_id");
        } else {
            sql.SELECT("emp_id");
        }
        sql.SELECT("customer_id");
        sql.SELECT("emp_key");
        sql.SELECT("user_name_ch");
        sql.SELECT("annual_total");
        sql.SELECT("annual");
        sql.SELECT("can_leave");
        sql.SELECT("history_hour");
        sql.SELECT("refresh");
        sql.FROM("emp_vacation");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        EmpVacation record = (EmpVacation) parameter.get("record");
        EmpVacationExample example = (EmpVacationExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("emp_vacation");
        
        if (record.getEmpId() != null) {
            sql.SET("emp_id = #{record.empId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerId() != null) {
            sql.SET("customer_id = #{record.customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getEmpKey() != null) {
            sql.SET("emp_key = #{record.empKey,jdbcType=VARCHAR}");
        }
        
        if (record.getUserNameCh() != null) {
            sql.SET("user_name_ch = #{record.userNameCh,jdbcType=VARCHAR}");
        }
        
        if (record.getAnnualTotal() != null) {
            sql.SET("annual_total = #{record.annualTotal,jdbcType=VARCHAR}");
        }
        
        if (record.getAnnual() != null) {
            sql.SET("annual = #{record.annual,jdbcType=VARCHAR}");
        }
        
        if (record.getCanLeave() != null) {
            sql.SET("can_leave = #{record.canLeave,jdbcType=VARCHAR}");
        }
        
        if (record.getHistoryHour() != null) {
            sql.SET("history_hour = #{record.historyHour,jdbcType=VARCHAR}");
        }
        
        if (record.getRefresh() != null) {
            sql.SET("refresh = #{record.refresh,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("emp_vacation");
        
        sql.SET("emp_id = #{record.empId,jdbcType=VARCHAR}");
        sql.SET("customer_id = #{record.customerId,jdbcType=VARCHAR}");
        sql.SET("emp_key = #{record.empKey,jdbcType=VARCHAR}");
        sql.SET("user_name_ch = #{record.userNameCh,jdbcType=VARCHAR}");
        sql.SET("annual_total = #{record.annualTotal,jdbcType=VARCHAR}");
        sql.SET("annual = #{record.annual,jdbcType=VARCHAR}");
        sql.SET("can_leave = #{record.canLeave,jdbcType=VARCHAR}");
        sql.SET("history_hour = #{record.historyHour,jdbcType=VARCHAR}");
        sql.SET("refresh = #{record.refresh,jdbcType=TIMESTAMP}");
        
        EmpVacationExample example = (EmpVacationExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    protected void applyWhere(SQL sql, EmpVacationExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}