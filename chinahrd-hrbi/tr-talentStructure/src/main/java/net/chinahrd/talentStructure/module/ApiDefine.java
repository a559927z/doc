/**
 * net.chinahrd.cache
 */
package net.chinahrd.talentStructure.module;

import net.chinahrd.api.TalentStructureApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureDto;
import net.chinahrd.talentStructure.mvc.pc.service.TalentStructureService;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author wqcai
 */
public class ApiDefine extends ApiRegisterAbstract implements TalentStructureApi {
    private static final Logger log = Logger.getLogger(ApiDefine.class);
    
    @Injection
    TalentStructureService talentStructureService;

    /* (non-Javadoc)
     * @see net.chinahrd.core.api.ApiRegister#getApiType()
     */
    @Override
    public ApiType getApiType() {
        return ApiType.INTERFACE;
    }

    public TalentStructureDto getBudgetAnalyse(String organId, String customerId) {
        return talentStructureService.findBudgetAnalyse(organId, customerId);
    }
    
    public Map<String, Object> getTalentStuctureByMonth(String organId, String customerId) {
        return talentStructureService.queryTalentStuctureByMonth(organId, customerId);
    }
}
