package net.chinahrd.talentProfitLoss.mvc.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.talentProfitLoss.TalentProfitLossConfigDto;
import net.chinahrd.entity.dto.app.talentProfitLoss.TalentProfitLossDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.talentProfitLoss.mvc.app.service.MobileTalentProfitLossService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 人才损益  移动端
 * @author htpeng
 *2016年8月23日上午11:27:09
 */
@Controller
@RequestMapping("/mobile/talentProfitLoss")
public class MobileTalentProfitLossController extends BaseController {


	@Autowired
	private MobileTalentProfitLossService talentProfitLossService;

	@RequestMapping("/toTalentProfitLossView")
	public String toTalentProfitLossView(HttpServletRequest request) {
    	  Object obj=request.getParameter("organId");
          List<OrganDto> organPermit = getOrganPermit();
          if (CollectionKit.isNotEmpty(organPermit)) {
              if(null==obj){
            	  //当前机构id
            	  OrganDto topOneOrgan = organPermit.get(0);
  	        	request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
  	        	request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
              }else{
            	  String orgId=obj.toString();
            	  request.setAttribute("reqOrganId", orgId);
            	  boolean bool=false;
            	   for(OrganDto topOneOrgan:organPermit){
            		   if(topOneOrgan.getOrganizationId().equals(orgId)){
            			   request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
            			   bool=true;
            			   break;
            		   }
            	   }
            	   if(!bool){
            		   request.setAttribute("reqOrganName", "没有找到");
            	   }
              }
             
          }
      	request.setAttribute("time", DateUtil.getBeforeDay("yyyyMM"));
		request.setAttribute("minTime", DateUtil.getBeforeYear("yyyy-01",2));
		request.setAttribute("maxTime", DateUtil.getBeforeDay("yyyy-MM"));
		
		return "mobile/talentProfitLoss/talentProfitLoss";
	}

	/**
	 * queryChangeConfig
	 */
	@ResponseBody
   	@RequestMapping(value = "/queryChangeConfig", method = RequestMethod.POST)
	public Map<String, List<TalentProfitLossConfigDto>> queryChangeConfig() {
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		Map<String, List<TalentProfitLossConfigDto>> map = talentProfitLossService.queryChangeConfig(getCustomerId(),inflowList,outflowList);
		return map;
	}
	/**
	 * 本月/本年人才损益值
	 */
	@ResponseBody
   	@RequestMapping(value = "/getTopData", method = RequestMethod.POST)
	public Map<String, Object> getTopData(String organId,String time) {
		if (StringUtils.isEmpty(organId)) {
			return CollectionKit.newMap();
		}
		Map<String, Object> result=CollectionKit.newMap();
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		/**
		 * 流入
		 */
		Integer INPUT_map = talentProfitLossService.queryTalentInflowVal(getCustomerId(), organId,time,
				inflowList);
		result.put("input", INPUT_map);
		/**
		 * 流出
		 */
		Integer OUTPUT_map = talentProfitLossService.queryTalentOutflowVal(getCustomerId(), organId,time,
				outflowList);
		result.put("output", OUTPUT_map);
		result.put("rcsy", INPUT_map-OUTPUT_map);
		return result;
	}


	/**
	 * 人才损益-人员分布-饼图
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPopulationPie", method = RequestMethod.POST)
	public List<Object> queryPopulationPie(String organId, String time) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}

		return talentProfitLossService.queryPopulationPie(getCustomerId(), organId,time);
	}


	/**
	 * 人才损益同比
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTalentProfitLossSameData", method = RequestMethod.POST)
	public Map<String, Object> queryTalentProfitLossSameData(String organId, String times, String crowds) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> inflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
		List<Integer> outflowList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
		String year = DateUtil.getDBNow().substring(0, 4);
		String month = DateUtil.getDBNow().substring(5, 7);

		return talentProfitLossService.queryTalentProfitLossSameData(getCustomerId(), organId, year, month,
				inflowList, outflowList);
	}


	/**
	 * 异动统计饼图表格
	 */
	@ResponseBody
	@RequestMapping(value = "/queryInflowOutflowChangeType", method = RequestMethod.POST)
	public Map<String, Object> queryInflowOutflowChangeType(HttpServletResponse response, String organId, String time) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		Map<String, Object> map = talentProfitLossService.queryInflowOutflowChangeType(getCustomerId(), organId,time);
		return map;
	}


	/**
	 * 异动明细
	 */
	@ResponseBody
	@RequestMapping(value = "/queryChangeDetail", method = RequestMethod.POST)
	public PaginationDto<TalentProfitLossDto> queryChangeDetail(String organId, String time, String parentType, String type, int page, int rows) {
		if (StringUtils.isEmpty(organId)) {
			return null;
		}
		List<Integer> parentList = null;
		boolean input=true;;
		switch (parentType) {
		case "input":
			parentList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_INPUT);
			input=true;
			break;
		case "output":
			parentList = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCSY_OUTPUT);
			input=false;
			break;
		}
		;
		return talentProfitLossService.queryEntryListDatas(getCustomerId(), organId,input,
				time, parentList, type, page, rows);
	}
}
