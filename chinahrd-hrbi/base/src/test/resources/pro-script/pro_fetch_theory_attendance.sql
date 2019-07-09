DROP PROCEDURE IF EXISTS `pro_fetch_theory_attendance`;
CREATE PROCEDURE pro_fetch_theory_attendance(in p_customer_id VARCHAR(32), in p_years VARCHAR(4))
begin
	declare num INTEGER default 0;
	declare dat date;
  declare d varchar(2);
  declare uuid1 char(32);
  declare uuid2 char(32);
  declare wh int (1) DEFAULT 8;
  declare dayoffwh int (1) DEFAULT 0;
	START TRANSACTION;
	while num <= 364 do

						SELECT  DATE_ADD(concat(p_years,'-01-01'),INTERVAL num DAY) into dat from qzb_0608;
						select DATE_FORMAT(dat, '%w') into d from qzb_0608;
						select replace(uuid(),'-','') into uuid1 from qzb_0608;
						if  d='6'   then
							INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, 0, p_customer_id, p_years);
						end if;
						if  d='0'   then
							INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, 0, p_customer_id, p_years );
						end if;
						if  d='1'   then
							INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );
						end if;
						if  d='2'   then
							INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );					
						end if;
						if  d='3'   then
						 INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );
						end if;
						if  d='4'   then
						 INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );
						end if;
						if  d='5'   then
						 INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );
						end if;

						set num = num + 1;

	end while ;



 COMMIT;
end;
TRUNCATE table theory_attendance;

CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2010');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2011');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2012');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2013');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2014');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2015');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2016');

select year,count(*) from theory_attendance group by year;



