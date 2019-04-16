package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.User;
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

    @Test
    public void findUser() {
        User user = userService.findUser("123");
        Assert.assertNotNull(user);
    }

    @Test
    public void createUser() {
    }
}