package com.ccnu.bbs.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.VO.ResultVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.enums.ResultEnum;
import com.ccnu.bbs.exception.BBSException;
import com.ccnu.bbs.forms.ArticleForm;
import com.ccnu.bbs.service.Impl.ArticleServiceImpl;
import com.ccnu.bbs.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 帖子列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        List<ArticleVO> articles = articleService.allArticle(PageRequest.of(page - 1, size));
        return ResultVOUtil.success(articles);
    }

    /**
     * 图片上传
     * @param sessionId
     * @param multipartFiles
     * @return
     */
    @PostMapping("/upload")
    public ResultVO upload(@RequestParam String sessionId,
                           @RequestParam MultipartFile[] multipartFiles){
        // 1.查看是否有sessionId信息
        if (!redisTemplate.hasKey("sessionId::" + sessionId)){
            return ResultVOUtil.error(ResultEnum.SESSION_ID_NULL.getCode(), ResultEnum.SESSION_ID_NULL.getMessage());
        }
        try {
            List<String> imgUrls = articleService.uploadImg(Arrays.asList(multipartFiles));
            return ResultVOUtil.success(imgUrls);
        }catch (IOException e){
            return ResultVOUtil.error(ResultEnum.UPLOAD_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 创建帖子
     * @param sessionId
     * @param articleForm
     * @param bindingResult
     * @return
     */
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
        // 4.将帖子保存进数据库中
        Article article = articleService.createArticle(articleForm, userId);
        // 5.返回帖子id
        HashMap<String, String> map = new HashMap();
        map.put("articleId", article.getArticleId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/content")
    public ResultVO content(@RequestParam String articleId){
        if (articleId == null){
            return ResultVOUtil.error(ResultEnum.ARTICLE_ID_ERROR.getCode(), ResultEnum.ARTICLE_ID_ERROR.getMessage());
        }
        return ResultVOUtil.success(articleService.findArticle(articleId));
    }
}
