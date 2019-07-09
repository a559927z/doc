package net.chinahrd.mvc.pc.controller.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.utils.ExcelUtil;
import net.chinahrd.utils.UploadUtil;
import net.chinahrd.utils.export.PhantomJSUtil;

@Controller
@RequestMapping("/demo")
public class DemoController {
	@RequestMapping(value = "/toPerBenefitView")
	public ModelAndView toPerBenefitView() throws Exception {

		return new ModelAndView("biz/productivity/perBenefitTrend");
	}

	/**
	 * 导出Demo
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exportDemo")
	public String exportDemo() {
		return "demo/exportDemo";
	}

	@ResponseBody
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportDemo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PhantomJSUtil.export("monthReport", "/demo/display", request, response);
	}

	@RequestMapping("/display")
	public String renderMonthRepost() {
		return "demo/exportDemoExport";
	}

	/**
	 * 组件Demo
	 * 
	 * @return
	 */
	@RequestMapping(value = "/compDemo")
	public String compDemo() {
		return "demo/compDemo";
	}

	@RequestMapping(value = "/barDemo")
	public String demo() {
		return "demo/barDemo";
	}

	@RequestMapping(value = "/navMenuDemo")
	public String navMenuDemo() {
		return "demo/navMenuDemo";
	}

	@RequestMapping(value = "/timeLineDemo")
	public String timeLineDemo() {
		return "demo/timeLineDemo";
	}

	@RequestMapping(value = "/searchBoxDemo")
	public String searchBoxDemo() {
		return "demo/searchBoxDemo";
	}

	@RequestMapping(value = "/vernierCursorDemo")
	public String vernierCursorDemo() {
		return "demo/vernierCursorDemo";
	}

	/**
	 * 琛ㄦ牸缁勪欢
	 *
	 * @return
	 */
	@RequestMapping(value = "/gridDemo")
	public String gridDemo() {
		return "demo/gridDemo";
	}

	/**
	 * 鏍戠粍浠�
	 *
	 * @return
	 */
	@RequestMapping(value = "/treeDemo")
	public String treeDemo() {
		return "demo/treeDemo";
	}

	/**
	 * 鏈烘瀯鏍戠粍浠�
	 *
	 * @return
	 */
	@RequestMapping(value = "/orgTreeDemo")
	public String orgTreeDemo() {

		return "demo/orgTreeDemo";
	}

	/**
	 * 鏃ユ湡缁勪欢
	 *
	 * @return
	 */
	@RequestMapping(value = "/dateTimeDemo")
	public String dateTimeDemo() {
		return "demo/dateTimeDemo";
	}

	/**
	 * 娑堟伅鎻愮ず
	 *
	 * @return
	 */
	@RequestMapping(value = "/tooltipDemo")
	public String tooltipDemo() {
		return "demo/tooltipDemo";
	}

	/**
	 * 娑堟伅鎻愮ず
	 *
	 * @return
	 */
	@RequestMapping(value = "/messengerDemo")
	public String notifyDemo() {
		return "demo/messengerDemo";
	}

	/**
	 * 璺宠浆鍒拌В鏋恊xcel
	 *
	 * @return
	 */
	@RequestMapping(value = "/importExcelDemo")
	public String importExcelDemo() {
		return "demo/importExcelDemo";
	}

	/**
	 * 鎵ц瑙ｆ瀽excel
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/doImportExcel", method = RequestMethod.POST)
	public @ResponseBody Object doImportExcel(@RequestParam(value = "/inputfile") MultipartFile file)
			throws IOException {
		ResultDto result = new ResultDto();
		// 鍑嗗杩斿洖鍊煎垪琛�
		List<Map<String, Object>> valueList = new ArrayList<Map<String, Object>>();

		String fileName = file.getOriginalFilename();
		String extensionName = ExcelUtil.getExtensionName(fileName);
		if (extensionName.equalsIgnoreCase("xls")) {
			valueList = ExcelUtil.readExcel2003(file, null);
		} else if (extensionName.equalsIgnoreCase("xlsx")) {
			valueList = ExcelUtil.readExcel2007(file, null);
		}
		return valueList;
	}

	/**
	 * 鏍囩椤�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tabsDemo")
	public String tabsDemo() {
		return "demo/tabsDemo";
	}

	/**
	 * 涓婁紶闄勪欢demo
	 * 
	 * @return
	 */
	@RequestMapping(value = "/webuploaderDemo")
	public String webuploadDemo() {
		return "demo/webuploaderDemo";
	}

	@ResponseBody
	@RequestMapping(value = "/doUpload")
	public Object doUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String fileName = UploadUtil.upload(file, "/assets/upload/user/", request);
			System.out.println(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 鍥剧墖鎳掑姞杞�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/lazyLoadDemo")
	public String lazyLoadDemo() {
		return "demo/lazyLoadDemo";
	}

	/**
	 * 鍥剧墖鎳掑姞杞�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/riskTreeDemo")
	public String riskTreeDemo() {
		return "demo/riskTreeDemo";
	}

	/**
	 * 鍥剧墖鎳掑姞杞�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/riskTreeDemo2")
	public String riskTreeDemo2() {
		return "demo/riskTreeDemo2";
	}

	/**
	 * 浜烘墠鍦板浘
	 * 
	 * @return
	 */
	@RequestMapping(value = "/talentMapDemo")
	public String talentMapDemo() {
		return "demo/talentMapDemo";
	}

	/**
	 * 闄勫姞瀵艰埅
	 * 
	 * @return
	 */
	@RequestMapping(value = "/affixNavDemo")
	public String affixNavDemo() {
		return "demo/affixNavDemo";
	}

	/**
	 * 鏈烘瀯鏍戠粍浠�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/organTreeDemo")
	public String organTreeDemo() {
		return "demo/organ_tree_demo";
	}

	/**
	 * 鑱屼綅搴忓垪缁熻
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sequenceCountDemo")
	public String sequenceCountDemo() {
		return "demo/sequenceCountDemo";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pieTableDemo")
	public String pieTableDemo() {
		return "demo/pieTableDemo";
	}

}
