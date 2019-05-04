package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.searchRepository.ArticleSearchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ArticleSearchRepositoryTest {

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void save() {
        Page<Article> articles = articleRepository.findAll(PageRequest.of(0, 10));
        List<Article> articleList = new ArrayList();
        for (Article article : articles){
            article = articleSearchRepository.save(article);
            articleList.add(article);
        }
        assertNotEquals(0, articleList.size());
    }
}