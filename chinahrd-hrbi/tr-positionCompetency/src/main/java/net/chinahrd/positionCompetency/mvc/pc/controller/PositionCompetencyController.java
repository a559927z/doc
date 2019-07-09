package net.chinahrd.positionCompetency.mvc.pc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.eis.search.task.IndexEmpTask;
import net.chinahrd.entity.dto.pc.common.ConfigDto;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.positionCompetency.EmpContrastDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.EmpDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionCompetencyDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.SequenceAndAblityDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.ConfigService;
import net.chinahrd.positionCompetency.mvc.pc.service.PositionCompetencyService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.PropertiesUtil;

/**
 * 岗位胜任度 PC
 * @author htpeng
 *2016年8月29日下午2:31:12
 */
@Controller
@RequestMapping("/positionCompetency")
public class PositionCompetencyController extends BaseController {

    @Autowired
    private PositionCompetencyService positionCompetencyService;
    @Autowired
    private ConfigService configService;

	
    @Autowired
   	private IndexEmpTask indexEmpTask;

    
    @RequestMapping(value = "/toPositionCompetencyView")
    public String toPositionCompetencyView(HttpServletRequest request, String organId) {
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("positionCompetency/toPositionCompetencyView");
        if(null !=functionId){request.setAttribute("quotaId", functionId); }
        
        Map<String,List<String>> map=positionCompetencyService.queryAllTime(getCustomerId());
        
        String times = CollectionKit.convertToString(map.get("key"), ",");
        List<String>list=DateUtil.getYearMonthTohHalfs(map.get("value"));
        String timeValues = CollectionKit.convertToString(list, ",");
        if(map.get("key").size()>0){
        	 String time=map.get("key").get(0);
        	 String timeValue=list.get(0);
        	 request.setAttribute("time",time);
             request.setAttribute("timeValue", timeValue);
        }
        
        request.setAttribute("times", times);
        request.setAttribute("timeValues", timeValues);
       
        return "biz/competency/positionCompetency";
    }
    
   
    /**
	  * 岗位 selecet2
	  * @param keyName
	  * @param page
	  * @param rows
	  * @return
	  */
    @ResponseBody
 	@RequestMapping(value = "/queryPositionByName", method = RequestMethod.POST)
 	public PaginationDto<KVItemDto> queryPositionByName(String keyName,Integer page, Integer rows) {
 		PaginationDto<KVItemDto> dto = new PaginationDto<KVItemDto>(page, rows);
 		return positionCompetencyService.queryPositionByName(getCustomerId(), keyName,dto);
 	}
   
    /**
     * 获取所有下级机构
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/querySubOrgan", method = RequestMethod.POST)
    public List<PositionCompetencyDto> querySubOrgan( String organId) {
    	return positionCompetencyService.querySubOrgan(getCustomerId(),organId);
        
    }
    /**
     * 获取团队平均胜任度
     * @param times  时间
     * @param organ  机构
     * @param position 岗位
     * @param next   是否查询下级岗位
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamAvgCompetency", method = RequestMethod.POST)
    public List<PositionCompetencyDto> getTeamAvgCompetency(String yearMonth, String organId,String positionId, boolean next) {
    	return positionCompetencyService.getTeamAvgCompetency(getCustomerId(),yearMonth,organId,positionId,next);
        
    }
    
    /**
     * 获取岗位最高最低胜任度

     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionMaxMinCompetency", method = RequestMethod.POST)
    public Map<String,PositionCompetencyDto> getPositionMaxMinCompetency(String yearMonth, String organId) {
       
    
        return 	positionCompetencyService.queryPositionCompetency(getCustomerId(),organId,yearMonth);
    }
    
    
    
    
    
    /**
     * 序列团队平均胜任度对比分析

     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSequenceCompetency", method = RequestMethod.POST)
    public List<SequenceAndAblityDto> getSequenceCompetency(String yearMonth, String organId,String sequenceId) {
       
    
        return 	positionCompetencyService.querySequenceCompetency(getCustomerId(),organId,yearMonth,sequenceId);
    }

    /**
     * 职级团队平均胜任度对比分析

     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAbilityCompetency", method = RequestMethod.POST)
    public List<SequenceAndAblityDto> getAbilityCompetency(String yearMonth, String organId,String sequenceId) {
       
    
        return 	positionCompetencyService.queryAbilityCompetency(getCustomerId(),organId,yearMonth,sequenceId);
    }
   
    /**
	 * 岗位面板
	 */
    @ResponseBody
    @RequestMapping(value = "/queryPositionTable", method = RequestMethod.POST)
	public PaginationDto<PositionDetailDto> queryPositionTable(String customerId, String organId,String keyName,
			String yearMonth,Integer page, Integer rows) {
 		PaginationDto<PositionDetailDto> dto = new PaginationDto<PositionDetailDto>(page, rows);
		  return 	positionCompetencyService.queryPositionTable(getCustomerId(),organId,yearMonth,keyName,dto);
	}
    /**
  	 * 岗位面板下的人员列表表格
  	 */
      @ResponseBody
      @RequestMapping(value = "/queryPositionEmpTable", method = RequestMethod.POST)
  	public PaginationDto<EmpDetailDto> queryPositionEmpTable(String customerId, String organId,String keyName,String positionId,
  			String yearMonth,Integer page, Integer rows) {
   		PaginationDto<EmpDetailDto> dto = new PaginationDto<EmpDetailDto>(page, rows);
  		  return 	positionCompetencyService.queryPositionEmp(getCustomerId(),organId,yearMonth,positionId,keyName,dto);
  	}

    /**
     * 人员面板

     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryEmpByName", method = RequestMethod.POST)
    public PaginationDto<EmpDetailDto> queryEmpByName(String organId,String yearMonth,
    		String keyName,Double start,Double end,Integer page, Integer rows) {
 		PaginationDto<EmpDetailDto> dto = new PaginationDto<EmpDetailDto>(page, rows);
        return 	positionCompetencyService.queryEmpByName(getCustomerId(),organId,yearMonth,keyName,start,end,dto);
    
    }
    
    /**
     * 获取岗位高绩效画像标签
     *
     * @param position    岗位
     * @param yearNum     年度
     * @param continueNum 次数
     * @param star        星级
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionImages", method = RequestMethod.POST)
    public Map<String,Object> getPositionImages(String positionId, Integer yearNum, Integer continueNum, Integer star,boolean updatePositon) {
        if (StringUtils.isEmpty(positionId)) {
            return null;
        }
        return positionCompetencyService.queryPositionImages(positionId, yearNum, continueNum, star, getCustomerId(),updatePositon);
    }
    
    
    /**
     * 获取岗位高绩效画像标签
     *
     * @param position    岗位
     * @param yearNum     年度
     * @param continueNum 次数
     * @param star        星级
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpContrastInfo", method = RequestMethod.POST)
    public EmpContrastDetailDto getEmpContrastInfo(String empId,String yearMonth) {
        if (StringUtils.isEmpty(empId)) {
            return null;
        }
        return positionCompetencyService.getEmpContrastInfo( getCustomerId(),empId,yearMonth);
    }
    
    
    /***
     * 更新员工绩效基础配置信息
     *
     * @param heightPer   高绩效
     * @param lowPer      低绩效
     * @param perfmanWeek 绩效周期
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBaseConfig", method = RequestMethod.POST)
    public ResultDto<String> updateBaseConfig(String low, String high) {
        ResultDto<String> rsDto = new ResultDto<String>();
        List<ConfigDto> list = CollectionKit.newList();
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.GWSRD_LOW), low));
//        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.GWSRD_MIDDLE), middle));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.GWSRD_HIGH), high));
        configService.updateSysConfig(getCustomerId(), list);
        rsDto.setType(true);
        return rsDto;
    }

    /**
     * 获取员工绩效基础配置信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBaseConfig", method = RequestMethod.POST)
    public Map<String, Object> getBaseConfig() {
        Map<String, Object> maps = CollectionKit.newMap();
        Double low = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.GWSRD_LOW);
//        List<Double> middle = CacheHelper.getConfigValsByCacheDouble(ConfigKeyUtil.GWSRD_MIDDLE);
        Double high= CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.GWSRD_HIGH);
        maps.put("low", low);
//        maps.put("middle", middle);
        maps.put("high",  high);
        return maps;
    }

    
    /**
     * 关键词搜索
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/indexEmpTask", method = RequestMethod.POST)
    public boolean indexEmpTask() {
		indexEmpTask.call();
    	return true;
    }
    /**
     * 关键词搜索
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getKeyWordInfo", method = RequestMethod.POST)
    public Map<String,Object>  getKeyWordInfo(String empId,String keyword) {
    	
    	
		
		return positionCompetencyService.getKeyWordInfo(getCustomerId(),empId,keyword);
//
//		
//        return list;
    }
    

	
}