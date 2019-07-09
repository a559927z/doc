package net.chinahrd.db.input.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.db.input.service.DbImportBaseService;
import net.chinahrd.db.input.service.DbImportChurnRateService;
import net.chinahrd.db.input.service.DbImportMakeDataService;
import net.chinahrd.db.input.service.DbImportPerBenefitService;
import net.chinahrd.entity.dto.pc.common.ResultDto;

/**
 * 
 * @author jxzhang on 2016-02-29
 *
 */
@Controller
@RequestMapping("/dbImportJSP")
public class DbImportJSPController {

	/**
	 * 基础相关（基础维度相关）
	 */
	@Autowired
	private DbImportBaseService dbImportBaseService;



	/**
	 * 人均效益相关
	 */
	@Autowired
	private DbImportPerBenefitService dbImportPerBenefitService;

	/**
	 * 流失率相关
	 */
	@Autowired
	private DbImportChurnRateService dbImportChurnRateService;

	@Autowired
	private DbImportMakeDataService dbImportMakeDataService;
	
	@RequestMapping(value = "/toDbImportView", method = RequestMethod.GET)
	public String dbImport(HttpServletRequest request, String organId) {
		return "db/dbImport";
	}

	
	@ResponseBody
	@RequestMapping(value = "/makeData", method = RequestMethod.POST)
	public ResultDto<String> makeData(String key) {
		ResultDto<String> result = new ResultDto<>();
		
		boolean rs = false;
		if (key.equals("addDimEmpDays")) {
			ResourceBundle bundle = ResourceBundle.getBundle("conf/db", Locale.getDefault());
			String addDimEmpDays1 = bundle.getString("addDimEmpDays1");
			String addDimEmpDays2 = bundle.getString("addDimEmpDays2");
			rs = dbImportMakeDataService.addDimEmpDays(addDimEmpDays1, addDimEmpDays2);
		}
		result.setType(rs);
		return result;
	}
	

	@ResponseBody
	@RequestMapping(value = "/historyEmpCountMonth", method = RequestMethod.POST)
	public ResultDto<String> historyEmpCountMonth() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		result.setType(rs);
		return result;
	}


	@ResponseBody
	@RequestMapping(value = "/repleacDimOrganization", method = RequestMethod.POST)
	public ResultDto<String> repleacDimOrganization() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		rs = dbImportBaseService.replaceDimOrganization(false);
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/callEmpAll", method = RequestMethod.POST)
	public ResultDto<String> callALL(String buiss) {
		System.out.println(buiss);
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		if (buiss.equals("repleacSoureVDimEmp")) {
			//删除代码生成假数据
//			rs = dbImportService.repleacSoureVDimEmp();
		}
		if (buiss.equals("replaceVDimEmp")) {
		}
		if (buiss.equals("jobChange")) {
		}
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/callUnderling", method = RequestMethod.POST)
	public ResultDto<String> callUnderling() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/callHistoryEmpCount", method = RequestMethod.POST)
	public ResultDto<String> callHistoryEmpCount() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/replaceMonthlyEmpCount", method = RequestMethod.POST)
	public ResultDto<String> replaceMonthlyEmpCount() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		rs = dbImportChurnRateService.replaceMonthlyEmpCount();
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/callEmpOvertimeDay", method = RequestMethod.POST)
	public ResultDto<String> callEmpOvertimeDay() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/callTradeProfit", method = RequestMethod.POST)
	public ResultDto<String> callTradeProfit() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		rs = dbImportPerBenefitService.callTradeProfit();
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/callFactfte", method = RequestMethod.POST)
	public ResultDto<String> callFactfte() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		rs = dbImportPerBenefitService.callFactfte();
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/callTargetBenefitValue", method = RequestMethod.POST)
	public ResultDto<String> callTargetBenefitValue() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		rs = dbImportPerBenefitService.callTargetBenefitValue();
		result.setType(rs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/callDimTables", method = RequestMethod.POST)
	public ResultDto<String> callDimTables() {
		ResultDto<String> result = new ResultDto<>();
		boolean rs = false;
		rs = dbImportBaseService.callDimTables();
		result.setType(rs);
		return result;
	}

}
