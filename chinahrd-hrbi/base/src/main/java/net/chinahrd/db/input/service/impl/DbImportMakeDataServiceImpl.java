package net.chinahrd.db.input.service.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.db.input.Entity.SoureVDimEmpEntity;
import net.chinahrd.db.input.dao.DbImportDao;
import net.chinahrd.db.input.service.DbImportMakeDataService;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

@Transactional
@Service("dbImportMakeDataService")
public class DbImportMakeDataServiceImpl implements DbImportMakeDataService {

	@Autowired
	private DbImportDao dbImportDao;

	@Override
	public boolean addDimEmpDays(String yearLast, String yearFirst) {
		Date start = new Date();
		List<String> calcDays = calcDays(yearLast, yearFirst);
		
		List<SoureVDimEmpEntity> rs = dbImportDao.queryVDimEmp2(EisWebContext.getCustomerId());
		
		for (String day : calcDays) {
			
			List<SoureVDimEmpEntity> filterRs = filter(rs, day);			
			dbImportDao.addDimEmpDays(filterRs, day);
		}
		
		
		String useTime = DateUtil.getInterval(start, new Date());
		System.out.println(useTime);
		
		return true;
	}

	private List<SoureVDimEmpEntity> filter(List<SoureVDimEmpEntity> rs, String day){
		List<SoureVDimEmpEntity> filterRs = CollectionKit.newList();
		for (SoureVDimEmpEntity entity : rs) {
			entity.setId(Identities.uuid2());
			if(null == entity.getRun_off_date()){
				filterRs.add(entity);
			}else{
				int a = getYearMonthDayInt(entity.getRun_off_date());
				int b = getYearMonthDayInt(day);
				if(a<=b){
					filterRs.add(entity);
				}
			}
		}
		return filterRs;
	}
	
	private List<String> calcDays(String yearLast, String yearFirst) {
		List<String> days = CollectionKit.newList();
		DateTime dt = new DateTime(yearLast);
		String yestDay = "";
		do {
			yestDay = dt.minusDays(1).toString("yyyy-MM-dd");
			dt = new DateTime(yestDay);
			days.add(yestDay);

			
		} while (!(yestDay.equals(yearFirst)));
		System.out.println(days.size());

		return days;
	}
	
	private int getYearMonthDayInt(String str){
		str = str.split(" ")[0];
		int rs = Integer.parseInt(str.replace("-", ""));
		return rs;
	}

}
