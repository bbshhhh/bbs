package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.Portray;
import com.ccnu.bbs.repository.PortrayRepository;
import com.ccnu.bbs.service.PortrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortrayServiceImpl implements PortrayService {

    @Autowired
    private PortrayRepository portrayRepository;

    @Override
    public Portray findPortray(String userId) {
        return portrayRepository.findByPortrayUserId(userId);
    }

    @Override
    public Portray savePortray(String userId, Integer roleId) {
        Portray portray = new Portray();
        portray.setPortrayUserId(userId);
        portray.setPortrayRoleId(roleId);
        return portrayRepository.save(portray);
    }
}
