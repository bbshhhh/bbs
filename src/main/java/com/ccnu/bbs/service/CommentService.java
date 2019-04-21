package com.ccnu.bbs.service;

import com.ccnu.bbs.VO.CommentVO;
import com.ccnu.bbs.entity.Comment;
import com.ccnu.bbs.forms.CommentForm;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    /** 查询评论列表. */
    List<CommentVO> articleComment(String articleId, Pageable pageable);

    /** 创建评论. */
    Comment createComment(CommentForm commentForm, String userId);

    /** 查找评论. */
    CommentVO findComment(String commentId);
}
