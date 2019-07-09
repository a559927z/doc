# 9月30日年假处理
UPDATE `emp_vacation` SET annual = '6.0', refresh = now() where emp_key = 'qpzhu';

# 9月6日年假半天处理
UPDATE `emp_vacation` SET annual = '4.5', refresh = now() where emp_key = 'kshan';

# 9月25日-9月29日年假处理
UPDATE `emp_vacation` SET annual = '0', refresh = now() where emp_key = (SELECT user_key FROM dim_user t WHERE t.user_name_ch = '丁家强' );


# 9月25日-9月29日年假处理
UPDATE `emp_vacation` SET annual = '0', refresh = now() where emp_key = (SELECT user_key FROM dim_user t WHERE t.user_name_ch = '丁家强' );