package net.chinahrd.service.impl;

import com.google.common.collect.Maps;
import net.chinahrd.dao.ImportMgrDao;
import net.chinahrd.service.ImportMgrService;
import net.chinahrd.utils.holiday.DaysDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Title: ${type_name} <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年04月27日 12:12
 * @Verdion 1.0 版本
 * ${tags}
 */
@Service("importMgrService")
public class ImportMgrServiceImpl implements ImportMgrService {

    @Autowired
    private ImportMgrDao importMgrDao;

    @Override
    public boolean updateVacation(DaysDto daysDto) {
        try {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("days", daysDto.getDays());
            paramMap.put("isWorkFlag", daysDto.getIsWorkFlag());
            paramMap.put("isHoliday", daysDto.getIsHoliday());
            paramMap.put("isVacation", daysDto.getIsVacation());
            importMgrDao.updateVacation(paramMap);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<String, Date> getMaxDay() {
        return importMgrDao.getMaxDay();
    }

    @Override
    public Map<String, Integer> findMinMaxTime(String customerId) {
        return importMgrDao.findMinMaxTime(customerId);
    }

    @Override
    public List<DaysDto> queryDayList(String startDate, String endDate) {
        return importMgrDao.queryDayList(startDate, endDate);
    }

}
