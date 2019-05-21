package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.VO.CommentVO;
import com.ccnu.bbs.entity.Comment;
import com.ccnu.bbs.forms.CommentForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    private final static String userId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    private final static String articleId = "dfdf2f73e1134912a642555c072cd3dc";

    private final static String commentId = "4c013ec570754387ad8b7b79156da8cc";

    @Test
    public void hotArticleComment() {
        List<CommentVO> comments = commentService.hotArticleComment(userId,articleId);
        comments.forEach(System.out::println);
//        assertNotEquals(0, comments.size());
    }

    @Test
    public void articleComment() {
        Page<CommentVO> comments = commentService.articleComment(userId, articleId, PageRequest.of(0, 2));
        comments.forEach(System.out::println);
        assertNotEquals(0, comments.getTotalElements());
    }

    @Test
    public void createComment() {
        CommentForm commentForm = new CommentForm();
        commentForm.setArticleId(articleId);
        commentForm.setCommentContent("这小说好看吗？");
        Comment comment = commentService.createComment(userId, commentForm);
        assertNotNull(comment);
    }

    @Test
    public void findComment() {
        CommentVO comment = commentService.findComment(commentId, userId);
        assertNotNull(comment);
    }

    @Test
    public void getComment() {
        Comment comment = commentService.getComment(commentId);
        assertNotNull(comment);
    }

    @Test
    public void updateCommentDatabase() {
        commentService.updateCommentDatabase();
    }
}