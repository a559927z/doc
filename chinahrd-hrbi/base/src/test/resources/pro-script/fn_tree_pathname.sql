-- 调用函数输出name路径   
DROP FUNCTION IF EXISTS mup.fn_tree_pathname;   
CREATE FUNCTION mup.fn_tree_pathname(nid VARCHAR(32), delimit VARCHAR(10), customerId VARCHAR(32)) 
RETURNS VARCHAR(2000) CHARSET utf8   
BEGIN     
  DECLARE pathid VARCHAR(1000);
  SET @pathid='';
  CALL pro_create_pnlist(nid, delimit, customerId, @pathid);
  RETURN @pathid;
END;