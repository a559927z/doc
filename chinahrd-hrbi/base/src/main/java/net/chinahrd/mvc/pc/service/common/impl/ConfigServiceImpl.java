package net.chinahrd.mvc.pc.service.common.impl;

import java.util.*;

import net.chinahrd.entity.dto.pc.common.ConfigDto;
import net.chinahrd.module.SysCache;
import net.chinahrd.mvc.pc.dao.common.ConfigDao;
import net.chinahrd.mvc.pc.service.common.ConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigDao configDao;

    @Override
    public Map<String, List<ConfigDto>> getConfigs(String customerId, String configKeyPrefix) {
        Map<String, List<ConfigDto>> cfgMap = new HashMap<>();
        List<ConfigDto> cfgList = configDao.queryConfig(customerId, configKeyPrefix);
        String customerId2 = "";
        List<ConfigDto> cfgListTmp = null;
        for (ConfigDto cfg : cfgList) {
            if (!cfg.getCustomerId().equalsIgnoreCase(customerId2)) {
                if (cfgListTmp != null) {
                    cfgMap.put(customerId2, cfgListTmp);
                }
                customerId2 = cfg.getCustomerId();
                cfgListTmp = new ArrayList<>();
            }
            cfgListTmp.add(cfg);
        }
        if (cfgListTmp != null) {
            cfgMap.put(customerId2, cfgListTmp);
        }
        return cfgMap;
    }

    @Override
    public boolean updateSysConfig(String customerId, List<ConfigDto> list) {
        if (null != list) {
            try {
                configDao.updateSysConfig(customerId, list);
                SysCache.config.update();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    
	@Override
	public List<String> querySendToEmpList(String customerId, List<String> organIds) {
        if (null != customerId) {
            try {
                return configDao.querySendToEmpList(customerId, organIds);
            } catch (Exception e) {
            	e.printStackTrace();
                return null;
            }
        }
		return null;
    }
}
