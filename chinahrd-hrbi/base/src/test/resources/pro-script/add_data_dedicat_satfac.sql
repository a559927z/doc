set @date = '2014-01-12';
set @date2 = '2014-01-13';
DROP PROCEDURE IF EXISTS add_data_dedicat_satfac;
CREATE PROCEDURE  add_data_dedicat_satfac()
BEGIN

	DECLARE  dedicat_genre_id VARCHAR(32);
	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT 
				t.dedicat_genre_id
			FROM dim_dedicat_genre t
		;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		FETCH s_cur INTO dedicat_genre_id;
		WHILE done != 1 DO
			
				INSERT INTO dedicat_genre_soure (
					dedicat_soure_id,
					customer_id,
					organization_id,
					dedicat_genre_id,
					soure,
					`date` 
				)
				SELECT REPLACE(UUID(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', organization_id,
				dedicat_genre_id,
				FLOOR(50 + (RAND() * 50)) * 0.01,
				 @date
				FROM dedicat_organ
				where `date` = @date;

			FETCH s_cur INTO dedicat_genre_id;
		END WHILE;
		CLOSE s_cur;

END;

-- TRUNCATE TABLE dedicat_genre_soure;
 CALL add_data_dedicat_satfac ();
SELECT * FROM dedicat_genre_soure ;

DROP PROCEDURE IF EXISTS add_data_dedicat_satfac;
CREATE PROCEDURE  add_data_dedicat_satfac()
BEGIN

	DECLARE  satfac_genre_id VARCHAR(32);
	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT 
				t.satfac_genre_id
			FROM dim_satfac_genre t
		;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		FETCH s_cur INTO satfac_genre_id;
		WHILE done != 1 DO
			
				INSERT INTO satfac_genre_soure (
					satfac_soure_id,
					customer_id,
					organization_id,
					satfac_genre_id,
					soure,
					`date` 
				)
				SELECT REPLACE(UUID(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', organization_id,
				satfac_genre_id,
				FLOOR(50 + (RAND() * 50)) * 0.01,
				@date2
				FROM satfac_organ 
				where `date` = @date2;

			FETCH s_cur INTO satfac_genre_id;
		END WHILE;
		CLOSE s_cur;

END;

-- TRUNCATE TABLE satfac_genre_soure;
 CALL add_data_dedicat_satfac ();

SELECT * FROM satfac_genre_soure;

