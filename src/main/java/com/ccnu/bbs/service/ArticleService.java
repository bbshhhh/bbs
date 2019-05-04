package com.ccnu.bbs.service;

import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.forms.ArticleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArticleService {

    /** 查询帖子列表. */
    Page<ArticleVO> allArticle(Pageable pageable);

    /** 搜索帖子. */
//    Page<ArticleVO> searchArticle(String searchKey, Pageable pageable);

    /** 创建帖子. */
    Article createArticle(String userId, ArticleForm articleForm);

    /** 上传图片. */
    String uploadImg(MultipartFile multipartFile) throws IOException;

    /** 浏览帖子. */
    ArticleVO findArticle(String articleId);

    /** 查找帖子. */
    Article getArticle(String articleId);

    /** 删除帖子. */
    void deleteArticle(String aritcleId);

    /** 查找用户发表的帖子. */
    Page<ArticleVO> findUserArticle(String userId, Pageable pageable);

    /** 查找用户收藏的帖子. */
    Page<ArticleVO> findCollectArticle(String userId, Pageable pageable);

    /** 从redis更新数据库. */
    void updateArticleDatabase();
}
