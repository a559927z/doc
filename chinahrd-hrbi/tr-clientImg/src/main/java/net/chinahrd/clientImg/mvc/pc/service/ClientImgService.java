package net.chinahrd.clientImg.mvc.pc.service;

import net.chinahrd.entity.dto.pc.common.GraphChartDto;

import java.lang.reflect.InvocationTargetException;

/**
 * 客户画像Service接口类
 * Created by wqcai on 16/12/16 016.
 */
public interface ClientImgService {

    /**
     * 获取登录者节点数据
     *
     * @param empId
     * @param customerId
     * @return
     */
    GraphChartDto queryManageList(String empId, String customerId);

    /**
     * 获取销售人员节点数据
     *
     * @param empId
     * @param customerId
     * @return
     */
    GraphChartDto querySalesEmpImg(String empId, String customerId) throws InvocationTargetException, IllegalAccessException;


    /**
     * 获取销售客户相关节点数据
     *
     * @param empId
     * @param clientId
     * @param customerId
     * @return
     */
    GraphChartDto querySalesClientImg(String empId, String clientId, String customerId) throws InvocationTargetException, IllegalAccessException;

}
