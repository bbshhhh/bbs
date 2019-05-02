package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.*;
import com.ccnu.bbs.enums.LikeEnum;
import com.ccnu.bbs.enums.MessageEnum;
import com.ccnu.bbs.forms.LikeArticleForm;
import com.ccnu.bbs.forms.LikeCommentForm;
import com.ccnu.bbs.repository.ArticleRepository;
import com.ccnu.bbs.repository.LikeArticleRepository;
import com.ccnu.bbs.repository.LikeCommentRepository;
import com.ccnu.bbs.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeArticleRepository likeArticleRepository;

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MessageServiceImpl messageService;

    @Override
    /**
     * 查看帖子是否被点赞
     */
    public Boolean isArticleLike(String articleId, String userId) {
        LikeArticle likeArticle;
        if (redisTemplate.hasKey("LikeArticle::" + articleId + '-' + userId)){
            likeArticle = (LikeArticle) redisTemplate.opsForValue().get("LikeArticle::" + articleId + '-' + userId);
        }
        else {
            likeArticle = likeArticleRepository.findLikeArticle(articleId, userId);
        }
        if (likeArticle != null && likeArticle.getIsLike() == LikeEnum.LIKE.getCode()) return true;
        else return false;
    }

    @Override
    /**
     * 查看评论是否被点赞
     */
    public Boolean isCommentLike(String commentId, String userId) {
        LikeComment likeComment;
        if (redisTemplate.hasKey("LikeComment::" + commentId + '-' + userId)){
            likeComment = (LikeComment) redisTemplate.opsForValue().get("LikeComment::" + commentId + '-' + userId);
        }
        else {
            likeComment = likeCommentRepository.findLikeComment(commentId, userId);
        }
        if (likeComment != null && likeComment.getIsLike() == LikeEnum.LIKE.getCode()) return true;
        else return false;
    }

    @Override
    /**
     * 帖子点赞更新
     */
    public LikeArticle updateLikeArticle(LikeArticleForm likeArticleForm, String userId) {
        LikeArticle likeArticle;
        String articleId = likeArticleForm.getLikeArticleId();
        // 1.先看redis中有没有帖子点赞信息
        if (redisTemplate.hasKey("LikeArticle::" + articleId + '-' + userId)){
            likeArticle = (LikeArticle) redisTemplate.opsForValue().get("LikeArticle::" + articleId + '-' + userId);
        }
        // 2.若没有则去数据库中查询点赞信息
        else {
            likeArticle = likeArticleRepository.findLikeArticle(articleId, userId);
        }
        // 3.若还是没有则新建点赞信息,并设置帖子id和点赞用户id
        if (likeArticle == null){
            likeArticle = new LikeArticle();
            likeArticle.setLikeArticleId(articleId);
            likeArticle.setLikeUserId(userId);
        }
        // 4.在redis或数据库中查找帖子更新点赞数
        Article article = articleService.getArticle(articleId);
        // 如果点赞信息为空或者为未点赞且要更新的点赞信息为已点赞，则帖子点赞数+1
        if (likeArticle.getIsLike() == null || likeArticle.getIsLike() == LikeEnum.NOT_LIKE.getCode()){
            if (likeArticleForm.getIsLike() == LikeEnum.LIKE.getCode()){
                article.setArticleCommentNum(article.getArticleCommentNum() + 1);
            }
        }
        // 如果点赞信息为已点赞且要更新的点赞信息为未点赞，则帖子点赞数-1
        else {
            if (likeArticleForm.getIsLike() == LikeEnum.NOT_LIKE.getCode()){
                article.setArticleLikeNum(article.getArticleLikeNum() - 1);
            }
        }
        // 将帖子存入redis
        redisTemplate.opsForValue().set("Article::" + articleId, article);
        // 5.设置点赞状态
        likeArticle.setIsLike(likeArticleForm.getIsLike());
        // 6.将点赞信息存入redis
        redisTemplate.opsForValue().set("LikeArticle::" + articleId + '-' + userId, likeArticle);
        // 7.创建点赞新消息通知被点赞者
        Message message = new Message();
        message.setArticleId(articleId);
        message.setMessageType(MessageEnum.LIKE_MESSAGE.getCode());
        message.setReceiverUserId(article.getArticleUserId());
        message.setSenderUserId(userId);
        message.setRepliedContent(article.getArticleContent());
        message.setMessageContent("赞了你的帖子");
        messageService.createMessage(message);
        return likeArticle;
    }

    @Override
    /**
     * 评论点赞更新
     */
    public LikeComment updateLikeComment(LikeCommentForm likeCommentForm, String userId) {
        LikeComment likeComment;
        String commentId = likeCommentForm.getLikeCommentId();
        // 1.先看redis中有没有帖子点赞信息
        if (redisTemplate.hasKey("LikeComment::" + commentId + '-' + userId)){
            likeComment = (LikeComment) redisTemplate.opsForValue().get("LikeComment::" + commentId + '-' + userId);
        }
        // 2.若没有则去数据库中查询点赞信息
        else {
            likeComment = likeCommentRepository.findLikeComment(commentId, userId);
        }
        // 3.若还是没有则新建点赞信息,并设置帖子id和点赞用户id
        if (likeComment == null){
            likeComment = new LikeComment();
            likeComment.setLikeCommentId(commentId);
            likeComment.setLikeUserId(userId);
        }
        // 4.在redis或数据库中找到评论
        Comment comment = commentService.getComment(commentId);
        // 5.设置点赞状态
        likeComment.setIsLike(likeCommentForm.getIsLike());
        // 6.将点赞信息存入redis
        redisTemplate.opsForValue().set("LikeComment::" + commentId + '-' + userId, likeComment);
        // 7.创建点赞新消息通知被点赞者
        Message message = new Message();
        message.setArticleId(comment.getCommentArticleId());
        message.setMessageType(MessageEnum.LIKE_MESSAGE.getCode());
        message.setReceiverUserId(comment.getCommentUserId());
        message.setSenderUserId(userId);
        message.setRepliedContent(comment.getCommentContent());
        message.setMessageContent("赞了你的评论");
        messageService.createMessage(message);
        return likeComment;
    }

    @Override
    /**
     * 从redis更新帖子点赞到数据库
     */
    public void updateLikeArticleDatabase() {
        // 1.找到所有有关收藏的key
        Set<String> likeArticleKeys = redisTemplate.keys("LikeArticle::*");
        // 2.保存数据到数据库并清除redis中数据
        for (String likeArticleKey : likeArticleKeys){
            LikeArticle likeArticle = (LikeArticle) redisTemplate.opsForValue().get(likeArticleKey);
            likeArticleRepository.save(likeArticle);
            redisTemplate.delete(likeArticleKey);
        }
        return;
    }

    @Override
    /**
     * 从redis更新评论点赞到数据库
     */
    public void updateLikeCommentDatabase() {
        // 1.找到所有有关收藏的key
        Set<String> likeCommentKeys = redisTemplate.keys("LikeComment::*");
        // 2.保存数据到数据库并清除redis中数据
        for (String likeCommentKey : likeCommentKeys){
            LikeComment likeComment = (LikeComment) redisTemplate.opsForValue().get(likeCommentKey);
            likeCommentRepository.save(likeComment);
            redisTemplate.delete(likeCommentKey);
        }
        return;
    }
}