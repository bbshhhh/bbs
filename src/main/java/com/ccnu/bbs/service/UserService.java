package com.ccnu.bbs.service;

import com.ccnu.bbs.entity.User;

public interface UserService {

    /** 查询用户. */
    User findUser(String userId);
    /** 创建新用户. */
    User createUser(String userId);
}
