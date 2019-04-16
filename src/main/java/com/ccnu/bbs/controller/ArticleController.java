package com.ccnu.bbs.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.VO.ResultVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.enums.ResultEnum;
import com.ccnu.bbs.exception.BBSException;
import com.ccnu.bbs.forms.ArticleForm;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

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
            // 获得图片url
            if (article.getArticleImg()!=null){
                articleVO.setArticleImages(Arrays.asList(article.getArticleImg().split(";")));
            }
            // 查找关键词信息
            List<String> keywords = keywordService.articleKeyword(article.getArticleId());
            articleVO.setKeywords(keywords);
            articleVOList.add(articleVO);
        }
        return ResultVOUtil.success(articleVOList);
    }

    @PostMapping("/create")
    public ResultVO create(@RequestParam String sessionId,
                           @RequestBody ArticleForm articleForm,
                           BindingResult bindingResult){
        // 1.查看是否有sessionId信息
        if (!redisTemplate.hasKey("sessionId::" + sessionId)){
            return ResultVOUtil.error(ResultEnum.SESSION_ID_NULL.getCode(), ResultEnum.SESSION_ID_NULL.getMessage());
        }
        // 2.查看表单参数是否有问题
        if (bindingResult.hasErrors()){
            log.error("【发表帖子】参数不正确, articleForm={}", articleForm);
            throw new BBSException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // 3.得到用户信息
        WxMaJscode2SessionResult session = (WxMaJscode2SessionResult) redisTemplate.opsForValue().get("sessionId::" + sessionId);
        String userId = session.getOpenid();
        try {
            // 4.将帖子保存进数据库中
            Article article = articleService.createArticle(articleForm, userId);
            // 5.返回帖子id
            HashMap<String, String> map = new HashMap();
            map.put("articleId", article.getArticleId());
            return ResultVOUtil.success(map);
        }catch (IOException e){
            return ResultVOUtil.error(ResultEnum.UPLOAD_ERROR.getCode(), e.getMessage());
        }
    }
}
