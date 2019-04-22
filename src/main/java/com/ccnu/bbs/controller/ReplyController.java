package com.ccnu.bbs.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ccnu.bbs.VO.ReplyVO;
import com.ccnu.bbs.VO.ResultVO;
import com.ccnu.bbs.entity.Reply;
import com.ccnu.bbs.enums.ResultEnum;
import com.ccnu.bbs.exception.BBSException;
import com.ccnu.bbs.forms.ReplyForm;
import com.ccnu.bbs.service.Impl.ReplyServiceImpl;
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
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyServiceImpl replyService;


    @GetMapping("/list")
    public ResultVO list(@RequestParam String commentId,
                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        // 1.校验评论id
        if (commentId == null||commentId.isEmpty()){
            return ResultVOUtil.error(ResultEnum.COMMENT_ID_ERROR.getCode(), ResultEnum.COMMENT_ID_ERROR.getMessage());
        }
        List<ReplyVO> replyVOList = replyService.commentReply(commentId, PageRequest.of(page - 1, size)).getContent();
        return ResultVOUtil.success(replyVOList);
    }

    @PostMapping("/create")
    public ResultVO create(@RequestAttribute String userId,
                           @RequestBody ReplyForm replyForm,
                           BindingResult bindingResult){
        // 1.查看表单参数是否有问题
        if (bindingResult.hasErrors()){
            log.error("【发表回复】参数不正确, articleForm={}", replyForm);
            throw new BBSException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // 2.将评论保存进数据库中
        Reply reply = replyService.createReply(replyForm, userId);
        // 3.返回评论id
        HashMap<String, String> map = new HashMap();
        map.put("commentId", reply.getReplyId());
        return ResultVOUtil.success(map);
    }
}
