package com.ccnu.bbs.service.Impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.enums.ResultEnum;
import com.ccnu.bbs.enums.RoleEnum;
import com.ccnu.bbs.exception.BBSException;
import com.ccnu.bbs.repository.UserRepository;
import com.ccnu.bbs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    /**
     * 查找用户
     */
    public User findUser(String userId) {
        User user = getUser(userId);
        if (user != null){
            redisTemplate.opsForValue().set("User::" + userId, user, 1, TimeUnit.HOURS);
        }
        return user;
    }

    @Override
    /**
     * 创建用户
     */
    @Transactional
    public User createUser(String userId) {
        User user = new User();
        user.setUserRoleType(RoleEnum.USER.getCode());
        user.setUserId(userId);
        user = userRepository.save(user);
        return user;
    }

    @Override
    /**
     * 更新用户信息
     */
    @Transactional
    public User updateUser(WxMaUserInfo userInfo) {
        String userId = userInfo.getOpenId();
        User user = getUser(userId);
        if (user == null){
            throw new BBSException(ResultEnum.USER_NOT_EXIT);
        }
        user.setUserName(userInfo.getNickName());
        user.setUserGender(Integer.valueOf(userInfo.getGender()));
        user.setUserCity(userInfo.getCity());
        user.setUserProvince(userInfo.getProvince());
        user.setUserCountry(userInfo.getCountry());
        user.setUserImg(userInfo.getAvatarUrl());
        redisTemplate.delete("User::" + userId);
        return userRepository.save(user);
    }

    private User getUser(String userId){
        User user;
        if (redisTemplate.hasKey("User::" + userId)){
            user = (User)redisTemplate.opsForValue().get("User::" + userId);
        }
        else {
            user = userRepository.findByUserId(userId);
        }
        return user;
    }
}
