CREATE  PROCEDURE `proc_manpower_cost`(_year_month int)
begin 
declare _uuid char(32);
	select replace(uuid(),'-','') into _uuid;
  insert into manpower_cost
	select replace(uuid(),'-',''),a.customer_id,a.organization_id,sum(item_cost),month_end_sum,sum(item_cost)/month_end_sum,sum(item_cost)*1.1,sum(item_cost)*1.3,a.`year_month` from 
	manpower_cost_item a,
	history_emp_count_month b 
	where 
	a.organization_id=b.organization_id and 
	a.`year_month`=b.`year_month`   and a.`year_month`= _year_month and type=1 group by organization_id,`year_month`, a.customer_id,month_end_sum;
end;