package com.ccnu.bbs.service;

import com.ccnu.bbs.entity.Portray;

public interface PortrayService {

    Portray findPortray(String userId);

    Portray savePortray(String userId,Integer roleId);
}
