package net.chinahrd.mvc.pc.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.EmpDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.PojoDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.admin.UserDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.emp.DimEmp;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.EmpOrganService;
import net.chinahrd.mvc.pc.service.admin.OrganService;
import net.chinahrd.mvc.pc.service.admin.UserService;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ExcelUtil;
import net.chinahrd.utils.Str;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

/**
 * Created by jxzhang on 15/6/23.
 */
@Controller
@RequestMapping(value = "/emp")
public class EmpOrganController extends BaseController {

	@Autowired
	private EmpOrganService empOrganService;
	
	@Autowired
	private OrganService organService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		  return "biz/admin/emp/emp-list";
	}
  /**
  * 跳转员工数据配置页面 - (同步)
  *
  * @param model
  * @return
  */
 @RequestMapping(value = "/empOrgan", method = RequestMethod.GET)
 public String roleOrgan(Model model,
                         @RequestParam(value = "empId") String empId) {
     // 被操作角色对象
     EmpDto dto = empOrganService.findEmpById(getCustomerId(), empId);
     model.addAttribute("empDto", dto);
     return "biz/admin/emp/emp-organ";
 }
	
 /**
  * 跳转用户项目配置页面 - (同步)
  *
  * @param model
  * @return
  */
 @RequestMapping(value = "/empPro", method = RequestMethod.GET)
 public String userPro(Model model,
         @RequestParam(value = "empId") String empId) {
     // 被操作员工对象
	  EmpDto dto = empOrganService.findEmpById(getCustomerId(), empId);
	     model.addAttribute("empDto", dto);
     return "biz/admin/emp/emp-pro";
 }
 
 /**
  * 查询员工信息
  *
  * @param keyName
  * @param page
  * @param rows
  * @param sidx
  * @param sord
  * @return
  */
 @ResponseBody
 @RequestMapping(value = "/findEmpAll", method = RequestMethod.POST)
 public PaginationDto<net.chinahrd.entity.dto.pc.common.EmpDto> findEmpAll(String keyName,String reqOrgId, Integer page, Integer rows, String sidx, String sord) {
     PaginationDto<net.chinahrd.entity.dto.pc.common.EmpDto> dto = new PaginationDto<net.chinahrd.entity.dto.pc.common.EmpDto>(page, rows);
     dto = commonService.findEmpAll(keyName,reqOrgId, dto, sidx, sord, getCustomerId());
     return dto;
 }

	/**
	 * 执行解析excel
	 */
	@RequestMapping(value = "doImportExcel", method = RequestMethod.POST)
	public String doImportExcel(
			@RequestParam(value = "inputfile") MultipartFile file, Model model,
			HttpServletRequest request) throws IOException {
		List<Map<String, Object>> excelData = new ArrayList<Map<String, Object>>();

		String fileName = file.getOriginalFilename();
		String extensionName = ExcelUtil.getExtensionName(fileName);
		if (extensionName.equalsIgnoreCase("xls")) {
			excelData = ExcelUtil.readExcel2003(file, null);
		} else if (extensionName.equalsIgnoreCase("xlsx")) {
			excelData = ExcelUtil.readExcel2007(file, null);
		}
		String excelDataJson = JSON.toJSONString(excelData);
		request.setAttribute("excelDataJson", excelDataJson);

		return "biz/admin/organ/organ-list";
	}

    @ResponseBody
    @RequestMapping(value = "/getTreeDataJson", method = RequestMethod.POST)
    public Object getTreeDataJson(Model model, @RequestParam(value = "empId") String empId) {

        // 被操作角色对象已存的所有数据（包括：全勾、半勾）
        List<OrganDto> existOrgans = empOrganService.queryEmpOrgans(empId,
                getCustomerId(), false);

        List<TreeDto> treeDtos = empOrganService.dbToZtree(existOrgans);
        Object json = JSON.toJSON(treeDtos);
        return json;
    }
    
    /**
     * 添加角色数据权限
     *
     * @param pojoDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addEmpOrganiation", method = RequestMethod.POST)
    public ResultDto<String> addEmpOrganiation(
            @RequestBody(required = false) PojoDto pojoDto) {

        ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        result.setMsg("机构已存操作失败");

        if (null == pojoDto) {
            result.setMsg("对当前员工操作有误，请重新选择员工进行操作");
        }

        String empId = pojoDto.getEmpId();
        List<OrganDto> organDtos = pojoDto.getOrganDto();

        if (organDtos.size() == 0) {
            rs = empOrganService.deleteEmpOrganization(empId, getCustomerId());
            result.setMsg("删除成功");
        } else {

            rs = empOrganService.addEmpOrganization(empId, getUserId(),
                    getCustomerId(), organDtos);
            result.setMsg("添加成功");
        }
        result.setType(rs);
        return result;
    }
    
    /**
     * 进入员工添加界面
     */
    @RequestMapping(value = "toModifyEmp")
    public String  toAddEmp(Model model, String empId, String userId){
    	model.addAttribute("empId", getUserEmpId());
    	if(!Str.IsEmpty(empId))
    		model.addAttribute("emp", empOrganService.getDimEmp(empId));
    	if(!Str.IsEmpty(userId)){
    		UserDto user = userService.findUserById(getCustomerId(), userId);
    		if(user != null && !Str.IsEmpty(user.getEmpId()))
    			model.addAttribute("emp", empOrganService.getDimEmp(user.getEmpId()));
    	}
    	return "biz/employee/modifyEmp";
    }
    /**
     * 获取所有“枚举”
     */
    @ResponseBody
    @RequestMapping(value = "getDirs")
    public Map<String, Object> getDir(Model model, @RequestParam("empId") String empId, String parentOrgId){
    	Map<String, Object> target = CollectionKit.newMap();
    	//主序列
    	target.put("sequnce", CacheHelper.getSequence());
    	//子序列
    	target.put("subsequnce", CacheHelper.getSubSequence());
    	//职位
    	target.put("position", CacheHelper.getPosition());
    	//职位层级
    	target.put("positionAbility", commonService.queryAbilityHashMap(getCustomerId()));
    	//职级
    	target.put("abilityLv", commonService.queryAbilityLvHashMap(getCustomerId()));
    	//招聘渠道
    	target.put("channel", CacheHelper.getChannel()); 
    	//职衔
    	target.put("jobTitle", CacheHelper.getJobTitle());
    	//学历
    	target.put("degree", CacheHelper.getDegree());
    	//工作地址
    	target.put("workplace", CacheHelper.getCity());
    	//工作单位
    	OrganDto org = new OrganDto();
    	org.setCustomerId(getCustomerId());
    	target.put("parentorgrization", organService.findCompanyList(org));
    	//工作部门
    	if(!Str.IsEmpty(parentOrgId)){
	    	org.setDepth(-1);
	    	org.setOrganizationParentId(parentOrgId);
	    	org.setOrganizationTypeId(null);
	    	target.put("orgrization", organService.findList(org));
    	}
    	return target;
    }
    @ResponseBody
    @RequestMapping(value = "existEmp")
    public String existEmp(String empId, String empKey, String userName){
    	Integer count = commonService.existEmp(empId, empKey, userName);
    	if(count.equals(0)){
    		return "false";
    	}
    	return "true";
    	
    }
    
    /**
     * 保存员工
     */
    @ResponseBody
    @RequestMapping(value = "saveEmpInfo", method = RequestMethod.POST)
    public DimEmp saveEmpInfo(DimEmp emp){
    	//计算id,年龄
    	if(Str.IsEmpty(emp.getEmpId()))
    		return empOrganService.saveEnmInfo(emp,getCustomerId());
    	else
    		return empOrganService.updateEmpInfo(emp,getCustomerId());
    }

    
}
