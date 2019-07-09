/*
Navicat MySQL Data Transfer

Source Server         : 广州
Source Server Version : 50505
Source Host           : 172.16.9.50:3369
Source Database       : mup-source

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-05-25 16:33:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for source_360_report
-- ----------------------------
DROP TABLE IF EXISTS `source_360_report`;
CREATE TABLE `source_360_report` (
  `360_report_id` varchar(32) NOT NULL COMMENT '360测评报告ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `360_ability_id` varchar(32) DEFAULT NULL COMMENT '能力维度ID（字典表同步）',
  `360_ability_name` varchar(20) DEFAULT NULL COMMENT '能力维度名称（字典表同步）',
  `360_ability_lv_id` varchar(32) DEFAULT NULL COMMENT '能力层级ID（字典表同步）',
  `360_ability_lv_name` varchar(5) DEFAULT NULL COMMENT '能力层级名称（字典表同步）',
  `standard_score` double(5,2) DEFAULT NULL COMMENT '标准分',
  `gain_score` double(5,2) DEFAULT NULL COMMENT '取得分',
  `score` double(5,4) DEFAULT NULL COMMENT '成绩（取得分/标准分）',
  `module_type` int(1) DEFAULT NULL COMMENT '模块（1 想，2 做， 3 带）',
  `360_time_id` varchar(32) DEFAULT NULL COMMENT '360测评时段ID',
  PRIMARY KEY (`360_report_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-360测评报告（source_360_report）';

-- ----------------------------
-- Table structure for source_360_time
-- ----------------------------
DROP TABLE IF EXISTS `source_360_time`;
CREATE TABLE `source_360_time` (
  `360_time_id` varchar(32) NOT NULL COMMENT '360测评时段ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `report_date` datetime DEFAULT NULL COMMENT '分配测评时间',
  `report_name` varchar(20) DEFAULT NULL COMMENT '测试报告名称',
  `path` varchar(100) DEFAULT NULL COMMENT '路径',
  `type` int(1) DEFAULT NULL COMMENT '类别（1:360测评 0：其它测试）',
  `year` int(4) DEFAULT NULL COMMENT '公报时间（周期：年度、半年度）',
  PRIMARY KEY (`360_time_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-360测评时段（source_360_time）';

-- ----------------------------
-- Table structure for source_ability_change
-- ----------------------------
DROP TABLE IF EXISTS `source_ability_change`;
CREATE TABLE `source_ability_change` (
  `ability_change_id` varchar(32) NOT NULL COMMENT '员工能力ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `user_name_ch` varchar(50) DEFAULT NULL COMMENT '员工名称',
  `full_path` varchar(200) DEFAULT NULL COMMENT '员工机构全路径',
  `organization_parent_id` varchar(32) DEFAULT NULL COMMENT '父机构ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `sequence_id` varchar(32) DEFAULT NULL COMMENT '主序列',
  `sequence_sub_id` varchar(32) DEFAULT NULL COMMENT '子序列',
  `ability_id` varchar(32) DEFAULT NULL COMMENT '职位层级ID',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(3) DEFAULT NULL COMMENT '性别',
  `degree_id` varchar(32) DEFAULT NULL COMMENT '学历ID',
  `ability_number_id` varchar(32) DEFAULT NULL COMMENT '能力编码',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `year_months` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`ability_change_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='员工能力记录(`mup-source`.source_ability_change)';

-- ----------------------------
-- Table structure for source_budget_emp_number
-- ----------------------------
DROP TABLE IF EXISTS `source_budget_emp_number`;
CREATE TABLE `source_budget_emp_number` (
  `budget_emp_number_id` varchar(32) NOT NULL COMMENT '编制员工人数ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `number` int(4) DEFAULT NULL COMMENT '编制数',
  `year` int(4) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`budget_emp_number_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_budget_position_number
-- ----------------------------
DROP TABLE IF EXISTS `source_budget_position_number`;
CREATE TABLE `source_budget_position_number` (
  `budget_position_number_id` varchar(32) NOT NULL COMMENT '岗位编制数ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `position_id` varchar(32) DEFAULT NULL COMMENT '岗位ID',
  `number` int(4) DEFAULT NULL COMMENT '编制数',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`budget_position_number_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-岗位编制数（source_budget_position_number）';

-- ----------------------------
-- Table structure for source_course_record
-- ----------------------------
DROP TABLE IF EXISTS `source_course_record`;
CREATE TABLE `source_course_record` (
  `course_record_id` varchar(32) NOT NULL COMMENT '课程安排记录ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `course_id` varchar(32) DEFAULT NULL COMMENT '课程ID',
  `start_date` datetime DEFAULT NULL COMMENT '培训时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（0进行中，1已完成）',
  `train_unit` varchar(20) DEFAULT NULL COMMENT '主办方名称',
  `train_plan_id` varchar(32) DEFAULT NULL COMMENT '培训计划ID',
  `year` int(4) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`course_record_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-课程安排记录(source_course_record)';

-- ----------------------------
-- Table structure for source_dedicat_genre_score
-- ----------------------------
DROP TABLE IF EXISTS `source_dedicat_genre_score`;
CREATE TABLE `source_dedicat_genre_score` (
  `dedicat_score_id` varchar(32) NOT NULL COMMENT '敬业度评分ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `dedicat_genre_id` varchar(32) DEFAULT NULL COMMENT '敬业度分类ID',
  `soure` double(5,4) DEFAULT NULL COMMENT '得分',
  `date` date DEFAULT NULL COMMENT '报告日期',
  PRIMARY KEY (`dedicat_score_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-敬业度评分（source_dedicat_genre_score）';

-- ----------------------------
-- Table structure for source_dedicat_organ
-- ----------------------------
DROP TABLE IF EXISTS `source_dedicat_organ`;
CREATE TABLE `source_dedicat_organ` (
  `dedicat_organ_id` varchar(32) NOT NULL COMMENT '敬业机构评分ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `organization_name` varchar(20) DEFAULT NULL COMMENT '机构名称',
  `soure` double(4,4) DEFAULT NULL COMMENT '得分',
  `date` date DEFAULT NULL COMMENT '报告日期',
  PRIMARY KEY (`dedicat_organ_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-敬业度机构评分（source_dedicat_organ）';

-- ----------------------------
-- Table structure for source_dept_kpi
-- ----------------------------
DROP TABLE IF EXISTS `source_dept_kpi`;
CREATE TABLE `source_dept_kpi` (
  `dept_per_exam_relation_id` varchar(32) NOT NULL COMMENT '部门绩效目标ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `kpi_value` double(6,4) DEFAULT NULL COMMENT 'KPI达标率（范围：0-1）',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_per_exam_relation_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-部门绩效达标率（source_dept_kpi）';

-- ----------------------------
-- Table structure for source_dimission_risk
-- ----------------------------
DROP TABLE IF EXISTS `source_dimission_risk`;
CREATE TABLE `source_dimission_risk` (
  `dimission_risk_id` varchar(32) NOT NULL,
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL,
  `note` text,
  `risk_date` datetime DEFAULT NULL COMMENT '生效日期',
  `risk_flag` int(1) DEFAULT NULL COMMENT '1：红\r\n            2：黄\r\n            3：绿',
  `is_last` int(1) DEFAULT NULL COMMENT '是否最近一次的离职风险',
  PRIMARY KEY (`dimission_risk_id`),
  KEY `index_eId` (`emp_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_dimission_risk_item
-- ----------------------------
DROP TABLE IF EXISTS `source_dimission_risk_item`;
CREATE TABLE `source_dimission_risk_item` (
  `dimission_risk_item_id` varchar(32) NOT NULL,
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `dimission_risk_id` varchar(32) DEFAULT NULL,
  `separation_risk_id` varchar(32) DEFAULT NULL,
  `risk_flag` int(1) DEFAULT NULL COMMENT '1：红\r\n            2：黄\r\n            3：绿',
  PRIMARY KEY (`dimission_risk_item_id`),
  KEY `FK_Reference_28` (`dimission_risk_id`) USING BTREE,
  KEY `FK_Reference_29` (`separation_risk_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_dim_ability
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_ability`;
CREATE TABLE `source_dim_ability` (
  `ability_id` varchar(32) NOT NULL COMMENT '能力层级ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `ability_key` varchar(20) DEFAULT NULL COMMENT '能力编号',
  `ability_name` varchar(60) DEFAULT NULL COMMENT '能力层级名称',
  `curt_name` varchar(1) DEFAULT NULL COMMENT '短名',
  `type` tinyint(1) DEFAULT NULL COMMENT '序列类型',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ability_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-职位层级维(`mup-source`.source_dim_ability)';

-- ----------------------------
-- Table structure for source_dim_ability_lv
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_ability_lv`;
CREATE TABLE `source_dim_ability_lv` (
  `ability_lv_id` varchar(32) NOT NULL COMMENT '职级ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `ability_lv_key` varchar(20) DEFAULT NULL COMMENT '职级编号',
  `ability_lv_name` varchar(60) DEFAULT NULL COMMENT '职级名称',
  `curt_name` varchar(1) DEFAULT NULL COMMENT '短名',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ability_lv_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-职级维（`mup-source`.source_dim_ability_lv）';

-- ----------------------------
-- Table structure for source_dim_ability_number
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_ability_number`;
CREATE TABLE `source_dim_ability_number` (
  `ability_number_id` varchar(32) NOT NULL COMMENT '能力ID',
  `ability_number_key` varchar(20) DEFAULT NULL COMMENT '能力编码',
  `ability_number_name` varchar(20) DEFAULT NULL COMMENT '能力名称',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `create_date` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ability_number_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='员工能力记录(source_dim_ability_number)';

-- ----------------------------
-- Table structure for source_dim_certificate_info
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_certificate_info`;
CREATE TABLE `source_dim_certificate_info` (
  `certificate_info_id` varchar(32) NOT NULL COMMENT '证书信息ID',
  `certificate_name` varchar(100) DEFAULT NULL COMMENT '证书名称',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `certificate_type_id` varchar(32) DEFAULT NULL COMMENT '证书类型ID',
  `curt_name` tinyint(2) DEFAULT NULL COMMENT '短名',
  `show_index` tinyint(1) DEFAULT NULL COMMENT '排名',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`certificate_info_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='证书信息（source_dim_certificate_info）';

-- ----------------------------
-- Table structure for source_dim_change_type
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_change_type`;
CREATE TABLE `source_dim_change_type` (
  `change_type_id` varchar(32) NOT NULL COMMENT '异动类型ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `change_type_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `curt_name` char(1) DEFAULT NULL COMMENT '简称',
  `show_index` tinyint(3) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`change_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-异动类型维（`mup-source`.source_dim_change_type）';

-- ----------------------------
-- Table structure for source_dim_channel
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_channel`;
CREATE TABLE `source_dim_channel` (
  `channel_id` varchar(32) NOT NULL COMMENT '应聘渠道ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `channel_key` varchar(20) DEFAULT NULL COMMENT '招聘渠道编号',
  `channel_name` varchar(20) DEFAULT NULL COMMENT '招聘渠道名称',
  `show_index` tinyint(3) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`channel_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-招聘渠道维（`mup-source`.source_dim_channel）';

-- ----------------------------
-- Table structure for source_dim_checkwork_type
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_checkwork_type`;
CREATE TABLE `source_dim_checkwork_type` (
  `checkwork_type_id` varchar(32) NOT NULL COMMENT '考勤类型ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `checkwork_type_name` varchar(10) DEFAULT NULL COMMENT '考勤类型名称',
  `curt_name` tinyint(1) DEFAULT NULL COMMENT '短名称',
  `show_index` tinyint(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`checkwork_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-考勤类型（`mup-source`.source_dim_checkwork_type）';

-- ----------------------------
-- Table structure for source_dim_city
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_city`;
CREATE TABLE `source_dim_city` (
  `city_id` varchar(32) NOT NULL COMMENT '城市ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `city_key` varchar(20) DEFAULT NULL COMMENT '城市编码',
  `city_name` varchar(20) DEFAULT NULL COMMENT '城市名称',
  `province_id` varchar(32) DEFAULT NULL COMMENT '省ID',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`city_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-城市（`mup-source`.source_dim_city）';

-- ----------------------------
-- Table structure for source_dim_course
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_course`;
CREATE TABLE `source_dim_course` (
  `course_id` varchar(32) NOT NULL COMMENT '课程维ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `course_key` varchar(20) DEFAULT NULL COMMENT '课程编号',
  `course_name` varchar(20) DEFAULT NULL COMMENT '课程名称',
  `course_type_id` varchar(32) DEFAULT NULL COMMENT '课程类别ID',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`course_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-课程类型(`mup-source`.source_dim_course_type)';

-- ----------------------------
-- Table structure for source_dim_course_type
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_course_type`;
CREATE TABLE `source_dim_course_type` (
  `course_type_id` char(32) NOT NULL,
  `customer_id` char(32) DEFAULT NULL,
  `course_type_key` varchar(20) DEFAULT NULL,
  `course_type_name` varchar(20) DEFAULT NULL,
  `show_index` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`course_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_dim_dedicat_genre
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_dedicat_genre`;
CREATE TABLE `source_dim_dedicat_genre` (
  `dedicat_genre_id` varchar(32) NOT NULL COMMENT '敬业度ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `dedicat_name` varchar(100) DEFAULT NULL COMMENT '名称',
  `dedicat_genre_parent_id` varchar(32) DEFAULT NULL COMMENT '上级ID',
  `is_children` tinyint(1) DEFAULT NULL COMMENT '是否有子节点',
  `create_date` date DEFAULT NULL COMMENT '创建日期',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`dedicat_genre_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-敬业度分类（`mup-source`.source_dim_dedicat_genre）';

-- ----------------------------
-- Table structure for source_dim_dismission_week
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_dismission_week`;
CREATE TABLE `source_dim_dismission_week` (
  `dismission_week_id` varchar(32) NOT NULL COMMENT '离职周期范围维ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `name` varchar(10) DEFAULT NULL COMMENT '周期名称',
  `days` tinyint(4) DEFAULT NULL COMMENT '天数',
  `type` tinyint(1) DEFAULT NULL COMMENT '类型',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dismission_week_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_dim_encourages
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_encourages`;
CREATE TABLE `source_dim_encourages` (
  `encourages_id` varchar(32) NOT NULL COMMENT '激励要素ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `encourages_key` varchar(20) DEFAULT NULL COMMENT '激励要素编码',
  `content` varchar(20) DEFAULT NULL COMMENT '激励要素内容',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `c_id` varchar(32) DEFAULT NULL COMMENT '标识ID',
  PRIMARY KEY (`encourages_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-激励要素表(`mup-source`.source_dim_encourages)';

-- ----------------------------
-- Table structure for source_dim_job_title
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_job_title`;
CREATE TABLE `source_dim_job_title` (
  `job_title_id` varchar(32) NOT NULL COMMENT '职衔ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `job_title_key` varchar(20) NOT NULL COMMENT '职衔编号',
  `job_title_name` varchar(20) DEFAULT NULL COMMENT '职衔名称',
  `curt_name` varchar(2) DEFAULT NULL COMMENT '短名',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`job_title_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-职衔（`mup-source`.source_dim_job_title）';

-- ----------------------------
-- Table structure for source_dim_key_talent_type
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_key_talent_type`;
CREATE TABLE `source_dim_key_talent_type` (
  `key_talent_type_id` varchar(32) NOT NULL COMMENT '关键人才',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `key_talent_type_key` varchar(20) DEFAULT NULL COMMENT '关键人才类型编号',
  `key_talent_type_name` varchar(60) DEFAULT NULL COMMENT '关键人才名称',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`key_talent_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-关键人才库维(`mup-source`.source_dim_key_talent_type)';

-- ----------------------------
-- Table structure for source_dim_organization
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_organization`;
CREATE TABLE `source_dim_organization` (
  `id` char(32) NOT NULL,
  `customer_id` char(32) DEFAULT NULL,
  `organization_company_id` char(32) DEFAULT NULL,
  `organization_type_id` varchar(32) DEFAULT NULL,
  `organization_id` varchar(32) DEFAULT NULL,
  `organization_parent_id` varchar(32) DEFAULT NULL,
  `organization_name` varchar(50) DEFAULT NULL,
  `organization_name_full` varchar(50) DEFAULT NULL,
  `is_single` tinyint(1) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `profession_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-机构(`mup-source`.source_dim_organization)';

-- ----------------------------
-- Table structure for source_dim_organization_type
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_organization_type`;
CREATE TABLE `source_dim_organization_type` (
  `organization_type_id` varchar(32) NOT NULL COMMENT '机构级别ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_type_key` varchar(20) DEFAULT NULL COMMENT '机构级别编码',
  `organization_type_level` int(2) DEFAULT NULL COMMENT '机构级别层级',
  `organization_type_name` varchar(20) DEFAULT NULL COMMENT '机构级别名称',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`organization_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-机构级别维（`mup-source`.source_dim_organization_type）';

-- ----------------------------
-- Table structure for source_dim_performance
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_performance`;
CREATE TABLE `source_dim_performance` (
  `performance_id` varchar(32) NOT NULL COMMENT '绩效ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `performance_key` varchar(20) DEFAULT NULL COMMENT '绩效编号',
  `performance_name` varchar(60) DEFAULT NULL COMMENT '绩效名称',
  `curt_name` tinyint(1) DEFAULT NULL COMMENT '短名',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`performance_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-绩效维(`mup-source`.source_dim_performance)';

-- ----------------------------
-- Table structure for source_dim_population
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_population`;
CREATE TABLE `source_dim_population` (
  `population_id` varchar(32) NOT NULL COMMENT '人群范围ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `population_key` varchar(20) DEFAULT NULL COMMENT '人群范围编码',
  `population_name` varchar(20) DEFAULT NULL COMMENT '人群范围名称',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`population_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-人群范围维（`mup-source`.source_dim_population）';

-- ----------------------------
-- Table structure for source_dim_position
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_position`;
CREATE TABLE `source_dim_position` (
  `position_id` varchar(32) NOT NULL COMMENT '岗位ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `position_key` varchar(20) DEFAULT NULL COMMENT '岗位编码',
  `position_name` varchar(20) DEFAULT NULL COMMENT '岗位名称',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`position_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-岗位维(`mup-source`.source_dim_position)';

-- ----------------------------
-- Table structure for source_dim_profession
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_profession`;
CREATE TABLE `source_dim_profession` (
  `profession_id` varchar(32) NOT NULL COMMENT '行业ID',
  `profession_name` varchar(80) DEFAULT NULL COMMENT '行业名称',
  `profession_key` varchar(32) DEFAULT NULL COMMENT '行业编码',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`profession_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-行业维（`mup-source`.source_dim_profession）';

-- ----------------------------
-- Table structure for source_dim_project_input_type
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_project_input_type`;
CREATE TABLE `source_dim_project_input_type` (
  `project_input_type_id` varchar(32) NOT NULL,
  `project_input_type_name` varchar(10) DEFAULT NULL,
  `show_index` tinyint(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-项目输入类型维（`mup-source`.source_dim_project_input_type）';

-- ----------------------------
-- Table structure for source_dim_project_type
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_project_type`;
CREATE TABLE `source_dim_project_type` (
  `project_type_id` varchar(32) NOT NULL COMMENT '项目类型ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `project_type_name` varchar(10) DEFAULT NULL COMMENT '项目类型名称',
  `show_index` tinyint(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`project_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-项目类型维（`mup-source`.source_dim_project_type）';

-- ----------------------------
-- Table structure for source_dim_province
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_province`;
CREATE TABLE `source_dim_province` (
  `province_id` varchar(32) NOT NULL COMMENT '省ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `province_key` varchar(20) DEFAULT NULL COMMENT '省编码',
  `province_name` varchar(20) DEFAULT NULL COMMENT '省名称',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  `curt_name` char(1) DEFAULT NULL COMMENT '短名',
  PRIMARY KEY (`province_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-省（`mup-source`.source_dim_province）';

-- ----------------------------
-- Table structure for source_dim_quality
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_quality`;
CREATE TABLE `source_dim_quality` (
  `quality_id` varchar(32) NOT NULL COMMENT '能力素质ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `vocabulary` varchar(10) DEFAULT NULL COMMENT '能力素质词条',
  `note` varchar(80) DEFAULT NULL COMMENT '能力素质定义',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`quality_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-能力素质维（`mup-source`.source_dim_quality）';

-- ----------------------------
-- Table structure for source_dim_run_off
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_run_off`;
CREATE TABLE `source_dim_run_off` (
  `id` varchar(32) NOT NULL,
  `customer_id` varchar(32) DEFAULT NULL,
  `run_off_key` varchar(3) DEFAULT NULL COMMENT '编码',
  `run_off_name` varchar(200) DEFAULT NULL COMMENT '流失原因',
  `type` int(1) DEFAULT NULL COMMENT '1：主动流失。2：被动流失。3：其它。',
  `show_index` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-主动流失维（`mup-source`.source_dim_run_off）';

-- ----------------------------
-- Table structure for source_dim_sales_product
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_sales_product`;
CREATE TABLE `source_dim_sales_product` (
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `product_key` varchar(32) DEFAULT NULL COMMENT '商品编码',
  `product_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `product_cost` double(10,2) DEFAULT NULL COMMENT '商品成本（单位：元）',
  `product_price` double(10,2) DEFAULT NULL COMMENT '商品价格（单位：元）',
  `product_supplier_id` varchar(32) DEFAULT NULL COMMENT '产品供应商ID',
  `product_modul_id` varchar(32) DEFAULT NULL COMMENT '产品模块ID',
  `product_category_id` varchar(32) DEFAULT NULL COMMENT '产品类型ID',
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='商品（`mup-source`.source_dim_sales_product）';

-- ----------------------------
-- Table structure for source_dim_sales_team
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_sales_team`;
CREATE TABLE `source_dim_sales_team` (
  `team_id` varchar(32) NOT NULL COMMENT '团队ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `team_name` varchar(40) DEFAULT NULL COMMENT '团队名称',
  `emp_id` varchar(200) DEFAULT NULL COMMENT '负责人ID',
  `emp_names` varchar(100) DEFAULT NULL COMMENT '负责人名称',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`team_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='团队信息(`mup-source`.source_dim_sales_team)';

-- ----------------------------
-- Table structure for source_dim_satfac_genre
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_satfac_genre`;
CREATE TABLE `source_dim_satfac_genre` (
  `satfac_genre_id` varchar(32) NOT NULL COMMENT '满意度ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `satfac_name` varchar(100) DEFAULT NULL COMMENT '名称',
  `satfac_genre_parent_id` varchar(32) DEFAULT NULL COMMENT '上级ID',
  `is_children` tinyint(1) DEFAULT NULL COMMENT '是否有子节点',
  `create_date` date DEFAULT NULL COMMENT '创建日期',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`satfac_genre_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-满意度分类（`mup-source`.source_dim_satfac_genre）';

-- ----------------------------
-- Table structure for source_dim_separation_risk
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_separation_risk`;
CREATE TABLE `source_dim_separation_risk` (
  `separation_risk_id` varchar(32) NOT NULL COMMENT '离职风险ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `separation_risk_key` varchar(100) DEFAULT NULL COMMENT '离职风险编号',
  `separation_risk_parent_id` varchar(32) DEFAULT NULL COMMENT '上级ID',
  `separation_risk_parent_key` varchar(100) DEFAULT NULL COMMENT '上级编号',
  `separation_risk_name` varchar(20) DEFAULT NULL COMMENT '离职风险名称',
  `refer` text COMMENT '参考',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`separation_risk_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-离职风险维(`mup-source`.source_dim_separation_risk)';

-- ----------------------------
-- Table structure for source_dim_sequence
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_sequence`;
CREATE TABLE `source_dim_sequence` (
  `sequence_id` varchar(32) NOT NULL COMMENT '序列',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `sequence_key` varchar(20) DEFAULT NULL COMMENT '序列编号',
  `sequence_name` varchar(60) DEFAULT NULL COMMENT '序列名称',
  `curt_name` varchar(1) DEFAULT NULL COMMENT '短名',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`sequence_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-序列维(source_dim_sequence)';

-- ----------------------------
-- Table structure for source_dim_sequence_sub
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_sequence_sub`;
CREATE TABLE `source_dim_sequence_sub` (
  `sequence_sub_id` varchar(32) NOT NULL COMMENT '子序列ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `sequence_id` varchar(32) DEFAULT NULL COMMENT '序列',
  `sequence_sub_key` varchar(20) DEFAULT NULL COMMENT '子序列编号',
  `sequence_sub_name` varchar(60) DEFAULT NULL COMMENT '子序列名称',
  `curt_name` varchar(1) DEFAULT NULL COMMENT '短名',
  `show_index` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`sequence_sub_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-子序列维(dim_sequence_sub)';

-- ----------------------------
-- Table structure for source_dim_structure
-- ----------------------------
DROP TABLE IF EXISTS `source_dim_structure`;
CREATE TABLE `source_dim_structure` (
  `structure_id` varchar(32) NOT NULL COMMENT '工资结构ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `structure_name` varchar(20) DEFAULT NULL COMMENT '结构名称',
  `is_fixed` tinyint(1) DEFAULT NULL COMMENT '是否固定',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`structure_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-工资结构维（`mup-source`.source_dim_structure）';

-- ----------------------------
-- Table structure for source_emp_attendance_day
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_attendance_day`;
CREATE TABLE `source_emp_attendance_day` (
  `emp_attendance_day_id` varchar(32) NOT NULL COMMENT '出勤记录ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_key` varchar(20) DEFAULT NULL COMMENT '员工编号',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `user_name_ch` varchar(5) DEFAULT NULL COMMENT '员工名称',
  `hour_count` double(4,2) DEFAULT NULL COMMENT '实际出勤小时',
  `theory_hour_count` double(4,2) DEFAULT NULL COMMENT '应出勤小时',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `checkwork_type_id` varchar(32) DEFAULT NULL COMMENT '考勤类型ID',
  `days` date DEFAULT NULL COMMENT '日期',
  `year_months` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`emp_attendance_day_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-出勤记录（source_emp_attendance_day）';

-- ----------------------------
-- Table structure for source_emp_bonus_penalty
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_bonus_penalty`;
CREATE TABLE `source_emp_bonus_penalty` (
  `emp_bonus_penalty_id` varchar(32) NOT NULL COMMENT '奖惩信息ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `emp_bonus_penalty_name` varchar(20) DEFAULT NULL COMMENT '奖惩信息名称',
  `type` int(1) DEFAULT NULL COMMENT '奖惩类型（1：奖，0：惩）',
  `grant_unit` varchar(32) DEFAULT NULL COMMENT '授予单位',
  `witness_name` varchar(32) DEFAULT NULL COMMENT '证明人',
  `bonus_penalty_date` datetime DEFAULT NULL COMMENT '奖惩时间',
  `c_id` varchar(32) DEFAULT NULL COMMENT '标识ID',
  PRIMARY KEY (`emp_bonus_penalty_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-奖惩信息（source_emp_bonus_penalty）';

-- ----------------------------
-- Table structure for source_emp_certificate_info
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_certificate_info`;
CREATE TABLE `source_emp_certificate_info` (
  `emp_certificate_info_id` varchar(32) NOT NULL COMMENT '员工证书信息ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `certificate_id` varchar(32) DEFAULT NULL COMMENT '证书ID',
  `obtain_date` date DEFAULT NULL COMMENT '获取时间',
  PRIMARY KEY (`emp_certificate_info_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-员工证书信息（source_emp_certificate_info）';

-- ----------------------------
-- Table structure for source_emp_contact_person
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_contact_person`;
CREATE TABLE `source_emp_contact_person` (
  `emp_contact_person_id` varchar(32) NOT NULL COMMENT '联系人ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `emp_contact_person_name` varchar(20) DEFAULT NULL COMMENT '联系人名称',
  `tel_phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `call` varchar(10) DEFAULT NULL COMMENT '称呼（配偶、子女、朋友、同事）',
  `is_urgent` int(1) DEFAULT NULL COMMENT '是否紧急联系人',
  `create_time` datetime DEFAULT NULL COMMENT '录入时间',
  PRIMARY KEY (`emp_contact_person_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-联系人信息（source_emp_contact_person）';

-- ----------------------------
-- Table structure for source_emp_edu
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_edu`;
CREATE TABLE `source_emp_edu` (
  `emp_edu_id` varchar(32) NOT NULL COMMENT '教育背景ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `school_name` varchar(60) DEFAULT NULL COMMENT '学校名校',
  `degree_id` varchar(32) DEFAULT NULL COMMENT '学历ID',
  `degree` varchar(12) DEFAULT NULL COMMENT '学历名称',
  `major` varchar(12) DEFAULT NULL COMMENT '专业',
  `start_date` date DEFAULT NULL COMMENT '开始时间',
  `end_date` date DEFAULT NULL COMMENT '结束时间',
  `is_last_major` tinyint(1) DEFAULT NULL COMMENT '是否最后专业',
  `academic_degree` varchar(12) DEFAULT NULL COMMENT '学位',
  `develop_mode` varchar(12) DEFAULT NULL COMMENT '培养方式',
  `bonus` varchar(60) DEFAULT NULL COMMENT '奖励信息',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`emp_edu_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-教育背景（`mup-source`.source_emp_edu）';

-- ----------------------------
-- Table structure for source_emp_family
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_family`;
CREATE TABLE `source_emp_family` (
  `emp_family_id` varchar(32) NOT NULL COMMENT '家族ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `emp_family_name` varchar(20) DEFAULT NULL COMMENT '家属名称',
  `call` varchar(6) DEFAULT NULL COMMENT '称呼(父、母、子、女)',
  `is_child` tinyint(1) DEFAULT NULL COMMENT '是否是子女',
  `work_unit` varchar(20) DEFAULT NULL COMMENT '工作单位',
  `department_name` varchar(20) DEFAULT NULL COMMENT '部门名称',
  `position_name` varchar(20) DEFAULT NULL COMMENT '岗位/职务',
  `tel_phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `born_date` datetime DEFAULT NULL COMMENT '出生年月',
  `note` varchar(20) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`emp_family_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-家庭关系（source_emp_family）';

-- ----------------------------
-- Table structure for source_emp_other_day
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_other_day`;
CREATE TABLE `source_emp_other_day` (
  `emp_other_day_id` varchar(32) NOT NULL COMMENT '记录ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_key` varchar(20) DEFAULT NULL COMMENT '员工编号',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `user_name_ch` varchar(5) DEFAULT NULL COMMENT '员工名称',
  `hour_count` double(4,2) DEFAULT NULL COMMENT '休假小时',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `checkwork_type_id` varchar(32) DEFAULT NULL COMMENT '考勤类型ID',
  `days` date DEFAULT NULL COMMENT '日期',
  `year_months` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`emp_other_day_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-其他考勤记录（source_emp_other_day）';

-- ----------------------------
-- Table structure for source_emp_overtime_day
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_overtime_day`;
CREATE TABLE `source_emp_overtime_day` (
  `emp_overtime_day_id` varchar(32) NOT NULL COMMENT '员工加班记录ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_key` varchar(20) DEFAULT NULL COMMENT '员工编号',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `user_name_ch` varchar(5) DEFAULT NULL COMMENT '员工名称',
  `hour_count` double(4,2) DEFAULT NULL COMMENT '加班小时',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `checkwork_type_id` varchar(32) DEFAULT NULL COMMENT '考勤类型ID',
  `days` date DEFAULT NULL COMMENT '日期',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`emp_overtime_day_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-加班记录（source_emp_overtime_day）';

-- ----------------------------
-- Table structure for source_emp_past_resume
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_past_resume`;
CREATE TABLE `source_emp_past_resume` (
  `emp_past_resume_id` varchar(32) NOT NULL COMMENT '过往履历ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `work_unit` varchar(20) DEFAULT NULL COMMENT '就职单位',
  `department_name` varchar(20) DEFAULT NULL COMMENT '部门名称',
  `position_name` varchar(20) DEFAULT NULL COMMENT '岗位名称',
  `bonus_penalty_name` varchar(20) DEFAULT NULL COMMENT '奖惩名称',
  `witness_name` varchar(12) DEFAULT NULL COMMENT '证明人',
  `witness_contact_info` varchar(20) DEFAULT NULL COMMENT '证明人联系方式',
  `change_reason` varchar(20) DEFAULT NULL COMMENT '变动原因',
  `entry_date` datetime DEFAULT NULL COMMENT '入职日期',
  `run_off_date` datetime DEFAULT NULL COMMENT '流失日期',
  `mark` tinyint(1) DEFAULT NULL COMMENT '标记mark 1，新增 2，修改 3，已加索引',
  PRIMARY KEY (`emp_past_resume_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-过往履历（不是简历）（source_emp_past_resume）';

-- ----------------------------
-- Table structure for source_emp_personality
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_personality`;
CREATE TABLE `source_emp_personality` (
  `emp_personality_id` varchar(32) NOT NULL COMMENT '员工性格ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `personality_id` varchar(32) DEFAULT NULL COMMENT '性格ID',
  `type` int(1) DEFAULT NULL COMMENT '性格类型',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`emp_personality_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-员工性格（`mup-source`.source_emp_personality）';

-- ----------------------------
-- Table structure for source_emp_pq_eval_relation
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_pq_eval_relation`;
CREATE TABLE `source_emp_pq_eval_relation` (
  `emp_id` varchar(32) NOT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `examination_result_id` varchar(32) DEFAULT NULL COMMENT '考核结果ID',
  `examination_result` varchar(4) DEFAULT NULL COMMENT '考核结果',
  `date` date DEFAULT NULL COMMENT '考核日期',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `curt_name` int(1) DEFAULT NULL COMMENT '短名',
  PRIMARY KEY (`emp_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-员工岗位能力评价（emp_pq_eval_relation）';

-- ----------------------------
-- Table structure for source_emp_pq_relation
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_pq_relation`;
CREATE TABLE `source_emp_pq_relation` (
  `emp_pq_relation_id` varchar(32) NOT NULL COMMENT '员工岗位素质得分ID',
  `quality_id` varchar(32) DEFAULT NULL COMMENT '能力素质ID',
  `matching_score_id` varchar(32) DEFAULT NULL COMMENT '所得分数ID',
  `demands_matching_score_id` varchar(32) DEFAULT NULL COMMENT '要求分数ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `date` date DEFAULT NULL COMMENT '考核日期',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`emp_pq_relation_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-员工岗位素质得分（`mup-source`.source_emp_pq_relation）';

-- ----------------------------
-- Table structure for source_emp_prof_title
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_prof_title`;
CREATE TABLE `source_emp_prof_title` (
  `emp_prof_title_id` varchar(32) NOT NULL COMMENT '所获职称ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `gain_date` datetime DEFAULT NULL COMMENT '获得时间',
  `emp_prof_title_name` varchar(20) DEFAULT NULL COMMENT '职称名称',
  `prof_lv` varchar(8) DEFAULT NULL COMMENT '级别',
  `award_unit` varchar(20) DEFAULT NULL COMMENT '授予单位',
  `effect_date` datetime DEFAULT NULL COMMENT '有效期',
  PRIMARY KEY (`emp_prof_title_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-所获职称（`mup-source`.source_emp_prof_title）';

-- ----------------------------
-- Table structure for source_emp_train_experience
-- ----------------------------
DROP TABLE IF EXISTS `source_emp_train_experience`;
CREATE TABLE `source_emp_train_experience` (
  `train_experience_id` varchar(32) NOT NULL COMMENT '培训经历ID',
  `course_record_id` varchar(32) DEFAULT NULL COMMENT '课程进程记录ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `course_name` varchar(20) DEFAULT NULL COMMENT '课程名称/项目',
  `start_date` datetime DEFAULT NULL COMMENT '开始日期',
  `finish_date` datetime DEFAULT NULL COMMENT '完成日期',
  `train_time` double(5,2) DEFAULT NULL COMMENT '学时',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（1已完成，0进行中）',
  `result` varchar(8) DEFAULT NULL COMMENT '成绩（A、B.../98.5、88.0）',
  `train_unit` varchar(20) DEFAULT NULL COMMENT '培训机构',
  `gain_certificate` varchar(20) DEFAULT NULL COMMENT '所获证书',
  `lecturer_name` varchar(12) DEFAULT NULL COMMENT '讲师名称',
  `note` varchar(20) DEFAULT NULL COMMENT '备注',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `mark` tinyint(1) DEFAULT NULL COMMENT '标记',
  PRIMARY KEY (`train_experience_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-培训经历（source_emp_train_experience）';

-- ----------------------------
-- Table structure for source_fact_fte
-- ----------------------------
DROP TABLE IF EXISTS `source_fact_fte`;
CREATE TABLE `source_fact_fte` (
  `fte_id` varchar(32) NOT NULL COMMENT '等效全职员工数ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `organization_name` varchar(50) DEFAULT NULL COMMENT '机构名称',
  `fulltime_sum` double(10,2) DEFAULT NULL COMMENT '全职员工数',
  `passtime_sum` double(10,2) DEFAULT NULL COMMENT '兼职员工数',
  `overtime_sum` double(10,2) DEFAULT NULL COMMENT '加班员工数',
  `fte_value` double(10,2) DEFAULT NULL COMMENT '等效全职员工数值',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  `gain_amount` decimal(10,4) DEFAULT NULL,
  `sales_amount` decimal(10,4) DEFAULT NULL,
  `target_value` double(10,4) DEFAULT NULL COMMENT '目标人均效益(万)',
  `benefit_value` double(10,4) DEFAULT NULL COMMENT '实际人均效益（万）',
  `range_per` double(10,4) DEFAULT NULL,
  PRIMARY KEY (`fte_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_job_change
-- ----------------------------
DROP TABLE IF EXISTS `source_job_change`;
CREATE TABLE `source_job_change` (
  `emp_job_change_id` varchar(32) NOT NULL COMMENT '变更ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `emp_key` varchar(20) DEFAULT NULL COMMENT '员工编码',
  `user_name_ch` varchar(20) DEFAULT NULL COMMENT '员工中文名',
  `change_date` date DEFAULT NULL COMMENT '异动日期',
  `change_type_id` varchar(32) DEFAULT NULL COMMENT '异动类型ID',
  `change_type` int(3) DEFAULT NULL COMMENT '异动类型',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '部门ID（机构类别为3）',
  `organization_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `position_id` varchar(32) DEFAULT NULL COMMENT '岗位ID',
  `position_name` varchar(20) DEFAULT NULL COMMENT '岗位名称',
  `sequence_id` varchar(32) DEFAULT NULL COMMENT '序列ID',
  `sequence_name` varchar(20) DEFAULT NULL COMMENT '序列名称',
  `sequence_sub_id` varchar(32) DEFAULT NULL COMMENT '子序列ID',
  `sequence_sub_name` varchar(20) DEFAULT NULL COMMENT '子序列名称',
  `ability_id` varchar(32) DEFAULT NULL COMMENT '层级ID',
  `ability_name` varchar(20) DEFAULT NULL COMMENT '层级名称',
  `ability_lv_id` varchar(32) DEFAULT NULL COMMENT '职级ID',
  `ability_lv_name` varchar(20) DEFAULT NULL COMMENT '职级名称',
  `job_title_id` varchar(32) DEFAULT NULL COMMENT '职衔ID',
  `job_title_name` varchar(60) DEFAULT NULL COMMENT '职衔名称',
  `rank_name` varchar(5) DEFAULT NULL COMMENT '简称',
  `rank_name_ed` varchar(5) DEFAULT NULL COMMENT '前简称',
  `position_id_ed` varchar(32) DEFAULT NULL COMMENT '前岗位ID',
  `position_name_ed` varchar(20) DEFAULT NULL COMMENT '前岗位名称',
  `note` varchar(5) DEFAULT NULL COMMENT '备注',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`emp_job_change_id`),
  KEY `index_eId` (`emp_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-工作异动表（`mup-source`.source_job_change）';

-- ----------------------------
-- Table structure for source_job_relation
-- ----------------------------
DROP TABLE IF EXISTS `source_job_relation`;
CREATE TABLE `source_job_relation` (
  `job_relation_id` varchar(32) NOT NULL COMMENT '职位序列ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `sequence_sub_id` varchar(32) DEFAULT NULL COMMENT '子序列ID',
  `sequence_id` varchar(32) DEFAULT NULL COMMENT '主序列ID',
  `ability_id` varchar(32) DEFAULT NULL COMMENT '能力层级ID',
  `job_title_id` varchar(32) DEFAULT NULL COMMENT '职衔ID',
  `ability_lv_id` varchar(32) DEFAULT NULL COMMENT '职级ID',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `type` int(1) DEFAULT NULL COMMENT '类型（0：默认名称。1：特定名称）',
  `rank_name` varchar(5) DEFAULT NULL COMMENT '主子序列层级职级',
  `show_index` int(4) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`job_relation_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-职位序列关系（`mup-source`.source_job_relation）';

-- ----------------------------
-- Table structure for source_lecturer
-- ----------------------------
DROP TABLE IF EXISTS `source_lecturer`;
CREATE TABLE `source_lecturer` (
  `lecturer_id` varchar(32) NOT NULL COMMENT '讲师ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID（内部讲师）',
  `lecturer_name` varchar(20) DEFAULT NULL COMMENT '讲师名称',
  `level_id` varchar(32) DEFAULT NULL COMMENT '讲师级别ID',
  `type` tinyint(1) DEFAULT NULL COMMENT '讲师类别（1外部，2内部）',
  PRIMARY KEY (`lecturer_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-讲师（source_lecturer）';

-- ----------------------------
-- Table structure for source_lecturer_course_design
-- ----------------------------
DROP TABLE IF EXISTS `source_lecturer_course_design`;
CREATE TABLE `source_lecturer_course_design` (
  `lecturer_course_design_id` varchar(32) NOT NULL COMMENT '讲师设计课ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `lecturer_id` varchar(32) DEFAULT NULL COMMENT '讲师ID',
  `course_id` varchar(32) DEFAULT NULL COMMENT '课程ID',
  PRIMARY KEY (`lecturer_course_design_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-讲师设计课（source_lecturer_course_design）';

-- ----------------------------
-- Table structure for source_lecturer_course_speak
-- ----------------------------
DROP TABLE IF EXISTS `source_lecturer_course_speak`;
CREATE TABLE `source_lecturer_course_speak` (
  `lecturer_course_speak_id` varchar(32) NOT NULL COMMENT '讲师授课ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `lecturer_id` varchar(32) DEFAULT NULL COMMENT '讲师ID',
  `course_id` varchar(32) DEFAULT NULL COMMENT '课程ID',
  `start_date` datetime DEFAULT NULL COMMENT '授课开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '授课结束时间',
  `year` int(4) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`lecturer_course_speak_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-讲师授课（source_lecturer_course_speak）';

-- ----------------------------
-- Table structure for source_manpower_cost
-- ----------------------------
DROP TABLE IF EXISTS `source_manpower_cost`;
CREATE TABLE `source_manpower_cost` (
  `manpower_cost_id` varchar(32) NOT NULL COMMENT '人力成本ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `cost` double(10,2) DEFAULT NULL COMMENT '人力成本、已使用成本（单位：万元）',
  `cost_budget` double(10,2) DEFAULT NULL COMMENT '预算成本',
  `cost_company` double(10,2) DEFAULT NULL COMMENT '企业总成本（单位：万元）',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`manpower_cost_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-人力成本（manpower_cost）';

-- ----------------------------
-- Table structure for source_manpower_cost_value
-- ----------------------------
DROP TABLE IF EXISTS `source_manpower_cost_value`;
CREATE TABLE `source_manpower_cost_value` (
  `manpower_cost_value_id` varchar(32) NOT NULL COMMENT '人力年度预算成本ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `budget_value` double(20,4) DEFAULT NULL COMMENT '预算度本',
  `year` int(4) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`manpower_cost_value_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-人力年度预算成本（年）（`mup-source`.source_manpower_cost_value）';

-- ----------------------------
-- Table structure for source_map_management
-- ----------------------------
DROP TABLE IF EXISTS `source_map_management`;
CREATE TABLE `source_map_management` (
  `map_management_id` varchar(32) NOT NULL COMMENT '人才地图管理ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `top_id` varchar(32) DEFAULT NULL COMMENT '顶级ID',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `adjustment_id` varchar(32) DEFAULT NULL COMMENT '调整人ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '审核人ID',
  `starte_date` datetime DEFAULT NULL COMMENT '生成时间',
  `motify_date` datetime DEFAULT NULL COMMENT '调整时间',
  `release_date` datetime DEFAULT NULL COMMENT '发布时间',
  `year_months` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`map_management_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='人才地图管理（`mup-source`.source_map_management）';

-- ----------------------------
-- Table structure for source_map_public
-- ----------------------------
DROP TABLE IF EXISTS `source_map_public`;
CREATE TABLE `source_map_public` (
  `organization_id` varchar(32) NOT NULL COMMENT '顶级ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `full_path` varchar(200) DEFAULT NULL COMMENT '全路径',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `year_month` int(10) NOT NULL COMMENT '年月',
  PRIMARY KEY (`organization_id`,`year_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='人才地图发布(`mup-source`.source_map_public)';

-- ----------------------------
-- Table structure for source_map_talent
-- ----------------------------
DROP TABLE IF EXISTS `source_map_talent`;
CREATE TABLE `source_map_talent` (
  `map_talent_id` varchar(32) NOT NULL COMMENT '员工人才地图ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `x_axis_id` varchar(32) DEFAULT NULL COMMENT 'x轴ID',
  `x_axis_id_af` varchar(32) DEFAULT NULL COMMENT 'x轴ID后',
  `x_axis_id_af_r` varchar(32) DEFAULT NULL COMMENT 'x轴ID后R',
  `y_axis_id` varchar(32) DEFAULT NULL COMMENT 'y轴ID',
  `y_axis_id_af` varchar(32) DEFAULT NULL COMMENT 'y轴ID后',
  `y_axis_id_af_r` varchar(32) DEFAULT NULL COMMENT 'y轴ID后R',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_update` int(1) DEFAULT NULL COMMENT '更新标识',
  `modify_id` varchar(32) DEFAULT NULL COMMENT '调整人ID',
  `release_id` varchar(32) DEFAULT NULL COMMENT '发布人ID',
  `year_months` int(6) DEFAULT NULL COMMENT '年月',
  `note` varchar(500) DEFAULT NULL COMMENT '第一次调整说明',
  `note1` varchar(500) DEFAULT NULL COMMENT '第二次调整说明',
  `date_time` date DEFAULT NULL COMMENT '人才地图数据抽取时间',
  PRIMARY KEY (`map_talent_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='人才地图（`mup-source`.source_map_talent）';

-- ----------------------------
-- Table structure for source_map_talent_info
-- ----------------------------
DROP TABLE IF EXISTS `source_map_talent_info`;
CREATE TABLE `source_map_talent_info` (
  `emp_id` varchar(32) NOT NULL COMMENT '员工ID',
  `user_name_ch` varchar(50) DEFAULT NULL COMMENT '员工中文名',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `full_path` varchar(200) DEFAULT NULL COMMENT '员工机构全路径',
  `organization_parent_id` varchar(32) DEFAULT NULL COMMENT '父机构ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `sequence_id` varchar(32) DEFAULT NULL COMMENT '主序列',
  `sequence_sub_id` varchar(32) DEFAULT NULL COMMENT '子序列',
  `ability_id` varchar(32) DEFAULT NULL COMMENT '职位层级ID',
  `degree_id` varchar(32) DEFAULT NULL COMMENT '学位ID',
  `performance_id` varchar(32) DEFAULT NULL COMMENT '绩效ID',
  `position_id` varchar(32) DEFAULT NULL COMMENT '职位ID',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`emp_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='人才地图员工信息(`mup-source`.source_map_talent_info)';

-- ----------------------------
-- Table structure for source_map_talent_result
-- ----------------------------
DROP TABLE IF EXISTS `source_map_talent_result`;
CREATE TABLE `source_map_talent_result` (
  `map_talent_id` varchar(32) NOT NULL COMMENT '员工人才地图ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `x_axis_id` varchar(32) DEFAULT NULL COMMENT 'x轴ID',
  `y_axis_id` varchar(32) DEFAULT NULL COMMENT 'y轴ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `year_months` int(6) DEFAULT NULL COMMENT '年月',
  `note` varchar(500) DEFAULT NULL COMMENT '调整说明',
  PRIMARY KEY (`map_talent_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='人才地图结果（`mup-source`.source_map_talent_result）';

-- ----------------------------
-- Table structure for source_map_team
-- ----------------------------
DROP TABLE IF EXISTS `source_map_team`;
CREATE TABLE `source_map_team` (
  `map_team_id` varchar(32) NOT NULL COMMENT '自定义团队',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `team_name` varchar(50) DEFAULT NULL COMMENT '团队名称',
  `requirement` varchar(4000) DEFAULT NULL COMMENT '要求',
  `pk_view` int(1) DEFAULT NULL COMMENT '是否团队PK查看',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`map_team_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='自定义团队（`mup-source`.source_map_team）';

-- ----------------------------
-- Table structure for source_matching_school
-- ----------------------------
DROP TABLE IF EXISTS `source_matching_school`;
CREATE TABLE `source_matching_school` (
  `matching_school_id` varchar(32) NOT NULL COMMENT '学校ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `name` varchar(50) DEFAULT NULL COMMENT '学校名称',
  `school_type` varchar(10) DEFAULT NULL COMMENT '院校类型',
  `level` int(1) DEFAULT NULL COMMENT '等级',
  PRIMARY KEY (`matching_school_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='匹配表-学校表(source_matching_school)';

-- ----------------------------
-- Table structure for source_matching_score
-- ----------------------------
DROP TABLE IF EXISTS `source_matching_score`;
CREATE TABLE `source_matching_score` (
  `matching_score_id` varchar(32) NOT NULL COMMENT '分数映射ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `v1` varchar(10) DEFAULT NULL COMMENT '字符',
  `show_index` tinyint(1) DEFAULT NULL COMMENT '排名',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`matching_score_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='匹配表-分数映射（source_matching_score）';

-- ----------------------------
-- Table structure for source_organization_change
-- ----------------------------
DROP TABLE IF EXISTS `source_organization_change`;
CREATE TABLE `source_organization_change` (
  `organization_change_id` varchar(32) NOT NULL COMMENT '变更ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `emp_key` varchar(20) DEFAULT NULL COMMENT '员工编码',
  `user_name_ch` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `change_date` datetime DEFAULT NULL COMMENT '异动日期',
  `change_type_id` varchar(32) DEFAULT NULL COMMENT '异动类型ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `organization_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `organization_id_ed` varchar(32) DEFAULT NULL COMMENT '前部门ID',
  `organization_name_ed` varchar(60) DEFAULT NULL COMMENT '前部门名称',
  `position_id` varchar(32) DEFAULT NULL COMMENT '岗位ID',
  `position_name` varchar(20) DEFAULT NULL COMMENT '岗位名称',
  `sequence_id` varchar(32) DEFAULT NULL COMMENT '序列ID',
  `sequence_name` varchar(20) DEFAULT NULL COMMENT '序列名称',
  `ability_id` varchar(32) DEFAULT NULL COMMENT '层级ID',
  `ability_name` varchar(20) DEFAULT NULL COMMENT '层级名称',
  `note` varchar(5) DEFAULT NULL COMMENT '备注',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`organization_change_id`),
  KEY `index_eId` (`emp_id`),
  KEY `index_eKey` (`emp_key`),
  KEY `index_orgId_Day` (`change_date`,`organization_id`),
  KEY `ind_Ch_Date` (`change_date`),
  KEY `ind_Ch_Date_Uch` (`change_date`,`user_name_ch`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-机构异动表（`mup-source`.source_organization_change）';

-- ----------------------------
-- Table structure for source_organization_emp_relation
-- ----------------------------
DROP TABLE IF EXISTS `source_organization_emp_relation`;
CREATE TABLE `source_organization_emp_relation` (
  `customer_id` varchar(32) DEFAULT NULL,
  `organization_id` varchar(32) DEFAULT NULL,
  `emp_id` char(32) DEFAULT NULL,
  `sys_code_item_id` varchar(32) DEFAULT NULL,
  `refresh_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-机构负责人关系(source_organization_emp_relation)';

-- ----------------------------
-- Table structure for source_out_talent
-- ----------------------------
DROP TABLE IF EXISTS `source_out_talent`;
CREATE TABLE `source_out_talent` (
  `out_talent_id` varchar(32) NOT NULL COMMENT '外部人才ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `user_name_ch` varchar(5) DEFAULT NULL COMMENT '中文名',
  `user_name` varchar(20) DEFAULT NULL COMMENT '英文名',
  `email` text COMMENT '邮箱',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` char(3) DEFAULT NULL COMMENT '姓别',
  `degree_id` varchar(32) DEFAULT NULL COMMENT '学历ID',
  `url` text COMMENT '简历url',
  `school` varchar(30) DEFAULT NULL COMMENT '毕业院校',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `img_path` varchar(500) DEFAULT NULL COMMENT '图片路径',
  `tag` varchar(100) DEFAULT NULL COMMENT '标签',
  `c_id` varchar(32) DEFAULT NULL COMMENT '标识ID',
  PRIMARY KEY (`out_talent_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-外部人才库（out_talent）';

-- ----------------------------
-- Table structure for source_pay
-- ----------------------------
DROP TABLE IF EXISTS `source_pay`;
CREATE TABLE `source_pay` (
  `id` varchar(32) NOT NULL COMMENT '薪酬费用ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `usre_name_ch` varchar(5) DEFAULT NULL COMMENT '中文名',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `full_path` varchar(200) DEFAULT NULL COMMENT '机构全路径',
  `postion_id` varchar(32) DEFAULT NULL COMMENT '岗位ID',
  `pay_contract` varchar(32) DEFAULT NULL COMMENT '合同薪酬（单位：元）',
  `pay_should` varchar(32) DEFAULT NULL COMMENT '应发薪酬（单位：元）',
  `pay_actual` varchar(32) DEFAULT NULL COMMENT '实发工资（单位：元）',
  `salary_actual` varchar(32) DEFAULT NULL COMMENT '应发工资（单位：元）',
  `welfare_actual` varchar(50) DEFAULT NULL COMMENT '应发福利（单位：元）',
  `cr_value` double(6,4) DEFAULT NULL COMMENT 'CR值（过）',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `bonus` varchar(10) DEFAULT NULL COMMENT 'bonus',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-薪酬费用（`mup-source`.source_pay）';

-- ----------------------------
-- Table structure for source_pay_value
-- ----------------------------
DROP TABLE IF EXISTS `source_pay_value`;
CREATE TABLE `source_pay_value` (
  `pay_value_id` varchar(32) NOT NULL COMMENT '薪酬汇总ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `pay_value` double(10,4) DEFAULT NULL COMMENT '预算薪酬（单位：万元）',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pay_value_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_performance_change
-- ----------------------------
DROP TABLE IF EXISTS `source_performance_change`;
CREATE TABLE `source_performance_change` (
  `performance_change_id` varchar(32) NOT NULL COMMENT '变更ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `emp_key` varchar(20) DEFAULT NULL COMMENT '员工编码',
  `organization_parent_id` varchar(32) DEFAULT NULL COMMENT '单位ID（机构类别为2）',
  `organzation_parent_name` varchar(60) DEFAULT NULL COMMENT '单位名称',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '部门ID（机构类别为3）',
  `organization_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `position_id` varchar(32) DEFAULT NULL COMMENT '岗位ID',
  `position_name` varchar(20) DEFAULT NULL COMMENT '岗位名称',
  `performance_id` varchar(32) DEFAULT NULL COMMENT '绩效ID',
  `performance_name` varchar(60) DEFAULT NULL COMMENT '绩效名称',
  `rank_name` varchar(5) DEFAULT NULL COMMENT '简称',
  `rank_name_past` varchar(5) DEFAULT NULL COMMENT '前简称',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  `type` int(1) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`performance_change_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-绩效信息（source_performance_change）';

-- ----------------------------
-- Table structure for source_population_relation
-- ----------------------------
DROP TABLE IF EXISTS `source_population_relation`;
CREATE TABLE `source_population_relation` (
  `customer_id` varchar(32) DEFAULT NULL,
  `population_id` varchar(32) DEFAULT NULL,
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `organization_id` varchar(32) DEFAULT NULL,
  `days` date DEFAULT NULL,
  KEY `ind_days` (`days`),
  KEY `ind_eId` (`emp_id`),
  KEY `ind_day_eId` (`days`,`emp_id`),
  KEY `ind_day_pId` (`days`,`population_id`),
  KEY `ind_eId_day` (`emp_id`,`days`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-人员人群关系表(population_relation)';

-- ----------------------------
-- Table structure for source_position_quality
-- ----------------------------
DROP TABLE IF EXISTS `source_position_quality`;
CREATE TABLE `source_position_quality` (
  `position_quality_id` varchar(32) NOT NULL COMMENT '岗位能力素质ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `position_id` varchar(32) DEFAULT NULL COMMENT '岗位ID',
  `quality_id` varchar(32) DEFAULT NULL COMMENT '能力素质ID',
  `matching_source_id` varchar(32) DEFAULT NULL COMMENT '分数映射ID',
  `show_index` tinyint(1) DEFAULT NULL COMMENT '排序',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`position_quality_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-岗位能力素质（`mup-source`.source_position_quality）';

-- ----------------------------
-- Table structure for source_profession_quantile_relation
-- ----------------------------
DROP TABLE IF EXISTS `source_profession_quantile_relation`;
CREATE TABLE `source_profession_quantile_relation` (
  `id` varchar(32) NOT NULL COMMENT '行业分位ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `profession_id` varchar(32) DEFAULT NULL COMMENT '行业ID',
  `quantile_id` varchar(32) DEFAULT NULL COMMENT '分位ID',
  `quantile_value` double(4,3) DEFAULT NULL COMMENT '分位值（单位：万元）',
  `type` tinyint(1) DEFAULT NULL COMMENT '类别（1人均；2成本；3薪酬）',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-行业分位（`mup-source`.source_profession_quantile_relation）';

-- ----------------------------
-- Table structure for source_profession_value
-- ----------------------------
DROP TABLE IF EXISTS `source_profession_value`;
CREATE TABLE `source_profession_value` (
  `profession_value` varchar(32) NOT NULL COMMENT '行业指标值ID',
  `profession_name` varchar(50) DEFAULT NULL COMMENT '行业指标值名称',
  `profession_value_key` varchar(20) DEFAULT NULL COMMENT '行业指标值编码',
  `value` double(6,2) DEFAULT NULL COMMENT '行业指标值',
  `profession_id` varchar(32) DEFAULT NULL COMMENT '行业ID',
  `refresh` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`profession_value`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-行业指标值（source_profession_value）';

-- ----------------------------
-- Table structure for source_project
-- ----------------------------
DROP TABLE IF EXISTS `source_project`;
CREATE TABLE `source_project` (
  `project_id` varchar(32) NOT NULL COMMENT '项目ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `project_key` varchar(32) DEFAULT NULL COMMENT '项目编码',
  `project_name` varchar(30) DEFAULT NULL COMMENT '项目名称',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '负责人',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '主导机构',
  `project_type_id` varchar(32) DEFAULT NULL COMMENT '项目类型ID',
  `project_progress_id` varchar(32) DEFAULT NULL COMMENT '项目进度ID',
  `project_parent_id` varchar(32) DEFAULT NULL COMMENT '父项目ID',
  `full_path` text COMMENT '全路径',
  `has_chilren` tinyint(1) DEFAULT NULL COMMENT '是否有子节点',
  `start_date` date DEFAULT NULL COMMENT '项目开始时间',
  `end_date` date DEFAULT NULL COMMENT '项目结束时间',
  `refresh` date DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`project_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-项目（source_project）';

-- ----------------------------
-- Table structure for source_project_cost
-- ----------------------------
DROP TABLE IF EXISTS `source_project_cost`;
CREATE TABLE `source_project_cost` (
  `project_cost_id` varchar(32) NOT NULL COMMENT '项目费用明细ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `input` double(10,5) DEFAULT NULL COMMENT '投入（单位：万元）',
  `output` double(10,5) DEFAULT NULL COMMENT '产出（单位：万元）',
  `gain` double(10,5) DEFAULT NULL COMMENT '利润（单位：万元）',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目ID',
  `date` date DEFAULT NULL COMMENT '盘点日期',
  `type` tinyint(1) DEFAULT NULL COMMENT '日期类型(1季、2月、3双月)',
  PRIMARY KEY (`project_cost_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-项目费用明细（source_project_cost）';

-- ----------------------------
-- Table structure for source_project_input_detail
-- ----------------------------
DROP TABLE IF EXISTS `source_project_input_detail`;
CREATE TABLE `source_project_input_detail` (
  `project_input_detail_id` varchar(32) NOT NULL COMMENT '项目投入明细ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `project_id` varchar(32) DEFAULT NULL COMMENT '主导项目ID',
  `project_input_type_id` varchar(32) DEFAULT NULL COMMENT '投入类型ID',
  `outlay` double(12,7) DEFAULT NULL COMMENT '花费（单位：万元）',
  `date` date DEFAULT NULL COMMENT '盘点日期',
  `type` tinyint(1) DEFAULT NULL COMMENT '日期类型(1季、2月、3双月)',
  PRIMARY KEY (`project_input_detail_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关系表-项目投入费用明细（source_project_input_detail）';

-- ----------------------------
-- Table structure for source_project_manpower
-- ----------------------------
DROP TABLE IF EXISTS `source_project_manpower`;
CREATE TABLE `source_project_manpower` (
  `project_manpower_id` varchar(32) NOT NULL COMMENT '项目人力明细ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '参与员工',
  `input` double(10,2) DEFAULT NULL COMMENT '人力投入',
  `note` text COMMENT '工作内容',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目ID',
  `project_sub_id` varchar(32) DEFAULT NULL COMMENT '所服务子项目',
  `date` date DEFAULT NULL COMMENT '盘点日期',
  `type` tinyint(1) DEFAULT NULL COMMENT '日期类型(1季、2月、3双月)',
  PRIMARY KEY (`project_manpower_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-项目人力明细（source_project_manpower）';

-- ----------------------------
-- Table structure for source_project_maxvalue
-- ----------------------------
DROP TABLE IF EXISTS `source_project_maxvalue`;
CREATE TABLE `source_project_maxvalue` (
  `project_maxvalue_id` varchar(32) NOT NULL COMMENT '项目最大负荷数ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `maxval` int(4) DEFAULT NULL COMMENT '数值',
  `total_work_hours` double(5,2) DEFAULT NULL COMMENT '部门工时总数',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`project_maxvalue_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-项目最大负荷数（source_project_maxvalue）';

-- ----------------------------
-- Table structure for source_promotion_element_scheme
-- ----------------------------
DROP TABLE IF EXISTS `source_promotion_element_scheme`;
CREATE TABLE `source_promotion_element_scheme` (
  `scheme_id` varchar(32) NOT NULL COMMENT '方案ID',
  `customer_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户ID',
  `scheme_name` varchar(50) DEFAULT NULL COMMENT '方案名称',
  `company_age` int(3) DEFAULT NULL COMMENT '司龄',
  `curt_name_per` varchar(30) DEFAULT NULL COMMENT '绩效短名',
  `curt_name_eval` int(4) DEFAULT NULL COMMENT '能力评价短名',
  `certificate_id` varchar(32) DEFAULT NULL COMMENT '证书ID',
  `certificate_type_id` varchar(32) DEFAULT NULL COMMENT '证书类型ID',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modify_user_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `start_date` datetime DEFAULT NULL COMMENT '生效时间',
  `invalid_date` datetime DEFAULT NULL COMMENT '失效时间',
  PRIMARY KEY (`scheme_id`),
  KEY `idx_sId` (`scheme_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='晋级要素方案(promotion_element_scheme)';

-- ----------------------------
-- Table structure for source_promotion_plan
-- ----------------------------
DROP TABLE IF EXISTS `source_promotion_plan`;
CREATE TABLE `source_promotion_plan` (
  `promotion_plan_id` varbinary(32) NOT NULL COMMENT '职级晋升方案ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `rank_name_af` varbinary(5) DEFAULT NULL COMMENT '晋级后简称',
  `scheme_id` varchar(32) DEFAULT NULL COMMENT '方案ID',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modify_user_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`promotion_plan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='职级晋升方案(`mup-source`.source_promotion_plan)';

-- ----------------------------
-- Table structure for source_promotion_results
-- ----------------------------
DROP TABLE IF EXISTS `source_promotion_results`;
CREATE TABLE `source_promotion_results` (
  `promotion_results_id` varchar(32) NOT NULL COMMENT '员工晋级结果ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `company_age` int(2) DEFAULT NULL COMMENT '司龄',
  `oranization_parent_id` varchar(32) DEFAULT NULL COMMENT '父部门ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `full_path` varchar(20) DEFAULT NULL COMMENT '部门全路径',
  `performance_id` varchar(32) DEFAULT NULL COMMENT '绩效ID',
  `sequence_id` varchar(32) DEFAULT NULL COMMENT '主序列ID',
  `is_key_talent` int(1) DEFAULT NULL COMMENT '是否关键人才',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `entry_date` date DEFAULT NULL COMMENT '入职时间',
  `show_index` int(1) DEFAULT NULL COMMENT '排序',
  `rank_name` varchar(5) DEFAULT NULL COMMENT '当前简称',
  `show_index_af` int(1) DEFAULT NULL COMMENT '晋级后排序',
  `rank_name_af` varchar(5) DEFAULT NULL COMMENT '晋级后简称',
  `status` int(1) DEFAULT NULL COMMENT '晋级状态',
  `status_date` date DEFAULT NULL COMMENT '结果时间',
  `promotion_date` date DEFAULT NULL COMMENT '晋级时间',
  PRIMARY KEY (`promotion_results_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='员工晋级结果(`mup-source`.source_promotion_results)';

-- ----------------------------
-- Table structure for source_promotion_total
-- ----------------------------
DROP TABLE IF EXISTS `source_promotion_total`;
CREATE TABLE `source_promotion_total` (
  `promotion_total_id` varchar(32) NOT NULL COMMENT '员工晋级统计ID',
  `scheme_id` varbinary(32) DEFAULT NULL COMMENT '方案ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `rank_name` varchar(5) DEFAULT NULL COMMENT '当前简称',
  `rank_name_af` varchar(5) DEFAULT NULL COMMENT '晋级后简称',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `status` int(1) DEFAULT NULL COMMENT '申请状态',
  `condition_prop` double(3,1) DEFAULT NULL COMMENT '条件符合占比',
  `total_date` date DEFAULT NULL COMMENT '统计时间',
  PRIMARY KEY (`promotion_total_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='员工占比统计(promotion_total)';

-- ----------------------------
-- Table structure for source_recruit_channel
-- ----------------------------
DROP TABLE IF EXISTS `source_recruit_channel`;
CREATE TABLE `source_recruit_channel` (
  `recruit_channel_id` varchar(32) NOT NULL COMMENT '应聘渠道ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `channel_id` varchar(32) DEFAULT NULL COMMENT '渠道ID',
  `position_id` varchar(32) DEFAULT NULL COMMENT '职位ID',
  `employ_num` int(4) DEFAULT NULL COMMENT '已招人数',
  `outlay` double(10,2) DEFAULT NULL COMMENT '花费（单位：元）',
  `start_date` datetime DEFAULT NULL COMMENT '招聘开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '招聘结束时间',
  `days` int(4) DEFAULT NULL COMMENT '天数',
  `recruit_public_id` varchar(32) DEFAULT NULL COMMENT '招聘发布ID',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `c_id` varchar(32) DEFAULT NULL COMMENT '标识ID',
  PRIMARY KEY (`recruit_channel_id`),
  UNIQUE KEY `un_cid` (`c_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-招聘渠道（recruit_channel）';

-- ----------------------------
-- Table structure for source_recruit_public
-- ----------------------------
DROP TABLE IF EXISTS `source_recruit_public`;
CREATE TABLE `source_recruit_public` (
  `recruit_public_id` varchar(32) NOT NULL COMMENT '招聘发布ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '发布机构ID',
  `position_id` varchar(32) DEFAULT NULL COMMENT '招聘岗位ID',
  `employ_num` int(4) DEFAULT NULL COMMENT '已招人数（过）',
  `plan_num` int(4) DEFAULT NULL COMMENT '计划人数',
  `start_date` datetime DEFAULT NULL COMMENT '招聘开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '招聘结束时间',
  `days` int(4) DEFAULT NULL COMMENT '天数',
  `resume_num` int(4) DEFAULT NULL COMMENT '简历数（过）',
  `interview_num` int(4) DEFAULT NULL COMMENT '面试数（过）',
  `offer_num` int(4) DEFAULT NULL COMMENT '通过数（过）',
  `entry_num` int(4) DEFAULT NULL COMMENT '入职数（过）',
  `is_public` tinyint(1) DEFAULT NULL COMMENT '招聘岗位状态',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`recruit_public_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-招聘发布（`mup-source`.source_recruit_public）';

-- ----------------------------
-- Table structure for source_recruit_result
-- ----------------------------
DROP TABLE IF EXISTS `source_recruit_result`;
CREATE TABLE `source_recruit_result` (
  `recruit_result_id` varchar(32) NOT NULL COMMENT '应聘结果ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `recruit_result_key` varchar(20) DEFAULT NULL COMMENT '应聘者编号',
  `username` varchar(6) DEFAULT NULL COMMENT '姓名',
  `sex` char(3) DEFAULT NULL COMMENT '性别',
  `age` double(5,2) DEFAULT NULL COMMENT '年龄',
  `degree_id` varchar(32) DEFAULT NULL COMMENT '学历ID',
  `major` varchar(30) DEFAULT NULL COMMENT '专业',
  `school` varchar(30) DEFAULT NULL COMMENT '毕业院校',
  `is_resume` tinyint(1) DEFAULT NULL COMMENT '是否投简',
  `is_interview` tinyint(1) DEFAULT NULL COMMENT '是否面试',
  `is_offer` tinyint(1) DEFAULT NULL COMMENT '是否通过',
  `is_entry` tinyint(1) DEFAULT NULL COMMENT '是否入职',
  `url` text COMMENT '简历url',
  `recruit_public_id` varchar(32) DEFAULT NULL COMMENT '招聘发布ID',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `c_id` varchar(32) DEFAULT NULL COMMENT '标识ID',
  PRIMARY KEY (`recruit_result_id`),
  UNIQUE KEY `un_cid` (`c_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-招聘结果（`mup-source`.source_recruit_result）';

-- ----------------------------
-- Table structure for source_recruit_value
-- ----------------------------
DROP TABLE IF EXISTS `source_recruit_value`;
CREATE TABLE `source_recruit_value` (
  `recruit_value_id` varchar(32) NOT NULL COMMENT '招聘年度费用',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `budget_value` double(12,6) DEFAULT NULL COMMENT '预算度本（单位：万）',
  `outlay` double(12,6) DEFAULT NULL COMMENT '已花成本（单位：万、过）',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  `c_id` varchar(32) DEFAULT NULL COMMENT '标识ID',
  PRIMARY KEY (`recruit_value_id`),
  UNIQUE KEY `un_cid` (`c_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-招聘年度费用（年）（`mup-source`.source_recruit_value）';

-- ----------------------------
-- Table structure for source_run_off_record
-- ----------------------------
DROP TABLE IF EXISTS `source_run_off_record`;
CREATE TABLE `source_run_off_record` (
  `run_off_record_id` varchar(32) NOT NULL COMMENT '流失记录ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `run_off_id` varchar(32) DEFAULT NULL COMMENT '流失项ID',
  `where_abouts` varchar(200) DEFAULT NULL COMMENT '流失去向',
  `run_off_date` datetime NOT NULL COMMENT '流失日期',
  `is_warn` int(1) DEFAULT NULL COMMENT '是否有过预警',
  `re_hire` int(1) DEFAULT NULL COMMENT '建议重新录用',
  PRIMARY KEY (`run_off_record_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-流失记录(`mup-source`.source_run_off_record)';

-- ----------------------------
-- Table structure for source_salary
-- ----------------------------
DROP TABLE IF EXISTS `source_salary`;
CREATE TABLE `source_salary` (
  `id` varchar(32) NOT NULL COMMENT '工资ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `structure_id` varchar(32) DEFAULT NULL COMMENT '工资结构ID',
  `salary_value` double(10,5) DEFAULT NULL COMMENT '值（单位：万元）',
  `year_month` int(10) NOT NULL COMMENT '年月',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`year_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-工资明细（`mup-source`.source_salary）';

-- ----------------------------
-- Table structure for source_sales_ability
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_ability`;
CREATE TABLE `source_sales_ability` (
  `emp_id` varchar(32) NOT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `status` int(1) DEFAULT NULL COMMENT '考核状态',
  `check_date` date DEFAULT NULL COMMENT '考核时间',
  PRIMARY KEY (`emp_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务能力考核(`mup-source`.source_sales_ability)';

-- ----------------------------
-- Table structure for source_sales_detail
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_detail`;
CREATE TABLE `source_sales_detail` (
  `sales_detail_id` varchar(32) NOT NULL COMMENT '销售明细ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `product_id` varchar(32) DEFAULT NULL COMMENT '商品ID',
  `product_number` int(5) DEFAULT NULL COMMENT '商品数量',
  `sales_money` double(10,2) DEFAULT NULL COMMENT '实际销售金额（单位：元）',
  `sales_profit` double(10,2) DEFAULT NULL COMMENT '销售利润（单位：元）',
  `sales_province_id` varchar(32) DEFAULT NULL COMMENT '销售地点(省)',
  `sales_city_id` varchar(32) DEFAULT NULL COMMENT '销售地点(市)',
  `sales_date` date DEFAULT NULL COMMENT '销售时间',
  PRIMARY KEY (`sales_detail_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='销售明细(`mup-source`.source_sales_detail)';

-- ----------------------------
-- Table structure for source_sales_emp
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_emp`;
CREATE TABLE `source_sales_emp` (
  `emp_id` varchar(32) NOT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `user_name_ch` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `performance_id` varchar(32) DEFAULT NULL COMMENT '绩效',
  `degree_id` varchar(32) DEFAULT NULL COMMENT '学位ID',
  `team_id` varchar(32) DEFAULT NULL COMMENT '团队ID',
  PRIMARY KEY (`emp_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='销售人员信息表(`mup-source`.source_sales_emp)';

-- ----------------------------
-- Table structure for source_sales_emp_rank
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_emp_rank`;
CREATE TABLE `source_sales_emp_rank` (
  `emp_id` varchar(32) NOT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_rank` int(6) DEFAULT NULL COMMENT '排名',
  `organization_id` varchar(32) NOT NULL COMMENT '所排名机构',
  `rank_date` date NOT NULL COMMENT '时间',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  `proportion_id` varchar(32) DEFAULT NULL COMMENT '占比ID',
  PRIMARY KEY (`emp_id`,`organization_id`,`rank_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='员工销售排名(`mup-source`.source_sales_emp_rank)';

-- ----------------------------
-- Table structure for source_sales_emp_target
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_emp_target`;
CREATE TABLE `source_sales_emp_target` (
  `emp_id` varchar(32) DEFAULT NULL,
  `sales_target` double(10,4) DEFAULT NULL,
  `return_amount` double(10,4) DEFAULT NULL,
  `payment` double(10,4) DEFAULT NULL,
  `year_month` int(6) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_sales_org_target
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_org_target`;
CREATE TABLE `source_sales_org_target` (
  `organization_id` varchar(32) NOT NULL COMMENT '机构ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `sales_target` double(10,4) DEFAULT NULL COMMENT '目标销售额(单位：万元)',
  `sales_number` int(10) DEFAULT NULL COMMENT '目标销售量',
  `sales_profit` double(10,4) DEFAULT NULL COMMENT '目标销售利润',
  `return_amount` double(10,4) DEFAULT NULL COMMENT '回款额(单位：万元)',
  `payment` double(10,4) DEFAULT NULL COMMENT '已回款(单位：万元)',
  `year_month` int(6) NOT NULL COMMENT '年月',
  PRIMARY KEY (`organization_id`,`year_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='机构考核(`mup-source`.source_sales_org_target)';

-- ----------------------------
-- Table structure for source_sales_pro_target
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_pro_target`;
CREATE TABLE `source_sales_pro_target` (
  `product_id` varchar(32) NOT NULL COMMENT '产品ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `sales_target` double(14,4) DEFAULT NULL COMMENT '目标销售额(单位：万元',
  `return_amount` double(14,4) DEFAULT NULL COMMENT '回款额(单位：万元)',
  `payment` double(10,4) DEFAULT NULL COMMENT '已回款(单位：万元)',
  `year_month` int(6) NOT NULL COMMENT '年月',
  PRIMARY KEY (`product_id`,`year_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='产品考核(`mup-source`.source_sales_pro_target)';

-- ----------------------------
-- Table structure for source_sales_team_rank
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_team_rank`;
CREATE TABLE `source_sales_team_rank` (
  `team_id` varchar(32) NOT NULL COMMENT '团队ID',
  `organization_id` varchar(32) NOT NULL COMMENT '机构ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `team_rank` int(6) DEFAULT NULL COMMENT '排名',
  `rank_date` date NOT NULL COMMENT '时间',
  `year_month` int(6) NOT NULL COMMENT '年月',
  PRIMARY KEY (`team_id`,`organization_id`,`year_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source_sales_team_target
-- ----------------------------
DROP TABLE IF EXISTS `source_sales_team_target`;
CREATE TABLE `source_sales_team_target` (
  `team_id` varchar(32) NOT NULL COMMENT '团队ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `sales_target` int(10) DEFAULT NULL COMMENT '目标销售额',
  `payment` int(10) DEFAULT NULL COMMENT '已回款',
  `return_amount` int(10) DEFAULT NULL COMMENT '回款额',
  `year_month` int(6) NOT NULL COMMENT '年月',
  PRIMARY KEY (`team_id`,`year_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='团队考核(`mup-source`.source_sales_team_target)';

-- ----------------------------
-- Table structure for source_satfac_genre_score
-- ----------------------------
DROP TABLE IF EXISTS `source_satfac_genre_score`;
CREATE TABLE `source_satfac_genre_score` (
  `satfac_score_id` varchar(32) NOT NULL COMMENT '满意度评分ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `satfac_genre_id` varchar(32) DEFAULT NULL COMMENT '满意度分类ID',
  `soure` double(5,4) DEFAULT NULL COMMENT '得分',
  `date` date DEFAULT NULL COMMENT '报告日期',
  PRIMARY KEY (`satfac_score_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-满意度评分（source_satfac_genre_score）';

-- ----------------------------
-- Table structure for source_satfac_organ
-- ----------------------------
DROP TABLE IF EXISTS `source_satfac_organ`;
CREATE TABLE `source_satfac_organ` (
  `satfac_organ_id` varchar(32) NOT NULL COMMENT '满意机构评分ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `organization_name` varchar(20) DEFAULT NULL COMMENT '机构名称',
  `soure` double(4,4) DEFAULT NULL COMMENT '得分',
  `date` date DEFAULT NULL COMMENT '报告日期',
  PRIMARY KEY (`satfac_organ_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史表-满意度机构评分（source_satfac_organ）';

-- ----------------------------
-- Table structure for source_share_holding
-- ----------------------------
DROP TABLE IF EXISTS `source_share_holding`;
CREATE TABLE `source_share_holding` (
  `id` varchar(32) NOT NULL COMMENT '持股明细ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `usre_name_ch` varchar(5) DEFAULT NULL COMMENT '中文名',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `full_path` varchar(200) DEFAULT NULL COMMENT '机构全路径',
  `now_share` int(4) DEFAULT NULL COMMENT '当前数量（单位：股）',
  `confer_share` int(4) DEFAULT NULL COMMENT '授予数量（单位：股）',
  `price` double(10,4) DEFAULT NULL COMMENT '授予价（单位：元/股）',
  `hold_period` varchar(5) DEFAULT NULL COMMENT '持有期',
  `sub_num` int(4) DEFAULT NULL COMMENT '最近减持数量',
  `sub_date` datetime DEFAULT NULL COMMENT '最近减持时间',
  `year` int(4) DEFAULT NULL COMMENT '年',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-持股明细（source_share_holding）';

-- ----------------------------
-- Table structure for source_target_benefit_value
-- ----------------------------
DROP TABLE IF EXISTS `source_target_benefit_value`;
CREATE TABLE `source_target_benefit_value` (
  `target_benefit_value_id` varchar(32) NOT NULL COMMENT '目标人均效益ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `target_value` double(6,2) DEFAULT NULL COMMENT '目标人均效益值',
  `year` int(4) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`target_benefit_value_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-目标人均效益（年）（`mup-source`.source_target_benefit_value）';

-- ----------------------------
-- Table structure for source_trade_profit
-- ----------------------------
DROP TABLE IF EXISTS `source_trade_profit`;
CREATE TABLE `source_trade_profit` (
  `trade_profit_id` char(32) NOT NULL COMMENT '营业利润ID',
  `customer_id` char(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `sales_amount` decimal(10,4) DEFAULT NULL COMMENT '营业收入、销售总额（单位：万元）',
  `expend_amount` double(10,2) DEFAULT NULL COMMENT '营业支出、企业总成本（单位：万元）',
  `gain_amount` decimal(10,4) DEFAULT NULL COMMENT '营业利润（单位：万元）',
  `target_value` double(10,4) DEFAULT NULL COMMENT '目标人均效益值（单位：万元）',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`trade_profit_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-营业利润（`mup-source`.source_trade_profit）';

-- ----------------------------
-- Table structure for source_train_outlay
-- ----------------------------
DROP TABLE IF EXISTS `source_train_outlay`;
CREATE TABLE `source_train_outlay` (
  `train_outlay_id` varchar(32) NOT NULL COMMENT '培训花费用ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `outlay` double(6,2) DEFAULT NULL COMMENT '花费（万元）汇',
  `date` datetime DEFAULT NULL COMMENT '时间',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `year` int(4) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`train_outlay_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-培训实际花费用（source_train_outlay）';

-- ----------------------------
-- Table structure for source_train_plan
-- ----------------------------
DROP TABLE IF EXISTS `source_train_plan`;
CREATE TABLE `source_train_plan` (
  `train_plan_id` varchar(32) NOT NULL COMMENT '培训计划ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `train_name` varchar(20) DEFAULT NULL COMMENT '计划名称',
  `train_num` int(3) DEFAULT NULL COMMENT '计划培训人数',
  `date` datetime DEFAULT NULL COMMENT '制定发布时间',
  `start_date` datetime DEFAULT NULL COMMENT '计划实施开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '计划实施结束时间',
  `year` int(4) DEFAULT NULL COMMENT '计划实施年',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（0进行中，1已完成）',
  PRIMARY KEY (`train_plan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-培训计划(train_plan)';

-- ----------------------------
-- Table structure for source_train_satfac
-- ----------------------------
DROP TABLE IF EXISTS `source_train_satfac`;
CREATE TABLE `source_train_satfac` (
  `train_satfac_id` varchar(32) NOT NULL COMMENT '培训满意度ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `soure` double(5,4) DEFAULT NULL COMMENT '得分',
  `year` int(4) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`train_satfac_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-培训满意度（source_train_satfac）';

-- ----------------------------
-- Table structure for source_train_value
-- ----------------------------
DROP TABLE IF EXISTS `source_train_value`;
CREATE TABLE `source_train_value` (
  `train_value_id` varchar(32) NOT NULL COMMENT '培训年度预算费用ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `budget_value` double(6,2) DEFAULT NULL COMMENT '预算度本（万元）',
  `year` int(4) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`train_value_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-培训年度预算费用（年）（source_train_value）';

-- ----------------------------
-- Table structure for source_v_dim_emp
-- ----------------------------
DROP TABLE IF EXISTS `source_v_dim_emp`;
CREATE TABLE `source_v_dim_emp` (
  `v_dim_emp_id` varchar(32) DEFAULT NULL COMMENT '员工维度ID',
  `emp_id` varchar(32) NOT NULL COMMENT '员工ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_key` varchar(20) DEFAULT NULL COMMENT '员工编码',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `user_name_ch` varchar(5) DEFAULT NULL COMMENT '中文名',
  `emp_hf_id` varchar(32) DEFAULT NULL COMMENT '上级ID',
  `emp_hf_key` varchar(20) DEFAULT NULL COMMENT '上级编码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `img_path` varchar(32) DEFAULT NULL COMMENT '头像',
  `passtime_or_fulltime` varchar(1) DEFAULT NULL COMMENT '工作性质(p、f、s)',
  `contract` varchar(20) DEFAULT NULL COMMENT '合同性质',
  `blood` varchar(2) DEFAULT NULL COMMENT '血型',
  `age` int(3) DEFAULT NULL COMMENT '年龄（生日日期计算）',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `nation` varchar(9) DEFAULT NULL COMMENT '民族',
  `degree_id` varchar(32) DEFAULT NULL COMMENT '学历ID',
  `degree` varchar(20) DEFAULT NULL COMMENT '学历',
  `native_place` varchar(90) DEFAULT NULL COMMENT '籍贯',
  `country` varchar(20) DEFAULT NULL COMMENT '国籍',
  `birth_place` varchar(90) DEFAULT NULL COMMENT '出生地',
  `birth_date` datetime DEFAULT NULL COMMENT '出生日期',
  `national_id` varchar(60) DEFAULT NULL COMMENT '证件号码',
  `national_type` varchar(60) DEFAULT NULL COMMENT '证件类型',
  `marry_status` int(1) DEFAULT NULL COMMENT '婚姻状况',
  `patty_name` varchar(20) DEFAULT NULL COMMENT '政治面貌',
  `company_age` int(3) DEFAULT NULL COMMENT '司龄（入职日期计算）',
  `is_key_talent` int(1) DEFAULT NULL COMMENT '是否关键人才',
  `organization_parent_id` varchar(32) DEFAULT NULL COMMENT '单位ID（dept为：2）',
  `organization_parent_name` varchar(60) DEFAULT NULL COMMENT '单位名称',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `organization_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `position_id` varchar(32) DEFAULT NULL COMMENT '岗位ID',
  `position_name` varchar(60) DEFAULT NULL COMMENT '岗位名称',
  `sequence_id` varchar(32) DEFAULT NULL COMMENT '主序列ID',
  `sequence_name` varchar(60) DEFAULT NULL COMMENT '主序列名称',
  `sequence_sub_id` varchar(32) DEFAULT NULL COMMENT '子序列ID',
  `sequence_sub_name` varchar(60) DEFAULT NULL COMMENT '子序列名称',
  `ability_id` varchar(32) DEFAULT NULL COMMENT '职位层级ID',
  `ability_name` varchar(60) DEFAULT NULL COMMENT '职位层级名称',
  `job_title_id` varchar(32) DEFAULT NULL COMMENT '职衔ID',
  `job_title_name` varchar(20) DEFAULT NULL COMMENT '职衔名称',
  `ability_lv_id` varchar(32) DEFAULT NULL COMMENT '职级ID',
  `ability_lv_name` varchar(60) DEFAULT NULL COMMENT '职级名称',
  `rank_name` varchar(5) DEFAULT NULL COMMENT '主子序列层级职级（短名）',
  `performance_id` varchar(32) DEFAULT NULL COMMENT '绩效ID',
  `performance_name` varchar(20) DEFAULT NULL COMMENT '绩效名称',
  `population_id` varchar(32) DEFAULT NULL COMMENT '人群ID',
  `population_name` varchar(20) DEFAULT NULL COMMENT '人群名称',
  `tel_phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq号码',
  `wx_code` varchar(32) DEFAULT NULL COMMENT '微信号码',
  `address` varchar(60) DEFAULT NULL COMMENT '住址',
  `contract_unit` varchar(32) DEFAULT NULL COMMENT '合同单位',
  `work_place_id` varchar(32) DEFAULT NULL COMMENT '工作地点ID',
  `work_place` varchar(60) DEFAULT NULL COMMENT '工作地点',
  `regular_date` datetime DEFAULT NULL COMMENT '转正日期',
  `channel_id` varchar(32) DEFAULT NULL COMMENT '应聘渠道ID',
  `speciality` varchar(32) DEFAULT NULL COMMENT '特长',
  `is_regular` int(1) DEFAULT NULL COMMENT '是否正职（1 主岗位  0 负岗位）',
  `area_id` varchar(32) DEFAULT NULL COMMENT '地区',
  `entry_date` datetime DEFAULT NULL COMMENT '入职日期',
  `run_off_date` datetime DEFAULT NULL COMMENT '流失日期',
  `refresh_date` datetime DEFAULT NULL COMMENT '更新日期',
  `mark` tinyint(1) DEFAULT NULL COMMENT '标记',
  PRIMARY KEY (`emp_id`),
  KEY `index_eId` (`emp_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='维度表-员工信息维(`mup-source`.source_v_dim_emp)';

-- ----------------------------
-- Table structure for source_welfare_cpm
-- ----------------------------
DROP TABLE IF EXISTS `source_welfare_cpm`;
CREATE TABLE `source_welfare_cpm` (
  `id` varchar(32) NOT NULL COMMENT '企业福利明细ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `user_name_ch` varchar(50) DEFAULT NULL COMMENT '员工中文名',
  `cpm_id` varchar(32) DEFAULT NULL COMMENT '企业福利ID',
  `welfare_value` double(10,4) DEFAULT NULL COMMENT '发放金额（单位：元）',
  `date` date DEFAULT NULL COMMENT '发放时间',
  `year_month` int(6) NOT NULL COMMENT '年月',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`year_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-企业福利明细（货币）（`mup-source`.source_welfare_cpm）';

-- ----------------------------
-- Table structure for source_welfare_nfb
-- ----------------------------
DROP TABLE IF EXISTS `source_welfare_nfb`;
CREATE TABLE `source_welfare_nfb` (
  `id` varchar(32) NOT NULL COMMENT '福利ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `user_name_ch` varchar(50) DEFAULT NULL COMMENT '员工中文名',
  `nfb_id` varchar(32) DEFAULT NULL COMMENT '国家固定福利ID',
  `welfare_value` double(10,4) DEFAULT NULL COMMENT '缴费金额（单位：元）',
  `date` date DEFAULT NULL COMMENT '缴费时间',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-固定福利明细（`mup-source`.source_welfare_nfb）';

-- ----------------------------
-- Table structure for source_welfare_uncpm
-- ----------------------------
DROP TABLE IF EXISTS `source_welfare_uncpm`;
CREATE TABLE `source_welfare_uncpm` (
  `id` varchar(32) NOT NULL COMMENT '企业福利明细ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `emp_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `uncpm_id` varchar(32) DEFAULT NULL COMMENT '福利类别ID',
  `note` varchar(50) DEFAULT NULL COMMENT '备注',
  `date` date DEFAULT NULL COMMENT '发放时间',
  `year_month` int(6) DEFAULT NULL COMMENT '年月',
  `refresh` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务表-企业福利明细（非货币）（`mup-source`.source_welfare_uncpm）';


drop table if exists source_manpower_cost_item;
create table source_manpower_cost_item
(
   manpower_cost_item_id varchar(32) not null comment '人力成本结构ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   item_id              varchar(32) comment '结构ID',
   item_name            varchar(20) comment '结构名称',
   item_cost            double(15,2) comment '结构成本值（单位：万元）',
   `year_month`         int(6) comment '年月',
   show_index           int(1) comment '排序',
   primary key (manpower_cost_item_id)
);
alter table source_manpower_cost_item comment '业务表-人力成本结构（manpower_cost_item）';

