package net.chinahrd.mvc.pc.controller.admin;

import com.alibaba.fastjson.JSON;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.UserDto;
import net.chinahrd.entity.dto.pc.admin.UserRoleDto;
import net.chinahrd.entity.dto.pc.common.FormOperType;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.RoleService;
import net.chinahrd.mvc.pc.service.admin.UserService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.ExcelUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.PropertiesUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	/**
	 * 对用户进行新增、修改、删除的操作
	 *
	 * @param userDto
	 *            用户的信息
	 * @param oper
	 *            操作类型（新增、修改、删除）
	 * @return 操作是否成功
	 */
	@ResponseBody
	@RequestMapping(value = "/operateUser", method = RequestMethod.POST)
	public boolean operateUser(UserDto userDto, String oper) {
		String customerId = getCustomerId();
		String userId = getUserId();
		if (FormOperType.ADD.getOper().equals(oper)) {
			userDto.setUserId(Identities.uuid2());
			userDto.setCustomerId(customerId);
			userDto.setCreateUserId(userId);
			userDto.setCreateTime(DateUtil.getTimestamp());
			return userService.addUser(userDto);
		}
		if (StringUtils.isEmpty(userDto.getId())) {
			return false;
		}
		boolean isSuccess = false;
		if (FormOperType.EDIT.getOper().equals(oper)) {
			userDto.setCustomerId(customerId);
			userDto.setModifyUserId(userId);
			userDto.setModifyTime(DateUtil.getTimestamp());
			isSuccess = userService.editUser(userDto);
		} else {
			List<String> ids = CollectionKit.strToList(userDto.getId());
			// TODO 这个方法可能去掉，将改为逻辑删除。更新dim_user表is_lock = 1， by jxzhng on 2016-03-17
			isSuccess = userService.deleteUser(customerId, ids);
		}
		return isSuccess;
	}

	/**
	 * 跳转用户管理页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		return "biz/admin/user/user-list";
	}

	/**
	 * 查询用户信息
	 *
	 * @param userName
	 * @param page
	 * @param rows
	 * @param sidx
	 * @param sord
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findUserAll", method = RequestMethod.POST)
	public PaginationDto<UserDto> findUserAll(String userName, Integer page, Integer rows, String sidx, String sord) {
		PaginationDto<UserDto> dto = new PaginationDto<UserDto>(page, rows);
		dto = userService.findAll(getCustomerId(), userName, dto);
		return dto;
	}

	/**
	 * 修改用户密码信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePasswd", method = RequestMethod.POST)
	public boolean updateUserPasswd(@RequestParam("userKey") String userKey, @RequestParam("passwd") String inputPwd) {
		// 组装明密 by jxzhang
		boolean result = false;
		if (userKey.length() < 1) {
			String keyPwd = inputPwd + StringUtils.substring(EisWebContext.getCurrentUser().getUserKey(), 1, 2);
			String enPwd = Base64.encodeToString(keyPwd.getBytes());
			result = userService.updateUserPasswd(enPwd, getUserId(), getCustomerId());
		} else {
			String keyPwd = inputPwd + StringUtils.substring(userKey, 1, 2);
			String enPwd = Base64.encodeToString(keyPwd.getBytes());
			result = userService.updateUserPasswdByUserKey(enPwd, userKey, EisWebContext.getCustomerId());
			HttpSession session = request.getSession(true);
			session.removeAttribute("verCode_" + userKey);
		}
		if (result) {
			getUserInfo().setPassword(inputPwd);
		}
		return result;
	}

	/**
	 * 修改特定用户密码信息,若是还原密码，则改为123456
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserPWDbyUserName", method = RequestMethod.POST)
	public boolean updateUserPWDbyUserName(@RequestParam("passwd") String inputPwd,
			@RequestParam("userName") String userName) {
		String enPwd = "";
		String defaultPWD = PropertiesUtil.getProperty("user.password").trim();
		if (defaultPWD.equals(inputPwd)) {
			enPwd = defaultPWD;
		} else {
			String keyPwd = inputPwd + StringUtils.substring(EisWebContext.getCurrentUser().getUserKey(), 1, 2);
			enPwd = Base64.encodeToString(keyPwd.getBytes());
		}
		boolean result = userService.updateUserPWDbyUserName(enPwd, userName, getCustomerId());
		return result;
	}

	/**
	 * 根据用户信息查询角色
	 *
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findRoleOfUser", method = RequestMethod.POST)
	public List<UserRoleDto> findRoleOfUser(String userId) {
		List<UserRoleDto> dtos = roleService.findRoleOfUser(getCustomerId(), userId);
		return dtos;
	}

	/**
	 * 添加用户角色关联
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addUserRoles", method = RequestMethod.POST)
	public ResultDto<String> addUserRoles(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "roleIds[]", required = false) String[] roleIds) {
		ResultDto<String> result = new ResultDto<String>();
		roleService.addUserRole(getCustomerId(), userId, roleIds, getUserId());
		result.setType(true);
		return result;
	}

	/**
	 * 用户绑定员工 - (同步)
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userBindEmp", method = RequestMethod.POST)
	public ResultDto<String> bindEmp(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "empId") String empId) {

		ResultDto<String> result = new ResultDto<>();
		// 被操作角色对象
		boolean b = userService.userBindEmp(getCustomerId(), userId, empId);
		if (b) {
			result.setType(true);
			result.setMsg("绑定成功！");
		} else {
			result.setType(false);
			result.setMsg("绑定失败！");
		}
		return result;
	}

	/**
	 * 跳转员工数据配置页面 - (同步)
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userOrgan", method = RequestMethod.GET)
	public String roleOrgan(Model model, @RequestParam(value = "userId") String userId) {
		// 被操作角色对象
		UserDto dto = userService.findUserById(getCustomerId(), userId);
		model.addAttribute("userDto", dto);
		return "biz/admin/user/user-organ";
	}

	/**
	 * 判断用户是否存在
	 * 
	 * @param empId
	 * @param empKey
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "existUser")
	public String existUser(@RequestParam("userName") String userName, String empId) {
		Integer count = userService.existUser(empId, userName);
		if (count.equals(0)) {
			return "false";
		}
		return "true";

	}

	/**
	 * 执行解析excel
	 */
	@RequestMapping(value = "doImportExcel", method = RequestMethod.POST)
	public String doImportExcel(@RequestParam(value = "inputfile") MultipartFile file, Model model,
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

}
