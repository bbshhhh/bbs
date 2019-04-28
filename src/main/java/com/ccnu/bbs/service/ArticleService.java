package com.ccnu.bbs.service;

import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.forms.ArticleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    /** 查询帖子列表. */
    List<ArticleVO> allArticle(Pageable pageable);

    /** 创建帖子. */
    Article createArticle(String userId, ArticleForm articleForm);

    /** 上传图片. */
    String uploadImg(MultipartFile multipartFile) throws IOException;

    /** 查找帖子. */
    ArticleVO findArticle(String articleId);
}
