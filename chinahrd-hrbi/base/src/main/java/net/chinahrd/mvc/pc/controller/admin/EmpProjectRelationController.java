package net.chinahrd.mvc.pc.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.admin.PojoDto;
import net.chinahrd.entity.dto.pc.admin.ProjectRelationDto;
import net.chinahrd.entity.dto.pc.admin.ProjectTypeDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.EmpProjectRelationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;


/**
 * 配置页面使用-配置项目权限	
 * @author lihj
 *
 */
@Controller
@RequestMapping(value = "/empProjectRelation")
public class EmpProjectRelationController extends BaseController {
   @Autowired
    private EmpProjectRelationService empProjectRelationService;

    /**
     * 根据用户ID查询用户项目权限-user2project
     *
     * @param model
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTreeDataJson", method = RequestMethod.POST)
    public Object getTreeDataJson(Model model, @RequestParam(value = "empId") String empId) {
    	 Map<String,Object> mapObj=new HashMap<String, Object>();
        // 被操作角色对象已存的所有数据（包括：全勾、半勾）
        List<ProjectRelationDto> existOrgans = empProjectRelationService.queryEmpProject(empId,
                getCustomerId(), false);

        List<TreeDto> treeDtos = empProjectRelationService.dbToZtree(existOrgans);
        Object json = JSON.toJSON(treeDtos);
        List<ProjectTypeDto> queryProType=empProjectRelationService.queryProType();
        mapObj.put("jsonData", json);
        mapObj.put("proType", queryProType);
        return mapObj;
    }

    /**
     * 添加用户项目权限-user2project
     *
     * @param pojoDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addEmpProject", method = RequestMethod.POST)
    public ResultDto<String> addEmpOrganiation(
            @RequestBody(required = false) PojoDto pojoDto) {

        ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        result.setMsg("项目已存操作失败");

        if (null == pojoDto) {
            result.setMsg("对当前员工操作有误，请重新选择员工进行操作");
        }

        String empId = pojoDto.getEmpId();
        List<ProjectRelationDto> projectDtos = pojoDto.getProjectDto();

        if (projectDtos.size() == 0) {
            rs = empProjectRelationService.deleteEmpProject(empId, getCustomerId());
            result.setMsg("删除成功");
        } else {

            rs = empProjectRelationService.addEmpProject(empId, getUserId(), getCustomerId(), projectDtos);
            result.setMsg("添加成功");
        }
        result.setType(rs);
        return result;
    }
	

}
