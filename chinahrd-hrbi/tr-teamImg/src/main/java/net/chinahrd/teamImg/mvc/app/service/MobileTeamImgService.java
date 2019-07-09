package net.chinahrd.teamImg.mvc.app.service;

import java.util.Map;

/**
 * 团队画像 app service
 * @author htpeng
 *2016年6月14日下午5:28:26
 */
public interface MobileTeamImgService {

    /**
     * 团队画像
     * @param organId
     * @return
     */
    Map<String, Object> getTeamImgData(String organId, String customeId);
}
