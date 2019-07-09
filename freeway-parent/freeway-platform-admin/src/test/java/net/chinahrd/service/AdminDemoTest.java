package net.chinahrd.service;

import net.chinahrd.AdminApplication;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.demo.request.UserQueryReq;
import net.chinahrd.modules.demo.response.UserInfoRes;
import net.chinahrd.modules.demo.service.UserInfoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdminApplication.class})
public class AdminDemoTest {

    @Autowired
    private UserInfoService userInfoService;

    //测试平台数据库
    @Test
    public void testPatformData() {
        UserQueryReq dataReq = new UserQueryReq();
        dataReq.setPageNum(1);
        dataReq.setPageSize(1);
        dataReq.setUserName("admin");

        CurrentUser currentUser = new CurrentUser();
        currentUser.setTenantId("F54096CC86A9494DA345B74DD25AE5B0");
        dataReq.setCurrentUser(currentUser);

        PageData<UserInfoRes> ret1 = userInfoService.selectByPage(dataReq);
        System.out.println(ret1.getList().get(0).getUserName());

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
