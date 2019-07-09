package net.chinahrd.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.dto.VDimEmp;
import net.chinahrd.dto.VDimEmpExample.Criteria;
import net.chinahrd.dto.VDimEmpExample.Criterion;
import net.chinahrd.dto.VDimEmpExample;
import org.apache.ibatis.jdbc.SQL;

public class VDimEmpSqlProvider {

    public String countByExample(VDimEmpExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("v_dim_emp");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(VDimEmpExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("v_dim_emp");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(VDimEmp record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("v_dim_emp");

        if (record.getEmpId() != null) {
            sql.VALUES("emp_id", "#{empId,jdbcType=VARCHAR}");
        }

        if (record.getCustomerId() != null) {
            sql.VALUES("customer_id", "#{customerId,jdbcType=CHAR}");
        }

        if (record.getEmpKey() != null) {
            sql.VALUES("emp_key", "#{empKey,jdbcType=VARCHAR}");
        }

        if (record.getUserName() != null) {
            sql.VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
        }

        if (record.getUserNameCh() != null) {
            sql.VALUES("user_name_ch", "#{userNameCh,jdbcType=VARCHAR}");
        }

        if (record.getEmpHfId() != null) {
            sql.VALUES("emp_hf_id", "#{empHfId,jdbcType=CHAR}");
        }

        if (record.getEmpHfKey() != null) {
            sql.VALUES("emp_hf_key", "#{empHfKey,jdbcType=VARCHAR}");
        }

        if (record.getReportRelation() != null) {
            sql.VALUES("report_relation", "#{reportRelation,jdbcType=VARCHAR}");
        }

        if (record.getEmail() != null) {
            sql.VALUES("email", "#{email,jdbcType=VARCHAR}");
        }

        if (record.getImgPath() != null) {
            sql.VALUES("img_path", "#{imgPath,jdbcType=VARCHAR}");
        }

        if (record.getPasstimeOrFulltime() != null) {
            sql.VALUES("passtime_or_fulltime", "#{passtimeOrFulltime,jdbcType=VARCHAR}");
        }

        if (record.getContract() != null) {
            sql.VALUES("contract", "#{contract,jdbcType=VARCHAR}");
        }

        if (record.getBlood() != null) {
            sql.VALUES("blood", "#{blood,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.VALUES("age", "#{age,jdbcType=DOUBLE}");
        }

        if (record.getCompanyAge() != null) {
            sql.VALUES("company_age", "#{companyAge,jdbcType=DOUBLE}");
        }

        if (record.getIsKeyTalent() != null) {
            sql.VALUES("is_key_talent", "#{isKeyTalent,jdbcType=BIT}");
        }

        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=CHAR}");
        }

        if (record.getNation() != null) {
            sql.VALUES("nation", "#{nation,jdbcType=VARCHAR}");
        }

        if (record.getDegreeId() != null) {
            sql.VALUES("degree_id", "#{degreeId,jdbcType=CHAR}");
        }

        if (record.getDegree() != null) {
            sql.VALUES("degree", "#{degree,jdbcType=VARCHAR}");
        }

        if (record.getNativePlace() != null) {
            sql.VALUES("native_place", "#{nativePlace,jdbcType=VARCHAR}");
        }

        if (record.getCountry() != null) {
            sql.VALUES("country", "#{country,jdbcType=VARCHAR}");
        }

        if (record.getBirthPlace() != null) {
            sql.VALUES("birth_place", "#{birthPlace,jdbcType=VARCHAR}");
        }

        if (record.getBirthDate() != null) {
            sql.VALUES("birth_date", "#{birthDate,jdbcType=DATE}");
        }

        if (record.getNationalId() != null) {
            sql.VALUES("national_id", "#{nationalId,jdbcType=CHAR}");
        }

        if (record.getNationalType() != null) {
            sql.VALUES("national_type", "#{nationalType,jdbcType=VARCHAR}");
        }

        if (record.getMarryStatus() != null) {
            sql.VALUES("marry_status", "#{marryStatus,jdbcType=BIT}");
        }

        if (record.getPattyName() != null) {
            sql.VALUES("patty_name", "#{pattyName,jdbcType=VARCHAR}");
        }

        if (record.getPositionId() != null) {
            sql.VALUES("position_id", "#{positionId,jdbcType=CHAR}");
        }

        if (record.getPositionName() != null) {
            sql.VALUES("position_name", "#{positionName,jdbcType=VARCHAR}");
        }

        if (record.getOrganizationParentId() != null) {
            sql.VALUES("organization_parent_id", "#{organizationParentId,jdbcType=CHAR}");
        }

        if (record.getOrganizationParentName() != null) {
            sql.VALUES("organization_parent_name", "#{organizationParentName,jdbcType=VARCHAR}");
        }

        if (record.getOrganizationId() != null) {
            sql.VALUES("organization_id", "#{organizationId,jdbcType=CHAR}");
        }

        if (record.getOrganizationName() != null) {
            sql.VALUES("organization_name", "#{organizationName,jdbcType=VARCHAR}");
        }

        if (record.getSequenceId() != null) {
            sql.VALUES("sequence_id", "#{sequenceId,jdbcType=CHAR}");
        }

        if (record.getSequenceName() != null) {
            sql.VALUES("sequence_name", "#{sequenceName,jdbcType=VARCHAR}");
        }

        if (record.getSequenceSubId() != null) {
            sql.VALUES("sequence_sub_id", "#{sequenceSubId,jdbcType=CHAR}");
        }

        if (record.getSequenceSubName() != null) {
            sql.VALUES("sequence_sub_name", "#{sequenceSubName,jdbcType=VARCHAR}");
        }

        if (record.getPerformanceId() != null) {
            sql.VALUES("performance_id", "#{performanceId,jdbcType=CHAR}");
        }

        if (record.getPerformanceName() != null) {
            sql.VALUES("performance_name", "#{performanceName,jdbcType=VARCHAR}");
        }

        if (record.getAbilityId() != null) {
            sql.VALUES("ability_id", "#{abilityId,jdbcType=CHAR}");
        }

        if (record.getJobTitleId() != null) {
            sql.VALUES("job_title_id", "#{jobTitleId,jdbcType=CHAR}");
        }

        if (record.getAbilityLvId() != null) {
            sql.VALUES("ability_lv_id", "#{abilityLvId,jdbcType=CHAR}");
        }

        if (record.getAbilityName() != null) {
            sql.VALUES("ability_name", "#{abilityName,jdbcType=VARCHAR}");
        }

        if (record.getJobTitleName() != null) {
            sql.VALUES("job_title_name", "#{jobTitleName,jdbcType=VARCHAR}");
        }

        if (record.getAbilityLvName() != null) {
            sql.VALUES("ability_lv_name", "#{abilityLvName,jdbcType=VARCHAR}");
        }

        if (record.getRankName() != null) {
            sql.VALUES("rank_name", "#{rankName,jdbcType=VARCHAR}");
        }

        if (record.getPopulationId() != null) {
            sql.VALUES("population_id", "#{populationId,jdbcType=CHAR}");
        }

        if (record.getPopulationName() != null) {
            sql.VALUES("population_name", "#{populationName,jdbcType=VARCHAR}");
        }

        if (record.getAreaId() != null) {
            sql.VALUES("area_id", "#{areaId,jdbcType=CHAR}");
        }

        if (record.getRunOffDate() != null) {
            sql.VALUES("run_off_date", "#{runOffDate,jdbcType=DATE}");
        }

        if (record.getEntryDate() != null) {
            sql.VALUES("entry_date", "#{entryDate,jdbcType=TIMESTAMP}");
        }

        if (record.getTelPhone() != null) {
            sql.VALUES("tel_phone", "#{telPhone,jdbcType=VARCHAR}");
        }

        if (record.getQq() != null) {
            sql.VALUES("qq", "#{qq,jdbcType=VARCHAR}");
        }

        if (record.getWxCode() != null) {
            sql.VALUES("wx_code", "#{wxCode,jdbcType=VARCHAR}");
        }

        if (record.getAddress() != null) {
            sql.VALUES("address", "#{address,jdbcType=VARCHAR}");
        }

        if (record.getContractUnit() != null) {
            sql.VALUES("contract_unit", "#{contractUnit,jdbcType=VARCHAR}");
        }

        if (record.getWorkPlaceId() != null) {
            sql.VALUES("work_place_id", "#{workPlaceId,jdbcType=CHAR}");
        }

        if (record.getWorkPlace() != null) {
            sql.VALUES("work_place", "#{workPlace,jdbcType=VARCHAR}");
        }

        if (record.getRegularDate() != null) {
            sql.VALUES("regular_date", "#{regularDate,jdbcType=TIMESTAMP}");
        }

        if (record.getApplyType() != null) {
            sql.VALUES("apply_type", "#{applyType,jdbcType=VARCHAR}");
        }

        if (record.getChannelId() != null) {
            sql.VALUES("channel_id", "#{channelId,jdbcType=CHAR}");
        }

        if (record.getSpeciality() != null) {
            sql.VALUES("speciality", "#{speciality,jdbcType=VARCHAR}");
        }

        if (record.getIsRegular() != null) {
            sql.VALUES("is_regular", "#{isRegular,jdbcType=VARCHAR}");
        }

        if (record.getRefreshDate() != null) {
            sql.VALUES("refresh_date", "#{refreshDate,jdbcType=TIMESTAMP}");
        }

        if (record.getCId() != null) {
            sql.VALUES("c_id", "#{cId,jdbcType=VARCHAR}");
        }

        if (record.getMark() != null) {
            sql.VALUES("mark", "#{mark,jdbcType=TINYINT}");
        }

        if (record.getVDimEmpId() != null) {
            sql.VALUES("v_dim_emp_id", "#{vDimEmpId,jdbcType=CHAR}");
        }

        return sql.toString();
    }

    public String selectByExample(VDimEmpExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("emp_id");
        } else {
            sql.SELECT("emp_id");
        }
        sql.SELECT("customer_id");
        sql.SELECT("emp_key");
        sql.SELECT("user_name");
        sql.SELECT("user_name_ch");
        sql.SELECT("emp_hf_id");
        sql.SELECT("emp_hf_key");
        sql.SELECT("report_relation");
        sql.SELECT("email");
        sql.SELECT("img_path");
        sql.SELECT("passtime_or_fulltime");
        sql.SELECT("contract");
        sql.SELECT("blood");
        sql.SELECT("age");
        sql.SELECT("company_age");
        sql.SELECT("is_key_talent");
        sql.SELECT("sex");
        sql.SELECT("nation");
        sql.SELECT("degree_id");
        sql.SELECT("degree");
        sql.SELECT("native_place");
        sql.SELECT("country");
        sql.SELECT("birth_place");
        sql.SELECT("birth_date");
        sql.SELECT("national_id");
        sql.SELECT("national_type");
        sql.SELECT("marry_status");
        sql.SELECT("patty_name");
        sql.SELECT("position_id");
        sql.SELECT("position_name");
        sql.SELECT("organization_parent_id");
        sql.SELECT("organization_parent_name");
        sql.SELECT("organization_id");
        sql.SELECT("organization_name");
        sql.SELECT("sequence_id");
        sql.SELECT("sequence_name");
        sql.SELECT("sequence_sub_id");
        sql.SELECT("sequence_sub_name");
        sql.SELECT("performance_id");
        sql.SELECT("performance_name");
        sql.SELECT("ability_id");
        sql.SELECT("job_title_id");
        sql.SELECT("ability_lv_id");
        sql.SELECT("ability_name");
        sql.SELECT("job_title_name");
        sql.SELECT("ability_lv_name");
        sql.SELECT("rank_name");
        sql.SELECT("population_id");
        sql.SELECT("population_name");
        sql.SELECT("area_id");
        sql.SELECT("run_off_date");
        sql.SELECT("entry_date");
        sql.SELECT("tel_phone");
        sql.SELECT("qq");
        sql.SELECT("wx_code");
        sql.SELECT("address");
        sql.SELECT("contract_unit");
        sql.SELECT("work_place_id");
        sql.SELECT("work_place");
        sql.SELECT("regular_date");
        sql.SELECT("apply_type");
        sql.SELECT("channel_id");
        sql.SELECT("speciality");
        sql.SELECT("is_regular");
        sql.SELECT("refresh_date");
        sql.SELECT("c_id");
        sql.SELECT("mark");
        sql.SELECT("v_dim_emp_id");
        sql.FROM("v_dim_emp");
        applyWhere(sql, example, false);

        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }

        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        VDimEmp record = (VDimEmp) parameter.get("record");
        VDimEmpExample example = (VDimEmpExample) parameter.get("example");

        SQL sql = new SQL();
        sql.UPDATE("v_dim_emp");

        if (record.getEmpId() != null) {
            sql.SET("emp_id = #{record.empId,jdbcType=VARCHAR}");
        }

        if (record.getCustomerId() != null) {
            sql.SET("customer_id = #{record.customerId,jdbcType=CHAR}");
        }

        if (record.getEmpKey() != null) {
            sql.SET("emp_key = #{record.empKey,jdbcType=VARCHAR}");
        }

        if (record.getUserName() != null) {
            sql.SET("user_name = #{record.userName,jdbcType=VARCHAR}");
        }

        if (record.getUserNameCh() != null) {
            sql.SET("user_name_ch = #{record.userNameCh,jdbcType=VARCHAR}");
        }

        if (record.getEmpHfId() != null) {
            sql.SET("emp_hf_id = #{record.empHfId,jdbcType=CHAR}");
        }

        if (record.getEmpHfKey() != null) {
            sql.SET("emp_hf_key = #{record.empHfKey,jdbcType=VARCHAR}");
        }

        if (record.getReportRelation() != null) {
            sql.SET("report_relation = #{record.reportRelation,jdbcType=VARCHAR}");
        }

        if (record.getEmail() != null) {
            sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        }

        if (record.getImgPath() != null) {
            sql.SET("img_path = #{record.imgPath,jdbcType=VARCHAR}");
        }

        if (record.getPasstimeOrFulltime() != null) {
            sql.SET("passtime_or_fulltime = #{record.passtimeOrFulltime,jdbcType=VARCHAR}");
        }

        if (record.getContract() != null) {
            sql.SET("contract = #{record.contract,jdbcType=VARCHAR}");
        }

        if (record.getBlood() != null) {
            sql.SET("blood = #{record.blood,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.SET("age = #{record.age,jdbcType=DOUBLE}");
        }

        if (record.getCompanyAge() != null) {
            sql.SET("company_age = #{record.companyAge,jdbcType=DOUBLE}");
        }

        if (record.getIsKeyTalent() != null) {
            sql.SET("is_key_talent = #{record.isKeyTalent,jdbcType=BIT}");
        }

        if (record.getSex() != null) {
            sql.SET("sex = #{record.sex,jdbcType=CHAR}");
        }

        if (record.getNation() != null) {
            sql.SET("nation = #{record.nation,jdbcType=VARCHAR}");
        }

        if (record.getDegreeId() != null) {
            sql.SET("degree_id = #{record.degreeId,jdbcType=CHAR}");
        }

        if (record.getDegree() != null) {
            sql.SET("degree = #{record.degree,jdbcType=VARCHAR}");
        }

        if (record.getNativePlace() != null) {
            sql.SET("native_place = #{record.nativePlace,jdbcType=VARCHAR}");
        }

        if (record.getCountry() != null) {
            sql.SET("country = #{record.country,jdbcType=VARCHAR}");
        }

        if (record.getBirthPlace() != null) {
            sql.SET("birth_place = #{record.birthPlace,jdbcType=VARCHAR}");
        }

        if (record.getBirthDate() != null) {
            sql.SET("birth_date = #{record.birthDate,jdbcType=DATE}");
        }

        if (record.getNationalId() != null) {
            sql.SET("national_id = #{record.nationalId,jdbcType=CHAR}");
        }

        if (record.getNationalType() != null) {
            sql.SET("national_type = #{record.nationalType,jdbcType=VARCHAR}");
        }

        if (record.getMarryStatus() != null) {
            sql.SET("marry_status = #{record.marryStatus,jdbcType=BIT}");
        }

        if (record.getPattyName() != null) {
            sql.SET("patty_name = #{record.pattyName,jdbcType=VARCHAR}");
        }

        if (record.getPositionId() != null) {
            sql.SET("position_id = #{record.positionId,jdbcType=CHAR}");
        }

        if (record.getPositionName() != null) {
            sql.SET("position_name = #{record.positionName,jdbcType=VARCHAR}");
        }

        if (record.getOrganizationParentId() != null) {
            sql.SET("organization_parent_id = #{record.organizationParentId,jdbcType=CHAR}");
        }

        if (record.getOrganizationParentName() != null) {
            sql.SET("organization_parent_name = #{record.organizationParentName,jdbcType=VARCHAR}");
        }

        if (record.getOrganizationId() != null) {
            sql.SET("organization_id = #{record.organizationId,jdbcType=CHAR}");
        }

        if (record.getOrganizationName() != null) {
            sql.SET("organization_name = #{record.organizationName,jdbcType=VARCHAR}");
        }

        if (record.getSequenceId() != null) {
            sql.SET("sequence_id = #{record.sequenceId,jdbcType=CHAR}");
        }

        if (record.getSequenceName() != null) {
            sql.SET("sequence_name = #{record.sequenceName,jdbcType=VARCHAR}");
        }

        if (record.getSequenceSubId() != null) {
            sql.SET("sequence_sub_id = #{record.sequenceSubId,jdbcType=CHAR}");
        }

        if (record.getSequenceSubName() != null) {
            sql.SET("sequence_sub_name = #{record.sequenceSubName,jdbcType=VARCHAR}");
        }

        if (record.getPerformanceId() != null) {
            sql.SET("performance_id = #{record.performanceId,jdbcType=CHAR}");
        }

        if (record.getPerformanceName() != null) {
            sql.SET("performance_name = #{record.performanceName,jdbcType=VARCHAR}");
        }

        if (record.getAbilityId() != null) {
            sql.SET("ability_id = #{record.abilityId,jdbcType=CHAR}");
        }

        if (record.getJobTitleId() != null) {
            sql.SET("job_title_id = #{record.jobTitleId,jdbcType=CHAR}");
        }

        if (record.getAbilityLvId() != null) {
            sql.SET("ability_lv_id = #{record.abilityLvId,jdbcType=CHAR}");
        }

        if (record.getAbilityName() != null) {
            sql.SET("ability_name = #{record.abilityName,jdbcType=VARCHAR}");
        }

        if (record.getJobTitleName() != null) {
            sql.SET("job_title_name = #{record.jobTitleName,jdbcType=VARCHAR}");
        }

        if (record.getAbilityLvName() != null) {
            sql.SET("ability_lv_name = #{record.abilityLvName,jdbcType=VARCHAR}");
        }

        if (record.getRankName() != null) {
            sql.SET("rank_name = #{record.rankName,jdbcType=VARCHAR}");
        }

        if (record.getPopulationId() != null) {
            sql.SET("population_id = #{record.populationId,jdbcType=CHAR}");
        }

        if (record.getPopulationName() != null) {
            sql.SET("population_name = #{record.populationName,jdbcType=VARCHAR}");
        }

        if (record.getAreaId() != null) {
            sql.SET("area_id = #{record.areaId,jdbcType=CHAR}");
        }

        if (record.getRunOffDate() != null) {
            sql.SET("run_off_date = #{record.runOffDate,jdbcType=DATE}");
        }

        if (record.getEntryDate() != null) {
            sql.SET("entry_date = #{record.entryDate,jdbcType=TIMESTAMP}");
        }

        if (record.getTelPhone() != null) {
            sql.SET("tel_phone = #{record.telPhone,jdbcType=VARCHAR}");
        }

        if (record.getQq() != null) {
            sql.SET("qq = #{record.qq,jdbcType=VARCHAR}");
        }

        if (record.getWxCode() != null) {
            sql.SET("wx_code = #{record.wxCode,jdbcType=VARCHAR}");
        }

        if (record.getAddress() != null) {
            sql.SET("address = #{record.address,jdbcType=VARCHAR}");
        }

        if (record.getContractUnit() != null) {
            sql.SET("contract_unit = #{record.contractUnit,jdbcType=VARCHAR}");
        }

        if (record.getWorkPlaceId() != null) {
            sql.SET("work_place_id = #{record.workPlaceId,jdbcType=CHAR}");
        }

        if (record.getWorkPlace() != null) {
            sql.SET("work_place = #{record.workPlace,jdbcType=VARCHAR}");
        }

        if (record.getRegularDate() != null) {
            sql.SET("regular_date = #{record.regularDate,jdbcType=TIMESTAMP}");
        }

        if (record.getApplyType() != null) {
            sql.SET("apply_type = #{record.applyType,jdbcType=VARCHAR}");
        }

        if (record.getChannelId() != null) {
            sql.SET("channel_id = #{record.channelId,jdbcType=CHAR}");
        }

        if (record.getSpeciality() != null) {
            sql.SET("speciality = #{record.speciality,jdbcType=VARCHAR}");
        }

        if (record.getIsRegular() != null) {
            sql.SET("is_regular = #{record.isRegular,jdbcType=VARCHAR}");
        }

        if (record.getRefreshDate() != null) {
            sql.SET("refresh_date = #{record.refreshDate,jdbcType=TIMESTAMP}");
        }

        if (record.getCId() != null) {
            sql.SET("c_id = #{record.cId,jdbcType=VARCHAR}");
        }

        if (record.getMark() != null) {
            sql.SET("mark = #{record.mark,jdbcType=TINYINT}");
        }

        if (record.getVDimEmpId() != null) {
            sql.SET("v_dim_emp_id = #{record.vDimEmpId,jdbcType=CHAR}");
        }

        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("v_dim_emp");

        sql.SET("emp_id = #{record.empId,jdbcType=VARCHAR}");
        sql.SET("customer_id = #{record.customerId,jdbcType=CHAR}");
        sql.SET("emp_key = #{record.empKey,jdbcType=VARCHAR}");
        sql.SET("user_name = #{record.userName,jdbcType=VARCHAR}");
        sql.SET("user_name_ch = #{record.userNameCh,jdbcType=VARCHAR}");
        sql.SET("emp_hf_id = #{record.empHfId,jdbcType=CHAR}");
        sql.SET("emp_hf_key = #{record.empHfKey,jdbcType=VARCHAR}");
        sql.SET("report_relation = #{record.reportRelation,jdbcType=VARCHAR}");
        sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        sql.SET("img_path = #{record.imgPath,jdbcType=VARCHAR}");
        sql.SET("passtime_or_fulltime = #{record.passtimeOrFulltime,jdbcType=VARCHAR}");
        sql.SET("contract = #{record.contract,jdbcType=VARCHAR}");
        sql.SET("blood = #{record.blood,jdbcType=VARCHAR}");
        sql.SET("age = #{record.age,jdbcType=DOUBLE}");
        sql.SET("company_age = #{record.companyAge,jdbcType=DOUBLE}");
        sql.SET("is_key_talent = #{record.isKeyTalent,jdbcType=BIT}");
        sql.SET("sex = #{record.sex,jdbcType=CHAR}");
        sql.SET("nation = #{record.nation,jdbcType=VARCHAR}");
        sql.SET("degree_id = #{record.degreeId,jdbcType=CHAR}");
        sql.SET("degree = #{record.degree,jdbcType=VARCHAR}");
        sql.SET("native_place = #{record.nativePlace,jdbcType=VARCHAR}");
        sql.SET("country = #{record.country,jdbcType=VARCHAR}");
        sql.SET("birth_place = #{record.birthPlace,jdbcType=VARCHAR}");
        sql.SET("birth_date = #{record.birthDate,jdbcType=DATE}");
        sql.SET("national_id = #{record.nationalId,jdbcType=CHAR}");
        sql.SET("national_type = #{record.nationalType,jdbcType=VARCHAR}");
        sql.SET("marry_status = #{record.marryStatus,jdbcType=BIT}");
        sql.SET("patty_name = #{record.pattyName,jdbcType=VARCHAR}");
        sql.SET("position_id = #{record.positionId,jdbcType=CHAR}");
        sql.SET("position_name = #{record.positionName,jdbcType=VARCHAR}");
        sql.SET("organization_parent_id = #{record.organizationParentId,jdbcType=CHAR}");
        sql.SET("organization_parent_name = #{record.organizationParentName,jdbcType=VARCHAR}");
        sql.SET("organization_id = #{record.organizationId,jdbcType=CHAR}");
        sql.SET("organization_name = #{record.organizationName,jdbcType=VARCHAR}");
        sql.SET("sequence_id = #{record.sequenceId,jdbcType=CHAR}");
        sql.SET("sequence_name = #{record.sequenceName,jdbcType=VARCHAR}");
        sql.SET("sequence_sub_id = #{record.sequenceSubId,jdbcType=CHAR}");
        sql.SET("sequence_sub_name = #{record.sequenceSubName,jdbcType=VARCHAR}");
        sql.SET("performance_id = #{record.performanceId,jdbcType=CHAR}");
        sql.SET("performance_name = #{record.performanceName,jdbcType=VARCHAR}");
        sql.SET("ability_id = #{record.abilityId,jdbcType=CHAR}");
        sql.SET("job_title_id = #{record.jobTitleId,jdbcType=CHAR}");
        sql.SET("ability_lv_id = #{record.abilityLvId,jdbcType=CHAR}");
        sql.SET("ability_name = #{record.abilityName,jdbcType=VARCHAR}");
        sql.SET("job_title_name = #{record.jobTitleName,jdbcType=VARCHAR}");
        sql.SET("ability_lv_name = #{record.abilityLvName,jdbcType=VARCHAR}");
        sql.SET("rank_name = #{record.rankName,jdbcType=VARCHAR}");
        sql.SET("population_id = #{record.populationId,jdbcType=CHAR}");
        sql.SET("population_name = #{record.populationName,jdbcType=VARCHAR}");
        sql.SET("area_id = #{record.areaId,jdbcType=CHAR}");
        sql.SET("run_off_date = #{record.runOffDate,jdbcType=DATE}");
        sql.SET("entry_date = #{record.entryDate,jdbcType=TIMESTAMP}");
        sql.SET("tel_phone = #{record.telPhone,jdbcType=VARCHAR}");
        sql.SET("qq = #{record.qq,jdbcType=VARCHAR}");
        sql.SET("wx_code = #{record.wxCode,jdbcType=VARCHAR}");
        sql.SET("address = #{record.address,jdbcType=VARCHAR}");
        sql.SET("contract_unit = #{record.contractUnit,jdbcType=VARCHAR}");
        sql.SET("work_place_id = #{record.workPlaceId,jdbcType=CHAR}");
        sql.SET("work_place = #{record.workPlace,jdbcType=VARCHAR}");
        sql.SET("regular_date = #{record.regularDate,jdbcType=TIMESTAMP}");
        sql.SET("apply_type = #{record.applyType,jdbcType=VARCHAR}");
        sql.SET("channel_id = #{record.channelId,jdbcType=CHAR}");
        sql.SET("speciality = #{record.speciality,jdbcType=VARCHAR}");
        sql.SET("is_regular = #{record.isRegular,jdbcType=VARCHAR}");
        sql.SET("refresh_date = #{record.refreshDate,jdbcType=TIMESTAMP}");
        sql.SET("c_id = #{record.cId,jdbcType=VARCHAR}");
        sql.SET("mark = #{record.mark,jdbcType=TINYINT}");
        sql.SET("v_dim_emp_id = #{record.vDimEmpId,jdbcType=CHAR}");

        VDimEmpExample example = (VDimEmpExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(VDimEmp record) {
        SQL sql = new SQL();
        sql.UPDATE("v_dim_emp");

        if (record.getCustomerId() != null) {
            sql.SET("customer_id = #{customerId,jdbcType=CHAR}");
        }

        if (record.getEmpKey() != null) {
            sql.SET("emp_key = #{empKey,jdbcType=VARCHAR}");
        }

        if (record.getUserName() != null) {
            sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
        }

        if (record.getUserNameCh() != null) {
            sql.SET("user_name_ch = #{userNameCh,jdbcType=VARCHAR}");
        }

        if (record.getEmpHfId() != null) {
            sql.SET("emp_hf_id = #{empHfId,jdbcType=CHAR}");
        }

        if (record.getEmpHfKey() != null) {
            sql.SET("emp_hf_key = #{empHfKey,jdbcType=VARCHAR}");
        }

        if (record.getReportRelation() != null) {
            sql.SET("report_relation = #{reportRelation,jdbcType=VARCHAR}");
        }

        if (record.getEmail() != null) {
            sql.SET("email = #{email,jdbcType=VARCHAR}");
        }

        if (record.getImgPath() != null) {
            sql.SET("img_path = #{imgPath,jdbcType=VARCHAR}");
        }

        if (record.getPasstimeOrFulltime() != null) {
            sql.SET("passtime_or_fulltime = #{passtimeOrFulltime,jdbcType=VARCHAR}");
        }

        if (record.getContract() != null) {
            sql.SET("contract = #{contract,jdbcType=VARCHAR}");
        }

        if (record.getBlood() != null) {
            sql.SET("blood = #{blood,jdbcType=VARCHAR}");
        }

        if (record.getAge() != null) {
            sql.SET("age = #{age,jdbcType=DOUBLE}");
        }

        if (record.getCompanyAge() != null) {
            sql.SET("company_age = #{companyAge,jdbcType=DOUBLE}");
        }

        if (record.getIsKeyTalent() != null) {
            sql.SET("is_key_talent = #{isKeyTalent,jdbcType=BIT}");
        }

        if (record.getSex() != null) {
            sql.SET("sex = #{sex,jdbcType=CHAR}");
        }

        if (record.getNation() != null) {
            sql.SET("nation = #{nation,jdbcType=VARCHAR}");
        }

        if (record.getDegreeId() != null) {
            sql.SET("degree_id = #{degreeId,jdbcType=CHAR}");
        }

        if (record.getDegree() != null) {
            sql.SET("degree = #{degree,jdbcType=VARCHAR}");
        }

        if (record.getNativePlace() != null) {
            sql.SET("native_place = #{nativePlace,jdbcType=VARCHAR}");
        }

        if (record.getCountry() != null) {
            sql.SET("country = #{country,jdbcType=VARCHAR}");
        }

        if (record.getBirthPlace() != null) {
            sql.SET("birth_place = #{birthPlace,jdbcType=VARCHAR}");
        }

        if (record.getBirthDate() != null) {
            sql.SET("birth_date = #{birthDate,jdbcType=DATE}");
        }

        if (record.getNationalId() != null) {
            sql.SET("national_id = #{nationalId,jdbcType=CHAR}");
        }

        if (record.getNationalType() != null) {
            sql.SET("national_type = #{nationalType,jdbcType=VARCHAR}");
        }

        if (record.getMarryStatus() != null) {
            sql.SET("marry_status = #{marryStatus,jdbcType=BIT}");
        }

        if (record.getPattyName() != null) {
            sql.SET("patty_name = #{pattyName,jdbcType=VARCHAR}");
        }

        if (record.getPositionId() != null) {
            sql.SET("position_id = #{positionId,jdbcType=CHAR}");
        }

        if (record.getPositionName() != null) {
            sql.SET("position_name = #{positionName,jdbcType=VARCHAR}");
        }

        if (record.getOrganizationParentId() != null) {
            sql.SET("organization_parent_id = #{organizationParentId,jdbcType=CHAR}");
        }

        if (record.getOrganizationParentName() != null) {
            sql.SET("organization_parent_name = #{organizationParentName,jdbcType=VARCHAR}");
        }

        if (record.getOrganizationId() != null) {
            sql.SET("organization_id = #{organizationId,jdbcType=CHAR}");
        }

        if (record.getOrganizationName() != null) {
            sql.SET("organization_name = #{organizationName,jdbcType=VARCHAR}");
        }

        if (record.getSequenceId() != null) {
            sql.SET("sequence_id = #{sequenceId,jdbcType=CHAR}");
        }

        if (record.getSequenceName() != null) {
            sql.SET("sequence_name = #{sequenceName,jdbcType=VARCHAR}");
        }

        if (record.getSequenceSubId() != null) {
            sql.SET("sequence_sub_id = #{sequenceSubId,jdbcType=CHAR}");
        }

        if (record.getSequenceSubName() != null) {
            sql.SET("sequence_sub_name = #{sequenceSubName,jdbcType=VARCHAR}");
        }

        if (record.getPerformanceId() != null) {
            sql.SET("performance_id = #{performanceId,jdbcType=CHAR}");
        }

        if (record.getPerformanceName() != null) {
            sql.SET("performance_name = #{performanceName,jdbcType=VARCHAR}");
        }

        if (record.getAbilityId() != null) {
            sql.SET("ability_id = #{abilityId,jdbcType=CHAR}");
        }

        if (record.getJobTitleId() != null) {
            sql.SET("job_title_id = #{jobTitleId,jdbcType=CHAR}");
        }

        if (record.getAbilityLvId() != null) {
            sql.SET("ability_lv_id = #{abilityLvId,jdbcType=CHAR}");
        }

        if (record.getAbilityName() != null) {
            sql.SET("ability_name = #{abilityName,jdbcType=VARCHAR}");
        }

        if (record.getJobTitleName() != null) {
            sql.SET("job_title_name = #{jobTitleName,jdbcType=VARCHAR}");
        }

        if (record.getAbilityLvName() != null) {
            sql.SET("ability_lv_name = #{abilityLvName,jdbcType=VARCHAR}");
        }

        if (record.getRankName() != null) {
            sql.SET("rank_name = #{rankName,jdbcType=VARCHAR}");
        }

        if (record.getPopulationId() != null) {
            sql.SET("population_id = #{populationId,jdbcType=CHAR}");
        }

        if (record.getPopulationName() != null) {
            sql.SET("population_name = #{populationName,jdbcType=VARCHAR}");
        }

        if (record.getAreaId() != null) {
            sql.SET("area_id = #{areaId,jdbcType=CHAR}");
        }

        if (record.getRunOffDate() != null) {
            sql.SET("run_off_date = #{runOffDate,jdbcType=DATE}");
        }

        if (record.getEntryDate() != null) {
            sql.SET("entry_date = #{entryDate,jdbcType=TIMESTAMP}");
        }

        if (record.getTelPhone() != null) {
            sql.SET("tel_phone = #{telPhone,jdbcType=VARCHAR}");
        }

        if (record.getQq() != null) {
            sql.SET("qq = #{qq,jdbcType=VARCHAR}");
        }

        if (record.getWxCode() != null) {
            sql.SET("wx_code = #{wxCode,jdbcType=VARCHAR}");
        }

        if (record.getAddress() != null) {
            sql.SET("address = #{address,jdbcType=VARCHAR}");
        }

        if (record.getContractUnit() != null) {
            sql.SET("contract_unit = #{contractUnit,jdbcType=VARCHAR}");
        }

        if (record.getWorkPlaceId() != null) {
            sql.SET("work_place_id = #{workPlaceId,jdbcType=CHAR}");
        }

        if (record.getWorkPlace() != null) {
            sql.SET("work_place = #{workPlace,jdbcType=VARCHAR}");
        }

        if (record.getRegularDate() != null) {
            sql.SET("regular_date = #{regularDate,jdbcType=TIMESTAMP}");
        }

        if (record.getApplyType() != null) {
            sql.SET("apply_type = #{applyType,jdbcType=VARCHAR}");
        }

        if (record.getChannelId() != null) {
            sql.SET("channel_id = #{channelId,jdbcType=CHAR}");
        }

        if (record.getSpeciality() != null) {
            sql.SET("speciality = #{speciality,jdbcType=VARCHAR}");
        }

        if (record.getIsRegular() != null) {
            sql.SET("is_regular = #{isRegular,jdbcType=VARCHAR}");
        }

        if (record.getRefreshDate() != null) {
            sql.SET("refresh_date = #{refreshDate,jdbcType=TIMESTAMP}");
        }

        if (record.getCId() != null) {
            sql.SET("c_id = #{cId,jdbcType=VARCHAR}");
        }

        if (record.getMark() != null) {
            sql.SET("mark = #{mark,jdbcType=TINYINT}");
        }

        if (record.getVDimEmpId() != null) {
            sql.SET("v_dim_emp_id = #{vDimEmpId,jdbcType=CHAR}");
        }

        sql.WHERE("emp_id = #{empId,jdbcType=VARCHAR}");

        return sql.toString();
    }

    protected void applyWhere(SQL sql, VDimEmpExample example, boolean includeExamplePhrase) {
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
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j, criterion.getTypeHandler()));
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