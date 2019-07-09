package net.chinahrd.mvc.pc.service.admin.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.chinahrd.core.web.eis.license.License;
import net.chinahrd.core.web.eis.license.LicenseConfig;
import net.chinahrd.core.web.eis.license.LicenseData;
import net.chinahrd.entity.dto.pc.admin.FunctionDto;
import net.chinahrd.entity.dto.pc.admin.FunctionItemDto;
import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.entity.dto.pc.admin.RoleFunctionDto;
import net.chinahrd.entity.dto.pc.manage.HomeConfigDto;
import net.chinahrd.entity.enums.HomeConfigEnum;
import net.chinahrd.mvc.pc.dao.admin.FunctionDao;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.CompareUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

/**
 * 功能Service实现类
 * Created by wqcai on 15/6/12.
 */
@Transactional
@Service("functionService")
public class FunctionServiceImpl implements FunctionService {

	private static Logger log = LoggerFactory.getLogger(FunctionServiceImpl.class);

	private final String HOME_FUNCTION_CODE = "b5c9410dc7e4422c8e0189c7f8056b5f";

    @Autowired
    private FunctionDao functionDao;

    @Override
    public List<FunctionTreeDto> findFunctionItemTree(List<String> customerIds) {
        return functionDao.findFunctionItemTree(customerIds);
    }

    @Override
    public FunctionTreeDto findFunctionObj(String customerId, String id, String url) {
        return functionDao.findFunctionObj(customerId, id, url);
    }

    @Override
    public void deleteFunctionAndItem(String customerId, String id, Integer type) {
        if (type != null && type.intValue() != 1) {
            functionDao.deleteFunction(customerId, id);
            functionDao.deleteFunctionItem(customerId, null, id);
        } else {
            functionDao.deleteFunctionItem(customerId, id, null);
        }
    }

    @Override
    public void addFunction(FunctionDto functionDto) {
        functionDao.addFunction(functionDto);
    }

    @Override
    public void addFunctionItem(FunctionItemDto itemDto) {
        functionDao.addFunctionItem(itemDto);
    }

    @Override
    public void updateFunction(FunctionDto functionDto) {
        functionDao.updateFunction(functionDto);
        //根节点修改其子节点的full_path属性
        if (!StringUtils.hasText(functionDto.getPid())) {
            functionDao.updateFullPathByParent(functionDto.getCustomerId(), functionDto.getFunctionKey(), functionDto.getFunctionId());
        }
    }

    @Override
    public void updateFunctionItem(FunctionItemDto itemDto) {
        functionDao.updateFunctionItem(itemDto);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<RoleFunctionDto> findFunctionAll(String customerId, List<String> roleIds) {
        List<RoleFunctionDto> list = new ArrayList<>();
        List<RoleFunctionDto> result = functionDao.findFunctionAll(customerId, roleIds);
        for (RoleFunctionDto parentDto : result) {
            String parentPid = parentDto.getPid();
            if (null != parentPid && !StringUtils.isEmpty(parentPid)) {     //pid为空才是父级
                continue;
            }
            //为父级节点添加子节点
            String parentFunctionId = parentDto.getFunctionId();
            for (RoleFunctionDto childDto : result) {
                String childId = childDto.getFunctionId();
                String childPid = childDto.getPid();
                if (parentFunctionId.equals(childPid)) {
                    RoleFunctionDto subDto = parentDto.findChildById(childId);
                    if (null == subDto) {
                        parentDto.addChild(childDto);
                    }
                    continue;
                }
            }
            list.add(parentDto);
        }
    	LicenseData licenseData=License.getInstance().getLicenseData();
		List<String> lice=(List<String>)licenseData.get(LicenseConfig.LicenseParm.SV);
		if(null==lice){
			lice=CollectionKit.newList();
		}
		handFunction(list,lice);
        return list;
    }
    
    private boolean handFunction(List<RoleFunctionDto> list,List<String> lice){
    	boolean hasChild=false;
    	Iterator<RoleFunctionDto> iterator= list.iterator();
    	while (iterator.hasNext()){
    		RoleFunctionDto rfd=iterator.next();
    		boolean result=handFunction(rfd.getChilds(),lice);
    		boolean b=false;
    		for(String str :lice){
    			if(str.equals(rfd.getPathUrl())){
    				b=true;
    			}
    		}
    		if(!result&&!b){
    			iterator.remove();
    		}else{
    			hasChild=true;
    		}
    	}
    	
    	return hasChild;
    }
    @Override
    public void addRoleFunction(String customerId, String roleId, String[] functionItems, String createUserId) {
        functionDao.deleteRoleFunction(customerId, roleId);
        List<Map<String, String>> list = getFunctionAndItems(functionItems);
        List<RoleFunctionDto> dtos = new ArrayList<>();
        if(list.size()>0){
        	for (Map<String, String> map : list) {
                RoleFunctionDto dto = new RoleFunctionDto();
                dto.setRoleFunctionId(Identities.uuid2());
                dto.setCustomerId(customerId);
                dto.setFunctionId(map.get("functionId"));
                dto.setRoleId(roleId);
                dto.setFunctionItem(map.get("Items"));
                dto.setCreateUserId(createUserId);
                dto.setCreateTime(DateUtil.getTimestamp());
                dtos.add(dto);
            }
            functionDao.addRoleFunction(dtos);
        }
    }

    @Override
    public List<RoleFunctionDto> findRoleFunction(String customerId, String roleId) {
        return functionDao.findRoleFunction(customerId, roleId);
    }

    /**
     * 获取功能和功能操作信息
     *
     * @param functionItems
     * @return
     */
    private List<Map<String, String>> getFunctionAndItems(String[] functionItems) {
        List<Map<String, String>> list = CollectionKit.newList();
        if(null==functionItems){
        	return list;
        }
        for (String item : functionItems) {
            String[] functionAndItem = item.split(":");
            if (!list.isEmpty()) {
                boolean doAdd = false;
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> rs = list.get(i);
                    if (rs.get("functionId").equals(functionAndItem[0])) {
                        list.get(i).put("Items", rs.get("Items").concat(",").concat(functionAndItem[1]));
                        doAdd = true;
                        break;
                    }
                }
                if (doAdd) continue;    //假如有操作添加到某一个功能中,则退出跳出循环
            }
            Map<String, String> map = CollectionKit.newMap();
            map.put("functionId", functionAndItem[0]);
            map.put("Items", functionAndItem[1]);
            list.add(map);
        }
        return list;
    }
    
    
    //===========================================================
    
    @Override
    public List<HomeConfigDto> queryUserHomeConfig(String empId, String customerId) {
        List<HomeConfigDto> dtos = queryUserHomeConfig(HOME_FUNCTION_CODE, empId, customerId);
        if (dtos == null || (dtos != null && dtos.size() == 0)) {
            dtos = getHomeConfigEnum(empId, customerId);
        } else {
            for (HomeConfigDto configDto : dtos) {
                HomeConfigEnum configEnum = HomeConfigEnum.valueOf(configDto.getCardCode());
                configDto.setName(configEnum.getTitle());
                configDto.setModule(configEnum.getModuleId());
            }
            Collections.sort(dtos, CompareUtil.createComparator(1, "module", "showIndex"));
        }
        return dtos;
    }

    @Override
    public List<HomeConfigDto> queryUserHomeConfig(String functionCode, String empId, String customerId) {
        return functionDao.queryHomeConfig(functionCode, empId, customerId);
    }

    @Override
    public void editUserHomeConfig(String homeConfig, String empId, String customerId) {
        editUserHomeConfig(homeConfig, HOME_FUNCTION_CODE, empId, customerId);
    }

    @Override
    public void editUserHomeConfig(String homeConfig, String functionCode, String empId, String customerId) {
        Gson gson = new Gson();
        List<HomeConfigDto> list = gson.fromJson(homeConfig, new TypeToken<List<HomeConfigDto>>() {
        }.getType());
        List<HomeConfigDto> insertLists = CollectionKit.newList();
        List<HomeConfigDto> updateLists = CollectionKit.newList();
        for (HomeConfigDto configDto : list) {
            if (configDto.getFunctionConfigId() == null) {
                configDto.setFunctionConfigId(Identities.uuid2());
                configDto.setEmpId(empId);
                configDto.setCustomerId(customerId);
                configDto.setFunctionId(functionCode);
                insertLists.add(configDto);
            }else{
                updateLists.add(configDto);
            }
        }

        try {
            if (insertLists.size() > 0){
            	functionDao.insertUserHomeConfig(insertLists);
            }
            if (updateLists.size() > 0){
            	functionDao.updateUserHomeConfig(updateLists);
            }
        } catch (Exception e) {
            log.error("没有允许执行多条sql的权限。");
            log.error(e.getMessage());
        }
    }

    private List<HomeConfigDto> getHomeConfigEnum(String empId, String customerId) {
        List<HomeConfigDto> homeConfigDtos = new ArrayList<HomeConfigDto>();
        int idx = 1, functionIdx = 1;
        for (HomeConfigEnum configEnum : HomeConfigEnum.values()) {
            HomeConfigDto dto = new HomeConfigDto();
            dto.setEmpId(empId);
            dto.setCustomerId(empId);
            dto.setFunctionId(HOME_FUNCTION_CODE);
            dto.setView(Boolean.TRUE);
            dto.setCardCode(configEnum.toString());
            dto.setModule(configEnum.getModuleId());
            dto.setName(configEnum.getTitle());
            if (configEnum.getModuleId() == 1) {     //不同的模块不同的索引
                dto.setShowIndex(idx++);
            } else {
                dto.setShowIndex(functionIdx++);
            }
            homeConfigDtos.add(dto);
        }
        return homeConfigDtos;
    }

}
