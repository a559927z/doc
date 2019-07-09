package net.chinahrd.keyTalent.mvc.pc.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.common.EncourageDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentLogDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentPanelDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentTagDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentsCardDto;
import net.chinahrd.keyTalent.mvc.pc.service.KeyTalentsService;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 关键人才库Controller
 * Created by wqcai on 15/09/28 0028.
 */
@Controller
@RequestMapping("/keyTalents")
public class KeyTalentsController  extends BaseController {
	@Autowired
    private KeyTalentsService keyTalentsService;
	
	
    @RequestMapping(value = "/toKeyTalentsView")
    public String toKeyTalentsView(){
        return "biz/employee/keyTalents";
    }
    
    @RequestMapping(value = "/toKeyTalentsEvaluateView")
    public String toKeyTalentsEvaluateView(HttpServletRequest request,String keyTalentId){
    	request.setAttribute("keyTalentId", keyTalentId);
    	request.setAttribute("customerId", getUserEmpId());
        return "biz/employee/keyTalentsEvaluate";
    }
    
    private String getOrganId(){
    	List<OrganDto> organPermit = getOrganPermit();
    	if (CollectionKit.isEmpty(organPermit)) {
            
           return null;
        }
    	OrganDto topOneOrgan = organPermit.get(0);
    	String organizationId=topOneOrgan.getOrganizationId();
    	return organizationId;
    }
    
	/**
	 * 离职风险预警 查询
	 */
    @ResponseBody
    @RequestMapping(value = "/getRunoffRiskWarnEmp", method = RequestMethod.POST)
    public List<KeyTalentsCardDto> getRunoffRiskWarnEmp() {
    
        return keyTalentsService.queryRunoffRiskWarnEmp(getCustomerId(), getUserId(),getUserEmpId());
    }
    

    /**
	 * 我关注的人员查询
	 */
    @ResponseBody
    @RequestMapping(value = "/getFocuseEmp", method = RequestMethod.POST)
	public List<KeyTalentsCardDto> getFocuseEmp() {
    	return keyTalentsService.queryFocuseEmp(getCustomerId(), getUserId(),getUserEmpId());
	}

    /**
	 * 最近更新人员 查询
	 */
    @ResponseBody
    @RequestMapping(value = "/getLastRefreshEmp", method = RequestMethod.POST)
	public Map<String, Object> getLastRefreshEmp(String order,Integer page, Integer rows) {
    	
    	PaginationDto<KeyTalentsCardDto> pagedto = new PaginationDto<KeyTalentsCardDto>(page==null?1:page, rows==null?30:rows);
		return keyTalentsService.queryLastRefreshEmp(getCustomerId(), getUserId(),getUserEmpId(), pagedto);
	}

    /**
	 * 添加关注
	 */
    @ResponseBody
    @RequestMapping(value = "/addFocuseEmp", method = RequestMethod.POST)
	public ResultDto<String> addFocuseEmp(String keyTalentId) {
		// TODO Auto-generated method stub
    	  ResultDto<String> result = new ResultDto<>();
          boolean rs = false;
          if (null == keyTalentId) {
              result.setMsg("操作有误！");
          }else{
        	  rs=keyTalentsService.addFocuseEmp(getCustomerId(),getUserEmpId(),keyTalentId);
        	  result.setType(rs);
        	  if(rs){
        		  result.setMsg("添加关注成功！");
        	  }else{
        		  result.setMsg("添加关注失败！"); 
        	  }
          }
          return result;

	}

    /**
   	 * 取消关注
   	 */
   @ResponseBody
   @RequestMapping(value = "/removeFocuseEmp", method = RequestMethod.POST)
	public ResultDto<String>  removeFocuseEmp( String keyTalentId) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == keyTalentId) {
            result.setMsg("操作有误！");
        }else{
      	  rs=keyTalentsService.removeFocuseEmp(getCustomerId(),getUserEmpId(),keyTalentId);
      	  result.setType(rs);
      	  if(rs){
      		  result.setMsg("取消关注成功！");
      	  }else{
      		  result.setMsg("取消关注失败！"); 
      	  }
        }
        return result;
	}

   /**
    * 删除关键人才
    */
  @ResponseBody
  @RequestMapping(value = "/deleteKeyTalent", method = RequestMethod.POST)
	public ResultDto<String> deleteKeyTalent( String keyTalentId) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == keyTalentId) {
            result.setMsg("操作有误！");
        }else{
        		rs=keyTalentsService.deleteKeyTalent(getCustomerId(),getUserEmpId(),keyTalentId);
      	  result.setType(rs);
      	  if(rs){
      		  result.setMsg("删除关键人才成功！");
      	  }else{
      		  result.setMsg("删除关键人才失败！"); 
      	  }
        }
        return result;
	}

	  /**
	   * 添加关键人才
	   */
	 @ResponseBody
	 @RequestMapping(value = "/addKeyTalent", method = RequestMethod.POST)
	public ResultDto<String> addKeyTalent(String empId,String keyTalentTypeId) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == empId) {
            result.setMsg("操作有误！");
        }else{
      	  rs=keyTalentsService.addKeyTalent(getCustomerId(),getUserEmpId(),empId,keyTalentTypeId);
      	  result.setType(rs);
      	  if(rs){
      		  result.setMsg("添加关键人才成功！");
      	  }else{
      		  result.setMsg("添加关键人才失败！"); 
      	  }
        }
        return result;
	}


	/**
	 * 标签查询 查询
	 */
	 @ResponseBody
	 @RequestMapping(value = "/queryTag", method = RequestMethod.POST)
	public List<KeyTalentTagDto> queryTag(String keyTalentId) {
		// TODO Auto-generated method stub
		return keyTalentsService.queryTag(getCustomerId(),keyTalentId);
	}

	 /**
		 * 历史标签 查询
		 */
	 @ResponseBody
	 @RequestMapping(value = "/getHistoryTag", method = RequestMethod.POST)
	public List<KeyTalentTagDto> queryHistoryTag(String keyTalentId) {
		 return keyTalentsService.queryHistoryTag(getCustomerId(),keyTalentId);
	}

	 /**
		 * 添加手动标签
		 */
	 @ResponseBody
	 @RequestMapping(value = "/addKeyTalentTag", method = RequestMethod.POST)
	public ResultDto<String> addKeyTalentTag(String keyTalentId,String tags ,String type) {
		 ResultDto<String> result = new ResultDto<>();
	        boolean rs = false;
	        if (null == type) {
	            result.setMsg("操作有误！");
	        }else{
	      	  rs=keyTalentsService.addKeyTalentTag(getCustomerId(),keyTalentId,getUserEmpId(),getUserEmpName(),tags,type);
	      	  result.setType(rs);
	      	  if(rs){
	      		  result.setMsg("添加标签成功！");
	      	  }else{
	      		  result.setMsg("添加标签失败！"); 
	      	  }
	        }
	        return result;
	}



	 /**
		 * 删除手动标签
		 */
	 @ResponseBody
	 @RequestMapping(value = "/deleteKeyTalentTag", method = RequestMethod.POST)
	public  ResultDto<String>  deleteKeyTalentTag(String tagId,String type) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == tagId) {
            result.setMsg("操作有误！");
        }else{
      	  rs=keyTalentsService.deleteKeyTalentTag(getCustomerId(),getUserEmpName(),tagId,type);
      	  result.setType(rs);
      	  if(rs){
      		  result.setMsg("删除标签成功！");
      	  }else{
      		  result.setMsg("删除标签失败！"); 
      	  }
        }
        return result;
	}

	 /**
		 * 根据关键人才Id
		 */
	 @ResponseBody
	 @RequestMapping(value = "/getKeyTalentById", method = RequestMethod.POST)
	public KeyTalentDto queryKeyTalentById( String keyTalentId) {
		// TODO Auto-generated method stub
		return keyTalentsService.queryKeyTalentById(getCustomerId(),keyTalentId);
	}
	
	 /**
	 * 关键人才核心激励要素查询
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value = "/getKeyTalentEncourage", method = RequestMethod.POST)
	public Map<String,Object> queryKeyTalentEncourage(String keyTalentId) {
		// TODO Auto-generated method stub
		return keyTalentsService.queryKeyTalentEncourage(getCustomerId(),keyTalentId);
	}

	 /**
		 * 核心激励要素查询
		 * @return
		 */
	@ResponseBody
	@RequestMapping(value = "/getEncourage", method = RequestMethod.POST)
	public List<EncourageDto> queryEncourage(String keyTalentId) {
		// TODO Auto-generated method stub
		return keyTalentsService.queryEncourage(getCustomerId(),keyTalentId);
	}


	 /**
	 * 核心激励要素修改
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateKeyTalentEncourage", method = RequestMethod.POST)
	public ResultDto<String> updateKeyTalentEncourage(String encourages,String keyTalentId,String note) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == keyTalentId) {
            result.setMsg("操作有误！");
        }else{
      	  rs=keyTalentsService.updateKeyTalentEncourage(getCustomerId(),getUserEmpId(), encourages,keyTalentId,note);
      	  result.setType(rs);
      	  if(rs){
      		  result.setMsg("修改核心激励要素成功！");
      	  }else{
      		  result.setMsg("修改核心激励要素失败！"); 
      	  }
        }
        return result;
	}

	/**
	 * 添加 跟踪日志
	 * @param customerId
	 * @param keyTalentId
	 */
	@ResponseBody
	@RequestMapping(value = "/addKeyTalentLog", method = RequestMethod.POST)
	public ResultDto<String> addKeyTalentLog(String keyTalentId,String content) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == keyTalentId) {
            result.setMsg("操作有误！");
        }else{
      	  rs=keyTalentsService.addKeyTalentLog(getCustomerId(),getUserEmpId(),keyTalentId,content);
      	  result.setType(rs);
      	  if(rs){
      		  result.setMsg("添加跟踪日志成功！");
      	  }else{
      		  result.setMsg("添加跟踪日志失败！"); 
      	  }
        }
        return result;
	}

	/**
	 * 查询跟踪日志
	 * @param keyTalentId
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentLog", method = RequestMethod.POST)
	public List<KeyTalentLogDto> queryKeyTalentLog(String keyTalentId) {
		// TODO Auto-generated method stub
		return keyTalentsService.queryKeyTalentLog(getCustomerId(),keyTalentId);
	}

	/**
	 * 修改跟踪日志
	 * @param keyTalentId
	 */
	@ResponseBody
	@RequestMapping(value = "/updateKeyTalentLog", method = RequestMethod.POST)
	public ResultDto<String> updateKeyTalentLog(String keyTalentId, String keyTalentLogId,
			String content) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == keyTalentLogId) {
            result.setMsg("操作有误！");
        }else{
      	  rs=keyTalentsService.updateKeyTalentLog(getCustomerId(),keyTalentId,getUserEmpId(),keyTalentLogId,content);
      	  result.setType(rs);
      	  if(rs){
      		  result.setMsg("修改跟踪日志成功！");
      	  }else{
      		  result.setMsg("修改跟踪日志失败！"); 
      	  }
        }
        return result;
	}

	/**
	 * 删除跟踪日志
	 * @param keyTalentId
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteKeyTalentLog", method = RequestMethod.POST)
	public ResultDto<String> deleteKeyTalentLog(String keyTalentId,String keyTalentLogId) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == keyTalentLogId) {
            result.setMsg("操作有误！");
        }else{
      	  rs=keyTalentsService.deleteKeyTalentLog(getCustomerId(), keyTalentId,getUserEmpId(),keyTalentLogId);
      	  result.setType(rs);
      	  if(rs){
      		  result.setMsg("删除跟踪日志成功！");
      	  }else{
      		  result.setMsg("删除跟踪日志失败！"); 
      	  }
        }
        return result;
	}

	/**
	 * 	左侧面板查询     各个类型的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentTypePanel", method = RequestMethod.POST)
	public List<KeyTalentPanelDto> queryKeyTalentTypePanel( String organizationId) {
		// TODO Auto-generated method stub
		return keyTalentsService.queryKeyTalentTypePanel(getCustomerId(), getUserId());
	}

	/**
	 * 	根据类型查询关键人才
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentByType", method = RequestMethod.POST)
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByType(String organizationId, String keyTalentTypeId
			, String order,Integer page, Integer rows) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(page, rows);
		return keyTalentsService.queryKeyTalentByType(getCustomerId(), getUserId(),keyTalentTypeId,getUserEmpId(), order,dto);
	}


	/**
	 * 	 左侧面板查询       各个激励要素的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentEncouragePanel", method = RequestMethod.POST)
	public List<KeyTalentPanelDto> queryKeyTalentEncouragePanel( String organizationId) {
		return keyTalentsService.queryKeyTalentEncouragePanel(getCustomerId(), getUserId());
	}

	/**
	 * 	根据激励要素查询关键人才 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentByEncourage", method = RequestMethod.POST)
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByEncourage(String organizationId,
			String  encourageId, String order,Integer page, Integer rows) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(page, rows);
		return keyTalentsService.queryKeyTalentByEncourage(getCustomerId(), getUserId(), encourageId,getUserEmpId(),order,dto);
	}

	/**
	 * 	左侧面板查询   各个部门的关键人才数量 （含有下级）
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentOrganPanel", method = RequestMethod.POST)
	public List<KeyTalentPanelDto> queryKeyTalentOrganPanel(String organizationId) {
		return keyTalentsService.queryKeyTalentOrganPanel(getCustomerId(), getUserId());
	}

	/**
	 * 		根据部门查询关键人才 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentByOrgan", method = RequestMethod.POST)
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByOrgan(String organizationId, String order,Integer page, Integer rows) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(page, rows);
		return keyTalentsService.queryKeyTalentByOrgan(getCustomerId(), organizationId,getUserEmpId(),order,dto);
	}
	 /**
	 * 关键人才类型列表查询
	 */
	 @ResponseBody
	 @RequestMapping(value = "/getKeyTalentType", method = RequestMethod.POST)
	public List<KVItemDto> queryKeyTalentType( ) {
		// TODO Auto-generated method stub
		return keyTalentsService.queryKeyTalentType(getCustomerId());
	}
	 
	 /**
	  * 非关键人才列表
	  * @param keyName
	  * @param page
	  * @param rows
	  * @return
	  */
    @ResponseBody
	@RequestMapping(value = "/getNotKeyTalentByName", method = RequestMethod.POST)
	public PaginationDto<EmpDetailDto> queryNotKeyTalentByName(String keyName,Integer page, Integer rows) {
		PaginationDto<EmpDetailDto> dto = new PaginationDto<EmpDetailDto>(page, rows);
		return keyTalentsService.queryNotKeyTalentByName(getCustomerId(), getUserId(),keyName,dto);
	}

	/**
	 * 跟级姓名 id模糊查询关键人才列表
	 */

    @ResponseBody
	@RequestMapping(value = "/getKeyTalentByName", method = RequestMethod.POST)
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByName(String keyName,Integer page, Integer rows, String order) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(page, rows);
		return keyTalentsService.queryKeyTalentByName(getCustomerId(), getUserId(),keyName,getUserEmpId(),order,dto);
	}

}
