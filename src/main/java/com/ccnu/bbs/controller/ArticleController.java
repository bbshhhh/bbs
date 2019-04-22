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


    /**
     * 帖子列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(@RequestAttribute String userId,
                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        // 查询帖子列表
        List<ArticleVO> articles = articleService.allArticle(userId, PageRequest.of(page - 1, size));
        return ResultVOUtil.success(articles);
    }

    /**
     * 图片上传
     * @param multipartFiles
     * @return
     */
    @PostMapping("/upload")
    public ResultVO upload(@RequestParam MultipartFile[] multipartFiles){
        // 1.进行图片上传
        try {
            List<String> imgUrls = articleService.uploadImg(Arrays.asList(multipartFiles));
            return ResultVOUtil.success(imgUrls);
        }catch (IOException e){
            return ResultVOUtil.error(ResultEnum.UPLOAD_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 创建帖子
     * @param userId
     * @param articleForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO create(@RequestAttribute String userId,
                           @RequestBody ArticleForm articleForm,
                           BindingResult bindingResult){
        // 1.查看表单参数是否有问题
        if (bindingResult.hasErrors()){
            log.error("【发表帖子】参数不正确, articleForm={}", articleForm);
            throw new BBSException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // 2.将帖子保存进数据库中
        Article article = articleService.createArticle(userId, articleForm);
        // 3.返回帖子id
        HashMap<String, String> map = new HashMap();
        map.put("articleId", article.getArticleId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/content")
    public ResultVO content(@RequestAttribute String userId,
                            @RequestParam String articleId){
        // 查询帖子内容
        if (articleId == null){
            return ResultVOUtil.error(ResultEnum.ARTICLE_ID_ERROR.getCode(), ResultEnum.ARTICLE_ID_ERROR.getMessage());
        }
        return ResultVOUtil.success(articleService.findArticle(userId, articleId));
    }
}
