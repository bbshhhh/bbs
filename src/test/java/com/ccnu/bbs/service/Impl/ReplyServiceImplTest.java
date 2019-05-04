package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.ReplyVO;
import com.ccnu.bbs.entity.Reply;
import com.ccnu.bbs.forms.ReplyForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ReplyServiceImplTest {

    @Autowired
    private ReplyServiceImpl replyService;

    private final static String userId = "oRp4Z402QnQqQIdcR3C3Z3fyIQu4";

    private final static String commentId = "4c013ec570754387ad8b7b79156da8cc";

    @Test
    public void createReply() {
        ReplyForm replyForm = new ReplyForm();
        replyForm.setCommentId(commentId);
        replyForm.setReplyContent("好看，我已经看完了，根本停不下来");
        Reply reply = replyService.createReply(replyForm, userId);
        assertNotNull(reply);
    }

    @Test
    public void commentReply() {
        Page<ReplyVO> replies = replyService.commentReply(commentId, PageRequest.of(0, 2));
        assertNotEquals(0, replies.getTotalElements());
    }
}