#=======================================================pro_fetch_dim_emp=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_emp`;
CREATE PROCEDURE pro_fetch_dim_emp(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE empKey VARCHAR(32);
	DECLARE userName VARCHAR(10);
	DECLARE userNameCh VARCHAR(5);
	DECLARE imgPath VARCHAR(200);
	DECLARE curEnabled int(1);
	DECLARE isKeyTalent int(1) DEFAULT 1;
	DECLARE passtimeOrFulltime VARCHAR(1);
	DECLARE curAge, companyAge int(3);
	DECLARE curSex  VARCHAR(1);
	DECLARE curNation, curDegree  VARCHAR(9);
	DECLARE nativePlace  VARCHAR(90);
	DECLARE curCountry  VARCHAR(20);
	DECLARE birthPlace  VARCHAR(90);
	DECLARE birthDate  datetime;
-- 	DECLARE nationalId, nationalType, abName, keyTalentName, perName  VARCHAR(60);
	DECLARE nationalId, nationalType, keyTalentName VARCHAR(60);
	DECLARE marryStatus  INT(1);
	DECLARE pattyName  VARCHAR(20);
-- 	DECLARE abId, keyTalentId, perId VARCHAR(32);
	DECLARE keyTalentId VARCHAR(32);
	DECLARE effectDate, expiryDate, roDate , entryDate, regularDate DATETIME;
	DECLARE telPhone VARCHAR(11);
	DECLARE curQQ VARCHAR(20);
	DECLARE wxCode VARCHAR(32);
	DECLARE curAddress VARCHAR(60);
	DECLARE contractUnit VARCHAR(32);
	DECLARE workPlace VARCHAR(60);
	DECLARE applyType, applyChannel VARCHAR(32);
	DECLARE curSpeciality VARCHAR(60);
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;

	DECLARE exist int;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
					SELECT 
						emp_key	empKey,
						user_name userName,
						user_name_ch userNameCh,
						img_path imgPath,
						passtime_or_fulltime passtimeOrFulltime,
						age curAge,
						company_age companyAge,
						sex curSex,
						nation curNation,
						degree curDegree,
						native_place nativePlace,
						country curCountry,
						birth_place birthPlace,
						birth_date birthDate,
						national_id nationalId,
						national_type nationalType,
						marry_status marryStatus,
						patty_name pattyName,
-- 						t1.ability_id abId,
						t2.key_talent_type_id keyTalentId,
-- 						t3.performance_id perId,
-- 						t1.ability_id abName,
						t2.key_talent_type_id keyTalentName,
-- 						t3.performance_id perName,
						effect_date effectDate,
						expiry_date expiryDate,
						enabled curEnabled,
						is_key_talent isKeyTalent,
						run_off_date roDate,
						entry_date entryDate,
						tel_phone telPhone,
						qq curQQ,
						wx_code wxCode,
						address curAddress,
						contract_unit contractUnit,
						work_place workPlace,
						regular_date regularDate,
						apply_type applyType,
						apply_channel applyChannel,
						speciality curSpeciality
					FROM soure_dim_emp sue 
-- 					INNER JOIN dim_ability t1 on t1.ability_key = sue.ability_key AND t1.customer_Id = sue.customer_id
					INNER JOIN dim_key_talent_type t2 on t2.key_talent_type_key = sue.key_talent_type_key AND t2.customer_Id = sue.customer_id
-- 					INNER JOIN dim_performance t3 on t3.performance_key = sue.performance_key AND t3.customer_Id = sue.customer_id
					WHERE sue.customer_id = customerId;
	
-- 	DECLARE CONTINUE HANDLER FOR NOT FOUND SET SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = null;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId,  concat("【员工-维度表】：", empKey, "的员工数据失败！"), startTime, now(), "error");
		END;
		
		SET startTime = now();
		OPEN s_cur;
		WHILE(done IS NOT NULL) DO

			FETCH s_cur INTO empKey, userName, userNameCh,imgPath, passtimeOrFulltime,
											curAge,companyAge, curSex, curNation, curDegree, nativePlace, curCountry, birthPlace, birthDate, nationalId, nationalType,
											marryStatus, pattyName,
-- 											abId, keyTalentId, perId,
-- 											abName, keyTalentName, perName,
											keyTalentId, keyTalentName,
											effectDate, expiryDate, curEnabled, isKeyTalent, roDate,
										 entryDate,
										 telPhone,
										 curQQ,
										 wxCode,
										 curAddress,
										 contractUnit,
										 workPlace,
										 regularDate,
										 applyType,
										 applyChannel,
										 curSpeciality;

  			START TRANSACTION;
				SELECT count(1) as exist into exist FROM dim_emp emp
				WHERE emp.emp_key = empKey AND customer_id = customerId;

				IF exist>0 THEN
-- INSERT INTO debug(exist, type) VALUES (exist, 'update');
					UPDATE dim_emp
					SET user_name = userName, user_name_ch = userNameCh, img_path = imgPath, passtime_or_fulltime = passtimeOrFulltime,
							age = curAge, company_age = companyAge, sex = curSex, nation = curNation, degree = curDegree, native_place = nativePlace, country = curCountry,
							birth_place = birthPlace, birth_date = birthDate, national_id = nationalId, national_type = nationalType,
							marry_status = marryStatus, patty_name = pattyName,
							/*ability_name = abName,*/ key_talent_type_name = keyTalentName, /*performance_name = perName,*/
							effect_date = effectDate, expiry_date = expiryDate, enabled = curEnabled, is_key_talent = isKeyTalent, run_off_date = roDate,
							entry_date = entryDate,
							tel_phone = telPhone,
							qq = curQQ,
							wx_code = wxCode,
							address = curAddress,
							contract_unit = contractUnit,
							work_place = workPlace,
							regular_date = regularDate,
							apply_type = applyType,
							apply_channel = applyChannel,
							speciality = curSpeciality
					WHERE emp_key = empKey AND customer_id = customerId;
						
			-- INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type) VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【员工-维度表】：", empKey, "内容数据更新成功！"), startTime, now(), "success");
				ELSE
-- INSERT INTO debug(exist, type) VALUES (exist, 'insert');
					INSERT INTO dim_emp(
									emp_id, customer_id, emp_key, user_name, user_name_ch, img_path,
									passtime_or_fulltime,
									age, company_age, sex, nation, degree, native_place, country, birth_place, birth_date,
									national_id , national_type , marry_status , patty_name,
									/*ability_id,*/ key_talent_type_id, /*performance_id,*/
									ability_name, key_talent_type_name, performance_name,
									effect_date, expiry_date,enabled, is_key_talent, run_off_date,
									entry_date,tel_phone,qq, wx_code,address,contract_unit,work_place,regular_date,apply_type,apply_channel,speciality
									)
					VALUES(replace(UUID(),'-',''), customerId, empKey, userName, userNameCh, imgPath, passtimeOrFulltime,
									curAge, companyAge, curSex, curNation, curDegree, nativePlace, curCountry, birthPlace, birthDate,
									nationalId , nationalType , marryStatus , pattyName,
									/*abId,*/ keyTalentId, /*perId,*/
									/*abName,*/ keyTalentName, /*perName,*/
									effectDate, expiryDate,curEnabled, isKeyTalent, roDate,
									entryDate,
									telPhone,
									curQQ,
									wxCode,
									curAddress,
									contractUnit,
									workPlace,
									regularDate,
									applyType,
									applyChannel,
									curSpeciality
								);
			END IF;

-- 			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 			VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【员工-维度表】：数据成功", IF(exist>0, "update", "insert") ), startTime, now(), "success");
-- 			set exist = 0;

		END WHILE;
		CLOSE s_cur;

END;
-- DELIMITER ;
