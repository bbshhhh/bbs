package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.ReplyVO;
import com.ccnu.bbs.entity.Reply;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.forms.ReplyForm;
import com.ccnu.bbs.repository.ReplyRepository;
import com.ccnu.bbs.repository.UserRepository;
import com.ccnu.bbs.service.ReplyService;
import com.ccnu.bbs.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    /**
     * 查找评论的回复列表
     */
    public Page<ReplyVO> commentReply(String commentId, Pageable pageable) {
        // 1.根据评论id查询出回复
        Page<Reply> replies = replyRepository.findCommentReply(commentId, pageable);
        List<ReplyVO> replyVOList = new ArrayList();
        // 2.将用户信息放入评论中
        for (Reply reply : replies){
            ReplyVO replyVO = new ReplyVO();
            // 获得回复信息
            BeanUtils.copyProperties(reply, replyVO);
            // 获得回复作者信息
            User user = userRepository.findByUserId(reply.getReplyUserId());
            BeanUtils.copyProperties(user, replyVO);
            replyVOList.add(replyVO);
        }
        return new PageImpl(replyVOList, pageable, replies.getTotalElements());
    }

    @Override
    /**
     * 创建回复
     */
    public Reply createReply(ReplyForm replyForm, String userId) {
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyForm, reply);
        // 1.设置主键
        reply.setReplyId(KeyUtil.genUniqueKey());
        // 2.设置评论id
        reply.setReplyCommentId(replyForm.getCommentId());
        // 3.设置回复者
        reply.setReplyUserId(userId);
        return replyRepository.save(reply);
    }

}
