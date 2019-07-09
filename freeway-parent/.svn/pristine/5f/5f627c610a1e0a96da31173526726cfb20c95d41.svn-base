package net.chinahrd.modules.pub.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.chinahrd.common.AbstractBaseController;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.common.auth.InjectCurrentUser;
import net.chinahrd.common.validate.MyValid;
import net.chinahrd.modules.pub.entity.DatasourceInfo;
import net.chinahrd.modules.pub.request.DatasourceQueryReq;
import net.chinahrd.modules.pub.response.DatasourceInfoRes;
import net.chinahrd.modules.pub.service.DatasourceInfoService;
import net.chinahrd.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 数据源管理 前端控制器
 * </p>
 *
 * @author zoujiaxi
 * @since 2019-03-19
 */

@InjectCurrentUser
@RestController
@RequestMapping("/pub/datasource")
@Api(value="/pub/datasource",description="数据源管理")
public class DatasourceInfoController extends AbstractBaseController {
    @Autowired
	private DatasourceInfoService datasourceInfoService;

    @MyValid(DatasourceQueryReq.class)
    @ApiOperation(value = "数据源列表分页查询", notes = "selectByPage", httpMethod = "POST")
    @PostMapping(value = "/selectByPage")
    public Object selectByPage(@RequestBody DatasourceQueryReq dataReq, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }

        if (dataReq.getCurrentUser() != null) {
            System.out.println("用户信息已经自动注入dataReq参数,userName:" + dataReq.getCurrentUser().getUserName());
        }

        PageData<DatasourceInfoRes> list = datasourceInfoService.selectByPage(dataReq);
        return setResultData(list);
    }

    @MyValid(DatasourceQueryReq.class)
    @ApiOperation(value = "数据源删除", notes = "deleteById", httpMethod = "POST")
    @PostMapping(value = "/deleteById")
    public void deleteById(@RequestBody DatasourceQueryReq dataReq, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }

        if (dataReq.getCurrentUser() != null) {
            System.out.println("用户信息已经自动注入dataReq参数,userName:" + dataReq.getCurrentUser().getUserName());
        }
        datasourceInfoService.deleteById(dataReq);
    }

    @MyValid(DatasourceQueryReq.class)
    @ApiOperation(value = "数据源新增", notes = "save", httpMethod = "POST")
    @PostMapping(value = "/save")
    public void save(@RequestBody DatasourceInfo entity, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }
//        String datasourceId = UuidUtil.uuid32();
//        entity.setDatasourceId(datasourceId);
//        entity.setCreateBy(user.getUserName());
//        entity.setCreateDate(new Date());
//        entity.setUpdateBy(user.getUserName());
//        entity.setUpdateDate(new Date());
//        datasourceInfoService.save(entity);
        datasourceInfoService.insert(entity,user);
    }

    @MyValid(DatasourceQueryReq.class)
    @ApiOperation(value = "数据源修改", notes = "updateById", httpMethod = "POST")
    @PostMapping(value = "/updateById")
    public void updateById(@RequestBody DatasourceInfo entity, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }
        datasourceInfoService.updateById(entity,user);
    }

    @MyValid(DatasourceQueryReq.class)
    @ApiOperation(value = "根据id查询", notes = "updateById", httpMethod = "POST")
    @PostMapping(value = "/selectById")
    public DatasourceInfo selectById(@RequestBody DatasourceInfo entity, CurrentUser user) {
        if (user != null && StringUtils.isNotBlank(user.getUserName())) {
            System.out.println("用户信息已经自动注入user参数,userName:" + user.getUserName());
        }
        return datasourceInfoService.selectById(entity.getDatasourceId());
    }
}

