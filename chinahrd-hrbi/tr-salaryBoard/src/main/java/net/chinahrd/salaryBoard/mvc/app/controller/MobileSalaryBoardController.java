package net.chinahrd.salaryBoard.mvc.app.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryEmpCRDto;
import net.chinahrd.entity.dto.app.salaryBoard.SalaryWageDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.salaryBoard.mvc.app.service.MobileSalaryBoardService;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 薪酬看板 app
 * @author hjli
 *2016年11月21日上午11:45:45
 */
@Controller
@RequestMapping("mobile/salaryBoard")
public class MobileSalaryBoardController extends BaseController {
	
	@Autowired
	private MobileSalaryBoardService mobileSalaryBoardService;
    
    @RequestMapping(value = "/toSalaryBoardView")
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
        return "mobile/salaryBoard/salaryBoard";
    }

    /**
     * 薪酬占人力成本比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryProportion", method = RequestMethod.GET)
    public Map<String,Object> getSalaryProportion(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryProportion(getCustomerId(), organId);
    }
    
    /**
     * 投资回报率
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryRateOfReturn", method = RequestMethod.GET)
    public Map<String,Object> getSalaryRateOfReturn(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryRateOfReturn(getCustomerId(), organId);
    }
    /**
     * 薪酬总额统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryPayTotal", method = RequestMethod.GET)
    public Map<String,Object> getSalaryPayTotal(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryPayTotal(getCustomerId(), organId);
    }
    
    /**
     * 下级组织薪酬统计以及平均统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalarySubOrganization", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalarySubOrganization(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalarySubOrganization(getCustomerId(), organId);
    }
    
    /**
     * 人力资本投资回报率月度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryMonthRateOfReturn", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryMonthRateOfReturn(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryMonthRateOfReturn(getCustomerId(), organId);
    }
    
    /**
     * 组织KPI达标率、人力成本、薪酬总额的年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryCostKpi", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryCostKpi(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryCostKpi(getCustomerId(), organId);
    }
    
    
    
    /**
     * 营业额、利润、人力成本及薪酬总额的月度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryCostSalesProfit", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryCostSalesProfit(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryCostSalesProfit(getCustomerId(), organId);
    }
    
    /**
     * 行业分位值年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryBitValueYear", method = RequestMethod.GET)
    public List<SalaryBoardDto> getSalaryBitValueYear (String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryBitValueYear(getCustomerId(), organId);
    }
    
    
    /**
     * 员工CR值
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryEmpCR", method = RequestMethod.GET)
    public Map<String,Object> getSalaryEmpCR (String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryEmpCR(getCustomerId(), organId);
    }
    
    /**
     * 员工CR值分页
     *
     * @param keyName
     * @param page
     * @param rows
     * @param sidx
     * @param sord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSalaryEmpCR", method = RequestMethod.POST)
    public PaginationDto<SalaryEmpCRDto> findSalaryEmpCR(String organId, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<SalaryEmpCRDto> dto = new PaginationDto<SalaryEmpCRDto>(page, rows);
        dto = mobileSalaryBoardService.findSalaryEmpCR(organId, dto, sidx, sord, getCustomerId());
        return dto;
    }
    
    /**
     * 薪酬差异度岗位表
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryDifferencePost", method = RequestMethod.POST)
    public PaginationDto<SalaryBoardDto> getSalaryDifferencePost (String organId, int page, int rows) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryDifferencePost(getCustomerId(), organId,page,rows);
    }
    
    /**
     * 年终奖金总额与利润的年度趋势
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalaryBonusProfit", method = RequestMethod.GET)
    public List<SalaryWageDto> getSalaryBonusProfit(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return mobileSalaryBoardService.getSalaryBonusProfit(getCustomerId(), organId);
    }

    
}
