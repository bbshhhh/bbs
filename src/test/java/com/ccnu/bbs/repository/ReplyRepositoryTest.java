package com.ccnu.bbs.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void findCommentReplyTest() {
        replyRepository.findCommentReply("123", PageRequest.of(0, 1)).forEach(System.out::println);
    }

    @Test
    public void findUserReplyTest() {
        replyRepository.findUserReply("456", PageRequest.of(0, 1)).forEach(System.out::println);
    }
}