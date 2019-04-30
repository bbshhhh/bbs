package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.MessageVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MessageServiceImplTest {

    @Autowired
    private MessageServiceImpl messageService;

    private final static String receiverUserId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    @Test
    public void getUserMessage() {
        Page<MessageVO> replyMessages = messageService.getUserMessage(receiverUserId, 1, PageRequest.of(0, 2));
        Page<MessageVO> likeMessages = messageService.getUserMessage(receiverUserId, 0, PageRequest.of(0, 2));
        assertNotEquals(0, replyMessages.getTotalElements());
        assertNotEquals(0, likeMessages.getTotalElements());
    }

    @Test
    public void haveMessage() {
        Boolean haveReplyMessages = messageService.haveMessage(receiverUserId, 1);
        Boolean haveLikeMessages = messageService.haveMessage(receiverUserId, 0);
        assertTrue(haveReplyMessages);
        assertTrue(haveLikeMessages);
    }
}