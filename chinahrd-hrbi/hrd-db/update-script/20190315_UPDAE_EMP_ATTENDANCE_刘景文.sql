-- 添加年假
INSERT INTO emp_vacation (emp_id, customer_id, emp_key, user_name_ch, annual, refresh)VALUE
(
  (SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '刘景文' ),
  "1",
  (SELECT user_key FROM dim_user t WHERE t.user_name_ch = '刘景文' ),
  "刘景文",
  "-2.0",
  CURDATE()
);