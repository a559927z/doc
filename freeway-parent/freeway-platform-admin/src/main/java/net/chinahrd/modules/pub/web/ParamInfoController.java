package net.chinahrd.modules.pub.web;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.pub.entity.ParamInfo;
import net.chinahrd.modules.pub.request.ParamInfoReq;
import net.chinahrd.modules.pub.request.ParamQueryReq;
import net.chinahrd.modules.pub.service.ParamInfoService;
import net.chinahrd.utils.PageUtil;
import net.chinahrd.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import net.chinahrd.common.auth.InjectCurrentUser;
import net.chinahrd.common.AbstractBaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cxhuo_gz
 * @since 2019-03-21
 */

@InjectCurrentUser
@RestController
@RequestMapping("/pub/paramInfo")
@Api(value="/pub/paramInfo",description="系统参数信息")
public class ParamInfoController extends AbstractBaseController {
    @Autowired
	private ParamInfoService paramInfoService;

    @ApiOperation(value = "增加参数数据", notes = "addParamInfo", httpMethod = "POST")
    @PostMapping(value = "/addParamInfo")
    public Object addParamInfo(@RequestBody ParamInfoReq paramInfoReq) {

        ParamInfo paramInfo = new ParamInfo();
        BeanUtils.copyProperties(paramInfoReq,paramInfo);
        String uuid = UuidUtil.uuid32();
        paramInfo.setParamId(uuid);
        paramInfo.setCreateInfo(paramInfoReq.getCurrentUser().getUserName());
        boolean b = paramInfoService.save(paramInfo);
        if (b){
            return setSuceessMsg("ok");
        }else {
            return setSuceessMsg("不ok，没成功");
        }
    }
    @ApiOperation(value = "编辑参数数据", notes = "editParamInfo", httpMethod = "POST")
    @PostMapping(value = "/editParamInfo")
    public Object editParamInfo(@RequestBody ParamInfoReq paramInfoReq) {

        ParamInfo paramInfo = new ParamInfo();
        BeanUtils.copyProperties(paramInfoReq,paramInfo);
        paramInfo.setUpdateInfo(paramInfoReq.getCurrentUser().getUserName());
        QueryWrapper<ParamInfo> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ParamInfo> lambda = queryWrapper.lambda();
        lambda.eq(ParamInfo::getParamId,paramInfo.getParamId());
        boolean b = paramInfoService.update(paramInfo,queryWrapper);
        if (b){
            return setSuceessMsg("ok");
        }else {
            return setSuceessMsg("不ok，没成功");
        }
    }
    @ApiOperation(value = "查询单条数据", notes = "getOneParamInfo", httpMethod = "GET")
    @PostMapping(value = "/getOneParamInfo")
    public ParamInfo getOneParamInfo( String paramInfoId) {
        QueryWrapper<ParamInfo> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ParamInfo> lambda = queryWrapper.lambda();
        lambda.eq(ParamInfo::getParamId,paramInfoId);
        return paramInfoService.getOne(queryWrapper);

    }
    @ApiOperation(value = "分页查询参数数据列表", notes = "getParamInfoList", httpMethod = "POST")
    @PostMapping(value = "/getParamInfoList")
    public PageData<ParamInfo> getParamInfoList(@RequestBody ParamQueryReq paramQueryReq) {
        PageHelper.startPage(paramQueryReq);
        ParamInfo paramInfo = new ParamInfo();
        BeanUtils.copyProperties(paramQueryReq,paramInfo);
        QueryWrapper<ParamInfo> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ParamInfo> lambda = queryWrapper.lambda();
        if (StringUtils.isNotBlank(paramInfo.getParamKey())){
            lambda.eq(ParamInfo::getParamKey,paramInfo.getParamKey());
        }
        if (StringUtils.isNotBlank(paramInfo.getParamName())){
            lambda.eq(ParamInfo::getParamName,paramInfo.getParamName());
        }
        if (StringUtils.isNotBlank(paramInfo.getParamValue())){
            lambda.eq(ParamInfo::getParamValue,paramInfo.getParamValue());
        }
        if (StringUtils.isNotBlank(paramInfo.getParamType())){
            lambda.eq(ParamInfo::getParamType,paramInfo.getParamType());
        }
        List<ParamInfo> list = paramInfoService.list(queryWrapper);
        return PageUtil.convert(list);
    }
    @ApiOperation(value = "删除参数信息", notes = "deleteParamInfo", httpMethod = "GET")
    @GetMapping(value = "/deleteParamInfo")
    public Object deleteParamInfo(String paramId) {
        if (StringUtils.isNotBlank(paramId)) {
            QueryWrapper<ParamInfo> queryWrapper = new QueryWrapper<>();
            LambdaQueryWrapper<ParamInfo> lambda = queryWrapper.lambda();
            lambda.eq(ParamInfo::getParamId,paramId);
            boolean result = paramInfoService.remove(queryWrapper);
            if (result){
                return setSuceessMsg("ok");
            }else {
                return setSuceessMsg("不ok，没成功");
            }
        }else {
            return setSuceessMsg("主键id不能为空！");
        }
    }
}

