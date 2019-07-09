package net.chinahrd.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VDimEmpExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VDimEmpExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andEmpIdIsNull() {
            addCriterion("emp_id is null");
            return (Criteria) this;
        }

        public Criteria andEmpIdIsNotNull() {
            addCriterion("emp_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmpIdEqualTo(String value) {
            addCriterion("emp_id =", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotEqualTo(String value) {
            addCriterion("emp_id <>", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdGreaterThan(String value) {
            addCriterion("emp_id >", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdGreaterThanOrEqualTo(String value) {
            addCriterion("emp_id >=", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdLessThan(String value) {
            addCriterion("emp_id <", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdLessThanOrEqualTo(String value) {
            addCriterion("emp_id <=", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdLike(String value) {
            addCriterion("emp_id like", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotLike(String value) {
            addCriterion("emp_id not like", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdIn(List<String> values) {
            addCriterion("emp_id in", values, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotIn(List<String> values) {
            addCriterion("emp_id not in", values, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdBetween(String value1, String value2) {
            addCriterion("emp_id between", value1, value2, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotBetween(String value1, String value2) {
            addCriterion("emp_id not between", value1, value2, "empId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(String value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(String value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(String value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(String value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLike(String value) {
            addCriterion("customer_id like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotLike(String value) {
            addCriterion("customer_id not like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<String> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<String> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(String value1, String value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(String value1, String value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andEmpKeyIsNull() {
            addCriterion("emp_key is null");
            return (Criteria) this;
        }

        public Criteria andEmpKeyIsNotNull() {
            addCriterion("emp_key is not null");
            return (Criteria) this;
        }

        public Criteria andEmpKeyEqualTo(String value) {
            addCriterion("emp_key =", value, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyNotEqualTo(String value) {
            addCriterion("emp_key <>", value, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyGreaterThan(String value) {
            addCriterion("emp_key >", value, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyGreaterThanOrEqualTo(String value) {
            addCriterion("emp_key >=", value, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyLessThan(String value) {
            addCriterion("emp_key <", value, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyLessThanOrEqualTo(String value) {
            addCriterion("emp_key <=", value, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyLike(String value) {
            addCriterion("emp_key like", value, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyNotLike(String value) {
            addCriterion("emp_key not like", value, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyIn(List<String> values) {
            addCriterion("emp_key in", values, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyNotIn(List<String> values) {
            addCriterion("emp_key not in", values, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyBetween(String value1, String value2) {
            addCriterion("emp_key between", value1, value2, "empKey");
            return (Criteria) this;
        }

        public Criteria andEmpKeyNotBetween(String value1, String value2) {
            addCriterion("emp_key not between", value1, value2, "empKey");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameChIsNull() {
            addCriterion("user_name_ch is null");
            return (Criteria) this;
        }

        public Criteria andUserNameChIsNotNull() {
            addCriterion("user_name_ch is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameChEqualTo(String value) {
            addCriterion("user_name_ch =", value, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChNotEqualTo(String value) {
            addCriterion("user_name_ch <>", value, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChGreaterThan(String value) {
            addCriterion("user_name_ch >", value, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChGreaterThanOrEqualTo(String value) {
            addCriterion("user_name_ch >=", value, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChLessThan(String value) {
            addCriterion("user_name_ch <", value, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChLessThanOrEqualTo(String value) {
            addCriterion("user_name_ch <=", value, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChLike(String value) {
            addCriterion("user_name_ch like", value, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChNotLike(String value) {
            addCriterion("user_name_ch not like", value, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChIn(List<String> values) {
            addCriterion("user_name_ch in", values, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChNotIn(List<String> values) {
            addCriterion("user_name_ch not in", values, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChBetween(String value1, String value2) {
            addCriterion("user_name_ch between", value1, value2, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andUserNameChNotBetween(String value1, String value2) {
            addCriterion("user_name_ch not between", value1, value2, "userNameCh");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdIsNull() {
            addCriterion("emp_hf_id is null");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdIsNotNull() {
            addCriterion("emp_hf_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdEqualTo(String value) {
            addCriterion("emp_hf_id =", value, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdNotEqualTo(String value) {
            addCriterion("emp_hf_id <>", value, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdGreaterThan(String value) {
            addCriterion("emp_hf_id >", value, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdGreaterThanOrEqualTo(String value) {
            addCriterion("emp_hf_id >=", value, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdLessThan(String value) {
            addCriterion("emp_hf_id <", value, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdLessThanOrEqualTo(String value) {
            addCriterion("emp_hf_id <=", value, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdLike(String value) {
            addCriterion("emp_hf_id like", value, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdNotLike(String value) {
            addCriterion("emp_hf_id not like", value, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdIn(List<String> values) {
            addCriterion("emp_hf_id in", values, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdNotIn(List<String> values) {
            addCriterion("emp_hf_id not in", values, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdBetween(String value1, String value2) {
            addCriterion("emp_hf_id between", value1, value2, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfIdNotBetween(String value1, String value2) {
            addCriterion("emp_hf_id not between", value1, value2, "empHfId");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyIsNull() {
            addCriterion("emp_hf_key is null");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyIsNotNull() {
            addCriterion("emp_hf_key is not null");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyEqualTo(String value) {
            addCriterion("emp_hf_key =", value, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyNotEqualTo(String value) {
            addCriterion("emp_hf_key <>", value, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyGreaterThan(String value) {
            addCriterion("emp_hf_key >", value, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyGreaterThanOrEqualTo(String value) {
            addCriterion("emp_hf_key >=", value, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyLessThan(String value) {
            addCriterion("emp_hf_key <", value, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyLessThanOrEqualTo(String value) {
            addCriterion("emp_hf_key <=", value, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyLike(String value) {
            addCriterion("emp_hf_key like", value, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyNotLike(String value) {
            addCriterion("emp_hf_key not like", value, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyIn(List<String> values) {
            addCriterion("emp_hf_key in", values, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyNotIn(List<String> values) {
            addCriterion("emp_hf_key not in", values, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyBetween(String value1, String value2) {
            addCriterion("emp_hf_key between", value1, value2, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andEmpHfKeyNotBetween(String value1, String value2) {
            addCriterion("emp_hf_key not between", value1, value2, "empHfKey");
            return (Criteria) this;
        }

        public Criteria andReportRelationIsNull() {
            addCriterion("report_relation is null");
            return (Criteria) this;
        }

        public Criteria andReportRelationIsNotNull() {
            addCriterion("report_relation is not null");
            return (Criteria) this;
        }

        public Criteria andReportRelationEqualTo(String value) {
            addCriterion("report_relation =", value, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationNotEqualTo(String value) {
            addCriterion("report_relation <>", value, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationGreaterThan(String value) {
            addCriterion("report_relation >", value, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationGreaterThanOrEqualTo(String value) {
            addCriterion("report_relation >=", value, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationLessThan(String value) {
            addCriterion("report_relation <", value, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationLessThanOrEqualTo(String value) {
            addCriterion("report_relation <=", value, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationLike(String value) {
            addCriterion("report_relation like", value, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationNotLike(String value) {
            addCriterion("report_relation not like", value, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationIn(List<String> values) {
            addCriterion("report_relation in", values, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationNotIn(List<String> values) {
            addCriterion("report_relation not in", values, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationBetween(String value1, String value2) {
            addCriterion("report_relation between", value1, value2, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andReportRelationNotBetween(String value1, String value2) {
            addCriterion("report_relation not between", value1, value2, "reportRelation");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andImgPathIsNull() {
            addCriterion("img_path is null");
            return (Criteria) this;
        }

        public Criteria andImgPathIsNotNull() {
            addCriterion("img_path is not null");
            return (Criteria) this;
        }

        public Criteria andImgPathEqualTo(String value) {
            addCriterion("img_path =", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathNotEqualTo(String value) {
            addCriterion("img_path <>", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathGreaterThan(String value) {
            addCriterion("img_path >", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathGreaterThanOrEqualTo(String value) {
            addCriterion("img_path >=", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathLessThan(String value) {
            addCriterion("img_path <", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathLessThanOrEqualTo(String value) {
            addCriterion("img_path <=", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathLike(String value) {
            addCriterion("img_path like", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathNotLike(String value) {
            addCriterion("img_path not like", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathIn(List<String> values) {
            addCriterion("img_path in", values, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathNotIn(List<String> values) {
            addCriterion("img_path not in", values, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathBetween(String value1, String value2) {
            addCriterion("img_path between", value1, value2, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathNotBetween(String value1, String value2) {
            addCriterion("img_path not between", value1, value2, "imgPath");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeIsNull() {
            addCriterion("passtime_or_fulltime is null");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeIsNotNull() {
            addCriterion("passtime_or_fulltime is not null");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeEqualTo(String value) {
            addCriterion("passtime_or_fulltime =", value, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeNotEqualTo(String value) {
            addCriterion("passtime_or_fulltime <>", value, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeGreaterThan(String value) {
            addCriterion("passtime_or_fulltime >", value, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeGreaterThanOrEqualTo(String value) {
            addCriterion("passtime_or_fulltime >=", value, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeLessThan(String value) {
            addCriterion("passtime_or_fulltime <", value, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeLessThanOrEqualTo(String value) {
            addCriterion("passtime_or_fulltime <=", value, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeLike(String value) {
            addCriterion("passtime_or_fulltime like", value, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeNotLike(String value) {
            addCriterion("passtime_or_fulltime not like", value, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeIn(List<String> values) {
            addCriterion("passtime_or_fulltime in", values, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeNotIn(List<String> values) {
            addCriterion("passtime_or_fulltime not in", values, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeBetween(String value1, String value2) {
            addCriterion("passtime_or_fulltime between", value1, value2, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andPasstimeOrFulltimeNotBetween(String value1, String value2) {
            addCriterion("passtime_or_fulltime not between", value1, value2, "passtimeOrFulltime");
            return (Criteria) this;
        }

        public Criteria andContractIsNull() {
            addCriterion("contract is null");
            return (Criteria) this;
        }

        public Criteria andContractIsNotNull() {
            addCriterion("contract is not null");
            return (Criteria) this;
        }

        public Criteria andContractEqualTo(String value) {
            addCriterion("contract =", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractNotEqualTo(String value) {
            addCriterion("contract <>", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractGreaterThan(String value) {
            addCriterion("contract >", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractGreaterThanOrEqualTo(String value) {
            addCriterion("contract >=", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractLessThan(String value) {
            addCriterion("contract <", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractLessThanOrEqualTo(String value) {
            addCriterion("contract <=", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractLike(String value) {
            addCriterion("contract like", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractNotLike(String value) {
            addCriterion("contract not like", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractIn(List<String> values) {
            addCriterion("contract in", values, "contract");
            return (Criteria) this;
        }

        public Criteria andContractNotIn(List<String> values) {
            addCriterion("contract not in", values, "contract");
            return (Criteria) this;
        }

        public Criteria andContractBetween(String value1, String value2) {
            addCriterion("contract between", value1, value2, "contract");
            return (Criteria) this;
        }

        public Criteria andContractNotBetween(String value1, String value2) {
            addCriterion("contract not between", value1, value2, "contract");
            return (Criteria) this;
        }

        public Criteria andBloodIsNull() {
            addCriterion("blood is null");
            return (Criteria) this;
        }

        public Criteria andBloodIsNotNull() {
            addCriterion("blood is not null");
            return (Criteria) this;
        }

        public Criteria andBloodEqualTo(String value) {
            addCriterion("blood =", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodNotEqualTo(String value) {
            addCriterion("blood <>", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodGreaterThan(String value) {
            addCriterion("blood >", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodGreaterThanOrEqualTo(String value) {
            addCriterion("blood >=", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodLessThan(String value) {
            addCriterion("blood <", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodLessThanOrEqualTo(String value) {
            addCriterion("blood <=", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodLike(String value) {
            addCriterion("blood like", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodNotLike(String value) {
            addCriterion("blood not like", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodIn(List<String> values) {
            addCriterion("blood in", values, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodNotIn(List<String> values) {
            addCriterion("blood not in", values, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodBetween(String value1, String value2) {
            addCriterion("blood between", value1, value2, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodNotBetween(String value1, String value2) {
            addCriterion("blood not between", value1, value2, "blood");
            return (Criteria) this;
        }

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Double value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Double value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Double value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Double value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Double value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Double value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Double> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Double> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Double value1, Double value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Double value1, Double value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeIsNull() {
            addCriterion("company_age is null");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeIsNotNull() {
            addCriterion("company_age is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeEqualTo(Double value) {
            addCriterion("company_age =", value, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeNotEqualTo(Double value) {
            addCriterion("company_age <>", value, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeGreaterThan(Double value) {
            addCriterion("company_age >", value, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeGreaterThanOrEqualTo(Double value) {
            addCriterion("company_age >=", value, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeLessThan(Double value) {
            addCriterion("company_age <", value, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeLessThanOrEqualTo(Double value) {
            addCriterion("company_age <=", value, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeIn(List<Double> values) {
            addCriterion("company_age in", values, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeNotIn(List<Double> values) {
            addCriterion("company_age not in", values, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeBetween(Double value1, Double value2) {
            addCriterion("company_age between", value1, value2, "companyAge");
            return (Criteria) this;
        }

        public Criteria andCompanyAgeNotBetween(Double value1, Double value2) {
            addCriterion("company_age not between", value1, value2, "companyAge");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentIsNull() {
            addCriterion("is_key_talent is null");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentIsNotNull() {
            addCriterion("is_key_talent is not null");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentEqualTo(Boolean value) {
            addCriterion("is_key_talent =", value, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentNotEqualTo(Boolean value) {
            addCriterion("is_key_talent <>", value, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentGreaterThan(Boolean value) {
            addCriterion("is_key_talent >", value, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_key_talent >=", value, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentLessThan(Boolean value) {
            addCriterion("is_key_talent <", value, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentLessThanOrEqualTo(Boolean value) {
            addCriterion("is_key_talent <=", value, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentIn(List<Boolean> values) {
            addCriterion("is_key_talent in", values, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentNotIn(List<Boolean> values) {
            addCriterion("is_key_talent not in", values, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentBetween(Boolean value1, Boolean value2) {
            addCriterion("is_key_talent between", value1, value2, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andIsKeyTalentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_key_talent not between", value1, value2, "isKeyTalent");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andDegreeIdIsNull() {
            addCriterion("degree_id is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIdIsNotNull() {
            addCriterion("degree_id is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeIdEqualTo(String value) {
            addCriterion("degree_id =", value, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdNotEqualTo(String value) {
            addCriterion("degree_id <>", value, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdGreaterThan(String value) {
            addCriterion("degree_id >", value, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdGreaterThanOrEqualTo(String value) {
            addCriterion("degree_id >=", value, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdLessThan(String value) {
            addCriterion("degree_id <", value, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdLessThanOrEqualTo(String value) {
            addCriterion("degree_id <=", value, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdLike(String value) {
            addCriterion("degree_id like", value, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdNotLike(String value) {
            addCriterion("degree_id not like", value, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdIn(List<String> values) {
            addCriterion("degree_id in", values, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdNotIn(List<String> values) {
            addCriterion("degree_id not in", values, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdBetween(String value1, String value2) {
            addCriterion("degree_id between", value1, value2, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIdNotBetween(String value1, String value2) {
            addCriterion("degree_id not between", value1, value2, "degreeId");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("degree is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("degree is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("degree =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("degree <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("degree >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("degree >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("degree <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("degree <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("degree like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("degree not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("degree in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("degree not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("degree between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("degree not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNull() {
            addCriterion("native_place is null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNotNull() {
            addCriterion("native_place is not null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceEqualTo(String value) {
            addCriterion("native_place =", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotEqualTo(String value) {
            addCriterion("native_place <>", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThan(String value) {
            addCriterion("native_place >", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("native_place >=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThan(String value) {
            addCriterion("native_place <", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThanOrEqualTo(String value) {
            addCriterion("native_place <=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLike(String value) {
            addCriterion("native_place like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotLike(String value) {
            addCriterion("native_place not like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIn(List<String> values) {
            addCriterion("native_place in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotIn(List<String> values) {
            addCriterion("native_place not in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceBetween(String value1, String value2) {
            addCriterion("native_place between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotBetween(String value1, String value2) {
            addCriterion("native_place not between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andCountryIsNull() {
            addCriterion("country is null");
            return (Criteria) this;
        }

        public Criteria andCountryIsNotNull() {
            addCriterion("country is not null");
            return (Criteria) this;
        }

        public Criteria andCountryEqualTo(String value) {
            addCriterion("country =", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotEqualTo(String value) {
            addCriterion("country <>", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThan(String value) {
            addCriterion("country >", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThanOrEqualTo(String value) {
            addCriterion("country >=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThan(String value) {
            addCriterion("country <", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThanOrEqualTo(String value) {
            addCriterion("country <=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLike(String value) {
            addCriterion("country like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotLike(String value) {
            addCriterion("country not like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryIn(List<String> values) {
            addCriterion("country in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotIn(List<String> values) {
            addCriterion("country not in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryBetween(String value1, String value2) {
            addCriterion("country between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotBetween(String value1, String value2) {
            addCriterion("country not between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceIsNull() {
            addCriterion("birth_place is null");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceIsNotNull() {
            addCriterion("birth_place is not null");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceEqualTo(String value) {
            addCriterion("birth_place =", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceNotEqualTo(String value) {
            addCriterion("birth_place <>", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceGreaterThan(String value) {
            addCriterion("birth_place >", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("birth_place >=", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceLessThan(String value) {
            addCriterion("birth_place <", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceLessThanOrEqualTo(String value) {
            addCriterion("birth_place <=", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceLike(String value) {
            addCriterion("birth_place like", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceNotLike(String value) {
            addCriterion("birth_place not like", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceIn(List<String> values) {
            addCriterion("birth_place in", values, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceNotIn(List<String> values) {
            addCriterion("birth_place not in", values, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceBetween(String value1, String value2) {
            addCriterion("birth_place between", value1, value2, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceNotBetween(String value1, String value2) {
            addCriterion("birth_place not between", value1, value2, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthDateIsNull() {
            addCriterion("birth_date is null");
            return (Criteria) this;
        }

        public Criteria andBirthDateIsNotNull() {
            addCriterion("birth_date is not null");
            return (Criteria) this;
        }

        public Criteria andBirthDateEqualTo(Date value) {
            addCriterionForJDBCDate("birth_date =", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("birth_date <>", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateGreaterThan(Date value) {
            addCriterionForJDBCDate("birth_date >", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth_date >=", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateLessThan(Date value) {
            addCriterionForJDBCDate("birth_date <", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth_date <=", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateIn(List<Date> values) {
            addCriterionForJDBCDate("birth_date in", values, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("birth_date not in", values, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth_date between", value1, value2, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth_date not between", value1, value2, "birthDate");
            return (Criteria) this;
        }

        public Criteria andNationalIdIsNull() {
            addCriterion("national_id is null");
            return (Criteria) this;
        }

        public Criteria andNationalIdIsNotNull() {
            addCriterion("national_id is not null");
            return (Criteria) this;
        }

        public Criteria andNationalIdEqualTo(String value) {
            addCriterion("national_id =", value, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdNotEqualTo(String value) {
            addCriterion("national_id <>", value, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdGreaterThan(String value) {
            addCriterion("national_id >", value, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdGreaterThanOrEqualTo(String value) {
            addCriterion("national_id >=", value, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdLessThan(String value) {
            addCriterion("national_id <", value, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdLessThanOrEqualTo(String value) {
            addCriterion("national_id <=", value, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdLike(String value) {
            addCriterion("national_id like", value, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdNotLike(String value) {
            addCriterion("national_id not like", value, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdIn(List<String> values) {
            addCriterion("national_id in", values, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdNotIn(List<String> values) {
            addCriterion("national_id not in", values, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdBetween(String value1, String value2) {
            addCriterion("national_id between", value1, value2, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalIdNotBetween(String value1, String value2) {
            addCriterion("national_id not between", value1, value2, "nationalId");
            return (Criteria) this;
        }

        public Criteria andNationalTypeIsNull() {
            addCriterion("national_type is null");
            return (Criteria) this;
        }

        public Criteria andNationalTypeIsNotNull() {
            addCriterion("national_type is not null");
            return (Criteria) this;
        }

        public Criteria andNationalTypeEqualTo(String value) {
            addCriterion("national_type =", value, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeNotEqualTo(String value) {
            addCriterion("national_type <>", value, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeGreaterThan(String value) {
            addCriterion("national_type >", value, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeGreaterThanOrEqualTo(String value) {
            addCriterion("national_type >=", value, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeLessThan(String value) {
            addCriterion("national_type <", value, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeLessThanOrEqualTo(String value) {
            addCriterion("national_type <=", value, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeLike(String value) {
            addCriterion("national_type like", value, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeNotLike(String value) {
            addCriterion("national_type not like", value, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeIn(List<String> values) {
            addCriterion("national_type in", values, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeNotIn(List<String> values) {
            addCriterion("national_type not in", values, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeBetween(String value1, String value2) {
            addCriterion("national_type between", value1, value2, "nationalType");
            return (Criteria) this;
        }

        public Criteria andNationalTypeNotBetween(String value1, String value2) {
            addCriterion("national_type not between", value1, value2, "nationalType");
            return (Criteria) this;
        }

        public Criteria andMarryStatusIsNull() {
            addCriterion("marry_status is null");
            return (Criteria) this;
        }

        public Criteria andMarryStatusIsNotNull() {
            addCriterion("marry_status is not null");
            return (Criteria) this;
        }

        public Criteria andMarryStatusEqualTo(Boolean value) {
            addCriterion("marry_status =", value, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusNotEqualTo(Boolean value) {
            addCriterion("marry_status <>", value, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusGreaterThan(Boolean value) {
            addCriterion("marry_status >", value, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("marry_status >=", value, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusLessThan(Boolean value) {
            addCriterion("marry_status <", value, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("marry_status <=", value, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusIn(List<Boolean> values) {
            addCriterion("marry_status in", values, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusNotIn(List<Boolean> values) {
            addCriterion("marry_status not in", values, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("marry_status between", value1, value2, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andMarryStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("marry_status not between", value1, value2, "marryStatus");
            return (Criteria) this;
        }

        public Criteria andPattyNameIsNull() {
            addCriterion("patty_name is null");
            return (Criteria) this;
        }

        public Criteria andPattyNameIsNotNull() {
            addCriterion("patty_name is not null");
            return (Criteria) this;
        }

        public Criteria andPattyNameEqualTo(String value) {
            addCriterion("patty_name =", value, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameNotEqualTo(String value) {
            addCriterion("patty_name <>", value, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameGreaterThan(String value) {
            addCriterion("patty_name >", value, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameGreaterThanOrEqualTo(String value) {
            addCriterion("patty_name >=", value, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameLessThan(String value) {
            addCriterion("patty_name <", value, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameLessThanOrEqualTo(String value) {
            addCriterion("patty_name <=", value, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameLike(String value) {
            addCriterion("patty_name like", value, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameNotLike(String value) {
            addCriterion("patty_name not like", value, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameIn(List<String> values) {
            addCriterion("patty_name in", values, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameNotIn(List<String> values) {
            addCriterion("patty_name not in", values, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameBetween(String value1, String value2) {
            addCriterion("patty_name between", value1, value2, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPattyNameNotBetween(String value1, String value2) {
            addCriterion("patty_name not between", value1, value2, "pattyName");
            return (Criteria) this;
        }

        public Criteria andPositionIdIsNull() {
            addCriterion("position_id is null");
            return (Criteria) this;
        }

        public Criteria andPositionIdIsNotNull() {
            addCriterion("position_id is not null");
            return (Criteria) this;
        }

        public Criteria andPositionIdEqualTo(String value) {
            addCriterion("position_id =", value, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdNotEqualTo(String value) {
            addCriterion("position_id <>", value, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdGreaterThan(String value) {
            addCriterion("position_id >", value, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdGreaterThanOrEqualTo(String value) {
            addCriterion("position_id >=", value, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdLessThan(String value) {
            addCriterion("position_id <", value, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdLessThanOrEqualTo(String value) {
            addCriterion("position_id <=", value, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdLike(String value) {
            addCriterion("position_id like", value, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdNotLike(String value) {
            addCriterion("position_id not like", value, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdIn(List<String> values) {
            addCriterion("position_id in", values, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdNotIn(List<String> values) {
            addCriterion("position_id not in", values, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdBetween(String value1, String value2) {
            addCriterion("position_id between", value1, value2, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionIdNotBetween(String value1, String value2) {
            addCriterion("position_id not between", value1, value2, "positionId");
            return (Criteria) this;
        }

        public Criteria andPositionNameIsNull() {
            addCriterion("position_name is null");
            return (Criteria) this;
        }

        public Criteria andPositionNameIsNotNull() {
            addCriterion("position_name is not null");
            return (Criteria) this;
        }

        public Criteria andPositionNameEqualTo(String value) {
            addCriterion("position_name =", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameNotEqualTo(String value) {
            addCriterion("position_name <>", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameGreaterThan(String value) {
            addCriterion("position_name >", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameGreaterThanOrEqualTo(String value) {
            addCriterion("position_name >=", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameLessThan(String value) {
            addCriterion("position_name <", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameLessThanOrEqualTo(String value) {
            addCriterion("position_name <=", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameLike(String value) {
            addCriterion("position_name like", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameNotLike(String value) {
            addCriterion("position_name not like", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameIn(List<String> values) {
            addCriterion("position_name in", values, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameNotIn(List<String> values) {
            addCriterion("position_name not in", values, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameBetween(String value1, String value2) {
            addCriterion("position_name between", value1, value2, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameNotBetween(String value1, String value2) {
            addCriterion("position_name not between", value1, value2, "positionName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdIsNull() {
            addCriterion("organization_parent_id is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdIsNotNull() {
            addCriterion("organization_parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdEqualTo(String value) {
            addCriterion("organization_parent_id =", value, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdNotEqualTo(String value) {
            addCriterion("organization_parent_id <>", value, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdGreaterThan(String value) {
            addCriterion("organization_parent_id >", value, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("organization_parent_id >=", value, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdLessThan(String value) {
            addCriterion("organization_parent_id <", value, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdLessThanOrEqualTo(String value) {
            addCriterion("organization_parent_id <=", value, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdLike(String value) {
            addCriterion("organization_parent_id like", value, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdNotLike(String value) {
            addCriterion("organization_parent_id not like", value, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdIn(List<String> values) {
            addCriterion("organization_parent_id in", values, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdNotIn(List<String> values) {
            addCriterion("organization_parent_id not in", values, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdBetween(String value1, String value2) {
            addCriterion("organization_parent_id between", value1, value2, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentIdNotBetween(String value1, String value2) {
            addCriterion("organization_parent_id not between", value1, value2, "organizationParentId");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameIsNull() {
            addCriterion("organization_parent_name is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameIsNotNull() {
            addCriterion("organization_parent_name is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameEqualTo(String value) {
            addCriterion("organization_parent_name =", value, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameNotEqualTo(String value) {
            addCriterion("organization_parent_name <>", value, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameGreaterThan(String value) {
            addCriterion("organization_parent_name >", value, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameGreaterThanOrEqualTo(String value) {
            addCriterion("organization_parent_name >=", value, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameLessThan(String value) {
            addCriterion("organization_parent_name <", value, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameLessThanOrEqualTo(String value) {
            addCriterion("organization_parent_name <=", value, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameLike(String value) {
            addCriterion("organization_parent_name like", value, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameNotLike(String value) {
            addCriterion("organization_parent_name not like", value, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameIn(List<String> values) {
            addCriterion("organization_parent_name in", values, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameNotIn(List<String> values) {
            addCriterion("organization_parent_name not in", values, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameBetween(String value1, String value2) {
            addCriterion("organization_parent_name between", value1, value2, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationParentNameNotBetween(String value1, String value2) {
            addCriterion("organization_parent_name not between", value1, value2, "organizationParentName");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdIsNull() {
            addCriterion("organization_id is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdIsNotNull() {
            addCriterion("organization_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdEqualTo(String value) {
            addCriterion("organization_id =", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotEqualTo(String value) {
            addCriterion("organization_id <>", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdGreaterThan(String value) {
            addCriterion("organization_id >", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdGreaterThanOrEqualTo(String value) {
            addCriterion("organization_id >=", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdLessThan(String value) {
            addCriterion("organization_id <", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdLessThanOrEqualTo(String value) {
            addCriterion("organization_id <=", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdLike(String value) {
            addCriterion("organization_id like", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotLike(String value) {
            addCriterion("organization_id not like", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdIn(List<String> values) {
            addCriterion("organization_id in", values, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotIn(List<String> values) {
            addCriterion("organization_id not in", values, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdBetween(String value1, String value2) {
            addCriterion("organization_id between", value1, value2, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotBetween(String value1, String value2) {
            addCriterion("organization_id not between", value1, value2, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameIsNull() {
            addCriterion("organization_name is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameIsNotNull() {
            addCriterion("organization_name is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameEqualTo(String value) {
            addCriterion("organization_name =", value, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameNotEqualTo(String value) {
            addCriterion("organization_name <>", value, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameGreaterThan(String value) {
            addCriterion("organization_name >", value, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameGreaterThanOrEqualTo(String value) {
            addCriterion("organization_name >=", value, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameLessThan(String value) {
            addCriterion("organization_name <", value, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameLessThanOrEqualTo(String value) {
            addCriterion("organization_name <=", value, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameLike(String value) {
            addCriterion("organization_name like", value, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameNotLike(String value) {
            addCriterion("organization_name not like", value, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameIn(List<String> values) {
            addCriterion("organization_name in", values, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameNotIn(List<String> values) {
            addCriterion("organization_name not in", values, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameBetween(String value1, String value2) {
            addCriterion("organization_name between", value1, value2, "organizationName");
            return (Criteria) this;
        }

        public Criteria andOrganizationNameNotBetween(String value1, String value2) {
            addCriterion("organization_name not between", value1, value2, "organizationName");
            return (Criteria) this;
        }

        public Criteria andSequenceIdIsNull() {
            addCriterion("sequence_id is null");
            return (Criteria) this;
        }

        public Criteria andSequenceIdIsNotNull() {
            addCriterion("sequence_id is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceIdEqualTo(String value) {
            addCriterion("sequence_id =", value, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdNotEqualTo(String value) {
            addCriterion("sequence_id <>", value, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdGreaterThan(String value) {
            addCriterion("sequence_id >", value, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdGreaterThanOrEqualTo(String value) {
            addCriterion("sequence_id >=", value, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdLessThan(String value) {
            addCriterion("sequence_id <", value, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdLessThanOrEqualTo(String value) {
            addCriterion("sequence_id <=", value, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdLike(String value) {
            addCriterion("sequence_id like", value, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdNotLike(String value) {
            addCriterion("sequence_id not like", value, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdIn(List<String> values) {
            addCriterion("sequence_id in", values, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdNotIn(List<String> values) {
            addCriterion("sequence_id not in", values, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdBetween(String value1, String value2) {
            addCriterion("sequence_id between", value1, value2, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceIdNotBetween(String value1, String value2) {
            addCriterion("sequence_id not between", value1, value2, "sequenceId");
            return (Criteria) this;
        }

        public Criteria andSequenceNameIsNull() {
            addCriterion("sequence_name is null");
            return (Criteria) this;
        }

        public Criteria andSequenceNameIsNotNull() {
            addCriterion("sequence_name is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceNameEqualTo(String value) {
            addCriterion("sequence_name =", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameNotEqualTo(String value) {
            addCriterion("sequence_name <>", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameGreaterThan(String value) {
            addCriterion("sequence_name >", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameGreaterThanOrEqualTo(String value) {
            addCriterion("sequence_name >=", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameLessThan(String value) {
            addCriterion("sequence_name <", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameLessThanOrEqualTo(String value) {
            addCriterion("sequence_name <=", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameLike(String value) {
            addCriterion("sequence_name like", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameNotLike(String value) {
            addCriterion("sequence_name not like", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameIn(List<String> values) {
            addCriterion("sequence_name in", values, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameNotIn(List<String> values) {
            addCriterion("sequence_name not in", values, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameBetween(String value1, String value2) {
            addCriterion("sequence_name between", value1, value2, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameNotBetween(String value1, String value2) {
            addCriterion("sequence_name not between", value1, value2, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdIsNull() {
            addCriterion("sequence_sub_id is null");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdIsNotNull() {
            addCriterion("sequence_sub_id is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdEqualTo(String value) {
            addCriterion("sequence_sub_id =", value, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdNotEqualTo(String value) {
            addCriterion("sequence_sub_id <>", value, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdGreaterThan(String value) {
            addCriterion("sequence_sub_id >", value, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdGreaterThanOrEqualTo(String value) {
            addCriterion("sequence_sub_id >=", value, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdLessThan(String value) {
            addCriterion("sequence_sub_id <", value, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdLessThanOrEqualTo(String value) {
            addCriterion("sequence_sub_id <=", value, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdLike(String value) {
            addCriterion("sequence_sub_id like", value, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdNotLike(String value) {
            addCriterion("sequence_sub_id not like", value, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdIn(List<String> values) {
            addCriterion("sequence_sub_id in", values, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdNotIn(List<String> values) {
            addCriterion("sequence_sub_id not in", values, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdBetween(String value1, String value2) {
            addCriterion("sequence_sub_id between", value1, value2, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubIdNotBetween(String value1, String value2) {
            addCriterion("sequence_sub_id not between", value1, value2, "sequenceSubId");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameIsNull() {
            addCriterion("sequence_sub_name is null");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameIsNotNull() {
            addCriterion("sequence_sub_name is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameEqualTo(String value) {
            addCriterion("sequence_sub_name =", value, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameNotEqualTo(String value) {
            addCriterion("sequence_sub_name <>", value, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameGreaterThan(String value) {
            addCriterion("sequence_sub_name >", value, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameGreaterThanOrEqualTo(String value) {
            addCriterion("sequence_sub_name >=", value, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameLessThan(String value) {
            addCriterion("sequence_sub_name <", value, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameLessThanOrEqualTo(String value) {
            addCriterion("sequence_sub_name <=", value, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameLike(String value) {
            addCriterion("sequence_sub_name like", value, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameNotLike(String value) {
            addCriterion("sequence_sub_name not like", value, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameIn(List<String> values) {
            addCriterion("sequence_sub_name in", values, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameNotIn(List<String> values) {
            addCriterion("sequence_sub_name not in", values, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameBetween(String value1, String value2) {
            addCriterion("sequence_sub_name between", value1, value2, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andSequenceSubNameNotBetween(String value1, String value2) {
            addCriterion("sequence_sub_name not between", value1, value2, "sequenceSubName");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdIsNull() {
            addCriterion("performance_id is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdIsNotNull() {
            addCriterion("performance_id is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdEqualTo(String value) {
            addCriterion("performance_id =", value, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdNotEqualTo(String value) {
            addCriterion("performance_id <>", value, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdGreaterThan(String value) {
            addCriterion("performance_id >", value, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdGreaterThanOrEqualTo(String value) {
            addCriterion("performance_id >=", value, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdLessThan(String value) {
            addCriterion("performance_id <", value, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdLessThanOrEqualTo(String value) {
            addCriterion("performance_id <=", value, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdLike(String value) {
            addCriterion("performance_id like", value, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdNotLike(String value) {
            addCriterion("performance_id not like", value, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdIn(List<String> values) {
            addCriterion("performance_id in", values, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdNotIn(List<String> values) {
            addCriterion("performance_id not in", values, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdBetween(String value1, String value2) {
            addCriterion("performance_id between", value1, value2, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceIdNotBetween(String value1, String value2) {
            addCriterion("performance_id not between", value1, value2, "performanceId");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameIsNull() {
            addCriterion("performance_name is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameIsNotNull() {
            addCriterion("performance_name is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameEqualTo(String value) {
            addCriterion("performance_name =", value, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameNotEqualTo(String value) {
            addCriterion("performance_name <>", value, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameGreaterThan(String value) {
            addCriterion("performance_name >", value, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameGreaterThanOrEqualTo(String value) {
            addCriterion("performance_name >=", value, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameLessThan(String value) {
            addCriterion("performance_name <", value, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameLessThanOrEqualTo(String value) {
            addCriterion("performance_name <=", value, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameLike(String value) {
            addCriterion("performance_name like", value, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameNotLike(String value) {
            addCriterion("performance_name not like", value, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameIn(List<String> values) {
            addCriterion("performance_name in", values, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameNotIn(List<String> values) {
            addCriterion("performance_name not in", values, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameBetween(String value1, String value2) {
            addCriterion("performance_name between", value1, value2, "performanceName");
            return (Criteria) this;
        }

        public Criteria andPerformanceNameNotBetween(String value1, String value2) {
            addCriterion("performance_name not between", value1, value2, "performanceName");
            return (Criteria) this;
        }

        public Criteria andAbilityIdIsNull() {
            addCriterion("ability_id is null");
            return (Criteria) this;
        }

        public Criteria andAbilityIdIsNotNull() {
            addCriterion("ability_id is not null");
            return (Criteria) this;
        }

        public Criteria andAbilityIdEqualTo(String value) {
            addCriterion("ability_id =", value, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdNotEqualTo(String value) {
            addCriterion("ability_id <>", value, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdGreaterThan(String value) {
            addCriterion("ability_id >", value, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdGreaterThanOrEqualTo(String value) {
            addCriterion("ability_id >=", value, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdLessThan(String value) {
            addCriterion("ability_id <", value, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdLessThanOrEqualTo(String value) {
            addCriterion("ability_id <=", value, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdLike(String value) {
            addCriterion("ability_id like", value, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdNotLike(String value) {
            addCriterion("ability_id not like", value, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdIn(List<String> values) {
            addCriterion("ability_id in", values, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdNotIn(List<String> values) {
            addCriterion("ability_id not in", values, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdBetween(String value1, String value2) {
            addCriterion("ability_id between", value1, value2, "abilityId");
            return (Criteria) this;
        }

        public Criteria andAbilityIdNotBetween(String value1, String value2) {
            addCriterion("ability_id not between", value1, value2, "abilityId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdIsNull() {
            addCriterion("job_title_id is null");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdIsNotNull() {
            addCriterion("job_title_id is not null");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdEqualTo(String value) {
            addCriterion("job_title_id =", value, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdNotEqualTo(String value) {
            addCriterion("job_title_id <>", value, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdGreaterThan(String value) {
            addCriterion("job_title_id >", value, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdGreaterThanOrEqualTo(String value) {
            addCriterion("job_title_id >=", value, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdLessThan(String value) {
            addCriterion("job_title_id <", value, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdLessThanOrEqualTo(String value) {
            addCriterion("job_title_id <=", value, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdLike(String value) {
            addCriterion("job_title_id like", value, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdNotLike(String value) {
            addCriterion("job_title_id not like", value, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdIn(List<String> values) {
            addCriterion("job_title_id in", values, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdNotIn(List<String> values) {
            addCriterion("job_title_id not in", values, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdBetween(String value1, String value2) {
            addCriterion("job_title_id between", value1, value2, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andJobTitleIdNotBetween(String value1, String value2) {
            addCriterion("job_title_id not between", value1, value2, "jobTitleId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdIsNull() {
            addCriterion("ability_lv_id is null");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdIsNotNull() {
            addCriterion("ability_lv_id is not null");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdEqualTo(String value) {
            addCriterion("ability_lv_id =", value, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdNotEqualTo(String value) {
            addCriterion("ability_lv_id <>", value, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdGreaterThan(String value) {
            addCriterion("ability_lv_id >", value, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdGreaterThanOrEqualTo(String value) {
            addCriterion("ability_lv_id >=", value, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdLessThan(String value) {
            addCriterion("ability_lv_id <", value, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdLessThanOrEqualTo(String value) {
            addCriterion("ability_lv_id <=", value, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdLike(String value) {
            addCriterion("ability_lv_id like", value, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdNotLike(String value) {
            addCriterion("ability_lv_id not like", value, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdIn(List<String> values) {
            addCriterion("ability_lv_id in", values, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdNotIn(List<String> values) {
            addCriterion("ability_lv_id not in", values, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdBetween(String value1, String value2) {
            addCriterion("ability_lv_id between", value1, value2, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityLvIdNotBetween(String value1, String value2) {
            addCriterion("ability_lv_id not between", value1, value2, "abilityLvId");
            return (Criteria) this;
        }

        public Criteria andAbilityNameIsNull() {
            addCriterion("ability_name is null");
            return (Criteria) this;
        }

        public Criteria andAbilityNameIsNotNull() {
            addCriterion("ability_name is not null");
            return (Criteria) this;
        }

        public Criteria andAbilityNameEqualTo(String value) {
            addCriterion("ability_name =", value, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameNotEqualTo(String value) {
            addCriterion("ability_name <>", value, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameGreaterThan(String value) {
            addCriterion("ability_name >", value, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameGreaterThanOrEqualTo(String value) {
            addCriterion("ability_name >=", value, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameLessThan(String value) {
            addCriterion("ability_name <", value, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameLessThanOrEqualTo(String value) {
            addCriterion("ability_name <=", value, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameLike(String value) {
            addCriterion("ability_name like", value, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameNotLike(String value) {
            addCriterion("ability_name not like", value, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameIn(List<String> values) {
            addCriterion("ability_name in", values, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameNotIn(List<String> values) {
            addCriterion("ability_name not in", values, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameBetween(String value1, String value2) {
            addCriterion("ability_name between", value1, value2, "abilityName");
            return (Criteria) this;
        }

        public Criteria andAbilityNameNotBetween(String value1, String value2) {
            addCriterion("ability_name not between", value1, value2, "abilityName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameIsNull() {
            addCriterion("job_title_name is null");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameIsNotNull() {
            addCriterion("job_title_name is not null");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameEqualTo(String value) {
            addCriterion("job_title_name =", value, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameNotEqualTo(String value) {
            addCriterion("job_title_name <>", value, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameGreaterThan(String value) {
            addCriterion("job_title_name >", value, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameGreaterThanOrEqualTo(String value) {
            addCriterion("job_title_name >=", value, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameLessThan(String value) {
            addCriterion("job_title_name <", value, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameLessThanOrEqualTo(String value) {
            addCriterion("job_title_name <=", value, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameLike(String value) {
            addCriterion("job_title_name like", value, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameNotLike(String value) {
            addCriterion("job_title_name not like", value, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameIn(List<String> values) {
            addCriterion("job_title_name in", values, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameNotIn(List<String> values) {
            addCriterion("job_title_name not in", values, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameBetween(String value1, String value2) {
            addCriterion("job_title_name between", value1, value2, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andJobTitleNameNotBetween(String value1, String value2) {
            addCriterion("job_title_name not between", value1, value2, "jobTitleName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameIsNull() {
            addCriterion("ability_lv_name is null");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameIsNotNull() {
            addCriterion("ability_lv_name is not null");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameEqualTo(String value) {
            addCriterion("ability_lv_name =", value, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameNotEqualTo(String value) {
            addCriterion("ability_lv_name <>", value, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameGreaterThan(String value) {
            addCriterion("ability_lv_name >", value, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameGreaterThanOrEqualTo(String value) {
            addCriterion("ability_lv_name >=", value, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameLessThan(String value) {
            addCriterion("ability_lv_name <", value, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameLessThanOrEqualTo(String value) {
            addCriterion("ability_lv_name <=", value, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameLike(String value) {
            addCriterion("ability_lv_name like", value, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameNotLike(String value) {
            addCriterion("ability_lv_name not like", value, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameIn(List<String> values) {
            addCriterion("ability_lv_name in", values, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameNotIn(List<String> values) {
            addCriterion("ability_lv_name not in", values, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameBetween(String value1, String value2) {
            addCriterion("ability_lv_name between", value1, value2, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andAbilityLvNameNotBetween(String value1, String value2) {
            addCriterion("ability_lv_name not between", value1, value2, "abilityLvName");
            return (Criteria) this;
        }

        public Criteria andRankNameIsNull() {
            addCriterion("rank_name is null");
            return (Criteria) this;
        }

        public Criteria andRankNameIsNotNull() {
            addCriterion("rank_name is not null");
            return (Criteria) this;
        }

        public Criteria andRankNameEqualTo(String value) {
            addCriterion("rank_name =", value, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameNotEqualTo(String value) {
            addCriterion("rank_name <>", value, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameGreaterThan(String value) {
            addCriterion("rank_name >", value, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameGreaterThanOrEqualTo(String value) {
            addCriterion("rank_name >=", value, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameLessThan(String value) {
            addCriterion("rank_name <", value, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameLessThanOrEqualTo(String value) {
            addCriterion("rank_name <=", value, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameLike(String value) {
            addCriterion("rank_name like", value, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameNotLike(String value) {
            addCriterion("rank_name not like", value, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameIn(List<String> values) {
            addCriterion("rank_name in", values, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameNotIn(List<String> values) {
            addCriterion("rank_name not in", values, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameBetween(String value1, String value2) {
            addCriterion("rank_name between", value1, value2, "rankName");
            return (Criteria) this;
        }

        public Criteria andRankNameNotBetween(String value1, String value2) {
            addCriterion("rank_name not between", value1, value2, "rankName");
            return (Criteria) this;
        }

        public Criteria andPopulationIdIsNull() {
            addCriterion("population_id is null");
            return (Criteria) this;
        }

        public Criteria andPopulationIdIsNotNull() {
            addCriterion("population_id is not null");
            return (Criteria) this;
        }

        public Criteria andPopulationIdEqualTo(String value) {
            addCriterion("population_id =", value, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdNotEqualTo(String value) {
            addCriterion("population_id <>", value, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdGreaterThan(String value) {
            addCriterion("population_id >", value, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdGreaterThanOrEqualTo(String value) {
            addCriterion("population_id >=", value, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdLessThan(String value) {
            addCriterion("population_id <", value, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdLessThanOrEqualTo(String value) {
            addCriterion("population_id <=", value, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdLike(String value) {
            addCriterion("population_id like", value, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdNotLike(String value) {
            addCriterion("population_id not like", value, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdIn(List<String> values) {
            addCriterion("population_id in", values, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdNotIn(List<String> values) {
            addCriterion("population_id not in", values, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdBetween(String value1, String value2) {
            addCriterion("population_id between", value1, value2, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationIdNotBetween(String value1, String value2) {
            addCriterion("population_id not between", value1, value2, "populationId");
            return (Criteria) this;
        }

        public Criteria andPopulationNameIsNull() {
            addCriterion("population_name is null");
            return (Criteria) this;
        }

        public Criteria andPopulationNameIsNotNull() {
            addCriterion("population_name is not null");
            return (Criteria) this;
        }

        public Criteria andPopulationNameEqualTo(String value) {
            addCriterion("population_name =", value, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameNotEqualTo(String value) {
            addCriterion("population_name <>", value, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameGreaterThan(String value) {
            addCriterion("population_name >", value, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameGreaterThanOrEqualTo(String value) {
            addCriterion("population_name >=", value, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameLessThan(String value) {
            addCriterion("population_name <", value, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameLessThanOrEqualTo(String value) {
            addCriterion("population_name <=", value, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameLike(String value) {
            addCriterion("population_name like", value, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameNotLike(String value) {
            addCriterion("population_name not like", value, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameIn(List<String> values) {
            addCriterion("population_name in", values, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameNotIn(List<String> values) {
            addCriterion("population_name not in", values, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameBetween(String value1, String value2) {
            addCriterion("population_name between", value1, value2, "populationName");
            return (Criteria) this;
        }

        public Criteria andPopulationNameNotBetween(String value1, String value2) {
            addCriterion("population_name not between", value1, value2, "populationName");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNull() {
            addCriterion("area_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNotNull() {
            addCriterion("area_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaIdEqualTo(String value) {
            addCriterion("area_id =", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotEqualTo(String value) {
            addCriterion("area_id <>", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThan(String value) {
            addCriterion("area_id >", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThanOrEqualTo(String value) {
            addCriterion("area_id >=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThan(String value) {
            addCriterion("area_id <", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThanOrEqualTo(String value) {
            addCriterion("area_id <=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLike(String value) {
            addCriterion("area_id like", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotLike(String value) {
            addCriterion("area_id not like", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIn(List<String> values) {
            addCriterion("area_id in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotIn(List<String> values) {
            addCriterion("area_id not in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdBetween(String value1, String value2) {
            addCriterion("area_id between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotBetween(String value1, String value2) {
            addCriterion("area_id not between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andRunOffDateIsNull() {
            addCriterion("run_off_date is null");
            return (Criteria) this;
        }

        public Criteria andRunOffDateIsNotNull() {
            addCriterion("run_off_date is not null");
            return (Criteria) this;
        }

        public Criteria andRunOffDateEqualTo(Date value) {
            addCriterionForJDBCDate("run_off_date =", value, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("run_off_date <>", value, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateGreaterThan(Date value) {
            addCriterionForJDBCDate("run_off_date >", value, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("run_off_date >=", value, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateLessThan(Date value) {
            addCriterionForJDBCDate("run_off_date <", value, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("run_off_date <=", value, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateIn(List<Date> values) {
            addCriterionForJDBCDate("run_off_date in", values, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("run_off_date not in", values, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("run_off_date between", value1, value2, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andRunOffDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("run_off_date not between", value1, value2, "runOffDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateIsNull() {
            addCriterion("entry_date is null");
            return (Criteria) this;
        }

        public Criteria andEntryDateIsNotNull() {
            addCriterion("entry_date is not null");
            return (Criteria) this;
        }

        public Criteria andEntryDateEqualTo(Date value) {
            addCriterion("entry_date =", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateNotEqualTo(Date value) {
            addCriterion("entry_date <>", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateGreaterThan(Date value) {
            addCriterion("entry_date >", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("entry_date >=", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateLessThan(Date value) {
            addCriterion("entry_date <", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateLessThanOrEqualTo(Date value) {
            addCriterion("entry_date <=", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateIn(List<Date> values) {
            addCriterion("entry_date in", values, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateNotIn(List<Date> values) {
            addCriterion("entry_date not in", values, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateBetween(Date value1, Date value2) {
            addCriterion("entry_date between", value1, value2, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateNotBetween(Date value1, Date value2) {
            addCriterion("entry_date not between", value1, value2, "entryDate");
            return (Criteria) this;
        }

        public Criteria andTelPhoneIsNull() {
            addCriterion("tel_phone is null");
            return (Criteria) this;
        }

        public Criteria andTelPhoneIsNotNull() {
            addCriterion("tel_phone is not null");
            return (Criteria) this;
        }

        public Criteria andTelPhoneEqualTo(String value) {
            addCriterion("tel_phone =", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneNotEqualTo(String value) {
            addCriterion("tel_phone <>", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneGreaterThan(String value) {
            addCriterion("tel_phone >", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("tel_phone >=", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneLessThan(String value) {
            addCriterion("tel_phone <", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneLessThanOrEqualTo(String value) {
            addCriterion("tel_phone <=", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneLike(String value) {
            addCriterion("tel_phone like", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneNotLike(String value) {
            addCriterion("tel_phone not like", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneIn(List<String> values) {
            addCriterion("tel_phone in", values, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneNotIn(List<String> values) {
            addCriterion("tel_phone not in", values, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneBetween(String value1, String value2) {
            addCriterion("tel_phone between", value1, value2, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneNotBetween(String value1, String value2) {
            addCriterion("tel_phone not between", value1, value2, "telPhone");
            return (Criteria) this;
        }

        public Criteria andQqIsNull() {
            addCriterion("qq is null");
            return (Criteria) this;
        }

        public Criteria andQqIsNotNull() {
            addCriterion("qq is not null");
            return (Criteria) this;
        }

        public Criteria andQqEqualTo(String value) {
            addCriterion("qq =", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotEqualTo(String value) {
            addCriterion("qq <>", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThan(String value) {
            addCriterion("qq >", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThanOrEqualTo(String value) {
            addCriterion("qq >=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThan(String value) {
            addCriterion("qq <", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThanOrEqualTo(String value) {
            addCriterion("qq <=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLike(String value) {
            addCriterion("qq like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotLike(String value) {
            addCriterion("qq not like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqIn(List<String> values) {
            addCriterion("qq in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotIn(List<String> values) {
            addCriterion("qq not in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqBetween(String value1, String value2) {
            addCriterion("qq between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotBetween(String value1, String value2) {
            addCriterion("qq not between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andWxCodeIsNull() {
            addCriterion("wx_code is null");
            return (Criteria) this;
        }

        public Criteria andWxCodeIsNotNull() {
            addCriterion("wx_code is not null");
            return (Criteria) this;
        }

        public Criteria andWxCodeEqualTo(String value) {
            addCriterion("wx_code =", value, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeNotEqualTo(String value) {
            addCriterion("wx_code <>", value, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeGreaterThan(String value) {
            addCriterion("wx_code >", value, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeGreaterThanOrEqualTo(String value) {
            addCriterion("wx_code >=", value, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeLessThan(String value) {
            addCriterion("wx_code <", value, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeLessThanOrEqualTo(String value) {
            addCriterion("wx_code <=", value, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeLike(String value) {
            addCriterion("wx_code like", value, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeNotLike(String value) {
            addCriterion("wx_code not like", value, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeIn(List<String> values) {
            addCriterion("wx_code in", values, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeNotIn(List<String> values) {
            addCriterion("wx_code not in", values, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeBetween(String value1, String value2) {
            addCriterion("wx_code between", value1, value2, "wxCode");
            return (Criteria) this;
        }

        public Criteria andWxCodeNotBetween(String value1, String value2) {
            addCriterion("wx_code not between", value1, value2, "wxCode");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andContractUnitIsNull() {
            addCriterion("contract_unit is null");
            return (Criteria) this;
        }

        public Criteria andContractUnitIsNotNull() {
            addCriterion("contract_unit is not null");
            return (Criteria) this;
        }

        public Criteria andContractUnitEqualTo(String value) {
            addCriterion("contract_unit =", value, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitNotEqualTo(String value) {
            addCriterion("contract_unit <>", value, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitGreaterThan(String value) {
            addCriterion("contract_unit >", value, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitGreaterThanOrEqualTo(String value) {
            addCriterion("contract_unit >=", value, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitLessThan(String value) {
            addCriterion("contract_unit <", value, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitLessThanOrEqualTo(String value) {
            addCriterion("contract_unit <=", value, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitLike(String value) {
            addCriterion("contract_unit like", value, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitNotLike(String value) {
            addCriterion("contract_unit not like", value, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitIn(List<String> values) {
            addCriterion("contract_unit in", values, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitNotIn(List<String> values) {
            addCriterion("contract_unit not in", values, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitBetween(String value1, String value2) {
            addCriterion("contract_unit between", value1, value2, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andContractUnitNotBetween(String value1, String value2) {
            addCriterion("contract_unit not between", value1, value2, "contractUnit");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdIsNull() {
            addCriterion("work_place_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdIsNotNull() {
            addCriterion("work_place_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdEqualTo(String value) {
            addCriterion("work_place_id =", value, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdNotEqualTo(String value) {
            addCriterion("work_place_id <>", value, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdGreaterThan(String value) {
            addCriterion("work_place_id >", value, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdGreaterThanOrEqualTo(String value) {
            addCriterion("work_place_id >=", value, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdLessThan(String value) {
            addCriterion("work_place_id <", value, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdLessThanOrEqualTo(String value) {
            addCriterion("work_place_id <=", value, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdLike(String value) {
            addCriterion("work_place_id like", value, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdNotLike(String value) {
            addCriterion("work_place_id not like", value, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdIn(List<String> values) {
            addCriterion("work_place_id in", values, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdNotIn(List<String> values) {
            addCriterion("work_place_id not in", values, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdBetween(String value1, String value2) {
            addCriterion("work_place_id between", value1, value2, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIdNotBetween(String value1, String value2) {
            addCriterion("work_place_id not between", value1, value2, "workPlaceId");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIsNull() {
            addCriterion("work_place is null");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIsNotNull() {
            addCriterion("work_place is not null");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceEqualTo(String value) {
            addCriterion("work_place =", value, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceNotEqualTo(String value) {
            addCriterion("work_place <>", value, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceGreaterThan(String value) {
            addCriterion("work_place >", value, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("work_place >=", value, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceLessThan(String value) {
            addCriterion("work_place <", value, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceLessThanOrEqualTo(String value) {
            addCriterion("work_place <=", value, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceLike(String value) {
            addCriterion("work_place like", value, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceNotLike(String value) {
            addCriterion("work_place not like", value, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceIn(List<String> values) {
            addCriterion("work_place in", values, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceNotIn(List<String> values) {
            addCriterion("work_place not in", values, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceBetween(String value1, String value2) {
            addCriterion("work_place between", value1, value2, "workPlace");
            return (Criteria) this;
        }

        public Criteria andWorkPlaceNotBetween(String value1, String value2) {
            addCriterion("work_place not between", value1, value2, "workPlace");
            return (Criteria) this;
        }

        public Criteria andRegularDateIsNull() {
            addCriterion("regular_date is null");
            return (Criteria) this;
        }

        public Criteria andRegularDateIsNotNull() {
            addCriterion("regular_date is not null");
            return (Criteria) this;
        }

        public Criteria andRegularDateEqualTo(Date value) {
            addCriterion("regular_date =", value, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateNotEqualTo(Date value) {
            addCriterion("regular_date <>", value, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateGreaterThan(Date value) {
            addCriterion("regular_date >", value, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateGreaterThanOrEqualTo(Date value) {
            addCriterion("regular_date >=", value, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateLessThan(Date value) {
            addCriterion("regular_date <", value, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateLessThanOrEqualTo(Date value) {
            addCriterion("regular_date <=", value, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateIn(List<Date> values) {
            addCriterion("regular_date in", values, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateNotIn(List<Date> values) {
            addCriterion("regular_date not in", values, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateBetween(Date value1, Date value2) {
            addCriterion("regular_date between", value1, value2, "regularDate");
            return (Criteria) this;
        }

        public Criteria andRegularDateNotBetween(Date value1, Date value2) {
            addCriterion("regular_date not between", value1, value2, "regularDate");
            return (Criteria) this;
        }

        public Criteria andApplyTypeIsNull() {
            addCriterion("apply_type is null");
            return (Criteria) this;
        }

        public Criteria andApplyTypeIsNotNull() {
            addCriterion("apply_type is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTypeEqualTo(String value) {
            addCriterion("apply_type =", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotEqualTo(String value) {
            addCriterion("apply_type <>", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeGreaterThan(String value) {
            addCriterion("apply_type >", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("apply_type >=", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeLessThan(String value) {
            addCriterion("apply_type <", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeLessThanOrEqualTo(String value) {
            addCriterion("apply_type <=", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeLike(String value) {
            addCriterion("apply_type like", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotLike(String value) {
            addCriterion("apply_type not like", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeIn(List<String> values) {
            addCriterion("apply_type in", values, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotIn(List<String> values) {
            addCriterion("apply_type not in", values, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeBetween(String value1, String value2) {
            addCriterion("apply_type between", value1, value2, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotBetween(String value1, String value2) {
            addCriterion("apply_type not between", value1, value2, "applyType");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(String value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(String value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(String value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(String value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(String value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(String value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLike(String value) {
            addCriterion("channel_id like", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotLike(String value) {
            addCriterion("channel_id not like", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<String> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<String> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(String value1, String value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(String value1, String value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andSpecialityIsNull() {
            addCriterion("speciality is null");
            return (Criteria) this;
        }

        public Criteria andSpecialityIsNotNull() {
            addCriterion("speciality is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialityEqualTo(String value) {
            addCriterion("speciality =", value, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityNotEqualTo(String value) {
            addCriterion("speciality <>", value, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityGreaterThan(String value) {
            addCriterion("speciality >", value, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityGreaterThanOrEqualTo(String value) {
            addCriterion("speciality >=", value, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityLessThan(String value) {
            addCriterion("speciality <", value, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityLessThanOrEqualTo(String value) {
            addCriterion("speciality <=", value, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityLike(String value) {
            addCriterion("speciality like", value, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityNotLike(String value) {
            addCriterion("speciality not like", value, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityIn(List<String> values) {
            addCriterion("speciality in", values, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityNotIn(List<String> values) {
            addCriterion("speciality not in", values, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityBetween(String value1, String value2) {
            addCriterion("speciality between", value1, value2, "speciality");
            return (Criteria) this;
        }

        public Criteria andSpecialityNotBetween(String value1, String value2) {
            addCriterion("speciality not between", value1, value2, "speciality");
            return (Criteria) this;
        }

        public Criteria andIsRegularIsNull() {
            addCriterion("is_regular is null");
            return (Criteria) this;
        }

        public Criteria andIsRegularIsNotNull() {
            addCriterion("is_regular is not null");
            return (Criteria) this;
        }

        public Criteria andIsRegularEqualTo(String value) {
            addCriterion("is_regular =", value, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularNotEqualTo(String value) {
            addCriterion("is_regular <>", value, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularGreaterThan(String value) {
            addCriterion("is_regular >", value, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularGreaterThanOrEqualTo(String value) {
            addCriterion("is_regular >=", value, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularLessThan(String value) {
            addCriterion("is_regular <", value, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularLessThanOrEqualTo(String value) {
            addCriterion("is_regular <=", value, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularLike(String value) {
            addCriterion("is_regular like", value, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularNotLike(String value) {
            addCriterion("is_regular not like", value, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularIn(List<String> values) {
            addCriterion("is_regular in", values, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularNotIn(List<String> values) {
            addCriterion("is_regular not in", values, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularBetween(String value1, String value2) {
            addCriterion("is_regular between", value1, value2, "isRegular");
            return (Criteria) this;
        }

        public Criteria andIsRegularNotBetween(String value1, String value2) {
            addCriterion("is_regular not between", value1, value2, "isRegular");
            return (Criteria) this;
        }

        public Criteria andRefreshDateIsNull() {
            addCriterion("refresh_date is null");
            return (Criteria) this;
        }

        public Criteria andRefreshDateIsNotNull() {
            addCriterion("refresh_date is not null");
            return (Criteria) this;
        }

        public Criteria andRefreshDateEqualTo(Date value) {
            addCriterion("refresh_date =", value, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateNotEqualTo(Date value) {
            addCriterion("refresh_date <>", value, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateGreaterThan(Date value) {
            addCriterion("refresh_date >", value, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateGreaterThanOrEqualTo(Date value) {
            addCriterion("refresh_date >=", value, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateLessThan(Date value) {
            addCriterion("refresh_date <", value, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateLessThanOrEqualTo(Date value) {
            addCriterion("refresh_date <=", value, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateIn(List<Date> values) {
            addCriterion("refresh_date in", values, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateNotIn(List<Date> values) {
            addCriterion("refresh_date not in", values, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateBetween(Date value1, Date value2) {
            addCriterion("refresh_date between", value1, value2, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andRefreshDateNotBetween(Date value1, Date value2) {
            addCriterion("refresh_date not between", value1, value2, "refreshDate");
            return (Criteria) this;
        }

        public Criteria andCIdIsNull() {
            addCriterion("c_id is null");
            return (Criteria) this;
        }

        public Criteria andCIdIsNotNull() {
            addCriterion("c_id is not null");
            return (Criteria) this;
        }

        public Criteria andCIdEqualTo(String value) {
            addCriterion("c_id =", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdNotEqualTo(String value) {
            addCriterion("c_id <>", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdGreaterThan(String value) {
            addCriterion("c_id >", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdGreaterThanOrEqualTo(String value) {
            addCriterion("c_id >=", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdLessThan(String value) {
            addCriterion("c_id <", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdLessThanOrEqualTo(String value) {
            addCriterion("c_id <=", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdLike(String value) {
            addCriterion("c_id like", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdNotLike(String value) {
            addCriterion("c_id not like", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdIn(List<String> values) {
            addCriterion("c_id in", values, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdNotIn(List<String> values) {
            addCriterion("c_id not in", values, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdBetween(String value1, String value2) {
            addCriterion("c_id between", value1, value2, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdNotBetween(String value1, String value2) {
            addCriterion("c_id not between", value1, value2, "cId");
            return (Criteria) this;
        }

        public Criteria andMarkIsNull() {
            addCriterion("mark is null");
            return (Criteria) this;
        }

        public Criteria andMarkIsNotNull() {
            addCriterion("mark is not null");
            return (Criteria) this;
        }

        public Criteria andMarkEqualTo(Byte value) {
            addCriterion("mark =", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotEqualTo(Byte value) {
            addCriterion("mark <>", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkGreaterThan(Byte value) {
            addCriterion("mark >", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkGreaterThanOrEqualTo(Byte value) {
            addCriterion("mark >=", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkLessThan(Byte value) {
            addCriterion("mark <", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkLessThanOrEqualTo(Byte value) {
            addCriterion("mark <=", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkIn(List<Byte> values) {
            addCriterion("mark in", values, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotIn(List<Byte> values) {
            addCriterion("mark not in", values, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkBetween(Byte value1, Byte value2) {
            addCriterion("mark between", value1, value2, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotBetween(Byte value1, Byte value2) {
            addCriterion("mark not between", value1, value2, "mark");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdIsNull() {
            addCriterion("v_dim_emp_id is null");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdIsNotNull() {
            addCriterion("v_dim_emp_id is not null");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdEqualTo(String value) {
            addCriterion("v_dim_emp_id =", value, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdNotEqualTo(String value) {
            addCriterion("v_dim_emp_id <>", value, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdGreaterThan(String value) {
            addCriterion("v_dim_emp_id >", value, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdGreaterThanOrEqualTo(String value) {
            addCriterion("v_dim_emp_id >=", value, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdLessThan(String value) {
            addCriterion("v_dim_emp_id <", value, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdLessThanOrEqualTo(String value) {
            addCriterion("v_dim_emp_id <=", value, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdLike(String value) {
            addCriterion("v_dim_emp_id like", value, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdNotLike(String value) {
            addCriterion("v_dim_emp_id not like", value, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdIn(List<String> values) {
            addCriterion("v_dim_emp_id in", values, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdNotIn(List<String> values) {
            addCriterion("v_dim_emp_id not in", values, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdBetween(String value1, String value2) {
            addCriterion("v_dim_emp_id between", value1, value2, "vDimEmpId");
            return (Criteria) this;
        }

        public Criteria andVDimEmpIdNotBetween(String value1, String value2) {
            addCriterion("v_dim_emp_id not between", value1, value2, "vDimEmpId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}