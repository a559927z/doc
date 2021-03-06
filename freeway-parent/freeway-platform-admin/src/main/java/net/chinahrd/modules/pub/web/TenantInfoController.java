package net.chinahrd.modules.pub.web;


import io.swagger.annotations.ApiOperation;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.common.validate.MyValid;
import net.chinahrd.modules.pub.entity.DatasourceInfo;
import net.chinahrd.modules.pub.entity.TenantInfo;
import net.chinahrd.modules.pub.request.DatasourceQueryReq;
import net.chinahrd.modules.pub.request.TenantInfoReq;
import net.chinahrd.modules.pub.request.TenantQueryReq;
import net.chinahrd.modules.pub.response.TenantInfoRes;
import net.chinahrd.modules.pub.service.TenantInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import net.chinahrd.common.auth.InjectCurrentUser;
import org.springframework.web.bind.annotation.RestController;
import net.chinahrd.common.AbstractBaseController;

/**
 * <p>
 * 租户管理 前端控制器
 * </p>
 *
 * @author zoujiaxi
 * @since 2019-03-19
 */

@InjectCurrentUser
@RestController
@RequestMapping("/pub/tenant")
@Api(value="/pub/tenant",description="租户管理")
public class TenantInfoController extends AbstractBaseController {
    @Autowired
	private TenantInfoService tenantInfoService;

    @MyValid(TenantQueryReq.class)
    @ApiOperation(value = "租户列表分页查询", notes = "selectByPage", httpMethod = "POST")
    @PostMapping(value = "/selectByPage")
    public Object selectByPage(@RequestBody TenantQueryReq dataReq, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }

        if (dataReq.getCurrentUser() != null) {
            System.out.println("用户信息已经自动注入dataReq参数,userName:" + dataReq.getCurrentUser().getUserName());
        }

        PageData<TenantInfoRes> list = tenantInfoService.selectByPage(dataReq);
        return setResultData(list);
    }

    @MyValid(TenantQueryReq.class)
    @ApiOperation(value = "租户删除", notes = "deleteById", httpMethod = "POST")
    @PostMapping(value = "/deleteById")
    public void deleteById(@RequestBody TenantQueryReq dataReq, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }

        if (dataReq.getCurrentUser() != null) {
            System.out.println("用户信息已经自动注入dataReq参数,userName:" + dataReq.getCurrentUser().getUserName());
        }
        tenantInfoService.deleteById(dataReq.getTenantId());
    }

    @MyValid(TenantQueryReq.class)
    @ApiOperation(value = "租户新增", notes = "save", httpMethod = "POST")
    @PostMapping(value = "/save")
    public void save(@RequestBody TenantInfoReq entity, CurrentUser user) {
        if (user!=null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }
        tenantInfoService.add(entity,user);
    }

    @MyValid(TenantQueryReq.class)
    @ApiOperation(value = "租户修改", notes = "updateById", httpMethod = "POST")
    @PostMapping(value = "/updateById")
    public void updateById(@RequestBody TenantInfoReq entity, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }
        tenantInfoService.updateById(entity,user);
    }

    @MyValid(DatasourceQueryReq.class)
    @ApiOperation(value = "根据id查询", notes = "updateById", httpMethod = "POST")
    @PostMapping(value = "/selectById")
    public TenantInfoRes selectById(@RequestBody TenantInfoReq entity, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }
        return tenantInfoService.selectById(entity.getTenantId());
    }
}

