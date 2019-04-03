package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

    // 查看某用户的粉丝
    @Query("select u from User u, Attention a where u.userId = a.attentionFollowerId and a.attentionUserId = ?1")
    Page<User> findFollower(String userId, Pageable pageable);

    // 查看某用户的关注
    @Query("select u from User u, Attention a where u.userId = a.attentionUserId and a.attentionFollowerId = ?1")
    Page<User> findAttention(String userId, Pageable pageable);

    // 查看收藏了某帖子的用户
    @Query("select u from User u, Collect c where u.userId = c.collectUserId and c.collectArticleId = ?1")
    Page<User> findCollectUser(String articleId, Pageable pageable);

    // 查看点赞了某帖子的用户
    @Query("select u from User u, Like l where u.userId = l.likeUserId and l.likeArticleId = ?1")
    Page<User> findLikeUser(String articleId, Pageable pageable);
}
