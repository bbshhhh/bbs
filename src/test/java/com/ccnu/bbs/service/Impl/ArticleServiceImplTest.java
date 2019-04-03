package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ArticleServiceImplTest {

    @Autowired
    public ArticleServiceImpl articleService;

    @Test
    public void listArticle() {
        Page<Article> res = articleService.listArticle(PageRequest.of(0, 3));
        Assert.assertNotEquals(0, res.getSize());
    }
}