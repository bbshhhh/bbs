package com.ccnu.bbs.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ccnu.bbs.VO.CommentVO;
import com.ccnu.bbs.VO.ResultVO;
import com.ccnu.bbs.entity.Comment;
import com.ccnu.bbs.enums.ResultEnum;
import com.ccnu.bbs.exception.BBSException;
import com.ccnu.bbs.forms.CommentForm;
import com.ccnu.bbs.service.Impl.CommentServiceImpl;
import com.ccnu.bbs.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;


    @GetMapping("/list")
    public ResultVO list(@RequestParam String articleId,
                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        // 1.校验帖子id
        if (articleId == null||articleId.isEmpty()){
            return ResultVOUtil.error(ResultEnum.ARTICLE_ID_ERROR.getCode(), ResultEnum.ARTICLE_ID_ERROR.getMessage());
        }
        List<CommentVO> commentVOList = commentService.articleComment(articleId, PageRequest.of(page - 1, size));
        return ResultVOUtil.success(commentVOList);
    }

    @PostMapping("/create")
    public ResultVO create(@RequestAttribute String userId,
                           @RequestBody CommentForm commentForm,
                           BindingResult bindingResult){
        // 1.查看表单参数是否有问题
        if (bindingResult.hasErrors()){
            log.error("【发表评论】参数不正确, articleForm={}", commentForm);
            throw new BBSException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // 2.将评论保存进数据库中
        Comment comment = commentService.createComment(userId, commentForm);
        // 3.返回帖子id
        HashMap<String, String> map = new HashMap();
        map.put("commentId", comment.getCommentId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/content")
    public ResultVO content(@RequestParam String commentId){
        if (commentId == null){
            return ResultVOUtil.error(ResultEnum.COMMENT_ID_ERROR.getCode(), ResultEnum.COMMENT_ID_ERROR.getMessage());
        }
        return ResultVOUtil.success(commentService.findComment(commentId));
    }
}
