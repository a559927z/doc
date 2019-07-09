package net.chinahrd.modules.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.chinahrd.modules.demo.entity.UserInfo;
import net.chinahrd.response.RbacUserRes;
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

}
