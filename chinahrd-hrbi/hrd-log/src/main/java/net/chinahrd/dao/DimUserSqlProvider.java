package net.chinahrd.dao;

import java.util.List;
import java.util.Map;
import net.chinahrd.dto.DimUser;
import net.chinahrd.dto.DimUserExample.Criteria;
import net.chinahrd.dto.DimUserExample.Criterion;
import net.chinahrd.dto.DimUserExample;
import org.apache.ibatis.jdbc.SQL;

public class DimUserSqlProvider {

    public String countByExample(DimUserExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("dim_user");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(DimUserExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("dim_user");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(DimUser record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("dim_user");
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerId() != null) {
            sql.VALUES("customer_id", "#{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getEmpId() != null) {
            sql.VALUES("emp_id", "#{empId,jdbcType=VARCHAR}");
        }
        
        if (record.getUserKey() != null) {
            sql.VALUES("user_key", "#{userKey,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            sql.VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
        }
        
        if (record.getUserNameCh() != null) {
            sql.VALUES("user_name_ch", "#{userNameCh,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.VALUES("email", "#{email,jdbcType=VARCHAR}");
        }
        
        if (record.getNote() != null) {
            sql.VALUES("note", "#{note,jdbcType=VARCHAR}");
        }
        
        if (record.getSysDeploy() != null) {
            sql.VALUES("sys_deploy", "#{sysDeploy,jdbcType=INTEGER}");
        }
        
        if (record.getShowIndex() != null) {
            sql.VALUES("show_index", "#{showIndex,jdbcType=INTEGER}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.VALUES("create_user_id", "#{createUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getModifyUserId() != null) {
            sql.VALUES("modify_user_id", "#{modifyUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsLock() != null) {
            sql.VALUES("is_lock", "#{isLock,jdbcType=BIT}");
        }
        
        return sql.toString();
    }

    public String selectByExample(DimUserExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("user_id");
        } else {
            sql.SELECT("user_id");
        }
        sql.SELECT("customer_id");
        sql.SELECT("emp_id");
        sql.SELECT("user_key");
        sql.SELECT("user_name");
        sql.SELECT("user_name_ch");
        sql.SELECT("password");
        sql.SELECT("email");
        sql.SELECT("note");
        sql.SELECT("sys_deploy");
        sql.SELECT("show_index");
        sql.SELECT("create_user_id");
        sql.SELECT("modify_user_id");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("is_lock");
        sql.FROM("dim_user");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        DimUser record = (DimUser) parameter.get("record");
        DimUserExample example = (DimUserExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("dim_user");
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{record.userId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerId() != null) {
            sql.SET("customer_id = #{record.customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getEmpId() != null) {
            sql.SET("emp_id = #{record.empId,jdbcType=VARCHAR}");
        }
        
        if (record.getUserKey() != null) {
            sql.SET("user_key = #{record.userKey,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            sql.SET("user_name = #{record.userName,jdbcType=VARCHAR}");
        }
        
        if (record.getUserNameCh() != null) {
            sql.SET("user_name_ch = #{record.userNameCh,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        }
        
        if (record.getNote() != null) {
            sql.SET("note = #{record.note,jdbcType=VARCHAR}");
        }
        
        if (record.getSysDeploy() != null) {
            sql.SET("sys_deploy = #{record.sysDeploy,jdbcType=INTEGER}");
        }
        
        if (record.getShowIndex() != null) {
            sql.SET("show_index = #{record.showIndex,jdbcType=INTEGER}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.SET("create_user_id = #{record.createUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getModifyUserId() != null) {
            sql.SET("modify_user_id = #{record.modifyUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsLock() != null) {
            sql.SET("is_lock = #{record.isLock,jdbcType=BIT}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("dim_user");
        
        sql.SET("user_id = #{record.userId,jdbcType=VARCHAR}");
        sql.SET("customer_id = #{record.customerId,jdbcType=VARCHAR}");
        sql.SET("emp_id = #{record.empId,jdbcType=VARCHAR}");
        sql.SET("user_key = #{record.userKey,jdbcType=VARCHAR}");
        sql.SET("user_name = #{record.userName,jdbcType=VARCHAR}");
        sql.SET("user_name_ch = #{record.userNameCh,jdbcType=VARCHAR}");
        sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        sql.SET("note = #{record.note,jdbcType=VARCHAR}");
        sql.SET("sys_deploy = #{record.sysDeploy,jdbcType=INTEGER}");
        sql.SET("show_index = #{record.showIndex,jdbcType=INTEGER}");
        sql.SET("create_user_id = #{record.createUserId,jdbcType=VARCHAR}");
        sql.SET("modify_user_id = #{record.modifyUserId,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("is_lock = #{record.isLock,jdbcType=BIT}");
        
        DimUserExample example = (DimUserExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DimUser record) {
        SQL sql = new SQL();
        sql.UPDATE("dim_user");
        
        if (record.getCustomerId() != null) {
            sql.SET("customer_id = #{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getEmpId() != null) {
            sql.SET("emp_id = #{empId,jdbcType=VARCHAR}");
        }
        
        if (record.getUserKey() != null) {
            sql.SET("user_key = #{userKey,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
        }
        
        if (record.getUserNameCh() != null) {
            sql.SET("user_name_ch = #{userNameCh,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{email,jdbcType=VARCHAR}");
        }
        
        if (record.getNote() != null) {
            sql.SET("note = #{note,jdbcType=VARCHAR}");
        }
        
        if (record.getSysDeploy() != null) {
            sql.SET("sys_deploy = #{sysDeploy,jdbcType=INTEGER}");
        }
        
        if (record.getShowIndex() != null) {
            sql.SET("show_index = #{showIndex,jdbcType=INTEGER}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.SET("create_user_id = #{createUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getModifyUserId() != null) {
            sql.SET("modify_user_id = #{modifyUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsLock() != null) {
            sql.SET("is_lock = #{isLock,jdbcType=BIT}");
        }
        
        sql.WHERE("user_id = #{userId,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, DimUserExample example, boolean includeExamplePhrase) {
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