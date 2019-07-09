DROP PROCEDURE IF EXISTS add_360_time_and_report;
CREATE PROCEDURE add_360_time_and_report ()
BEGIN

	DECLARE  eKey VARCHAR(20);

	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR

 	SELECT emp_key eKey FROM v_dim_emp WHERE run_off_date IS NULL;

	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		WHILE done != 1 DO
			FETCH s_cur INTO   eKey;

			SET @id = replace(uuid(),'-','');

			INSERT into soure_360_time VALUES (@id, 'b5c9410dc7e4422c8e0189c7f8056b5f', eKey, '2015-07-11', '2015年度测评', 'doc/2015年度测评.doc', 1, 2015);

			SET @a = (select round(round(rand(),1)*6) + 1);
			SET @x = (select truncate(round(round(rand(),1)*4)+rand(),2));
			SET @y = @x/5;
			INSERT into soure_360_report VALUES (replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', eKey, 4, '战略导向', @a, CONCAT(@a,'级'), 5, @x, @y, 1, @id);

			SET @a = (select round(round(rand(),1)*6) + 1);
			SET @x = (select truncate(round(round(rand(),1)*4)+rand(),2));
			SET @y = @x/5;
			INSERT into soure_360_report VALUES (replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', eKey, 7, '商业敏感', @a, CONCAT(@a,'级'), 5, @x, @y, 1, @id);

			SET @a = (select round(round(rand(),1)*6) + 1);
			SET @x = (select truncate(round(round(rand(),1)*4)+rand(),2));
			SET @y = @x/5;
			INSERT into soure_360_report VALUES (replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', eKey, 3, '归纳思维', @a, CONCAT(@a,'级'), 5, @x, @y, 1, @id);

			SET @a = (select round(round(rand(),1)*6) + 1);
			SET @x = (select truncate(round(round(rand(),1)*4)+rand(),2));
			SET @y = @x/5;
			INSERT into soure_360_report VALUES (replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', eKey, 1, '沟通协调', @a, CONCAT(@a,'级'), 5, @x, @y, 2, @id);

			SET @a = (select round(round(rand(),1)*6) + 1);
			SET @x = (select truncate(round(round(rand(),1)*4)+rand(),2));
			SET @y = @x/5;
			INSERT into soure_360_report VALUES (replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', eKey, 9, '动手能力', @a, CONCAT(@a,'级'), 5, @x, @y, 2, @id);

			SET @a = (select round(round(rand(),1)*6) + 1);
			SET @x = (select truncate(round(round(rand(),1)*4)+rand(),2));
			SET @y = @x/5;
			INSERT into soure_360_report VALUES (replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', eKey, 10, '人际理解', @a, CONCAT(@a,'级'), 5, @x, @y, 3, @id);



		END WHILE;
		CLOSE s_cur;

END;


 CALL add_360_time_and_report ();

