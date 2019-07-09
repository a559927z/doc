package net.chinahrd.modules.demo.web;


import io.swagger.annotations.Api;
import net.chinahrd.common.AbstractBaseController;
import net.chinahrd.common.auth.InjectCurrentUser;
import net.chinahrd.modules.demo.service.EmpInfoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/demo/empInfo")
@Api(value="/demo/empInfo",description="")
public class EmpInfoController extends AbstractBaseController {
    @Reference
	private EmpInfoService empInfoService;
}

