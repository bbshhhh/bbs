package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Message;
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
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private final static String receiverUserId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    @Test
    public void findMessageTest() {
        Page<Message> replyMessages = messageRepository.findMessage(receiverUserId, 1, PageRequest.of(0, 2));
        Page<Message> likeMessages = messageRepository.findMessage(receiverUserId, 0, PageRequest.of(0, 2));
        assertNotEquals(0, replyMessages.getTotalElements());
        assertNotEquals(0, likeMessages.getTotalElements());
    }

    @Test
    public void haveNewMessageTest() {
        Integer replyMessageCount = messageRepository.haveNewMessage(receiverUserId, 1);
        Integer likeMessageCount = messageRepository.haveNewMessage(receiverUserId, 0);
        assertNotEquals(Integer.valueOf(0), replyMessageCount);
        assertNotEquals(Integer.valueOf(0), likeMessageCount);
    }
}