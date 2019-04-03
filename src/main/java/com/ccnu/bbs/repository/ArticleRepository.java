package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.math.BigInteger;

public interface ArticleRepository extends JpaRepository<Article, BigInteger> {

    // 分页查看所有帖子
    @Query("select a from Article a order by a.articleHotNum desc")
    Page<Article> findAll(Pageable pageable);

    // 查看用户所发帖子
    @Query("select a from Article a where a.articleUserId = ?1 order by a.articleCreateTime desc")
    Page<Article> findUserArticle(String userId, Pageable pageable);

    // 查看被某位用户收藏的帖子
    @Query("select a from Article a, Collect c where " +
            "a.articleId = c.collectArticleId and c.collectUserId = ?1 order by a.articleCreateTime desc ")
    Page<Article> findUserCollect(String userId, Pageable pageable);

    // 查看被某位用户点赞的帖子
    @Query("select a from Article a, Like l where " +
            "a.articleId = l.likeArticleId and l.likeUserId = ?1 order by a.articleCreateTime desc ")
    Page<Article> findUserLike(String userId, Pageable pageable);

    @Transactional
    void deleteByArticleId(String articleId);
}
