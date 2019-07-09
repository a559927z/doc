package net.chinahrd.service;

import junit.framework.TestCase;
import net.chinahrd.ServiceApplication;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.deployment.TenantInfoHolder;
import net.chinahrd.modules.demo.request.EmpQueryReq;
import net.chinahrd.modules.demo.response.EmpInfoRes;
import net.chinahrd.modules.demo.service.EmpInfoService;
import net.chinahrd.platform.demo.entity.DemoUserInfo;
import net.chinahrd.platform.demo.request.DemoUserQueryReq;
import net.chinahrd.platform.demo.response.DemoUserInfoRes;
import net.chinahrd.platform.demo.service.DemoUserInfoService;
import net.chinahrd.utils.UuidUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})
public class DemoTest {

    @Autowired
    private EmpInfoService empInfoService;

    @Autowired
    private DemoUserInfoService userInfoService;


    //@Test
    public void testInsert() {
        System.out.println("测试中1");
        DemoUserInfo userInfo = new DemoUserInfo();
        userInfo.setUserId(UuidUtil.uuid32());
        userInfo.setUserName("bright");
        userInfo.setCreateInfo(UuidUtil.uuid32(), UuidUtil.uuid32());

        userInfoService.save(userInfo);
        // 断言
        TestCase.assertEquals(1, 1);
    }

    // 测试业务数据库 ,可能多个，动态切换
    @Test
    public void testBusinessData() {
        EmpQueryReq dataReq = new EmpQueryReq();
        dataReq.setPageNum(1);
        dataReq.setPageSize(1);
        dataReq.setEmpName("毛小");

        CurrentUser currentUser = new CurrentUser();
        currentUser.setTenantId("F54096CC86A9494DA345B74DD25AE5B0");
        dataReq.setCurrentUser(currentUser);

        TenantInfoHolder.setDataSourceKey("d1");
        PageData<EmpInfoRes> ret1 = empInfoService.selectByPage(dataReq);
        System.out.println(ret1.getList().get(0).getEmpName());
        TenantInfoHolder.removeDataSourceKey();


        TenantInfoHolder.setDataSourceKey("d2");
        PageData<EmpInfoRes> ret2 = empInfoService.selectByPage(dataReq);
        System.out.println(ret2.getList().get(0).getEmpName());
        TenantInfoHolder.removeDataSourceKey();
    }

    //测试平台数据库
    @Test
    public void testPatformData() {
        DemoUserQueryReq dataReq = new DemoUserQueryReq();
        dataReq.setPageNum(1);
        dataReq.setPageSize(1);
        dataReq.setUserName("admin");

        CurrentUser currentUser = new CurrentUser();
        currentUser.setTenantId("F54096CC86A9494DA345B74DD25AE5B0");
        dataReq.setCurrentUser(currentUser);

        PageData<DemoUserInfoRes> ret1 = userInfoService.selectByPage(dataReq);
        System.out.println(ret1.getList().get(0).getUserName());

    }

    //测试平台数据库和业务数据库
    @Test
    public void testMix() {
        CurrentUser currentUser = new CurrentUser();
        currentUser.setTenantId("F54096CC86A9494DA345B74DD25AE5B0");

        DemoUserQueryReq dataReq = new DemoUserQueryReq();
        dataReq.setPageNum(1);
        dataReq.setPageSize(1);
        dataReq.setUserName("admin");
        dataReq.setCurrentUser(currentUser);

        PageData<DemoUserInfoRes> ret = userInfoService.selectByPage(dataReq);
        System.out.println(ret.getList().get(0).getUserName());

        EmpQueryReq empQueryReq = new EmpQueryReq();
        empQueryReq.setPageNum(1);
        empQueryReq.setPageSize(1);
        empQueryReq.setEmpName("毛小");

        TenantInfoHolder.setDataSourceKey("d1");
        PageData<EmpInfoRes> ret1 = empInfoService.selectByPage(empQueryReq);
        System.out.println(ret1.getList().get(0).getEmpName());
        TenantInfoHolder.removeDataSourceKey();

    }

    @Test
    public void selectListByTask() {
        List<DemoUserInfoRes> userInfoRes = userInfoService.selectListByTask();
        int size = 0;
        while (true) {
            size = userInfoRes.size();
            if (size > 0) {
                break;
            }
            System.out.print(size);
        }
    }

    @Before
    public void testBefore() {
        System.out.println("测试前");
    }

    @After
    public void testAfter() {
        System.out.println("测试后");
    }
}
