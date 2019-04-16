package com.ccnu.bbs.service.Impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.repository.UserRepository;
import com.ccnu.bbs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Cacheable(cacheNames = "User", key = "#userId")
    public User findUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User createUser(String userId) {
        User user = new User();
        user.setUserId(userId);
        userRepository.save(user);
        return user;
    }

    @Override
    @CacheEvict(cacheNames = "User", key = "#userInfo.openId")
    public User updateUser(WxMaUserInfo userInfo) {
        String userId = userInfo.getOpenId();
        User user = userRepository.findByUserId(userId);
        user.setUserName(userInfo.getNickName());
        user.setUserSex(Integer.valueOf(userInfo.getGender()));
        user.setUserCity(userInfo.getCity());
        user.setUserProvince(userInfo.getProvince());
        user.setUserCountry(userInfo.getCountry());
        user.setUserImg(userInfo.getAvatarUrl());
        return userRepository.save(user);
    }
}
