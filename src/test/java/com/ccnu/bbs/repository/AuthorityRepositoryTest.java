package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Authority;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AuthorityRepositoryTest {

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    public void findRoleAuthorityTest() {
        Authority res = authorityRepository.findRoleAuthority(1);
        Assert.assertNotNull(res);
    }
}