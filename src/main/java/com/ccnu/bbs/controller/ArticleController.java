package com.ccnu.bbs.controller;

import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.VO.ResultVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.enums.ResultEnum;
import com.ccnu.bbs.exception.BBSException;
import com.ccnu.bbs.forms.ArticleForm;
import com.ccnu.bbs.forms.CollectForm;
import com.ccnu.bbs.forms.LikeArticleForm;
import com.ccnu.bbs.service.Impl.ArticleServiceImpl;
import com.ccnu.bbs.service.Impl.CollectServiceImpl;
import com.ccnu.bbs.service.Impl.LikeServiceImpl;
import com.ccnu.bbs.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private CollectServiceImpl collectService;

    /**
     * 帖子列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        // 查询帖子列表
        Page<ArticleVO> articles = articleService.allArticle(PageRequest.of(page - 1, size));
        return ResultVOUtil.success(articles);
    }

    @GetMapping("/search")
    public ResultVO search(@RequestParam String searchKey,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size){
        // 搜索帖子列表
        Page<ArticleVO> articles = articleService.searchArticle(searchKey, PageRequest.of(page - 1, size));
        return ResultVOUtil.success(articles);
    }

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    public ResultVO upload(@RequestParam MultipartFile multipartFile){
        // 1.进行图片上传
        try {
            String imgUrl = articleService.uploadImg(multipartFile);
            return ResultVOUtil.success(imgUrl);
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


    /**
     * 删除帖子
     * @param articleId
     * @return
     */
    @GetMapping("/delete")
    public ResultVO delete(@RequestParam String articleId){
        if (articleId == null){
            return ResultVOUtil.error(ResultEnum.ARTICLE_ID_ERROR.getCode(), ResultEnum.ARTICLE_ID_ERROR.getMessage());
        }
        try {
            articleService.deleteArticle(articleId);
            return ResultVOUtil.success();
        }
        catch (BBSException e){
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 帖子内容
     * @param articleId
     * @return
     */
    @GetMapping("/content")
    public ResultVO content(@RequestParam String articleId){
        // 查询帖子内容
        if (articleId == null){
            return ResultVOUtil.error(ResultEnum.ARTICLE_ID_ERROR.getCode(), ResultEnum.ARTICLE_ID_ERROR.getMessage());
        }
        try{
            return ResultVOUtil.success(articleService.findArticle(articleId));
        }catch (BBSException e){
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }

    }

    /**
     * 帖子点赞
     * @param userId
     * @param likeArticleForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/like")
    public ResultVO like(@RequestAttribute String userId,
                         @RequestBody LikeArticleForm likeArticleForm,
                         BindingResult bindingResult){
        // 1.查看表单参数是否有问题
        if (bindingResult.hasErrors()){
            log.error("【帖子点赞】参数不正确, likeArticleForm={}", likeArticleForm);
            throw new BBSException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            likeService.updateLikeArticle(likeArticleForm, userId);
            return ResultVOUtil.success();
        }
        catch (BBSException e){
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 帖子收藏
     * @param userId
     * @param collectForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/collect")
    public ResultVO collect(@RequestAttribute String userId,
                            @RequestBody CollectForm collectForm,
                            BindingResult bindingResult){
        // 1.查看表单参数是否有问题
        if (bindingResult.hasErrors()){
            log.error("【帖子收藏】参数不正确, likeArticleForm={}", collectForm);
            throw new BBSException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        collectService.updateCollectArticle(collectForm, userId);
        return ResultVOUtil.success();
    }

    /**
     * 我的帖子
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/myArticle")
    public ResultVO myArticle(@RequestAttribute String userId,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size){
        // 查询用户帖子
        Page<ArticleVO> articles = articleService.findUserArticle(userId, PageRequest.of(page - 1, size));
        return ResultVOUtil.success(articles);
    }

    /**
     * 我的收藏
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/myCollect")
    public ResultVO myCollect(@RequestAttribute String userId,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size){
        Page<ArticleVO> articles = articleService.findCollectArticle(userId, PageRequest.of(page - 1, size));
        return ResultVOUtil.success(articles);
    }
}
