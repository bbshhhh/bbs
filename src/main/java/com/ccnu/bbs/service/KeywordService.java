package com.ccnu.bbs.service;

import com.ccnu.bbs.entity.Keyword;

import java.util.List;
import java.util.Map;

public interface KeywordService {

    /** 获取帖子关键词列表. */
    List<String> articleKeywords(String articleId);
    /** 获取用户浏览过的文章的关键词及次数. */
    List<Map<String, Object>> userKeywords(String userId);
    /** 查找关键词. */
    Keyword findKeyword(String keywordName);
    /** 创建关键词. */
    Keyword createKeyword(String keywordName);
}
