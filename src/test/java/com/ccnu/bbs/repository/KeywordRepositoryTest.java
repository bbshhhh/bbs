package com.ccnu.bbs.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class KeywordRepositoryTest {

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    public void findArticleKeywordTest() {
        keywordRepository.findArticleKeywory("1").forEach(System.out::println);
    }

    @Test
    public void findUserKeywordTest() {
        keywordRepository.findUserKeyword("123").forEach(System.out::println);
    }
}