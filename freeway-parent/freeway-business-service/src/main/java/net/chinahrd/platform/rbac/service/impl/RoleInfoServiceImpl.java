package net.chinahrd.platform.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.platform.rbac.dao.RoleInfoDAO;
import net.chinahrd.platform.rbac.entity.RoleInfo;
import net.chinahrd.platform.rbac.service.RoleInfoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzmo
 * @since 2019-03-05
 */
@Slf4j
@Component
@Service(interfaceClass = RoleInfoService.class)
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoDAO, RoleInfo> implements RoleInfoService {


}
