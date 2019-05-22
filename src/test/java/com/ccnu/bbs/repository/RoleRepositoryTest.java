package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    private final static String userId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    @Test
    public void findUserRoleTest() {
        Role res = roleRepository.findUserRole(userId);
        Assert.assertNotNull(res);
    }
}