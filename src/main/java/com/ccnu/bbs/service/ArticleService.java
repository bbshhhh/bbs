package com.ccnu.bbs.service;


import com.ccnu.bbs.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    /** 查询帖子列表 */
    Page<Article> listArticle(Pageable pageable);
}
