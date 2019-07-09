package net.chinahrd.teamImg.mvc.pc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.eis.annotation.log.ControllerAop;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.teamImg.module.Cache;
import net.chinahrd.teamImg.mvc.pc.service.TeamImgService;

/**
 * 团队画像 Created by jxzhang
 */
@Controller
@RequestMapping("/teamImg")
public class TeamImgController extends BaseController {

	@Autowired
	private TeamImgService teamImgService;

	/**
	 * 团队画像
	 *
	 * @param organId
	 * @return
	 */
	@ControllerAop(description = "跳转团队画像")
	@ResponseBody
	@RequestMapping(value = "/getTeamImgData", method = RequestMethod.POST)
	public Map<Integer, Object> getTeamImgData(String organId) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}

//		teamImgService.getDwq(1);
//		 return Cache.teamImg.get(organId).get();
		return teamImgService.getTeamImgData(organId, EisWebContext.getCustomerId());
	}

}
