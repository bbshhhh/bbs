package com.ccnu.bbs.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void findAllTest(){
        articleRepository.findAll(PageRequest.of(0, 1)).forEach(System.out::println);
    }

    @Test
    public void findAllByZoneTest(){
        articleRepository.findAllByTopic(0, PageRequest.of(0, 2)).forEach(System.out::println);
    }

    @Test
    public void findUserArticleTest(){
        articleRepository.findUserArticle("123", PageRequest.of(0, 1)).forEach(System.out::println);
    }

    @Test
    public void findUserCollectTest() {
        articleRepository.findUserCollect("123", PageRequest.of(1, 1)).forEach(System.out::println);
    }

    @Test
    public void findUserLikeTest() {
        articleRepository.findUserLike("123", PageRequest.of(0, 1)).forEach(System.out::println);
    }

}