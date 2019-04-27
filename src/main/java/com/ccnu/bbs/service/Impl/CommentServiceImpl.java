package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.CommentVO;
import com.ccnu.bbs.VO.ReplyVO;
import com.ccnu.bbs.entity.Comment;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.forms.CommentForm;
import com.ccnu.bbs.repository.CommentRepository;
import com.ccnu.bbs.service.CommentService;
import com.ccnu.bbs.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ReplyServiceImpl replyService;

    @Autowired
    private LikeServiceImpl likeService;

    @Override
    /**
     * 查询帖子评论列表
     */
    public List<CommentVO> articleComment(String articleId, Pageable pageable) {
        // 1.根据帖子id查询评论，并按照评论点赞数降序排列
        Page<Comment> comments = commentRepository.findArticleComment(articleId, pageable);
        List<CommentVO> commentVOList = new ArrayList();
        // 2.对每一个评论加入评论作者信息和回复信息
        for (Comment comment : comments){
            CommentVO commentVO = comment2commentVO(comment.getCommentUserId(), comment);
            commentVOList.add(commentVO);
        }
        return commentVOList;
    }

    @Override
    /**
     * 创建评论
     */
    public Comment createComment(String userId, CommentForm commentForm){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentForm, comment);
        // 1.设置主键
        comment.setCommentId(KeyUtil.genUniqueKey());
        // 2.设置文章id
        comment.setCommentArticleId(commentForm.getArticleId());
        // 3.设置评论者
        comment.setCommentUserId(userId);
        return commentRepository.save(comment);
    }

    @Override
    /**
     * 查看评论
     */
    @Cacheable(cacheNames = "Comment", key = "#commentId")
    public CommentVO findComment(String commentId){
        Comment comment = commentRepository.findComment(commentId);
        CommentVO commentVO = null;
        if (comment != null){
            commentVO = comment2commentVO(comment.getCommentUserId(), comment);
        }
        return commentVO;
    }

    /**
     * 评论内容拼装
     * @param userId
     * @param comment
     * @return
     */
    CommentVO comment2commentVO(String userId, Comment comment){

        CommentVO commentVO = new CommentVO();
        // 获得评论信息
        BeanUtils.copyProperties(comment, commentVO);
        // 查找作者信息
        User user = userService.findUser(comment.getCommentUserId());
        BeanUtils.copyProperties(user, commentVO);
        // 查找回复信息
        List<ReplyVO> replies = replyService.commentReply(comment.getCommentId(), PageRequest.of(0, 3)).getContent();
        commentVO.setReplies(replies);
        // 查看评论是否被当前用户点赞
        commentVO.setIsLike(likeService.isCommentLike(comment.getCommentId(), userId));
        return commentVO;
    }
}
