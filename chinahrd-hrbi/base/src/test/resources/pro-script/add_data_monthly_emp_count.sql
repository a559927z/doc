DROP PROCEDURE IF EXISTS add_data_monthly_emp_count;
CREATE PROCEDURE add_data_monthly_emp_count (in p_ym int(4))
BEGIN

	DECLARE i INT DEFAULT 1;
	-- 指定年
	DELETE FROM monthly_emp_count WHERE `year_month` LIKE concat(p_ym, '%');

		WHILE i <= 12 DO

			IF i<10 THEN
				
				CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 
											concat(date_format(LAST_DAY(CONCAT(p_ym,'-', '0', i, '-', '01')),'%Y-%m-'), '01'),
											1); -- 月度员工数

				CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 
											concat(LAST_DAY(CONCAT(p_ym,'-', '0', i, '-', '01'))) 
											, 0);
			ELSE

				CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 
											concat(date_format(LAST_DAY(CONCAT(p_ym,'-', i, '-', '01')),'%Y-%m-'), '01'),
											1); -- 月度员工数
				CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 
											concat(LAST_DAY(CONCAT(p_ym,'-', i, '-', '01'))) 
											, 0);

			END IF;

			SET i = i + 1;
		END WHILE;

END;

 CALL add_data_monthly_emp_count (2010);
 CALL add_data_monthly_emp_count (2011);
 CALL add_data_monthly_emp_count (2012);
 CALL add_data_monthly_emp_count (2013);
 CALL add_data_monthly_emp_count (2014);


		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-01-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-01-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-02-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-02-28', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-03-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-03-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-04-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-04-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-05-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-05-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-07-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-07-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-09-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-09-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-10-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-10-31', 0); 
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-11-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-11-30', 0); 

-- DELETE FROM monthly_emp_count WHERE `year_month` LIKE concat(2010, '%');
-- 
-- 		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-01-01', 1); -- 月度员工数
-- 		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-01-31', 0);