-- ----------------------------------------------------
-- 18年核算补给年假7天
-- ----------------------------------------------------

-- 更新年假
DELETE FROM emp_vacation WHERE user_name_ch = '黄永军';

-- 添加年假
INSERT INTO emp_vacation (emp_id, customer_id, emp_key, user_name_ch, annual, refresh)VALUE
(
  (SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '黄永军' ),
  "1",
  (SELECT user_key FROM dim_user t WHERE t.user_name_ch = '黄永军' ),
  "黄永军",
  "7.0",
  CURDATE()
)
;

SELECT * FROM emp_vacation t WHERE t.user_name_ch = '黄永军';