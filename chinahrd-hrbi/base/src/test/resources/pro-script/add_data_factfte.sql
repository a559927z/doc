DROP PROCEDURE IF EXISTS add_data_factfte;
CREATE PROCEDURE add_data_factfte (in p_ym int(4))
BEGIN

	DECLARE i INT DEFAULT 1;
	-- 指定年
	DELETE FROM monthly_emp_count WHERE `year_month` LIKE concat(p_ym, '%');

		WHILE i <= 12 DO

			IF i<10 THEN
				
		
				CALL pro_fetch_factfte(CONCAT(p_ym,'-', '0', i, '-', '11'),'demo');
			ELSE
				CALL pro_fetch_factfte(CONCAT(p_ym,'-',  i, '-', '11'),'demo');

			END IF;

			SET i = i + 1;
		END WHILE;

END;


 CALL add_data_factfte (2011);
 CALL add_data_factfte (2012);
 CALL add_data_factfte (2013);
 CALL add_data_factfte (2014);
 CALL add_data_factfte (2015);

-- 
-- 		CALL pro_init_factfte('2013-01-11','demo');
			CALL pro_fetch_factfte('2015-09-11','demo');
			CALL pro_fetch_factfte('2015-11-11','demo');