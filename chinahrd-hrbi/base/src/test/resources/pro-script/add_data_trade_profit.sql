DROP PROCEDURE IF EXISTS add_data_trade_profit;
CREATE PROCEDURE add_data_trade_profit (in p_ym int(6))
BEGIN

	
	DECLARE i INT DEFAULT 1;
	-- 指定年 和 机构Key
	DELETE FROM soure_trade_profit WHERE organization_key = 'ZRW' and `year_month` BETWEEN p_ym and p_ym+12  ;
		WHILE i <= 12 DO

				INSERT INTO soure_trade_profit VALUES(
					'b5c9410dc7e4422c8e0189c7f8056b5f', 'humanResources', 'ZRW', 0.0, 0.0, p_ym+i
				);

			SET i = i + 1;
		END WHILE;

END;

 CALL add_data_trade_profit (201000);




-- **********************************************************************



DROP PROCEDURE IF EXISTS add_data_trade_profit;
CREATE PROCEDURE add_data_trade_profit (in p_organ_key VARCHAR(20), in p_ym int(6))
BEGIN

	DECLARE i INT DEFAULT 1;

-- -- 加年
-- 	-- 指定年 和 机构Key
-- 	DELETE FROM soure_trade_profit WHERE `year_month` LIKE CONCAT(p_ym,'%') and organization_key = p_organ_key ;
-- 		WHILE i <= 12 DO
-- 			set @temp =  round(round(rand(),2)*100);
-- 			IF i<10 THEN
-- 				INSERT INTO soure_trade_profit VALUES(
-- 					'b5c9410dc7e4422c8e0189c7f8056b5f', 'humanResources', p_organ_key,  @temp, @temp+round(round(rand(),1)*10), p_ym+i
-- 				);
-- 			ELSE
-- 				INSERT INTO soure_trade_profit VALUES(
-- 					'b5c9410dc7e4422c8e0189c7f8056b5f', 'humanResources', p_organ_key,  @temp, @temp+round(round(rand(),1)*10), p_ym+i
-- 				);
-- 			END IF;
-- 
-- 			SET i = i + 1;
-- 		END WHILE;



-- 加单月
		set @temp =  round(round(rand(),2)*100);
		INSERT INTO soure_trade_profit VALUES(
			REPLACE(UUID(),'-',''),
			'b5c9410dc7e4422c8e0189c7f8056b5f', 'humanResources',
			p_organ_key,  @temp, @temp+round(round(rand(),1)*10), p_ym
		);

	 CALL sum_data_trade_profit ();

END;
-- 加年
 CALL add_data_trade_profit ('BJ', 201000);
 CALL add_data_trade_profit ('GZ', 201000);
 CALL add_data_trade_profit ('SH', 201000);
 CALL add_data_trade_profit ('shBusiness', 201000);

-- 加单月
 CALL add_data_trade_profit ('ZRW', 201511);
 CALL add_data_trade_profit ('SZ', 201511);
 CALL add_data_trade_profit ('BJ', 201511);
 CALL add_data_trade_profit ('GZ', 201511);
 CALL add_data_trade_profit ('SH', 201511);
 CALL add_data_trade_profit ('shBusiness', 201511);


-- **********************************************************************


DROP PROCEDURE IF EXISTS sum_data_trade_profit;
CREATE PROCEDURE sum_data_trade_profit ()
BEGIN
	
	DECLARE ym INT;
	DECLARE ga, sa decimal(10,2);

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT `year_month` ym  FROM soure_trade_profit where organization_key = 'ZRW';
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		WHILE done != 1 DO
			FETCH s_cur INTO ym;

			SELECT sum(gain_amount) as ga , SUM(sales_amount) as sa into ga, sa FROM soure_trade_profit
			where organization_key in ('BJ','GZ','SH','SZ','shBusiness') and `year_month` = ym;

			UPDATE soure_trade_profit SET gain_amount = ga, sales_amount = sa
			where organization_key ='ZRW' and `year_month` = ym;

		END WHILE;

	CLOSE s_cur;

END;




