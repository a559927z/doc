drop procedure if exists pro_update_emp_attendance;
CREATE PROCEDURE pro_update_emp_attendance(
	in p_customer_id VARCHAR(32), in p_user_name_ch VARCHAR(32),
	in p_source VARCHAR(32), in p_target VARCHAR(32),
	in p_year INTEGER(4), in p_year_month INTEGER(6),
	in p_begin_day VARCHAR(32), in p_end_day VARCHAR(32),
	in p_opt_day FLOAT(4,2)
)
BEGIN

	set @session.empId = (SELECT emp_id FROM dim_user t WHERE t.user_name_ch = p_user_name_ch and is_lock = 0);
	set @session.empKey = (SELECT user_key FROM dim_user t WHERE t.user_name_ch = p_user_name_ch and is_lock = 0);
	set @session.source = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name=p_source);
	set @session.target = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name=p_target);
	set @session.userNameCh = p_user_name_ch;
	set @session.yearMonth = p_year_month;

	-- emp_attendance
	DELETE FROM emp_attendance WHERE emp_id = (SELECT @session.empId) and `year_month` = (select @session.yearMonth);
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-02 09:00:00', '2019-01-02 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-02', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-03 09:00:00', '2019-01-03 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-03', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-04 09:00:00', '2019-01-04 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-04', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-07 09:00:00', '2019-01-07 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-07', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-08 09:00:00', '2019-01-08 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-08', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-09 09:00:00', '2019-01-09 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-09', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-10 09:00:00', '2019-01-10 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-10', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-11 09:00:00', '2019-01-11 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-11', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-14 09:00:00', '2019-01-14 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-14', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-15 09:00:00', '2019-01-15 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-15', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-16 09:00:00', '2019-01-16 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-16', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-17 09:00:00', '2019-01-17 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-17', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-18 09:00:00', '2019-01-18 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-18', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-21 09:00:00', '2019-01-21 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-21', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-22 09:00:00', '2019-01-22 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-22', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-23 09:00:00', '2019-01-23 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-23', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-24 09:00:00', '2019-01-24 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-24', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-25 09:00:00', '2019-01-25 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-25', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-28 09:00:00', '2019-01-28 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-28', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-29 09:00:00', '2019-01-29 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-29', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-30 09:00:00', '2019-01-30 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-30', (SELECT @session.yearMonth), now());
  INSERT INTO emp_attendance (`emp_attendance_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `clock_in_am`, `clock_out_am`, `clock_in_pm`, `clock_out_pm`, `opt_in`, `opt_out`, `opt_reason`, `cal_hour`, `organization_id`, `checkwork_type_id`, `note`, `days`, `year_month`, `refresh`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), (SELECT @session.userNameCh), NULL, NULL, NULL, NULL, '2019-01-31 09:00:00', '2019-01-31 18:00:00',  '出差', '7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '补录数据脚本 by jxzhang', '2019-01-31', (SELECT @session.yearMonth), now());

  --  如何申请人有x.5天的情况(x>0)，此处有bug，0.5天和1天的没有区分。 0.5天用4,3.5  1天用0,7.5
	UPDATE emp_attendance
	SET cal_hour = '4,3.5',	checkwork_type_id = concat((SELECT @session.source), ',', (SELECT @session.target))
	WHERE emp_attendance_id IN
		(
			SELECT * FROM (
				SELECT t.emp_attendance_id FROM emp_attendance t
				WHERE t.emp_id = ( SELECT @session.empId )
				AND t.days BETWEEN p_begin_day AND p_end_day
				AND t.checkwork_type_id LIKE concat('%',(SELECT @session.source),'%')
			) tt
		)
	;

	-- emp_attendance_day
	DELETE FROM emp_attendance_day WHERE emp_id = (SELECT @session.empId) and `year_months` = p_year_month;
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-02', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-03', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-04', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-07', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-08', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-09', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-10', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-11', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-14', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-15', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-16', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-17', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-18', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-21', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-22', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-23', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-24', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-25', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-28', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-29', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-30', NULL, p_year_month);
  INSERT INTO emp_attendance_day (`emp_attendance_day_id`, `customer_id`, `emp_key`, `emp_id`, `user_name_ch`, `hour_count`, `theory_hour_count`, `organization_id`, `checkwork_type_id`, `days`, `c_id`, `year_months`) VALUES (replace(UUID(),'-',''), '1',  (SELECT @session.empKey),  (SELECT @session.empId), p_user_name_ch, '7.50', '7.50', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-01-31', NULL, p_year_month);

	UPDATE emp_attendance_day
	SET hour_count = 0.0,	checkwork_type_id = (SELECT @session.target)
	WHERE	emp_id = (SELECT @session.empId) AND days BETWEEN p_begin_day AND p_end_day
	;

	-- emp_other_day
	UPDATE emp_other_day
	SET checkwork_type_id = (SELECT @session.target)
	WHERE emp_other_day_id IN
		(
			SELECT emp_other_day_id FROM (
				SELECT emp_other_day_id FROM emp_other_day t
				WHERE t.emp_id = (SELECT @session.empId)
				AND t.days BETWEEN p_begin_day AND p_end_day
				AND t.checkwork_type_id  = (SELECT @session.target)
			) tt
		)
	;

	-- emp_vacation
	SET @session.annual = (SELECT annual FROM emp_vacation WHERE emp_id = (SELECT @session.empId)) + p_opt_day;
	UPDATE emp_vacation SET annual = (SELECT @session.annual) WHERE emp_id = (SELECT @session.empId);

	-- emp_attendance_month
	CALL pro_fetch_emp_attendance_month ( "1", "SYSTEM", (SELECT @session.empId), p_year);


	COMMIT;
END;


-- ----------------------------------------------------
-- 201901月份考勤中对应的0.5天，24日(正常出勤 --> 年假)
-- ----------------------------------------------------
call pro_update_emp_attendance('1','马龙','正常出勤','年假',2019, 201901, '2019-01-24','2019-01-24', -0.5);

SELECT * FROM emp_vacation WHERE emp_id = (SELECT @session.empId);
SELECT * FROM emp_attendance WHERE emp_id = (SELECT @session.empId) and `year_month` = (SELECT @session.yearMonth);
SELECT * FROM emp_attendance_day WHERE emp_id = (SELECT @session.empId) and year_months = (SELECT @session.yearMonth);

