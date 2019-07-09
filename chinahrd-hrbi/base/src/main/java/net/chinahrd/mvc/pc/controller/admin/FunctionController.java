package net.chinahrd.mvc.pc.controller.admin;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.entity.dto.pc.admin.FunctionDto;
import net.chinahrd.entity.dto.pc.admin.FunctionItemDto;
import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wqcai on 15/6/11.
 */
@Controller
@RequestMapping(value = "/function")
public class FunctionController extends BaseController {

    @Autowired
    private FunctionService functionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "biz/admin/function/function-list";
    }


    /**
     * 查询所有功能信息和操作信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findFunctionAndItem", method = RequestMethod.POST)
    public List<FunctionTreeDto> findFunctionAndItem() {
        List<String> strings = new ArrayList<>();
        List<FunctionTreeDto> treeDtos = functionService.findFunctionItemTree(strings);
        return treeDtos;
    }

    /**
     * 删除功能或功能操作
     *
     * @param id   主键ID
     * @param type 0：功能 1：功能操作
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delFunctionAndItem", method = RequestMethod.POST)
    public void deleteFunctionAndItem(@RequestParam("id") String id, Integer type) {
        functionService.deleteFunctionAndItem(getCustomerId(), id, type);
    }


    /**
     * 添加或修改功能信息
     *
     * @param functionDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editFunction", method = RequestMethod.POST)
    public ResultDto<Object> editFunction(FunctionDto functionDto) {
        ResultDto<Object> result = new ResultDto<Object>();
        if (null == functionDto) {
            return result;
        }
        //functionId为空则添加,不为空则修改
        String fullPath = "";
        if (StringUtils.isEmpty(functionDto.getFunctionId())) {
            String functionId = Identities.uuid2();
            functionDto.setCustomerId(getCustomerId());
            functionDto.setFunctionId(functionId);
            functionDto.setCreateUserId(getUserId());

            if (StringUtils.hasText(functionDto.getpKey())) {
                fullPath = functionDto.getpKey() + "_";
            }
            fullPath += functionDto.getFunctionKey();
            functionDto.setFullPath(fullPath);
            functionDto.setCreateTime(DateUtil.getTimestamp());
            functionService.addFunction(functionDto);
            result.setType(true);
            result.setT(functionId);
        } else {
            if (StringUtils.hasText(functionDto.getpKey())) {
                fullPath = functionDto.getpKey() + "_";
            }
            fullPath += functionDto.getFunctionKey();
            functionDto.setCustomerId(getCustomerId());
            functionDto.setFullPath(fullPath);
            functionService.updateFunction(functionDto);
            result.setType(true);
            result.setT(functionDto);
        }
        return result;
    }

    /**
     * 添加或修改功能操作信息
     *
     * @param itemDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editFunctionItem", method = RequestMethod.POST)
    public ResultDto<Object> editFunctionItem(FunctionItemDto itemDto) {
        ResultDto<Object> result = new ResultDto<Object>();
        if (null == itemDto) {
            result.setType(false);
            return result;
        }
        //functionItemId为空则添加,不为空则修改
        if (StringUtils.isEmpty(itemDto.getFunctionItemId())) {
            String itemId = Identities.uuid2();
            itemDto.setCustomerId(getCustomerId());
            itemDto.setFunctionItemId(itemId);
            itemDto.setCreateUserId(getUserId());
            itemDto.setCreateTime(DateUtil.getTimestamp());
            functionService.addFunctionItem(itemDto);
            result.setType(true);
            result.setT(itemId);
        } else {
            functionService.updateFunctionItem(itemDto);
            result.setType(true);
            result.setT(itemDto);
        }
        return result;
    }
}
