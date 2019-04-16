package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.repository.KeywordRepository;
import com.ccnu.bbs.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    @Override
    @Cacheable(cacheNames = "Keywords", key = "#articleId")
    public List<String> articleKeyword(String articleId){
        return keywordRepository.findArticleKeyword(articleId);
    }
}
