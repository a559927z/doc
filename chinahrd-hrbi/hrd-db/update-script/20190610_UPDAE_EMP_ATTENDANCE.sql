#######################################################
################ 20190610最新考勤差异调整 #############
#######################################################

####### 曹理菊  ################
INSERT INTO emp_other_day (emp_other_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('552485800332230656', '1', 'ljcao_gz','441209623567925248','曹理菊', 7.5, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-02-01', '201902'),
       ('552485800344813568', '1', 'ljcao_gz','441209623567925248','曹理菊', 7.5, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-02-02', '201902');
UPDATE emp_attendance SET opt_in = NULL, opt_out = NULL, opt_reason = '', cal_hour = '0,7.5', checkwork_type_id = 'b90bb95e3c01413b80899b49ba13392e,c5b620905b444ffa82dda81fbf99d389' WHERE emp_key = 'ljcao_gz' AND emp_attendance_id in( '552485800332230656', '552485800344813568');
UPDATE emp_attendance_day SET hour_count = 0 WHERE emp_key = 'ljcao_gz' AND emp_attendance_day_id IN ('552485800332230656', '552485800344813568');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '441209623567925248', 201902);

DELETE FROM emp_other_day WHERE emp_key = 'ljcao_gz' AND days BETWEEN '2019-04-03' AND '2019-04-04';
UPDATE emp_attendance SET opt_in = '2019-04-03 09:00:00', opt_out = '2019-04-03 18:00:00', opt_reason = '其他', cal_hour = '7.5', checkwork_type_id = 'b90bb95e3c01413b80899b49ba13392e' WHERE emp_key = 'ljcao_gz' AND emp_attendance_id = '574658104159895552';
UPDATE emp_attendance SET opt_in = '2019-04-04 09:00:00', opt_out = '2019-04-04 18:00:00', opt_reason = '其他', cal_hour = '7.5', checkwork_type_id = 'b90bb95e3c01413b80899b49ba13392e' WHERE emp_key = 'ljcao_gz' AND emp_attendance_id = '574658063324151808';
UPDATE emp_attendance_day SET hour_count = 7.5 WHERE emp_key = 'ljcao_gz' AND emp_attendance_day_id IN ('574658104159895552', '574658063324151808');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '441209623567925248', 201904);

UPDATE emp_vacation SET annual_total = 4.5, annual = 2.5, can_leave = 0 WHERE emp_key ='ljcao_gz' AND user_name_ch = '曹理菊';

####### 曾诗  ################
INSERT INTO emp_other_day (emp_other_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('574521715548225536', '1', 'shzeng','62965e414f1811e68c4490b11c32f63c','曾诗', 7.5, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-04-04', '201904');
UPDATE emp_attendance SET opt_in = NULL, opt_out = NULL, opt_reason = '', cal_hour = '0,7.5', checkwork_type_id = 'b90bb95e3c01413b80899b49ba13392e,c5b620905b444ffa82dda81fbf99d389' WHERE emp_key = 'shzeng' AND emp_attendance_id in( '574521715548225536');
UPDATE emp_attendance_day SET hour_count = 0 WHERE emp_key = 'shzeng' AND emp_attendance_day_id IN ('574521715548225536');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '62965e414f1811e68c4490b11c32f63c', 201904);
UPDATE emp_vacation SET annual = 3 WHERE user_name_ch ='曾诗';

####### 肖伟  ################
INSERT INTO emp_other_day (emp_other_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('528370534547718143', '1', 'wxiao','62966c0c4f1811e68c4490b11c32f63c','肖伟', 4, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2018-12-29', '201812');
INSERT INTO emp_attendance (emp_attendance_id, customer_id, emp_key, emp_id, user_name_ch, opt_in, opt_out, cal_hour, organization_id, checkwork_type_id, note, days,`year_month`,refresh)
VALUES ('528370534547718143', '1', 'wxiao','62966c0c4f1811e68c4490b11c32f63c','肖伟', '2018-12-29 09:00:00', '2018-12-29 12:00:00', '4,4', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e,c5b620905b444ffa82dda81fbf99d389', '补充调休数据', '2018-12-29', '201812', '2019-06-11 15:22:21');
INSERT INTO emp_attendance_day (emp_attendance_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, theory_hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('528370534547718143', '1', 'wxiao','62966c0c4f1811e68c4490b11c32f63c','肖伟', 4, 7.5, '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2018-12-29', '201812');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '62966c0c4f1811e68c4490b11c32f63c', 201812);

INSERT INTO emp_other_day (emp_other_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('552418693208866816', '1', 'wxiao','62966c0c4f1811e68c4490b11c32f63c','肖伟', 4, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-02-15', '201902');
UPDATE emp_attendance SET opt_in = '2019-02-15 09:00:00', opt_out = '2019-02-15 12:00:00', opt_reason = '', cal_hour = '4,4', checkwork_type_id = 'b90bb95e3c01413b80899b49ba13392e,c5b620905b444ffa82dda81fbf99d389', note = '补充1月30~31日年假' WHERE emp_key = 'wxiao' AND emp_attendance_id in('552418693208866816');
UPDATE emp_attendance_day SET hour_count = 4 WHERE emp_key = 'wxiao' AND emp_attendance_day_id IN ('552418693208866816');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '62966c0c4f1811e68c4490b11c32f63c', 201902);
UPDATE emp_vacation SET annual = 5.5 WHERE user_name_ch ='肖伟';

####### 彭谨  ################
INSERT INTO emp_other_day (emp_other_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('1b417c572fbe11e993d50017fa00d36d', '1', 'jpeng_gz','363455134505631744','彭谨', 7.5, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-01-02', '201901'),
       ('1b419ded2fbe11e993d50017fa00d36d', '1', 'jpeng_gz', '363455134505631744', '彭谨',7.5, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-01-09', '201901');
UPDATE emp_attendance SET opt_in = NULL, opt_out = NULL, opt_reason = '', cal_hour = '0,7.5', checkwork_type_id = 'b90bb95e3c01413b80899b49ba13392e,c5b620905b444ffa82dda81fbf99d389', note = '补充调休年假数据' WHERE emp_key = 'jpeng_gz' AND emp_attendance_id in ('1b16be7e2fbe11e993d50017fa00d36d', '1b16efc02fbe11e993d50017fa00d36d');
UPDATE emp_attendance_day SET hour_count = 0 WHERE emp_key = 'jpeng_gz' AND emp_attendance_day_id IN ('1b417c572fbe11e993d50017fa00d36d', '1b419ded2fbe11e993d50017fa00d36d');
UPDATE emp_attendance SET cal_hour = '7.5', checkwork_type_id = 'b90bb95e3c01413b80899b49ba13392e' WHERE emp_key = 'jpeng_gz' AND emp_attendance_id in ('1b4184452fbe11e993d50017fa00d36d');
UPDATE emp_attendance_day SET hour_count = 7.5 WHERE emp_key = 'jpeng_gz' AND emp_attendance_day_id IN ('1b4184452fbe11e993d50017fa00d36d');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '363455134505631744', 201901);
UPDATE emp_vacation SET annual = 7 WHERE user_name_ch ='彭谨';

####### 田一甲  ################
INSERT INTO emp_other_day (emp_other_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('574977587848151039', '1', 'yjtian','62967fcd4f1811e68c4490b11c32f63c','田一甲', 7.5, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-04-28', '201904');
INSERT INTO emp_attendance (emp_attendance_id, customer_id, emp_key, emp_id, user_name_ch, opt_in, opt_out, cal_hour, organization_id, checkwork_type_id, note, days,`year_month`,refresh)
VALUES ('574977587848151039', '1', 'yjtian','62967fcd4f1811e68c4490b11c32f63c','田一甲', NULL, NULL, '0,7.5', '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e,c5b620905b444ffa82dda81fbf99d389', '补充调休年假数据', '2019-04-28', '201904', '2019-06-11 15:22:21');
INSERT INTO emp_attendance_day (emp_attendance_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, theory_hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('574977587848151039', '1', 'yjtian','62967fcd4f1811e68c4490b11c32f63c','田一甲', 0, 7.5, '3b6329ee4f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-04-28', '201904');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '62967fcd4f1811e68c4490b11c32f63c', 201904);
UPDATE emp_vacation SET annual = 2 WHERE user_name_ch ='田一甲';

####### 莫文忠  ################
INSERT INTO emp_other_day (emp_other_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('574892329467379712', '1', 'wzmo_gz','491662385954684928','莫文忠', 7.5, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-04-01', '201904');
UPDATE emp_attendance SET opt_in = NULL, opt_out = NULL, opt_reason = '', cal_hour = '0,7.5', checkwork_type_id = 'b90bb95e3c01413b80899b49ba13392e,c5b620905b444ffa82dda81fbf99d389' WHERE emp_key = 'wzmo_gz' AND emp_attendance_id in('574892329467379712');
UPDATE emp_attendance_day SET hour_count = 0 WHERE emp_key = 'wzmo_gz' AND emp_attendance_day_id IN ('574892329467379712');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '491662385954684928', 201904);

INSERT INTO emp_other_day (emp_other_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('585418207146278911', '1', 'wzmo_gz','491662385954684928','莫文忠', 7.5, '3b632a694f2411e68c4490b11c32f63c', 'c5b620905b444ffa82dda81fbf99d389', '2019-05-05', '201905');
INSERT INTO emp_attendance (emp_attendance_id, customer_id, emp_key, emp_id, user_name_ch, opt_in, opt_out, cal_hour, organization_id, checkwork_type_id, note, days,`year_month`,refresh)
VALUES ('585418207146278911', '1', 'wzmo_gz','491662385954684928','莫文忠', NULL, NULL, '0,7.5', '3b632a694f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e,c5b620905b444ffa82dda81fbf99d389', '补充调休年假数据', '2019-05-05', '201905', '2019-06-11 15:22:21');
INSERT INTO emp_attendance_day (emp_attendance_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, theory_hour_count, organization_id, checkwork_type_id, days, year_months)
VALUES ('585418207146278911', '1', 'wzmo_gz','491662385954684928','莫文忠', 0, 7.5, '3b632a694f2411e68c4490b11c32f63c', 'b90bb95e3c01413b80899b49ba13392e', '2019-05-05', '201905');
CALL pro_fetch_emp_attendance_month ("1", "SYSTEM", '491662385954684928', 201905);
INSERT INTO emp_vacation (emp_id, customer_id, emp_key, user_name_ch, annual_total, annual, refresh)
VALUE (
  (SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '莫文忠' ),
  '1',
  (SELECT user_key FROM dim_user t WHERE t.user_name_ch = '莫文忠' ),
  "莫文忠",
  10,
  7,
  CURDATE()
);


####### 优化剩余年假  ################
UPDATE emp_vacation SET annual = 5 WHERE user_name_ch ='陈开枝';
UPDATE emp_vacation SET annual = 6 WHERE user_name_ch ='黄晓燕';
UPDATE emp_vacation SET annual = 6 WHERE user_name_ch ='李德才';
UPDATE emp_vacation SET annual = 2, can_leave = 0 WHERE user_name_ch ='陶逸兴';
UPDATE emp_vacation SET annual = 4 WHERE user_name_ch ='刘亚利';
UPDATE emp_vacation SET annual = 1 WHERE user_name_ch ='霍操玺';
UPDATE emp_vacation SET annual = 0 WHERE user_name_ch ='杨小霖';
INSERT INTO emp_vacation (emp_id, customer_id, emp_key, user_name_ch, annual_total, annual, refresh)
VALUE (
  (SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '邹佳栖' ),
  '1',
  (SELECT user_key FROM dim_user t WHERE t.user_name_ch = '邹佳栖' ),
  "邹佳栖",
  3.5,
  3.5,
  CURDATE()
);


####### 删除多余账号数据  ################
DELETE FROM dim_user WHERE user_id IN ('545611951338684416');