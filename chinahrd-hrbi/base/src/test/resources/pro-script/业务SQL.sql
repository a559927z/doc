###################################高绩效未晋升人###################################
SELECT DISTINCT
	(vde.emp_id) AS empId,
	vde.user_name_ch AS userNameCh,
	vde.img_path AS imgPath,
	vde.position_name AS positionName,
	vde.sequence_name AS sequenceName,
	vde.sequence_sub_name AS sequenceSubName,
	vde.job_title_name AS jobTitleName,
	vde.ability_name AS abilityName,
	vde.performance_name AS performanceName
FROM
	(
		-- 高绩效
		SELECT t.emp_id FROM performance_change t
		WHERE t.emp_id IN (
					-- 负责人里的所有直接下属
					SELECT t3.emp_id FROM v_dim_emp t3 
					WHERE t3.emp_hf_id IN (
						SELECT t2.emp_id FROM v_dim_emp t2 
						-- 机构里的所有负责人
						 INNER JOIN (
								SELECT t.emp_id, t.customer_id FROM organization_emp_relation t WHERE t.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0' AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f' 
						) tt on tt.emp_id = t2.emp_id and tt.customer_id = t2.customer_id ) AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
				) 
				AND t.performance_id IN ( 
						SELECT per.performance_id FROM dim_performance per 
						INNER JOIN warn_rang t1 on (per.curt_name BETWEEN t1.min_per AND t1.max_per ) AND t1.type = 4 WHERE per.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f' 
				)
		-- 连续4次
		AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
		AND t.year_month BETWEEN 201312 AND 201506
		GROUP BY t.emp_id having count(t.emp_id)=(SELECT max_per FROM warn_rang WHERE type = 3) 
) tt 
INNER JOIN (
	select 
		vde.emp_id, vde.user_name_ch, vde.img_path, vde.position_name, vde.sequence_name, vde.sequence_sub_name, vde.job_title_name, vde.ability_name, vde.performance_name 
	from v_dim_emp vde inner join performance_change t on vde.rank_name = t.rank_name where t.year_month = 201312 
) vde on vde.emp_id= tt.emp_id 


###################################当前机构里负责人的直接下属###################################
SELECT 
	*
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
		AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
;




###################################昨天到两周前的假期（包括法定假期和法定休息和公司提供）###################################
SELECT count(1) FROM holiday 
WHERE (type=1 or type=2 or type = 3)
 AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY)  ;



###################################两周里工作天数###################################
-- 方式一(开发使用)：
		SELECT count(1) FROM holiday 
		WHERE type=4
		 AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY) ;


-- 方式二(测试使用)：
		-- 两周期里的假期数
		SET @holiday = (SELECT count(1) FROM holiday 
										WHERE (type=1 or type=2 or type = 3)
										 AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY) );


		-- 实际两周里工作天数 = 员工所有状态 - 两周期里的假期数
		SELECT t.emp_id, (count(t.emp_id) - @holiday) AS workDays
		FROM emp_status t
		WHERE date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY)  GROUP BY emp_id





###################################员工实际两周里工作天数###################################

-- 方式一(开发使用)：
	SELECT t.emp_id, (count(t.emp_id)) AS workDays
	FROM emp_status t
	WHERE status_type =1 AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY)  GROUP BY emp_id


-- 方式二(测试使用)：
		SELECT t.emp_id, (count(t.emp_id) - @holiday) AS workDays
		FROM emp_status t
		WHERE status_type NOT IN (2,3,4,5,6, 7, 8, 9) AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY)  GROUP BY emp_id




###################################两周前的加班总时长###################################

SELECT emp_id, sum(hour_count) AS otHour
FROM emp_overtime_day 
WHERE date BETWEEN DATE_ADD(CURDATE(),INTERVAL -2 WEEK) AND NOW() GROUP BY emp_id;





###################################工作欠皆（两周里加长时长平均超过4小时的人）###################################
-- 方式一（开发使用）：
		-- 考察加班周期
		SET @otCyc = (SELECT max_per FROM warn_rang WHERE type = 6);
		-- 考察里的工作天数
		SET @workDayNum = (SELECT count(1) FROM holiday 
										 WHERE type = 4 
											 AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -@otCyc WEEK) AND DATE_ADD(CURDATE(),INTERVAL -1 DAY) );


		SELECT count(tt.emp_id) AS lNGNum
		FROM (
				SELECT 
							emp_id,  customer_id,  organization_id, 
							sum(hour_count) otHour, -- 加班周期里的加班时长
							@workDayNum AS workDayNum, 			-- 周期里的工作天数
							(IFNULL(sum(hour_count),0) / @workDayNum) AS avgOt	-- （近两周平均加班=近两周加班时数/近两周工作日天数）
					FROM emp_overtime_day 
					WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
						AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -@otCyc WEEK) AND NOW() 
			GROUP BY emp_id
		) tt
		WHERE tt.avgOt > 4
		;



###################################低绩效没调整###################################
			-- 低绩岗位没变Num
-- 					SELECT count(tt.performance_id) AS lPerNum
				SELECT * 
					FROM (
						SELECT t.performance_id, tmp.under_emp_id FROM
						tmp_stewards_under tmp					-- 直接下属
						INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
								-- 低绩效范围
								AND t.performance_id IN
															(
																SELECT per.performance_id FROM dim_performance per
																INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 5
																WHERE per.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
															)
								AND	t.`year_month` = 201312
						WHERE tmp.organization_id = '423238847d2311e58aee08606e0aa89a' 

					) tt
					INNER JOIN 
						(
							SELECT t.performance_id, tmp.under_emp_id  FROM
							tmp_stewards_under tmp 					-- 直接下属
							INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
									AND t.performance_id IN
																(
																	SELECT per.performance_id FROM dim_performance per
																	INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 5
																	WHERE per.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
																)
									AND	t.`year_month` = 201412
								WHERE tmp.organization_id = '423238847d2311e58aee08606e0aa89a' 
							) ttt
						-- 周期里 低绩没变
					on tt.under_emp_id = ttt.under_emp_id
						-- 周期里 岗位没变
					WHERE tt.under_emp_id NOT IN (
								SELECT t2.emp_id FROM job_change t2 WHERE  t2.customer_id = '423238847d2311e58aee08606e0aa89a' and t2.change_type != 3
									and  EXTRACT(YEAR_MONTH FROM t2.change_date) BETWEEN '201312' and EXTRACT(YEAR_MONTH FROM CURDATE())
							)
					;



###################################高绩效没晋升###################################
	SELECT *
	FROM (
							SELECT t.performance_id, tmp.under_emp_id, t.rank_name
							FROM tmp_stewards_under tmp 
							INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
									-- 高绩效范围
									AND t.performance_id IN
																(
																	SELECT per.performance_id FROM dim_performance per
																	INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 4
																	WHERE per.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
																)
									AND	t.`year_month` = 201312
							WHERE tmp.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
	) tt
	INNER JOIN (
							SELECT t.performance_id, tmp.under_emp_id
							FROM tmp_stewards_under tmp 
							INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
									-- 高绩效范围
									AND t.performance_id IN
																(
																	SELECT per.performance_id FROM dim_performance per
																	INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 4
																	WHERE per.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
																)
									AND	t.`year_month` = 201412
							WHERE tmp.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0'
	) ttt
	on tt.under_emp_id = ttt.under_emp_id
	INNER JOIN v_dim_emp t3 on t3.rank_name = tt.rank_name AND t3.emp_id = tt.under_emp_id AND t3.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
;























###################################201305月初和月末各部门在职的人###################################
			SELECT 
					mec.monthly_emp_id, 'b5c9410dc7e4422c8e0189c7f8056b5f', tt1.organization_id, tt1.organization_full_path,
					mec.month_begin, tt1.month_end, 201305
			FROM monthly_emp_count mec
			LEFT JOIN (
					SELECT t.organization_id, 'b5c9410dc7e4422c8e0189c7f8056b5f', t.full_path organization_full_path,
									IFNULL(tt.month_end,0) month_end, 201305
					FROM dim_organization t
					LEFT JOIN (
						SELECT DISTINCT
							de.organization_id,
							count(1) month_end
						FROM
							v_dim_emp de
						WHERE
							de.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
						AND de.entry_date <= '2013-05-31'  AND ( de.run_off_date > '2013-05-31'  or de.run_off_date IS NULL) 
						GROUP BY de.organization_id
					) tt on tt.organization_id = t.organization_id
					ORDER BY t.full_path
			) tt1 on tt1.organization_id = mec.organization_id
			WHERE mec.`YEAR_MONTH` = 201305
			ORDER BY mec.organization_full_path
			;

###################################201305月初各部门在职的人###################################
				SELECT t.organization_id, t.full_path organization_full_path,
								IFNULL(tt.month_begin,0) month_begin, 201305
				FROM dim_organization t
				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id,
						count(1) month_begin
					FROM
						v_dim_emp de
					WHERE
						de.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f' 
					AND de.entry_date <= '2013-05-01' AND ( de.run_off_date > '2013-05-01' or de.run_off_date IS NULL) 
					GROUP BY de.organization_id
				) tt on tt.organization_id = t.organization_id
			ORDER BY t.full_path;



###################################8月份的主动离职人数###################################
SELECT 
		t4.monthly_emp_id, t4.customer_id, t4.organization_id, t4.organization_full_path, 
		t4.month_begin, t4.month_end, t4.month_count, t4.month_begin_sum, t4.month_end_sum, t4.month_count_sum,
		tt.actCount, 0
FROM monthly_emp_count t4 
LEFT JOIN (
		SELECT count(1) actCount, t2.organization_id FROM run_off_record t1 
		left JOIN v_dim_emp t2 on t2.emp_id = t1.emp_id AND t1.customer_id = t2.customer_id
		left JOIN dim_run_off t3 on t1.run_off_id = t3.run_off_id AND t3.customer_id = t1.customer_id
		where 
			t1.run_off_date BETWEEN '2015-08-01' AND '2015-08-31' 
		AND t3.type = 1
		GROUP BY t2.organization_id
	) tt on tt.organization_id = t4.organization_id
WHERE t4.`year_month` = 201508



###################################当前员工下的单位###################################
-- 方式一：(开发使用)
	SELECT t.organization_parent_name '（分）公司名称', t.organization_name '部门名称' FROM v_dim_emp t;

-- 方式二：(开发使用、测试使用)
SELECT t.organization_parent_name '（分）公司名称', t.organization_name  '部门名称'  FROM performance_change t
WHERE t.emp_id = '7a87f146577611e5a5e608606e0aa89a' 
 AND t.performance_change_id = 
	(SELECT t8.performance_change_id FROM performance_change t8 WHERE t.emp_key = t8.emp_key ORDER BY `year_month` DESC LIMIT 0,1);


-- 方式三：（测试使用）
	SELECT t2.organization_name '（分）公司名称', t1.organization_name  '部门名称' FROM emp_position_relation t
	INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id AND t1.enabled=1 and t1.effect_date <=NOW() and t1.Expiry_Date is null
	INNER JOIN dim_organization t2 on t2.full_path = SUBSTRING_INDEX(t1.full_path, '_', 2)
	where t.emp_id = '7a87f146577611e5a5e608606e0aa89a' ;

-- 方式四：（测试使用）
	SELECT 
			t4.organization_id '（分）公司ID', t4.organization_Key '（分）公司名Key', t4.organization_name '（分）公司名称'
	FROM dim_organization t4
	WHERE t4.full_path = (
		SELECT SUBSTRING_INDEX(t3.full_path,'_',2) 
		FROM dim_organization t3 
			WHERE t3.organization_id = (
				SELECT t1.organization_id FROM emp_position_relation t
				INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id AND t1.enabled=1 and t1.effect_date <=NOW() and t1.Expiry_Date is null
				where t.emp_id = '7a87f146577611e5a5e608606e0aa89a' )
	);



###################################在职员工###################################
-- 视图-正职并在职的员工
SELECT * FROM v_dim_emp t where t.run_off_date is NULL and is_regular ;
-- 在职的员工
SELECT t FROM dim_emp
WHERE t.enabled=1 AND t.effect_date <=NOW() AND (t.expiry_date IS NULL OR t.expiry_date> NOW()) AND t.run_off_date IS NULL


###################################2014年前在职员工###################################
SELECT * FROM dim_emp de
de.entry_date <= '2014-01-01' AND ( de.run_off_date >'2014-01-01' or de.run_off_date IS NULL)

###################################已离职员工###################################
select * from run_off_record

###################################有风险同时还在公司里的人###################################
SELECT t1.* FROM dimission_risk t1
INNER JOIN run_off_record t2 on t1.customer_id = t2.customer_id 
AND t1.emp_id != t2.emp_id;

###################################有离职风险的人###################################

SELECT t1.* FROM dimission_risk t1

###################################无离职风险的###################################
select * from v_dim_emp where not in( select emp_id from dimission_risk)



###################################当前机构下所有子孙部门###################################
SELECT
	t1.* FROM v_dim_organization t1
WHERE
	locate(
		(
			SELECT t.full_path FROM v_dim_organization t
			WHERE t.organization_id = 'f34c9b714a0711e5a91808606e0aa89a'
			AND t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
		),
		t1.full_path
	)
AND t1.organization_id != 'f34c9b714a0711e5a91808606e0aa89a'
AND t1.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
ORDER BY t1.depth;



###################################功能权限###################################
SELECT
u.user_name,
r.role_name,
f.full_path,
fr.item_codes
FROM
dim_user AS u
LEFT JOIN user_role_relation AS ur ON ur.user_id = u.user_id
left join dim_role r on r.role_id = ur.role_id
LEFT JOIN function_role_relation AS fr ON ur.role_id = fr.role_id 
left join dim_function f on f.function_id = fr.function_id
where u.user_id = '1191d082b2be4ab98e1a8c7102afb41c'



###################################行业均值###################################

SELECT
 bu.business_unit_name,pr.profession_name, pr.pcb_value
FROM v_dim_organization org 
INNER JOIN v_dim_business_unit bu
on org.business_unit_id = bu.business_unit_id
inner JOIN dim_profession pr on pr.profession_id = bu.profession_id
where org.organization_id='fcb4d31b3470460f93be81cf1dd64cf0'


###################################每个部门fte值###################################
		select 
			sum(tt.fulltimeSum) as fulltimeSum, sum(tt.passtimeSum) as passtimeSum, sum(tt.overtimeSum) as overtimeSum,
			(sum(tt.fulltimeSum) + sum(tt.passtimeSum) + sum(tt.overtimeSum)) as fteCalc 
		into @fulltimeSum, @passtimeSum,@overtimeSum, @fteCalc
		from(
			SELECT IFNULL(sum(t.fulltimeSum),0.0) as fulltimeSum, 0.0 as passtimeSum, 0.0 as overtimeSum
			FROM (
				SELECT
					count(ep.emp_id) as fulltimeSum, ep.organization_id
				FROM
					emp_position_relation ep
				INNER JOIN (SELECT vo.organization_id FROM v_dim_organization vo where full_path LIKE '%ZRW_GZ_product%') org2
				on org2.organization_id = ep.organization_id
				LEFT JOIN dim_emp de on de.emp_id = ep.emp_id
				where de.passtime_or_fulltime = 'f'
				GROUP BY
					org2.organization_id
			) t
			union ALL
			SELECT 0.0 as fulltimeSum, IFNULL(sum(t2.passtimeSum), 0.0) * 0.5 as passtimeSum, 0.0 as overtimeSum
			FROM (
				SELECT
					count(ep.emp_id) as passtimeSum
				FROM
					emp_position_relation ep
				INNER JOIN (SELECT vo.organization_id FROM v_dim_organization vo where full_path LIKE '%ZRW_GZ_product%') org2
				on org2.organization_id = ep.organization_id
				LEFT JOIN dim_emp de on de.emp_id = ep.emp_id
				where de.passtime_or_fulltime = 'p'
				GROUP BY
					org2.organization_id
			) t2
			union ALL
			SELECT 0.0 as fulltimeSum, 0.0 as passtimeSum, t3.overtimeSum as overtimeSum
			FROM (
				SELECT
					IFNULL(sum(eo.hour_count),0.0) / 8 as overtimeSum
				FROM
					emp_position_relation ep
				INNER JOIN (SELECT vo.organization_id, vo.organization_name FROM v_dim_organization vo where full_path LIKE '%ZRW_GZ_product%') org2
					on org2.organization_id = ep.organization_id
				inner JOIN emp_overtime eo on eo.emp_id = ep.emp_id
				GROUP BY
					org2.organization_id
			) t3

		) tt;

select @fulltimeSum, @passtimeSum,@overtimeSum, @fteCalc





###################################人均效益变化幅度###################################
		SELECT t1.organization_id as organizationId, t1.organization_name as organizationName, t1.benefit_value as benefitValue, t1.`year_month` as yearMonth
		from 
		(
		SELECT ff.organization_id, ff.organization_name, ff.benefit_value, ff.`year_month` 
		FROM fact_fte ff 
		INNER JOIN (
			SELECT vo.organization_id FROM v_dim_organization vo
			WHERE locate(
				(SELECT vo1.full_path from v_dim_organization vo1
					where vo1.organization_id ='fcb4d31b3470460f93be81cf1dd64cf0' and vo1.is_single=1)
				, vo.full_path)
		) org ON ff.organization_id = org.organization_id
		WHERE 
			ff.`year_month` = (SELECT MAX(ff1.`year_month`) FROM `fact_fte` ff1)
			and ff.organization_id !='fcb4d31b3470460f93be81cf1dd64cf0'
			
				AND ff.benefit_value <180
			
		ORDER BY ff.benefit_value
		LIMIT 0,3
		) t1
		union ALL
		select * FROM
		(
		SELECT ff.organization_id, ff.organization_name, ff.benefit_value, ff.`year_month`
		FROM fact_fte ff 
		INNER JOIN (
			SELECT vo.organization_id FROM v_dim_organization vo
			WHERE locate(
				(SELECT vo1.full_path from v_dim_organization vo1
					where vo1.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0' and vo1.is_single=1)
				, vo.full_path)
		) org ON ff.organization_id = org.organization_id
		WHERE 
			ff.`year_month` = (SELECT ( MAX(ff1.`year_month`)-1) FROM `fact_fte` ff1)
			and ff.organization_id != 'fcb4d31b3470460f93be81cf1dd64cf0'
							AND ff.benefit_value <180
		ORDER BY ff.benefit_value
		LIMIT 0,3
		) t2;


-- ------------------------------------------------------------------------------------



		SELECT 
			t1.organization_id as organizationId, t1.organization_name as organizationName, 
			t1.benefit_value as `value`, t1.range_per as range_per, t1.`year_month` as yearMonth
		from 
		(
		SELECT ff.organization_id, ff.organization_name,ff.benefit_value, ff.range_per, ff.`year_month` 
		FROM fact_fte ff 
		INNER JOIN (
			SELECT vo.organization_id FROM v_dim_organization vo
			WHERE locate(
				(SELECT vo1.full_path from v_dim_organization vo1
					where vo1.organization_id ='fcb4d31b3470460f93be81cf1dd64cf0' and vo1.is_single=1)
				, vo.full_path)
		) org ON ff.organization_id = org.organization_id
		WHERE 
			ff.`year_month` = (SELECT ( MAX(ff1.`year_month`)-1) FROM `fact_fte` ff1)
			and ff.organization_id !='fcb4d31b3470460f93be81cf1dd64cf0'
		ORDER BY ff.range_per
		LIMIT 0,3
		) t1
		union ALL
		select * FROM
		(
		SELECT ff.organization_id, ff.organization_name,ff.benefit_value, ff.range_per, ff.`year_month`
		FROM fact_fte ff 
		INNER JOIN (
			SELECT vo.organization_id FROM v_dim_organization vo
			WHERE locate(
				(SELECT vo1.full_path from v_dim_organization vo1
					where vo1.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0' and vo1.is_single=1)
				, vo.full_path)
		) org ON ff.organization_id = org.organization_id
		WHERE 
			ff.`year_month` = (SELECT MAX(ff1.`year_month`) FROM `fact_fte` ff1)
			and ff.organization_id != 'fcb4d31b3470460f93be81cf1dd64cf0'
		ORDER BY ff.range_per
		LIMIT 0,3
		) t2;

-- ------------------------------
SELECT
	ff2.organization_id as organizationId, ff2.organization_name as organizationName, 
	ff2.benefit_value as benefitValue, ff2.range_per as rangePer, ff2.`year_month` as yearMonth
FROM(
		SELECT ff.organization_id, ff.`year_month`
		FROM fact_fte ff 
		INNER JOIN (
			SELECT vo.organization_id FROM v_dim_organization vo
			WHERE locate(
				(SELECT vo1.full_path from v_dim_organization vo1
					where vo1.organization_id = 'fcb4d31b3470460f93be81cf1dd64cf0' and vo1.is_single=1)
				, vo.full_path)
		) org ON ff.organization_id = org.organization_id
		WHERE 
			ff.`year_month` = (SELECT MAX(ff1.`year_month`) FROM `fact_fte` ff1) 
			and ff.organization_id != 'fcb4d31b3470460f93be81cf1dd64cf0'
		ORDER BY ff.range_per
		LIMIT 0,3
) t
INNER JOIN fact_fte ff2 on ff2.organization_id = t.organization_id
where ff2.`year_month` = (t.`year_month`-1) or ff2.`year_month` = t.`year_month`
ORDER BY ff2.`year_month`;

###################################员工岗位Key###################################
SELECT t1.emp_id, t.emp_key FROM emp_position_relation t1 
INNER JOIN dim_emp t on t.emp_id = t1.emp_id
where
t.run_off_date is NULL AND
 t.emp_key BETWEEN 'abc_700' and 'abc_710'



