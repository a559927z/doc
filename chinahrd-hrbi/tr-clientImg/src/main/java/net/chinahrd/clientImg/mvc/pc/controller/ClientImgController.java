package net.chinahrd.clientImg.mvc.pc.controller;

import net.chinahrd.api.SalesBoardApi;
import net.chinahrd.clientImg.mvc.pc.service.ClientImgService;
import net.chinahrd.core.api.ApiManagerCenter;
import net.chinahrd.entity.dto.pc.common.GraphChartDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 客户画像
 * Created by wqcai on 16/12/16 016.
 */
@Controller
@RequestMapping(value = "/clientImg")
public class ClientImgController extends BaseController {

    @Autowired
    private ClientImgService clientImgService;

    /***
     * 跳转到客户画像主页
     * @return
     */
    @RequestMapping(value = "/toClientImgView")
    public String toClientImgView(HttpServletRequest request) {
        String functionId = CacheHelper.getFunctionId("clientImg/toClientImgView");
        if (StringUtils.isNotBlank(functionId)) {
            request.setAttribute("quotaId", functionId);
        }
        return "biz/other/clientImg";
    }

    /**
     * 获取登录人关联节点数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManageList", method = RequestMethod.GET)
    public GraphChartDto getManageList() {
        return clientImgService.queryManageList(getUserEmpId(), getCustomerId());
    }

    /**
     * 获取销售人员关联节点信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesEmpImg", method = RequestMethod.POST)
    public GraphChartDto getSalesEmpImg(String empId) throws InvocationTargetException, IllegalAccessException {
//        if (StringUtils.isEmpty(empId)) empId = getUserEmpId();
        return clientImgService.querySalesEmpImg(empId, getCustomerId());
    }

    /**
     * 获取客户关联节点信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesClientImg", method = RequestMethod.POST)
    public GraphChartDto getSalesClientImg(String empId, String clientId) throws InvocationTargetException, IllegalAccessException {
        if (StringUtils.isEmpty(clientId)) return null;
        return clientImgService.querySalesClientImg(empId, clientId, getCustomerId());
    }

    /**
     * 获取管理者销售总额，销售利润，回款总额
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManageTotalSalesInfo", method = RequestMethod.POST)
    public List<SalesBoardDto> getManageTotalSalesInfo() {
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        List<SalesBoardDto> list = null;
        if (api != null) {
            list = api.getSalesMoneyAndProfitAndReturnAmount(getCustomerId(), getUserEmpId());
        }
        return list;
    }

    /**
     * 获取销售人员销售总额，销售利润，回款总额
     *
     * @param empId 销售人员id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSaleTotalSalesInfo", method = RequestMethod.POST)
    public List<SalesBoardDto> getSaleTotalSalesInfo(String empId) {
        if (StringUtils.isEmpty(empId)) return null;
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        List<SalesBoardDto> list = null;
        if (api != null) {
            list = api.getSalesMoneyAndProfitAndReturnAmount(getCustomerId(), empId);
        }
        return list;
    }

    /**
     * 获取客户购买总额，销售利润，回款总额
     *
     * @param empId 销售人员id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClientTotalSalesInfo", method = RequestMethod.POST)
    public List<SalesBoardDto> getClientTotalSalesInfo(String empId, String clientId) {
        if (StringUtils.isEmpty(clientId)) return null;
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        List<SalesBoardDto> list = null;
        if (api != null) {
            list = api.getClientSalesMoneyAndProfitAndReturnAmount(getCustomerId(), empId, clientId);
        }
        return list;
    }

    /**
     * 获取管理者销售额、环比变化
     *
     * @param row 显示最近几个月数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManageSalesMoneyAndRing", method = RequestMethod.POST)
    public Map<String, Object> getManageSalesMoneyAndRing(Integer row) {
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        Map<String, Object> map = CollectionKit.newMap();
        if (api != null) {
            map = api.getSalesMoneyAndRing(getCustomerId(), getUserEmpId(), row);
        }
        return map;
    }

    /**
     * 获取销售人员销售额、环比变化
     *
     * @param empId 销售人员id
     * @param row   显示最近几个月数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSaleSalesMoneyAndRing", method = RequestMethod.POST)
    public Map<String, Object> getSaleSalesMoneyAndRing(String empId, Integer row) {
        if (StringUtils.isEmpty(empId)) return null;
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        Map<String, Object> map = CollectionKit.newMap();
        if (api != null) {
            map = api.getSalesMoneyAndRing(getCustomerId(), empId, row);
        }
        return map;
    }

    /**
     * 获取销售人员/客户销售额、环比变化
     *
     * @param empId    销售人员id
     * @param clientId 客户id
     * @param row      显示最近几个月数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClientSalesMoneyAndRing", method = RequestMethod.POST)
    public Map<String, Object> getClientSalesMoneyAndRing(String empId, String clientId, Integer row) {
        if (StringUtils.isEmpty(clientId)) return null;
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        Map<String, Object> map = CollectionKit.newMap();
        if (api != null) {
            map = api.getClientSalesMoneyAndRing(getCustomerId(), empId, clientId, row);
        }
        return map;
    }

    /**
     * 获取管理者回款总额/环比变化
     *
     * @param row 最近几个月数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManageReturnAmountAndRing", method = RequestMethod.POST)
    public Map<String, Object> getManageReturnAmountAndRing(Integer row) {
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        Map<String, Object> map = CollectionKit.newMap();
        if (api != null) {
            map = api.getSalesReturnAmountAndRing(getCustomerId(), getUserEmpId(), row);
        }
        return map;
    }

    /**
     * 获取销售人员回款总额/环比变化
     *
     * @param empId 销售人员id
     * @param row   最近几个月数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSaleReturnAmountAndRing", method = RequestMethod.POST)
    public Map<String, Object> getSaleReturnAmountAndRing(String empId, Integer row) {
        if (StringUtils.isEmpty(empId)) return null;
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        Map<String, Object> map = CollectionKit.newMap();
        if (api != null) {
            map = api.getSalesReturnAmountAndRing(getCustomerId(), empId, row);
        }
        return map;
    }

    /**
     * 获取销售人员/客户回款总额/环比变化
     *
     * @param empId    销售人员id
     * @param row      最近几个月数据
     * @param clientId 销售客户id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClientReturnAmountAndRing", method = RequestMethod.POST)
    public Map<String, Object> getClientReturnAmountAndRing(String empId, String clientId, Integer row) {
        if (StringUtils.isEmpty(clientId)) return null;
        SalesBoardApi api = ApiManagerCenter.getApiDoc(SalesBoardApi.class);
        Map<String, Object> map = CollectionKit.newMap();
        if (api != null) {
            map = api.getClientReturnAmountAndRing(getCustomerId(), empId, clientId, row);
        }
        return map;
    }
}
