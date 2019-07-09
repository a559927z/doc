package net.chinahrd.platform.demo.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.chinahrd.common.AbstractBaseController;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.common.auth.InjectCurrentUser;
import net.chinahrd.common.validate.MyValid;
import net.chinahrd.platform.demo.request.DemoUserQueryReq;
import net.chinahrd.platform.demo.response.DemoUserInfoRes;
import net.chinahrd.platform.demo.service.DemoUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bright
 * @since 2019-03-06
 */

@InjectCurrentUser
@RestController
@RequestMapping("/demo/userInfo")
@Api(value="/demo/userInfo",description="用户管理")
public class DemoUserInfoController extends AbstractBaseController {
	@Reference
	private DemoUserInfoService userInfoService;

	@MyValid(DemoUserQueryReq.class)
	@ApiOperation(value = "用户列表分页查询", notes = "selectByPage", httpMethod = "POST")
	@PostMapping(value = "/selectByPage")
	public Object selectByPage(@RequestBody DemoUserQueryReq dataReq, CurrentUser user) {
		if (user != null && StringUtils.isNotBlank(user.getUserName())) {
			System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
		}

		if (dataReq.getCurrentUser() != null) {
			System.out.println("用户信息已经自动注入dataReq参数,userName:" + dataReq.getCurrentUser().getUserName());
		}

		PageData<DemoUserInfoRes> list = userInfoService.selectByPage(dataReq);
		return setResultData(list);
	}
}

