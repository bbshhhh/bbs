package com.ccnu.bbs.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @Test
    public void allTopicTest() {
        topicRepository.findAll().forEach(System.out::println);
    }
}
