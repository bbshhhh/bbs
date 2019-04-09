package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.repository.UserRepository;
import com.ccnu.bbs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    @Override
    public User findUser(String userId) {
        boolean hasKey = redisTemplate.hasKey(userId);
        User user;
        if (hasKey){
            user = (User) redisTemplate.opsForValue().get(userId);
            log.info("==========从缓存中获得数据=========");
            log.info("【用户id】: {}",user.getUserId());
            log.info("==============================");
        }
        else {
            user = userRepository.findByUserId(userId);
            if (user != null){
                log.info("==========从数据表中获得数据=========");
                log.info("【用户id】: ", user.getUserId());
                log.info("==============================");
                redisTemplate.opsForValue().set(userId, user);
            }
        }
        return user;
    }

    @Override
    public User createUser(String userId) {
        User user = new User();
        user.setUserId(userId);
        userRepository.save(user);
        return user;
    }
}
