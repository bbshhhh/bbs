package com.ccnu.bbs.service;

import com.ccnu.bbs.VO.CommentVO;
import com.ccnu.bbs.entity.Comment;
import com.ccnu.bbs.forms.CommentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    /** 热评列表. */
    List<CommentVO> hotArticleComment(String articleId);

    /** 查询评论列表. */
    Page<CommentVO> articleComment(String articleId, Pageable pageable);

    /** 创建评论. */
    Comment createComment(String userId, CommentForm commentForm);

    /** 查找评论. */
    CommentVO findComment(String commentId);
}
