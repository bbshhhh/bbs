package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void findArticleCommentTest() {
        commentRepository.findArticleComment("1", PageRequest.of(1, 1)).forEach(System.out::println);
    }

    @Test
    public void findUserCommentTest() {
        commentRepository.findUserComment("456", PageRequest.of(0, 1)).forEach(System.out::println);
    }

    @Test
    public void findUserLikeTest() {
        commentRepository.findUserLike("123", PageRequest.of(0, 2)).forEach(System.out::println);
    }
}