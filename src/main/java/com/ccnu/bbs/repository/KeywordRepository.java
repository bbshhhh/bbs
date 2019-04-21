package com.ccnu.bbs.repository;


import com.ccnu.bbs.entity.Keyword;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    // 查看某篇文章的关键词
    @Query("select k.keywordName from Keyword k, com.ccnu.bbs.entity.Extract e where " +
            "k.keywordId = e.extractKeywordId and e.extractArticleId = ?1")
    @Cacheable(cacheNames = "Keywords:Article", key = "#articleId")
    List<String> findArticleKeyword(String articleId);

    // 查看某用户浏览过的文章的关键词及次数
    @Query("select new map(k.keywordName, b.browseCount) from Keyword k, com.ccnu.bbs.entity.Browse b where " +
            "k.keywordId = b.browseKeywordId and b.browseUserId = ?1 order by b.browseCount desc")
    @Cacheable(cacheNames = "Keywords:User", key = "#userId")
    List<Map<String, Object>> findUserKeyword(String userId);
}
