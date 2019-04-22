package com.ccnu.bbs.repository;


import com.ccnu.bbs.entity.Like;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface LikeRepository extends JpaRepository<Like, BigInteger> {

    @Query("select l from com.ccnu.bbs.entity.Like l where l.likeArticleId = ?1 and l.likeUserId = ?2")
    @Cacheable(cacheNames = "Like", key = "(#articleId) + '-' + (#userId)")
    Like findLikeArticle(String articleId, String userId);

    @Query("select l from com.ccnu.bbs.entity.Like l where l.likeCommentId = ?1 and l.likeUserId = ?2")
    @Cacheable(cacheNames = "Like", key = "(#commentId) + '-' + (#userId)")
    Like findLikeComment(String commentId, String userId);
}
