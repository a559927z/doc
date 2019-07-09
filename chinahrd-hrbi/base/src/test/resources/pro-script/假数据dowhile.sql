DROP PROCEDURE IF EXISTS dowhile;
CREATE PROCEDURE dowhile ()
BEGIN
	DECLARE vIndex INT DEFAULT 1;
	DECLARE v_gAmou, s_sAmou DOUBLE;
	DECLARE gAmou, sAmou DOUBLE;

WHILE vIndex <= 2 DO

	SET v_gAmou = FLOOR(100 +(RAND() * 10));
	set s_sAmou = v_gAmou+20;
	

	INSERT INTO soure_trade_profit 
	VALUES ( 'b5c9410dc7e4422c8e0189c7f8056b5f', 'humanResources', 'SH', v_gAmou, s_sAmou, 201507 + vIndex );

	SELECT gain_amount,sales_amount  INTO gAmou,sAmou  
	FROM soure_trade_profit
	WHERE organization_key = 'ZRW' AND `YEAR_MONTH` = 201507 + vIndex;


	UPDATE soure_trade_profit SET gain_amount = gAmou+v_gAmou, sales_amount = sAmou+s_sAmou
	WHERE organization_key = 'ZRW' AND `YEAR_MONTH` = 201507 + vIndex;



	set v_gAmou = 0; SET s_sAmou = 0;
	SET vIndex=vIndex + 1;

END
WHILE;

END;

 CALL dowhile ();