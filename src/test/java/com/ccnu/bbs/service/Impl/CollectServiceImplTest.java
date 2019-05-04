package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.Collect;
import com.ccnu.bbs.enums.CollectEnum;
import com.ccnu.bbs.forms.CollectForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CollectServiceImplTest {

    @Autowired
    private CollectServiceImpl collectService;

    private final static String userId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    private final static String articleId = "dfdf2f73e1134912a642555c072cd3dc";

    @Test
    public void isArticleCollect() {
        Boolean isCollect = collectService.isArticleCollect(articleId, userId);
        assertNotNull(isCollect);
    }

    @Test
    public void updateCollectArticle() {
        CollectForm collectForm = new CollectForm();
        collectForm.setIsCollect(CollectEnum.COLLECT.getCode());
        collectForm.setCollectArticleId(articleId);
        Collect collect = collectService.updateCollectArticle(collectForm, userId);
        assertNotNull(collect);
    }

    @Test
    public void updateCollectDatabase() {
        collectService.updateCollectDatabase();
    }
}