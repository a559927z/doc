-- 12.29
SELECT replace(UUID(),'-',''),  t.organization_id, t.full_path organization_full_path,
								IFNULL(tt.month_begin,0) month_begin, ttp.month_begin, ttf.month_begin
				FROM dim_organization t
				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(de.emp_id) month_begin
					FROM v_dim_emp de
					WHERE
						1=1
-- 2013.05.01 <= 2015-01-01 AND (2015-02-01 > 2015-01-01 or NULL)
-- 作用：找出当前指定时间的在职员工。（员工入职日期少于当前时间，并且员工的离职日期大于当前时间或员工没有离职）
					AND de.entry_date <= '2015-11-1' AND ( de.run_off_date > '2015-11-1' or de.run_off_date IS NULL) 

					GROUP BY de.organization_id
				) tt on tt.organization_id = t.organization_id

				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(de.emp_id) month_begin
					FROM v_dim_emp de
					WHERE
						1=1
-- 2013.05.01 <= 2015-01-01 AND (2015-02-01 > 2015-01-01 or NULL)
-- 作用：找出当前指定时间的在职员工。（员工入职日期少于当前时间，并且员工的离职日期大于当前时间或员工没有离职）
					AND de.entry_date <= '2015-11-1' AND ( de.run_off_date > '2015-11-1' or de.run_off_date IS NULL) 
					AND de.passtime_or_fulltime = 'p'
					GROUP BY de.organization_id
				) ttp on ttp.organization_id = t.organization_id


				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(de.emp_id) month_begin
					FROM v_dim_emp de
					WHERE
						1=1
-- 2013.05.01 <= 2015-01-01 AND (2015-02-01 > 2015-01-01 or NULL)
-- 作用：找出当前指定时间的在职员工。（员工入职日期少于当前时间，并且员工的离职日期大于当前时间或员工没有离职）
					AND de.entry_date <= '2015-11-1' AND ( de.run_off_date > '2015-11-1' or de.run_off_date IS NULL) 
					AND de.passtime_or_fulltime = 'f'
					GROUP BY de.organization_id
				) ttf on ttf.organization_id = t.organization_id

				ORDER BY t.full_path;



-- 12 12
		SELECT 
			 tb.emp_id empId,
			 tb.emp_key empKey,
			-- tmp.user_name_ch usernameCh,
			 tb.organization_id organizationId,
			 tb.position_name positionName,
			 tb.sequence_name sequenceName,
			 tb.sequence_sub_name sequenceSubName,
			 tb.ability_match abilityMatch,
			 tb.exam_date examDate
		 FROM talent_development_exam tb
		 INNER JOIN (
		 		SELECT 
					t3.emp_id, t3.user_name_ch
				FROM v_dim_emp t3
				-- 负责人里的所有直接下属
				WHERE t3.emp_hf_id IN (
						SELECT t2.emp_id FROM v_dim_emp t2
						-- 机构里的所有负责人
						INNER JOIN (
								SELECT t.emp_id, t.customer_id FROM organization_emp_relation t
								WHERE t.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
									AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
							) tt on tt.emp_id = t2.emp_id and tt.customer_id = t2.customer_id
					)
				AND t3.run_off_date IS NULL
				AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
		 ) tmp on tmp.emp_id = tb.emp_id
		






	SELECT  emp_id, emp_key, rank_name rName, change_date cDate, change_type changeType
	FROM job_change t
	WHERE 
1=1
-- AND t.change_type = 3 -- t.change_type = 1
-- 	AND (
-- 		YEAR (NOW()) = YEAR (t.change_date)
-- 		AND QUARTER (NOW()) = QUARTER (t.change_date)
-- 	)
	AND t.emp_id in (
		SELECT 
				t3.emp_id
			FROM v_dim_emp t3
			-- 负责人里的所有直接下属
			WHERE t3.emp_hf_id IN (
					SELECT t2.emp_id FROM v_dim_emp t2
					-- 机构里的所有负责人
					INNER JOIN (
							SELECT t.emp_id, t.customer_id FROM organization_emp_relation t
							WHERE t.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
								AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
						) tt on tt.emp_id = t2.emp_id and tt.customer_id = t2.customer_id
				)
			-- AND t3.run_off_date IS NULL
			AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
	)
-- GROUP BY emp_key, change_date
ORDER BY emp_key, t.change_date desc 




-- 12.10
SELECT * FROM job_change t WHERE t.change_type = 1
AND (YEAR(NOW()) = YEAR(t.change_date)
AND QUARTER(NOW()) = QUARTER(t.change_date))

-- 12 .9
SELECT count(t1.emp_id) -- , t2.module_type moduleType, t2.360_ability_name abilityName, t2.standard_score standardScore, t2.gain_score gainScore, t2.score score 
FROM 
	360_time t1 
INNER JOIN
		(SELECT 
			t3.emp_id, t3.customer_id
		FROM v_dim_emp t3
		-- 负责人里的所有直接下属
		WHERE t3.emp_hf_id IN (
				SELECT t2.emp_id FROM v_dim_emp t2
				-- 机构里的所有负责人
				INNER JOIN (
						SELECT t.emp_id, t.customer_id FROM organization_emp_relation t
						WHERE t.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
							AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
					) tt on tt.emp_id = t2.emp_id and tt.customer_id = t2.customer_id
			)
		AND t3.run_off_date IS NULL
		AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
		) tt on tt.emp_id = t1.emp_id AND tt.customer_id = t1.customer_id
INNER JOIN 360_report t2 on t2.360_time_id = t1.360_time_id AND t2.customer_id = t1.customer_id
WHERE t1.report_date = (SELECT MAX(report_date) FROM 360_time WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f')

GROUP BY t1.emp_id
ORDER BY module_type
;



SELECT t1.emp_id, t2.module_type moduleType, t2.360_ability_name abilityName, t2.standard_score standardScore, t2.gain_score gainScore, t2.score score 
FROM 
		(SELECT 
			t3.emp_id, t3.customer_id
		FROM v_dim_emp t3
		-- 负责人里的所有直接下属
		WHERE t3.emp_hf_id IN (
				SELECT t2.emp_id FROM v_dim_emp t2
				-- 机构里的所有负责人
				INNER JOIN (
						SELECT t.emp_id, t.customer_id FROM organization_emp_relation t
						WHERE t.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
							AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
					) tt on tt.emp_id = t2.emp_id and tt.customer_id = t2.customer_id
			)
		AND t3.run_off_date IS NULL
		AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
		) tt
INNER JOIN 360_time t1 on tt.emp_id = t1.emp_id  
			AND t1.report_date = (SELECT MAX(report_date) FROM 360_time WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f')
			AND tt.customer_id = t1.customer_id
INNER JOIN 360_report t2 on t2.360_time_id = t1.360_time_id AND t2.customer_id = t1.customer_id


-- WHERE t1.report_date = (SELECT MAX(report_date) FROM 360_time WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f')

-- GROUP BY 360_ability_name
ORDER BY module_type
;


-- 12.1
-- 12.1
SELECT 
		t3.emp_id empId, t3.user_name_ch, t4.show_index showIndex, t4.ability_id abilityId, t4.ability_name abilityName,
		t3.sex sex, t3.birth_date birthDate, t3.marry_status marryStatus, t3.age, t3.company_age , t3.blood,
		t5.type
FROM v_dim_emp t3
INNER JOIN dim_ability t4 on t3.ability_id = t4.ability_id 
		AND t3.customer_id = t4.customer_id 
-- 员工性格
INNER JOIN emp_personality t5 on t5.customer_id = t3.customer_id AND t5.emp_id = t3.emp_id
-- 负责人里的所有直接下属
WHERE t3.emp_hf_id IN (
				SELECT t2.emp_id FROM v_dim_emp t2
				-- 机构里的所有负责人
				INNER JOIN (
						SELECT t.emp_id, t.customer_id FROM organization_emp_relation t
						WHERE t.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
							AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
					) tt on tt.emp_id = t2.emp_id and tt.customer_id = t2.customer_id
			)
		AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
ORDER BY t4.show_index ASC;

SELECT 
		t3.emp_id empId, t4.show_index showIndex, t4.ability_id abilityId, t4.ability_name abilityName,
		t3.sex sex, t3.birth_date birthDate, t3.marry_status marryStatus
FROM dim_ability t4
left JOIN v_dim_emp t3 on t3.ability_id = t4.ability_id 
		AND t3.customer_id = t4.customer_id 
		-- 负责人里的所有直接下属
		AND t3.emp_hf_id IN (
				SELECT t2.emp_id FROM v_dim_emp t2
				-- 机构里的所有负责人
				INNER JOIN (
						SELECT t.emp_id, t.customer_id FROM organization_emp_relation t
						WHERE t.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
							AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
					) tt on tt.emp_id = t2.emp_id and tt.customer_id = t2.customer_id
			)
		AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
ORDER BY t4.show_index ASC;





-- 11. 19
SELECT count(1) FROM holiday 
WHERE type=1 or type=2
 AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY)  ;




SELECT emp_id, sum(hour_count), 0
FROM emp_overtime_day 
where date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND NOW() GROUP BY emp_id;



SELECT tt.emp_id, tt.otHour, tt.workDays, tt.lifeNot from (
SELECT t.emp_id, 
			IFNULL(t1.otHour,0) AS otHour, (count(t.emp_id) - 4) AS workDays,
			(IFNULL(t1.otHour,0)/(count(t.emp_id) - 4)) AS lifeNot
FROM emp_status t
LEFT JOIN (
		SELECT emp_id, sum(hour_count) otHour
		FROM emp_overtime_day 
		where date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND NOW() GROUP BY emp_id
) t1 on t.emp_id = t1.emp_id
where status_type =1 AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY)  GROUP BY emp_id
) tt
where tt.lifeNot >4


-- 11.17
SELECT count(t.emp_key)
FROM performance_change t 
WHERE t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
	-- 高绩效范围
	AND t.performance_id IN
					(SELECT per.performance_id FROM dim_performance per
						INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 4
						WHERE per.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
					)
	-- 考察周期职级没变
	AND t.emp_id IN (
		SELECT t1.emp_id 
		FROM performance_change t1 
		INNER JOIN performance_change t2 ON t1.rank_name = t2.rank_name AND t2.`year_month` = 201312
			AND t1.customer_id = t2.customer_id
		WHERE t1.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f' AND t1.`year_month` = 201412
	);






-- 11.10
SELECT replace(uuid(),'-',''),dim_organization.customer_id, dim_organization.organization_key, dim_organization.full_path, emp_key,GROUP_CONCAT(emp_key), count(1)   FROM dim_organization
	left JOIN v_dim_emp on  v_dim_emp.organization_id = dim_organization.organization_id
GROUP BY dim_organization.organization_key
 ORDER BY full_path



-- 10
SELECT
	vde.emp_id AS empId,
	CONCAT(
		vde.user_name,
		'（',
		vde.user_name_ch,
		'）'
	) AS userNameCh,
	ror.is_warn AS isWarn,
	vde.organization_name AS organizationName,
	vde.is_key_talent AS isKeyTalent,
	vde.position_name AS positionName,
	vde.sequence_id AS sequenceId,
	vde.sequence_name AS sequenceName,
	vde.ability_id AS abilityId,
	vde.ability_name AS abilityName,
	per.performance_key AS performanceKey,
	vde.performance_name AS performanceName,
	tt.key_talent_type_id AS keyTalentTypeId,
	tt.key_talent_type_name AS keyTalentTypeName,
	dro.type AS roType,
	ror.run_off_date AS roDate,
	ror.where_abouts AS whereAbouts
FROM
	run_off_record ror
INNER JOIN v_dim_emp vde ON ror.emp_id = vde.emp_id
AND ror.customer_id = vde.customer_id
AND ror.run_off_date = vde.run_off_date
INNER JOIN dim_performance per ON per.performance_id = vde.performance_id
AND per.customer_id = vde.customer_id
INNER JOIN dim_run_off dro ON ror.run_off_id = dro.run_off_id
AND dro.customer_id = ror.customer_id
INNER JOIN dim_key_talent_type tt ON tt.key_talent_type_id = vde.key_talent_type_id
AND tt.customer_id = vde.customer_id
WHERE
	ror.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
AND ror.run_off_date BETWEEN '2015-04-01'
AND '2015-06-30'
AND EXISTS (
	SELECT
		1
	FROM
		v_dim_organization t1
	WHERE
		locate(
			(
				SELECT
					t.full_path
				FROM
					v_dim_organization t
				WHERE
					t.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
				AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
			),
			t1.full_path
		)
	AND t1.organization_id = vde.organization_id
)
AND per.performance_key IN ('2,3')
LIMIT 10

-- 10.30
'abc_707','abc_701','abc_702','jxzhang'

-- 10.26
INSERT INTO budget_emp_number VALUES(
REPLACE(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f',
	'15e24f67bddd47ee94a9a09e7ff1d2b8',
	230,
	'c578a03a3adb4a6eaa327e130259e339',
	2015
);

INSERT INTO job_change VALUES(
	REPLACE(uuid(),'-',''),
	'b5c9410dc7e4422c8e0189c7f8056b5f',
	'e673c034589448a0bc05830ebf85c4d6', 
	'jxzhang',
	'2014-08-01 01:00:00',
	2,
	'1e233b6ca5e840c089d5ceff8684a7ac',
	'开发部',
	'60ceacf5f6ae4dc6aaab30310138a673',
	'产品人员',
	'05b1a81244b411e590e608606e0aa89a',
	'业务序列',
	'36beb363725311e58f6008606e0aa89a',
'拓展序列',
'1f4ff3d344b511e590e608606e0aa89a',
'独立工作者',
'2210998470b111e58a1708606e0aa89a',
	'5级','BE3.5'
);

-- 10.20
INSERT INTO job_change VALUES(
replace(UUID(), '-',''),'b5c9410dc7e4422c8e0189c7f8056b5f','e673c034589448a0bc05830ebf85c4d6','jxzhang','2014-3-10 01:00:00',
1,'1e233b6ca5e840c089d5ceff8684a7ac','开发部','2bfb5fd40b2a46c4896bcd8c83cfa45d','高级开发工程师','05b1f3cb44b411e590e608606e0aa89a',
'专业序列','36bdb5bb725311e58f6008606e0aa89a','产品序列','1f4ff3d344b511e590e608606e0aa89a','独立工作者','22100f3470b111e58a1708606e0aa89a','4级','PP3.4'
);

INSERT INTO performance_change VALUES(
replace(UUID(), '-',''),'b5c9410dc7e4422c8e0189c7f8056b5f','e673c034589448a0bc05830ebf85c4d6','jxzhang',
'9f570168958c4f23a7d2b86a52f8b79b','广州项目管理中心','1e233b6ca5e840c089d5ceff8684a7ac','开发部',
'2bfb5fd40b2a46c4896bcd8c83cfa45d','高级开发工程师',
'baa8e31644b611e590e608606e0aa89a','二星','633f74f870b211e58a1708606e0aa89a','高级',79.5,201506
);

INSERT INTO evaluation_report VALUES(
	replace(UUID(), '-', ''), 'b5c9410dc7e4422c8e0189c7f8056b5f', 'e673c034589448a0bc05830ebf85c4d6',
	'2015年度360年度测评报告', 8.3,8.5,8.9,8.7, '/doc/2014年度360年度测评报告.doc',1
);

INSERT INTO soure_evaluation_report VALUES(
	'b5c9410dc7e4422c8e0189c7f8056b5f', 'jxzhang',
	'2014年度360年度测评报告', 8.3,8.5,8.9,8.7, '/doc/2014年度360年度测评报告.doc',1
);
INSERT INTO soure_evaluation_report VALUES(
	'b5c9410dc7e4422c8e0189c7f8056b5f', 'jxzhang',
	'2013年度360年度测评报告', 8.6,	8.6,	9.3,	8.8, '/doc/2013年度360年度测评报告.doc',1
);
INSERT INTO soure_evaluation_report VALUES(
	'b5c9410dc7e4422c8e0189c7f8056b5f', 'jxzhang',
	'2013晋升适岗测评报告',NULL,NULL,NULL,NULL, '/doc/2013晋升适岗测评报告.doc',0
);

-- 10.07

SELECT
	vde.emp_id AS empId,
	CONCAT(
		vde.user_name,
		'（',
		vde.user_name_ch,
		'）'
	) AS userNameCh,
	ror.is_warn AS isWarn,
	vde.organization_name AS organizationName,
	vde.position_name AS positionName,
	vde.sequence_name AS sequenceName,
	abl.ability_name AS abilityName,
	per.performance_name AS performanceName,
	tt.key_talent_type_name AS keyTalentTypeName,
	dro.type AS roType,
	ror.run_off_date AS roDate,
	ror.where_abouts AS whereAbouts
FROM
	run_off_record ror
INNER JOIN v_dim_emp vde ON ror.emp_id = vde.emp_id
AND ror.customer_id = vde.customer_id
AND ror.run_off_date = vde.run_off_date
INNER JOIN dim_ability abl ON abl.ability_id = vde.ability_id
AND abl.customer_id = vde.customer_id
INNER JOIN dim_performance per ON per.performance_id = vde.performance_id
AND per.customer_id = vde.customer_id
INNER JOIN dim_run_off dro ON ror.run_off_id = dro.run_off_id
AND dro.customer_id = ror.customer_id
INNER JOIN dim_key_talent_type tt ON tt.key_talent_type_id = vde.key_talent_type_id
AND tt.customer_id = vde.customer_id
WHERE
	ror.customer_id = ?
AND ror.run_off_date BETWEEN ?
AND ?

-- 10.12

DELETE FROM job_change;
replace into job_change(emp_job_change_id, customer_id, emp_id, organization_id, position_id, sequence_id, ability_id, change_date, change_type)
SELECT
REPLACE(UUID(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f',
	t.emp_id,
	t.organization_id,
	t.position_id,
	t.sequence_id,
	t.ability_id,
	tt.effect_date as change_date,
	3
FROM
	v_dim_emp t
INNER JOIN dim_emp tt on tt.emp_id = t.emp_id
;

SELECT * from job_change where ability_lv_id is null ;

