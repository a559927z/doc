package net.chinahrd.clientImg.mvc.pc.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import net.chinahrd.clientImg.module.Cache;
import net.chinahrd.clientImg.mvc.pc.dao.ClientImgDao;
import net.chinahrd.clientImg.mvc.pc.service.ClientImgService;
import net.chinahrd.entity.dto.pc.clientImg.ClientBaseInfoDto;
import net.chinahrd.entity.dto.pc.clientImg.ClientContactsDto;
import net.chinahrd.entity.dto.pc.clientImg.ClientImgDto;
import net.chinahrd.entity.dto.pc.clientImg.ClientSummaryDto;
import net.chinahrd.entity.dto.pc.common.GraphChartDto;
import net.chinahrd.entity.dto.pc.common.GraphLinksDto;
import net.chinahrd.entity.dto.pc.common.GraphNodesDto;
import net.chinahrd.entity.enums.TipsEnum;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

/**
 * 客户画像Service实现类
 * Created by wqcai on 16/12/16 016.
 */
@Service("clientImgService")
public class ClientImgServiceImpl implements ClientImgService {

    @Autowired
    private ClientImgDao clientImgDao;

    @Override
    public GraphChartDto queryManageList(String empId, String customerId) {
    	String yearMonth = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
        //当前登录人及下属（销售人员） 状态区分
        List<GraphNodesDto> subSalesEmps = clientImgDao.querySubSalesEmps(empId, customerId, yearMonth);
        List<String> subSalesEmpIds = CollectionKit.newList();
        List<GraphLinksDto> subSalesEmpsLinks = CollectionKit.newList();
        for (GraphNodesDto nodesDto : subSalesEmps) {
            String nodeId = nodesDto.getId();
            if (empId.equals(nodeId)) {
                nodesDto.setCategory(0);    //第1类别
                nodesDto.setSymbolSize(60); //当前登录人状态
                continue;
            }
            nodesDto.setCategory(1);        //第2类别
            nodesDto.setSymbolSize(40);     //第2级别状态
            nodesDto.setParent(empId);
            subSalesEmpIds.add(nodeId);
            subSalesEmpsLinks.add(new GraphLinksDto(Identities.uuid2(), nodeId, empId));
        }
        //销售客户状态区分
        List<GraphNodesDto> salesEmpClients = clientImgDao.querySalesEmpClients(subSalesEmpIds, customerId);
//        List<GraphNodesDto> clientDimensionResults = filterRedundantData(salesEmpClients);
//
//        List<GraphLinksDto> clientSalesEmpLinks = CollectionKit.newList();
//        for (GraphNodesDto nodesDto : clientDimensionResults) {
//            nodesDto.setCategory(2);        //第3类别
//            nodesDto.setSymbolSize(20);     //第3级别状态
//            clientSalesEmpLinks.add(new GraphLinksDto(Identities.uuid2(), nodesDto.getId(), nodesDto.getParent()));
//        }
        List<GraphLinksDto> clientSalesEmpLinks = CollectionKit.newList();
        for (GraphNodesDto nodesDto : salesEmpClients) {
            nodesDto.setCategory(2);        //第3类别
            nodesDto.setSymbolSize(20);     //第3级别状态
            clientSalesEmpLinks.add(new GraphLinksDto(Identities.uuid2(), nodesDto.getId(), nodesDto.getParent()));
        }
        //组装返回的对象
        List<GraphNodesDto> rsNodes = CollectionKit.newList();
//        rsNodes.addAll(clientDimensionResults);
        rsNodes.addAll(salesEmpClients);
        rsNodes.addAll(subSalesEmps);
        List<GraphLinksDto> rsLinks = CollectionKit.newList();
        rsLinks.addAll(clientSalesEmpLinks);
        rsLinks.addAll(subSalesEmpsLinks);

        GraphChartDto rsDto = new GraphChartDto();
        rsDto.setNodes(rsNodes);
        rsDto.setLinks(rsLinks);
        return rsDto;
    }

    @Override
    public GraphChartDto querySalesEmpImg(String empId, String customerId) throws InvocationTargetException, IllegalAccessException {
        //下属（销售人员）及上级 状态区分
        List<GraphNodesDto> salesEmpHfInfos = clientImgDao.querySalesEmpHfInfo(empId, customerId);
        List<GraphLinksDto> subSalesEmpsLinks = CollectionKit.newList();
        String parentId = null;
        for (GraphNodesDto nodesDto : salesEmpHfInfos) {
            String nodeId = nodesDto.getId();
            if (empId.equals(nodeId)) {
                nodesDto.setCategory(1);                //第2类别
                nodesDto.setParent(parentId);
                nodesDto.setSymbolSize(60);             //当前所选人状态
                continue;
            }
            parentId = nodeId;
            nodesDto.setCategory(0);                    //第1类别
            nodesDto.setSymbolSize(40);                 //第2级别状态
            subSalesEmpsLinks.add(new GraphLinksDto(Identities.uuid2(), empId, nodeId));
        }
        //销售客户状态区分
        List<String> subSalesEmpIds = CollectionKit.newList();
        subSalesEmpIds.add(empId);
        List<GraphNodesDto> salesEmpClients = clientImgDao.querySalesEmpClients(subSalesEmpIds, customerId);
        List<GraphLinksDto> clientSalesEmpLinks = CollectionKit.newList();
        List<String> clientIds = CollectionKit.newList();
        for (GraphNodesDto nodesDto : salesEmpClients) {
            nodesDto.setCategory(2);            //第3类别
            nodesDto.setSymbolSize(40);         //第2级别状态
            nodesDto.setParent(empId);
            clientSalesEmpLinks.add(new GraphLinksDto(Identities.uuid2(), nodesDto.getId(), empId));
            clientIds.add(nodesDto.getId());
        }
        //客户维度状态区分
//        List<GraphNodesDto> clientDimensions = clientImgDao.querySalesClientDimension(clientIds, customerId);
        List<ClientImgDto> list = Cache.queryClientExistRelation.get();
        List<GraphNodesDto> clientDimensions = CollectionKit.newList();
        List<GraphLinksDto> clientDimensionLinks = CollectionKit.newList();

        for (ClientImgDto clientImgDto : list) {
            String clientId = clientImgDto.getClientId();
            if (!clientIds.contains(clientId)) {
                continue;
            }
            Method[] methods = clientImgDto.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                String methodName = methods[i].getName();
                if (methodName.startsWith("get") && methodName.startsWith("getExist")) {    //判断是维度的属性
                    String lsName = methodName.substring(8);   //枚举名
                    Object loValue = methods[i].invoke(clientImgDto);  // 值
                    TipsEnum dimensionEnum = TipsEnum.asEnumByInfo(lsName, 13);
                    if (dimensionEnum != null && (loValue != null && loValue.hashCode() == 1)){ //判断存在的枚举类型
                        String clientDimensionsId = Identities.uuid2();
                        GraphNodesDto nodesDto = new GraphNodesDto();
                        nodesDto.setId(clientDimensionsId);
                        nodesDto.setText(dimensionEnum.getDesc());
                        nodesDto.setCategory(3);            //第4类别
                        nodesDto.setSymbolSize(20);         //第3级别状态
                        nodesDto.setParent(clientId);
                        clientDimensions.add(nodesDto);
                        clientDimensionLinks.add(new GraphLinksDto(Identities.uuid2(), clientDimensionsId, clientId));
                    }
                }
            }
        }


//        List<GraphNodesDto> clientDimensionResults = filterRedundantData(clientDimensions);
//
//        List<GraphLinksDto> clientDimensionLinks = CollectionKit.newList();
//        for (GraphNodesDto nodesDto : clientDimensionResults) {
//            nodesDto.setCategory(3);            //第4类别
//            nodesDto.setSymbolSize(20);         //第3级别状态
//            clientDimensionLinks.add(new GraphLinksDto(Identities.uuid2(), nodesDto.getId(), nodesDto.getParent()));
//        }

//        for (GraphNodesDto nodesDto : clientDimensions) {
//            nodesDto.setCategory(3);            //第4类别
//            nodesDto.setSymbolSize(20);         //第3级别状态
//            clientDimensionLinks.add(new GraphLinksDto(Identities.uuid2(), nodesDto.getId(), nodesDto.getParent()));
//        }

        //组装返回的对象
        List<GraphNodesDto> rsNodes = CollectionKit.newList();
//        rsNodes.addAll(clientDimensionResults);
        rsNodes.addAll(clientDimensions);
        rsNodes.addAll(salesEmpClients);
        rsNodes.addAll(salesEmpHfInfos);

        List<GraphLinksDto> rsLinks = CollectionKit.newList();
        rsLinks.addAll(clientDimensionLinks);
        rsLinks.addAll(clientSalesEmpLinks);
        rsLinks.addAll(subSalesEmpsLinks);

        GraphChartDto rsDto = new GraphChartDto();
        rsDto.setNodes(rsNodes);
        rsDto.setLinks(rsLinks);
        return rsDto;
    }

    @Override
    public GraphChartDto querySalesClientImg(String empId, String clientId, String customerId) throws InvocationTargetException, IllegalAccessException {
        //下属（销售人员）及上级 状态区分
        List<GraphNodesDto> salesEmpHfInfos = clientImgDao.querySalesEmpHfInfo(empId, customerId);
        List<GraphLinksDto> subSalesEmpsLinks = CollectionKit.newList();
        String parentId = null;
        for (GraphNodesDto nodesDto : salesEmpHfInfos) {
            String nodeId = nodesDto.getId();
            if (empId.equals(nodeId)) {
                nodesDto.setCategory(1);                //第2类别
                nodesDto.setParent(parentId);
                nodesDto.setSymbolSize(40);             //第2级别状态
                continue;
            }
            parentId = nodeId;
            nodesDto.setCategory(0);                    //第1类别
            nodesDto.setSymbolSize(40);                 //第2级别状态
            subSalesEmpsLinks.add(new GraphLinksDto(Identities.uuid2(), empId, nodeId));
        }
        //销售客户状态区分
        GraphNodesDto salesClient = clientImgDao.querySalesEmpClientInfo(clientId, customerId);
        salesClient.setCategory(2);            //第3类别
        salesClient.setSymbolSize(60);         //第2级别状态
        GraphLinksDto salesClientLink = new GraphLinksDto(Identities.uuid2(), salesClient.getId(), empId);

        //客户维度状态区分
        List<String> clientIds = CollectionKit.newList();
        clientIds.add(salesClient.getId());
//        List<GraphNodesDto> clientDimensions = clientImgDao.querySalesClientDimension(clientIds, customerId);
        List<GraphNodesDto> clientDimensions = CollectionKit.newList();
        List<GraphLinksDto> clientDimensionLinks = CollectionKit.newList();
        List<GraphNodesDto> clientDimensions2 = CollectionKit.newList();
        List<GraphLinksDto> clientDimensionLinks2 = CollectionKit.newList();
//        for (GraphNodesDto nodesDto : clientDimensions) {
//            nodesDto.setCategory(3);            //第4类别
//            nodesDto.setSymbolSize(40);         //第3级别状态
//            clientDimensionLinks.add(new GraphLinksDto(Identities.uuid2(), nodesDto.getId(), nodesDto.getParent()));
//        }
        List<ClientImgDto> list = Cache.queryClientExistRelation.get();
        for (ClientImgDto clientImgDto : list) {
            if (!clientId.equals(clientImgDto.getClientId())) {
                continue;
            }
            Method[] methods = clientImgDto.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                String methodName = methods[i].getName();
                if (methodName.startsWith("get") && methodName.startsWith("getExist")) {    //判断是维度的属性
                    String lsName = methodName.substring(8);   //枚举名
                    Object loValue = methods[i].invoke(clientImgDto);  // 值
                    TipsEnum dimensionEnum = TipsEnum.asEnumByInfo(lsName, 13);
                    if (dimensionEnum != null && (loValue != null && loValue.hashCode() == 1)){ //判断存在的枚举类型
                        String clientDimensionsId = Identities.uuid2();
                        GraphNodesDto nodesDto = new GraphNodesDto();
                        nodesDto.setId(clientDimensionsId);
                        nodesDto.setText(dimensionEnum.getDesc());
                        nodesDto.setCategory(3);            //第4类别
                        nodesDto.setSymbolSize(40);         //第3级别状态
                        nodesDto.setParent(clientId);
                        clientDimensions.add(nodesDto);
                        clientDimensionLinks.add(new GraphLinksDto(Identities.uuid2(), clientDimensionsId, clientId));
                        
                        List clientInfos = CollectionKit.newList();
                        //公司对应详细信息，如基础信息，联系人信息等
                        if("BASEINFO".equals(dimensionEnum.getInfo())){
                        	List<ClientBaseInfoDto> baseList = clientImgDao.querySalesClientBaseInfo(customerId, clientId);
                        	clientInfos = baseList;
                        }
                        if("CONTACTS".equals(dimensionEnum.getInfo())){
                        	List<ClientContactsDto> contactsList = clientImgDao.querySalesClientContacts(customerId, clientId);
                        	clientInfos = contactsList;
                        }
                        if("SUMMARY".equals(dimensionEnum.getInfo())){
                        	List<ClientSummaryDto> summayList = clientImgDao.querySalesClientSummary(customerId, clientId);
                        	clientInfos = summayList;
                        }
                        if(clientInfos != null && clientInfos.size() > 0){
                        	for(int a=0; a < clientInfos.size(); a++){
                        		String clientDimensionsId2 = Identities.uuid2();
                        		GraphNodesDto nodesDto2 = new GraphNodesDto();
                        		nodesDto2.setId(clientDimensionsId2);
                        		nodesDto2.setCategory(4);            //第4类别
                        		nodesDto2.setSymbolSize(20);         //第3级别状态
                        		nodesDto2.setParent(clientDimensionsId);
                        		nodesDto2.setText(dimensionEnum.getDesc() + (a + 1));
                        		nodesDto2.setObject(clientInfos.get(a));
                        		clientDimensions2.add(nodesDto2);
                        		clientDimensionLinks2.add(new GraphLinksDto(Identities.uuid2(), clientDimensionsId2, clientDimensionsId));
                        	}
                        }
                    }
                }
            }
        }

        //组装返回的对象
        List<GraphNodesDto> rsNodes = CollectionKit.newList();
        rsNodes.addAll(clientDimensions2);
        rsNodes.addAll(clientDimensions);
        rsNodes.add(salesClient);
        rsNodes.addAll(salesEmpHfInfos);

        List<GraphLinksDto> rsLinks = CollectionKit.newList();
        rsLinks.addAll(clientDimensionLinks2);
        rsLinks.addAll(clientDimensionLinks);
        rsLinks.add(salesClientLink);
        rsLinks.addAll(subSalesEmpsLinks);


        GraphChartDto rsDto = new GraphChartDto();
        rsDto.setNodes(rsNodes);
        rsDto.setLinks(rsLinks);
        return rsDto;
    }

    private List<GraphNodesDto> filterRedundantData(List<GraphNodesDto> data) {
        List<GraphNodesDto> Results = CollectionKit.newList();
        MultiValueMap<String, GraphNodesDto> groupNodes = new LinkedMultiValueMap<String, GraphNodesDto>();
        for (GraphNodesDto nodesDto : data) {
            groupNodes.add(nodesDto.getParent(), nodesDto);
        }

        for (Map.Entry<String, List<GraphNodesDto>> groupEntry : groupNodes.entrySet()) {
            String key = groupEntry.getKey();
            List<GraphNodesDto> list = groupEntry.getValue();
            if (list.size() > 12) {
                for (int i = 0; i < list.size() && i < 11; i++) {
                    Results.add(list.get(i));
                }
                GraphNodesDto nodesDto = new GraphNodesDto();
                nodesDto.setId(Identities.uuid2());
                nodesDto.setParent(key);
                nodesDto.setText("...");
                nodesDto.setName("...");
                nodesDto.setType(1);
                Results.add(nodesDto);
            } else {
                Results.addAll(list);
            }
        }
        return Results;
    }
}
