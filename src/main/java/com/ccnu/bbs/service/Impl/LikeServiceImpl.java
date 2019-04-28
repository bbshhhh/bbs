package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.LikeArticle;
import com.ccnu.bbs.entity.LikeComment;
import com.ccnu.bbs.enums.LikeEnum;
import com.ccnu.bbs.forms.LikeArticleForm;
import com.ccnu.bbs.forms.LikeCommentForm;
import com.ccnu.bbs.repository.LikeArticleRepository;
import com.ccnu.bbs.repository.LikeCommentRepository;
import com.ccnu.bbs.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeArticleRepository likeArticleRepository;

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Override
    /**
     * 查看帖子是否被点赞
     */
    @Cacheable(cacheNames = "LikeArticle", key = "(#articleId) + '-' + (#userId)")
    public Boolean isArticleLike(String articleId, String userId) {
        LikeArticle likeArticle = likeArticleRepository.findLikeArticle(articleId, userId);
        if (likeArticle != null && likeArticle.getIsLike() == LikeEnum.LIKE.getCode()) return true;
        else return false;
    }

    @Override
    /**
     * 查看评论是否被点赞
     */
    @Cacheable(cacheNames = "LikeComment", key = "(#commentId) + '-' + (#userId)")
    public Boolean isCommentLike(String commentId, String userId) {
        LikeComment likeComment = likeCommentRepository.findLikeComment(commentId, userId);
        if (likeComment != null && likeComment.getIsLike() == LikeEnum.LIKE.getCode()) return true;
        else return false;
    }

    @Override
    /**
     * 帖子点赞更新
     */
    @CacheEvict(cacheNames = "LikeArticle", key = "(#likeArticleForm.likeArticleId) + '-' + (#userId)")
    public LikeArticle updateLikeArticle(LikeArticleForm likeArticleForm, String userId) {
        return null;
    }

    @Override
    /**
     * 评论点赞更新
     */
    @CacheEvict(cacheNames = "LikeComment", key = "(#likeCommentForm.likeCommentId) + '-' + (#userId)")
    public LikeComment updateLikeComment(LikeCommentForm likeCommentForm, String userId) {
        return null;
    }
}
