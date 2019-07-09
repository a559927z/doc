package net.chinahrd.manpowerCost.mvc.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.app.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerItemDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerOrganDto;
import net.chinahrd.entity.dto.app.manpowerCost.ManpowerTrendDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.manpowerCost.mvc.app.service.MobileManpowerCostService;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 移动端
 * 人力成本Controller
 * @author htpeng
 *2016年5月27日下午5:54:23
 */
@Controller
@RequestMapping("mobile/manpowerCost")
public class MobileManpowerCostController extends BaseController {
    @Autowired
    private MobileManpowerCostService mobileManpowerCostService;



    /**
     * 跳转人力成本分析
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toManpowerCostView", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
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
           	  System.out.println("拦截到的orgId:"+orgId);
           	  request.setAttribute("reqOrganId", orgId);
           	   for(OrganDto topOneOrgan:organPermit){
           		   if(topOneOrgan.getOrganizationId().equals(orgId)){
           			   request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
           			   break;
           		   }
           	   }
             }
         }
        request.setAttribute("curdate", DateUtil.getDBCurdate());
        return "mobile/manpowerCost/manpowerCost";

    }


    /**
     * 成本月度趋势
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrendByMonth", method = RequestMethod.GET)
    ManpowerTrendDto getTrendByMonth(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileManpowerCostService.queryTrendByMonth(getCustomerId(), organId, time);
    }


    /**
     * 人力成本结构
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getItemDetail", method = RequestMethod.GET)
    List<ManpowerItemDto> queryItemDetail(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileManpowerCostService.queryItemDetail(getCustomerId(), organId, time);
    }

    /**
     * 各架构人力成本
     *
     * @param organId
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrganCost", method = RequestMethod.GET)
    List<ManpowerOrganDto> queryOrganCost(String organId, String time) {
        return mobileManpowerCostService.queryOrganCost(getCustomerId(), organId, time);
    }

  
    /**
     * 获取本年的人力成本 和总成本
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProportionYear", method = RequestMethod.GET)
    public List<ManpowerCompareDto> getProportionYear(String organId, String time) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return mobileManpowerCostService.queryProportionYear(getCustomerId(), organId, time);
    }

 
    /**
     * 销售 成本  利润 明细
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllDetailData", method = RequestMethod.GET)
    public List<ManpowerDto> getAllDetailData(String organId, String time) {

        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileManpowerCostService.queryAllDetailData(getCustomerId(), organId, time);
    }

}
