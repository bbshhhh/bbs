package com.ccnu.bbs.service.Impl;

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
    @Cacheable(cacheNames = "Keywords:Article", key = "#articleId")
    public List<String> articleKeywords(String articleId){
        return keywordRepository.findArticleKeyword(articleId);
    }

    @Override
    @Cacheable(cacheNames = "Keywords:User", key = "#userId")
    public List<Map<String, Object>> userKeywords(String userId){
        return keywordRepository.findUserKeyword(userId);
    }

}
