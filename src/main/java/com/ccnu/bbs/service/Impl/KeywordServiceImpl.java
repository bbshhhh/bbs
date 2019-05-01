package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.Keyword;
import com.ccnu.bbs.repository.KeywordRepository;
import com.ccnu.bbs.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    @Override
    /**
     * 获取帖子关键词列表
     */
    @Cacheable(cacheNames = "Keywords:Article", key = "#articleId")
    public List<String> articleKeywords(String articleId){
        return keywordRepository.findArticleKeyword(articleId);
    }

    @Override
    /**
     * 获取用户浏览过的文章的关键词及次数
     */
    @Cacheable(cacheNames = "Keywords:User", key = "#userId")
    public List<Map<String, Object>> userKeywords(String userId){
        return keywordRepository.findUserKeyword(userId);
    }

    @Override
    /**
     * 查找关键词
     */
    @Cacheable(cacheNames = "Keyword", key = "#keywordName")
    public Keyword findKeyword(String keywordName){
        return keywordRepository.findKeyword(keywordName);
    }

    @Override
    /**
     * 创建关键词
     */
    public Keyword createKeyword(String keywordName){
        Keyword keyword = new Keyword();
        keyword.setKeywordName(keywordName);
        return keywordRepository.save(keyword);
    }

}
