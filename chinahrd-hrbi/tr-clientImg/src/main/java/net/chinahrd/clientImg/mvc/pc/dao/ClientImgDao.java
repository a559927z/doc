package net.chinahrd.clientImg.mvc.pc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.clientImg.ClientBaseInfoDto;
import net.chinahrd.entity.dto.pc.clientImg.ClientContactsDto;
import net.chinahrd.entity.dto.pc.clientImg.ClientSummaryDto;
import net.chinahrd.entity.dto.pc.common.GraphNodesDto;

/**
 * 客户画像dao接口类
 * Created by wqcai on 16/12/16 016.
 */
@Repository("clientImgDao")
public interface ClientImgDao {

    /**
     * 获取下属（销售人员）信息
     *
     * @param empId
     * @param customerId
     * @return
     */
    List<GraphNodesDto> querySubSalesEmps(@Param("empId") String empId, @Param("customerId") String customerId, @Param("yearMonth") String yearMonth);

    /**
     * 获取销售人员及上级信息
     * @param empId
     * @param customerId
     * @return
     */
    List<GraphNodesDto> querySalesEmpHfInfo(@Param("empId") String empId, @Param("customerId") String customerId);


    /**
     * 获取下属（销售人员）客户信息
     * @param empIds
     * @param customerId
     * @return
     */
    List<GraphNodesDto> querySalesEmpClients(@Param("empIds") List<String> empIds, @Param("customerId") String customerId);

    /**
     * 获取销售客户相关信息
     * @param clientId
     * @param customerId
     * @return
     */
    GraphNodesDto querySalesEmpClientInfo(@Param("clientId") String clientId, @Param("customerId") String customerId);

    /**
     * 获取销售客户维度信息
     * @param clientIds
     * @param customerId
     * @return
     */
    List<GraphNodesDto> querySalesClientDimension(@Param("clientIds") List<String> clientIds, @Param("customerId") String customerId);
    
    /**
     * 获取销售客户基本信息
     * @param customerId
     * @param clientId
     * @return
     * */
    List<ClientBaseInfoDto> querySalesClientBaseInfo(@Param("customerId") String customerId, @Param("clientId") String clientId);
    
    /**
     * 获取销售客户联系人信息
     * @param customerId
     * @param clientId
     * @return
     * */
    List<ClientContactsDto> querySalesClientContacts(@Param("customerId") String customerId, @Param("clientId") String clientId);
    
    /**
     * 获取销售客户纪要信息
     * @param customerId
     * @param clientId
     * @return
     * */
    List<ClientSummaryDto> querySalesClientSummary(@Param("customerId") String customerId, @Param("clientId") String clientId);
}
