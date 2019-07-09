-- 历史表-敬业度评分
drop procedure if exists pro_fetch_dedicat_genre_score;
CREATE  PROCEDURE `pro_fetch_dedicat_genre_score`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
    DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
    DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
    DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【历史表-敬业度评分】');

    INSERT INTO dedicat_genre_score(
    	dedicat_score_id,customer_id,organization_id,dedicat_genre_id,score,date)
    SELECT 
    	dedicat_score_id,customerId,organization_id,dedicat_genre_id,score,date 
    FROM `mup-source`.source_dedicat_genre_score a
    ON DUPLICATE KEY UPDATE
      organization_id=a.organization_id,
      dedicat_genre_id=a.dedicat_genre_id,
      score=a.score,
      date=a.date;

END;


-- 历史表-满意度评分
drop procedure if exists pro_fetch_satfac_genre_score;
CREATE PROCEDURE `pro_fetch_satfac_genre_score`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【历史表-满意度评分】');

  INSERT INTO satfac_genre_score(
  		 satfac_score_id, customer_id,organization_id,satfac_genre_id,score,date)
  SELECT satfac_score_id, customerId,organization_id,satfac_genre_id,score,date
  FROM `mup-source`.source_satfac_genre_score a
  ON DUPLICATE KEY UPDATE
    organization_id=a.organization_id,
    satfac_genre_id=a.satfac_genre_id,
    score=a.score,
    date=a.date
    ;

END;

-- 历史表-满意度机构评分
drop procedure if exists pro_fetch_satfac_organ;
CREATE PROCEDURE `pro_fetch_satfac_organ`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
    DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
    DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
    DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【历史表-满意度机构评分】');

    INSERT INTO satfac_organ(satfac_organ_id,customer_id,organization_id,organization_name,score,date)
    SELECT satfac_organ_id,customerId,organization_id,organization_name,score,date
    FROM `mup-source`.source_satfac_organ a
    ON DUPLICATE KEY UPDATE
      organization_id=a.organization_id,
      organization_name=a.organization_name,
      score=a.score,
      date=a.date;
END;

-- 历史表-敬业度机构评分
drop procedure if exists pro_fetch_dedicat_organ;
CREATE PROCEDURE `pro_fetch_dedicat_organ`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
    DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
    DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
    DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【历史表-敬业度机构评分】');

    INSERT INTO dedicat_organ(dedicat_organ_id,customer_id,organization_id,organization_name,score,date)
    SELECT dedicat_organ_id,customerId,organization_id,organization_name,score,date
    FROM `mup-source`.source_dedicat_organ a
    ON DUPLICATE KEY UPDATE
    organization_id=a.organization_id,
    organization_name=a.organization_name,
    score=a.score,
    date=a.date;
END;