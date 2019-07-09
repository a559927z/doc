package net.chinahrd.modules.pub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.chinahrd.modules.pub.dao.ParamInfoDAO;
import net.chinahrd.modules.pub.entity.ParamInfo;
import net.chinahrd.modules.pub.service.ParamInfoService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 系统参数配置表 服务实现类
 * </p>
 *
 * @author cxhuo_gz
 * @since 2019-03-21
 */
@Slf4j
@Service
public class ParamInfoServiceImpl extends ServiceImpl<ParamInfoDAO, ParamInfo> implements ParamInfoService {

}
