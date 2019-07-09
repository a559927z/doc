#=======================================================a1=======================================================
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `a1`;
CREATE PROCEDURE a1(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
  BEGIN

		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE tmp int DEFAULT 0;
		DECLARE diff INT DEFAULT (select datediff(pCurDate,'2014-01-01')); 
    DECLARE fy1 int  DEFAULT 0;
    DECLARE fy2 int  DEFAULT 0;
		START TRANSACTION;
     
						 WHILE tmp != diff DO
						 set @hours = (SELECT FLOOR(1 + (RAND() * 5)));
             set @minutes = (SELECT FLOOR(1 + (RAND() * 50)));
             set fy1 = (SELECT FLOOR(1 + (RAND() * 1000)));
             set fy2 = (SELECT FLOOR(1 + (RAND() * 30)));
             set @days = date_add(pCurDate, interval -tmp day);
						 SET @startD = cast((SELECT concat(DATE_FORMAT(date_add(pCurDate, interval -tmp day), '%Y-%m-%d'), " 18:00:00")) as datetime);
						 SET @endD = date_add(date_add(@startD,interval @hours hour),interval @minutes minute);       
             

             insert into soure_emp_overtime_other_day (
              c_id,
              customer_id,
              emp_id,
              emp_key,
              user_name_ch,
              population_id,
              startdate,
              enddate,
              organization_id,
              checkwork_type_id,
              days
              )  select replace(uuid(),'-',''),customer_id,emp_id,emp_key,user_name_ch,population_id,@startD,@endD,organization_id,'7f533380bf574dd9916972a08808e121',@days from v_dim_emp LIMIT  fy1, fy2;

							SET tmp = tmp + 1;
						  
              END WHILE;
	            COMMIT;
END;



CALL a1('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-12-18');

CALL pro_fetch_emp_overtime_other_day('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

