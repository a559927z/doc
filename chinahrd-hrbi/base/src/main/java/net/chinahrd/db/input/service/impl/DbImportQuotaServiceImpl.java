package net.chinahrd.db.input.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.db.input.dao.DbImportQuotaDao;
import net.chinahrd.db.input.service.DbImportQuotaService;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.utils.DateUtil;

@Transactional
@Service("dbImportQuotaService")
public class DbImportQuotaServiceImpl implements DbImportQuotaService {

	@Autowired
	private DbImportQuotaDao dbImportQuotaDao;
	
	
	@Transactional
	@Override
	public boolean callQuotaRenJunXiaoYi() {
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("p_customer_id", EisWebContext.getCustomerId());
		mp.put("p_user_id", "System-Event");
		mp.put("module", "pro_fetch_quota_RenJunXiaoYi");
		mp.put("is_error", 0);
		mp.put("curdate", DateUtil.getDBCurdate());
		
		dbImportQuotaDao.callQuotaRenJunXiaoYi(mp);
		
		int eM = dbImportQuotaDao.findLogInfo(mp);
		return eM >=1 ? true:false; 
	}


	
}
