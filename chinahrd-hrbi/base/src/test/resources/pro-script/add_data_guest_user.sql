
SET @uKey = 'wzhao';
SET @nameCh = '赵微';

INSERT INTO dim_user VALUES(
	replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f',
	'1b93d3d83cea11e59c5508606e0aa89a', -- emp_id
	@uKey, -- user_key
	@uKey, -- user_name
	@nameCh, -- user_name_ch
	'123456',
	concat(@uKey, '@chinahrd.net'),
	'体现用户',
	0, -- sys_deploy
	200, -- show_index
	'DBA',
	NULL,
	now(),
	NULL
);

INSERT INTO soure_user_role_relation VALUE(
'b5c9410dc7e4422c8e0189c7f8056b5f', @uKey, 'guest'
);
CALL pro_fetch_user_role_relation('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA');

