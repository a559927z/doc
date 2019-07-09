package net.chinahrd.teamImg.mvc.app.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.teamImg.mvc.app.service.MobileTeamImgService;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 团队画像 app
 * @author htpeng
 *2016年6月13日下午2:50:45
 */
@Controller
@RequestMapping("mobile/teamImg")
public class MobileTeamImgController extends BaseController {

    @Autowired
    private MobileTeamImgService mobileTeamImgService;
    
    @RequestMapping(value = "/toTeamImgView")
    public String toTalentStructureView(HttpServletRequest request) {
    	Object obj = request.getParameter("organId");
		List<OrganDto> organPermit = getOrganPermit();
		if (CollectionKit.isNotEmpty(organPermit)) {
			if (null == obj) {
				// 当前机构id
				OrganDto topOneOrgan = organPermit.get(0);
				request.setAttribute("reqOrganId",
						topOneOrgan.getOrganizationId());
				request.setAttribute("reqOrganName",
						topOneOrgan.getOrganizationName());
			} else {
				String orgId = obj.toString();
				request.setAttribute("reqOrganId", orgId);
				boolean bool = false;
				for (OrganDto topOneOrgan : organPermit) {
					if (topOneOrgan.getOrganizationId().equals(orgId)) {
						request.setAttribute("reqOrganName",
								topOneOrgan.getOrganizationName());
						bool = true;
						break;
					}
				}
				if (!bool) {
					request.setAttribute("reqOrganName", "没有找到");
				}
			}
		}
        return "mobile/teamImg/teamImg";
    }

    /**
     * 团队画像
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamImgData", method = RequestMethod.POST)
    public  Map<String, Object> getTeamImgData(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
    	
    	return mobileTeamImgService.getTeamImgData(organId, getCustomerId());
    }


    
}
