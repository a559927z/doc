package net.chinahrd.modules.demo.web;


import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import net.chinahrd.modules.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import net.chinahrd.common.auth.InjectCurrentUser;
import org.springframework.web.bind.annotation.RestController;
import net.chinahrd.common.AbstractBaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bright
 * @since 2019-03-20
 */

@InjectCurrentUser
@RestController
@RequestMapping("/demo/userInfo")
@Api(value="/demo/userInfo",description="")
public class UserInfoController extends AbstractBaseController {
    @Autowired
	private UserInfoService userInfoService;
}

