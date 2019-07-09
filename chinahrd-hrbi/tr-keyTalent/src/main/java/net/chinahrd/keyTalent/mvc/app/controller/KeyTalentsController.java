package net.chinahrd.keyTalent.mvc.app.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.chinahrd.api.DismissRiskApi;
import net.chinahrd.core.api.ApiManagerCenter;
import net.chinahrd.entity.dto.pc.emp.KeyTalentDto;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.common.EncourageDto;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.common.RiskTreeDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentLogDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentPanelDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentTagDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentsCardDto;
import net.chinahrd.keyTalent.mvc.pc.service.KeyTalentsService;
import net.chinahrd.mvc.app.AppUserMapping;
import net.chinahrd.mvc.app.DataPacket;
import net.chinahrd.mvc.app.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 关键人才库Controller
 * Created by wqcai on 15/09/28 0028.
 */
@Controller("AppKeyTalentsController")
@RequestMapping("/mobile/keyTalentDetail")
public class KeyTalentsController  extends BaseController {
	@Autowired
    private KeyTalentsService keyTalentsService;
	
   /**
     * 查询离职风险预测回归
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpRiskDetail")
    public DataPacket  getEmpRiskDetail(String empId,String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			DismissRiskApi api = ApiManagerCenter.getApiDoc(DismissRiskApi.class);
			List<RiskTreeDto> list =  api.getEmpRiskDetail(getCustomerId(),empId);
 			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(list);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
        
	}
    

    /**
	 * 我关注的人员查询
	 */
    @ResponseBody
    @RequestMapping(value = "/getFocuseEmp", method = RequestMethod.GET)
	public DataPacket getFocuseEmp(String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<KeyTalentsCardDto> list =  keyTalentsService.queryFocuseEmp(getCustomerId(), getUserId(),getUserEmpId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(list);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
    	
	}

    /**
	 * 最近更新人员 查询
	 */
    @ResponseBody
    @RequestMapping(value = "/getLastRefreshEmp", method = RequestMethod.GET)
	public DataPacket getLastRefreshEmp(String token, String order,Integer page, Integer rows) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(page, rows);
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			Map<String, Object>  map=  keyTalentsService.queryLastRefreshEmp(getCustomerId(), getUserId(),getUserEmpId(), dto);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(map == null ? (new ArrayList<KeyTalentsCardDto>()): map.get("list"));
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
		
	}

    /**
	 * 添加关注
	 */
    @ResponseBody
    @RequestMapping(value = "/addFocuseEmp", method = RequestMethod.GET)
	public ResultDto<String> addFocuseEmp(String keyTalentId,String token) {
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
   @RequestMapping(value = "/removeFocuseEmp", method = RequestMethod.GET)
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
  @RequestMapping(value = "/deleteKeyTalent", method = RequestMethod.GET)
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
	 @RequestMapping(value = "/addKeyTalent", method = RequestMethod.GET)
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
	 @RequestMapping(value = "/queryTag", method = RequestMethod.GET)
	public DataPacket queryTag(String keyTalentId,String token) { 
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<KeyTalentTagDto> list =  keyTalentsService.queryTag(getCustomerId(),keyTalentId);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(list);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
		
	}

	 /**
		 * 历史标签 查询
		 */
	 @ResponseBody
	 @RequestMapping(value = "/getHistoryTag", method = RequestMethod.GET)
	public DataPacket queryHistoryTag(String keyTalentId,String token) { 
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<KeyTalentTagDto>  list =  keyTalentsService.queryHistoryTag(getCustomerId(),keyTalentId);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(list);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
	}

	 /**
		 * 添加手动标签
		 */
	 @ResponseBody
	 @RequestMapping(value = "/addKeyTalentTag", method = RequestMethod.GET)
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
	 @RequestMapping(value = "/deleteKeyTalentTag", method = RequestMethod.GET)
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
	 @RequestMapping(value = "/getKeyTalentById", method = RequestMethod.GET)
	public KeyTalentDto queryKeyTalentById( String keyTalentId) {
		return keyTalentsService.queryKeyTalentById(getCustomerId(),keyTalentId);
	}
	
	 /**
	 * 关键人才核心激励要素查询
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value = "/getKeyTalentEncourage", method = RequestMethod.GET)
	public DataPacket queryKeyTalentEncourage(String keyTalentId,String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			Map<String,Object> map =  keyTalentsService.queryKeyTalentEncourage(getCustomerId(),keyTalentId);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(map);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
		
	}

	 /**
		 * 核心激励要素查询
		 * @return
		 */
	@ResponseBody
	@RequestMapping(value = "/getEncourage", method = RequestMethod.GET)
	public DataPacket queryEncourage(String keyTalentId,String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<EncourageDto> list =  keyTalentsService.queryEncourage(getCustomerId(),keyTalentId);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(list);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
		
	}


	 /**
	 * 核心激励要素修改
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateKeyTalentEncourage", method = RequestMethod.GET)
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
	@RequestMapping(value = "/addKeyTalentLog", method = RequestMethod.GET)
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
	@RequestMapping(value = "/getKeyTalentLog", method = RequestMethod.GET)
	public  DataPacket  queryKeyTalentLog(String keyTalentId,String token) {  
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<KeyTalentLogDto> list =  keyTalentsService.queryKeyTalentLog(getCustomerId(),keyTalentId);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(list);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
	}

	/**
	 * 修改跟踪日志
	 * @param keyTalentId
	 */
	@ResponseBody
	@RequestMapping(value = "/updateKeyTalentLog", method = RequestMethod.GET)
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
	@RequestMapping(value = "/deleteKeyTalentLog", method = RequestMethod.GET)
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
	@RequestMapping(value = "/getKeyTalentTypePanel", method = RequestMethod.GET)
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
	@RequestMapping(value = "/getKeyTalentByType", method = RequestMethod.GET)
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByType(String organizationId, String keyTalentTypeId,String keyName
			, String order,Integer page, Integer rows) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(page, rows);
		return keyTalentsService.queryKeyTalentByType(getCustomerId(), getUserId(),keyTalentTypeId,getUserEmpId(),order,dto);
	}


	/**
	 * 	 左侧面板查询       各个激励要素的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKeyTalentEncouragePanel", method = RequestMethod.GET)
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
	@RequestMapping(value = "/getKeyTalentByEncourage", method = RequestMethod.GET)
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
	@RequestMapping(value = "/getKeyTalentOrganPanel", method = RequestMethod.GET)
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
	@RequestMapping(value = "/getKeyTalentByOrgan", method = RequestMethod.GET)
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByOrgan(String organizationId, String order,Integer page, Integer rows) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(page, rows);
		return keyTalentsService.queryKeyTalentByOrgan(getCustomerId(), organizationId,getUserEmpId(),order,dto);
	}
	 /**
	 * 关键人才类型列表查询
	 */
	 @ResponseBody
	 @RequestMapping(value = "/getKeyTalentType", method = RequestMethod.GET)
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
	@RequestMapping(value = "/getNotKeyTalentByName", method = RequestMethod.GET)
	public PaginationDto<EmpDetailDto> queryNotKeyTalentByName(String keyName,Integer page, Integer rows) {
		PaginationDto<EmpDetailDto> dto = new PaginationDto<EmpDetailDto>(page, rows);
		return keyTalentsService.queryNotKeyTalentByName(getCustomerId(), getUserId(),keyName,dto);
	}

	/**
	 * 跟级姓名 id模糊查询关键人才列表
	 */

    @ResponseBody
	@RequestMapping(value = "/getKeyTalentByName", method = RequestMethod.GET)
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByName(String keyName,Integer page, Integer rows, String order) {
		PaginationDto<KeyTalentsCardDto> dto = new PaginationDto<KeyTalentsCardDto>(page, rows);
		return keyTalentsService.queryKeyTalentByName(getCustomerId(), getUserId(),keyName,getUserEmpId(),order,dto);
	}

}
