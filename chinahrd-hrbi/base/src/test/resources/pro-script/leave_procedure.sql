drop procedure if exists `aa`;
create procedure aa()
top:begin 
 
 call bb(); 
 if @done1 = 1 then
  leave top;
 else 
 call cc(); 
 end if;
 
 if @done1 = 1 then
 leave top;
 else 
 call dd();
 end if;
set @done1 =null;
end;

drop table if exists aa;
create table aa(id int);

drop table if exists db_log; 
create table db_log(name varchar(10))


drop procedure if exists bb;
create procedure bb()
begin 
declare done int default 0;
declare continue handler for SQLEXCEPTION set done = 1;
insert into aa select 1;
set @done1 = done; 
if done = 0 then 
 insert into db_log values('成功');
 else 
 insert into db_log values('失败');
end if;
end;


drop procedure if exists cc;
create procedure cc()
begin 
declare done int default 0;
declare continue handler for SQLEXCEPTION set done = 1;
insert into aa select 1;
set @done1 = done; 
if done = 0 then 
 insert into db_log values('成功');
 else 
 insert into db_log values('失败');
end if;
end;


drop procedure if exists dd;
create procedure dd()
begin 
declare done int default 0;
declare continue handler for SQLEXCEPTION set done = 1;
insert into aa select 2;
set @done1 = done; 
if done = 0 then 
 insert into db_log values('成功');
 else 
 insert into db_log values('失败');
end if;
end;

call aa();

select * from db_log;

select * from aa;

SELECT @done1;







