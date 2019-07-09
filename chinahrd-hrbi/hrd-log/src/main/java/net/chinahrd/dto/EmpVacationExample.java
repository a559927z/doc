package net.chinahrd.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpVacationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmpVacationExample() {
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

        public Criteria andAnnualTotalIsNull() {
            addCriterion("annual_total is null");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalIsNotNull() {
            addCriterion("annual_total is not null");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalEqualTo(String value) {
            addCriterion("annual_total =", value, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalNotEqualTo(String value) {
            addCriterion("annual_total <>", value, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalGreaterThan(String value) {
            addCriterion("annual_total >", value, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalGreaterThanOrEqualTo(String value) {
            addCriterion("annual_total >=", value, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalLessThan(String value) {
            addCriterion("annual_total <", value, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalLessThanOrEqualTo(String value) {
            addCriterion("annual_total <=", value, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalLike(String value) {
            addCriterion("annual_total like", value, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalNotLike(String value) {
            addCriterion("annual_total not like", value, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalIn(List<String> values) {
            addCriterion("annual_total in", values, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalNotIn(List<String> values) {
            addCriterion("annual_total not in", values, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalBetween(String value1, String value2) {
            addCriterion("annual_total between", value1, value2, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualTotalNotBetween(String value1, String value2) {
            addCriterion("annual_total not between", value1, value2, "annualTotal");
            return (Criteria) this;
        }

        public Criteria andAnnualIsNull() {
            addCriterion("annual is null");
            return (Criteria) this;
        }

        public Criteria andAnnualIsNotNull() {
            addCriterion("annual is not null");
            return (Criteria) this;
        }

        public Criteria andAnnualEqualTo(String value) {
            addCriterion("annual =", value, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualNotEqualTo(String value) {
            addCriterion("annual <>", value, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualGreaterThan(String value) {
            addCriterion("annual >", value, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualGreaterThanOrEqualTo(String value) {
            addCriterion("annual >=", value, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualLessThan(String value) {
            addCriterion("annual <", value, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualLessThanOrEqualTo(String value) {
            addCriterion("annual <=", value, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualLike(String value) {
            addCriterion("annual like", value, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualNotLike(String value) {
            addCriterion("annual not like", value, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualIn(List<String> values) {
            addCriterion("annual in", values, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualNotIn(List<String> values) {
            addCriterion("annual not in", values, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualBetween(String value1, String value2) {
            addCriterion("annual between", value1, value2, "annual");
            return (Criteria) this;
        }

        public Criteria andAnnualNotBetween(String value1, String value2) {
            addCriterion("annual not between", value1, value2, "annual");
            return (Criteria) this;
        }

        public Criteria andCanLeaveIsNull() {
            addCriterion("can_leave is null");
            return (Criteria) this;
        }

        public Criteria andCanLeaveIsNotNull() {
            addCriterion("can_leave is not null");
            return (Criteria) this;
        }

        public Criteria andCanLeaveEqualTo(String value) {
            addCriterion("can_leave =", value, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveNotEqualTo(String value) {
            addCriterion("can_leave <>", value, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveGreaterThan(String value) {
            addCriterion("can_leave >", value, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveGreaterThanOrEqualTo(String value) {
            addCriterion("can_leave >=", value, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveLessThan(String value) {
            addCriterion("can_leave <", value, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveLessThanOrEqualTo(String value) {
            addCriterion("can_leave <=", value, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveLike(String value) {
            addCriterion("can_leave like", value, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveNotLike(String value) {
            addCriterion("can_leave not like", value, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveIn(List<String> values) {
            addCriterion("can_leave in", values, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveNotIn(List<String> values) {
            addCriterion("can_leave not in", values, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveBetween(String value1, String value2) {
            addCriterion("can_leave between", value1, value2, "canLeave");
            return (Criteria) this;
        }

        public Criteria andCanLeaveNotBetween(String value1, String value2) {
            addCriterion("can_leave not between", value1, value2, "canLeave");
            return (Criteria) this;
        }

        public Criteria andHistoryHourIsNull() {
            addCriterion("history_hour is null");
            return (Criteria) this;
        }

        public Criteria andHistoryHourIsNotNull() {
            addCriterion("history_hour is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryHourEqualTo(String value) {
            addCriterion("history_hour =", value, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourNotEqualTo(String value) {
            addCriterion("history_hour <>", value, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourGreaterThan(String value) {
            addCriterion("history_hour >", value, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourGreaterThanOrEqualTo(String value) {
            addCriterion("history_hour >=", value, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourLessThan(String value) {
            addCriterion("history_hour <", value, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourLessThanOrEqualTo(String value) {
            addCriterion("history_hour <=", value, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourLike(String value) {
            addCriterion("history_hour like", value, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourNotLike(String value) {
            addCriterion("history_hour not like", value, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourIn(List<String> values) {
            addCriterion("history_hour in", values, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourNotIn(List<String> values) {
            addCriterion("history_hour not in", values, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourBetween(String value1, String value2) {
            addCriterion("history_hour between", value1, value2, "historyHour");
            return (Criteria) this;
        }

        public Criteria andHistoryHourNotBetween(String value1, String value2) {
            addCriterion("history_hour not between", value1, value2, "historyHour");
            return (Criteria) this;
        }

        public Criteria andRefreshIsNull() {
            addCriterion("refresh is null");
            return (Criteria) this;
        }

        public Criteria andRefreshIsNotNull() {
            addCriterion("refresh is not null");
            return (Criteria) this;
        }

        public Criteria andRefreshEqualTo(Date value) {
            addCriterion("refresh =", value, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshNotEqualTo(Date value) {
            addCriterion("refresh <>", value, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshGreaterThan(Date value) {
            addCriterion("refresh >", value, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshGreaterThanOrEqualTo(Date value) {
            addCriterion("refresh >=", value, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshLessThan(Date value) {
            addCriterion("refresh <", value, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshLessThanOrEqualTo(Date value) {
            addCriterion("refresh <=", value, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshIn(List<Date> values) {
            addCriterion("refresh in", values, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshNotIn(List<Date> values) {
            addCriterion("refresh not in", values, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshBetween(Date value1, Date value2) {
            addCriterion("refresh between", value1, value2, "refresh");
            return (Criteria) this;
        }

        public Criteria andRefreshNotBetween(Date value1, Date value2) {
            addCriterion("refresh not between", value1, value2, "refresh");
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