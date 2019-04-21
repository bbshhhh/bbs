package com.ccnu.bbs.repository;


import com.ccnu.bbs.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, String> {

    // 查看某个帖子的评论(按照点赞数降序排列)
    @Query("select c from Comment c where c.commentArticleId = ?1 order by c.commentLikeNum desc")
    Page<Comment> findArticleComment(String articleId, Pageable pageable);

    // 查看某个用户的评论((按照评论时间降序排列)
    @Query("select c from Comment c where c.commentUserId = ?1 order by c.commentTime desc")
    Page<Comment> findUserComment(String userId, Pageable pageable);

    // 查看被某个用户点赞的评论
    @Query("select c from Comment c, com.ccnu.bbs.entity.Like l where " +
            "c.commentId = l.likeCommentId and l.likeUserId = ?1")
    Page<Comment> findUserLike(String userId, Pageable pageable);

    @Query("select c from Comment c where c.commentId = ?1")
    Comment findComment(String commentId);

    @Transactional
    void deleteByCommentArticleId(String commentId);
}
