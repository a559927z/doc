package net.chinahrd.mvc.pc.controller.admin;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.PojoDto;
import net.chinahrd.entity.dto.pc.admin.RoleDto;
import net.chinahrd.entity.dto.pc.admin.RoleFunctionDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.common.FormOperType;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.mvc.pc.service.admin.OrganService;
import net.chinahrd.mvc.pc.service.admin.RoleService;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionService functionService;

    @Autowired
    private OrganService organService;

    /**
     * 跳转角色管理页面
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "biz/admin/role/role-list";
    }

    /**
     * 对用户进行新增、修改、删除的操作
     *
     * @param roleDto 用户的信息
     * @param oper    操作类型（新增、修改、删除）
     * @return 操作是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/operateRole", method = RequestMethod.POST)
    public boolean operateRole(RoleDto roleDto, String oper) {
    	
        if (FormOperType.ADD.getOper().equals(oper)) {
            roleDto.setRoleId(Identities.uuid2());
            roleDto.setCustomerId(getCustomerId());
            roleDto.setCreateUserId(getUserId());
            roleDto.setCreateTime(DateUtil.getTimestamp());
            return roleService.addRole(roleDto);
        }
        if (StringUtils.isEmpty(roleDto.getId())) {
            return false;
        }
        boolean isSuccess = false;
        if (FormOperType.EDIT.getOper().equals(oper)) {
            roleDto.setModifyUserId(getUserId());
            roleDto.setCustomerId(getCustomerId());
            roleDto.setModifyTime(DateUtil.getTimestamp());
            isSuccess = roleService.editRole(roleDto);
        } /*else {
            List<String> ids = CollectionKit.strToList(roleDto.getId());
            //bug 317 用户使用的角色不能删除	by jxzhang
            int hasEmpByRoleId = roleService.hasEmpByRoleId(getCustomerId(), ids);
            if(hasEmpByRoleId > 0){
            	return false;
            }
            isSuccess = roleService.deleteRole(getCustomerId(), ids);
        }*/
        return isSuccess;
    }
    
    
	@ResponseBody
	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
	public ResultDto<String> delRole(String[] ids) {
		//bug 317 用户使用的角色不能删除	by jxzhang
		 ResultDto<String> result = new ResultDto<>();
		boolean isSuccess = false;
		List<String> asList = Arrays.asList(ids);
		int hasEmpByRoleId = roleService.hasEmpByRoleId(getCustomerId(),asList);
		if (hasEmpByRoleId > 0) {
			result.setMsg("用户使用的角色不能删除");
			result.setType(isSuccess);
			return result;
		}
		isSuccess = roleService.deleteRole(getCustomerId(), asList);
		result.setMsg("删除成功");
		result.setType(isSuccess);
		return result;
	}


    /**
     * 加载所有角色信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findRoleAll", method = RequestMethod.POST)
    public PaginationDto<RoleDto> findRoleAll(String userName,
                                              Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<RoleDto> dto = new PaginationDto<RoleDto>(page, rows);
        dto = roleService.findAll(getCustomerId(), dto);
        return dto;
    }

    /**
     * 跳转角色功能配置页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/roleFunction", method = RequestMethod.GET)
    public String roleFunction(Model model, @RequestParam(value = "roleId") String roleId) {
        String customerId = getCustomerId();
        RoleDto dto = roleService.findRoleById(customerId, roleId);
        List<RoleFunctionDto> list = functionService.findFunctionAll(customerId, null);
        model.addAttribute("roleDto", dto);
        model.addAttribute("list", list);
        return "biz/admin/role/role-function";
    }

    /**
     * 添加角色功能权限
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRoleFunction", method = RequestMethod.POST)
    public ResultDto<String> addRoleFunction(
            @RequestParam(value = "roleId") String roleId,
            @RequestParam(value = "functionItem[]",required=false) String[] functionItems) {
        ResultDto<String> result = new ResultDto<>();
        functionService.addRoleFunction(getCustomerId(), roleId, functionItems, getUserId());
        result.setType(true);
        return result;
    }

    /**
     * 获取角色关联的功能及功能操作信息
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRoleFunction", method = RequestMethod.POST)
    public List<RoleFunctionDto> getRoleFunction(
            @RequestParam(value = "roleId") String roleId) {
        List<RoleFunctionDto> list = functionService.findRoleFunction(getCustomerId(), roleId);
        return list;
    }

    /**
     * 跳转角色数据配置页面 - (异步)
     *
     * @param model
     * @return
     */
//    @RequestMapping(value = "/roleOrganAsync", method = RequestMethod.GET)
//    public String roleOrganAsyn(Model model,
//                            @RequestParam(value = "roleId") String roleId) {
//        RoleDto dto = roleService.findRoleById(getCustomerId(), roleId);
//        model.addAttribute("roleDto", dto);
//
//        List<OrganDto> roleOrgans = organService.queryRoleOrgans(roleId,
//                getCustomerId(), true);
//        String fullPathsStr = CollectionKit.extractToString(roleOrgans,
//                "fullPath", ",");
//        model.addAttribute("fullPathsStr", fullPathsStr);
//
//        return "biz/admin/role/role-organ-async";
//    }

    /**
     * 跳转角色数据配置页面 - (同步)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/roleOrgan", method = RequestMethod.GET)
    public String roleOrgan(Model model,
                            @RequestParam(value = "roleId") String roleId) {
        // 被操作角色对象
        RoleDto dto = roleService.findRoleById(getCustomerId(), roleId);
        model.addAttribute("roleDto", dto);
        return "biz/admin/role/role-organ";
    }
    @ResponseBody
    @RequestMapping(value = "/getTreeDataJson", method = RequestMethod.POST)
    public Object getTreeDataJson(Model model, @RequestParam(value = "roleId") String roleId) {

        // 被操作角色对象已存的所有数据（包括：全勾、半勾）
        List<OrganDto> existOrgans = organService.queryRoleOrgans(roleId,
                getCustomerId(), false);

        List<TreeDto> treeDtos = organService.dbToZtree(existOrgans);
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
    @RequestMapping(value = "/addRoleOrganiation", method = RequestMethod.POST)
    public ResultDto<String> addRoleOrganiation(
            @RequestBody(required = false) PojoDto pojoDto) {
        ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        result.setMsg("机构已存操作失败");

        if (null == pojoDto) {
            result.setMsg("对当前角色操作有误，请重新选择角色进行操作");
        }

        String roleId = pojoDto.getRoleId();
        List<OrganDto> organDtos = pojoDto.getOrganDto();

        if (organDtos.size() == 0) {
            rs = organService.deleteRoleOrganization(roleId, getCustomerId());
            result.setMsg("删除成功");
        } else {

            rs = organService.addRoleOrganization(roleId, getUserId(),
                    getCustomerId(), organDtos);
            result.setMsg("添加成功");
        }
        result.setType(rs);
        return result;
    }

}
