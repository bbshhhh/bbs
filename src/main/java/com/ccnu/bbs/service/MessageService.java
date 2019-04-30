package com.ccnu.bbs.service;

import com.ccnu.bbs.VO.MessageVO;
import com.ccnu.bbs.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    /** 查看用户消息. */
    Page<MessageVO> getUserMessage(String userId, Integer messageType, Pageable pageable);
    /** 查看是否有新消息. */
    Boolean haveMessage(String userId, Integer messageType);
    /** 保存新消息. */
}
