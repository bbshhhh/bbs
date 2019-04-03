package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.repository.ArticleRepository;
import com.ccnu.bbs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Page<Article> listArticle(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
