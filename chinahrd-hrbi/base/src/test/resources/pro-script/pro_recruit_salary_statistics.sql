drop procedure if exists pro_recruit_salary_statistics;
create procedure `pro_recruit_salary_statistics`(_year_months INT, _years int,_customerid char(32))
begin 
			INSERT INTO recruit_salary_statistics(
				recruit_salary_statistics_id,
                                customer_id,
				position_id,
				position_name,
				rank_name,
				avg_sal,
				emp_total,
				year_months
      )
			SELECT 
				REPLACE(UUID(),'-',''),
                                _customerid, 
				rp.position_id AS positionId,
				dp.position_name AS positionName,
				vde.rank_name AS rankName,
				AVG(AES_DECRYPT(pay.pay_should, 'hrbi')) AS pay,COUNT(*) AS EMP_TOTAL, _year_months
			FROM recruit_public rp
			LEFT JOIN dim_position dp ON rp.customer_id = dp.customer_id AND dp.position_id = rp.position_id
			LEFT JOIN v_dim_emp vde ON vde.customer_id = dp.customer_id AND vde.position_id = dp.position_id
			LEFT JOIN pay ON pay.customer_id = vde.customer_id AND pay.emp_id = vde.emp_id AND pay.`year_month` = _year_months
			WHERE
				rp.customer_id = _customerid
				AND rp.`year` = _years
			GROUP BY rp.position_id, dp.position_name, vde.rank_name, REPLACE (UUID(), '-', ''),customer_id;
         commit;
end;