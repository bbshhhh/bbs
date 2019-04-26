package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.LikeComment;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface LikeCommentRepository extends JpaRepository<LikeComment, BigInteger> {

    @Query("select l from LikeComment l where l.likeCommentId = ?1 and l.likeUserId = ?2")
    @Cacheable(cacheNames = "LikeComment", key = "(#commentId) + '-' + (#userId)")
    LikeComment findLikeComment(String commentId, String userId);
}
