package net.chinahrd.controller;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.base.ClientBaseController;
import net.chinahrd.dto.KVItemDto;
import net.chinahrd.service.EmpMgrService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.VerifyPermissionUtil;
import net.chinahrd.vo.UserLockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@Controller
@RequestMapping("/emp")
@Slf4j
public class EmpMgrController extends ClientBaseController {

    @Autowired
    private EmpMgrService empMgrService;

    @RequestMapping(value = "/index.do")
    public String toUserOpt() {
        return "empMgr";
    }

    /**
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/list.do")
    public Map<String, Object> queryUserList(
            HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        //直接返回前台
        String draw = request.getParameter("draw");
        //数据起始位置
        Integer startIndex = Integer.parseInt(request.getParameter("startIndex"));
        //数据长度
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        //原生搜索
        String search = request.getParameter("search");
        //获取客户端需要那一列排序
        String orderColumn = request.getParameter("orderColumn");
        if (orderColumn == null) {
            orderColumn = "user_key";
        }
        //获取排序方式 默认为asc
        String orderDir = request.getParameter("orderDir");
        if (orderDir == null) {
            orderDir = "asc";
        }

        List<UserLockVo> userLockList = empMgrService.queryEmpList(startIndex, pageSize, search, orderColumn, orderDir);

        Map<String, Object> rsMap = CollectionKit.newMap();
        rsMap.put("data", userLockList);
        rsMap.put("total", userLockList.get(0).getTotal());
        rsMap.put("draw", draw);

        return rsMap;
//        buildSuccessResponse(request, response, rsMap);
    }


    /**
     * 删除
     *
     * @param userIds
     */
    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public KVItemDto delete(@RequestParam("userIds[]") List<String> userIds) {
        try {
            empMgrService.delete(userIds);
            return new KVItemDto<Boolean, String>(true, "操作成功");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new KVItemDto<Boolean, String>(false, "操作失败");
    }

    /**
     * 加锁
     *
     * @param userIds
     */
    @ResponseBody
    @RequestMapping(value = "/lock.do")
    public KVItemDto updateLockStatus(@RequestParam("userIds[]") List<String> userIds) {
        try {
            empMgrService.updateLockStatus(userIds);
            return new KVItemDto<Boolean, String>(true, "操作成功");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new KVItemDto<Boolean, String>(false, "操作失败");
    }

    /**
     * 手动修改个人年假天数
     *
     * @param annualTotal
     * @param userId
     */
    @ResponseBody
    @RequestMapping(value = "/updateAnnualTotal.do")
    public KVItemDto updateAnnualTotal(
            @RequestParam("annualTotal") Double annualTotal,
            @RequestParam("userId") String userId) {
        try {
            empMgrService.updateAnnualTotal(annualTotal, userId);
            return new KVItemDto<Boolean, String>(true, "操作成功");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new KVItemDto<Boolean, String>(false, "操作失败");
    }


}
