
select 
*-- sum(t.fulltimeSum), t.organization_name 
from(
	SELECT
		count(ep.emp_id) as fulltimeSum,
		org2.organization_id,
		org2.organization_key,
		org2.organization_name,
		org2.full_path
	FROM
		emp_position_relation ep
	inner JOIN (SELECT org.organization_id, org.organization_key,org.organization_name, org.full_path FROM v_dim_organization org WHERE org.is_single = 1 and org.full_path like '%ZRW%') org2
	on org2.organization_id = ep.organization_id
	LEFT JOIN dim_emp de on de.emp_id = ep.emp_id
	where de.passtime_or_fulltime = 'f'
	GROUP BY
		org2.organization_id
)t
;


				SELECT
					count(ep.emp_id), org2.organization_name
				FROM
					emp_position_relation ep
				INNER JOIN (SELECT vo.organization_id, vo.organization_name FROM v_dim_organization vo where full_path LIKE '%ZRW%') org2
					on org2.organization_id = ep.organization_id
				inner JOIN emp_overtime eo on eo.emp_id = ep.emp_id
				GROUP BY
					org2.organization_id, org2.organization_name;


SELECT
	count(ep.emp_id) as fulltime,
	0.0 as passtime,
	org2.organization_name,
	org2.full_path
FROM
	emp_position_relation ep
LEFT JOIN (SELECT org.organization_id,org.organization_name, org.full_path FROM dim_organization org WHERE org.is_single = 1) org2
on org2.organization_id = ep.organization_id
LEFT JOIN dim_emp de on de.emp_id = ep.emp_id
where de.passtime_or_fulltime = 'f' 
GROUP BY
	org2.organization_id

UNION ALL

SELECT
	0.0 as fulltime,
	count(ep.emp_id) * 0.5 as passtime,
	org2.organization_name,
	org2.full_path
FROM
	emp_position_relation ep
LEFT JOIN (SELECT org.organization_id,org.organization_name, org.full_path FROM dim_organization org WHERE org.is_single = 1) org2
on org2.organization_id = ep.organization_id
LEFT JOIN dim_emp de on de.emp_id = ep.emp_id
where de.passtime_or_fulltime = 'p' 
GROUP BY
	org2.organization_id
;







select org.organization_name from dim_organization org
left join (SELECT org.organization_id, org.organization_key,org.organization_name, org.full_path FROM dim_organization org WHERE org.is_single = 1) org2
on org2.organization_id = org.organization_id
where org2.full_path LIKE 'ZRW_GZ%'