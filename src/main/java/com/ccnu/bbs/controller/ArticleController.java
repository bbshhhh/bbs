package com.ccnu.bbs.controller;

import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.VO.ResultVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.service.Impl.ArticleServiceImpl;
import com.ccnu.bbs.service.Impl.KeywordServiceImpl;
import com.ccnu.bbs.service.Impl.UserServiceImpl;
import com.ccnu.bbs.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private KeywordServiceImpl keywordService;

    @GetMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        // 1.查找出帖子列表并按热度排序
        Page<Article> articles = articleService.allArticle(PageRequest.of(page - 1, size));
        List<ArticleVO> articleVOList = new ArrayList();
        // 2.对每一篇帖子加入作者信息和关键词信息
        for (Article article : articles){
            ArticleVO articleVO = new ArticleVO();
            // 获得文章信息
            BeanUtils.copyProperties(article, articleVO);
            // 查找作者信息
            User user = userService.findUser(article.getArticleUserId());
            BeanUtils.copyProperties(user, articleVO);
            // 查找关键词信息
            List<String> keywords = keywordService.articleKeyword(article.getArticleId());
            articleVO.setKeywords(keywords);
            articleVOList.add(articleVO);
        }
        return ResultVOUtil.success(articleVOList);
    }

}
