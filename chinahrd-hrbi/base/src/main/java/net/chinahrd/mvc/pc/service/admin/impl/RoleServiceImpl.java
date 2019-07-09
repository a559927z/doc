package net.chinahrd.mvc.pc.service.admin.impl;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.entity.dto.pc.admin.RoleDto;
import net.chinahrd.entity.dto.pc.admin.UserRoleDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.mvc.pc.dao.admin.RoleDao;
import net.chinahrd.mvc.pc.service.admin.RoleService;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 角色Service实现类
 * Created by wqcai on 15/6/10.
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	

    @Autowired
    private RoleDao roleDao;

    @Override
    public PaginationDto<RoleDto> findAll(String customerId, PaginationDto<RoleDto> pageDto) {
        int count = roleDao.findAllCount(customerId);
        pageDto.setRecords(count);
        if (count == 0) {
            return pageDto;
        }
        RowBounds rowBounds = new RowBounds(pageDto.getOffset(), pageDto.getLimit());
        List<RoleDto> roleList = roleDao.findAll(customerId, rowBounds);
        pageDto.setRows(roleList);
        return pageDto;
    }

    @Override
    public List<UserRoleDto> findRoleOfUser(String customerId, String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        return roleDao.findRoleOfUser(customerId, userId);
    }

    @Override
    public boolean addRole(RoleDto roleDto) {
        try {
            roleDao.insertRole(roleDto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean editRole(RoleDto roleDto) {
        try {
            roleDao.updateRole(roleDto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteRole(String customerId, List<String> roleId) {
        try {
            roleDao.deleteRole(customerId, roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public RoleDto findRoleById(String customerId, String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            return null;
        }
        return roleDao.findRoleById(customerId, roleId);
    }

    @Override
    public void addUserRole(String customerId, String userId, String[] roleIds, String createUserId) {
        roleDao.deleteUserRole(customerId, userId);
        if (null == roleIds || roleIds.length <= 0) {
            return;
        }
        List<UserRoleDto> dtos = new ArrayList<>();
        for (String roleId : roleIds) {
            UserRoleDto dto = new UserRoleDto();
            dto.setUserRoleId(Identities.uuid2());
            dto.setCustomerId(customerId);
            dto.setUserId(userId);
            dto.setRoleId(roleId);
            dto.setCreateUserId(createUserId);
            dto.setCreateTime(DateUtil.getTimestamp());

            dtos.add(dto);
        }
        roleDao.addUserRole(dtos);
    }

    @Override
    public List<ItemDto> queryRoleIdByUserId(String customerId, String userId) {
        return roleDao.queryRoleIdByUserId(customerId, userId);
    }

	@Override
	public int hasEmpByRoleId(String customerId, List<String> roleIds) {
        return roleDao.hasEmpByRoleId(customerId, roleIds);
	}
}
