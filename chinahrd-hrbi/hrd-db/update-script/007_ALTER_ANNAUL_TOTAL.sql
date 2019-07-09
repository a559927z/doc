ALTER TABLE emp_vacation ADD COLUMN annual_total VARCHAR(10) DEFAULT '0' COMMENT  '本年总年假' AFTER user_name_ch ;

UPDATE emp_vacation SET annual_total = 3 WHERE user_name_ch IN('毛嘉鹏');
UPDATE emp_vacation SET annual_total = 4 WHERE user_name_ch IN('李厚生');
UPDATE emp_vacation SET annual_total = 5 WHERE user_name_ch IN('田一甲','梁淑仪','陶逸兴','刘蕾','丁家强','陈隐','马龙','余家安','彭海涛','刘畅','梁斌','杨小丽','单考');
UPDATE emp_vacation SET annual_total = 7 WHERE user_name_ch IN('吴振良','何国盛','程小丽','彭谨','鲁力','李德才','肖伟','邹关健','朱青平','蔡伟钦','曾知凤','曾诗');
UPDATE emp_vacation SET annual_total = 10 WHERE user_name_ch IN('许宁','杨光亮','张景星');