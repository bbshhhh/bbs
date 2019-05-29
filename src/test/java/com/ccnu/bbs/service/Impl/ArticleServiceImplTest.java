package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.forms.ArticleForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
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
public class ArticleServiceImplTest {

    @Autowired
    private ArticleServiceImpl articleService;

    private final static String userId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    private final static String articleId = "dfdf2f73e1134912a642555c072cd3dc";

    @Test
    public void allArticle() {
        articleService.allArticle(PageRequest.of(1, 2)).forEach(System.out::println);
    }

    @Test
    public void searchArticle() {
        Page<ArticleVO> articles = articleService.searchArticle("创建帖子测试武侠", PageRequest.of(0, 10));
        assertNotEquals(0, articles.getTotalElements());
    }

    @Test
    public void deleteImg() {
        String imgUrl = "http://img.ccnunercel.cn/bbs/5a901650ffff43f4955122daa286704a";
        try {
            Response response = articleService.deleteImg(imgUrl);
            assertEquals(true, response.isOK());
        }catch (QiniuException e){
            e.printStackTrace();
        }
    }

    @Test
    public void createArticle() {
        ArticleForm articleForm = new ArticleForm();
        articleForm.setArticleTitle("傲世九重天");
        articleForm.setArticleContent("一笑风雷震，一怒沧海寒；一手破苍穹，一剑舞长天！好男儿，就是要，舞风云、凌天下、做君主、傲世九重天！谁陪我，琼霄舞风云？谁伴我，傲世九重天。");
        List<String> imgUrls = new ArrayList();
        imgUrls.add("https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=366308bd523d26973ade000f3492d99e/3b292df5e0fe99252abe5c2737a85edf8db17112.jpg");
        articleForm.setImgUrls(imgUrls);
        articleForm.setArticleKeywords("武侠  玄幻");
        Article article = articleService.createArticle(userId, articleForm);
        assertNotNull(article);
    }

    @Test
    public void findArticle() {
        ArticleVO articleVO = articleService.findArticle(articleId, userId);
        assertNotNull(articleVO);
    }

    @Test
    public void getArticle() {
        Article article = articleService.getArticle(articleId);
        assertNotNull(article);
    }

    @Test
    public void deleteArticle() {
        articleService.deleteArticle(articleId);
    }

    @Test
    public void findUserArticle() {
        articleService.findUserArticle(userId, PageRequest.of(0, 2)).forEach(System.out::println);
    }

    @Test
    public void findCollectArticle() {
        articleService.findCollectArticle(userId, PageRequest.of(0, 2)).forEach(System.out::println);
    }

    @Test
    public void updateArticleDatabase() {
        articleService.updateArticleDatabase();
    }
}