package net.chinahrd.modules.pub.web;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.pub.entity.LogInfo;
import net.chinahrd.modules.pub.entity.ParamInfo;
import net.chinahrd.modules.pub.request.LogInfoQueryReq;
import net.chinahrd.modules.pub.request.ParamQueryReq;
import net.chinahrd.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import net.chinahrd.modules.pub.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import net.chinahrd.common.auth.InjectCurrentUser;
import org.springframework.web.bind.annotation.RestController;
import net.chinahrd.common.AbstractBaseController;

import java.util.List;

/**
 * <p>
 * 日志记录表  前端控制器
 * </p>
 *
 * @author bright
 * @since 2019-03-21
 */

@InjectCurrentUser
@RestController
@RequestMapping("/pub/logInfo")
@Api(value="/pub/logInfo",description="日志记录表 ")
public class LogInfoController extends AbstractBaseController {
    @Autowired
	private LogInfoService logInfoService;
    @ApiOperation(value = "分页查询参数数据列表", notes = "getParamInfoList", httpMethod = "POST")
    @PostMapping(value = "/getParamInfoList")
    public PageData<LogInfo> getParamInfoList(@RequestBody LogInfoQueryReq logInfoQueryReq) {
        PageHelper.startPage(logInfoQueryReq);
        LogInfo logInfo = new LogInfo();
        BeanUtils.copyProperties(logInfoQueryReq, logInfo);
        QueryWrapper<LogInfo> queryWrapper = new QueryWrapper<>();
        List<LogInfo> list = logInfoService.list(queryWrapper);
        return PageUtil.convert(list);
    }

}

