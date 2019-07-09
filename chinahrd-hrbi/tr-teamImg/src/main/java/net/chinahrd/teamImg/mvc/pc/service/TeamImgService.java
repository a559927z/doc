package net.chinahrd.teamImg.mvc.pc.service;

import java.util.Map;

public interface TeamImgService {

    /**
     * 团队画像
     * @param organId
     * @return
     */
    Map<Integer, Object> getTeamImgData(String organId, String customeId);
    
    void getDwq(int a);
}
