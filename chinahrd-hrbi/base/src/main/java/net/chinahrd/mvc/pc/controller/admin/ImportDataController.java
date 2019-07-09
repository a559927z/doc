package net.chinahrd.mvc.pc.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.ImportDataService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ExcelUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by jxzhang on 15/6/23.
 */
@Controller
@RequestMapping(value = "/importData")
public class ImportDataController extends BaseController {

	 @Autowired
	 private ImportDataService importDataService;

	@RequestMapping(value = "home")
	public String organList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return "biz/admin/importData/home";
	}

	/**
	 * 执行解析excel
	 */
	@ResponseBody
	@RequestMapping(value = "doImportExcel", method = RequestMethod.POST)
	public ResultDto<String> doImportExcel(
			@RequestParam(value = "inputfile",required=false) MultipartFile file,
			@RequestParam(value = "type",required=false) String type) throws IOException {

		List<Map<String, Object>> excelData = CollectionKit.newList();
		String fileName = file.getOriginalFilename();
		String extensionName = ExcelUtil.getExtensionName(fileName);

		if (extensionName.equalsIgnoreCase("xls")) {
			excelData = ExcelUtil.readExcel2003(file, getRex(type));
		} else if (extensionName.equalsIgnoreCase("xlsx")) {
			excelData = ExcelUtil.readExcel2007(file, getRex(type));
		}
		
		ResultDto<String> result = new ResultDto<String>();
		if(excelData.size() == 0){
			result.setMsg("模板格式不正确!");
			return result;
		}
		
		boolean isSuccess = importDataService.addOrgans(excelData, getCustomerId(), getUserId());
		result.setType(isSuccess);
		result.setMsg(isSuccess ? "导入数据成功!" : "导入数据失败!");
		return result;
	}

	private String getRex(String type) {
		String rex = "";
		if (type.equals("organ")) {
			rex = "business_unit_key,organization_type_key,organization_key,organization_parent_key,organization_name,is_single,effect_data,enabled,";
		}
		return rex;
	}


}
