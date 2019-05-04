package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.LikeArticle;
import com.ccnu.bbs.entity.LikeComment;
import com.ccnu.bbs.enums.LikeEnum;
import com.ccnu.bbs.forms.LikeArticleForm;
import com.ccnu.bbs.forms.LikeCommentForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LikeServiceImplTest {

    @Autowired
    private LikeServiceImpl likeService;

    private final static String userId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    private final static String articleId = "dfdf2f73e1134912a642555c072cd3dc";

    private final static String commentId = "4c013ec570754387ad8b7b79156da8cc";

    @Test
    public void isArticleLike() {
        Boolean isLike = likeService.isArticleLike(articleId, userId);
        assertNotNull(isLike);
    }

    @Test
    public void isCommentLike() {
        Boolean isLike = likeService.isCommentLike(commentId, userId);
        assertNotNull(isLike);
    }

    @Test
    public void updateLikeArticle() {
        LikeArticleForm likeArticleForm = new LikeArticleForm();
        likeArticleForm.setLikeArticleId(articleId);
        likeArticleForm.setIsLike(LikeEnum.LIKE.getCode());
        LikeArticle likeArticle = likeService.updateLikeArticle(likeArticleForm, userId);
        assertNotNull(likeArticle);
    }

    @Test
    public void updateLikeComment() {
        LikeCommentForm likeCommentForm = new LikeCommentForm();
        likeCommentForm.setLikeCommentId(commentId);
        likeCommentForm.setIsLike(LikeEnum.LIKE.getCode());
        LikeComment likeComment = likeService.updateLikeComment(likeCommentForm, userId);
        assertNotNull(likeComment);
    }

    @Test
    public void updateLikeArticleDatabase() {
        likeService.updateLikeArticleDatabase();
    }

    @Test
    public void updateLikeCommentDatabase() {
        likeService.updateLikeCommentDatabase();
    }
}