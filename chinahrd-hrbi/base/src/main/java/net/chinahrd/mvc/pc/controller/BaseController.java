package net.chinahrd.mvc.pc.controller;

import java.util.List;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.eis.permission.support.ShiroAuthorizingRealm;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.VersionController;
import net.chinahrd.utils.CollectionKit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wqcai on 15/6/15.
 */
public abstract class BaseController extends VersionController{

	// jxzhang 这样所有Controller子类都用再加载log对像 by 2016-12-13
	public static final Logger log = LoggerFactory.getLogger(BaseController.class);
    /**
     * 获取登录用户ID
     *
     * @return
     */
    public String getUserId() {
        return EisWebContext.getCurrentUser().getUserId();
    }

    public RbacUser getUserInfo(){
        return EisWebContext.getCurrentUser();
    }
    /**
     * 获取登录用户客户ID
     *
     * @return
     */
    public String getCustomerId() {
        String customerId = EisWebContext.getCurrentUser().getCustomerId();
        if (StringUtils.isEmpty(customerId)) {
            customerId = EisWebContext.getCustomerId();
        }

        return customerId;
    }

    public String getUserEmpId() {
        return EisWebContext.getCurrentUser().getEmpId();
    }

    /**
     * 登录人所有数据权限	by jxzhang
     * @return
     */
    public List<OrganDto> getOrganPermit() {
        return EisWebContext.getCurrentUser().getOrganPermit();
    }
    
    /**
     * 登录人所有数据权限ID集	by jxzhang
     * @return
     */
    public List<String> getOrganPermitId(){
		List<OrganDto> organPermit = getOrganPermit();
    	List<String> rs = CollectionKit.newList();
    	if(null == organPermit){
    		rs.add("");
    		return rs;
        }
    	for (OrganDto organDto : organPermit) {
    		rs.add(organDto.getOrganizationId());
		}
		return  rs;
	}
    

    public String getUserEmpName() {
        return EisWebContext.getCurrentUser().getUserNameCh();
    }
    /**
     * 是否系统配置管理员
     * @return
     */
    public boolean isSysDeploy(){
        return EisWebContext.getCurrentUser().getSysDeploy() == 1;
    }
    @Override
    public   void autowiredAfter(){
    	//PC目前没有操作
    }
}
