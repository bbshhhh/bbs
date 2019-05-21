package com.ccnu.bbs.service.Impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TopicServiceImplTest {

    @Autowired
    private TopicServiceImpl topicService;

    @Test
    public void allTopic() {
        topicService.allTopic().forEach(System.out::println);
    }
}