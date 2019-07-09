package net.chinahrd.platform.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.chinahrd.platform.rbac.entity.UserInfo;
import net.chinahrd.response.RbacResourceRes;
import net.chinahrd.response.RbacRoleRes;
import net.chinahrd.response.RbacUserRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzmo
 * @since 2019-03-05
 */
public interface UserInfoDAO extends BaseMapper<UserInfo> {

    /**
     * 通过用户名查找用户信息
     *
     * @param userName
     * @return
     * @author wzmo
     */
    List<RbacUserRes> selectByUserName(@Param("userName") String userName);

    /**
     * 由角色Id查询资源
     *
     * @param roleId
     * @return
     */
    List<RbacResourceRes> selectResourcesByRoleId(@Param("roleId") String roleId);

    /**
     * 由用户Id查角色
     *
     * @param userId
     * @return
     */
    List<RbacRoleRes> selectRolesByUserId(@Param("userId") String userId);
}
