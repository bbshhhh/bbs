package com.ccnu.bbs.service.Impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.enums.UserGenderEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    private final static String userId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    @Test
    public void findUser() {
        User user = userService.findUser(userId);
        Assert.assertNotNull(user);
    }

    @Test
    public void createUser() {
        User user = userService.createUser("1111");
        assertNotNull(user);
    }

    @Test
    public void updateUser() {
        WxMaUserInfo userInfo = new WxMaUserInfo();
        userInfo.setOpenId("789");
        userInfo.setNickName("悲酥清风");
        userInfo.setAvatarUrl("http://img.ccnunercel.cn/20170710002950_PW8xw.jpeg");
        userInfo.setCity("Kunming");
        userInfo.setProvince("Yunnan");
        userInfo.setCountry("China");
        userInfo.setGender("1");
        User user = userService.updateUser(userInfo);
        assertNotNull(user);
    }
}