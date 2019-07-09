CALL pro_fetch_dim_emp('b5c9410dc7e4422c8e0189c7f8056b5f', '3cfd3db15ffc4c119e344e82eb8cbb19');
CALL pro_fetch_organization( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
CALL pro_fetch_emp_position_relation( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
CALL pro_fetch_emp_overtime( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');	-- 加班时间

UPDATE dim_position SET effect_date = '2013/1/1 01:00:00';
UPDATE emp_position_relation SET effect_date = '2013/1/1 01:00:00';
UPDATE dim_organization SET effect_date = '2013/1/1 01:00:00';
UPDATE dim_business_unit SET effect_date = '2013/1/1 01:00:00';
UPDATE dim_emp SET effect_date = '2013/5/1 01:00:00';


-- UPDATE soure_trade_profit set customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'

CALL pro_fetch_trade_profit('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
CALL pro_init_factfte('2013-06-11','demo');
CALL pro_init_factfte('2013-07-11','demo');
CALL pro_init_factfte('2013-08-11','demo');
CALL pro_init_factfte('2013-09-11','demo');
CALL pro_init_factfte('2013-10-11','demo');
CALL pro_init_factfte('2013-11-11','demo');
CALL pro_init_factfte('2013-12-11','demo');
CALL pro_init_factfte('2014-01-11','demo');
CALL pro_init_factfte('2014-02-11','demo');
CALL pro_init_factfte('2014-03-11','demo');
CALL pro_init_factfte('2014-04-11','demo');
CALL pro_init_factfte('2014-05-11','demo');
CALL pro_init_factfte('2014-06-11','demo');
CALL pro_init_factfte('2014-07-11','demo');
CALL pro_init_factfte('2014-08-11','demo');
CALL pro_init_factfte('2014-09-11','demo');
CALL pro_init_factfte('2014-10-11','demo');
CALL pro_init_factfte('2014-11-11','demo');
CALL pro_init_factfte('2014-12-11','demo');
CALL pro_init_factfte('2015-01-11','demo');
CALL pro_init_factfte('2015-02-11','demo');
CALL pro_init_factfte('2015-03-11','demo');
CALL pro_init_factfte('2015-04-11','demo');
CALL pro_init_factfte('2015-05-11','demo');
CALL pro_init_factfte('2015-06-11','demo');
CALL pro_init_factfte('2015-07-11','demo');


CALL pro_update_ffRange('2015-05-11','b5c9410dc7e4422c8e0189c7f8056b5f');


