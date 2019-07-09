package net.chinahrd.keyTalent.mvc.app.controller;

import java.util.List;







import net.chinahrd.entity.dto.app.common.EmpDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.keyTalent.mvc.app.service.MobileKeyTalentsService;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.utils.Transcode;
import net.chinahrd.entity.dto.app.keyTalents.KeyTalentPanelDto;
import net.chinahrd.entity.dto.app.keyTalents.KeyTalentsCardDto;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 移动端 关键人才库
 * 
 * @author htpeng 2016年5月16日下午5:53:37
 */
@Controller
@RequestMapping("mobile/keyTalent")
public class MobileKeyTalentController extends BaseController {

	@Autowired
	private MobileKeyTalentsService mobileKeyTalentsService;
	private static final String QUERY_TYPE = "QUERY_TYPE";
	private static final String QUERY_ORGAN = "QUERY_ORGAN";

	@ResponseBody
	@RequestMapping(value = "/getKeyTalentTypePanel", method = RequestMethod.GET)
	public List<KeyTalentPanelDto> getTalentSearch() {

		return mobileKeyTalentsService.queryKeyTalentTypePanel(getCustomerId(),
				getOrganPermitId());

	}

	/**
	 * 左侧面板查询 各个部门的关键人才数量 （含有下级）
	 * 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentOrganPanel", method = RequestMethod.GET)
	public List<KeyTalentPanelDto> queryKeyTalentOrganPanel(String organId) {
		if (organId == null || organId.equals("")) {
			return mobileKeyTalentsService.queryKeyTalentOrganPanel(getUser(),
					getCustomerId(), getTopOrganStr(), true);
		}
		List<String> list = CollectionKit.newList();
		list.add(organId);
		return mobileKeyTalentsService.queryKeyTalentOrganPanel(getUser(),
				getCustomerId(), list, false);
	}

	/**
	 * 关键人才卡牌查询
	 */

	@ResponseBody
	@RequestMapping(value = "/getKeyTalent", method = RequestMethod.GET)
	public PaginationDto<KeyTalentsCardDto> getKeyTalent(String key,
			String type, Integer page, Integer pageSize) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(
				page, pageSize);

		// return mobileTalentSearchService.queryTalentSearch(getCustomerId(),
		// key,getOrganPermit(), dto);

		if (key.equals("-1")) {
			// 查询全部
			return mobileKeyTalentsService.getKeyTalentAll(getCustomerId(),
					getUserEmpId(), getOrganPermitId(), dto);
		} else if (type.equals(QUERY_TYPE)) {
			// 根据查询类型
			return mobileKeyTalentsService.getKeyTalentByType(getCustomerId(),
					getUserEmpId(), key, getOrganPermitId(), dto);
		} else if (type.equals(QUERY_ORGAN)) {
			// 根据机构查询
			return mobileKeyTalentsService.getKeyTalentByOrgan(getCustomerId(),
					getUserEmpId(), key, dto);
		}
		return null;

	}

	/**
	 * 离职风险预警 查询
	 */

	@ResponseBody
	@RequestMapping(value = "/getRunoffRiskWarnEmp", method = RequestMethod.GET)
	public List<KeyTalentsCardDto> queryRunoffRiskWarnEmp() {
		return mobileKeyTalentsService.queryRunoffRiskWarnEmp(getCustomerId(),
				getOrganPermitId());

	}

	/**
	 * 关键人才列表
	 */

	@ResponseBody
	@RequestMapping(value = "/getKeyTalentByName", method = RequestMethod.GET)
	PaginationDto<EmpDto> queryKeyTalentByName(String key,
			Integer page, Integer pageSize) {
		PaginationDto<EmpDto> dto = new PaginationDto<EmpDto>(
				page, pageSize);

		return mobileKeyTalentsService.queryKeyTalentByName(getCustomerId(),
				Transcode.toStringHex(key), getUserEmpId(), getOrganPermitId(), dto);
	}
}
