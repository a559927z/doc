package net.chinahrd.mvc.pc.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.PojoDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.OrganService;
import net.chinahrd.utils.ExcelUtil;

/**
 * Created by jxzhang on 15/6/23.
 */
@Controller
@RequestMapping(value = "/organ")
public class OrganController extends BaseController {

    @Autowired
    private OrganService organService;

	/*==============================================================*/
    /*  配置页面使用-配置数据权限							            */
    /*==============================================================*/

    /**
     * 根据用户ID查询用户组织架构权限-user2organ
     *
     * @param model
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTreeDataJson", method = RequestMethod.POST)
    public Object getTreeDataJson(Model model, @RequestParam(value = "userId") String userId) {

        // 被操作角色对象已存的所有数据（包括：全勾、半勾）
        List<OrganDto> existOrgans = organService.queryUserOrgans(userId,
                getCustomerId(), false);

        List<TreeDto> treeDtos = organService.dbToZtree(existOrgans);
        Object json = JSON.toJSON(treeDtos);
        return json;
    }

    /**
     * 添加角色数据权限-user2organ
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

        String userId = pojoDto.getUserId();
        List<OrganDto> organDtos = pojoDto.getOrganDto();

        if (organDtos.size() == 0) {
            rs = organService.deleteUserOrganization(userId, getCustomerId());
            result.setMsg("删除成功");
        } else {

            rs = organService.addUserOrganization(userId, getUserId(), getCustomerId(), organDtos);
            result.setMsg("添加成功");
        }
        result.setType(rs);
        return result;
    }
	
	
	
	/*==============================================================*/
	/*  指标页面使用-机构树选择							                */
	/*==============================================================*/

    /**
     * 使用时机构树，提供给 @see organTreeSeletor
     *
     * @param id
     * @param isSingleOrgan 是否独立核算
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryOrganTree", method = RequestMethod.POST)
    public List<TreeDto> queryOrganTree(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "isSingleOrgan", required = false) Boolean isSingleOrgan) {

        List<TreeDto> list = organService.queryOrganTree(getUserId(),
                getCustomerId(), isSingleOrgan);

        return list;
    }
	
	/*==============================================================*/
	/*  指标页面使用-获取机构名称							                */
	/*==============================================================*/

    /**
     * 使用时机构树，提供给 @see organTreeSeletor
     *
     * @param id
     * @return name
     */
    @ResponseBody
    @RequestMapping(value = "/getOrganNameById", method = RequestMethod.POST)
    public ResultDto<String> getOrganNameById(
            @RequestParam(value = "id", required = false) String id) {
        ResultDto<String> rs = new ResultDto<String>();
        OrganDto dto = organService.findOrganById(id, getCustomerId());
        String organName = dto == null ? null : dto.getOrganizationName();
        rs.setType(true);
        rs.setT(organName);
        return rs;
    }


    /**
     * 组件机构管理页面
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "list")
    public String organList(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        return "biz/admin/organ/organ-list";
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
    @RequestMapping(value="findList")
    public List<OrganDto> findList(OrganDto org){
    	org.setCustomerId(getCustomerId());
    	return organService.findList(org);
    }

}
