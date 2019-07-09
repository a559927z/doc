
	DROP EVENT
	IF EXISTS evn_refresh_dim_user_lock;
	CREATE EVENT
	IF NOT EXISTS evn_refresh_dim_user_lock ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', '01:00:00') ON COMPLETION PRESERVE DO

			UPDATE dim_user 
			SET is_lock = 1,
					modify_user_id = 'DB-Event',
					modify_time = NOW()
			WHERE user_id IN (
				SELECT user_id FROM (
					SELECT user_id FROM dim_user WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) > create_time
					AND is_lock = 0 AND `password` = '123456'
				) t 
			);

