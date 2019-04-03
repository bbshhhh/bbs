package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findFollowerTest(){
        Page<User> userPage = userRepository.findFollower("456", PageRequest.of(1, 1));
        userPage.forEach(System.out::println);
        Assert.assertNotEquals(0, userPage.getTotalElements());
    }

    @Test
    public void findAttentionTest(){
        Page<User> userPage = userRepository.findAttention("123", PageRequest.of(0, 1));
        userPage.forEach(System.out::println);
        Assert.assertNotEquals(0, userPage.getTotalElements());
    }
}